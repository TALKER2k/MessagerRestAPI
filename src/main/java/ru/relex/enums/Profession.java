package ru.relex.enums;

public enum Profession {
    DEVELOPER(4), DOCTOR(12), TEACHER(13.5), CONSULTANT(7);


    public Double currency;
    Profession(double i) {
        this.currency=i;
    }
}
