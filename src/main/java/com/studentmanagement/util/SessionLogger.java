package com.studentmanagement.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Simple session logger that writes actions to a per-session file.
 */
public class SessionLogger implements AutoCloseable {
    private static final String DATA_DIR = "data";
    private static final String SESSIONS_DIR = DATA_DIR + "/sessions";
    private static final DateTimeFormatter TS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final String sessionId;
    private final String username;
    private final BufferedWriter writer;

    public SessionLogger(String username) throws IOException {
        ensureSessionsDir();
        this.username = username;
        this.sessionId = UUID.randomUUID().toString();
        String filename = String.format("session-%s-%s.log", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")), sessionId);
        File file = new File(SESSIONS_DIR, filename);
        this.writer = new BufferedWriter(new FileWriter(file, true));
        writeLine("SESSION_START", "username=" + username);
    }

    private static void ensureSessionsDir() throws IOException {
        Path dir = Paths.get(SESSIONS_DIR);
        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
        }
    }

    private void writeLine(String action, String details) throws IOException {
        String line = String.format("%s | %s | %s | %s", LocalDateTime.now().format(TS), sessionId, action, details == null ? "" : details);
        writer.write(line);
        writer.newLine();
        writer.flush();
    }

    public void logAction(String action, String details) {
        try {
            writeLine(action, details);
        } catch (IOException e) {
            System.out.println("✗ Warning: Failed to write session log: " + e.getMessage());
        }
    }

    public String getSessionId() { return sessionId; }

    @Override
    public void close() {
        try {
            writeLine("SESSION_END", "username=" + username);
            writer.close();
        } catch (IOException e) {
            // swallow
        }
    }
}
