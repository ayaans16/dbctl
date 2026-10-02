package com.ayaanshaikh.dbctl.util;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Runs an external command (pg_dump, mysqldump, mongodump, ...) and captures its exit code,
 * stdout and stderr. Every engine shells out through this instead of calling ProcessBuilder
 * directly, so timeout handling and stream draining only need to be gotten right once.
 */
@Component
public class ProcessExecutor {

    public ProcessResult execute(List<String> command, Map<String, String> extraEnv, Duration timeout)
            throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.environment().putAll(extraEnv);
        Process process = builder.start();

        StringBuilder stdout = new StringBuilder();
        StringBuilder stderr = new StringBuilder();
        Thread stdoutReader = startReader(process.getInputStream(), stdout);
        Thread stderrReader = startReader(process.getErrorStream(), stderr);

        boolean finished = process.waitFor(timeout.toMillis(), TimeUnit.MILLISECONDS);
        if (!finished) {
            process.destroyForcibly();
            stdoutReader.join();
            stderrReader.join();
            throw new IOException("Command timed out after " + timeout + ": " + String.join(" ", command));
        }

        stdoutReader.join();
        stderrReader.join();
        return new ProcessResult(process.exitValue(), stdout.toString(), stderr.toString());
    }

    private Thread startReader(InputStream stream, StringBuilder target) {
        Thread thread = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    target.append(line).append(System.lineSeparator());
                }
            } catch (IOException ignored) {
                // stream closes when the process exits or is destroyed — nothing left to read
            }
        });
        thread.start();
        return thread;
    }
}
