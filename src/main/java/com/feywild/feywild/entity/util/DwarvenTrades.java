package com.feywild.feywild.entity.util;

import com.feywild.feywild.item.ModItems;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.util.IItemProvider;

import java.util.Random;

public class DwarvenTrades {

    //TODO: Serialise the trades

    public static final Int2ObjectMap<VillagerTrades.ITrade[]> DWARVEN_TRADES = toIntMap(ImmutableMap.of(1, new VillagerTrades.ITrade[]{
            new DwarvenTrades.FeyGemForItemsTrade(Items.IRON_ORE, 3, 5, 1),
            new DwarvenTrades.FeyGemForItemsTrade(Items.GOLD_ORE, 3, 5, 1),
            new DwarvenTrades.FeyGemForItemsTrade(Items.COAL, 18, 5, 1),
            new DwarvenTrades.FeyGemForItemsTrade(Items.REDSTONE, 12, 5, 1),
            new DwarvenTrades.FeyGemForItemsTrade(Items.LAPIS_LAZULI, 6, 5, 1)

    }, 2, new VillagerTrades.ITrade[]{
            new DwarvenTrades.ItemsForFeyGemTrade(Items.DIAMOND_PICKAXE, 10, 1, 1, 20),
            new DwarvenTrades.ItemsForFeyGemTrade(Items.DIAMOND_SHOVEL, 8, 1, 1, 20),
            new DwarvenTrades.ItemsForFeyGemTrade(Items.LANTERN, 2, 1, 5, 5),

    }, 3, new VillagerTrades.ITrade[]{
            new DwarvenTrades.ItemsForFeyGemTrade(ModItems.SUMMONING_SCROLL_DWARF_BLACKSMITH.get(), 30, 1, 1, 10)
    }));


    /* TAMED */

    public static final Int2ObjectMap<VillagerTrades.ITrade[]> DWARVEN_BLACKSMITH_TRADES = toIntMap(ImmutableMap.of(1, new VillagerTrades.ITrade[]{
            new DwarvenTrades.ItemsForFeyGemTrade(ModItems.GREATER_FEY_GEM.get(), 4, 1, 1),
            new DwarvenTrades.ItemsForGreaterFeyGemTrade(ModItems.SHINY_FEY_GEM.get(), 4, 1, 2),
            new DwarvenTrades.ItemsForShinyFeyGemTrade(ModItems.BRILLIANT_FEY_GEM.get(), 4, 1, 3)

    }, 2, new VillagerTrades.ITrade[]{
            new DwarvenTrades.ItemsForBrilliantFeyGemTrade(ModItems.SCHEMATICS_GEM_TRANSMUTATION.get(), 2, 1, 1, 5),
            new DwarvenTrades.ItemsForBrilliantFeyGemTrade(ModItems.SCHEMATICS_FEY_ALTAR.get(), 2, 1, 1, 10)
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
}


