package ru.gpb.interview;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SaleDetails {

    /** Number of digits after decimal point in price */
    public static final int PRICE_SCALE = 2;

    /** DateTime of the sale */
    private LocalDateTime dateTime;
    /** Name of the selling point where the sale was made */
    private String sellingPointName;
    /** Price of the sold item */
    private BigDecimal price;

    public SaleDetails() {
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public SaleDetails withDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public String getSellingPointName() {
        return sellingPointName;
    }

    public SaleDetails withSellingPointName(String sellingPointName) {
        this.sellingPointName = sellingPointName;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public SaleDetails withPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String toOutputString() {
        return String.format(
                "%s\t%s\t%s",
                dateTime,
                sellingPointName,
                price
        );
    }

    /* We assume that the string s complies with format in ru.gpb.interview.SaleDetails.toOutputString */
    public static SaleDetails fromString(String s) {
        String[] split = s.trim().split("\t");
        return new SaleDetails()
                .withDateTime(LocalDateTime.parse(split[0]))
                .withSellingPointName(split[1])
                .withPrice(new BigDecimal(split[2]));
    }
}
