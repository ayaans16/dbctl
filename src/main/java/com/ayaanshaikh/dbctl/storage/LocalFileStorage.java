package com.ayaanshaikh.dbctl.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Writes/reads backup artifacts on the local filesystem, under a configurable base directory
 * (default: ./backups). An absolute {@code name} is used as-is; a relative one is resolved
 * against the base directory.
 */
@Component
public class LocalFileStorage implements BackupStorage {

    private final Path baseDir;

    public LocalFileStorage(@Value("${dbctl.storage.base-dir:backups}") String baseDir) {
        this.baseDir = Paths.get(baseDir).toAbsolutePath().normalize();
    }

    @Override
    public Path resolve(String name) {
        Path requested = Paths.get(name);
        Path resolved = requested.isAbsolute() ? requested : baseDir.resolve(requested);
        try {
            Files.createDirectories(resolved.getParent());
        } catch (IOException e) {
            throw new UncheckedIOException("Could not create directory for " + resolved, e);
        }
        return resolved;
    }
}
