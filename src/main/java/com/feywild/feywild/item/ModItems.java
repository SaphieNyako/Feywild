package com.feywild.feywild.item;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.config.MiscConfig;
import com.feywild.feywild.entity.*;
import com.feywild.feywild.sound.ModSoundEvents;
import io.github.noeppi_noeppi.libx.annotation.RegisterClass;
import io.github.noeppi_noeppi.libx.mod.registration.ItemBase;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.TranslationTextComponent;

@RegisterClass
public class ModItems {

    public static final Item lesserFeyGem = new ItemBase(FeywildMod.getInstance(), new Item.Properties());
    public static final Item greaterFeyGem = new ItemBase(FeywildMod.getInstance(), new Item.Properties());
    public static final Item shinyFeyGem = new ItemBase(FeywildMod.getInstance(), new Item.Properties());
    public static final Item brilliantFeyGem = new ItemBase(FeywildMod.getInstance(), new Item.Properties());
    public static final Item feyInkBottle = new ItemBase(FeywildMod.getInstance(), new Item.Properties());
    public static final Item summoningScroll = new ItemBase(FeywildMod.getInstance(), new Item.Properties());
    public static final Item feywildLexicon = new FeywildLexicon(FeywildMod.getInstance(), new Item.Properties());

    //Can also give food item .hunger(5) .saturation(1.5f)
    public static final FeyDust feyDust = new FeyDust(FeywildMod.getInstance(), new Item.Properties().food(
            new Food.Builder().effect(
                    () -> new EffectInstance(Effects.LEVITATION, MiscConfig.fey_dust_ticks, 1), 1).build()));

    public static final Item mandrake = new TooltipItem(FeywildMod.getInstance(), new Item.Properties().food(
            new Food.Builder()
                    .effect(() -> new EffectInstance(Effects.BLINDNESS, 200, 0), 1)
                    .build()
    ), new TranslationTextComponent("message.feywild.mandrake"));

    public static final MandrakePotion mandrakePotion = new MandrakePotion(FeywildMod.getInstance(), new Item.Properties().food(new Food.Builder().build()));

    public static final FeywildMusicDisc feywildMusicDisc = new FeywildMusicDisc();

    public static final Item schematicsGemTransmutation = new Schematics(FeywildMod.getInstance(), new Item.Properties(), new TranslationTextComponent("message.feywild.schematics_gem_transmutation"));
    public static final Item schematicsFeyAltar = new Schematics(FeywildMod.getInstance(), new Item.Properties(), new TranslationTextComponent("message.feywild.schematics_fey_altar"));

    public static final MarketScroll marketScroll = new MarketScroll(FeywildMod.getInstance(),new Item.Properties().stacksTo(1));

    public static final SummoningScrollDwarfBlacksmith summoningScrollDwarfBlacksmith = new SummoningScrollDwarfBlacksmith(FeywildMod.getInstance(), ModEntityTypes.dwarfBlacksmith, new Item.Properties());
    public static final SummoningScrollFey<SpringPixieEntity> summoningScrollSpringPixie = new SummoningScrollFey<>(FeywildMod.getInstance(), ModEntityTypes.springPixie, ModSoundEvents.summoningSpringPixie, new Item.Properties());
    public static final SummoningScrollFey<SummerPixieEntity> summoningScrollSummerPixie = new SummoningScrollFey<>(FeywildMod.getInstance(), ModEntityTypes.summerPixie, ModSoundEvents.summoningSummerPixie, new Item.Properties());
    public static final SummoningScrollFey<AutumnPixieEntity> summoningScrollAutumnPixie = new SummoningScrollFey<>(FeywildMod.getInstance(), ModEntityTypes.autumnPixie, ModSoundEvents.summoningAutumnPixie, new Item.Properties());
    public static final SummoningScrollFey<WinterPixieEntity> summoningScrollWinterPixie = new SummoningScrollFey<>(FeywildMod.getInstance(), ModEntityTypes.winterPixie, ModSoundEvents.summoningWinterPixie, new Item.Properties());

    /* QUEST ITEMS
    public static final RegistryObject<Item> FEY_SHEEP_DROPPINGS =
            Registration.ITEMS.register("fey_sheep_droppings",
                    () -> new Item(new Item.Properties().tab(FeywildMod.FEYWILD_TAB))); */

    /* TOOLS

    public static final RegistryObject<Item> FEY_SHOVEL =
            Registration.ITEMS.register("fey_shovel",
                    () -> new ShovelItem(ModItemTier.FEY,  0f, 0f,
                    new Item.Properties()
                        .tab(FeywildMod.FEYWILD_TAB)
                        .addToolType(ToolType.SHOVEL, 2)
                        .defaultDurability(150)));

    public static final RegistryObject<Item> FEY_HOE =
            Registration.ITEMS.register("fey_hoe",
                    () -> new HoeItem(ModItemTier.FEY, 0, 0,
                    new Item.Properties()
                    .tab(FeywildMod.FEYWILD_TAB)
                    .addToolType(ToolType.HOE, 2)
                    .defaultDurability(150))); */

