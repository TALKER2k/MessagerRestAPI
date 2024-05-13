package ru.relex.enums;

public enum Hobby {
    FOOTBALL(6), HOCKEY(7), DANCE(3.5), COMPUTER_GAMES(15), CHESS(-2),
    COOKING(1), PHOTOS(1.5), DEVELOPING(2.3), DESIGN(2.1);

    public final Double currency;

    Hobby(double v) {
        this.currency=v;
    }
}
