package com.saphienyako.feywild.item;

import com.saphienyako.feywild.config.FeywildConfig;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.block.GiantFlowerBlock;
import com.saphienyako.feywild.block.ModBlocks;
import com.saphienyako.feywild.entity.ModEntities;
import com.saphienyako.feywild.item.base.ToolTipBaseItem;
import com.saphienyako.feywild.item.base.ToolTipBaseItemNameBlockItem;
import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;


public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Feywild.MOD_ID);

    public static final DeferredItem<Item> FEY_GEM = ITEMS.register("fey_gem", () -> new ToolTipBaseItem(new Item.Properties(),
            Component.translatable("message.feywild.fey_gem")));

    public static final DeferredItem<Item> FEY_INK_BOTTLE = ITEMS.register("fey_ink_bottle",() -> new ToolTipBaseItem(new Item.Properties(),
            Component.translatable("message.feywild.fey_ink_bottle")));
    public static final DeferredItem<Item> PIXIE_ORB = ITEMS.register("pixie_orb",() -> new ToolTipBaseItem(new Item.Properties(),
            Component.translatable("message.feywild.pixie_orb")));

    public static final DeferredItem<Item> EMPTY_SUMMONING_SCROLL = ITEMS.register("empty_summoning_scroll",() -> new ToolTipBaseItem(new Item.Properties(),
            Component.translatable("message.feywild.empty_summoning_scroll")));

    public static final DeferredItem<Item> MANDRAKE = ITEMS.register("mandrake", ()-> new ToolTipBaseItem(new Item.Properties().food(
            new FoodProperties.Builder()
                    .nutrition(3)
                    .saturationModifier(1.2f)
                    .effect(() -> new MobEffectInstance(MobEffects.BLINDNESS, 200, 0), 1)
                    .build()), Component.translatable("message.feywild.mandrake")));

    public static final DeferredItem<Item> FEYWILD_LEXICON =
            ITEMS.registerItem("feywild_lexicon", FeywildLexicon::new, new Item.Properties());

    public static final DeferredItem<Item> FEY_DUST =
            ITEMS.registerItem("fey_dust", FeyDustItem::new, new Item.Properties().food(
                    new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.LEVITATION, FeywildConfig.feyDustDuration, 1), 1).build()));

    public static final DeferredItem<Item> MANDRAKE_ROOT = ITEMS.register("mandrake_root",
            () -> new ToolTipBaseItemNameBlockItem(ModBlocks.MANDRAKE_CROP.get(), new Item.Properties().food(
                    new FoodProperties.Builder()
                            .nutrition(1)
                            .saturationModifier(0.4f)
                            .build()), Component.translatable("message.feywild.mandrake_root")));

    public static final DeferredItem<Item> GIANT_SUN_FLOWER_SEED = ITEMS.register("giant_sun_flower_seed", () -> new GiantFlowerSeedItem((GiantFlowerBlock) ModBlocks.GIANT_SUN_FLOWER.get()));
    public static final DeferredItem<Item> GIANT_CROCUS_FLOWER_SEED = ITEMS.register("giant_crocus_flower_seed", () -> new GiantFlowerSeedItem((GiantFlowerBlock) ModBlocks.GIANT_CROCUS_FLOWER.get()));
    public static final DeferredItem<Item> GIANT_DANDELION_FLOWER_SEED = ITEMS.register("giant_dandelion_flower_seed", () -> new GiantFlowerSeedItem((GiantFlowerBlock) ModBlocks.GIANT_DANDELION_FLOWER.get()));

    public static final DeferredItem<Item> FEYWILD_MUSIC_DISC = ITEMS.registerItem("feywild_music_disc",
            properties -> new Item(properties.jukeboxPlayable(ModSounds.FEYWILD_MUSIC_KEY).stacksTo(1)));

    public static final DeferredItem<Item> SUMMONING_SCROLL_SPRING_PIXIE = ITEMS.register("summoning_scroll_spring_pixie", () -> new SummoningScrollItem(new Item.Properties(),
            Component.translatable("message.feywild.summoning_scroll_spring_pixie")));
    public static final DeferredItem<Item> SUMMONING_SCROLL_SUMMER_PIXIE = ITEMS.register("summoning_scroll_summer_pixie", () -> new SummoningScrollItem(new Item.Properties(),
            Component.translatable("message.feywild.summoning_scroll_summer_pixie")));
    public static final DeferredItem<Item> SUMMONING_SCROLL_AUTUMN_PIXIE = ITEMS.register("summoning_scroll_autumn_pixie", () -> new SummoningScrollItem(new Item.Properties(),
            Component.translatable("message.feywild.summoning_scroll_autumn_pixie")));
    public static final DeferredItem<Item> SUMMONING_SCROLL_WINTER_PIXIE = ITEMS.register("summoning_scroll_winter_pixie", () -> new SummoningScrollItem(new Item.Properties(),
            Component.translatable("message.feywild.summoning_scroll_winter_pixie")));
    public static final DeferredItem<Item> SUMMONING_SCROLL_SHROOMLING = ITEMS.register("summoning_scroll_shroomling", () -> new SummoningScrollItem(new Item.Properties(),
            Component.translatable("message.feywild.summoning_scroll_shroomling")));
    public static final DeferredItem<Item> SUMMONING_SCROLL_MANDRAGORA = ITEMS.register("summoning_scroll_mandragora", () -> new SummoningScrollItem(new Item.Properties(),
            Component.translatable("message.feywild.summoning_scroll_mandragora")));

    public static final DeferredItem<Item> SUMMONING_SCROLL_BELLSNICKEL = ITEMS.register("summoning_scroll_bellsnickel", () -> new SummoningScrollItem(new Item.Properties(),
            Component.translatable("message.feywild.summoning_scroll_bellsnickel")));
    //TODO add lang

    //public static final DeferredItem<Item> SUMMONING_SCROLL_BEE_KNIGHT = ITEMS.register("summoning_scroll_bee_knight", () -> new SummoningScrollItem(new Item.Properties()));
    @SuppressWarnings("deprecation")
    public static final DeferredItem<Item> SPAWN_EGG_SPRING_PIXIE = ITEMS.register("spawn_egg_spring_pixie", () -> new SpawnEggItem(ModEntities.SPRING_PIXIE.get(), 0xf085a9, 0xa1db67, new Item.Properties()));
    @SuppressWarnings("deprecation")
    public static final DeferredItem<Item> SPAWN_EGG_AUTUMN_PIXIE = ITEMS.register("spawn_egg_autumn_pixie", () -> new SpawnEggItem(ModEntities.AUTUMN_PIXIE.get(),0xb73737, 0xa56259, new Item.Properties()));
    @SuppressWarnings("deprecation")
    public static final DeferredItem<Item> SPAWN_EGG_SUMMER_PIXIE = ITEMS.register("spawn_egg_summer_pixie", ()-> new SpawnEggItem(ModEntities.SUMMER_PIXIE.get(),0xf38807, 0xfedc5a, new Item.Properties()));
    @SuppressWarnings("deprecation")
    public static final DeferredItem<Item> SPAWN_EGG_WINTER_PIXIE = ITEMS.register("spawn_egg_winter_pixie", ()-> new SpawnEggItem(ModEntities.WINTER_PIXIE.get(),0x84b4be, 0x323c81, new Item.Properties()));
    @SuppressWarnings("deprecation")
    public static final DeferredItem<Item> SPAWN_EGG_SHROOMLING = ITEMS.register("spawn_egg_shroomling", () -> new SpawnEggItem(ModEntities.SHROOMLING.get(), 0xf2d5d0, 0xd4260b, new Item.Properties()));
    @SuppressWarnings("deprecation")
    public static final DeferredItem<Item> SPAWN_EGG_MANDRAGORA = ITEMS.register("spawn_egg_mandragora", () -> new SpawnEggItem(ModEntities.MANDRAGORA.get(), 0x54d911, 0xf7a3f7, new Item.Properties()));
    @SuppressWarnings("deprecation")
    public static final DeferredItem<Item> SPAWN_EGG_BELLSNICKEL = ITEMS.register("spawn_egg_bellsnickel", () -> new SpawnEggItem(ModEntities.BELLSNICKEL.get(),0x6a95f6, 0x2f3063, new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
