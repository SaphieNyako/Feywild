package com.feywild.feywild.quest;

import com.feywild.feywild.entity.ModEntities;
import com.feywild.feywild.entity.base.Fey;
import net.minecraft.world.entity.EntityType;

import javax.annotation.Nullable;
import java.util.Locale;

public enum Alignment {

    SPRING("spring", ModEntities.springPixie),
    SUMMER("summer", ModEntities.summerPixie),
    AUTUMN("autumn", ModEntities.autumnPixie),
    WINTER("winter", ModEntities.winterPixie);

    public final String id;
    public final EntityType<? extends Fey> fey;

    Alignment(String id, EntityType<? extends Fey> fey) {
        this.id = id;
        this.fey = fey;
    }

    public static Alignment byId(String id) {
        return switch (id.toLowerCase(Locale.ROOT).trim()) {
            case "spring" -> SPRING;
            case "summer" -> SUMMER;
            case "autumn" -> AUTUMN;
            case "winter" -> WINTER;
            default -> throw new IllegalArgumentException("Invalid fey alignment: " + id);
        };
    }

    public static String optionId(@Nullable Alignment alignment) {
        return alignment == null ? "unaligned" : alignment.id;
    }

    @Nullable
    public static Alignment byOptionId(String id) {
        try {
            return byId(id);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
