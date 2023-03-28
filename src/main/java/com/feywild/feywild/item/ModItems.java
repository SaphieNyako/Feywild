package com.feywild.feywild.item;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.config.MiscConfig;
import com.feywild.feywild.entity.*;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import org.moddingx.libx.annotation.registration.RegisterClass;
import org.moddingx.libx.base.ItemBase;

@RegisterClass(registry = "ITEM_REGISTRY")
public class ModItems {

    public static final Item lesserFeyGem = new ItemBase(FeywildMod.getInstance(), new Item.Properties());
    public static final Item greaterFeyGem = new ItemBase(FeywildMod.getInstance(), new Item.Properties());
    public static final Item shinyFeyGem = new ItemBase(FeywildMod.getInstance(), new Item.Properties());
    public static final Item brilliantFeyGem = new ItemBase(FeywildMod.getInstance(), new Item.Properties());
    public static final Item rawElvenQuartz = new ItemBase(FeywildMod.getInstance(), new Item.Properties());
    public static final Item rawAutumnElvenQuartz = new ItemBase(FeywildMod.getInstance(), new Item.Properties());
    public static final Item rawSpringElvenQuartz = new ItemBase(FeywildMod.getInstance(), new Item.Properties());
    public static final Item rawSummerElvenQuartz = new ItemBase(FeywildMod.getInstance(), new Item.Properties());
    public static final Item rawWinterElvenQuartz = new ItemBase(FeywildMod.getInstance(), new Item.Properties());
    public static final Item feyInkBottle = new ItemBase(FeywildMod.getInstance(), new Item.Properties());
    public static final Item soulShard = new ItemBase(FeywildMod.getInstance(), new Item.Properties());
    public static final SoulGem soulGem = new SoulGem(FeywildMod.getInstance(), new Item.Properties());
    public static final Item summoningScroll = new EmptyScroll(FeywildMod.getInstance(), new Item.Properties());
    public static final Item feywildLexicon = new FeywildLexicon(FeywildMod.getInstance(), new Item.Properties());
    public static final Item honeycomb = new ItemBase(FeywildMod.getInstance(), new Item.Properties());
    public static final Item inactiveMarketRuneStone = new TooltipItem(FeywildMod.getInstance(), new Item.Properties().stacksTo(1), Component.translatable("message.feywild.inactive_market_rune_stone"));
    public static final MarketRuneStone marketRuneStone = new MarketRuneStone(FeywildMod.getInstance(), new Item.Properties().stacksTo(1));
    public static final TeleportationOrb teleportationOrb = new TeleportationOrb(FeywildMod.getInstance(), new Item.Properties().stacksTo(1));
    public static final PixieOrb pixieOrb = new PixieOrb(FeywildMod.getInstance(), new Item.Properties().stacksTo(1));
    public static final FeywildMusicDisc feywildMusicDisc = new FeywildMusicDisc();
    public static final Item schematicsGemTransmutation = new Schematics(FeywildMod.getInstance(), new Item.Properties(), Component.translatable("message.feywild.schematics_gem_transmutation"));
    // public static final Item schematicsElementalRuneCrafting = new Schematics(FeywildMod.getInstance(), new Item.Properties(), Component.translatable("message.feywild.schematics_elemental_rune_crafting"));
    // public static final Item schematicsSeasonalRuneCrafting = new Schematics(FeywildMod.getInstance(), new Item.Properties(), Component.translatable("message.feywild.schematics_seasonal_rune_crafting"));
    // public static final Item schematicsDeadlyRuneCrafting = new Schematics(FeywildMod.getInstance(), new Item.Properties(), Component.translatable("message.feywild.schematics_deadly_rune_crafting"));
    // public static final Item schematicsYggdrasilRuneCrafting = new Schematics(FeywildMod.getInstance(), new Item.Properties(), Component.translatable("message.feywild.schematics_yggdrasil_rune_crafting"));
    public static final SummoningScrollDwarfBlacksmith summoningScrollDwarfBlacksmith = new SummoningScrollDwarfBlacksmith(FeywildMod.getInstance(), ModEntities.dwarfBlacksmith, new Item.Properties());
    public static final SummoningScrollFey<SpringPixie> summoningScrollSpringPixie = new SummoningScrollFey<>(FeywildMod.getInstance(), ModEntities.springPixie, ModSoundEvents.summoningSpringPixieShort, new Item.Properties());
    public static final SummoningScrollFey<SummerPixie> summoningScrollSummerPixie = new SummoningScrollFey<>(FeywildMod.getInstance(), ModEntities.summerPixie, ModSoundEvents.summoningSummerPixieShort, new Item.Properties());
    public static final SummoningScrollFey<AutumnPixie> summoningScrollAutumnPixie = new SummoningScrollFey<>(FeywildMod.getInstance(), ModEntities.autumnPixie, ModSoundEvents.summoningAutumnPixieShort, new Item.Properties());
    public static final SummoningScrollFey<WinterPixie> summoningScrollWinterPixie = new SummoningScrollFey<>(FeywildMod.getInstance(), ModEntities.winterPixie, ModSoundEvents.summoningWinterPixieShort, new Item.Properties());
    public static final SummoningScrollFey<BeeKnight> summoningScrollBeeKnight = new SummoningScrollFey<>(FeywildMod.getInstance(), ModEntities.beeKnight, null, new Item.Properties());
    public static final SummoningScrollFey<Mandragora> summoningScrollMandragora = new SummoningScrollFey<>(FeywildMod.getInstance(), ModEntities.mandragora, null, new Item.Properties());
    public static final SummoningScrollFey<Shroomling> summoningScrollShroomling = new SummoningScrollFey<>(FeywildMod.getInstance(), ModEntities.shroomling, null, new Item.Properties());
    public static final SummoningScrollAllay<Allay> summoningScrollAllay = new SummoningScrollAllay<>(FeywildMod.getInstance(), EntityType.ALLAY, null, new Item.Properties());

