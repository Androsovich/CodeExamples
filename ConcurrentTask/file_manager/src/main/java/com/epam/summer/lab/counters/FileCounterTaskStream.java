package com.epam.summer.lab.counters;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * This task uses the Java fork-join framework and the sequential
 * streams framework to compute the size in bytes of a given file, as
 * well as all the files in folders reachable from this file.
 */
public class FileCounterTaskStream extends AbstractFileCounter {

    public FileCounterTaskStream(Path path) {
        super(path);
    }

    public FileCounterTaskStream(Path path, AtomicLong documentCount, AtomicLong folderCount) {
        super(path, documentCount, folderCount);

    }

    @Override
    protected Long compute() {
        if (path.toFile().isFile()) {
            documentCount.incrementAndGet();

            // Return the length of the file.
            return path.toFile().length();
        } else {
            // Increment the count of folders.
            folderCount.incrementAndGet();

            // Create a list of tasks to fork to process the contents
            // of a folder.
            List<ForkJoinTask<Long>> forks = Stream
                    // Convert the list of files into a stream of files.
                    .of(Objects.requireNonNull(path.toFile().listFiles()))

                    // Map each file into a FileCounterTaskStream and fork it.
                    .map(temp -> new FileCounterTaskStream(temp.toPath(),
                            documentCount,
                            folderCount).fork())

                    // Trigger intermediate operation processing and
                    // collect the results into a list.
                    .collect(toList());

            return forks
                    // Convert the list to a stream.
                    .stream()

                    // Join all the tasks.
                    .mapToLong(ForkJoinTask::join)

                    // Sum the sizes of all the files.
                    .sum();
        }
    }
}