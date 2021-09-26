package com.feywild.feywild.jei.util;

import com.feywild.feywild.trade.recipe.TradeRecipe;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.common.MinecraftForge;

import java.util.List;

public class FeywildJeiReloader {
    
    public static List<TradeRecipe> clientTrades = ImmutableList.of();
    
    public static void reload() {
        ClientPlayNetHandler connection = Minecraft.getInstance().getConnection();
        //noinspection ConstantConditions
        if (connection != null && connection.getRecipeManager() != null) {
            MinecraftForge.EVENT_BUS.post(new RecipesUpdatedEvent(connection.getRecipeManager()));
        }
    }
}
