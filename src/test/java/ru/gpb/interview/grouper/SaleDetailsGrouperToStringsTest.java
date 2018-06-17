package ru.gpb.interview.grouper;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;
import ru.gpb.interview.SaleDetails;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

public class SaleDetailsGrouperToStringsTest {
    @Test
    public void groupByDateOrderByDate() throws Exception {
        LocalDate day1 = LocalDate.of(2018, 6, 16);
        LocalDate day2 = LocalDate.of(2018, 6, 17);
        List<String> groupedOutput = new SaleDetailsGrouperToStrings().groupByDateOrderByDate(
                Stream.of(new SaleDetails()
                                .withDateTime(LocalDateTime.of(day2, LocalTime.of(18, 0)))
                                .withPrice(BigDecimal.valueOf(123, SaleDetails.PRICE_SCALE))
                                .withSellingPointName("Перекрёсток"),
                        new SaleDetails()
                                .withDateTime(LocalDateTime.of(day2, LocalTime.of(15, 0)))
                                .withPrice(BigDecimal.valueOf(456, SaleDetails.PRICE_SCALE))
                                .withSellingPointName("Пятёрочка"),
                        new SaleDetails()
                                .withDateTime(LocalDateTime.of(day1, LocalTime.of(12, 0)))
                                .withPrice(BigDecimal.valueOf(789, SaleDetails.PRICE_SCALE))
                                .withSellingPointName("Азбука вкуса")
                )
        );
        Assert.assertEquals(
                Lists.newArrayList(String.format("%s\t%s", day1, "7.89"), String.format("%s\t%s", day2, "5.79")),
                groupedOutput
        );
    }

    @Test
    public void groupBySellingPointNameOrderByPriceDesc() throws Exception {
        LocalDateTime someDate = LocalDateTime.of(2018, 6, 16, 12, 0);
        String topSeller = "Перекрёсток";
        String anotherSeller = "Пятёрочка";
        List<String> groupedOutput = new SaleDetailsGrouperToStrings().groupBySellingPointNameOrderByPriceDesc(
                Stream.of(new SaleDetails()
                                .withDateTime(someDate)
                                .withPrice(BigDecimal.valueOf(123, SaleDetails.PRICE_SCALE))
                                .withSellingPointName(topSeller),
                        new SaleDetails()
                                .withDateTime(someDate)
                                .withPrice(BigDecimal.valueOf(456, SaleDetails.PRICE_SCALE))
                                .withSellingPointName(anotherSeller),
                        new SaleDetails()
                                .withDateTime(someDate)
                                .withPrice(BigDecimal.valueOf(789, SaleDetails.PRICE_SCALE))
                                .withSellingPointName(topSeller)
                )
        );
        Assert.assertEquals(
                Lists.newArrayList(
                        String.format("%s\t%s", topSeller, "9.12"),
                        String.format("%s\t%s", anotherSeller, "4.56")
                ),
                groupedOutput
        );
    }

}