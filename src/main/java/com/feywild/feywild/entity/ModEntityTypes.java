package com.feywild.feywild.entity;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.util.Registration;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

import java.util.ResourceBundle;

public class ModEntityTypes {

    /* REGISTRATION FOR EACH ENTITY*/
    public static final RegistryObject<EntityType<SpringPixieEntity>> SPRING_PIXIE = Registration.ENTITIES.register("spring_pixie",
            () -> EntityType.Builder.create(SpringPixieEntity::new, EntityClassification.CREATURE)
    .size(1,1)
    .build(new ResourceLocation(FeywildMod.MOD_ID + "spring_pixie").toString()));

    public static void register() {}

}
