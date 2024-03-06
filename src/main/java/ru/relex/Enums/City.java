package ru.relex.Enums;

public enum City {
    MOSCOW(3), VORONEZH(4.5), PITER(2.2), KIEV(12);

    public Double currency;

    City(double v) {
        this.currency = v;
    }
}
