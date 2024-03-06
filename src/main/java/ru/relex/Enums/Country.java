package ru.relex.Enums;

public enum Country {
    RUSSIA(1), KAZAKHSTAN(4.6), UKRAIN(1.5), USA(9);

    public final Double currency;

    Country(double i) {
        this.currency=i;
    }
}
