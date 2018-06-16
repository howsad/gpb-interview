package ru.gpb.interview.grouper;

import com.google.common.base.Stopwatch;

/**
 * This is main class for pt2 of the interview task, namely grouping the output
 *
 * Command line args sample: inputPath=../Hello.txt outputPath=../Out.txt
 */

public class SaleDetailsGrouperMain {

    public static void main(String[] args) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        new SaleDetailsGrouper().groupInputIntoFile(GrouperSettings.parseCommandLineArgs(args));
        stopwatch.stop();
        System.out.println(String.format("Elapsed time: %s", stopwatch.elapsed()));
    }

}
