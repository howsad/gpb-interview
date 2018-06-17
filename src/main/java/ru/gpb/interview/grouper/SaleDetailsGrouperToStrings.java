package ru.gpb.interview.grouper;

import ru.gpb.interview.SaleDetails;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class SaleDetailsGrouperToStrings {

    List<String> groupByDateOrderByDate(Stream<SaleDetails> saleDetailsStream) {
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

    List<String> groupBySellingPointNameOrderByPriceDesc(Stream<SaleDetails> saleDetailsStream) {
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
