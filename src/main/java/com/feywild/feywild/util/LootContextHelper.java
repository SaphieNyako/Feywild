package com.feywild.feywild.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.lang.reflect.Field;

public class LootContextHelper {
    
    public static LootContext copyWith(LootContext context, ResourceLocation queriedLootTable) {
        LootContext copy = new LootContext.Builder(context).create(LootContextParamSets.EMPTY);
        try {
            Field field = ObfuscationReflectionHelper.findField(LootContext.class, "queriedLootTableId");
            field.setAccessible(true);
            field.set(copy, queriedLootTable);
            return copy;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
