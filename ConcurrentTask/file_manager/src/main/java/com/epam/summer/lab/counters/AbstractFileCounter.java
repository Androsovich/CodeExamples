package com.epam.summer.lab.counters;

import java.nio.file.Path;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Abstract super class for the various FileCounter* subclasses.
 */
public abstract class AbstractFileCounter extends RecursiveTask<Long> {
    protected final Path path;
    protected final AtomicLong documentCount;
    protected final AtomicLong folderCount;

    protected AtomicLong totalSize;

    public AbstractFileCounter(Path path) {
        this.path = path;
        this.documentCount = new AtomicLong(0);
        this.folderCount = new AtomicLong(0);
        this.totalSize = new AtomicLong(0);
    }

    public AbstractFileCounter(Path path, AtomicLong documentCount, AtomicLong folderCount) {
        this.path = path;
        this.documentCount = documentCount;
        this.folderCount = folderCount;
    }

    public long getDocumentCount() {
        return documentCount.get();
    }

    public long getFolderCount() {
        return folderCount.get();
    }
}