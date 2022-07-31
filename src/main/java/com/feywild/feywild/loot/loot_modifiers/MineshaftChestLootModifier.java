package com.feywild.feywild.loot.loot_modifiers;

import com.feywild.feywild.config.MiscConfig;
import com.feywild.feywild.item.ModItems;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.apache.commons.lang3.NotImplementedException;

import javax.annotation.Nonnull;

// UPDATE_TODO datagen for this (remove config for weight, can use datapack)
public class MineshaftChestLootModifier extends LootModifier {
    
    protected MineshaftChestLootModifier(LootItemCondition[] conditions) {
        super(conditions);
    }
    
    @Nonnull
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (context.getRandom().nextFloat() < MiscConfig.rune_stone_weight) {
            generatedLoot.add(new ItemStack(ModItems.inactiveMarketRuneStone, 1));
        }
        if (context.getRandom().nextFloat() < 0.60) {
            generatedLoot.add(new ItemStack(ModItems.lesserFeyGem, 3));
        }
        if (context.getRandom().nextFloat() < 0.30) {
            generatedLoot.add(new ItemStack(ModItems.greaterFeyGem, 2));
        }
        if (context.getRandom().nextFloat() < 0.15) {
            generatedLoot.add(new ItemStack(ModItems.schematicsGemTransmutation, 1));
            generatedLoot.add(new ItemStack(ModItems.shinyFeyGem, 1));
        }
        if (context.getRandom().nextFloat() < 0.08) {
            generatedLoot.add(new ItemStack(ModItems.brilliantFeyGem, 1));
        }
        if (context.getRandom().nextFloat() < 0.04) {
            generatedLoot.add(new ItemStack(ModItems.feywildMusicDisc, 1));
        }
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        throw new NotImplementedException();
    }
}
