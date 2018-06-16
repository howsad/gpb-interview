package ru.gpb.interview;

import java.time.LocalDateTime;

public class SaleDetails {

    /** DateTime of the sale */
    private LocalDateTime dateTime;
    /** Name of the selling point where the sale was made */
    private String sellingPointName;
    /** Price measured in minor units of currency, i.e. 1$ = 100 cents. So in that case price would be 100 */
    private Integer price;

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

    public Integer getPrice() {
        return price;
    }

    public SaleDetails withPrice(Integer price) {
        this.price = price;
        return this;
    }

    public String toOutputString() {
        return String.format(
                "%s\t%s\t%d.%02d",
                dateTime,
                sellingPointName,
                price / 100,
                price % 100
        );
    }

    /* We assume that the string s complies with format in ru.gpb.interview.SaleDetails.toOutputString */
    public static SaleDetails fromString(String s) {
        String[] split = s.trim().split("\t");
        String price = split[2].replace(".", "");
        return new SaleDetails()
                .withDateTime(LocalDateTime.parse(split[0]))
                .withSellingPointName(split[1])
                .withPrice(Integer.valueOf(price));
    }
}
