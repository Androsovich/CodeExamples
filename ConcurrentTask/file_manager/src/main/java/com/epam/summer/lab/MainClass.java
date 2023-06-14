package com.epam.summer.lab;

import com.epam.summer.lab.counters.*;
import com.epam.summer.lab.utils.RunTimer;

import java.nio.file.Paths;
import java.util.concurrent.ForkJoinPool;

public class MainClass {
    public static void main(String[] args) {
        final String filename = args[0];

        System.out.println("Starting the file counter program");

        // Run a test that uses the Java Files.walkFileTree() method,
        // Java 7 features, and the Visitor pattern to count the
        // files.
        runFileCounterWalkFileTree(filename);

        // Run a test that uses the Java Files.walk() method and
        // a sequential stream to count the files.
        runFileCounterWalkStream(filename);

        // Run a test that uses the Java fork-join framework in
        // conjunction with Java sequential streams features.
        runFileCounterTaskStream(filename);

        //Run a test that uses the Java fork-join framework
        //with a threshold 100% of the total number of files
        runFileCounterWithThreshold(filename, 100);

        //Run a test that uses the Java fork-join framework
        //with a threshold 50% of the total number of files
        runFileCounterWithThreshold(filename, 50);

        //Run a test that uses the Java fork-join framework
        //with a threshold 25% of the total number of files
        runFileCounterWithThreshold(filename, 25);

        //Run a test that uses the Java fork-join framework
        //with a threshold 10% of the total number of files
        runFileCounterWithThreshold(filename, 10);

        //Run a test that uses the Java fork-join framework
        //with a threshold 1% of the total number of files
        runFileCounterWithThreshold(filename, 1);


        // Get and print the timing results.
        System.out.println(RunTimer.getTimingResults());

        System.out.println("Ending the file counter program");
    }

    /**
     * Run a test that uses the Java Files.walkFileTree() method, Java
     * 7 features, and the Visitor pattern to count the files.
     */
    private static void runFileCounterWalkFileTree(String fileName) {
        runTest(ForkJoinPool.commonPool(),
                new FileCounterWalkFileTree
                        (Paths.get(fileName)),
                "FileCounterWalkFileTree");
    }

    /**
     * Run a test that uses the Java Files.walk() method and a
     * sequential stream to count the files.
     */
    private static void runFileCounterWalkStream(String fileName) {
        runTest(ForkJoinPool.commonPool(),
                new FileCounterWalkStream
                        (Paths.get(fileName)),
                "FileCounterWalkStream");
    }

    /**
     * Run a test that uses the Java fork-join framework in
     * conjunction with Java sequential streams features.
     */
    private static void runFileCounterTaskStream(String fileName) {
        runTest(ForkJoinPool.commonPool(),
                new FileCounterTaskStream
                        (Paths.get(fileName)),
                "FileCounterTaskStream");
    }

    /**
     * Run a test that uses the Java fork-join framework
     * with a threshold percent of the total number of files
     */
    private static void runFileCounterWithThreshold(String fileName, int percentThreshold) {
        runTest(ForkJoinPool.commonPool(),
                new FileCounterWithThreshold
                        (Paths.get(fileName), percentThreshold),
                "FileCounterWithThreshold with percent Threshold : " + percentThreshold + " % ");
    }

    /**
     * Run all the tests and collect/print the results.
     *
     * @param fJPool   The fork-join pool to use for the test
     * @param testTask The file counter task to run
     * @param testName The name of the test
     */
    private static void runTest(ForkJoinPool fJPool,
                                AbstractFileCounter testTask,
                                String testName) {
        // Run the GC first to avoid perturbing the tests.
        System.gc();

        // Run the task on the root of a large directory hierarchy.
        long size = RunTimer.timeRun(() -> fJPool.invoke(testTask),
                testName);

        // Print the results.
        System.out.println(testName
                + ": "
                + (testTask.getDocumentCount()
                + testTask.getFolderCount())
                + " files ("
                + testTask.getDocumentCount()
                + " documents and "
                + testTask.getFolderCount()
                + " folders) contained "
                + size // / 1_000_000)
                + " bytes");
    }
}


