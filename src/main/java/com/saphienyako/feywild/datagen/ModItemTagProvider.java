package com.saphienyako.feywild.datagen;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.block.ModBlocks;
import com.saphienyako.feywild.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider,
                              CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, Feywild.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        this.tag(ItemTags.CREEPER_DROP_MUSIC_DISCS).add(ModItems.FEYWILD_MUSIC_DISC.get());
        this.tag(Tags.Items.MUSIC_DISCS).add(ModItems.FEYWILD_MUSIC_DISC.get());
        this.tag(Tags.Items.CROPS).add(ModItems.MANDRAKE.get());
        this.tag(Tags.Items.DUSTS).add(ModItems.FEY_DUST.get());
        this.tag(Tags.Items.ENCHANTING_FUELS).add(ModItems.FEY_DUST.get());
        this.tag(Tags.Items.GEMS).add(ModItems.FEY_GEM.get());
        this.tag(Tags.Items.SEEDS).add(ModItems.GIANT_CROCUS_FLOWER_SEED.get());
        this.tag(Tags.Items.SEEDS).add(ModItems.GIANT_DANDELION_FLOWER_SEED.get());
        this.tag(Tags.Items.SEEDS).add(ModItems.GIANT_SUN_FLOWER_SEED.get());
        this.tag(Tags.Items.MUSHROOMS).add(ModBlocks.ORANGE_MUSHROOM.asItem());
        this.tag(Tags.Items.MUSHROOMS).add(ModBlocks.YELLOW_MUSHROOM.asItem());
        this.tag(Tags.Items.MUSHROOMS).add(ModBlocks.GREEN_MUSHROOM.asItem());
        this.tag(Tags.Items.MUSHROOMS).add(ModBlocks.LIGHT_BLUE_MUSHROOM.asItem());
        this.tag(Tags.Items.MUSHROOMS).add(ModBlocks.BLUE_MUSHROOM.asItem());
        this.tag(Tags.Items.MUSHROOMS).add(ModBlocks.PURPLE_MUSHROOM.asItem());
        this.tag(Tags.Items.MUSHROOMS).add(ModBlocks.PINK_MUSHROOM.asItem());

        //TREES
        //AUTUMN
        tag(ItemTags.LOGS)
                .add(ModBlocks.AUTUMN_TREE_LOG.get().asItem())
                .add(ModBlocks.AUTUMN_TREE_CRACKED_LOG.get().asItem())
                .add(ModBlocks.AUTUMN_TREE_WOOD_SLAB.get().asItem())
                .add(ModBlocks.AUTUMN_TREE_WOOD_STAIRS.get().asItem())
                .add(ModBlocks.AUTUMN_TREE_WOOD_WALL.get().asItem())
                .add(ModBlocks.AUTUMN_TREE_WOOD.get().asItem())
                .add(ModBlocks.AUTUMN_TREE_STRIPPED_LOG.get().asItem())
                .add(ModBlocks.AUTUMN_TREE_STRIPPED_WOOD.get().asItem())
                .add(ModBlocks.AUTUMN_TREE_STRIPPED_WOOD_SLAB.get().asItem())
                .add(ModBlocks.AUTUMN_TREE_STRIPPED_WOOD_STAIRS.get().asItem())
                .add(ModBlocks.AUTUMN_TREE_STRIPPED_WOOD_WALL.get().asItem())
                .add(ModBlocks.SPRING_TREE_LOG.get().asItem())
                .add(ModBlocks.SPRING_TREE_CRACKED_LOG.get().asItem())
                .add(ModBlocks.SPRING_TREE_WOOD_SLAB.get().asItem())
                .add(ModBlocks.SPRING_TREE_WOOD_STAIRS.get().asItem())
                .add(ModBlocks.SPRING_TREE_WOOD_WALL.get().asItem())
                .add(ModBlocks.SPRING_TREE_WOOD.get().asItem())
                .add(ModBlocks.SPRING_TREE_STRIPPED_LOG.get().asItem())
                .add(ModBlocks.SPRING_TREE_STRIPPED_WOOD.get().asItem())
                .add(ModBlocks.SPRING_TREE_STRIPPED_WOOD_SLAB.get().asItem())
                .add(ModBlocks.SPRING_TREE_STRIPPED_WOOD_STAIRS.get().asItem())
                .add(ModBlocks.SPRING_TREE_STRIPPED_WOOD_WALL.get().asItem())
                .add(ModBlocks.SUMMER_TREE_LOG.get().asItem())
                .add(ModBlocks.SUMMER_TREE_CRACKED_LOG.get().asItem())
                .add(ModBlocks.SUMMER_TREE_WOOD_SLAB.get().asItem())
                .add(ModBlocks.SUMMER_TREE_WOOD_STAIRS.get().asItem())
                .add(ModBlocks.SUMMER_TREE_WOOD_WALL.get().asItem())
                .add(ModBlocks.SUMMER_TREE_WOOD.get().asItem())
                .add(ModBlocks.SUMMER_TREE_STRIPPED_LOG.get().asItem())
                .add(ModBlocks.SUMMER_TREE_STRIPPED_WOOD.get().asItem())
                .add(ModBlocks.SUMMER_TREE_STRIPPED_WOOD_SLAB.get().asItem())
                .add(ModBlocks.SUMMER_TREE_STRIPPED_WOOD_STAIRS.get().asItem())
                .add(ModBlocks.SUMMER_TREE_STRIPPED_WOOD_WALL.get().asItem())
                .add(ModBlocks.WINTER_TREE_LOG.get().asItem())
                .add(ModBlocks.WINTER_TREE_CRACKED_LOG.get().asItem())
                .add(ModBlocks.WINTER_TREE_WOOD_SLAB.get().asItem())
                .add(ModBlocks.WINTER_TREE_WOOD_STAIRS.get().asItem())
                .add(ModBlocks.WINTER_TREE_WOOD_WALL.get().asItem())
                .add(ModBlocks.WINTER_TREE_WOOD.get().asItem())
                .add(ModBlocks.WINTER_TREE_STRIPPED_LOG.get().asItem())
                .add(ModBlocks.WINTER_TREE_STRIPPED_WOOD.get().asItem())
                .add(ModBlocks.WINTER_TREE_STRIPPED_WOOD_SLAB.get().asItem())
                .add(ModBlocks.WINTER_TREE_STRIPPED_WOOD_STAIRS.get().asItem())
                .add(ModBlocks.WINTER_TREE_STRIPPED_WOOD_WALL.get().asItem());

        tag(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.AUTUMN_TREE_LOG.get().asItem())
                .add(ModBlocks.AUTUMN_TREE_CRACKED_LOG.get().asItem())
                .add(ModBlocks.AUTUMN_TREE_WOOD_SLAB.get().asItem())
                .add(ModBlocks.AUTUMN_TREE_WOOD_STAIRS.get().asItem())
                .add(ModBlocks.AUTUMN_TREE_WOOD_WALL.get().asItem())
                .add(ModBlocks.AUTUMN_TREE_WOOD.get().asItem())
                .add(ModBlocks.AUTUMN_TREE_STRIPPED_LOG.get().asItem())
                .add(ModBlocks.AUTUMN_TREE_STRIPPED_WOOD.get().asItem())
                .add(ModBlocks.AUTUMN_TREE_STRIPPED_WOOD_SLAB.get().asItem())
                .add(ModBlocks.AUTUMN_TREE_STRIPPED_WOOD_STAIRS.get().asItem())
                .add(ModBlocks.AUTUMN_TREE_STRIPPED_WOOD_WALL.get().asItem())
                .add(ModBlocks.AUTUMN_TREE_PLANKS.get().asItem())
                .add(ModBlocks.AUTUMN_TREE_PLANKS_SLAB.get().asItem())
                .add(ModBlocks.AUTUMN_TREE_PLANKS_STAIRS.get().asItem())
                .add(ModBlocks.AUTUMN_TREE_PLANKS_FENCE.get().asItem())
                .add(ModBlocks.AUTUMN_TREE_PLANKS_FENCE_GATE.get().asItem())
                .add(ModBlocks.AUTUMN_TREE_PLANKS_PRESSURE_PLATE.get().asItem())
                .add(ModBlocks.AUTUMN_TREE_PLANKS_BUTTON.get().asItem())
                .add(ModBlocks.SPRING_TREE_LOG.get().asItem())
                .add(ModBlocks.SPRING_TREE_CRACKED_LOG.get().asItem())
                .add(ModBlocks.SPRING_TREE_WOOD_SLAB.get().asItem())
                .add(ModBlocks.SPRING_TREE_WOOD_STAIRS.get().asItem())
                .add(ModBlocks.SPRING_TREE_WOOD_WALL.get().asItem())
                .add(ModBlocks.SPRING_TREE_WOOD.get().asItem())
                .add(ModBlocks.SPRING_TREE_STRIPPED_LOG.get().asItem())
                .add(ModBlocks.SPRING_TREE_STRIPPED_WOOD.get().asItem())
                .add(ModBlocks.SPRING_TREE_STRIPPED_WOOD_SLAB.get().asItem())
                .add(ModBlocks.SPRING_TREE_STRIPPED_WOOD_STAIRS.get().asItem())
                .add(ModBlocks.SPRING_TREE_STRIPPED_WOOD_WALL.get().asItem())
                .add(ModBlocks.SPRING_TREE_PLANKS.get().asItem())
                .add(ModBlocks.SPRING_TREE_PLANKS_SLAB.get().asItem())
                .add(ModBlocks.SPRING_TREE_PLANKS_STAIRS.get().asItem())
                .add(ModBlocks.SPRING_TREE_PLANKS_FENCE.get().asItem())
                .add(ModBlocks.SPRING_TREE_PLANKS_FENCE_GATE.get().asItem())
                .add(ModBlocks.SPRING_TREE_PLANKS_PRESSURE_PLATE.get().asItem())
                .add(ModBlocks.SPRING_TREE_PLANKS_BUTTON.get().asItem())
                .add(ModBlocks.SUMMER_TREE_LOG.get().asItem())
                .add(ModBlocks.SUMMER_TREE_CRACKED_LOG.get().asItem())
                .add(ModBlocks.SUMMER_TREE_WOOD_SLAB.get().asItem())
                .add(ModBlocks.SUMMER_TREE_WOOD_STAIRS.get().asItem())
                .add(ModBlocks.SUMMER_TREE_WOOD_WALL.get().asItem())
                .add(ModBlocks.SUMMER_TREE_WOOD.get().asItem())
                .add(ModBlocks.SUMMER_TREE_STRIPPED_LOG.get().asItem())
                .add(ModBlocks.SUMMER_TREE_STRIPPED_WOOD.get().asItem())
                .add(ModBlocks.SUMMER_TREE_STRIPPED_WOOD_SLAB.get().asItem())
                .add(ModBlocks.SUMMER_TREE_STRIPPED_WOOD_STAIRS.get().asItem())
                .add(ModBlocks.SUMMER_TREE_STRIPPED_WOOD_WALL.get().asItem())
                .add(ModBlocks.SUMMER_TREE_PLANKS.get().asItem())
                .add(ModBlocks.SUMMER_TREE_PLANKS_SLAB.get().asItem())
                .add(ModBlocks.SUMMER_TREE_PLANKS_STAIRS.get().asItem())
                .add(ModBlocks.SUMMER_TREE_PLANKS_FENCE.get().asItem())
                .add(ModBlocks.SUMMER_TREE_PLANKS_FENCE_GATE.get().asItem())
                .add(ModBlocks.SUMMER_TREE_PLANKS_PRESSURE_PLATE.get().asItem())
                .add(ModBlocks.SUMMER_TREE_PLANKS_BUTTON.get().asItem())
                .add(ModBlocks.WINTER_TREE_LOG.get().asItem())
                .add(ModBlocks.WINTER_TREE_CRACKED_LOG.get().asItem())
                .add(ModBlocks.WINTER_TREE_WOOD_SLAB.get().asItem())
                .add(ModBlocks.WINTER_TREE_WOOD_STAIRS.get().asItem())
                .add(ModBlocks.WINTER_TREE_WOOD_WALL.get().asItem())
                .add(ModBlocks.WINTER_TREE_WOOD.get().asItem())
                .add(ModBlocks.WINTER_TREE_STRIPPED_LOG.get().asItem())
                .add(ModBlocks.WINTER_TREE_STRIPPED_WOOD.get().asItem())
                .add(ModBlocks.WINTER_TREE_STRIPPED_WOOD_SLAB.get().asItem())
                .add(ModBlocks.WINTER_TREE_STRIPPED_WOOD_STAIRS.get().asItem())
                .add(ModBlocks.WINTER_TREE_STRIPPED_WOOD_WALL.get().asItem())
                .add(ModBlocks.WINTER_TREE_PLANKS.get().asItem())
                .add(ModBlocks.WINTER_TREE_PLANKS_SLAB.get().asItem())
                .add(ModBlocks.WINTER_TREE_PLANKS_STAIRS.get().asItem())
                .add(ModBlocks.WINTER_TREE_PLANKS_FENCE.get().asItem())
                .add(ModBlocks.WINTER_TREE_PLANKS_FENCE_GATE.get().asItem())
                .add(ModBlocks.WINTER_TREE_PLANKS_PRESSURE_PLATE.get().asItem())
                .add(ModBlocks.WINTER_TREE_PLANKS_BUTTON.get().asItem());


        tag(ItemTags.PLANKS)
                .add(ModBlocks.AUTUMN_TREE_PLANKS.get().asItem())
                .add(ModBlocks.SPRING_TREE_PLANKS.get().asItem())
                .add(ModBlocks.SUMMER_TREE_PLANKS.get().asItem())
                .add(ModBlocks.WINTER_TREE_PLANKS.get().asItem());

        tag(ItemTags.DOORS)
                .add(ModBlocks.AUTUMN_TREE_PLANKS_DOOR.get().asItem())
                .add(ModBlocks.SPRING_TREE_PLANKS_DOOR.get().asItem())
                .add(ModBlocks.SUMMER_TREE_PLANKS_DOOR.get().asItem())
                .add(ModBlocks.WINTER_TREE_PLANKS_DOOR.get().asItem());

        tag(ItemTags.TRAPDOORS)
                .add(ModBlocks.AUTUMN_TREE_PLANKS_TRAPDOOR.get().asItem())
                .add(ModBlocks.SPRING_TREE_PLANKS_TRAPDOOR.get().asItem())
                .add(ModBlocks.SUMMER_TREE_PLANKS_TRAPDOOR.get().asItem())
                .add(ModBlocks.WINTER_TREE_PLANKS_TRAPDOOR.get().asItem());

        tag(ItemTags.BUTTONS)
                .add(ModBlocks.AUTUMN_TREE_PLANKS_BUTTON.get().asItem())
                .add(ModBlocks.SPRING_TREE_PLANKS_BUTTON.get().asItem())
                .add(ModBlocks.SUMMER_TREE_PLANKS_BUTTON.get().asItem())
                .add(ModBlocks.WINTER_TREE_PLANKS_BUTTON.get().asItem());
    }

}
