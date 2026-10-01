/**
 * Picocli command classes — the CLI surface (e.g. {@code dbctl backup}, {@code dbctl restore}).
 * Commands should parse args/flags, resolve a {@link com.ayaanshaikh.dbctl.model.ConnectionConfig},
 * look up the matching engine from {@code core}, and delegate — no backup/restore logic here.
 */
package com.ayaanshaikh.dbctl.cli;
