package com.ayaanshaikh.dbctl.model;

/**
 * A request to restore {@code connection} from a previously created backup artifact at
 * {@code inputPath}.
 */
public record RestoreJob(
        ConnectionConfig connection,
        String inputPath
) {
}
