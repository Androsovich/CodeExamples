package com.epam.summer.lab.counters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This task uses the Java Files.walk() method and a sequential stream
 * to count the files and compute the size in bytes of all files in
 * folders reachable from the given root file.
 */
public class FileCounterWalkStream extends AbstractFileCounter {

    public FileCounterWalkStream(Path path) {
        super(path);
    }

    @Override
    protected Long compute() {
        try {
            return Files.walk(path)
                    .mapToLong(path -> {
                        if (Files.isDirectory(path)) {
                            folderCount.incrementAndGet();
                            return 0L;
                        } else {
                            documentCount.incrementAndGet();
                            return path.toFile().length();
                        }
                    })
                    .sum();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Return 0 if an exception occurs (shouldn't happen).
        return 0L;
    }
}