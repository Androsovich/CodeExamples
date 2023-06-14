package com.epam.summer.lab.counters;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;

/**
 * This task uses the Java Files.walkFileTree() method and Java 7 features
 * to count the files and compute the size in bytes of all files in
 * folders reachable from the given root file.
 */
public class FileCounterWalkFileTree extends AbstractFileCounter {

    public FileCounterWalkFileTree(Path path) {
        super(path);
    }

    @Override
    protected Long compute() {
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    folderCount.incrementAndGet();

                    return CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    documentCount.incrementAndGet();
                    totalSize.addAndGet(file.toFile().length());

                    return CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return totalSize.get();
    }
}