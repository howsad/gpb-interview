package ru.gpb.interview.grouper;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import ru.gpb.interview.SaleDetails;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class SaleDetailsGrouper {

    void groupInputIntoFile(GrouperSettings grouperSettings) {
        try {
            Path inputFilePath = grouperSettings.getInputFilePath();
            Path outputFilePath = grouperSettings.getOutputFilePath();
            ArrayList<String> output = Lists.newArrayList(Iterables.concat(
                    groupByDateOrderByDate(getSaleDetailsStream(inputFilePath)),
                    Collections.singletonList("*====================================*"),
                    groupBySellingPointNameOrderByPriceDesc(getSaleDetailsStream(inputFilePath))
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

    private List<String> groupByDateOrderByDate(Stream<SaleDetails> saleDetailsStream) {
        return saleDetailsStream
                .collect(
                        Collectors.groupingBy(
                                sd -> sd.getDateTime().toLocalDate(),
                                TreeMap::new,
                                Collectors.mapping(
                                        SaleDetails::getPrice,
                                        Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                                )
                        )
                ).entrySet().stream()
                .map(e -> String.format("%s\t%s", e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    private List<String> groupBySellingPointNameOrderByPriceDesc(Stream<SaleDetails> saleDetailsStream) {
        return saleDetailsStream
                .collect(
                        Collectors.groupingBy(
                                SaleDetails::getSellingPointName,
                                Collectors.mapping(
                                        SaleDetails::getPrice,
                                        Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                                )
                        )
                ).entrySet().stream()
                .sorted(Comparator.<Map.Entry<String, BigDecimal>, BigDecimal>comparing(Map.Entry::getValue).reversed())
                // I feel like it is incidental that both stats share the same format. No need for code reuse
                .map(e -> String.format("%s\t%s", e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

}
