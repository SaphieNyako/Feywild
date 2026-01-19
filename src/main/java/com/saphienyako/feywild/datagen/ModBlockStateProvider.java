package com.saphienyako.feywild.datagen;


import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

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
}