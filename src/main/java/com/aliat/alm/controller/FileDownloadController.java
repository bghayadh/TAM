package com.aliat.alm.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jcraft.jsch.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import com.aliat.alm.common.AlmDbSession;


@RestController
@RequestMapping("/DownloadAttachment")
public class FileDownloadController {
	private static Session session1 = null;
	private static Query query = null;
	private static Transaction tx = null;



    @GetMapping
    public ResponseEntity<InputStreamResource> downloadFile(
            @RequestParam("attachmentName") String attachmentName,
            @RequestParam("attachmentPath") String attachmentPath) {

        try {
            System.out.println("Attempting to download file: " + attachmentPath + attachmentName);

            // Retrieve the file content
            byte[] fileContent = getFileContent(attachmentPath, attachmentName);


            if (fileContent == null) {
                return ResponseEntity.notFound().build();
            }

            // Create InputStream from the file content
            InputStream inputStream = new ByteArrayInputStream(fileContent);

            // Determine file type based on attachment name
            String mimeType = attachmentName.endsWith(".pdf") ? "application/pdf" : "image/jpeg";
            


            // Prepare HTTP response with file content
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(mimeType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachmentName + "\"")
                    .body(new InputStreamResource(inputStream));

        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    private byte[] getFileContent(String attachmentPath, String attachmentName) throws JSchException, SftpException, IOException {
       

    	String username="",pass="",hostname="";
    	
        session1 = AlmDbSession.getInstance().getSession();
		if (session1 != null && session1.isOpen()) {
			tx = session1.beginTransaction();

			try {

    	 query = session1.createNativeQuery(
				"select USERNAME,PASSWORD,IP_ADDRESS FROM SYSTEM_SETTINGS");
    	List<Object[]> results = query.getResultList();
		if(results.size()>0) {
			for (Object[] row : results) {
				username=(String) row[0];
				pass=(String) row[1];
				hostname=(String) row[2];
			}
		}
			 
			} 
			catch (Exception e) {
				System.out.println("Failed to fetch system settings");
			}
			finally {
				if (session1 != null && session1.isOpen()) {
					session1.close();
				}
			}
		}
    	JSch jsch = new JSch();
        com.jcraft.jsch.Session session = jsch.getSession(username,hostname, 22);

        session.setPassword(pass);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();

        ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
        channelSftp.connect();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        channelSftp.get(attachmentPath + attachmentName, outputStream);

        channelSftp.disconnect();
        session.disconnect();

        return outputStream.toByteArray();
    }
}
