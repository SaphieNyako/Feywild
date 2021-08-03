package com.feywild.feywild.trade.item;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;

public interface StackFactory {
    
    ItemStack createStack(Random random);
    
    static StackFactory fromJson(JsonElement json) {
        if (json.isJsonArray()) {
            ImmutableList.Builder<StackFactory> factories = ImmutableList.builder();
            for (JsonElement elem : json.getAsJsonArray()) {
                factories.add(fromJson(elem));
            }
            return new CompoundStackFactory(factories.build());
        } else {
            JsonObject obj = json.getAsJsonObject();
            int min;
            int max;
            if (obj.has("count")) {
                if (obj.get("count").isJsonArray() && obj.get("count").getAsJsonArray().size() == 2) {
                    min = obj.get("count").getAsJsonArray().get(0).getAsInt();
                    max = obj.get("count").getAsJsonArray().get(1).getAsInt();
                } else {
                    min = obj.get("count").getAsInt();
                    max = obj.get("count").getAsInt();
                }
            } else {
                min = 1;
                max = 1;
            }
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(obj.get("item").getAsString()));
            if (item == null) {
                throw new IllegalStateException("Can't create stack factory without item");
            }
            CompoundNBT nbt;
            try {
                if (obj.has("nbt") && obj.get("nbt").isJsonPrimitive()) {
                    nbt = JsonToNBT.parseTag(obj.get("nbt").getAsString());
                } else if (obj.has("nbt")) {
                    nbt = JsonToNBT.parseTag(obj.get("nbt").toString());
                } else {
                    nbt = null;
                }
            } catch (CommandSyntaxException e) {
                throw new IllegalStateException("Invalid NBT", e);
            }
            return new SimpleStackFactory(new ItemStack(item, min, nbt), min, max);
        }
    }
}
