package com.ayaanshaikh.dbctl.model;

import java.time.Instant;

/**
 * The outcome of a backup or restore run. Engines should catch their own failures (a missing
 * client binary, a non-zero exit code, an IO error) and return a failed result rather than
 * throwing, so callers in {@code cli/} don't need a try/catch for expected failure modes.
 */
public record BackupResult(
        boolean success,
        String message,
        String artifactPath,
        Instant timestamp
) {

    public static BackupResult success(String message, String artifactPath) {
        return new BackupResult(true, message, artifactPath, Instant.now());
    }

    public static BackupResult failure(String message) {
        return new BackupResult(false, message, null, Instant.now());
    }
}