    public static final SummoningScrollTreeEnt<SpringTreeEnt> summoningScrollSpringTreeEnt = new SummoningScrollTreeEnt<>(FeywildMod.getInstance(), ModEntities.springTreeEnt, null, new Item.Properties(), Alignment.SPRING);
    public static final SummoningScrollTreeEnt<SummerTreeEnt> summoningScrollSummerTreeEnt = new SummoningScrollTreeEnt<>(FeywildMod.getInstance(), ModEntities.summerTreeEnt, null, new Item.Properties(), Alignment.SUMMER);
    public static final SummoningScrollTreeEnt<WinterTreeEnt> summoningScrollWinterTreeEnt = new SummoningScrollTreeEnt<>(FeywildMod.getInstance(), ModEntities.winterTreeEnt, null, new Item.Properties(), Alignment.WINTER);
    public static final SummoningScrollTreeEnt<HexenTreeEnt> summoningScrollHexenTreeEnt = new SummoningScrollTreeEnt<>(FeywildMod.getInstance(), ModEntities.hexenTreeEnt, null, new Item.Properties(), null);
    public static final SummoningScrollTreeEnt<BlossomTreeEnt> summoningScrollBlossomTreeEnt = new SummoningScrollTreeEnt<>(FeywildMod.getInstance(), ModEntities.blossomTreeEnt, null, new Item.Properties(), null);

    public static final SummoningScrollBoss<Mab> summoningScrollMab = new SummoningScrollBoss<>(FeywildMod.getInstance(), ModEntities.mab, ModSoundEvents.mabAmbience, new Item.Properties());
    public static final SummoningScrollBoss<Titania> summoningScrollTitania = new SummoningScrollBoss<>(FeywildMod.getInstance(), ModEntities.titania, ModSoundEvents.titaniaAmbience, new Item.Properties());

    /* Geckolib registration requires Item registration */
    public static final Item feyAltar = new FeyAltarItem(ModBlocks.feyAltar, new Item.Properties().tab(FeywildMod.getInstance().tab));

    public static final Item reaperScythe = new ReaperScythe(new Item.Properties().tab(FeywildMod.getInstance().tab));

    public static final Item feyWingsAutumn = new FeyWing(ModArmorMaterials.FEY_WINGS, EquipmentSlot.CHEST, new Item.Properties().tab(FeywildMod.getInstance().tab), FeyWing.Variant.AUTUMN);
    public static final Item feyWingsBlossom = new FeyWing(ModArmorMaterials.FEY_WINGS, EquipmentSlot.CHEST, new Item.Properties().tab(FeywildMod.getInstance().tab), FeyWing.Variant.BLOSSOM);
    public static final Item feyWingsLight = new FeyWing(ModArmorMaterials.FEY_WINGS, EquipmentSlot.CHEST, new Item.Properties().tab(FeywildMod.getInstance().tab), FeyWing.Variant.LIGHT);
    public static final Item feyWingsSpring = new FeyWing(ModArmorMaterials.FEY_WINGS, EquipmentSlot.CHEST, new Item.Properties().tab(FeywildMod.getInstance().tab), FeyWing.Variant.SPRING);
    public static final Item feyWingsSummer = new FeyWing(ModArmorMaterials.FEY_WINGS, EquipmentSlot.CHEST, new Item.Properties().tab(FeywildMod.getInstance().tab), FeyWing.Variant.SUMMER);
    public static final Item feyWingsWinter = new FeyWing(ModArmorMaterials.FEY_WINGS, EquipmentSlot.CHEST, new Item.Properties().tab(FeywildMod.getInstance().tab), FeyWing.Variant.WINTER);

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
