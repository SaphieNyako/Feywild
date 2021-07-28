package com.feywild.feywild.item;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.util.Registration;
import com.feywild.feywild.util.configs.Config;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nonnull;

public class ModItems {

    /* ITEMS */

    public static final RegistryObject<Item> LESSER_FEY_GEM =
            Registration.ITEMS.register("lesser_fey_gem",
                    () -> new Item(new Item.Properties().tab(FeywildMod.FEYWILD_TAB)));

    public static final RegistryObject<Item> GREATER_FEY_GEM =
            Registration.ITEMS.register("greater_fey_gem",
                    () -> new Item(new Item.Properties().tab(FeywildMod.FEYWILD_TAB)));

    public static final RegistryObject<Item> SHINY_FEY_GEM =
            Registration.ITEMS.register("shiny_fey_gem",
                    () -> new Item(new Item.Properties().tab(FeywildMod.FEYWILD_TAB)));

    public static final RegistryObject<Item> BRILLIANT_FEY_GEM =
            Registration.ITEMS.register("brilliant_fey_gem",
                    () -> new Item(new Item.Properties().tab(FeywildMod.FEYWILD_TAB)));

    public static final RegistryObject<Item> FEY_INK_BOTTLE =
            Registration.ITEMS.register("fey_ink_bottle",
                    () -> new Item(new Item.Properties().tab(FeywildMod.FEYWILD_TAB)));

    public static final RegistryObject<Item> SUMMONING_SCROLL =
            Registration.ITEMS.register("summoning_scroll",
                    () -> new Item(new Item.Properties().tab(FeywildMod.FEYWILD_TAB)));

    /* BOOK */
    public static final RegistryObject<Item> FEYWILD_LEXICON =
            Registration.ITEMS.register("feywild_lexicon",
                    () -> new FeywildLexicon(new Item.Properties().tab(FeywildMod.FEYWILD_TAB)));

    /* FOOD ITEMS */
    public static final RegistryObject<Item> FEY_DUST =
            Registration.ITEMS.register("fey_dust",
                    () -> new FeyDust(new Item.Properties().tab(FeywildMod.FEYWILD_TAB)
                            .food(new Food.Builder()
                                    .effect(() -> new EffectInstance(Effects.LEVITATION, Config.FEY_DUST_DURATION.get(), 1), 1)
                                    .build())));
    //Can also give food item .hunger(5) .saturation(1.5f)

    public static final RegistryObject<Item> MANDRAKE =
            Registration.ITEMS.register("mandrake",
                    () -> new Mandrake(new Item.Properties().tab(FeywildMod.FEYWILD_TAB)
                            .food(new Food.Builder()
                                    .effect(() -> new EffectInstance(Effects.BLINDNESS, 200, 0), 1)
                                    .build())));

    public static final RegistryObject<Item> MANDRAKE_POTION =
            Registration.ITEMS.register("mandrake_potion",
                    () -> new MandrakePotion(new Item.Properties().tab(FeywildMod.FEYWILD_TAB).food(new Food.Builder().build())));

    //CROP ITEMS
    public static final RegistryObject<Item> MANDRAKE_SEED =
            Registration.ITEMS.register("mandrake_seed",
                    () -> new BlockItem(ModBlocks.MANDRAKE_CROP.get(), new Item.Properties().tab(FeywildMod.FEYWILD_TAB)));

    public static final RegistryObject<Item> SUNFLOWER_SEED =
            Registration.ITEMS.register("sunflower_seed", SunflowerSeeds::new
            );

    public static final RegistryObject<Item> DANDELION_SEED =
            Registration.ITEMS.register("dandelion_seed", DandelionSeeds::new
            );

    public static final RegistryObject<Item> CROCUS_SEED =
            Registration.ITEMS.register("crocus_seed", CrocusSeeds::new
            );

    /* MUSIC DISC */

    public static final RegistryObject<Item> MUSIC_DISC_FEYWILD =
            Registration.ITEMS.register("music_disc_feywild",
                    FeywildMusicDisc::new);

    /* SCHEMATICS */

    public static final RegistryObject<Item> SCHEMATICS_GEM_TRANSMUTATION =
            Registration.ITEMS.register("schematics_gem_transmutation",
                    () -> new Schematics(new Item.Properties().tab(FeywildMod.FEYWILD_TAB), new TranslationTextComponent("message.feywild.schematics_gem_transmutation")));

    public static final RegistryObject<Item> SCHEMATICS_FEY_ALTAR =
            Registration.ITEMS.register("schematics_fey_altar",
                    () -> new Schematics(new Item.Properties().tab(FeywildMod.FEYWILD_TAB), new TranslationTextComponent("message.feywild.schematics_fey_altar")));

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

