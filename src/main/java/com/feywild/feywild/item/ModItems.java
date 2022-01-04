package com.feywild.feywild.item;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.config.MiscConfig;
import com.feywild.feywild.entity.*;
import com.feywild.feywild.sound.ModSoundEvents;
import io.github.noeppi_noeppi.libx.annotation.registration.RegisterClass;
import io.github.noeppi_noeppi.libx.base.ItemBase;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

@RegisterClass
public class ModItems {

    public static final Item lesserFeyGem = new ItemBase(FeywildMod.getInstance(), new Item.Properties());
    public static final Item greaterFeyGem = new ItemBase(FeywildMod.getInstance(), new Item.Properties());
    public static final Item shinyFeyGem = new ItemBase(FeywildMod.getInstance(), new Item.Properties());
    public static final Item brilliantFeyGem = new ItemBase(FeywildMod.getInstance(), new Item.Properties());
    public static final Item feyInkBottle = new ItemBase(FeywildMod.getInstance(), new Item.Properties());
    public static final Item summoningScroll = new EmptyScroll(FeywildMod.getInstance(), new Item.Properties());
    public static final Item feywildLexicon = new FeywildLexicon(FeywildMod.getInstance(), new Item.Properties());
    public static final Item honeycomb = new ItemBase(FeywildMod.getInstance(), new Item.Properties());
    public static final Item inactiveMarketRuneStone = new TooltipItem(FeywildMod.getInstance(), new Item.Properties().stacksTo(1), new TranslatableComponent("message.feywild.inactive_market_rune_stone"));
    public static final MarketRuneStone marketRuneStone = new MarketRuneStone(FeywildMod.getInstance(), new Item.Properties().stacksTo(1));
    public static final FeywildMusicDisc feywildMusicDisc = new FeywildMusicDisc();
    public static final Item schematicsGemTransmutation = new Schematics(FeywildMod.getInstance(), new Item.Properties(), new TranslatableComponent("message.feywild.schematics_gem_transmutation"));
    public static final Item schematicsElementalRuneCrafting = new Schematics(FeywildMod.getInstance(), new Item.Properties(), new TranslatableComponent("message.feywild.schematics_elemental_rune_crafting"));
    public static final Item schematicsSeasonalRuneCrafting = new Schematics(FeywildMod.getInstance(), new Item.Properties(), new TranslatableComponent("message.feywild.schematics_seasonal_rune_crafting"));
    public static final Item schematicsDeadlyRuneCrafting = new Schematics(FeywildMod.getInstance(), new Item.Properties(), new TranslatableComponent("message.feywild.schematics_deadly_rune_crafting"));
    public static final Item schematicsYggdrasilRuneCrafting = new Schematics(FeywildMod.getInstance(), new Item.Properties(), new TranslatableComponent("message.feywild.schematics_yggdrasil_rune_crafting"));
    public static final SummoningScrollDwarfBlacksmith summoningScrollDwarfBlacksmith = new SummoningScrollDwarfBlacksmith(FeywildMod.getInstance(), ModEntityTypes.dwarfBlacksmith, new Item.Properties());
    public static final SummoningScrollFey<SpringPixie> summoningScrollSpringPixie = new SummoningScrollFey<>(FeywildMod.getInstance(), ModEntityTypes.springPixie, ModSoundEvents.summoningSpringPixieShort, new Item.Properties());
    public static final SummoningScrollFey<SummerPixie> summoningScrollSummerPixie = new SummoningScrollFey<>(FeywildMod.getInstance(), ModEntityTypes.summerPixie, ModSoundEvents.summoningSummerPixieShort, new Item.Properties());
    public static final SummoningScrollFey<AutumnPixie> summoningScrollAutumnPixie = new SummoningScrollFey<>(FeywildMod.getInstance(), ModEntityTypes.autumnPixie, ModSoundEvents.summoningAutumnPixieShort, new Item.Properties());
    public static final SummoningScrollFey<WinterPixie> summoningScrollWinterPixie = new SummoningScrollFey<>(FeywildMod.getInstance(), ModEntityTypes.winterPixie, ModSoundEvents.summoningWinterPixieShort, new Item.Properties());
    public static final SummoningScrollFey<BeeKnight> summoningScrollBeeKnight = new SummoningScrollFey<>(FeywildMod.getInstance(), ModEntityTypes.beeKnight, null, new Item.Properties());
    public static final SummoningScrollFey<Mandragora> summoningScrollMandragora = new SummoningScrollFey<>(FeywildMod.getInstance(), ModEntityTypes.mandragora, null, new Item.Properties());
    public static final SummoningScrollFey<Shroomling> summoningScrollShroomling = new SummoningScrollFey<>(FeywildMod.getInstance(), ModEntityTypes.shroomling, null, new Item.Properties());

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
                    .build()), new TranslatableComponent("message.feywild.magical_honey_cookie"));
}
