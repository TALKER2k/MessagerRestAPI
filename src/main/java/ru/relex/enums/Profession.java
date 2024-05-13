package ru.relex.enums;

public enum Profession {
    DEVELOPER(4), DOCTOR(12), TEACHER(13.5), CONSULTANT(7), ACTOR(15.2),
    POET(1.7), SCULPTOR(2.1), PAINTER(6.1), PHOTOS(6.5);


    public Double currency;
    Profession(double i) {
        this.currency=i;
    }
}
