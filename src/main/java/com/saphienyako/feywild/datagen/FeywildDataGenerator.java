package com.saphienyako.feywild.datagen;

import com.saphienyako.feywild.Feywild;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FeywildDataGenerator {

    public static void gatherData(GatherDataEvent.Client event) {
        PackOutput packOutput = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        event.addProvider(new ModModelProvider(packOutput));
        event.createBlockAndItemTags(
                ModBlockTagProvider::new,
                (output, provider, contentsGetter) -> new ModItemTagProvider(output, provider));
        event.addProvider(new LootTableProvider(packOutput, Collections.emptySet(), List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK)), lookupProvider));
        event.addProvider(new ModRecipeProvider.Runner(packOutput, lookupProvider));

        event.addProvider(new ModWorldGenProvider(packOutput, lookupProvider));
    }
}
