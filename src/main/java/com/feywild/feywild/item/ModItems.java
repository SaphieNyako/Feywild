package com.feywild.feywild.item;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.entity.ModEntityTypes;
import com.feywild.feywild.entity.SpringPixieEntity;
import com.feywild.feywild.util.Config;
import com.feywild.feywild.util.Registration;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.feywild.feywild.entity.ModEntityTypes.*;

public class ModItems {

    //GEMS


    public static final RegistryObject<Item> LESSER_FEY_GEM =
            Registration.ITEMS.register("lesser_fey_gem",
                    () -> new Item(new Item.Properties().group(FeywildMod.FEYWILD_TAB)));

    public static final RegistryObject<Item> GREATER_FEY_GEM =
            Registration.ITEMS.register("greater_fey_gem",
                    () -> new Item(new Item.Properties().group(FeywildMod.FEYWILD_TAB)));

    public static final RegistryObject<Item> SHINY_FEY_GEM =
            Registration.ITEMS.register("shiny_fey_gem",
                    () -> new Item(new Item.Properties().group(FeywildMod.FEYWILD_TAB)));

    public static final RegistryObject<Item> BRILLIANT_FEY_GEM =
            Registration.ITEMS.register("brilliant_fey_gem",
                    () -> new Item(new Item.Properties().group(FeywildMod.FEYWILD_TAB)));

    //FOOD ITEMS
    public static final RegistryObject<Item> FEY_DUST =
            Registration.ITEMS.register("fey_dust",
                    () -> new FeyDust(new Item.Properties().group(FeywildMod.FEYWILD_TAB)
                            .food(new Food.Builder()
                            .effect(() -> new EffectInstance(Effects.LEVITATION, Config.FEY_DUST_DURATION.get() , 1), 1 )
                            .build())));
    //FeyDust requires (Properties properties)
    //Can also give food item .hunger(5) .saturation(1.5f)
    //Config.FEY_DUST_DURATION.get() is to get the config file values

    public static final RegistryObject<Item> MANDRAKE =
            Registration.ITEMS.register("mandrake",
                    () -> new Mandrake(new Item.Properties().group(FeywildMod.FEYWILD_TAB)
                            .food(new Food.Builder()
                                    .effect(() -> new EffectInstance(Effects.BLINDNESS, 200, 0), 1 )
                                    .build())));


    //CROP ITEMS
    public static final RegistryObject<Item> MANDRAKE_SEED=
            Registration.ITEMS.register("mandrake_seed",
                    () -> new BlockItem(ModBlocks.MANDRAKE_CROP.get(), new Item.Properties().group(FeywildMod.FEYWILD_TAB)));




    //QUEST ITEMS
    public static final RegistryObject<Item> FEY_SHEEP_DROPPINGS =
            Registration.ITEMS.register("fey_sheep_droppings",
                    () -> new Item(new Item.Properties().group(FeywildMod.FEYWILD_TAB)));

    /* TOOLS */

    public static final RegistryObject<Item> FEY_SHOVEL =
            Registration.ITEMS.register("fey_shovel",
                    () -> new ShovelItem(ModItemTier.FEY,  0f, 0f,
                    new Item.Properties()
                        .group(FeywildMod.FEYWILD_TAB)
                        .addToolType(ToolType.SHOVEL, 2)
                        .defaultMaxDamage(150)));

    public static final RegistryObject<Item> FEY_HOE =
            Registration.ITEMS.register("fey_hoe",
                    () -> new HoeItem(ModItemTier.FEY, 0, 0,
                    new Item.Properties()
                    .group(FeywildMod.FEYWILD_TAB)
                    .addToolType(ToolType.HOE, 2)
                    .defaultMaxDamage(150)));

    /* ARMOR */

    public static final RegistryObject<Item> FEY_ARMOR_HELMET =
            Registration.ITEMS.register("fey_armor_helmet",
                    () -> new ArmorItem(ModArmorTier.FEY_ARMOR, EquipmentSlotType.HEAD, new Item.Properties()
                    .group(FeywildMod.FEYWILD_TAB)));

