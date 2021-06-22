package com.feywild.feywild.entity.util;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.item.ModItems;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.JSONUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class DwarvenTrades {

    //TODO: Serialise the trades
    public static final List<ItemStack> commonLoot = new LinkedList<>(), commonFood = new LinkedList<>(), legendaryLoot = new LinkedList<>(), legendaryFood = new LinkedList<>();

    public static final Int2ObjectMap<VillagerTrades.ITrade[]> DWARVEN_TRADES = toIntMap(ImmutableMap.of(1, new VillagerTrades.ITrade[]{
            new DwarvenTrades.FeyGemForItemsTrade(Items.IRON_ORE, 3, 5, 1),
            new DwarvenTrades.FeyGemForItemsTrade(Items.GOLD_ORE, 3, 5, 1),
            new DwarvenTrades.FeyGemForItemsTrade(Items.COAL, 18, 5, 1),
            new DwarvenTrades.FeyGemForItemsTrade(Items.REDSTONE, 12, 5, 1),
            new DwarvenTrades.FeyGemForItemsTrade(Items.LAPIS_LAZULI, 6, 5, 1)

    }, 2, new VillagerTrades.ITrade[]{
            new DwarvenTrades.ItemsForFeyGemTrade(Items.DIAMOND_PICKAXE, 10, 1, 1, 10),
            new DwarvenTrades.ItemsForFeyGemTrade(Items.DIAMOND_SHOVEL, 8, 1, 1, 10),
            new DwarvenTrades.ItemsForFeyGemTrade(Items.LANTERN, 2, 1, 5, 5),
            new DwarvenTrades.ItemsForFeyGemTrade(Items.TORCH, 1, 5, 10, 3),
            new DwarvenTrades.ItemsForFeyGemTrade(ModItems.MANDRAKE.get(), 1, 1, 5, 3),
            new DwarvenTrades.ItemsForFeyGemTrade(Items.TNT, 3, 1, 5, 5),
            new DwarvenTrades.ItemsForFeyGemTrade(Items.MINECART, 5, 1, 2, 3),
            new DwarvenTrades.ItemsForFeyGemTrade(Items.MULE_SPAWN_EGG, 10, 1, 1, 10)

    }, 3, new VillagerTrades.ITrade[]{
            new DwarvenTrades.ItemsForFeyGemTrade(ModItems.SUMMONING_SCROLL_DWARF_BLACKSMITH.get(), 30, 1, 1, 10)
    }));


    /* TAMED */

    public static final Int2ObjectMap<VillagerTrades.ITrade[]> DWARVEN_BLACKSMITH_TRADES = toIntMap(ImmutableMap.of(1, new VillagerTrades.ITrade[]{
                    new DwarvenTrades.RandomCommonFoodItemsForRandomCommonOreItemsTrade(),
                    new DwarvenTrades.RandomCommonFoodItemsForRandomCommonOreItemsTrade(),
                    new DwarvenTrades.RandomCommonFoodItemsForRandomCommonOreItemsTrade(),
                    new DwarvenTrades.RandomLegendaryFoodItemsForRandomLegendaryOreItemsTrade(),
            },

            2, new VillagerTrades.ITrade[]{
                    new DwarvenTrades.ItemsForFeyGemTrade(ModItems.GREATER_FEY_GEM.get(), 4, 1, 1),
                    new DwarvenTrades.ItemsForGreaterFeyGemTrade(ModItems.SHINY_FEY_GEM.get(), 4, 1, 2),
                    new DwarvenTrades.ItemsForShinyFeyGemTrade(ModItems.BRILLIANT_FEY_GEM.get(), 4, 1, 3),
                    new DwarvenTrades.ItemsForBrilliantFeyGemTrade(ModItems.SCHEMATICS_FEY_ALTAR.get(), 2, 1, 1, 10)

            }, 3, new VillagerTrades.ITrade[]{
                    new DwarvenTrades.ItemsForBrilliantFeyGemTrade(ModItems.SCHEMATICS_GEM_TRANSMUTATION.get(), 2, 1, 1, 5),

            }));

    private static Int2ObjectMap<VillagerTrades.ITrade[]> toIntMap(ImmutableMap<Integer, VillagerTrades.ITrade[]> p_221238_0_) {
        return new Int2ObjectOpenHashMap<>(p_221238_0_);
    }

    static class FeyGemForItemsTrade implements VillagerTrades.ITrade {

        private final Item field_221183_a;

        private final int field_221184_b;

        private final int field_221185_c;

        private final int field_221186_d;

        private final float field_221187_e;

        public FeyGemForItemsTrade(IItemProvider itemIn, int itemCountIn, int maxUsesIn, int givenXP) {
            this.field_221183_a = itemIn.asItem();
            this.field_221184_b = itemCountIn;
            this.field_221185_c = maxUsesIn;
            this.field_221186_d = givenXP;
            this.field_221187_e = 0.05F;
        }

        public MerchantOffer getOffer(Entity p_221182_1_, Random p_221182_2_) {
            ItemStack itemstack = new ItemStack(this.field_221183_a, this.field_221184_b);
            return new MerchantOffer(itemstack, new ItemStack(ModItems.LESSER_FEY_GEM.get()), this.field_221185_c, this.field_221186_d, this.field_221187_e);
        }
    }

    static class ItemsForFeyGemTrade implements VillagerTrades.ITrade {

        private final ItemStack itemSold;

        private final int feyGemCount;

        private final int soldItemCount;

        private final int maxUses;

        private final int givenXP;

        private final float priceMultiplier;

        public ItemsForFeyGemTrade(Block itemSoldIn, int feyGemCount, int soldItemCountIn, int maxUsesIn, int givenXPIn) {
            this(new ItemStack(itemSoldIn), feyGemCount, soldItemCountIn, maxUsesIn, givenXPIn);
        }

        public ItemsForFeyGemTrade(Item itemSoldIn, int feyGemCount, int soldItemCountIn, int givenXPIn) {
            this(new ItemStack(itemSoldIn), feyGemCount, soldItemCountIn, 12, givenXPIn);
        }

        public ItemsForFeyGemTrade(Item itemSoldIn, int feyGemCount, int soldItemCountIn, int maxUsesIn, int givenXPIn) {
            this(new ItemStack(itemSoldIn), feyGemCount, soldItemCountIn, maxUsesIn, givenXPIn);
        }

        public ItemsForFeyGemTrade(ItemStack itemSoldIn, int feyGemCount, int soldItemCountIn, int maxUsesIn, int givenXPIn) {
            this(itemSoldIn, feyGemCount, soldItemCountIn, maxUsesIn, givenXPIn, 0.05F);
        }

        public ItemsForFeyGemTrade(ItemStack itemSoldIn, int feyGemCount, int soldItemCountIn, int maxUsesIn, int givenXPIn, float priceMultiplierIn) {
            this.itemSold = itemSoldIn;
            this.feyGemCount = feyGemCount;
            this.soldItemCount = soldItemCountIn;
            this.maxUses = maxUsesIn;
            this.givenXP = givenXPIn;
            this.priceMultiplier = priceMultiplierIn;
        }

        public MerchantOffer getOffer(Entity p_221182_1_, Random p_221182_2_) {
            return new MerchantOffer(new ItemStack(ModItems.LESSER_FEY_GEM.get(), this.feyGemCount), new ItemStack(this.itemSold.getItem(), this.soldItemCount), this.maxUses, this.givenXP, this.priceMultiplier);

        }

    }

    static class ItemsForGreaterFeyGemTrade implements VillagerTrades.ITrade {

        private final ItemStack itemSold;

        private final int feyGemCount;

        private final int soldItemCount;

        private final int maxUses;

        private final int givenXP;

        private final float priceMultiplier;

        public ItemsForGreaterFeyGemTrade(Block itemSoldIn, int feyGemCount, int soldItemCountIn, int maxUsesIn, int givenXPIn) {
            this(new ItemStack(itemSoldIn), feyGemCount, soldItemCountIn, maxUsesIn, givenXPIn);
        }

        public ItemsForGreaterFeyGemTrade(Item itemSoldIn, int feyGemCount, int soldItemCountIn, int givenXPIn) {
            this(new ItemStack(itemSoldIn), feyGemCount, soldItemCountIn, 12, givenXPIn);
        }

        public ItemsForGreaterFeyGemTrade(Item itemSoldIn, int feyGemCount, int soldItemCountIn, int maxUsesIn, int givenXPIn) {
            this(new ItemStack(itemSoldIn), feyGemCount, soldItemCountIn, maxUsesIn, givenXPIn);
        }

        public ItemsForGreaterFeyGemTrade(ItemStack itemSoldIn, int feyGemCount, int soldItemCountIn, int maxUsesIn, int givenXPIn) {
            this(itemSoldIn, feyGemCount, soldItemCountIn, maxUsesIn, givenXPIn, 0.05F);
        }

        public ItemsForGreaterFeyGemTrade(ItemStack itemSoldIn, int feyGemCount, int soldItemCountIn, int maxUsesIn, int givenXPIn, float priceMultiplierIn) {
            this.itemSold = itemSoldIn;
            this.feyGemCount = feyGemCount;
            this.soldItemCount = soldItemCountIn;
            this.maxUses = maxUsesIn;
            this.givenXP = givenXPIn;
            this.priceMultiplier = priceMultiplierIn;
        }

        public MerchantOffer getOffer(Entity p_221182_1_, Random p_221182_2_) {
            return new MerchantOffer(new ItemStack(ModItems.GREATER_FEY_GEM.get(), this.feyGemCount), new ItemStack(this.itemSold.getItem(), this.soldItemCount), this.maxUses, this.givenXP, this.priceMultiplier);

        }

    }

    static class ItemsForShinyFeyGemTrade implements VillagerTrades.ITrade {

        private final ItemStack itemSold;

        private final int feyGemCount;

        private final int soldItemCount;

        private final int maxUses;

        private final int givenXP;

        private final float priceMultiplier;

        public ItemsForShinyFeyGemTrade(Block itemSoldIn, int feyGemCount, int soldItemCountIn, int maxUsesIn, int givenXPIn) {
            this(new ItemStack(itemSoldIn), feyGemCount, soldItemCountIn, maxUsesIn, givenXPIn);
        }

        public ItemsForShinyFeyGemTrade(Item itemSoldIn, int feyGemCount, int soldItemCountIn, int givenXPIn) {
            this(new ItemStack(itemSoldIn), feyGemCount, soldItemCountIn, 12, givenXPIn);
        }

        public ItemsForShinyFeyGemTrade(Item itemSoldIn, int feyGemCount, int soldItemCountIn, int maxUsesIn, int givenXPIn) {
            this(new ItemStack(itemSoldIn), feyGemCount, soldItemCountIn, maxUsesIn, givenXPIn);
        }

        public ItemsForShinyFeyGemTrade(ItemStack itemSoldIn, int feyGemCount, int soldItemCountIn, int maxUsesIn, int givenXPIn) {
            this(itemSoldIn, feyGemCount, soldItemCountIn, maxUsesIn, givenXPIn, 0.05F);
        }

        public ItemsForShinyFeyGemTrade(ItemStack itemSoldIn, int feyGemCount, int soldItemCountIn, int maxUsesIn, int givenXPIn, float priceMultiplierIn) {
            this.itemSold = itemSoldIn;
            this.feyGemCount = feyGemCount;
            this.soldItemCount = soldItemCountIn;
            this.maxUses = maxUsesIn;
            this.givenXP = givenXPIn;
            this.priceMultiplier = priceMultiplierIn;
        }

        public MerchantOffer getOffer(Entity p_221182_1_, Random p_221182_2_) {
            return new MerchantOffer(new ItemStack(ModItems.SHINY_FEY_GEM.get(), this.feyGemCount), new ItemStack(this.itemSold.getItem(), this.soldItemCount), this.maxUses, this.givenXP, this.priceMultiplier);

        }

    }

    static class ItemsForBrilliantFeyGemTrade implements VillagerTrades.ITrade {

        private final ItemStack itemSold;

        private final int feyGemCount;

        private final int soldItemCount;

        private final int maxUses;

        private final int givenXP;

        private final float priceMultiplier;

        public ItemsForBrilliantFeyGemTrade(Block itemSoldIn, int feyGemCount, int soldItemCountIn, int maxUsesIn, int givenXPIn) {
            this(new ItemStack(itemSoldIn), feyGemCount, soldItemCountIn, maxUsesIn, givenXPIn);
        }

        public ItemsForBrilliantFeyGemTrade(Item itemSoldIn, int feyGemCount, int soldItemCountIn, int givenXPIn) {
            this(new ItemStack(itemSoldIn), feyGemCount, soldItemCountIn, 12, givenXPIn);
        }

        public ItemsForBrilliantFeyGemTrade(Item itemSoldIn, int feyGemCount, int soldItemCountIn, int maxUsesIn, int givenXPIn) {
            this(new ItemStack(itemSoldIn), feyGemCount, soldItemCountIn, maxUsesIn, givenXPIn);
        }

        public ItemsForBrilliantFeyGemTrade(ItemStack itemSoldIn, int feyGemCount, int soldItemCountIn, int maxUsesIn, int givenXPIn) {
            this(itemSoldIn, feyGemCount, soldItemCountIn, maxUsesIn, givenXPIn, 0.05F);
        }

        public ItemsForBrilliantFeyGemTrade(ItemStack itemSoldIn, int feyGemCount, int soldItemCountIn, int maxUsesIn, int givenXPIn, float priceMultiplierIn) {
            this.itemSold = itemSoldIn;
            this.feyGemCount = feyGemCount;
            this.soldItemCount = soldItemCountIn;
            this.maxUses = maxUsesIn;
            this.givenXP = givenXPIn;
            this.priceMultiplier = priceMultiplierIn;
        }

        public MerchantOffer getOffer(Entity p_221182_1_, Random p_221182_2_) {
            return new MerchantOffer(new ItemStack(ModItems.BRILLIANT_FEY_GEM.get(), this.feyGemCount), new ItemStack(this.itemSold.getItem(), this.soldItemCount), this.maxUses, this.givenXP, this.priceMultiplier);

        }

    }

    static class RandomCommonFoodItemsForRandomCommonOreItemsTrade implements VillagerTrades.ITrade {

        private final int maxUses;

        private final int givenXP;

        private final float priceMultiplier;

        public RandomCommonFoodItemsForRandomCommonOreItemsTrade() {
            this.maxUses = 5;
            this.givenXP = 2;
            this.priceMultiplier = 0.05F;
        }

        public MerchantOffer getOffer(Entity p_221182_1_, Random random) {
            return new MerchantOffer(commonFood.get(random.nextInt(commonFood.size())),
                    commonLoot.get(random.nextInt(commonLoot.size())),
                    this.maxUses, this.givenXP, this.priceMultiplier);

        }

    }

    static class RandomLegendaryFoodItemsForRandomLegendaryOreItemsTrade implements VillagerTrades.ITrade {

        private final int maxUses;

        private final int givenXP;

        private final float priceMultiplier;

        public RandomLegendaryFoodItemsForRandomLegendaryOreItemsTrade() {
            this.maxUses = 2;
            this.givenXP = 5;
            this.priceMultiplier = 0.05F;
        }

        public MerchantOffer getOffer(Entity p_221182_1_, Random random) {
            return new MerchantOffer(legendaryFood.get(random.nextInt(legendaryFood.size())),
                    legendaryLoot.get(random.nextInt(legendaryLoot.size())),
                    this.maxUses, this.givenXP, this.priceMultiplier);

        }

    }

    // Ancient's note : Since it's all here already why not just continue :P


    public static class TamedSerializer {
        public TamedSerializer(){
        }

        public List<ItemStack> deserialize(JsonObject object, int tradeRarity){

            try {
                if(object.get("type").getAsString().equals("dwarf_trade") && tradeRarity == object.get("rarity").getAsInt()) {
                    int pool = object.get("length").getAsInt();
                    List<ItemStack> ret = new LinkedList<>();
                    for (int i = 0; i < pool; i++) {
                        ret.add(ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(object,"entry"+ i)));
                    }
                    return ret;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
}


