package com.saphienyako.feywild.item;

import com.saphienyako.feywild.config.FeywildConfig;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.block.GiantFlowerBlock;
import com.saphienyako.feywild.block.ModBlocks;
import com.saphienyako.feywild.entity.ModEntities;
import com.saphienyako.feywild.item.base.ExplosionProofItem;
import com.saphienyako.feywild.item.base.ToolTipBaseItem;
import com.saphienyako.feywild.item.base.ToolTipBaseItemNameBlockItem;
import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.component.TypedEntityData;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;


public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Feywild.MOD_ID);

    public static final DeferredItem<@NotNull Item> FEY_GEM = ITEMS.register("fey_gem", () -> new ToolTipBaseItem(new Item.Properties(),
            Component.translatable("message.feywild.fey_gem")));
    public static final DeferredItem<@NotNull Item> PIXIE_WING_TIARA =  ITEMS.register("pixie_wing_tiara", () -> new ExplosionProofItem(new Item.Properties(),
            Component.translatable("message.feywild.pixie_wing_tiara")));


    public static final DeferredItem<@NotNull Item> AUTUMN_PIXIE_WING_TIARA =
            ITEMS.registerItem("autumn_pixie_wing_tiara", properties -> new PixieWingTiaraItem(properties,
                    Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/fey_wings/autumn")));

    public static final DeferredItem<@NotNull Item> SPRING_PIXIE_WING_TIARA =
            ITEMS.registerItem("spring_pixie_wing_tiara", properties -> new PixieWingTiaraItem(properties,
                            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/fey_wings/spring")));

    public static final DeferredItem<@NotNull Item> SUMMER_PIXIE_WING_TIARA =
            ITEMS.registerItem("summer_pixie_wing_tiara", properties -> new PixieWingTiaraItem(properties,
                    Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/fey_wings/summer")));

    public static final DeferredItem<@NotNull Item> WINTER_PIXIE_WING_TIARA =
            ITEMS.registerItem("winter_pixie_wing_tiara", properties -> new PixieWingTiaraItem(properties,
                    Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/fey_wings/winter")));

    public static final DeferredItem<@NotNull Item> SHADOW_WING_TIARA =
            ITEMS.registerItem("shadow_wing_tiara", properties -> new PixieWingTiaraItem(properties,
                    Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/fey_wings/shadow")));

    public static final DeferredItem<@NotNull Item> LIGHT_WING_TIARA =
            ITEMS.registerItem("light_wing_tiara", properties -> new PixieWingTiaraItem(properties,
                    Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/fey_wings/light")));

    public static final DeferredItem<@NotNull Item> FEY_INK_BOTTLE = ITEMS.register("fey_ink_bottle",() -> new ToolTipBaseItem(new Item.Properties(),
            Component.translatable("message.feywild.fey_ink_bottle")));
    public static final DeferredItem<@NotNull Item> PIXIE_ORB = ITEMS.register("pixie_orb",() -> new ToolTipBaseItem(new Item.Properties(),
            Component.translatable("message.feywild.pixie_orb")));

    public static final DeferredItem<@NotNull Item> EMPTY_SUMMONING_SCROLL = ITEMS.register("empty_summoning_scroll",() -> new ToolTipBaseItem(new Item.Properties(),
            Component.translatable("message.feywild.empty_summoning_scroll")));




    public static final DeferredItem<@NotNull Item> MANDRAKE =
            ITEMS.registerItem("mandrake",
                    properties -> new ToolTipBaseItem(properties,
                            Component.translatable("message.feywild.mandrake")),
                    properties -> properties
                            .food(new FoodProperties.Builder()
                                    .nutrition(3)
                                    .saturationModifier(1.2F)
                                    .build())
                            .component(DataComponents.CONSUMABLE,
                                    Consumables.defaultFood()
                                            .onConsume(new ApplyStatusEffectsConsumeEffect(
                                                    new MobEffectInstance(MobEffects.BLINDNESS, 200, 0), 1.0F))
                                            .build()));

    public static final DeferredItem<@NotNull Item> FEYWILD_LEXICON =
            ITEMS.registerItem("feywild_lexicon", FeywildLexicon::new);


    public static final DeferredItem<@NotNull Item> FEY_DUST =
            ITEMS.registerItem("fey_dust", FeyDustItem::new,
                    properties -> properties.food(new FoodProperties.Builder().build())
                            .component(DataComponents.CONSUMABLE,
                                    Consumables.defaultFood()
                                            .onConsume(new ApplyStatusEffectsConsumeEffect(
                                                    new MobEffectInstance(MobEffects.LEVITATION, FeywildConfig.feyDustDuration, 1), 1.0F))
                                            .build()));

    public static final DeferredItem<@NotNull Item> MANDRAKE_ROOT =
            ITEMS.registerItem("mandrake_root",
                    properties -> new ToolTipBaseItemNameBlockItem(ModBlocks.MANDRAKE_CROP.get(), properties, Component.translatable("message.feywild.mandrake_root")),
                    properties -> properties.food(new FoodProperties.Builder()
                                    .nutrition(1)
                                    .saturationModifier(0.4F)
                                    .build()));

    public static final DeferredItem<@NotNull Item> GIANT_SUN_FLOWER_SEED = ITEMS.register("giant_sun_flower_seed", () -> new GiantFlowerSeedItem((GiantFlowerBlock) ModBlocks.GIANT_SUN_FLOWER.get()));
    public static final DeferredItem<@NotNull Item> GIANT_CROCUS_FLOWER_SEED = ITEMS.register("giant_crocus_flower_seed", () -> new GiantFlowerSeedItem((GiantFlowerBlock) ModBlocks.GIANT_CROCUS_FLOWER.get()));
    public static final DeferredItem<@NotNull Item> GIANT_DANDELION_FLOWER_SEED = ITEMS.register("giant_dandelion_flower_seed", () -> new GiantFlowerSeedItem((GiantFlowerBlock) ModBlocks.GIANT_DANDELION_FLOWER.get()));

    public static final DeferredItem<@NotNull Item> FEYWILD_MUSIC_DISC = ITEMS.registerItem("feywild_music_disc",
            properties -> new Item(properties.jukeboxPlayable(ModSounds.FEYWILD_MUSIC_KEY).stacksTo(1)));

    public static final DeferredItem<@NotNull Item> SUMMONING_SCROLL_SPRING_PIXIE = ITEMS.register("summoning_scroll_spring_pixie", () -> new SummoningScrollItem(new Item.Properties(),
            Component.translatable("message.feywild.summoning_scroll_spring_pixie")));
    public static final DeferredItem<@NotNull Item> SUMMONING_SCROLL_SUMMER_PIXIE = ITEMS.register("summoning_scroll_summer_pixie", () -> new SummoningScrollItem(new Item.Properties(),
            Component.translatable("message.feywild.summoning_scroll_summer_pixie")));
    public static final DeferredItem<@NotNull Item> SUMMONING_SCROLL_AUTUMN_PIXIE = ITEMS.register("summoning_scroll_autumn_pixie", () -> new SummoningScrollItem(new Item.Properties(),
            Component.translatable("message.feywild.summoning_scroll_autumn_pixie")));
    public static final DeferredItem<@NotNull Item> SUMMONING_SCROLL_WINTER_PIXIE = ITEMS.register("summoning_scroll_winter_pixie", () -> new SummoningScrollItem(new Item.Properties(),
            Component.translatable("message.feywild.summoning_scroll_winter_pixie")));
    public static final DeferredItem<@NotNull Item> SUMMONING_SCROLL_SHROOMLING = ITEMS.register("summoning_scroll_shroomling", () -> new SummoningScrollItem(new Item.Properties(),
            Component.translatable("message.feywild.summoning_scroll_shroomling")));
    public static final DeferredItem<@NotNull Item> SUMMONING_SCROLL_MANDRAGORA = ITEMS.register("summoning_scroll_mandragora", () -> new SummoningScrollItem(new Item.Properties(),
            Component.translatable("message.feywild.summoning_scroll_mandragora")));

    public static final DeferredItem<@NotNull Item> SUMMONING_SCROLL_BELLSNICKEL = ITEMS.register("summoning_scroll_bellsnickel", () -> new SummoningScrollItem(new Item.Properties(),
            Component.translatable("message.feywild.summoning_scroll_bellsnickel")));

   public static final DeferredItem<@NotNull Item> SUMMONING_SCROLL_BEE_KNIGHT = ITEMS.register("summoning_scroll_bee_knight", () -> new SummoningScrollItem(new Item.Properties(),
            Component.translatable("message.feywild.summoning_scroll_bee_knight").withStyle(ChatFormatting.RED)));

    public static final DeferredItem<@NotNull Item> SUMMONING_SCROLL_SPRING_TREE_ENT = ITEMS.register("summoning_scroll_spring_tree_ent", () -> new SummoningScrollItem(new Item.Properties(),
            Component.translatable("message.feywild.summoning_scroll_spring_tree_ent")));

    public static final DeferredItem<@NotNull Item> SUMMONING_SCROLL_SUMMER_TREE_ENT = ITEMS.register("summoning_scroll_summer_tree_ent", () -> new SummoningScrollItem(new Item.Properties(),
            Component.translatable("message.feywild.summoning_scroll_summer_tree_ent")));

    public static final DeferredItem<@NotNull Item> SUMMONING_SCROLL_AUTUMN_TREE_ENT = ITEMS.register("summoning_scroll_autumn_tree_ent", () -> new SummoningScrollItem(new Item.Properties(),
            Component.translatable("message.feywild.summoning_scroll_autumn_tree_ent")));

    public static final DeferredItem<@NotNull Item> SUMMONING_SCROLL_WINTER_TREE_ENT = ITEMS.register("summoning_scroll_winter_tree_ent", () -> new SummoningScrollItem(new Item.Properties(),
            Component.translatable("message.feywild.summoning_scroll_winter_tree_ent")));

    public static final DeferredItem<@NotNull Item> SUMMONING_SCROLL_QUEEN_TITANIA = ITEMS.register("summoning_scroll_queen_titania", () -> new BossSummoningScrollItem(new Item.Properties(),
            Component.translatable("message.feywild.summoning_scroll_queen_titania")));

    public static final DeferredItem<@NotNull Item> SUMMONING_SCROLL_QUEEN_MAB = ITEMS.register("summoning_scroll_queen_mab", () -> new BossSummoningScrollItem(new Item.Properties(),
            Component.translatable("message.feywild.summoning_scroll_queen_mab")));

    public static final DeferredItem<@NotNull Item> SUMMONING_SCROLL_OBERON = ITEMS.register("summoning_scroll_oberon", () -> new BossSummoningScrollItem(new Item.Properties(),
            Component.translatable("message.feywild.summoning_scroll_oberon")));

    public static final DeferredItem<@NotNull Item> SUMMONING_SCROLL_ASHEN_LORD = ITEMS.register("summoning_scroll_ashen_lord", () -> new BossSummoningScrollItem(new Item.Properties(),
            Component.translatable("message.feywild.summoning_scroll_ashen_lord")));

    //TODO egg textures
    public static final DeferredItem<@NotNull SpawnEggItem> SPAWN_EGG_SPRING_PIXIE =
            ITEMS.registerItem("spawn_egg_spring_pixie",
                    SpawnEggItem::new, properties -> properties.component(DataComponents.ENTITY_DATA,
                            TypedEntityData.of(ModEntities.SPRING_PIXIE.get(), new CompoundTag())));

    public static final DeferredItem<@NotNull SpawnEggItem> SPAWN_EGG_AUTUMN_PIXIE =
            ITEMS.registerItem("spawn_egg_autumn_pixie",
                    SpawnEggItem::new, properties -> properties.component(DataComponents.ENTITY_DATA,
                            TypedEntityData.of(ModEntities.AUTUMN_PIXIE.get(), new CompoundTag())));

    public static final DeferredItem<@NotNull SpawnEggItem> SPAWN_EGG_SUMMER_PIXIE =
            ITEMS.registerItem("spawn_egg_summer_pixie",
                    SpawnEggItem::new, properties -> properties.component(DataComponents.ENTITY_DATA,
                            TypedEntityData.of(ModEntities.SUMMER_PIXIE.get(), new CompoundTag())));

    public static final DeferredItem<@NotNull SpawnEggItem> SPAWN_EGG_WINTER_PIXIE =
            ITEMS.registerItem("spawn_egg_winter_pixie",
                    SpawnEggItem::new, properties -> properties.component(DataComponents.ENTITY_DATA,
                            TypedEntityData.of(ModEntities.WINTER_PIXIE.get(), new CompoundTag())));

    public static final DeferredItem<@NotNull SpawnEggItem> SPAWN_EGG_SHROOMLING =
            ITEMS.registerItem("spawn_egg_shroomling",
                    SpawnEggItem::new, properties -> properties.component(DataComponents.ENTITY_DATA,
                            TypedEntityData.of(ModEntities.SHROOMLING.get(), new CompoundTag())));

    public static final DeferredItem<@NotNull SpawnEggItem> SPAWN_EGG_MANDRAGORA =
            ITEMS.registerItem("spawn_egg_mandragora",
                    SpawnEggItem::new, properties -> properties.component(DataComponents.ENTITY_DATA,
                            TypedEntityData.of(ModEntities.MANDRAGORA.get(), new CompoundTag())));

    public static final DeferredItem<@NotNull SpawnEggItem> SPAWN_EGG_BELLSNICKEL =
            ITEMS.registerItem("spawn_egg_bellsnickel",
                    SpawnEggItem::new, properties -> properties.component(DataComponents.ENTITY_DATA,
                            TypedEntityData.of(ModEntities.BELLSNICKEL.get(), new CompoundTag())));

    public static final DeferredItem<@NotNull SpawnEggItem> SPAWN_EGG_BEE_KNIGHT =
            ITEMS.registerItem("spawn_egg_bee_knight",
                    SpawnEggItem::new, properties -> properties.component(DataComponents.ENTITY_DATA,
                            TypedEntityData.of(ModEntities.BEE_MOUNT.get(), new CompoundTag())));

    public static final DeferredItem<@NotNull SpawnEggItem> SPAWN_EGG_SPRING_TREE_ENT =
            ITEMS.registerItem("spawn_egg_spring_tree_ent",
                    SpawnEggItem::new, properties -> properties.component(DataComponents.ENTITY_DATA,
                            TypedEntityData.of(ModEntities.SPRING_TREE_ENT.get(), new CompoundTag())));

    public static final DeferredItem<@NotNull SpawnEggItem> SPAWN_EGG_SUMMER_TREE_ENT =
            ITEMS.registerItem("spawn_egg_summer_tree_ent",
                    SpawnEggItem::new, properties -> properties.component(DataComponents.ENTITY_DATA,
                            TypedEntityData.of(ModEntities.SUMMER_TREE_ENT.get(), new CompoundTag())));

    public static final DeferredItem<@NotNull SpawnEggItem> SPAWN_EGG_AUTUMN_TREE_ENT =
            ITEMS.registerItem("spawn_egg_autumn_tree_ent",
                    SpawnEggItem::new, properties -> properties.component(DataComponents.ENTITY_DATA,
                            TypedEntityData.of(ModEntities.AUTUMN_TREE_ENT.get(), new CompoundTag())));

    public static final DeferredItem<@NotNull SpawnEggItem> SPAWN_EGG_WINTER_TREE_ENT =
            ITEMS.registerItem("spawn_egg_winter_tree_ent",
                    SpawnEggItem::new, properties -> properties.component(DataComponents.ENTITY_DATA,
                            TypedEntityData.of(ModEntities.WINTER_TREE_ENT.get(), new CompoundTag())));


    public static final DeferredItem<@NotNull SpawnEggItem> SPAWN_EGG_QUEEN_TITANIA =
            ITEMS.registerItem("spawn_egg_queen_titania",
                    SpawnEggItem::new, properties -> properties.component(DataComponents.ENTITY_DATA,
                            TypedEntityData.of(ModEntities.TITANIA.get(), new CompoundTag())));

    public static final DeferredItem<@NotNull SpawnEggItem> SPAWN_EGG_QUEEN_MAB =
            ITEMS.registerItem("spawn_egg_queen_mab",
                    SpawnEggItem::new, properties -> properties.component(DataComponents.ENTITY_DATA,
                            TypedEntityData.of(ModEntities.MAB.get(), new CompoundTag())));

    public static final DeferredItem<@NotNull SpawnEggItem> SPAWN_EGG_OBERON =
            ITEMS.registerItem("spawn_egg_oberon",
                    SpawnEggItem::new, properties -> properties.component(DataComponents.ENTITY_DATA,
                            TypedEntityData.of(ModEntities.OBERON.get(), new CompoundTag())));

    public static final DeferredItem<@NotNull SpawnEggItem> SPAWN_EGG_ASHEN_LORD =
            ITEMS.registerItem("spawn_egg_ashen_lord",
                    SpawnEggItem::new, properties -> properties.component(DataComponents.ENTITY_DATA,
                            TypedEntityData.of(ModEntities.ASHEN_LORD.get(), new CompoundTag())));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