    /* ARMOR

    public static final RegistryObject<Item> FEY_ARMOR_HELMET =
            Registration.ITEMS.register("fey_armor_helmet",
                    () -> new ArmorItem(ModArmorTier.FEY_ARMOR, EquipmentSlotType.HEAD, new Item.Properties()
                    .tab(FeywildMod.FEYWILD_TAB)));

    public static final RegistryObject<Item> FEY_ARMOR_CHEST =
            Registration.ITEMS.register("fey_armor_chest",
                    () -> new ArmorItem(ModArmorTier.FEY_ARMOR, EquipmentSlotType.CHEST, new Item.Properties()
                            .tab(FeywildMod.FEYWILD_TAB)));

    public static final RegistryObject<Item> FEY_ARMOR_LEGGINGS =
            Registration.ITEMS.register("fey_armor_leggings",
                    () -> new ArmorItem(ModArmorTier.FEY_ARMOR, EquipmentSlotType.LEGS, new Item.Properties()
                            .tab(FeywildMod.FEYWILD_TAB)));

    public static final RegistryObject<Item> FEY_ARMOR_BOOTS=
            Registration.ITEMS.register("fey_armor_boots",
                    () -> new ArmorItem(ModArmorTier.FEY_ARMOR, EquipmentSlotType.FEET, new Item.Properties()
                            .tab(FeywildMod.FEYWILD_TAB)));  */

//    public enum ModItemTier implements IItemTier {
//        FEY(250, 3f, 5f, 2, 15, Ingredient.of(new ItemStack(ModItems.GREATER_FEY_GEM.get())));
//
//        private final int maxUses;
//        private final float efficiency;
//        private final float attackDamage;
//        private final int harvestLevel;
//        private final int enchantability;
//        private final Ingredient repairMaterial;
//
//        ModItemTier(int maxUses, float efficiency, float attackDamage, int harvestLevel, int enchantability, Ingredient repairMaterial) {
//            this.maxUses = maxUses;
//            this.efficiency = efficiency;
//            this.attackDamage = attackDamage;
//            this.harvestLevel = harvestLevel;
//            this.enchantability = enchantability;
//            this.repairMaterial = repairMaterial;
//        }
//
//        @Override
//        public int getUses() {
//            return maxUses;
//        }
//
//        @Override
//        public float getSpeed() {
//            return efficiency;
//        }
//
//        @Override
//        public float getAttackDamageBonus() {
//            return attackDamage;
//        }
//
//        @Override
//        public int getLevel() {
//            return harvestLevel;
//        }
//
//        @Override
//        public int getEnchantmentValue() {
//            return enchantability;
//        }
//
//        @Nonnull
//        @Override
//        public Ingredient getRepairIngredient() {
//            return repairMaterial;
//        }
//    }
//
//    public enum ModArmorTier implements IArmorMaterial {
//        FEY_ARMOR(50, new int[]{2, 7, 5, 3}, 10, SoundEvents.ARMOR_EQUIP_ELYTRA, Ingredient.of(new ItemStack(ModItems.GREATER_FEY_GEM.get())),
//                FeywildMod.MOD_ID + ":fey_armor", 0, 1);
//
//        private final int durability;
//        private final int[] damageReductionAmountArray;
//        private final int enchantability;
//        private final SoundEvent soundEvent;
//        private final Ingredient repairMaterial;
//        private final String name;
//        private final float toughness;
//        private final float knockbackResistance;
//
//        ModArmorTier(int durability, int[] damageReductionAmountArray, int enchantability, SoundEvent soundEvent, Ingredient repairMaterial, String name, float toughness, float knockbackResistance) {
//            this.durability = durability;
//            this.damageReductionAmountArray = damageReductionAmountArray;
//            this.enchantability = enchantability;
//            this.soundEvent = soundEvent;
//            this.repairMaterial = repairMaterial;
//            this.name = name;
//            this.toughness = toughness;
//            this.knockbackResistance = knockbackResistance;
//        }
//
//        @Override
//        public int getDurabilityForSlot(@Nonnull EquipmentSlotType slotIn) {
//            return durability;
//        }
//
//        @Override
//        public int getDefenseForSlot(EquipmentSlotType slotIn) {
//            return damageReductionAmountArray[slotIn.getIndex()];
//        }
//
//        @Override
//        public int getEnchantmentValue() {
//            return enchantability;
//        }
//
//        @Nonnull
//        @Override
//        public SoundEvent getEquipSound() {
//            return soundEvent;
//        }
//
//        @Nonnull
//        @Override
//        public Ingredient getRepairIngredient() {
//            return repairMaterial;
//        }
//
//        @Nonnull
//        @Override
//        public String getName() {
//            return name;
//        }
//
//        @Override
//        public float getToughness() {
//            return toughness;
//        }
//
//        @Override
//        public float getKnockbackResistance() {
//            return knockbackResistance;
//        }
//    }
}
