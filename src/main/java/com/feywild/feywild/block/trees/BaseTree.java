package com.feywild.feywild.block.trees;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.Registerable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.MegaJungleTrunkPlacer;
import net.minecraft.server.level.ServerLevel;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;

import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;

public abstract class BaseTree extends AbstractTreeGrower implements Registerable {

    private static final int BASE_HEIGHT = 6;
    private static final int FIRST_RANDOM_HEIGHT = 7;
    private static final int SECOND_RANDOM_HEIGHT = 8;

    private static final int LEAVES_RADIUS = 5;
    private static final int LEAVES_OFFSET = 4;
    private static final int LEAVES_HEIGHT = 5;

    private final FeyLogBlock logBlock;
    private final FeyWoodBlock woodBlock;
    private final BlockItem logItem;
    private final BlockItem woodItem;

    private final Registerable logRegister;
    private final Registerable woodRegister;
    private final FeyLeavesBlock leaves;
    private final BaseSaplingBlock sapling;

    public BaseTree(ModX mod, Supplier<? extends FeyLeavesBlock> leavesFactory) {
        this.logBlock = new FeyLogBlock(BlockBehaviour.Properties.copy(Blocks.JUNGLE_LOG));
        this.woodBlock = new FeyWoodBlock(this.logBlock, BlockBehaviour.Properties.copy(Blocks.JUNGLE_WOOD));
        Item.Properties properties = mod.tab == null ? new Item.Properties() : new Item.Properties().tab(mod.tab);
        this.logItem = new BlockItem(this.logBlock, properties);
        this.woodItem = new BlockItem(this.woodBlock, properties);

        this.logRegister = new Registerable() {
            @Override
            public Set<Object> getAdditionalRegisters(ResourceLocation id) {
                return ImmutableSet.of(BaseTree.this.logBlock, BaseTree.this.logItem);
            }
        };
        this.woodRegister = new Registerable() {
            @Override
            public Set<Object> getAdditionalRegisters(ResourceLocation id) {
                return ImmutableSet.of(BaseTree.this.woodBlock, BaseTree.this.woodItem);
            }
        };

        this.leaves = leavesFactory.get();
        this.sapling = new BaseSaplingBlock(mod, this);
    }

    @Override
    public Map<String, Object> getNamedAdditionalRegisters(ResourceLocation id) {
        return ImmutableMap.of(
                "log", this.logRegister,
                "wood", this.woodRegister,
                "leaves", this.leaves,
                "sapling", this.sapling
        );
    }

    @Nonnull
    @Override
    public ConfiguredFeature<TreeConfiguration, ?> getConfiguredFeature(@Nonnull Random random, boolean largeHive) {
        TreeConfiguration featureConfig = this.getFeatureBuilder(random, largeHive).build();
        return Feature.TREE.configured(featureConfig);
    }

    protected TreeConfiguration.TreeConfigurationBuilder getFeatureBuilder(@Nonnull Random random, boolean largeHive) {
        return new TreeConfiguration.TreeConfigurationBuilder(
                new SimpleStateProvider(this.getLogBlock().defaultBlockState()),
                this.getGiantTrunkPlacer(),
                new SimpleStateProvider(this.getLeafBlock().defaultBlockState()),
                new SimpleStateProvider(this.getSapling().defaultBlockState()),
                this.getFoliagePlacer(),
                this.getTwoLayerFeature()
        );
    }

    protected FoliagePlacer getFoliagePlacer() {
        return new BlobFoliagePlacer(
                UniformInt.of(this.getLeavesRadius(), this.getLeavesRadius()),
                UniformInt.of(this.getLeavesOffset(), this.getLeavesOffset()),
                this.getLeavesHeight()
        );
    }

    protected TrunkPlacer getGiantTrunkPlacer() {
        return new MegaJungleTrunkPlacer(this.getBaseHeight(), this.getFirstRandomHeight(), this.getSecondRandomHeight());
    }

    //Branch placer
    protected TwoLayersFeatureSize getTwoLayerFeature() {
        return new TwoLayersFeatureSize(1, 0, 1);
    }

    public abstract void decorateSaplingGrowth(ServerLevel world, BlockPos pos, Random random);

    public Block getLogBlock() {
        return this.logBlock;
    }

    public Block getWoodBlock() {
        return this.woodBlock;
    }

    public Block getLeafBlock() {
        return this.leaves;
    }

    public Block getSapling() {
        return this.sapling;
    }

    protected int getLeavesRadius() {
        return LEAVES_RADIUS;
    }

    protected int getLeavesOffset() {
        return LEAVES_OFFSET;
    }

    protected int getLeavesHeight() {
        return LEAVES_HEIGHT;
    }

    protected int getBaseHeight() {
        return BASE_HEIGHT;
    }

    protected int getFirstRandomHeight() {
        return FIRST_RANDOM_HEIGHT;
    }

    protected int getSecondRandomHeight() {
        return SECOND_RANDOM_HEIGHT;
    }

    @Override
    public boolean growTree(@Nonnull ServerLevel level, @Nonnull ChunkGenerator generator, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull Random random) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (!(i == 0 && j == 0)) {
                    if (!level.isStateAtPosition(pos.offset(i, 0, j), BlockBehaviour.BlockStateBase::isAir) &&
                            !level.isStateAtPosition(pos.offset(i, 0, j), blockState -> blockState.getMaterial().equals(Material.REPLACEABLE_PLANT))) {
                        return false;
                    }
                }
            }
        }

        super.growTree(level, generator, pos, state, random);

        return true;
    }

}
