package ru.relex.enums;

public enum Country {
    RUSSIA(1), KAZAKHSTAN(4.6), UKRAIN(1.5), USA(9), UK(12), TURKEY(5.2),
    LITVA(1.7), LATVIYA(1.5), GERMANY(2.7), FRANCE(3), ITALY(2.5);

    public final Double currency;

    Country(double i) {
        this.currency=i;
    }
}
