package com.saphienyako.feywild.item;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.block.GiantFlowerBlock;
import com.saphienyako.feywild.block.ModBlocks;
import com.saphienyako.feywild.entity.ModEntities;
import com.saphienyako.feywild.item.base.ToolTipBaseItem;
import com.saphienyako.feywild.item.base.ToolTipBaseItemNameBlockItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    //TODO add patchouli(?)
    //TODO JEI
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Feywild.MOD_ID);

    public static final RegistryObject<Item> FEY_GEM = ITEMS.register("fey_gem", () -> new ToolTipBaseItem(new Item.Properties(),
            Component.translatable("message.feywild.fey_gem")));
    public static final RegistryObject<Item> MANDRAKE_ROOT = ITEMS.register("mandrake_root", () -> new ToolTipBaseItemNameBlockItem(ModBlocks.MANDRAKE_CROP.get(), new Item.Properties(),
            Component.translatable("message.feywild.mandrake_root")));
    public static final RegistryObject<Item> FEY_INK_BOTTLE = ITEMS.register("fey_ink_bottle", () -> new ToolTipBaseItem(new Item.Properties(),
            Component.translatable("message.feywild.fey_ink_bottle")));
    public static final RegistryObject<Item> FEYWILD_MUSIC_DISC = ITEMS.register("feywild_music_disc", FeywildMusicDiscItem::new);

    public static final RegistryObject<Item> PIXIE_ORB = ITEMS.register("pixie_orb", () -> new ToolTipBaseItem(new Item.Properties(),
            Component.translatable("message.feywild.pixie_orb")));
    public static final RegistryObject<Item> EMPTY_SUMMONING_SCROLL = ITEMS.register("empty_summoning_scroll", () -> new ToolTipBaseItem(new Item.Properties(),
            Component.translatable("message.feywild.empty_summoning_scroll")));

    public static final RegistryObject<Item> FEYWILD_LEXICON = ITEMS.register("feywild_lexicon", () -> new FeywildLexicon(new Item.Properties()));

    public static final RegistryObject<Item> BEE_KNIGHT_GOLD_SPEAR = ITEMS.register(
            "bee_knight_gold_spear", () -> new SwordItem(Tiers.GOLD, 3, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> BEE_KNIGHT_DIAMOND_SPEAR = ITEMS.register(
            "bee_knight_diamond_spear", () -> new SwordItem(Tiers.DIAMOND, 3, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> BEE_KNIGHT_NETHERITE_SPEAR = ITEMS.register(
            "bee_knight_netherite_spear", () -> new SwordItem(Tiers.NETHERITE, 3, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> SUMMONING_SCROLL_SPRING_PIXIE = ITEMS.register("summoning_scroll_spring_pixie", () -> new SummoningScrollItem<>(new Item.Properties(),
            Component.translatable("message.feywild.summoning_scroll_spring_pixie")));
    public static final RegistryObject<Item> SUMMONING_SCROLL_SUMMER_PIXIE = ITEMS.register("summoning_scroll_summer_pixie", () -> new SummoningScrollItem<>(new Item.Properties(),
            Component.translatable("message.feywild.summoning_scroll_summer_pixie")));
    public static final RegistryObject<Item> SUMMONING_SCROLL_AUTUMN_PIXIE = ITEMS.register("summoning_scroll_autumn_pixie", () -> new SummoningScrollItem<>(new Item.Properties(),
            Component.translatable("message.feywild.summoning_scroll_autumn_pixie")));
    public static final RegistryObject<Item> SUMMONING_SCROLL_WINTER_PIXIE = ITEMS.register("summoning_scroll_winter_pixie", () -> new SummoningScrollItem<>(new Item.Properties(),
            Component.translatable("message.feywild.summoning_scroll_winter_pixie")));

    public static final RegistryObject<Item> SUMMONING_SCROLL_SHROOMLING = ITEMS.register("summoning_scroll_shroomling", () -> new SummoningScrollItem<>(new Item.Properties(),
            Component.translatable("message.feywild.summoning_scroll_shroomling")));
    public static final RegistryObject<Item> SUMMONING_SCROLL_MANDRAGORA = ITEMS.register("summoning_scroll_mandragora", () -> new SummoningScrollItem<>(new Item.Properties(),
            Component.translatable("message.feywild.summoning_scroll_mandragora")));

    public static final RegistryObject<Item> SUMMONING_SCROLL_BELLSNICKEL = ITEMS.register("summoning_scroll_bellsnickel",() -> new SummoningScrollItem<>(new Item.Properties(),
            Component.translatable("message.feywild.summoning_scroll_bellsnickel")));

    public static final RegistryObject<Item> SUMMONING_SCROLL_BEE_KNIGHT = ITEMS.register("summoning_scroll_bee_knight", () -> new SummoningScrollItem<>(new Item.Properties(),
            Component.translatable("message.feywild.summoning_scroll_bee_knight")));

    public static final RegistryObject<Item> SUMMONING_SCROLL_SPRING_TREE_ENT = ITEMS.register("summoning_scroll_spring_tree_ent", () -> new SummoningScrollItem(new Item.Properties(),
            Component.translatable("message.feywild.summoning_scroll_spring_tree_ent")));

    public static final RegistryObject<Item> SUMMONING_SCROLL_SUMMER_TREE_ENT = ITEMS.register("summoning_scroll_summer_tree_ent", () -> new SummoningScrollItem(new Item.Properties(),
            Component.translatable("message.feywild.summoning_scroll_summer_tree_ent")));

    public static final RegistryObject<Item> SUMMONING_SCROLL_AUTUMN_TREE_ENT = ITEMS.register("summoning_scroll_autumn_tree_ent", () -> new SummoningScrollItem(new Item.Properties(),
            Component.translatable("message.feywild.summoning_scroll_autumn_tree_ent")));

    public static final RegistryObject<Item> SUMMONING_SCROLL_WINTER_TREE_ENT = ITEMS.register("summoning_scroll_winter_tree_ent", () -> new SummoningScrollItem(new Item.Properties(),
            Component.translatable("message.feywild.summoning_scroll_winter_tree_ent")));


    public static final RegistryObject<Item> FEY_DUST = ITEMS.register("fey_dust", () -> new FeyDustItem(new Item.Properties().food(
            new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.LEVITATION, 30, 1), 1).build())));

    public static final RegistryObject<Item> MANDRAKE = ITEMS.register("mandrake", () -> new ToolTipBaseItem(new Item.Properties().food(
            new FoodProperties.Builder()
                    .nutrition(3)
                    .saturationMod(1.2f)
                    .effect(() -> new MobEffectInstance(MobEffects.BLINDNESS, 200, 0), 1)
                    .build()),Component.translatable("message.feywild.mandrake")));

    public static final RegistryObject<Item> GIANT_SUN_FLOWER_SEED = ITEMS.register("giant_sun_flower_seed", () -> new GiantFlowerSeedItem((GiantFlowerBlock) ModBlocks.GIANT_SUN_FLOWER.get()));
    public static final RegistryObject<Item> GIANT_CROCUS_FLOWER_SEED = ITEMS.register("giant_crocus_flower_seed", () -> new GiantFlowerSeedItem((GiantFlowerBlock) ModBlocks.GIANT_CROCUS_FLOWER.get()));
    public static final RegistryObject<Item> GIANT_DANDELION_FLOWER_SEED = ITEMS.register("giant_dandelion_flower_seed", () -> new GiantFlowerSeedItem((GiantFlowerBlock) ModBlocks.GIANT_DANDELION_FLOWER.get()));
    public static final RegistryObject<Item> SPAWN_EGG_SPRING_PIXIE = ITEMS.register("spawn_egg_spring_pixie", () -> new ForgeSpawnEggItem(ModEntities.SPRING_PIXIE, 0xf085a9, 0xa1db67, new Item.Properties()));
    public static final RegistryObject<Item> SPAWN_EGG_AUTUMN_PIXIE = ITEMS.register("spawn_egg_autumn_pixie", () -> new ForgeSpawnEggItem(ModEntities.AUTUMN_PIXIE,0xb73737, 0xa56259, new Item.Properties()));
    public static final RegistryObject<Item> SPAWN_EGG_SUMMER_PIXIE = ITEMS.register("spawn_egg_summer_pixie", ()-> new ForgeSpawnEggItem(ModEntities.SUMMER_PIXIE,0xf38807, 0xfedc5a, new Item.Properties()));
    public static final RegistryObject<Item> SPAWN_EGG_WINTER_PIXIE = ITEMS.register("spawn_egg_winter_pixie", ()-> new ForgeSpawnEggItem(ModEntities.WINTER_PIXIE,0x84b4be, 0x323c81, new Item.Properties()));

    public static final RegistryObject<Item> SPAWN_EGG_SHROOMLING = ITEMS.register("spawn_egg_shroomling", () -> new ForgeSpawnEggItem(ModEntities.SHROOMLING, 0xf2d5d0, 0xd4260b, new Item.Properties()));

    public static final RegistryObject<Item> SPAWN_EGG_MANDRAGORA = ITEMS.register("spawn_egg_mandragora", () -> new ForgeSpawnEggItem(ModEntities.MANDRAGORA, 0x54d911, 0xf7a3f7, new Item.Properties()));

    public static final RegistryObject<Item> SPAWN_EGG_BELLSNICKEL = ITEMS.register("spawn_egg_bellsnickel", () -> new ForgeSpawnEggItem(ModEntities.BELLSNICKEL, 0x6a95f6, 0x2f3063, new Item.Properties()));

    public static final RegistryObject<Item> SPAWN_EGG_BEE_KNIGHT = ITEMS.register("spawn_egg_bee_knight", () -> new ForgeSpawnEggItem(ModEntities.BEE_MOUNT,0xeeb359, 0x66380c, new Item.Properties()));
    public static final RegistryObject<Item> SPAWN_EGG_SPRING_TREE_ENT = ITEMS.register("spawn_egg_spring_tree_ent", () -> new ForgeSpawnEggItem(ModEntities.SPRING_TREE_ENT,0x5C281E, 0xa1db67, new Item.Properties()));

    public static final RegistryObject<Item> SPAWN_EGG_SUMMER_TREE_ENT = ITEMS.register("spawn_egg_summer_tree_ent", () -> new ForgeSpawnEggItem(ModEntities.SUMMER_TREE_ENT,0x5C281E, 0xfedc5a, new Item.Properties()));

    public static final RegistryObject<Item> SPAWN_EGG_AUTUMN_TREE_ENT = ITEMS.register("spawn_egg_autumn_tree_ent", () -> new ForgeSpawnEggItem(ModEntities.AUTUMN_TREE_ENT,0x5C281E, 0xb73737, new Item.Properties()));

    public static final RegistryObject<Item> SPAWN_EGG_WINTER_TREE_ENT = ITEMS.register("spawn_egg_winter_tree_ent", () -> new ForgeSpawnEggItem(ModEntities.WINTER_TREE_ENT,0x5C281E, 0x84b4be, new Item.Properties()));



    public static void register(IEventBus eventBus) {ITEMS.register(eventBus);}
}
