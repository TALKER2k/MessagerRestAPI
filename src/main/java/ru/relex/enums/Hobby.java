package ru.relex.enums;

public enum Hobby {
    FOOTBALL(6), HOCKEY(7), DANCE(3.5), COMPUTER_GAMES(15);

    public final Double currency;

    Hobby(double v) {
        this.currency=v;
    }
}
