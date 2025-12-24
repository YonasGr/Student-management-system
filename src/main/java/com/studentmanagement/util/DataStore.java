package com.studentmanagement.util;

import com.studentmanagement.service.StudentManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Simple file-based persistence for StudentManager state.
 * Uses Java serialization to save/load the entire manager.
 */
public class DataStore {
    private static final String DATA_DIR = "data";
    private static final String STUDENTS_FILE = DATA_DIR + "/students.dat";

    /** Ensure data directory exists */
    private static void ensureDataDir() throws IOException {
        Path dir = Paths.get(DATA_DIR);
        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
        }
    }

    /** Save StudentManager to disk */
    public static void save(StudentManager manager) throws IOException {
        ensureDataDir();
        try (FileOutputStream fos = new FileOutputStream(STUDENTS_FILE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(manager);
            oos.flush();
        }
    }

    /** Load StudentManager from disk, or return new instance if not found */
    public static StudentManager load() {
        try {
            File file = new File(STUDENTS_FILE);
            if (!file.exists() || file.length() == 0) {
                return new StudentManager();
            }
            try (FileInputStream fis = new FileInputStream(file);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                Object obj = ois.readObject();
                if (obj instanceof StudentManager) {
                    return (StudentManager) obj;
                }
            }
        } catch (Exception e) {
            // If any error occurs, return a fresh manager to keep the app usable
            System.out.println("✗ Warning: Failed to load data. Starting with empty data. Details: " + e.getMessage());
        }
        return new StudentManager();
    }
}
