package com.feywild.feywild.quest;

import com.feywild.feywild.entity.ModEntityTypes;
import com.feywild.feywild.entity.util.FeyEntity;
import net.minecraft.entity.EntityType;

import java.util.Locale;

public enum Alignment {
    
    SPRING("spring", ModEntityTypes.springPixie),
    SUMMER("summer", ModEntityTypes.summerPixie),
    AUTUMN("autumn", ModEntityTypes.autumnPixie),
    WINTER("winter", ModEntityTypes.winterPixie);
    
    public final String id;
    public final EntityType<? extends FeyEntity> fey;

    Alignment(String id, EntityType<? extends FeyEntity> fey) {
        this.id = id;
        this.fey = fey;
    }
    
    public static Alignment byId(String id) {
        switch (id.toLowerCase(Locale.ROOT).trim()) {
            case "spring": return SPRING;
            case "summer": return SUMMER;
            case "autumn": return AUTUMN;
            case "winter": return WINTER;
            default: throw new IllegalArgumentException("Invalid fey alignment: " + id);
        }
    }
}