package com.feywild.feywild.trade.entries;

import com.feywild.feywild.trade.item.EmptyStackFactory;
import com.feywild.feywild.trade.item.SimpleStackFactory;
import com.feywild.feywild.trade.item.StackFactory;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

// A simple trade
public class SimpleTrade implements VillagerTrades.ITrade {
    
    public final StackFactory input;
    public final StackFactory additional;
    public final StackFactory output;
    public final Range uses;
    public final Range exp;
    public final float mul;

    public SimpleTrade(StackFactory input, StackFactory additional, StackFactory output, Range uses, Range exp, float mul) {
        this.input = input;
        this.additional = additional;
        this.output = output;
        this.uses = uses;
        this.exp = exp;
        this.mul = mul;
    }

    public static SimpleTrade fromJson(JsonObject json) {
        StackFactory input = StackFactory.fromJson(json.get("input"));
        StackFactory money;
        if (!json.has("additional")) {
            money = EmptyStackFactory.INSTANCE;
        } else if (json.get("additional").isJsonPrimitive()) {
            int amount = json.get("additional").getAsInt();
            money = new SimpleStackFactory(new ItemStack(Items.EMERALD, amount), amount, amount);
        } else {
            money = StackFactory.fromJson(json.get("money"));
        }
        StackFactory output = StackFactory.fromJson(json.get("output"));
        Range uses = json.has("uses") ? Range.fromJson(json.get("uses")) : Range.of(5);
        Range exp = json.has("exp") ? Range.fromJson(json.get("exp")) : Range.of(1);
        float mul = json.has("mul") ? json.get("mul").getAsFloat() : 0;
        return new SimpleTrade(input, money, output, uses, exp, mul);
    }

    @Nullable
    @Override
    public MerchantOffer getOffer(@Nonnull Entity merchant, @Nonnull Random random) {
        return new MerchantOffer(
                this.input.createStack(random), this.additional.createStack(random), this.output.createStack(random),
                0, this.uses.select(random), this.exp.select(random), this.mul, 0
        );
    }

    public static class Range {
        
        public final int min;
        public final int max;

        private Range(int min, int max) {
            this.min = Math.min(min, max);
            this.max = Math.max(min, max);
        }
        
        public int select(Random random) {
            return this.min + random.nextInt(1 + (this.max - this.min));
        }
        
        public static Range of(int value) {
            return new Range(value, value);
        }
        
        public static Range fromJson(JsonElement json) {
            if (json.isJsonArray() && json.getAsJsonArray().size() == 2) {
                return new Range(json.getAsJsonArray().get(0).getAsInt(), json.getAsJsonArray().get(1).getAsInt());
            } else {
                return new Range(json.getAsInt(), json.getAsInt());
            }
        }
    }
}
