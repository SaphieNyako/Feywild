package com.feywild.feywild.entity.util.trades;

import com.feywild.feywild.item.ModItems;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.util.JSONUtils;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class DwarvenTrades {

    public static final List<TradeData> commonLoot = new LinkedList<>(), legendaryLoot = new LinkedList<>();
    public static final List<TradeData> commonFood = new LinkedList<>(), legendaryFood = new LinkedList<>();
    public static final List<SimplyTrade> untamedLevel1 = new LinkedList<>(), untamedLevel2 = new LinkedList<>();

    public static Int2ObjectMap<VillagerTrades.ITrade[]> DWARVEN_TRADES;

    /* TAMED */
    public static Int2ObjectMap<VillagerTrades.ITrade[]> DWARVEN_BLACKSMITH_TRADES = toIntMap(ImmutableMap.of(1, new VillagerTrades.ITrade[]{
                    new DwarvenTrades.RandomCommonFoodItemsForRandomCommonOreItemsTrade(),
                    new DwarvenTrades.RandomCommonFoodItemsForRandomCommonOreItemsTrade(),
                    new DwarvenTrades.RandomCommonFoodItemsForRandomCommonOreItemsTrade(),
                    new DwarvenTrades.RandomLegendaryFoodItemsForRandomLegendaryOreItemsTrade(),
            },

            2, new VillagerTrades.ITrade[]{
                    new SimplyTrade(new ItemStack(ModItems.LESSER_FEY_GEM.get(), 4), new ItemStack(ModItems.GREATER_FEY_GEM.get(), 1), 12, 3, 0.05f),
                    new SimplyTrade(new ItemStack(ModItems.GREATER_FEY_GEM.get(), 4), new ItemStack(ModItems.SHINY_FEY_GEM.get(), 1), 12, 4, 0.05f),
                    new SimplyTrade(new ItemStack(ModItems.SHINY_FEY_GEM.get(), 4), new ItemStack(ModItems.BRILLIANT_FEY_GEM.get(), 1), 12, 5, 0.05f),
                    new SimplyTrade(new ItemStack(ModItems.BRILLIANT_FEY_GEM.get(), 2), new ItemStack(ModItems.SCHEMATICS_FEY_ALTAR.get(), 1), 1, 10, 0.05f),
            },
            3, getTrades(Collections.singletonList(
                    new SimplyTrade(new ItemStack(ModItems.BRILLIANT_FEY_GEM.get(), 1), new ItemStack(ModItems.SCHEMATICS_GEM_TRANSMUTATION.get(), 1), 1, 1, 5)
            ))));

    public static Int2ObjectMap<VillagerTrades.ITrade[]> toIntMap(ImmutableMap<Integer, VillagerTrades.ITrade[]> p_221238_0_) {
        return new Int2ObjectOpenHashMap<>(p_221238_0_);
    }

    public static VillagerTrades.ITrade[] getTrades(List<SimplyTrade> trades) {
        VillagerTrades.ITrade[] trades1 = new VillagerTrades.ITrade[trades.size()];
        for (int i = 0; i < trades.size(); i++) {
            trades1[i] = trades.get(i);
        }
        return trades1;
    }

    static class SimplyTrade implements VillagerTrades.ITrade {

        private final ItemStack itemStack, itemStackOut;

        private final int maxUses;

        private final int givenXp;

        private final float multiplier;

        public SimplyTrade(ItemStack itemIn, ItemStack itemOut, int maxUsesIn, int givenXP, float multiplier) {
            this.itemStack = itemIn;
            this.itemStackOut = itemOut;
            this.maxUses = maxUsesIn;
            this.givenXp = givenXP;
            this.multiplier = multiplier;
        }

        @Override
        public MerchantOffer getOffer(@Nonnull Entity p_221182_1_, @Nonnull Random p_221182_2_) {
            return new MerchantOffer(itemStack, itemStackOut, this.maxUses, this.givenXp, this.multiplier);
        }
    }

    static class RandomCommonFoodItemsForRandomCommonOreItemsTrade implements VillagerTrades.ITrade {

        public RandomCommonFoodItemsForRandomCommonOreItemsTrade() {
        }

        @Override
        public MerchantOffer getOffer(@Nonnull Entity p_221182_1_, Random random) {
            TradeData data = commonLoot.get(random.nextInt(commonLoot.size()));

            return new MerchantOffer(commonFood.get(random.nextInt(commonFood.size())).getStack(),
                    data.getStack(),
                    data.getMaxUses(), data.getGivenXP(), data.getPriceMultiplier());

        }

    }

    static class RandomLegendaryFoodItemsForRandomLegendaryOreItemsTrade implements VillagerTrades.ITrade {

        public RandomLegendaryFoodItemsForRandomLegendaryOreItemsTrade() {

        }

        @Override
        public MerchantOffer getOffer(@Nonnull Entity p_221182_1_, Random random) {
            TradeData data = legendaryLoot.get(random.nextInt(legendaryLoot.size()));
            return new MerchantOffer(legendaryFood.get(random.nextInt(legendaryFood.size())).getStack(),
                    data.getStack(),
                    data.getMaxUses(), data.getGivenXP(), data.getPriceMultiplier());

        }

    }

    public static class TamedSerializer {

        public TamedSerializer() {
        }

        public List<TradeData> deserialize(JsonObject object, int tradeRarity) {

            try {
                if (object.get("type").getAsString().equals("dwarf_trade") && tradeRarity == object.get("rarity").getAsInt()) {
                    List<TradeData> ret = new LinkedList<>();
                    JsonArray arr = object.getAsJsonArray("pool");
                    for (JsonElement object1 : arr) {
                        JsonObject elementToObj = object1.getAsJsonObject();
                        if (object.get("isLoot").getAsBoolean())
                            ret.add(new TradeData(elementToObj.get("maxUses").getAsInt(), elementToObj.get("givenXp").getAsInt(), elementToObj.get("priceMult").getAsFloat(), ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(elementToObj, "output"))));
                        else
                            ret.add(new TradeData(0, 0, 0f, ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(elementToObj, "output"))));
                    }
                    return ret;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static class UntamedSerializer {

        public UntamedSerializer() {
        }

        public List<SimplyTrade> deserialize(JsonObject object) {

            try {
                if (object.get("type").getAsString().equals("dwarf_trade")) {
                    List<SimplyTrade> ret = new LinkedList<>();
                    JsonArray arr = object.getAsJsonArray("pool");
                    for (JsonElement object1 : arr) {
                        JsonObject elementToObj = object1.getAsJsonObject();
                        ret.add(new SimplyTrade(ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(elementToObj, "input")), ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(elementToObj, "output")), elementToObj.get("maxUses").getAsInt(), elementToObj.get("givenXp").getAsInt(), elementToObj.get("priceMult").getAsFloat()));
                    }
                    return ret;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}


