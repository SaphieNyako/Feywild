package com.saphienyako.feywild.entity;

import java.util.Locale;

public enum Alignment {

    SPRING("spring"),
    SUMMER("summer"),
    AUTUMN("autumn"),
    WINTER("winter");

    public final String id;
    Alignment(String id) {
        this.id = id;
    }

    public static Alignment byId(String id) {
        if (id == null) throw new IllegalArgumentException("Invalid fey alignment: null");
        switch (id.toLowerCase(Locale.ROOT).trim()) {
            case "spring":
                return SPRING;
            case "summer":
                return SUMMER;
            case "autumn":
                return AUTUMN;
            case "winter":
                return WINTER;
            default:
                throw new IllegalArgumentException("Invalid fey alignment: " + id);
        }
    }

    public static String optionId(Alignment alignment) {
        return alignment == null ? "unaligned" : alignment.id;
    }


    public static Alignment byOptionId(String id) {
        try {
            return byId(id);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
