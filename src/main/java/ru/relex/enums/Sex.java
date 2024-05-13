package ru.relex.enums;

public enum Sex {
    MALE(1), FEMALE(0);

    public final Double currency;

    Sex(double i) {
        this.currency=i;
    }
}
