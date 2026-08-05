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
                    .sized(0.5f, 0.95f).build("spring_pixie"));

    public static final Supplier<EntityType<SummerPixieEntity>> SUMMER_PIXIE =
            ENTITY_TYPES.register("summer_pixie", () -> EntityType.Builder.of(SummerPixieEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.95f).build("summer_pixie"));

    public static final Supplier<EntityType<WinterPixieEntity>> WINTER_PIXIE =
            ENTITY_TYPES.register("winter_pixie", () -> EntityType.Builder.of(WinterPixieEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.95f).build("winter_pixie"));

    public static final Supplier<EntityType<AutumnPixieEntity>> AUTUMN_PIXIE =
            ENTITY_TYPES.register("autumn_pixie", () -> EntityType.Builder.of(AutumnPixieEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.95f).build("autumn_pixie"));

    public static final Supplier<EntityType<ShroomlingEntity>> SHROOMLING =
            ENTITY_TYPES.register("shroomling", () -> EntityType.Builder.of(ShroomlingEntity::new, MobCategory.CREATURE)
                    .sized(0.8f, 1.4f).eyeHeight(1.02f).build("shroomling"));

    public static final Supplier<EntityType<MandragoraEntity>> MANDRAGORA =
            ENTITY_TYPES.register("mandragora", () -> EntityType.Builder.of(MandragoraEntity::new, MobCategory.CREATURE)
                    .sized(0.7f, 1.3f).eyeHeight(0.9f).build("mandragora"));

    public static final Supplier<EntityType<MooShroomCowEntity>> MOO_SHROOM_COW =
            ENTITY_TYPES.register("moo_shroom_cow", () -> EntityType.Builder.of(MooShroomCowEntity::new, MobCategory.CREATURE).build("moo_shroom_cow"));

    public static final Supplier<EntityType<BellsnickelEntity>> BELLSNICKEL =
            ENTITY_TYPES.register("bellsnickel", ()-> EntityType.Builder.of(BellsnickelEntity::new, MobCategory.CREATURE)
                    .sized(1.2f, 1.3f).eyeHeight(0.6f).build("bellsnickel"));

    public static final Supplier<EntityType<BeeKnightEntity>> BEE_KNIGHT =
            ENTITY_TYPES.register("bee_knight", ()-> EntityType.Builder.of(BeeKnightEntity::new, MobCategory.CREATURE)
                    .sized(0.6f, 0.8f).build("bee_knight"));

    public static final Supplier<EntityType<BeeMountEntity>> BEE_MOUNT =
            ENTITY_TYPES.register("bee_mount", ()-> EntityType.Builder.of(BeeMountEntity::new, MobCategory.CREATURE)
                    .sized(1.0f,0.7f).eyeHeight(0.3f).build("bee_mount"));

    public static final Supplier<EntityType<SpringTreeEntEntity>> SPRING_TREE_ENT =
            ENTITY_TYPES.register("spring_tree_ent", ()-> EntityType.Builder.of(SpringTreeEntEntity::new, MobCategory.CREATURE)
                    .sized(2,3.9f).build("spring_tree_ent"));

    public static final Supplier<EntityType<SummerTreeEntEntity>> SUMMER_TREE_ENT =
            ENTITY_TYPES.register("summer_tree_ent", () -> EntityType.Builder.of(SummerTreeEntEntity::new, MobCategory.CREATURE)
                    .sized(2,3.9f).build("summer_tree_ent"));

    public static final Supplier<EntityType<AutumnTreeEntEntity>> AUTUMN_TREE_ENT =
            ENTITY_TYPES.register("autumn_tree_ent", () -> EntityType.Builder.of(AutumnTreeEntEntity::new, MobCategory.CREATURE)
                    .sized(2,3.9f).build("autumn_tree_ent"));

    public static final Supplier<EntityType<WinterTreeEntEntity>> WINTER_TREE_ENT =
            ENTITY_TYPES.register("winter_tree_ent", ()-> EntityType.Builder.of(WinterTreeEntEntity::new, MobCategory.CREATURE)
                    .sized(2,3.9f).build("winter_tree_ent"));

    public static final Supplier<EntityType<SpriteEntity>> SPRITE =
            ENTITY_TYPES.register("sprite", () -> EntityType.Builder.of(SpriteEntity::new, MobCategory.CREATURE).build("sprite"));

    public static final Supplier<EntityType<TitaniaEntity>> TITANIA =
            ENTITY_TYPES.register("titania", () -> EntityType.Builder.of(TitaniaEntity::new, MobCategory.CREATURE)
                    .sized(1.8f,3.7f).build("titania"));

    public static final Supplier<EntityType<MabEntity>> MAB =
            ENTITY_TYPES.register("mab", () -> EntityType.Builder.of(MabEntity::new, MobCategory.CREATURE)
                    .sized(1.8f,3.7f).build("mab"));

    public static final Supplier<EntityType<OberonEntity>> OBERON =
            ENTITY_TYPES.register("oberon", () -> EntityType.Builder.of(OberonEntity::new, MobCategory.CREATURE)
                    .sized(1.8f,3.7f).build("oberon"));

    public static final Supplier<EntityType<AshenLordEntity>> ASHEN_LORD =
            ENTITY_TYPES.register("ashen_lord", () -> EntityType.Builder.of(AshenLordEntity::new, MobCategory.CREATURE)
                    .sized(1.8f,3.7f).build("ashen_lord"));

    public static final Supplier<EntityType<LeafProjectile>> LEAF_PROJECTILE =
            ENTITY_TYPES.register(
                    "autumn_leaf_projectile", () -> EntityType.Builder.<LeafProjectile>of(LeafProjectile::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(4)
                            .updateInterval(10)
                            .build("autumn_leaf_projectile")
            );

    public static final Supplier<EntityType<FeyWingsEntity>> FEY_WINGS =
            ENTITY_TYPES.register("fey_wings", () -> EntityType.Builder.of(FeyWingsEntity::new, MobCategory.MISC).build("fey_wings"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

}
