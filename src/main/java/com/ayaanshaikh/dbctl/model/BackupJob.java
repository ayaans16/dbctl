package com.ayaanshaikh.dbctl.model;

/**
 * A request to back up {@code connection} into a file/directory at {@code outputPath}.
 */
public record BackupJob(
        ConnectionConfig connection,
        String outputPath
) {
}
