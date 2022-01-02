package com.feywild.feywild.quest.reward;

import com.google.gson.JsonObject;
import io.github.noeppi_noeppi.libx.crafting.CraftingHelper2;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.crafting.CraftingHelper;

public class ItemReward implements RewardType<ItemStack> {

    public static final ItemReward INSTANCE = new ItemReward();
    
    private ItemReward() {
        
    }
    
    @Override
    public Class<ItemStack> element() {
        return ItemStack.class;
    }

    @Override
    public void grantReward(ServerPlayer player, ItemStack element) {
        player.getInventory().add(element.copy());
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