    /* SUMMONING SCROLL */
    public static final RegistryObject<Item> SUMMONING_SCROLL_SPRING_PIXIE =
            Registration.ITEMS.register("summoning_scroll_spring_pixie",
                    SummoningScrollSpringPixie::new);
    public static final RegistryObject<Item> SUMMONING_SCROLL_SUMMER_PIXIE =
            Registration.ITEMS.register("summoning_scroll_summer_pixie",
                    SummoningScrollSummerPixie::new);
    public static final RegistryObject<Item> SUMMONING_SCROLL_AUTUMN_PIXIE =
            Registration.ITEMS.register("summoning_scroll_autumn_pixie",
                    SummoningScrollAutumnPixie::new);
    public static final RegistryObject<Item> SUMMONING_SCROLL_WINTER_PIXIE =
            Registration.ITEMS.register("summoning_scroll_winter_pixie",
                    SummoningScrollWinterPixie::new);
    public static final RegistryObject<Item> SUMMONING_SCROLL_DWARF_BLACKSMITH =
            Registration.ITEMS.register("summoning_scroll_dwarf_blacksmith",
                    SummoningScrollDwarfBlacksmith::new);

    private static <T extends Item> RegistryObject<T> registerBasedOnConfig(String name, T object, boolean shouldRegister) {
        if (shouldRegister) {
            return Registration.ITEMS.register(name, () -> object);
        }
        return null;
    }

    public static void register() {
    }

    public enum ModItemTier implements IItemTier {
        FEY(250, 3f, 5f, 2, 15, Ingredient.of(new ItemStack(ModItems.GREATER_FEY_GEM.get())));

        private final int maxUses;
        private final float efficiency;
        private final float attackDamage;
        private final int harvestLevel;
        private final int enchantability;
        private final Ingredient repairMaterial;

        ModItemTier(int maxUses, float efficiency, float attackDamage, int harvestLevel, int enchantability, Ingredient repairMaterial) {
            this.maxUses = maxUses;
            this.efficiency = efficiency;
            this.attackDamage = attackDamage;
            this.harvestLevel = harvestLevel;
            this.enchantability = enchantability;
            this.repairMaterial = repairMaterial;
        }

        @Override
        public int getUses() {
            return maxUses;
        }

        @Override
        public float getSpeed() {
            return efficiency;
        }

        @Override
        public float getAttackDamageBonus() {
            return attackDamage;
        }

        @Override
        public int getLevel() {
            return harvestLevel;
        }

        @Override
        public int getEnchantmentValue() {
            return enchantability;
        }

        @Nonnull
        @Override
        public Ingredient getRepairIngredient() {
            return repairMaterial;
        }
    }

    public enum ModArmorTier implements IArmorMaterial {
        FEY_ARMOR(50, new int[]{2, 7, 5, 3}, 10, SoundEvents.ARMOR_EQUIP_ELYTRA, Ingredient.of(new ItemStack(ModItems.GREATER_FEY_GEM.get())),
                FeywildMod.MOD_ID + ":fey_armor", 0, 1);

        private final int durability;
        private final int[] damageReductionAmountArray;
        private final int enchantability;
        private final SoundEvent soundEvent;
        private final Ingredient repairMaterial;
        private final String name;
        private final float toughness;
        private final float knockbackResistance;

        ModArmorTier(int durability, int[] damageReductionAmountArray, int enchantability, SoundEvent soundEvent, Ingredient repairMaterial, String name, float toughness, float knockbackResistance) {
            this.durability = durability;
            this.damageReductionAmountArray = damageReductionAmountArray;
            this.enchantability = enchantability;
            this.soundEvent = soundEvent;
            this.repairMaterial = repairMaterial;
            this.name = name;
            this.toughness = toughness;
            this.knockbackResistance = knockbackResistance;
        }

        @Override
        public int getDurabilityForSlot(@Nonnull EquipmentSlotType slotIn) {
            return durability;
        }

        @Override
        public int getDefenseForSlot(EquipmentSlotType slotIn) {
            return damageReductionAmountArray[slotIn.getIndex()];
        }

        @Override
        public int getEnchantmentValue() {
            return enchantability;
        }

        @Nonnull
        @Override
        public SoundEvent getEquipSound() {
            return soundEvent;
        }

        @Nonnull
        @Override
        public Ingredient getRepairIngredient() {
            return repairMaterial;
        }

        @Nonnull
        @Override
        public String getName() {
            return name;
        }

        @Override
        public float getToughness() {
            return toughness;
        }

        @Override
        public float getKnockbackResistance() {
            return knockbackResistance;
        }
    }

}
