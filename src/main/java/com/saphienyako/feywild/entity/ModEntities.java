package com.saphienyako.feywild.entity;

import com.saphienyako.feywild.Feywild;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Feywild.MOD_ID);

    public static final Supplier<EntityType<SpringPixieEntity>> SPRING_PIXIE =
            ENTITY_TYPES.register("spring_pixie", () -> EntityType.Builder.of(SpringPixieEntity::new, MobCategory.CREATURE)
                    .sized(0.7f, 1.7f).build("spring_pixie"));

    public static final Supplier<EntityType<SummerPixieEntity>> SUMMER_PIXIE =
            ENTITY_TYPES.register("summer_pixie", () -> EntityType.Builder.of(SummerPixieEntity::new, MobCategory.CREATURE)
                    .sized(0.7f, 1.7f).build("summer_pixie"));

    public static final Supplier<EntityType<WinterPixieEntity>> WINTER_PIXIE =
            ENTITY_TYPES.register("winter_pixie", () -> EntityType.Builder.of(WinterPixieEntity::new, MobCategory.CREATURE)
                    .sized(0.7f, 1.7f).build("winter_pixie"));

    public static final Supplier<EntityType<AutumnPixieEntity>> AUTUMN_PIXIE =
            ENTITY_TYPES.register("autumn_pixie", () -> EntityType.Builder.of(AutumnPixieEntity::new, MobCategory.CREATURE)
                    .sized(0.7f, 1.7f).build("autumn_pixie"));

    public static final Supplier<EntityType<ShroomlingEntity>> SHROOMLING =
            ENTITY_TYPES.register("shroomling", () -> EntityType.Builder.of(ShroomlingEntity::new, MobCategory.CREATURE).build("shroomling"));

    public static final Supplier<EntityType<MandragoraEntity>> MANDRAGORA =
            ENTITY_TYPES.register("mandragora", () -> EntityType.Builder.of(MandragoraEntity::new, MobCategory.CREATURE).build("mandragora"));

    //TODO .sized?

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

}
