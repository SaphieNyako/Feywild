package com.feywild.feywild.item;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.config.MiscConfig;
import com.feywild.feywild.entity.*;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import org.moddingx.libx.annotation.registration.RegisterClass;
import org.moddingx.libx.base.ItemBase;

@RegisterClass(registry = "ITEM_REGISTRY")
public class ModItems {

    public static final Item lesserFeyGem = new ItemBase(FeywildMod.getInstance(), new Item.Properties());
    public static final Item greaterFeyGem = new ItemBase(FeywildMod.getInstance(), new Item.Properties());
    public static final Item shinyFeyGem = new ItemBase(FeywildMod.getInstance(), new Item.Properties());
    public static final Item brilliantFeyGem = new ItemBase(FeywildMod.getInstance(), new Item.Properties());
    public static final Item feyInkBottle = new ItemBase(FeywildMod.getInstance(), new Item.Properties());
    public static final Item summoningScroll = new EmptyScroll(FeywildMod.getInstance(), new Item.Properties());
    public static final Item feywildLexicon = new FeywildLexicon(FeywildMod.getInstance(), new Item.Properties());
    public static final Item honeycomb = new ItemBase(FeywildMod.getInstance(), new Item.Properties());
    public static final Item inactiveMarketRuneStone = new TooltipItem(FeywildMod.getInstance(), new Item.Properties().stacksTo(1), Component.translatable("message.feywild.inactive_market_rune_stone"));
    public static final MarketRuneStone marketRuneStone = new MarketRuneStone(FeywildMod.getInstance(), new Item.Properties().stacksTo(1));
    public static final TeleportationOrb teleportationOrb = new TeleportationOrb(FeywildMod.getInstance(), new Item.Properties().stacksTo(1));
    public static final PixieOrb pixieOrb = new PixieOrb(FeywildMod.getInstance(), new Item.Properties().stacksTo(1));
    public static final FeywildMusicDisc feywildMusicDisc = new FeywildMusicDisc();
    public static final Item schematicsGemTransmutation = new Schematics(FeywildMod.getInstance(), new Item.Properties(), Component.translatable("message.feywild.schematics_gem_transmutation"));
    public static final Item schematicsElementalRuneCrafting = new Schematics(FeywildMod.getInstance(), new Item.Properties(), Component.translatable("message.feywild.schematics_elemental_rune_crafting"));
    public static final Item schematicsSeasonalRuneCrafting = new Schematics(FeywildMod.getInstance(), new Item.Properties(), Component.translatable("message.feywild.schematics_seasonal_rune_crafting"));
    public static final Item schematicsDeadlyRuneCrafting = new Schematics(FeywildMod.getInstance(), new Item.Properties(), Component.translatable("message.feywild.schematics_deadly_rune_crafting"));
    public static final Item schematicsYggdrasilRuneCrafting = new Schematics(FeywildMod.getInstance(), new Item.Properties(), Component.translatable("message.feywild.schematics_yggdrasil_rune_crafting"));
    public static final SummoningScrollDwarfBlacksmith summoningScrollDwarfBlacksmith = new SummoningScrollDwarfBlacksmith(FeywildMod.getInstance(), ModEntities.dwarfBlacksmith, new Item.Properties());
    public static final SummoningScrollFey<SpringPixie> summoningScrollSpringPixie = new SummoningScrollFey<>(FeywildMod.getInstance(), ModEntities.springPixie, ModSoundEvents.summoningSpringPixieShort, new Item.Properties());
    public static final SummoningScrollFey<SummerPixie> summoningScrollSummerPixie = new SummoningScrollFey<>(FeywildMod.getInstance(), ModEntities.summerPixie, ModSoundEvents.summoningSummerPixieShort, new Item.Properties());
    public static final SummoningScrollFey<AutumnPixie> summoningScrollAutumnPixie = new SummoningScrollFey<>(FeywildMod.getInstance(), ModEntities.autumnPixie, ModSoundEvents.summoningAutumnPixieShort, new Item.Properties());
    public static final SummoningScrollFey<WinterPixie> summoningScrollWinterPixie = new SummoningScrollFey<>(FeywildMod.getInstance(), ModEntities.winterPixie, ModSoundEvents.summoningWinterPixieShort, new Item.Properties());
    public static final SummoningScrollFey<BeeKnight> summoningScrollBeeKnight = new SummoningScrollFey<>(FeywildMod.getInstance(), ModEntities.beeKnight, null, new Item.Properties());
    public static final SummoningScrollFey<Mandragora> summoningScrollMandragora = new SummoningScrollFey<>(FeywildMod.getInstance(), ModEntities.mandragora, null, new Item.Properties());
    public static final SummoningScrollFey<Shroomling> summoningScrollShroomling = new SummoningScrollFey<>(FeywildMod.getInstance(), ModEntities.shroomling, null, new Item.Properties());

    /* Geckolib registration requires Item registration */
    public static final Item feyAltar = new FeyAltarItem(ModBlocks.feyAltar, new Item.Properties().tab(FeywildMod.getInstance().tab));

    @SuppressWarnings("ConstantConditions")
    public static final Item spawnEggSpringBotaniaPixie = new ForgeSpawnEggItem(() -> ModEntities.springBotaniaPixie, 0xCA73CA, 0x0EAC0A, new Item.Properties().tab(FeywildMod.getInstance().tab));
    public static final Item spawnEggSummerBotaniaPixie = new ForgeSpawnEggItem(() -> ModEntities.summerBotaniaPixie, 0xCA73CA, 0xE3C53D, new Item.Properties().tab(FeywildMod.getInstance().tab));
    public static final Item spawnEggAutumnBotaniaPixie = new ForgeSpawnEggItem(() -> ModEntities.autumnBotaniaPixie, 0xCA73CA, 0xD87A0E, new Item.Properties().tab(FeywildMod.getInstance().tab));
    public static final Item spawnEggWinterBotaniaPixie = new ForgeSpawnEggItem(() -> ModEntities.winterBotaniaPixie, 0xCA73CA, 0x0EC2E9, new Item.Properties().tab(FeywildMod.getInstance().tab));

    /* FOOD */
    public static final FeyDust feyDust = new FeyDust(FeywildMod.getInstance(), new Item.Properties().food(
            new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.LEVITATION, MiscConfig.fey_dust_ticks, 1), 1).build()));

    public static final Item mandrake = new Mandrake(FeywildMod.getInstance(), new Item.Properties().food(
            new FoodProperties.Builder()
                    .nutrition(3)
                    .saturationMod(1.2f)
                    .effect(() -> new MobEffectInstance(MobEffects.BLINDNESS, 200, 0), 1)
                    .build()));

    public static final MandrakePotion mandrakePotion = new MandrakePotion(FeywildMod.getInstance(), new Item.Properties().food(
            new FoodProperties.Builder().build()));

    public static final Item magicalHoneyCookie = new TooltipItem(FeywildMod.getInstance(), new Item.Properties().food(
            new FoodProperties.Builder()
                    .nutrition(5)
                    .saturationMod(2.0f)
                    .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 900, 0), 1)
                    .build()), Component.translatable("message.feywild.magical_honey_cookie"));
}
