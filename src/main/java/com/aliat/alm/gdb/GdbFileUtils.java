package com.aliat.alm.gdb;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class GdbFileUtils {

	public static void runOgr2Ogr(File shapefileSrcDir, File gdbOutputDir) throws IOException, InterruptedException {
		// Requires GDAL 3.6+ with OpenFileGDB write support installed on the server
		ProcessBuilder pb = new ProcessBuilder("ogr2ogr", "-f", "OpenFileGDB", gdbOutputDir.getAbsolutePath(),
				shapefileSrcDir.getAbsolutePath());
		pb.redirectErrorStream(true);
		Process process = pb.start();

		StringBuilder log = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				log.append(line).append("\n");
			}
		}

		boolean finished = process.waitFor(90, TimeUnit.SECONDS);
		if (!finished) {
			process.destroyForcibly();
			throw new RuntimeException("ogr2ogr timed out");
		}
		if (process.exitValue() != 0) {
			throw new RuntimeException("ogr2ogr failed: " + log);
		}
	}

	public static void zipMultipleDirectories(File zipFile, File... sourceDirs) throws IOException {
		try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
			for (File sourceDir : sourceDirs) {
				Path sourcePath = sourceDir.toPath();
				Files.walk(sourcePath).filter(path -> !Files.isDirectory(path)).forEach(path -> {
					// sourceDir.getName() keeps each folder's own name as a top-level entry in the
					// zip,
					// so both folders stay separate once extracted, instead of their files getting
					// mixed together
					String entryName = sourceDir.getName() + "/"
							+ sourcePath.relativize(path).toString().replace("\\", "/");
					try {
						zos.putNextEntry(new ZipEntry(entryName));
						Files.copy(path, zos);
						zos.closeEntry();
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				});
			}
		}
	}

	public static void deleteRecursively(File file) {
		if (file == null || !file.exists())
			return;
		File[] children = file.listFiles();
		if (children != null) {
			for (File child : children)
				deleteRecursively(child);
		}
		file.delete();
	}

	public static String sanitizeFileName(String name) {
		return name.replaceAll("[^a-zA-Z0-9_\\-]", "_");
	}
}