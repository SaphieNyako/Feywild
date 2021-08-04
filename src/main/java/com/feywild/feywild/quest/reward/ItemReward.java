package com.feywild.feywild.quest.reward;

import com.google.gson.JsonObject;
import io.github.noeppi_noeppi.libx.data.CraftingHelper2;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.crafting.CraftingHelper;

public class ItemReward implements RewardType<ItemStack> {

    public static ItemReward INSTANCE = new ItemReward();
    
    private ItemReward() {
        
    }
    
    @Override
    public Class<ItemStack> element() {
        return ItemStack.class;
    }

    @Override
    public void grantReward(ServerPlayerEntity player, ItemStack element) {
        player.inventory.add(element.copy());
    }

    @Override
    public ItemStack fromJson(JsonObject json) {
        return CraftingHelper.getItemStack(json.get("item").getAsJsonObject(), true);
    }

    @Override
    public JsonObject toJson(ItemStack element) {
        JsonObject json = new JsonObject();
        json.add("item", CraftingHelper2.serializeItemStack(element, true));
        return json;
    }
}
