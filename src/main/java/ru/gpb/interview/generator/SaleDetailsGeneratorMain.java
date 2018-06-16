package ru.gpb.interview.generator;

import com.google.common.base.Stopwatch;
import ru.gpb.interview.SaleDetails;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.SplittableRandom;

/**
 * This is main class for pt1 of the interview task, namely generating the output file with random sale details
 *
 * Command line args sample: namesPath=../Names.txt outputPath=../Hello.txt linesCount=10000000
 */

public class SaleDetailsGeneratorMain {

    private static final BigDecimal PRICE_FROM = BigDecimal.valueOf(10_000_00, SaleDetails.PRICE_SCALE);
    private static final BigDecimal PRICE_TO = BigDecimal.valueOf(100_000_00, SaleDetails.PRICE_SCALE);

    public static void main(String[] args) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        SaleDetailsStreamGenerator streamGenerator = new SaleDetailsStreamGenerator(
                PRICE_FROM,
                PRICE_TO,
                new SplittableRandom()
        );
        new SaleDetailsFileGenerator(streamGenerator).generateToFile(
                LocalDateTime.now(),
                GeneratorSettings.parseCommandLineArgs(args)
        );
        stopwatch.stop();
        System.out.println(String.format("Elapsed time: %s", stopwatch.elapsed()));
    }

}
