package com.saphienyako.feywild.item;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.block.GiantFlowerBlock;
import com.saphienyako.feywild.block.ModBlocks;
import com.saphienyako.feywild.entity.ModEntities;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Feywild.MOD_ID);

    public static final RegistryObject<Item> FEY_GEM = ITEMS.register("fey_gem", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.FEYWILD_TAB)));
    public static final RegistryObject<Item> MANDRAKE_ROOT = ITEMS.register("mandrake_root", () -> new BlockItem(ModBlocks.MANDRAKE_CROP.get(), new Item.Properties().tab(ModCreativeModeTab.FEYWILD_TAB)));
    public static final RegistryObject<Item> FEY_INK_BOTTLE = ITEMS.register("fey_ink_bottle", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.FEYWILD_TAB)));
    public static final RegistryObject<Item> FEYWILD_MUSIC_DISC = ITEMS.register("feywild_music_disc", FeywildMusicDiscItem::new);

    public static final RegistryObject<Item> PIXIE_ORB = ITEMS.register("pixie_orb", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.FEYWILD_TAB)));
    public static final RegistryObject<Item> EMPTY_SUMMONING_SCROLL = ITEMS.register("empty_summoning_scroll", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.FEYWILD_TAB)));

    public static final RegistryObject<Item> FEYWILD_LEXICON = ITEMS.register("feywild_lexicon", () -> new FeywildLexicon(new Item.Properties().tab(ModCreativeModeTab.FEYWILD_TAB)));
    public static final RegistryObject<Item> SUMMONING_SCROLL_SPRING_PIXIE = ITEMS.register("summoning_scroll_spring_pixie", () -> new SummoningScrollItem<>(new Item.Properties().tab(ModCreativeModeTab.FEYWILD_TAB)));
    public static final RegistryObject<Item> SUMMONING_SCROLL_SUMMER_PIXIE = ITEMS.register("summoning_scroll_summer_pixie", () -> new SummoningScrollItem<>(new Item.Properties().tab(ModCreativeModeTab.FEYWILD_TAB)));
    public static final RegistryObject<Item> SUMMONING_SCROLL_AUTUMN_PIXIE = ITEMS.register("summoning_scroll_autumn_pixie", () -> new SummoningScrollItem<>(new Item.Properties().tab(ModCreativeModeTab.FEYWILD_TAB)));
    public static final RegistryObject<Item> SUMMONING_SCROLL_WINTER_PIXIE = ITEMS.register("summoning_scroll_winter_pixie", () -> new SummoningScrollItem<>(new Item.Properties().tab(ModCreativeModeTab.FEYWILD_TAB)));
    public static final RegistryObject<Item> FEY_DUST = ITEMS.register("fey_dust", () -> new FeyDustItem(new Item.Properties().tab(ModCreativeModeTab.FEYWILD_TAB).food(
            new Food.Builder().effect(() -> new EffectInstance(Effects.LEVITATION, 30, 1), 1).build())));
    //TODO Configurations
    public static final RegistryObject<Item> MANDRAKE = ITEMS.register("mandrake", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.FEYWILD_TAB).food(
            new Food.Builder()
                    .nutrition(3)
                    .saturationMod(1.2f)
                    .effect(() -> new EffectInstance(Effects.BLINDNESS, 200, 0), 1)
                    .build())));
    //TODO Configurations

    public static final RegistryObject<Item> GIANT_SUN_FLOWER_SEED = ITEMS.register("giant_sun_flower_seed", () -> new GiantFlowerSeedItem((GiantFlowerBlock) ModBlocks.GIANT_SUN_FLOWER.get()));
    public static final RegistryObject<Item> GIANT_CROCUS_FLOWER_SEED = ITEMS.register("giant_crocus_flower_seed", () -> new GiantFlowerSeedItem((GiantFlowerBlock) ModBlocks.GIANT_CROCUS_FLOWER.get()));
    public static final RegistryObject<Item> GIANT_DANDELION_FLOWER_SEED = ITEMS.register("giant_dandelion_flower_seed", () -> new GiantFlowerSeedItem((GiantFlowerBlock) ModBlocks.GIANT_DANDELION_FLOWER.get()));

    public static void register(IEventBus eventBus) {ITEMS.register(eventBus);}
}
