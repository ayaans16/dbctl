package com.ayaanshaikh.dbctl.core;

import com.ayaanshaikh.dbctl.model.DatabaseType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Looks up the {@link BackupEngine}/{@link RestoreEngine} for a given {@link DatabaseType}, so
 * {@code cli/} never hardcodes "if mysql, if postgres...". Spring collects every engine bean
 * automatically; adding a new database just means adding a new {@code @Component} that
 * implements these interfaces, with no changes needed here.
 */
@Component
public class EngineRegistry {

    private final Map<DatabaseType, BackupEngine> backupEngines;
    private final Map<DatabaseType, RestoreEngine> restoreEngines;

    public EngineRegistry(List<BackupEngine> backupEngines, List<RestoreEngine> restoreEngines) {
        this.backupEngines = backupEngines.stream()
                .collect(Collectors.toMap(BackupEngine::supports, Function.identity()));
        this.restoreEngines = restoreEngines.stream()
                .collect(Collectors.toMap(RestoreEngine::supports, Function.identity()));
    }

    public BackupEngine backupEngineFor(DatabaseType type) {
        BackupEngine engine = backupEngines.get(type);
        if (engine == null) {
            throw new IllegalArgumentException("No backup engine registered for " + type);
        }
        return engine;
    }

    public RestoreEngine restoreEngineFor(DatabaseType type) {
        RestoreEngine engine = restoreEngines.get(type);
        if (engine == null) {
            throw new IllegalArgumentException("No restore engine registered for " + type);
        }
        return engine;
    }
}