    public static final RegistryObject<Item> FEY_ARMOR_CHEST =
            Registration.ITEMS.register("fey_armor_chest",
                    () -> new ArmorItem(ModArmorTier.FEY_ARMOR, EquipmentSlotType.CHEST, new Item.Properties()
                            .group(FeywildMod.FEYWILD_TAB)));

    public static final RegistryObject<Item> FEY_ARMOR_LEGGINGS =
            Registration.ITEMS.register("fey_armor_leggings",
                    () -> new ArmorItem(ModArmorTier.FEY_ARMOR, EquipmentSlotType.LEGS, new Item.Properties()
                            .group(FeywildMod.FEYWILD_TAB)));

    public static final RegistryObject<Item> FEY_ARMOR_BOOTS=
            Registration.ITEMS.register("fey_armor_boots",
                    () -> new ArmorItem(ModArmorTier.FEY_ARMOR, EquipmentSlotType.FEET, new Item.Properties()
                            .group(FeywildMod.FEYWILD_TAB)));

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



    /* SPAWN EGGS

    public static final RegistryObject<ModSpawnEggItem> SPRING_PIXIE_SPAWNING_EGG = Registration.ITEMS.register("spring_pixie_spawn_egg",
            () -> new ModSpawnEggItem(ModEntityTypes.SPRING_PIXIE, 0x99cc99, 0x9966cc,
                    new Item.Properties().group(FeywildMod.FEYWILD_TAB)));

    public static final RegistryObject<ModSpawnEggItem> AUTUMN_PIXIE_SPAWNING_EGG = Registration.ITEMS.register("autumn_pixie_spawn_egg",
            () -> new ModSpawnEggItem(ModEntityTypes.AUTUMN_PIXIE, 0xcc3333, 0x993333,
                    new Item.Properties().group(FeywildMod.FEYWILD_TAB)));

    public static final RegistryObject<ModSpawnEggItem> SUMMER_PIXIE_SPAWNING_EGG = Registration.ITEMS.register("summer_pixie_spawn_egg",
            () -> new ModSpawnEggItem(ModEntityTypes.SUMMER_PIXIE, 0xffcc00, 0xff6633,
                    new Item.Properties().group(FeywildMod.FEYWILD_TAB)));


    public static final RegistryObject<ModSpawnEggItem> WINTER_PIXIE_SPAWNING_EGG = Registration.ITEMS.register("winter_pixie_spawn_egg",
            () -> new ModSpawnEggItem(ModEntityTypes.WINTER_PIXIE, 0x99ffff, 0x333333,
                    new Item.Properties().group(FeywildMod.FEYWILD_TAB)));

     */

    //METHODES

    public static void register() {}


        public enum ModItemTier implements IItemTier {
        FEY(250, 3f, 5f, 2, 15, Ingredient.fromStacks(new ItemStack(ModItems.GREATER_FEY_GEM.get())));

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
        public int getMaxUses() {
            return maxUses;
        }

        @Override
        public float getEfficiency() {
            return efficiency;
        }

        @Override
        public float getAttackDamage() {
            return attackDamage;
        }

        @Override
        public int getHarvestLevel() {
            return harvestLevel;
        }

        @Override
        public int getEnchantability() {
            return enchantability;
        }

        @Override
        public Ingredient getRepairMaterial() {
            return repairMaterial;
        }
    }


    public enum ModArmorTier implements IArmorMaterial{
        FEY_ARMOR(50, new int[] {2,7,5,3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_ELYTRA, Ingredient.fromStacks(new ItemStack(ModItems.GREATER_FEY_GEM.get())),
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
        public int getDurability(EquipmentSlotType slotIn) {
            return durability;
        }

        @Override
        public int getDamageReductionAmount(EquipmentSlotType slotIn) {
            return damageReductionAmountArray[slotIn.getIndex()];
        }

        @Override
        public int getEnchantability() {
            return enchantability;
        }

        @Override
        public SoundEvent getSoundEvent() {
            return soundEvent;
        }

        @Override
        public Ingredient getRepairMaterial() {
            return repairMaterial;
        }

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
