package ru.gpb.interview.grouper;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import ru.gpb.interview.SaleDetails;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

class SaleDetailsGrouperToFile {

    private final SaleDetailsGrouperToStrings grouperToStrings;

    SaleDetailsGrouperToFile(SaleDetailsGrouperToStrings grouperToStrings) {
        this.grouperToStrings = grouperToStrings;
    }

    void groupInputIntoFile(GrouperSettings grouperSettings) {
        try {
            Path inputFilePath = grouperSettings.getInputFilePath();
            Path outputFilePath = grouperSettings.getOutputFilePath();
            ArrayList<String> output = Lists.newArrayList(Iterables.concat(
                    grouperToStrings.groupByDateOrderByDate(getSaleDetailsStream(inputFilePath)),
                    Collections.singletonList("*====================================*"),
                    grouperToStrings.groupBySellingPointNameOrderByPriceDesc(getSaleDetailsStream(inputFilePath))
            ));
            Files.write(outputFilePath, output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Stream<SaleDetails> getSaleDetailsStream(Path inputFile) throws IOException {
        return Files.lines(inputFile)
                .map(SaleDetails::fromString);
    }

}
