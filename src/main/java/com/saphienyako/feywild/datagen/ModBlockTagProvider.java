package com.saphienyako.feywild.datagen;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Feywild.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(Tags.Blocks.ORES).add(
                ModBlocks.FEY_GEM_ORE.get(),
                ModBlocks.FEY_GEM_ORE_DEEP_SLATE.get());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                ModBlocks.FEY_GEM_ORE.get(),
                ModBlocks.FEY_GEM_ORE_DEEP_SLATE.get());
               // ModBlocks.FEY_ALTAR.get());

        this.tag(BlockTags.NEEDS_IRON_TOOL).add(
                ModBlocks.FEY_GEM_ORE.get(),
                ModBlocks.FEY_GEM_ORE_DEEP_SLATE.get());
    }
}
