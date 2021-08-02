package com.feywild.feywild.block.trees;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.Registerable;
import net.minecraft.block.*;
import net.minecraft.block.trees.Tree;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.MegaJungleTrunkPlacer;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public abstract class BaseTree extends Tree implements Registerable {

    private static final int BASE_HEIGHT = 6;
    private static final int FIRST_RANDOM_HEIGHT = 7;
    private static final int SECOND_RANDOM_HEIGHT = 8;

    private static final int LEAVES_RADIUS = 5;
    private static final int LEAVES_OFFSET = 4;
    private static final int LEAVES_HEIGHT = 5;
    
    private final RotatedPillarBlock logBlock;
    private final FeyWoodBlock woodBlock;
    private final BlockItem logItem;
    private final BlockItem woodItem;
    
    private final Registerable logRegister;
    private final Registerable woodRegister;
    private final FeyLeavesBlock leaves;
    private final BaseSapling sapling;
    
    public BaseTree(ModX mod) {
        this(mod, FeyLeavesBlock::new);
    }
    
    public BaseTree(ModX mod, Function<ModX, ? extends FeyLeavesBlock> leavesFactory) {
        this.logBlock = new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.JUNGLE_LOG));
        this.woodBlock = new FeyWoodBlock(this.logBlock, AbstractBlock.Properties.copy(Blocks.JUNGLE_WOOD));
        Item.Properties properties = mod.tab == null ? new Item.Properties() : new Item.Properties().tab(mod.tab);
        this.logItem = new BlockItem(this.logBlock, properties);
        this.woodItem = new BlockItem(this.woodBlock, properties);
        
        this.logRegister = new Registerable() {
            @Override
            public Set<Object> getAdditionalRegisters() {
                return ImmutableSet.of(logBlock, logItem);
            }
        };
        this.woodRegister = new Registerable() {
            @Override
            public Set<Object> getAdditionalRegisters() {
                return ImmutableSet.of(woodBlock, woodItem);
            }
        };
        
        this.leaves = leavesFactory.apply(mod);
        this.sapling = new BaseSapling(mod, this);
    }

    @Override
    public Map<String, Object> getNamedAdditionalRegisters() {
        return ImmutableMap.of(
                "log", logRegister,
                "wood", woodRegister,
                "leaves", leaves,
                "sapling", sapling
        );
    }

    @Nullable
    @Override
    public ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(@Nonnull Random random, boolean largeHive) {
        BaseTreeFeatureConfig featureConfig = getFeatureBuilder(random, largeHive).build();
        return Feature.TREE.configured(featureConfig);
    }
    
    protected BaseTreeFeatureConfig.Builder getFeatureBuilder(@Nonnull Random random, boolean largeHive) {
        return new BaseTreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(getLogBlock().defaultBlockState()),
                new SimpleBlockStateProvider(getLeafBlock().defaultBlockState()),
                getFoliagePlacer(),
                getGiantTrunkPlacer(),
                getTwoLayerFeature()
        );
    }

    protected FoliagePlacer getFoliagePlacer() {
        return new BlobFoliagePlacer(
                FeatureSpread.fixed(getLeavesRadius()),
                FeatureSpread.fixed(getLeavesOffset()),
                getLeavesHeight()
        );
    }

    protected AbstractTrunkPlacer getGiantTrunkPlacer() {
        return new MegaJungleTrunkPlacer(getBaseHeight(), getFirstRandomHeight(), getSecondRandomHeight());
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
}
