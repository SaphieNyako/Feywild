package com.saphienyako.feywild.entity;

import com.saphienyako.feywild.Feywild;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITIES, Feywild.MOD_ID);

    public static final RegistryObject<EntityType<SpringPixieEntity>> SPRING_PIXIE =
            ENTITY_TYPES.register("spring_pixie", () -> EntityType.Builder.of(SpringPixieEntity::new, EntityClassification.CREATURE)
                    .sized(0.7f, 1.7f).build("spring_pixie"));

    public static final RegistryObject<EntityType<SummerPixieEntity>> SUMMER_PIXIE =
            ENTITY_TYPES.register("summer_pixie", () -> EntityType.Builder.of(SummerPixieEntity::new, EntityClassification.CREATURE)
                    .sized(0.7f, 1.7f).build("summer_pixie"));

    public static final RegistryObject<EntityType<WinterPixieEntity>> WINTER_PIXIE =
            ENTITY_TYPES.register("winter_pixie", () -> EntityType.Builder.of(WinterPixieEntity::new, EntityClassification.CREATURE)
                    .sized(0.7f, 1.7f).build("winter_pixie"));

    public static final RegistryObject<EntityType<AutumnPixieEntity>> AUTUMN_PIXIE =
            ENTITY_TYPES.register("autumn_pixie", () -> EntityType.Builder.of(AutumnPixieEntity::new, EntityClassification.CREATURE)
                    .sized(0.7f, 1.7f).build("autumn_pixie"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

}
