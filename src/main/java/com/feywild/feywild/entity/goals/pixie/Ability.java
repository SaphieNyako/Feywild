package com.feywild.feywild.entity.goals.pixie;

import java.util.Locale;

public enum Ability {

    FLOWER_WALK("flower_walk"),
    FIRE_WALK("fire_walk"),
    FROST_WALK("frost_walk"),
    WIND_WALK("wind_walk"),
    ANIMAL_BREEDING("animal_breeding");

    public final String id;

    Ability(String id) {
        this.id = id;
    }

    public static Ability byId(String id) {
        return switch (id.toLowerCase(Locale.ROOT).trim()) {
            case "flower_walk" -> FLOWER_WALK;
            case "fire_walk" -> FIRE_WALK;
            case "frost_walk" -> FROST_WALK;
            case "wind_walk" -> WIND_WALK;
            case "animal_breeding" -> ANIMAL_BREEDING;
            default -> throw new IllegalArgumentException("Invalid pixie ability: " + id);
        };
    }
}
