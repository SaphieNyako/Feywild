package com.feywild.feywild.data;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.flower.Crocus;
import com.feywild.feywild.block.flower.Dandelion;
import com.feywild.feywild.block.flower.GiantFlowerBlock;
import com.feywild.feywild.block.flower.Sunflower;
import com.feywild.feywild.block.trees.FeyLeavesBlock;
import com.feywild.feywild.block.trees.FeyWoodBlock;
import io.github.noeppi_noeppi.libx.data.provider.BlockStateProviderBase;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.block.Block;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStates extends BlockStateProviderBase {

    public BlockStates(ModX mod, DataGenerator generator, ExistingFileHelper fileHelper) {
        super(mod, generator, fileHelper);
    }

    @Override
    protected void setup() {
        manualModel(ModBlocks.dwarvenAnvil);
        manualModel(ModBlocks.feyAltar);
        manualModel(ModBlocks.libraryBell);
        manualModel(ModBlocks.treeMushroom);
    }

    @Override
    protected void defaultState(ResourceLocation id, Block block, ModelFile model) {
        if (block instanceof FeyLeavesBlock) {
            simpleBlock(block,
                    new ConfiguredModel(cubeAll(block)),
                    new ConfiguredModel(models().cubeAll(id.getPath() + "_02", new ResourceLocation(id.getNamespace(), "block/" + id.getPath() + "_02")))
            );
        } else if (block instanceof FeyWoodBlock) {
            axisBlock((RotatedPillarBlock) block, blockTexture(((FeyWoodBlock) block).getLogBlock()), blockTexture(((FeyWoodBlock) block).getLogBlock()));
        } else if (block instanceof RotatedPillarBlock) {
            axisBlock((RotatedPillarBlock) block, blockTexture(block), new ResourceLocation(id.getNamespace(), "block/tree_log_top"));
        } else if (block instanceof CropsBlock) {
            VariantBlockStateBuilder builder = getVariantBuilder(block);
            //noinspection CodeBlock2Expr
            BlockStateProperties.AGE_7.getPossibleValues().forEach(i -> {
                builder.partialState().with(BlockStateProperties.AGE_7, i).addModels(
                        new ConfiguredModel(models().withExistingParent(id.getPath() + i, new ResourceLocation("minecraft", "block/crop"))
                                .texture("crop", new ResourceLocation(id.getNamespace(), "block/" + id.getPath() + i))
                        )
                );
            });
        } else if (block instanceof GiantFlowerBlock) {
            VariantBlockStateBuilder builder = getVariantBuilder(block);
            builder.partialState().with(GiantFlowerBlock.PART, 0).addModels(new ConfiguredModel(models().getBuilder(id.getPath() + "_0")));
            builder.partialState().with(GiantFlowerBlock.PART, 2).addModels(new ConfiguredModel(models().getBuilder(id.getPath() + "_0")));
            builder.partialState().with(GiantFlowerBlock.PART, 1).addModels(
                    new ConfiguredModel(models().getExistingFile(new ResourceLocation(id.getNamespace(), "block/" + id.getPath() + "_stem"))),
                    new ConfiguredModel(models().getExistingFile(new ResourceLocation(id.getNamespace(), "block/" + id.getPath() + "_stem")), 0, 90, false),
                    new ConfiguredModel(models().getExistingFile(new ResourceLocation(id.getNamespace(), "block/" + id.getPath() + "_stem")), 0, 180, false),
                    new ConfiguredModel(models().getExistingFile(new ResourceLocation(id.getNamespace(), "block/" + id.getPath() + "_stem")), 0, 270, false)
            );
            if (block == ModBlocks.sunflower) {
                //noinspection CodeBlock2Expr
                Sunflower.TIME_VARIANT.getPossibleValues().forEach(i -> {
                    builder.partialState().with(GiantFlowerBlock.PART, 3).with(Sunflower.TIME_VARIANT, i).addModels(
                            new ConfiguredModel(models().getExistingFile(new ResourceLocation(id.getNamespace(), "block/" + id.getPath() + "_flower" + i)))
                    );
                });
            } else if (block == ModBlocks.dandelion) {
                builder.partialState().with(GiantFlowerBlock.PART, 3).with(Dandelion.VARIANT, 0).addModels(
                        new ConfiguredModel(models().getExistingFile(new ResourceLocation(id.getNamespace(), "block/" + id.getPath() + "_bud")))
                );
                builder.partialState().with(GiantFlowerBlock.PART, 3).with(Dandelion.VARIANT, 1).addModels(
                        new ConfiguredModel(models().getExistingFile(new ResourceLocation(id.getNamespace(), "block/" + id.getPath() + "_flower")))
                );
                builder.partialState().with(GiantFlowerBlock.PART, 3).with(Dandelion.VARIANT, 2).addModels(
                        new ConfiguredModel(models().getExistingFile(new ResourceLocation(id.getNamespace(), "block/" + id.getPath() + "_fluff")))
                );
                builder.partialState().with(GiantFlowerBlock.PART, 3).with(Dandelion.VARIANT, 3).addModels(
                        new ConfiguredModel(models().getExistingFile(new ResourceLocation(id.getNamespace(), "block/" + id.getPath() + "_nofluff")))
                );
            } else if (block == ModBlocks.crocus) {
                //noinspection CodeBlock2Expr
                Crocus.OPENING_STATE.getPossibleValues().forEach(i -> {
                    builder.partialState().with(GiantFlowerBlock.PART, 3).with(Crocus.OPENING_STATE, i).addModels(
                            new ConfiguredModel(models().getExistingFile(new ResourceLocation(id.getNamespace(), "block/" + id.getPath() + (i == 0 ? "_bud" : "_flower" + i))))
                    );
                });
            } else {
                builder.partialState().with(GiantFlowerBlock.PART, 3).addModels(
                        new ConfiguredModel(models().getExistingFile(new ResourceLocation(id.getNamespace(), "block/" + id.getPath() + "_flower")))
                );
            }
        } else {
            super.defaultState(id, block, model);
        }
    }

    @Override
    protected ModelFile defaultModel(ResourceLocation id, Block block) {
        if (block instanceof GiantFlowerBlock || block instanceof RotatedPillarBlock || block instanceof CropsBlock
                || block instanceof FeyLeavesBlock) {
            // Models are created in `defaultState`
            return null;
        } else {
            return super.defaultModel(id, block);
        }
    }
}
