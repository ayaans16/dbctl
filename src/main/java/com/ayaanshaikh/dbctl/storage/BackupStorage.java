package com.ayaanshaikh.dbctl.storage;

import java.nio.file.Path;

/**
 * Resolves a backup artifact name to a concrete location to write/read it, and makes sure that
 * location is ready to use (e.g. parent directories exist, for local storage).
 */
public interface BackupStorage {

    Path resolve(String name);
}
