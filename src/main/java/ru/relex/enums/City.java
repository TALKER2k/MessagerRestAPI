package ru.relex.enums;

public enum City {
    MOSCOW(3), VORONEZH(4.5), PITER(2.2), KIEV(12), SAMARA(-3), KURSK(5),
    BELGOROD(6), EKATERINBURG(-2.5);

    public Double currency;

    City(double v) {
        this.currency = v;
    }
}
