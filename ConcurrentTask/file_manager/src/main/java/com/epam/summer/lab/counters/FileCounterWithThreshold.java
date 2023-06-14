package com.epam.summer.lab.counters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This task uses the Java fork-join platform with a percentage threshold
 * of the total number of files to calculate the size in bytes of this file,
 * as well as all files in folders accessible from this file.
 * Also in this task, an inner private class is used to work according
 * to the classical algorithm fork-join platform
 */
public class FileCounterWithThreshold extends AbstractFileCounter {
    private final int THRESHOLD_PERCENTAGE;
    private final List<Path> paths;

    public FileCounterWithThreshold(Path path, int thresholdPercentage) {
        super(path);
        this.THRESHOLD_PERCENTAGE = thresholdPercentage;
        this.paths = getPaths(path);
    }

    private int calculateThreshold(int percent, double total) {
        final int ONE_HUNDRED_PERCENT = 100;
        return (int) Math.ceil(percent * total / ONE_HUNDRED_PERCENT);
    }

    //get paths all files and folders by path
    private List<Path> getPaths(Path path) {
        return getStreamPaths(path).collect(Collectors.toList());
    }

    /**
     * it is used to handle exceptions
     * and reduce the code in the method getPaths
     */
    private Stream<Path> getStreamPaths(Path path) {
        Stream<Path> pathStream = Stream.empty();

        try {
            pathStream = Files.walk(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathStream;
    }

    @Override
    protected Long compute() {
        countFiles();

        return totalSize.get();
    }

    /**
     * launching an auxiliary class for execution,
     * passing the full list of paths and the threshold of files for direct execution
     */
    public void countFiles() {
        final int THRESHOLD = calculateThreshold(THRESHOLD_PERCENTAGE, paths.size());
        FileCounter fileCounter = new FileCounter(THRESHOLD, paths);

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(fileCounter);
    }

    /**
     * auxiliary class for counting files, folders and the total file size.
     * It is he who does all the work with the fork Join framework.
     * I'm sorry I didn't want to make a separate class
     * because it's not used anywhere else.
     */
    private class FileCounter extends RecursiveAction {
        private final int THRESHOLD;
        private final List<Path> pathList;

        public FileCounter(int THRESHOLD, List<Path> pathList) {
            this.THRESHOLD = THRESHOLD;
            this.pathList = pathList;
        }

        @Override
        protected void compute() {
            walkDirectory(THRESHOLD, pathList);
        }

        /**
         * application of the classic fork join platform algorithm
         */
        private void walkDirectory(int THRESHOLD, List<Path> pathList) {
            if (pathList.size() <= THRESHOLD) {
                computeDirectly(pathList);
            } else {
                int middle = pathList.size() / 2;
                FileCounter start = new FileCounter(THRESHOLD, pathList.subList(0, middle));
                FileCounter end = new FileCounter(THRESHOLD, pathList.subList(middle, pathList.size()));

                invokeAll(start, end);
            }
        }

        /**
         * directly execute task
         */
        private void computeDirectly(List<Path> pathList) {
            totalSize.addAndGet(getTotalFilesSize(pathList));
        }

        /**
         * the mode of direct calculation of the total size
         *
         * @param pathList sublist of the list pathList according to the percentage threshold for direct execution
         */
        private long getTotalFilesSize(List<Path> pathList) {
            return pathList
                    .stream()
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
        }
    }
}