package com.aliat.alm.models;

public class ExportEntry {
    private final String filePath;
    private final String fileName;
    private final long createdAt;

    public ExportEntry(String filePath, String fileName, long createdAt) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.createdAt = createdAt;
    }

    public String getFilePath() { return filePath; }
    public String getFileName() { return fileName; }
    public long getCreatedAt() { return createdAt; }
}