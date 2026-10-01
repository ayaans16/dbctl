package com.ayaanshaikh.dbctl.core;

import com.ayaanshaikh.dbctl.model.BackupJob;
import com.ayaanshaikh.dbctl.model.BackupResult;
import com.ayaanshaikh.dbctl.model.DatabaseType;

/**
 * Implemented once per database engine (MySQL, Postgres, SQLite, ...). Implementations should
 * catch their own failures and return {@link BackupResult#failure} rather than throwing.
 */
public interface BackupEngine {

    /** Which {@link DatabaseType} this implementation handles. */
    DatabaseType supports();

    BackupResult backup(BackupJob job);
}
