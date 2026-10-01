package com.ayaanshaikh.dbctl.model;

/**
 * Connection details for a target database. Server-based engines (MySQL, Postgres, MariaDB,
 * Oracle, MongoDB) use host/port/username/password/database; SQLite only uses filePath since
 * it has no server to connect to.
 */
public record ConnectionConfig(
        DatabaseType type,
        String host,
        Integer port,
        String username,
        String password,
        String database,
        String filePath
) {
}
