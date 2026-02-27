package com.saphienyako.feywild.datagen;


import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.block.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.List;
import java.util.stream.IntStream;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Feywild.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.FEY_GEM_ORE);
        blockWithItem(ModBlocks.FEY_GEM_ORE_DEEP_SLATE);

        //DEFAULT ELVEN QUARTZ
        blockWithItem(ModBlocks.ELVEN_QUARTZ_BLOCK);
        stairsBlock(((StairBlock) ModBlocks.ELVEN_QUARTZ_STAIRS.get()), blockTexture(ModBlocks.ELVEN_QUARTZ_BLOCK.get()));
        slabBlock(((SlabBlock) ModBlocks.ELVEN_QUARTZ_SLAB.get()), blockTexture(ModBlocks.ELVEN_QUARTZ_BLOCK.get()), blockTexture(ModBlocks.ELVEN_QUARTZ_BLOCK.get()));
        blockItem(ModBlocks.ELVEN_QUARTZ_STAIRS);
        blockItem(ModBlocks.ELVEN_QUARTZ_SLAB);

        blockWithItem(ModBlocks.ELVEN_QUARTZ_BRICK);
        stairsBlock(((StairBlock) ModBlocks.ELVEN_QUARTZ_BRICK_STAIRS.get()), blockTexture(ModBlocks.ELVEN_QUARTZ_BRICK.get()));
        slabBlock(((SlabBlock) ModBlocks.ELVEN_QUARTZ_BRICK_SLAB.get()), blockTexture(ModBlocks.ELVEN_QUARTZ_BRICK.get()), blockTexture(ModBlocks.ELVEN_QUARTZ_BRICK.get()));
        blockItem(ModBlocks.ELVEN_QUARTZ_BRICK_STAIRS);
        blockItem(ModBlocks.ELVEN_QUARTZ_BRICK_SLAB);

        //SKIP MOSSY

        blockWithItem(ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK);
        stairsBlock(((StairBlock) ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get()), blockTexture(ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK.get()));
        slabBlock(((SlabBlock) ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get()), blockTexture(ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK.get()), blockTexture(ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK.get()));
        blockItem(ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK_STAIRS);
        blockItem(ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK_SLAB);

        axisBlock((RotatedPillarBlock) ModBlocks.ELVEN_QUARTZ_PILLAR.get(),
                blockTexture(ModBlocks.ELVEN_QUARTZ_PILLAR.get()),
                modLoc("block/elven_quartz_pillar_top")
        );
        blockItem(ModBlocks.ELVEN_QUARTZ_PILLAR);

        blockWithItem(ModBlocks.ELVEN_QUARTZ_POLISHED);
        stairsBlock(((StairBlock) ModBlocks.ELVEN_QUARTZ_POLISHED_STAIRS.get()), blockTexture(ModBlocks.ELVEN_QUARTZ_POLISHED.get()));
        slabBlock(((SlabBlock) ModBlocks.ELVEN_QUARTZ_POLISHED_SLAB.get()), blockTexture(ModBlocks.ELVEN_QUARTZ_POLISHED.get()), blockTexture(ModBlocks.ELVEN_QUARTZ_POLISHED.get()));
        blockItem(ModBlocks.ELVEN_QUARTZ_POLISHED_STAIRS);
        blockItem(ModBlocks.ELVEN_QUARTZ_POLISHED_SLAB);

        //SPRING
        blockWithItem(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK);
        stairsBlock(((StairBlock) ModBlocks.SPRING_ELVEN_QUARTZ_STAIRS.get()), blockTexture(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get()));
        slabBlock(((SlabBlock) ModBlocks.SPRING_ELVEN_QUARTZ_SLAB.get()), blockTexture(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get()), blockTexture(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get()));
        blockItem(ModBlocks.SPRING_ELVEN_QUARTZ_STAIRS);
        blockItem(ModBlocks.SPRING_ELVEN_QUARTZ_SLAB);

        blockWithItem(ModBlocks.SPRING_ELVEN_QUARTZ_BRICK);
        stairsBlock(((StairBlock) ModBlocks.SPRING_ELVEN_QUARTZ_BRICK_STAIRS.get()), blockTexture(ModBlocks.SPRING_ELVEN_QUARTZ_BRICK.get()));
        slabBlock(((SlabBlock) ModBlocks.SPRING_ELVEN_QUARTZ_BRICK_SLAB.get()), blockTexture(ModBlocks.SPRING_ELVEN_QUARTZ_BRICK.get()), blockTexture(ModBlocks.SPRING_ELVEN_QUARTZ_BRICK.get()));
        blockItem(ModBlocks.SPRING_ELVEN_QUARTZ_BRICK_STAIRS);
        blockItem(ModBlocks.SPRING_ELVEN_QUARTZ_BRICK_SLAB);

        //SKIP MOSSY

        blockWithItem(ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK);
        stairsBlock(((StairBlock) ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get()), blockTexture(ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK.get()));
        slabBlock(((SlabBlock) ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get()), blockTexture(ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK.get()), blockTexture(ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK.get()));
        blockItem(ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS);
        blockItem(ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK_SLAB);

        axisBlock((RotatedPillarBlock) ModBlocks.SPRING_ELVEN_QUARTZ_PILLAR.get(),
                blockTexture(ModBlocks.SPRING_ELVEN_QUARTZ_PILLAR.get()),
                modLoc("block/spring_elven_quartz_pillar_top")
        );
        blockItem(ModBlocks.SPRING_ELVEN_QUARTZ_PILLAR);

        blockWithItem(ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED);
        stairsBlock(((StairBlock) ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED_STAIRS.get()), blockTexture(ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED.get()));
        slabBlock(((SlabBlock) ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED_SLAB.get()), blockTexture(ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED.get()), blockTexture(ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED.get()));
        blockItem(ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED_STAIRS);
        blockItem(ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED_SLAB);

        //SUMMER
        blockWithItem(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK);
        stairsBlock(((StairBlock) ModBlocks.SUMMER_ELVEN_QUARTZ_STAIRS.get()), blockTexture(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get()));
        slabBlock(((SlabBlock) ModBlocks.SUMMER_ELVEN_QUARTZ_SLAB.get()), blockTexture(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get()), blockTexture(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get()));
        blockItem(ModBlocks.SUMMER_ELVEN_QUARTZ_STAIRS);
        blockItem(ModBlocks.SUMMER_ELVEN_QUARTZ_SLAB);

        blockWithItem(ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK);
        stairsBlock(((StairBlock) ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK_STAIRS.get()), blockTexture(ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK.get()));
        slabBlock(((SlabBlock) ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK_SLAB.get()), blockTexture(ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK.get()), blockTexture(ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK.get()));
        blockItem(ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK_STAIRS);
        blockItem(ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK_SLAB);

        //SKIP MOSSY

        blockWithItem(ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK);
        stairsBlock(((StairBlock) ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get()), blockTexture(ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK.get()));
        slabBlock(((SlabBlock) ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get()), blockTexture(ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK.get()), blockTexture(ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK.get()));
        blockItem(ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS);
        blockItem(ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK_SLAB);

        axisBlock((RotatedPillarBlock) ModBlocks.SUMMER_ELVEN_QUARTZ_PILLAR.get(),
                blockTexture(ModBlocks.SUMMER_ELVEN_QUARTZ_PILLAR.get()),
                modLoc("block/summer_elven_quartz_pillar_top")
        );
        blockItem(ModBlocks.SUMMER_ELVEN_QUARTZ_PILLAR);

        blockWithItem(ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED);
        stairsBlock(((StairBlock) ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED_STAIRS.get()), blockTexture(ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED.get()));
        slabBlock(((SlabBlock) ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED_SLAB.get()), blockTexture(ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED.get()), blockTexture(ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED.get()));
        blockItem(ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED_STAIRS);
        blockItem(ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED_SLAB);

        //WINTER
        blockWithItem(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK);
        stairsBlock(((StairBlock) ModBlocks.WINTER_ELVEN_QUARTZ_STAIRS.get()), blockTexture(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get()));
        slabBlock(((SlabBlock) ModBlocks.WINTER_ELVEN_QUARTZ_SLAB.get()), blockTexture(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get()), blockTexture(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get()));
        blockItem(ModBlocks.WINTER_ELVEN_QUARTZ_STAIRS);
        blockItem(ModBlocks.WINTER_ELVEN_QUARTZ_SLAB);

        blockWithItem(ModBlocks.WINTER_ELVEN_QUARTZ_BRICK);
        stairsBlock(((StairBlock) ModBlocks.WINTER_ELVEN_QUARTZ_BRICK_STAIRS.get()), blockTexture(ModBlocks.WINTER_ELVEN_QUARTZ_BRICK.get()));
        slabBlock(((SlabBlock) ModBlocks.WINTER_ELVEN_QUARTZ_BRICK_SLAB.get()), blockTexture(ModBlocks.WINTER_ELVEN_QUARTZ_BRICK.get()), blockTexture(ModBlocks.WINTER_ELVEN_QUARTZ_BRICK.get()));
        blockItem(ModBlocks.WINTER_ELVEN_QUARTZ_BRICK_STAIRS);
        blockItem(ModBlocks.WINTER_ELVEN_QUARTZ_BRICK_SLAB);

        //SKIP MOSSY

        blockWithItem(ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK);
        stairsBlock(((StairBlock) ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get()), blockTexture(ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK.get()));
        slabBlock(((SlabBlock) ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get()), blockTexture(ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK.get()), blockTexture(ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK.get()));
        blockItem(ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS);
        blockItem(ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK_SLAB);

        axisBlock((RotatedPillarBlock) ModBlocks.WINTER_ELVEN_QUARTZ_PILLAR.get(),
                blockTexture(ModBlocks.WINTER_ELVEN_QUARTZ_PILLAR.get()),
                modLoc("block/winter_elven_quartz_pillar_top")
        );
        blockItem(ModBlocks.WINTER_ELVEN_QUARTZ_PILLAR);

        blockWithItem(ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED);
        stairsBlock(((StairBlock) ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED_STAIRS.get()), blockTexture(ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED.get()));
        slabBlock(((SlabBlock) ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED_SLAB.get()), blockTexture(ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED.get()), blockTexture(ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED.get()));
        blockItem(ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED_STAIRS);
        blockItem(ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED_SLAB);
        //AUTUMN
        blockWithItem(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK);
        stairsBlock(((StairBlock) ModBlocks.AUTUMN_ELVEN_QUARTZ_STAIRS.get()), blockTexture(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get()));
        slabBlock(((SlabBlock) ModBlocks.AUTUMN_ELVEN_QUARTZ_SLAB.get()), blockTexture(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get()), blockTexture(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get()));
        blockItem(ModBlocks.AUTUMN_ELVEN_QUARTZ_STAIRS);
        blockItem(ModBlocks.AUTUMN_ELVEN_QUARTZ_SLAB);

        blockWithItem(ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK);
        stairsBlock(((StairBlock) ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK_STAIRS.get()), blockTexture(ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK.get()));
        slabBlock(((SlabBlock) ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK_SLAB.get()), blockTexture(ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK.get()), blockTexture(ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK.get()));
        blockItem(ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK_STAIRS);
        blockItem(ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK_SLAB);

        //SKIP MOSSY

        blockWithItem(ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK);
        stairsBlock(((StairBlock) ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get()), blockTexture(ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK.get()));
        slabBlock(((SlabBlock) ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get()), blockTexture(ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK.get()), blockTexture(ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK.get()));
        blockItem(ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS);
        blockItem(ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK_SLAB);

        axisBlock((RotatedPillarBlock) ModBlocks.AUTUMN_ELVEN_QUARTZ_PILLAR.get(),
                blockTexture(ModBlocks.AUTUMN_ELVEN_QUARTZ_PILLAR.get()),
                modLoc("block/autumn_elven_quartz_pillar_top")
        );
        blockItem(ModBlocks.AUTUMN_ELVEN_QUARTZ_PILLAR);

        blockWithItem(ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED);
        stairsBlock(((StairBlock) ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED_STAIRS.get()), blockTexture(ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED.get()));
        slabBlock(((SlabBlock) ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED_SLAB.get()), blockTexture(ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED.get()), blockTexture(ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED.get()));
        blockItem(ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED_STAIRS);
        blockItem(ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED_SLAB);

        //TREES
        //AUTUMN
        //LOG
        logBlock(((RotatedPillarBlock) ModBlocks.AUTUMN_TREE_LOG.get()));

        //CRACKEDLOG
       // logBlock(((RotatedPillarBlock) ModBlocks.AUTUMN_TREE_CRACKED_LOG.get()));

        //WOOD
        axisBlock((RotatedPillarBlock) ModBlocks.AUTUMN_TREE_WOOD.get(), blockTexture(ModBlocks.AUTUMN_TREE_LOG.get()), blockTexture(ModBlocks.AUTUMN_TREE_LOG.get()));
        stairsBlock((StairBlock) ModBlocks.AUTUMN_TREE_WOOD_STAIRS.get(), blockTexture(ModBlocks.AUTUMN_TREE_LOG.get()));
        slabBlock((SlabBlock) ModBlocks.AUTUMN_TREE_WOOD_SLAB.get(), blockTexture(ModBlocks.AUTUMN_TREE_LOG.get()), blockTexture(ModBlocks.AUTUMN_TREE_LOG.get()));
        wallBlock((WallBlock) ModBlocks.AUTUMN_TREE_WOOD_WALL.get(), blockTexture(ModBlocks.AUTUMN_TREE_LOG.get()));

        // STRIPPED LOG
        logBlock(((RotatedPillarBlock) ModBlocks.AUTUMN_TREE_STRIPPED_LOG.get()));

        //STRIPPED WOOD
        axisBlock((RotatedPillarBlock) ModBlocks.AUTUMN_TREE_STRIPPED_WOOD.get(), blockTexture(ModBlocks.AUTUMN_TREE_STRIPPED_LOG.get()), blockTexture(ModBlocks.AUTUMN_TREE_STRIPPED_LOG.get()));
        stairsBlock((StairBlock) ModBlocks.AUTUMN_TREE_STRIPPED_WOOD_STAIRS.get(), blockTexture(ModBlocks.AUTUMN_TREE_STRIPPED_LOG.get()));
        slabBlock((SlabBlock) ModBlocks.AUTUMN_TREE_STRIPPED_WOOD_SLAB.get(), blockTexture(ModBlocks.AUTUMN_TREE_STRIPPED_LOG.get()), blockTexture(ModBlocks.AUTUMN_TREE_STRIPPED_LOG.get()));
        wallBlock((WallBlock) ModBlocks.AUTUMN_TREE_STRIPPED_WOOD_WALL.get(), blockTexture(ModBlocks.AUTUMN_TREE_STRIPPED_LOG.get()));

        //PLANKS
        blockWithItem(ModBlocks.AUTUMN_TREE_PLANKS);
        stairsBlock((StairBlock) ModBlocks.AUTUMN_TREE_PLANKS_STAIRS.get(), blockTexture(ModBlocks.AUTUMN_TREE_PLANKS.get()));
        slabBlock((SlabBlock) ModBlocks.AUTUMN_TREE_PLANKS_SLAB.get(), blockTexture(ModBlocks.AUTUMN_TREE_PLANKS.get()), blockTexture(ModBlocks.AUTUMN_TREE_PLANKS.get()));
        fenceBlock((FenceBlock) ModBlocks.AUTUMN_TREE_PLANKS_FENCE.get(), blockTexture(ModBlocks.AUTUMN_TREE_PLANKS.get()));
        fenceGateBlock((FenceGateBlock) ModBlocks.AUTUMN_TREE_PLANKS_FENCE_GATE.get(), blockTexture(ModBlocks.AUTUMN_TREE_PLANKS.get()));
        //door Block manual
        //trapdoor Block manual
        pressurePlateBlock(((PressurePlateBlock) ModBlocks.AUTUMN_TREE_PLANKS_PRESSURE_PLATE.get()), blockTexture(ModBlocks.AUTUMN_TREE_PLANKS.get()));
        buttonBlock(((ButtonBlock) ModBlocks.AUTUMN_TREE_PLANKS_BUTTON.get()), blockTexture(ModBlocks.AUTUMN_TREE_PLANKS.get()));

        //LEAVES AND SAPLING
        leavesBlock(ModBlocks.AUTUMN_TREE_LEAVES_BROWN);
        leavesBlock(ModBlocks.AUTUMN_TREE_LEAVES_RED);
        leavesBlock(ModBlocks.AUTUMN_TREE_LEAVES_LIGHT_GRAY);
        leavesBlock(ModBlocks.AUTUMN_TREE_LEAVES_DARK_GRAY);
        saplingBlock(ModBlocks.AUTUMN_TREE_SAPLING);

        // ITEMS
        blockItem(ModBlocks.AUTUMN_TREE_LOG);
        //crackedLogBlock
        blockItem(ModBlocks.AUTUMN_TREE_WOOD);
        blockItem(ModBlocks.AUTUMN_TREE_WOOD_STAIRS);
        blockItem(ModBlocks.AUTUMN_TREE_WOOD_SLAB);
        wallItem(ModBlocks.AUTUMN_TREE_WOOD_WALL, ModBlocks.AUTUMN_TREE_LOG);
        blockItem(ModBlocks.AUTUMN_TREE_STRIPPED_LOG);
        blockItem(ModBlocks.AUTUMN_TREE_STRIPPED_WOOD);
        blockItem(ModBlocks.AUTUMN_TREE_STRIPPED_WOOD_STAIRS);
        blockItem(ModBlocks.AUTUMN_TREE_STRIPPED_WOOD_SLAB);
        wallItem(ModBlocks.AUTUMN_TREE_STRIPPED_WOOD_WALL, ModBlocks.AUTUMN_TREE_STRIPPED_LOG);
        blockItem(ModBlocks.AUTUMN_TREE_PLANKS_SLAB);
        blockItem(ModBlocks.AUTUMN_TREE_PLANKS_STAIRS);
        fenceItem(ModBlocks.AUTUMN_TREE_PLANKS_FENCE, ModBlocks.AUTUMN_TREE_PLANKS);
        blockItem(ModBlocks.AUTUMN_TREE_PLANKS_FENCE_GATE);
        //Doors require a manual setup for Item model
        //Trapdoor require a manual setup for Item model
        blockItem(ModBlocks.AUTUMN_TREE_PLANKS_PRESSURE_PLATE);
        blockItem(ModBlocks.AUTUMN_TREE_PLANKS_BUTTON);

        //SPRING
        //LOG
        logBlock(((RotatedPillarBlock) ModBlocks.SPRING_TREE_LOG.get()));

        //CRACKEDLOG
        //logBlock(((RotatedPillarBlock) ModBlocks.SPRING_TREE_CRACKED_LOG.get()));

        //WOOD
        axisBlock((RotatedPillarBlock) ModBlocks.SPRING_TREE_WOOD.get(), blockTexture(ModBlocks.SPRING_TREE_LOG.get()), blockTexture(ModBlocks.SPRING_TREE_LOG.get()));
        stairsBlock((StairBlock) ModBlocks.SPRING_TREE_WOOD_STAIRS.get(), blockTexture(ModBlocks.SPRING_TREE_LOG.get()));
        slabBlock((SlabBlock) ModBlocks.SPRING_TREE_WOOD_SLAB.get(), blockTexture(ModBlocks.SPRING_TREE_LOG.get()), blockTexture(ModBlocks.SPRING_TREE_LOG.get()));
        wallBlock((WallBlock) ModBlocks.SPRING_TREE_WOOD_WALL.get(), blockTexture(ModBlocks.SPRING_TREE_LOG.get()));

        // STRIPPED LOG
        logBlock(((RotatedPillarBlock) ModBlocks.SPRING_TREE_STRIPPED_LOG.get()));

        //STRIPPED WOOD
        axisBlock((RotatedPillarBlock) ModBlocks.SPRING_TREE_STRIPPED_WOOD.get(), blockTexture(ModBlocks.SPRING_TREE_STRIPPED_LOG.get()), blockTexture(ModBlocks.SPRING_TREE_STRIPPED_LOG.get()));
        stairsBlock((StairBlock) ModBlocks.SPRING_TREE_STRIPPED_WOOD_STAIRS.get(), blockTexture(ModBlocks.SPRING_TREE_STRIPPED_LOG.get()));
        slabBlock((SlabBlock) ModBlocks.SPRING_TREE_STRIPPED_WOOD_SLAB.get(), blockTexture(ModBlocks.SPRING_TREE_STRIPPED_LOG.get()), blockTexture(ModBlocks.SPRING_TREE_STRIPPED_LOG.get()));
        wallBlock((WallBlock) ModBlocks.SPRING_TREE_STRIPPED_WOOD_WALL.get(), blockTexture(ModBlocks.SPRING_TREE_STRIPPED_LOG.get()));

        //PLANKS
        blockWithItem(ModBlocks.SPRING_TREE_PLANKS);
        stairsBlock((StairBlock) ModBlocks.SPRING_TREE_PLANKS_STAIRS.get(), blockTexture(ModBlocks.SPRING_TREE_PLANKS.get()));
        slabBlock((SlabBlock) ModBlocks.SPRING_TREE_PLANKS_SLAB.get(), blockTexture(ModBlocks.SPRING_TREE_PLANKS.get()), blockTexture(ModBlocks.SPRING_TREE_PLANKS.get()));
        fenceBlock((FenceBlock) ModBlocks.SPRING_TREE_PLANKS_FENCE.get(), blockTexture(ModBlocks.SPRING_TREE_PLANKS.get()));
        fenceGateBlock((FenceGateBlock) ModBlocks.SPRING_TREE_PLANKS_FENCE_GATE.get(), blockTexture(ModBlocks.SPRING_TREE_PLANKS.get()));
        //door Block manual
        //trapdoor Block manual
        pressurePlateBlock(((PressurePlateBlock) ModBlocks.SPRING_TREE_PLANKS_PRESSURE_PLATE.get()), blockTexture(ModBlocks.SPRING_TREE_PLANKS.get()));
        buttonBlock(((ButtonBlock) ModBlocks.SPRING_TREE_PLANKS_BUTTON.get()), blockTexture(ModBlocks.SPRING_TREE_PLANKS.get()));

        //LEAVES AND SAPLING
        leavesBlock(ModBlocks.SPRING_TREE_LEAVES_CYAN);
        leavesBlock(ModBlocks.SPRING_TREE_LEAVES_GREEN);
        leavesBlock(ModBlocks.SPRING_TREE_LEAVES_LIME);
        saplingBlock(ModBlocks.SPRING_TREE_SAPLING);

        // ITEMS
        blockItem(ModBlocks.SPRING_TREE_LOG);
        //blockItem(ModBlocks.SPRING_TREE_CRACKED_LOG);
        blockItem(ModBlocks.SPRING_TREE_WOOD);
        blockItem(ModBlocks.SPRING_TREE_WOOD_STAIRS);
        blockItem(ModBlocks.SPRING_TREE_WOOD_SLAB);
        wallItem(ModBlocks.SPRING_TREE_WOOD_WALL, ModBlocks.SPRING_TREE_LOG);
        blockItem(ModBlocks.SPRING_TREE_STRIPPED_LOG);
        blockItem(ModBlocks.SPRING_TREE_STRIPPED_WOOD);
        blockItem(ModBlocks.SPRING_TREE_STRIPPED_WOOD_STAIRS);
        blockItem(ModBlocks.SPRING_TREE_STRIPPED_WOOD_SLAB);
        wallItem(ModBlocks.SPRING_TREE_STRIPPED_WOOD_WALL, ModBlocks.SPRING_TREE_STRIPPED_LOG);
        blockItem(ModBlocks.SPRING_TREE_PLANKS_SLAB);
        blockItem(ModBlocks.SPRING_TREE_PLANKS_STAIRS);
        fenceItem(ModBlocks.SPRING_TREE_PLANKS_FENCE, ModBlocks.SPRING_TREE_PLANKS);
        blockItem(ModBlocks.SPRING_TREE_PLANKS_FENCE_GATE);
        //Doors require a manual setup for Item model
        //Trapdoor require a manual setup for Item model
        blockItem(ModBlocks.SPRING_TREE_PLANKS_PRESSURE_PLATE);
        blockItem(ModBlocks.SPRING_TREE_PLANKS_BUTTON);

        //SUMMER
        //LOG
        logBlock(((RotatedPillarBlock) ModBlocks.SUMMER_TREE_LOG.get()));

        //CRACKEDLOG
        //logBlock(((RotatedPillarBlock) ModBlocks.SUMMER_TREE_CRACKED_LOG.get()));

        //WOOD
        axisBlock((RotatedPillarBlock) ModBlocks.SUMMER_TREE_WOOD.get(), blockTexture(ModBlocks.SUMMER_TREE_LOG.get()), blockTexture(ModBlocks.SUMMER_TREE_LOG.get()));
        stairsBlock((StairBlock) ModBlocks.SUMMER_TREE_WOOD_STAIRS.get(), blockTexture(ModBlocks.SUMMER_TREE_LOG.get()));
        slabBlock((SlabBlock) ModBlocks.SUMMER_TREE_WOOD_SLAB.get(), blockTexture(ModBlocks.SUMMER_TREE_LOG.get()), blockTexture(ModBlocks.SUMMER_TREE_LOG.get()));
        wallBlock((WallBlock) ModBlocks.SUMMER_TREE_WOOD_WALL.get(), blockTexture(ModBlocks.SUMMER_TREE_LOG.get()));

        // STRIPPED LOG
        logBlock(((RotatedPillarBlock) ModBlocks.SUMMER_TREE_STRIPPED_LOG.get()));

        //STRIPPED WOOD
        axisBlock((RotatedPillarBlock) ModBlocks.SUMMER_TREE_STRIPPED_WOOD.get(), blockTexture(ModBlocks.SUMMER_TREE_STRIPPED_LOG.get()), blockTexture(ModBlocks.SUMMER_TREE_STRIPPED_LOG.get()));
        stairsBlock((StairBlock) ModBlocks.SUMMER_TREE_STRIPPED_WOOD_STAIRS.get(), blockTexture(ModBlocks.SUMMER_TREE_STRIPPED_LOG.get()));
        slabBlock((SlabBlock) ModBlocks.SUMMER_TREE_STRIPPED_WOOD_SLAB.get(), blockTexture(ModBlocks.SUMMER_TREE_STRIPPED_LOG.get()), blockTexture(ModBlocks.SUMMER_TREE_STRIPPED_LOG.get()));
        wallBlock((WallBlock) ModBlocks.SUMMER_TREE_STRIPPED_WOOD_WALL.get(), blockTexture(ModBlocks.SUMMER_TREE_STRIPPED_LOG.get()));

        //PLANKS
        blockWithItem(ModBlocks.SUMMER_TREE_PLANKS);
        stairsBlock((StairBlock) ModBlocks.SUMMER_TREE_PLANKS_STAIRS.get(), blockTexture(ModBlocks.SUMMER_TREE_PLANKS.get()));
        slabBlock((SlabBlock) ModBlocks.SUMMER_TREE_PLANKS_SLAB.get(), blockTexture(ModBlocks.SUMMER_TREE_PLANKS.get()), blockTexture(ModBlocks.SUMMER_TREE_PLANKS.get()));
        fenceBlock((FenceBlock) ModBlocks.SUMMER_TREE_PLANKS_FENCE.get(), blockTexture(ModBlocks.SUMMER_TREE_PLANKS.get()));
        fenceGateBlock((FenceGateBlock) ModBlocks.SUMMER_TREE_PLANKS_FENCE_GATE.get(), blockTexture(ModBlocks.SUMMER_TREE_PLANKS.get()));
        //door Block manual
        //trapdoor Block manual
        pressurePlateBlock(((PressurePlateBlock) ModBlocks.SUMMER_TREE_PLANKS_PRESSURE_PLATE.get()), blockTexture(ModBlocks.SUMMER_TREE_PLANKS.get()));
        buttonBlock(((ButtonBlock) ModBlocks.SUMMER_TREE_PLANKS_BUTTON.get()), blockTexture(ModBlocks.SUMMER_TREE_PLANKS.get()));

        //LEAVES AND SAPLING
        leavesBlock(ModBlocks.SUMMER_TREE_LEAVES_ORANGE);
        leavesBlock(ModBlocks.SUMMER_TREE_LEAVES_YELLOW);
        saplingBlock(ModBlocks.SUMMER_TREE_SAPLING);

        // ITEMS
        blockItem(ModBlocks.SUMMER_TREE_LOG);
       // blockItem(ModBlocks.SUMMER_TREE_CRACKED_LOG);
        blockItem(ModBlocks.SUMMER_TREE_WOOD);
        blockItem(ModBlocks.SUMMER_TREE_WOOD_STAIRS);
        blockItem(ModBlocks.SUMMER_TREE_WOOD_SLAB);
        wallItem(ModBlocks.SUMMER_TREE_WOOD_WALL, ModBlocks.SUMMER_TREE_LOG);
        blockItem(ModBlocks.SUMMER_TREE_STRIPPED_LOG);
        blockItem(ModBlocks.SUMMER_TREE_STRIPPED_WOOD);
        blockItem(ModBlocks.SUMMER_TREE_STRIPPED_WOOD_STAIRS);
        blockItem(ModBlocks.SUMMER_TREE_STRIPPED_WOOD_SLAB);
        wallItem(ModBlocks.SUMMER_TREE_STRIPPED_WOOD_WALL, ModBlocks.SUMMER_TREE_STRIPPED_LOG);
        blockItem(ModBlocks.SUMMER_TREE_PLANKS_SLAB);
        blockItem(ModBlocks.SUMMER_TREE_PLANKS_STAIRS);
        fenceItem(ModBlocks.SUMMER_TREE_PLANKS_FENCE, ModBlocks.SUMMER_TREE_PLANKS);
        blockItem(ModBlocks.SUMMER_TREE_PLANKS_FENCE_GATE);
        //Doors require a manual setup for Item model
        //Trapdoor require a manual setup for Item model
        blockItem(ModBlocks.SUMMER_TREE_PLANKS_PRESSURE_PLATE);
        blockItem(ModBlocks.SUMMER_TREE_PLANKS_BUTTON);

        //WINTER
        //LOG
        logBlock(((RotatedPillarBlock) ModBlocks.WINTER_TREE_LOG.get()));

        //CRACKEDLOG
       // logBlock(((RotatedPillarBlock) ModBlocks.WINTER_TREE_CRACKED_LOG.get()));

        //WOOD
        axisBlock((RotatedPillarBlock) ModBlocks.WINTER_TREE_WOOD.get(), blockTexture(ModBlocks.WINTER_TREE_LOG.get()), blockTexture(ModBlocks.WINTER_TREE_LOG.get()));
        stairsBlock((StairBlock) ModBlocks.WINTER_TREE_WOOD_STAIRS.get(), blockTexture(ModBlocks.WINTER_TREE_LOG.get()));
        slabBlock((SlabBlock) ModBlocks.WINTER_TREE_WOOD_SLAB.get(), blockTexture(ModBlocks.WINTER_TREE_LOG.get()), blockTexture(ModBlocks.WINTER_TREE_LOG.get()));
        wallBlock((WallBlock) ModBlocks.WINTER_TREE_WOOD_WALL.get(), blockTexture(ModBlocks.WINTER_TREE_LOG.get()));

        // STRIPPED LOG
        logBlock(((RotatedPillarBlock) ModBlocks.WINTER_TREE_STRIPPED_LOG.get()));

        //STRIPPED WOOD
        axisBlock((RotatedPillarBlock) ModBlocks.WINTER_TREE_STRIPPED_WOOD.get(), blockTexture(ModBlocks.WINTER_TREE_STRIPPED_LOG.get()), blockTexture(ModBlocks.WINTER_TREE_STRIPPED_LOG.get()));
        stairsBlock((StairBlock) ModBlocks.WINTER_TREE_STRIPPED_WOOD_STAIRS.get(), blockTexture(ModBlocks.WINTER_TREE_STRIPPED_LOG.get()));
        slabBlock((SlabBlock) ModBlocks.WINTER_TREE_STRIPPED_WOOD_SLAB.get(), blockTexture(ModBlocks.WINTER_TREE_STRIPPED_LOG.get()), blockTexture(ModBlocks.WINTER_TREE_STRIPPED_LOG.get()));
        wallBlock((WallBlock) ModBlocks.WINTER_TREE_STRIPPED_WOOD_WALL.get(), blockTexture(ModBlocks.WINTER_TREE_STRIPPED_LOG.get()));

        //PLANKS
        blockWithItem(ModBlocks.WINTER_TREE_PLANKS);
        stairsBlock((StairBlock) ModBlocks.WINTER_TREE_PLANKS_STAIRS.get(), blockTexture(ModBlocks.WINTER_TREE_PLANKS.get()));
        slabBlock((SlabBlock) ModBlocks.WINTER_TREE_PLANKS_SLAB.get(), blockTexture(ModBlocks.WINTER_TREE_PLANKS.get()), blockTexture(ModBlocks.WINTER_TREE_PLANKS.get()));
        fenceBlock((FenceBlock) ModBlocks.WINTER_TREE_PLANKS_FENCE.get(), blockTexture(ModBlocks.WINTER_TREE_PLANKS.get()));
        fenceGateBlock((FenceGateBlock) ModBlocks.WINTER_TREE_PLANKS_FENCE_GATE.get(), blockTexture(ModBlocks.WINTER_TREE_PLANKS.get()));
        //door Block manual
        //trapdoor Block manual
        pressurePlateBlock(((PressurePlateBlock) ModBlocks.WINTER_TREE_PLANKS_PRESSURE_PLATE.get()), blockTexture(ModBlocks.WINTER_TREE_PLANKS.get()));
        buttonBlock(((ButtonBlock) ModBlocks.WINTER_TREE_PLANKS_BUTTON.get()), blockTexture(ModBlocks.WINTER_TREE_PLANKS.get()));

        //LEAVES AND SAPLING
        leavesBlock(ModBlocks.WINTER_TREE_LEAVES_LIGHT_BLUE);
        leavesBlock(ModBlocks.WINTER_TREE_LEAVES_BLUE);
        saplingBlock(ModBlocks.WINTER_TREE_SAPLING);

        // ITEMS
        blockItem(ModBlocks.WINTER_TREE_LOG);
       // blockItem(ModBlocks.WINTER_TREE_CRACKED_LOG);
        blockItem(ModBlocks.WINTER_TREE_WOOD);
        blockItem(ModBlocks.WINTER_TREE_WOOD_STAIRS);
        blockItem(ModBlocks.WINTER_TREE_WOOD_SLAB);
        wallItem(ModBlocks.WINTER_TREE_WOOD_WALL, ModBlocks.WINTER_TREE_LOG);
        blockItem(ModBlocks.WINTER_TREE_STRIPPED_LOG);
        blockItem(ModBlocks.WINTER_TREE_STRIPPED_WOOD);
        blockItem(ModBlocks.WINTER_TREE_STRIPPED_WOOD_STAIRS);
        blockItem(ModBlocks.WINTER_TREE_STRIPPED_WOOD_SLAB);
        wallItem(ModBlocks.WINTER_TREE_STRIPPED_WOOD_WALL, ModBlocks.WINTER_TREE_STRIPPED_LOG);
        blockItem(ModBlocks.WINTER_TREE_PLANKS_SLAB);
        blockItem(ModBlocks.WINTER_TREE_PLANKS_STAIRS);
        fenceItem(ModBlocks.WINTER_TREE_PLANKS_FENCE, ModBlocks.WINTER_TREE_PLANKS);
        blockItem(ModBlocks.WINTER_TREE_PLANKS_FENCE_GATE);
        //Doors require a manual setup for Item model
        //Trapdoor require a manual setup for Item model
        blockItem(ModBlocks.WINTER_TREE_PLANKS_PRESSURE_PLATE);
        blockItem(ModBlocks.WINTER_TREE_PLANKS_BUTTON);
    }
    @SuppressWarnings("unused")
    private void leavesBlock(DeferredBlock<Block> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(),
                models().singleTexture(deferredBlock.getId().getPath(), ResourceLocation.parse("minecraft:block/leaves"),
                        "all", blockTexture(deferredBlock.get())).renderType("cutout"));
    }

    private void blockWithItem(DeferredBlock<Block> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }
    @SuppressWarnings("unused")
    private void blockItem(DeferredBlock<Block> deferredBlock) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("feywild:block/" + deferredBlock.getId().getPath()));
    }
    @SuppressWarnings("unused")
    private void blockItem(DeferredBlock<Block> deferredBlock, String appendix) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("feywild:block/" + deferredBlock.getId().getPath() + appendix));
    }
    private void saplingBlock(DeferredBlock<Block> deferredBlock) {
        simpleBlock(deferredBlock.get(), models().cross(BuiltInRegistries.BLOCK.getKey(deferredBlock.get()).getPath(), blockTexture(deferredBlock.get())).renderType("cutout"));
    }

    private void wallItem(DeferredBlock<Block> wall, DeferredBlock<Block> baseBlock) {
        simpleBlockItem(wall.get(), models().withExistingParent(wall.getId().getPath(), mcLoc("block/wall_inventory")).texture("wall", blockTexture(baseBlock.get())));
    }

    private void fenceItem(DeferredBlock<Block> fence, DeferredBlock<Block> baseBlock) {
        simpleBlockItem(fence.get(), models().withExistingParent(fence.getId().getPath(), mcLoc("block/fence_inventory")).texture("texture", blockTexture(baseBlock.get())));
    }
}