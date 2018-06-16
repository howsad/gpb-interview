package ru.gpb.interview.generator;

import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import org.junit.Test;
import ru.gpb.interview.SaleDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

/**
 * This is not the best kind of test since its input is not predetermined. If there were a bug in code it would fail
 * "sometimes". We could have fixed this by using a seed (but which one exactly?) or mocking SplittableRandom
 */

public class SaleDetailsStreamGeneratorTest {

    private static final int PRICE_FROM = 100;
    private static final int PRICE_TO = 1000;
    private static final LocalDateTime REFERENCE_DATE = LocalDate.of(2018, 6, 16).atStartOfDay();
    private static final LocalDateTime LOWER_DATE = LocalDate.of(2017, 1, 1).atStartOfDay();
    private static final LocalDateTime UPPER_DATE = LocalDate.of(2018, 1, 1).atStartOfDay();
    private static final ArrayList<String> SELLING_POINTS_NAMES = Lists.newArrayList(
            "Перекрёсток", "Пятёрочка", "Магазин у дома"
    );

    @Test
    public void generateOutputStream() {
        List<SaleDetails> saleDetails = new SaleDetailsStreamGenerator(PRICE_FROM, PRICE_TO, new SplittableRandom())
                .generateOutputStream(REFERENCE_DATE, SELLING_POINTS_NAMES, 100)
                .collect(Collectors.toList());
        assertTrue(saleDetails.stream()
                .map(SaleDetails::getDateTime)
                .allMatch(dt -> Range.closed(LOWER_DATE, UPPER_DATE).contains(dt)));
        assertTrue(saleDetails.stream()
                .map(SaleDetails::getSellingPointName)
                .allMatch(SELLING_POINTS_NAMES::contains));
        assertTrue(saleDetails.stream()
                .map(SaleDetails::getPrice)
                .allMatch(p -> Range.closed(PRICE_FROM, PRICE_TO).contains(p)));
    }

}