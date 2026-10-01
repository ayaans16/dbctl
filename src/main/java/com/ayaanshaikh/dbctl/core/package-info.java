/**
 * Engine-agnostic contracts: the {@code BackupEngine} / {@code RestoreEngine} interfaces that each
 * database under {@code engine/} implements, plus the registry/factory that maps a database type
 * (from {@link com.ayaanshaikh.dbctl.model.ConnectionConfig}) to the right implementation.
 */
package com.ayaanshaikh.dbctl.core;
