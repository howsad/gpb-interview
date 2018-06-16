package ru.gpb.interview.generator;

import com.google.common.collect.Streams;
import ru.gpb.interview.SaleDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.SplittableRandom;
import java.util.stream.Stream;

class SaleDetailsStreamGenerator {

    private final int priceFrom;
    private final int priceTo;
    private final SplittableRandom random;

    SaleDetailsStreamGenerator(int priceFrom, int priceTo, SplittableRandom random) {
        this.priceFrom = priceFrom;
        this.priceTo = priceTo;
        this.random = random;
    }

    Stream<SaleDetails> generateOutputStream(
            LocalDateTime referenceDate,
            List<String> sellingPointsNames,
            long lineCount) {
        Stream<SaleDetails> withDateTimeStream = Streams.zip(
                Stream.generate(SaleDetails::new),
                generateDateTimeStream(referenceDate),
                SaleDetails::withDateTime
        );
        Stream<SaleDetails> withSellingPointNameStream = Streams.zip(
                withDateTimeStream,
                generateSellingPointNameStream(sellingPointsNames),
                SaleDetails::withSellingPointName
        );
        return Streams.zip(
                withSellingPointNameStream,
                generatePriceStream(priceFrom, priceTo),
                SaleDetails::withPrice
        )
                .limit(lineCount);
    }

    private Stream<LocalDateTime> generateDateTimeStream(LocalDateTime referenceDate) {
        LocalDateTime endDate = LocalDate.of(referenceDate.getYear(), 1, 1).atStartOfDay();
        LocalDateTime startDate = endDate.minusYears(1);
        int minutesInYear = (int) startDate.until(endDate, ChronoUnit.MINUTES);
        return random.ints(0, minutesInYear + 1)
                .mapToObj(min -> startDate.plus(min, ChronoUnit.MINUTES));
    }

    private Stream<String> generateSellingPointNameStream(List<String> nameList) {
        return random.ints(0, nameList.size())
                .mapToObj(nameList::get);
    }

    private Stream<Integer> generatePriceStream(int from, int to) {
        return random.ints(from, to + 1)
                .boxed();
    }

}
