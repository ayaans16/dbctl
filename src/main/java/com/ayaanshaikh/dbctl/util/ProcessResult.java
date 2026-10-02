package com.ayaanshaikh.dbctl.util;

public record ProcessResult(int exitCode, String stdout, String stderr) {

    public boolean succeeded() {
        return exitCode == 0;
    }
}
