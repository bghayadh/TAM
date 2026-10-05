package com.aliat.alm.gdb;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

import com.aliat.alm.models.ExportEntry;

@Component
public class GdbExportRegistry {

    private final Map<String, ExportEntry> exports = new ConcurrentHashMap<>();

    public void registerExport(String token, String filePath, String fileName) {
        exports.put(token, new ExportEntry(filePath, fileName, System.currentTimeMillis()));
    }

    public ExportEntry lookupExport(String token) {
        return exports.get(token);
    }

    public void removeExport(String token) {
        exports.remove(token);
    }

    public Map<String, ExportEntry> getAllEntries() {
        return exports;
    }
}