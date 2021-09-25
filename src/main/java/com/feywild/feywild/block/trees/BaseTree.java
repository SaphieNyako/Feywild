package com.feywild.feywild.block.trees;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.Registerable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.trees.Tree;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.MegaJungleTrunkPlacer;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class BaseTree extends Tree implements Registerable {

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
        this.logBlock = new FeyLogBlock(AbstractBlock.Properties.copy(Blocks.JUNGLE_LOG));
        this.woodBlock = new FeyWoodBlock(this.logBlock, AbstractBlock.Properties.copy(Blocks.JUNGLE_WOOD));
        Item.Properties properties = mod.tab == null ? new Item.Properties() : new Item.Properties().tab(mod.tab);
        this.logItem = new BlockItem(this.logBlock, properties);
        this.woodItem = new BlockItem(this.woodBlock, properties);

        this.logRegister = new Registerable() {
            @Override
            public Set<Object> getAdditionalRegisters() {
                return ImmutableSet.of(BaseTree.this.logBlock, BaseTree.this.logItem);
            }
        };
        this.woodRegister = new Registerable() {
            @Override
            public Set<Object> getAdditionalRegisters() {
                return ImmutableSet.of(BaseTree.this.woodBlock, BaseTree.this.woodItem);
            }
        };

        this.leaves = leavesFactory.get();
        this.sapling = new BaseSaplingBlock(mod, this);
    }

    @Override
    public Map<String, Object> getNamedAdditionalRegisters() {
        return ImmutableMap.of(
                "log", this.logRegister,
                "wood", this.woodRegister,
                "leaves", this.leaves,
                "sapling", this.sapling
        );
    }

    @Nonnull
    @Override
    public ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(@Nonnull Random random, boolean largeHive) {
        BaseTreeFeatureConfig featureConfig = this.getFeatureBuilder(random, largeHive).build();
        return Feature.TREE.configured(featureConfig);
    }

    protected BaseTreeFeatureConfig.Builder getFeatureBuilder(@Nonnull Random random, boolean largeHive) {
        return new BaseTreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(this.getLogBlock().defaultBlockState()),
                new SimpleBlockStateProvider(this.getLeafBlock().defaultBlockState()),
                this.getFoliagePlacer(),
                this.getGiantTrunkPlacer(),
                this.getTwoLayerFeature()
        );
    }

    protected FoliagePlacer getFoliagePlacer() {
        return new BlobFoliagePlacer(
                FeatureSpread.fixed(this.getLeavesRadius()),
                FeatureSpread.fixed(this.getLeavesOffset()),
                this.getLeavesHeight()
        );
    }

    protected AbstractTrunkPlacer getGiantTrunkPlacer() {
        return new MegaJungleTrunkPlacer(this.getBaseHeight(), this.getFirstRandomHeight(), this.getSecondRandomHeight());
    }

    //Branch placer
    protected TwoLayerFeature getTwoLayerFeature() {
        return new TwoLayerFeature(1, 0, 1);
    }

    public abstract void decorateSaplingGrowth(ServerWorld world, BlockPos pos, Random random);

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
    public boolean growTree(@Nonnull ServerWorld world, @Nonnull ChunkGenerator generator, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull Random random) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (!(i == 0 && j == 0)) {
                    if (!world.isStateAtPosition(pos.offset(i, 0, j), AbstractBlock.AbstractBlockState::isAir) &&
                            !world.isStateAtPosition(pos.offset(i, 0, j), blockState -> blockState.getMaterial().equals(Material.REPLACEABLE_PLANT))) {
                        return false;
                    }
                }
            }
        }

        super.growTree(world, generator, pos, state, random);

        return true;

    }

}
