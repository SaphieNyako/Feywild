package com.feywild.feywild.data;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.DisplayGlassBlock;
import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.ModTrees;
import com.feywild.feywild.block.MossyBlock;
import com.feywild.feywild.block.flower.CrocusBlock;
import com.feywild.feywild.block.flower.DandelionBlock;
import com.feywild.feywild.block.flower.GiantFlowerBlock;
import com.feywild.feywild.block.flower.SunflowerBlock;
import com.feywild.feywild.block.trees.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.client.model.generators.loaders.CompositeModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.moddingx.libx.annotation.data.Datagen;
import org.moddingx.libx.datagen.provider.BlockStateProviderBase;
import org.moddingx.libx.mod.ModX;

import java.util.Objects;
import java.util.function.Supplier;

@Datagen
public class BlockStateProvider extends BlockStateProviderBase {

    public static final ResourceLocation MOSS_OVERLAY = FeywildMod.getInstance().resource("block/moss_overlay");

    public BlockStateProvider(ModX mod, DataGenerator generator, ExistingFileHelper fileHelper) {
        super(mod, generator, fileHelper);
    }

    @Override
    protected void setup() {
        this.manualModel(ModBlocks.dwarvenAnvil);
        this.manualModel(ModBlocks.feyAltar);
        this.manualModel(ModBlocks.libraryBell);
        this.manualModel(ModBlocks.treeMushroom);
        this.manualModel(ModBlocks.magicalBrazier);

        this.manualModel(ModBlocks.feyMushroom, this.models().cross(
                Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(ModBlocks.feyMushroom)).getPath(),
                this.modLoc("block/" + Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(ModBlocks.feyMushroom)).getPath())
        ).renderType(RenderTypes.CUTOUT));

        this.manualModel(ModBlocks.ancientRunestone, this.models().cubeTop(
                Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(ModBlocks.ancientRunestone)).getPath(),
                this.modLoc("block/" + Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(ModBlocks.ancientRunestone)).getPath() + "_side"),
                this.modLoc("block/" + Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(ModBlocks.ancientRunestone)).getPath() + "_top")
        ));

        this.makeDisplayGlass();
        this.makeCrackedLogBlock(ModTrees.autumnTree.getCrackedLogBlock());
        this.makeCrackedLogBlock(ModTrees.springTree.getCrackedLogBlock());
        this.makeCrackedLogBlock(ModTrees.winterTree.getCrackedLogBlock());
        this.makeCrackedLogBlock(ModTrees.summerTree.getCrackedLogBlock());
        this.makeCrackedLogBlock(ModTrees.blossomTree.getCrackedLogBlock());
        this.makeCrackedLogBlock(ModTrees.hexenTree.getCrackedLogBlock());
    }

    private void makeCrackedLogBlock(FeyCrackedLogBlock logBlock) {
        this.manualState(logBlock);


        ResourceLocation id = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(logBlock));
        String tree = id.getPath().replace("_cracked_log", "");
        VariantBlockStateBuilder builder = this.getVariantBuilder(logBlock);
        FeyCrackedLogBlock.CRACKED.getPossibleValues().forEach(i ->
                builder.partialState().with(FeyCrackedLogBlock.CRACKED, i)
                        .addModels(new ConfiguredModel(this.models()
                                .cubeColumn(id.getPath() + (i == 1 ? "" : "_" + i),
                                        new ResourceLocation(id.getNamespace(), "block/" + id.getPath() + "_" + i),
                                        new ResourceLocation(id.getNamespace(), "block/" + tree + "_log")))));
    }

    private void makeDisplayGlass() {
        this.manualState(ModBlocks.displayGlass);

        ResourceLocation id = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(ModBlocks.displayGlass));
        VariantBlockStateBuilder builder = this.getVariantBuilder(ModBlocks.displayGlass);
        DisplayGlassBlock.BREAKAGE.getPossibleValues().forEach(i ->
                builder.partialState().with(DisplayGlassBlock.BREAKAGE, i)
                        .addModels(
                                new ConfiguredModel(this.models().cubeAll(
                                        id.getPath() + (i == 0 ? "" : i),
                                        new ResourceLocation(id.getNamespace(), "block/" + id.getPath() + i)
                                ).renderType(RenderTypes.TRANSLUCENT))
                        )
        );
    }


    @Override
    protected void defaultState(ResourceLocation id, Block block, Supplier<ModelFile> model) {
        if (block instanceof FeyLeavesBlock) {
            this.simpleBlock(block,
                    new ConfiguredModel(this.models()
                            .cubeAll(id.getPath(), this.modLoc("block/" + id.getPath()))
                            .renderType(RenderTypes.CUTOUT_MIPPED)
                    ),
                    new ConfiguredModel(this.models()
                            .cubeAll(id.getPath() + "_02", this.modLoc("block/" + id.getPath() + "_02"))
                            .renderType(RenderTypes.CUTOUT_MIPPED)
                    )
            );
        } else if (block instanceof FeyLogBlock feyLog) {
            this.axisBlock(feyLog, this.blockTexture(feyLog.getWoodBlock()), this.blockTexture(feyLog));
        } else if (block instanceof FeyStrippedLogBlock feyLog) {
            this.axisBlock(feyLog, this.blockTexture(feyLog.getWoodBlock()), this.blockTexture(feyLog));
        } else if (block instanceof CropBlock) {
            VariantBlockStateBuilder builder = this.getVariantBuilder(block);
            //noinspection CodeBlock2Expr
            BlockStateProperties.AGE_7.getPossibleValues().forEach(i -> {
                builder.partialState().with(BlockStateProperties.AGE_7, i).addModels(
                        new ConfiguredModel(this.models().withExistingParent(id.getPath() + i, new ResourceLocation("minecraft", "block/crop"))
                                .texture("crop", new ResourceLocation(id.getNamespace(), "block/" + id.getPath() + i))
                                .renderType(RenderTypes.CUTOUT)
                        )
                );
            });
        } else if (block instanceof GiantFlowerBlock) {
            VariantBlockStateBuilder builder = this.getVariantBuilder(block);
            // 0 and 2 only for particles
            builder.partialState().with(GiantFlowerBlock.PART, 0).addModels(new ConfiguredModel(this.models().getExistingFile(new ResourceLocation(id.getNamespace(), "block/" + id.getPath() + "_stem"))));
            builder.partialState().with(GiantFlowerBlock.PART, 2).addModels(new ConfiguredModel(this.models().getExistingFile(new ResourceLocation(id.getNamespace(), "block/" + id.getPath() + "_stem"))));
            builder.partialState().with(GiantFlowerBlock.PART, 1).addModels(
                    new ConfiguredModel(this.models().getExistingFile(new ResourceLocation(id.getNamespace(), "block/" + id.getPath() + "_stem"))),
                    new ConfiguredModel(this.models().getExistingFile(new ResourceLocation(id.getNamespace(), "block/" + id.getPath() + "_stem")), 0, 90, false),
                    new ConfiguredModel(this.models().getExistingFile(new ResourceLocation(id.getNamespace(), "block/" + id.getPath() + "_stem")), 0, 180, false),
                    new ConfiguredModel(this.models().getExistingFile(new ResourceLocation(id.getNamespace(), "block/" + id.getPath() + "_stem")), 0, 270, false)
            );
            if (block == ModBlocks.sunflower) {
                //noinspection CodeBlock2Expr
                SunflowerBlock.TIME_VARIANT.getPossibleValues().forEach(i -> {
                    builder.partialState().with(GiantFlowerBlock.PART, 3).with(SunflowerBlock.TIME_VARIANT, i).addModels(
                            new ConfiguredModel(this.models().getExistingFile(new ResourceLocation(id.getNamespace(), "block/" + id.getPath() + "_flower" + i)))
                    );
                });
            } else if (block == ModBlocks.dandelion) {
                builder.partialState().with(GiantFlowerBlock.PART, 3).with(DandelionBlock.VARIANT, 0).addModels(
                        new ConfiguredModel(this.models().getExistingFile(new ResourceLocation(id.getNamespace(), "block/" + id.getPath() + "_bud")))
                );
                builder.partialState().with(GiantFlowerBlock.PART, 3).with(DandelionBlock.VARIANT, 1).addModels(
                        new ConfiguredModel(this.models().getExistingFile(new ResourceLocation(id.getNamespace(), "block/" + id.getPath() + "_flower")))
                );
                builder.partialState().with(GiantFlowerBlock.PART, 3).with(DandelionBlock.VARIANT, 2).addModels(
                        new ConfiguredModel(this.models().getExistingFile(new ResourceLocation(id.getNamespace(), "block/" + id.getPath() + "_fluff")))
                );
                builder.partialState().with(GiantFlowerBlock.PART, 3).with(DandelionBlock.VARIANT, 3).addModels(
                        new ConfiguredModel(this.models().getExistingFile(new ResourceLocation(id.getNamespace(), "block/" + id.getPath() + "_nofluff")))
                );
            } else if (block == ModBlocks.crocus) {
                //noinspection CodeBlock2Expr
                CrocusBlock.OPENING_STATE.getPossibleValues().forEach(i -> {
                    builder.partialState().with(GiantFlowerBlock.PART, 3).with(CrocusBlock.OPENING_STATE, i).addModels(
                            new ConfiguredModel(this.models().getExistingFile(new ResourceLocation(id.getNamespace(), "block/" + id.getPath() + (i == 0 ? "_bud" : "_flower" + i))))
                    );
                });
            } else {
                builder.partialState().with(GiantFlowerBlock.PART, 3).addModels(
                        new ConfiguredModel(this.models().getExistingFile(new ResourceLocation(id.getNamespace(), "block/" + id.getPath() + "_flower")))
                );
            }
        } else {
            super.defaultState(id, block, model);
        }
    }

    @Override
    protected ModelFile defaultModel(ResourceLocation id, Block block) {
        if (block instanceof BaseSaplingBlock) {
            return this.models().cross(id.getPath(), this.blockTexture(block)).renderType(RenderTypes.CUTOUT);
        } else if (block instanceof MossyBlock mossy) {
            ResourceLocation parentId = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(mossy.getBaseBlock()));
            return this.models().getBuilder(id.getPath())
                    .parent(this.models().getExistingFile(this.mcLoc("block/block")))
                    .texture("particle", new ResourceLocation(parentId.getNamespace(), "block/" + parentId.getPath()))
                    .customLoader(CompositeModelBuilder::begin)
                    .child("block", this.models().nested()
                            .renderType(FeywildMod.getInstance().resource("semi_solid"))
                            .parent(new ModelFile.UncheckedModelFile(new ResourceLocation(parentId.getNamespace(), "block/" + parentId.getPath())))
                    )
                    .child("moss_layer", this.models().nested()
                            .renderType(FeywildMod.getInstance().resource("semi_cutout"))
                            .parent(this.models().getExistingFile(MOSS_OVERLAY))
                    )
                    .end();
        } else {
            return super.defaultModel(id, block);
        }
    }
}
