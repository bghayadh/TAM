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

@RestController
@RequestMapping("/DownloadAttachment")
public class FileDownloadController {

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
        JSch jsch = new JSch();
        com.jcraft.jsch.Session session = jsch.getSession("USER", "localhost", 22);

        session.setPassword("zeinab123");
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
