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
                ModBlocks.FEY_GEM_ORE_DEEP_SLATE.get()
        );

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                ModBlocks.FEY_GEM_ORE.get(),
                ModBlocks.FEY_GEM_ORE_DEEP_SLATE.get(),
                ModBlocks.FEY_ALTAR.get(),
                ModBlocks.ELVEN_QUARTZ_BLOCK.get(),
                ModBlocks.ELVEN_QUARTZ_STAIRS.get(),
                ModBlocks.ELVEN_QUARTZ_SLAB.get(),
                ModBlocks.ELVEN_QUARTZ_BRICK.get(),
                ModBlocks.ELVEN_QUARTZ_BRICK_STAIRS.get(),
                ModBlocks.ELVEN_QUARTZ_BRICK_SLAB.get(),
                ModBlocks.ELVEN_QUARTZ_MOSSY_BRICK.get(),
                ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK.get(),
                ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get(),
                ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get(),
                ModBlocks.ELVEN_QUARTZ_PILLAR.get(),
                ModBlocks.ELVEN_QUARTZ_POLISHED.get(),
                ModBlocks.ELVEN_QUARTZ_POLISHED_STAIRS.get(),
                ModBlocks.ELVEN_QUARTZ_POLISHED_SLAB.get(),
                //SPRING
                ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_STAIRS.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_SLAB.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_BRICK.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_BRICK_STAIRS.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_BRICK_SLAB.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_MOSSY_BRICK.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_PILLAR.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED_STAIRS.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED_SLAB.get(),
                //SUMMER
                ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_STAIRS.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_SLAB.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK_STAIRS.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK_SLAB.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_MOSSY_BRICK.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_PILLAR.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED_STAIRS.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED_SLAB.get(),
                //WINTER
                ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_STAIRS.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_SLAB.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_BRICK.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_BRICK_STAIRS.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_BRICK_SLAB.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_MOSSY_BRICK.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_PILLAR.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED_STAIRS.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED_SLAB.get(),
                //AUTUMN
                ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_STAIRS.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_SLAB.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK_STAIRS.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK_SLAB.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_MOSSY_BRICK.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_PILLAR.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED_STAIRS.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED_SLAB.get()
        );

        this.tag(BlockTags.NEEDS_IRON_TOOL).add(
                ModBlocks.FEY_GEM_ORE.get(),
                ModBlocks.FEY_GEM_ORE_DEEP_SLATE.get(),
                ModBlocks.ELVEN_QUARTZ_BLOCK.get(),
                ModBlocks.ELVEN_QUARTZ_STAIRS.get(),
                ModBlocks.ELVEN_QUARTZ_SLAB.get(),
                ModBlocks.ELVEN_QUARTZ_BRICK.get(),
                ModBlocks.ELVEN_QUARTZ_BRICK_STAIRS.get(),
                ModBlocks.ELVEN_QUARTZ_BRICK_SLAB.get(),
                ModBlocks.ELVEN_QUARTZ_MOSSY_BRICK.get(),
                ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK.get(),
                ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get(),
                ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get(),
                ModBlocks.ELVEN_QUARTZ_PILLAR.get(),
                ModBlocks.ELVEN_QUARTZ_POLISHED.get(),
                ModBlocks.ELVEN_QUARTZ_POLISHED_STAIRS.get(),
                ModBlocks.ELVEN_QUARTZ_POLISHED_SLAB.get(),
                //SPRING
                ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_STAIRS.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_SLAB.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_BRICK.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_BRICK_STAIRS.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_BRICK_SLAB.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_MOSSY_BRICK.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_PILLAR.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED_STAIRS.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED_SLAB.get(),
                //SUMMER
                ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_STAIRS.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_SLAB.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK_STAIRS.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK_SLAB.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_MOSSY_BRICK.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_PILLAR.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED_STAIRS.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED_SLAB.get(),
                //WINTER
                ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_STAIRS.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_SLAB.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_BRICK.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_BRICK_STAIRS.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_BRICK_SLAB.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_MOSSY_BRICK.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_PILLAR.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED_STAIRS.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED_SLAB.get(),
                //AUTUMN
                ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_STAIRS.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_SLAB.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK_STAIRS.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK_SLAB.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_MOSSY_BRICK.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_PILLAR.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED_STAIRS.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED_SLAB.get()
        );

        //TREES
        //AUTUMN
        this.tag(BlockTags.LOGS)
                .add(ModBlocks.AUTUMN_TREE_LOG.get())
                .add(ModBlocks.AUTUMN_TREE_WOOD.get())
                .add(ModBlocks.AUTUMN_TREE_STRIPPED_LOG.get())
                .add(ModBlocks.AUTUMN_TREE_STRIPPED_WOOD.get());

        this.tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.AUTUMN_TREE_LOG.get())
                .add(ModBlocks.AUTUMN_TREE_WOOD.get())
                .add(ModBlocks.AUTUMN_TREE_STRIPPED_LOG.get())
                .add(ModBlocks.AUTUMN_TREE_STRIPPED_WOOD.get());
    }
}
