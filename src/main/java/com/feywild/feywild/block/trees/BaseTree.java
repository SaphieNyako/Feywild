package com.feywild.feywild.block.trees;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.MegaJungleTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.material.Material;
import org.moddingx.libx.mod.ModX;
import org.moddingx.libx.registration.Registerable;
import org.moddingx.libx.registration.RegistrationContext;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.Objects;

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
    //   private final FeyLeavesBlock leaves;
    private final BaseSaplingBlock sapling;
    private final FeyStrippedLogBlock strippedLog;
    private final BlockItem strippedLogItem;
    private final FeyStrippedWoodBlock strippedWood;
    private final FeyPlanksBlock plankBlock;
    private final FeyCrackedLogBlock crackedLogBlock;
    private final BlockItem crackedLogItem;

    private Holder<ConfiguredFeature<?, ?>> feature;

    public BaseTree(ModX mod) { //Supplier<? extends FeyLeavesBlock> leavesFactory
        this.strippedWood = new FeyStrippedWoodBlock(mod, BlockBehaviour.Properties.of(Material.WOOD).strength(2f, 2f).sound(SoundType.WOOD).noOcclusion());
        this.woodBlock = new FeyWoodBlock(mod, this.strippedWood, BlockBehaviour.Properties.of(Material.WOOD).strength(2f, 2f).sound(SoundType.WOOD).noOcclusion(), mod.tab == null ? new Item.Properties() : new Item.Properties().tab(mod.tab));
        this.plankBlock = new FeyPlanksBlock(mod, BlockBehaviour.Properties.of(Material.WOOD).strength(2f, 2f).sound(SoundType.WOOD).noOcclusion(), mod.tab == null ? new Item.Properties() : new Item.Properties().tab(mod.tab));
        this.strippedLog = new FeyStrippedLogBlock(this.strippedWood, BlockBehaviour.Properties.of(Material.WOOD).strength(2f, 2f).sound(SoundType.WOOD).noOcclusion());
        this.logBlock = new FeyLogBlock(this.woodBlock, this.strippedLog, BlockBehaviour.Properties.of(Material.WOOD).strength(2f, 2f).sound(SoundType.WOOD).noOcclusion());
        this.logItem = new BlockItem(this.logBlock, mod.tab == null ? new Item.Properties() : new Item.Properties().tab(mod.tab));
        this.strippedLogItem = new BlockItem(this.strippedLog, mod.tab == null ? new Item.Properties() : new Item.Properties().tab(mod.tab));
        //   this.leaves = leavesFactory.get();
        this.sapling = new BaseSaplingBlock(mod, this);
        this.crackedLogBlock = new FeyCrackedLogBlock(this.strippedWood, BlockBehaviour.Properties.of(Material.WOOD).strength(2f, 2f).sound(SoundType.WOOD).noOcclusion(), getParticle());
        this.crackedLogItem = new BlockItem(this.crackedLogBlock, mod.tab == null ? new Item.Properties() : new Item.Properties().tab(mod.tab));
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void registerAdditional(RegistrationContext ctx, EntryCollector builder) {
        builder.registerNamed(Registry.BLOCK_REGISTRY, "log", this.logBlock);
        builder.registerNamed(Registry.ITEM_REGISTRY, "log", this.logItem);
        builder.registerNamed(Registry.BLOCK_REGISTRY, "wood", this.woodBlock);
        //   builder.registerNamed(Registry.BLOCK_REGISTRY, "leaves", this.leaves);
        builder.registerNamed(Registry.BLOCK_REGISTRY, "sapling", this.sapling);
        builder.registerNamed(Registry.BLOCK_REGISTRY, "stripped_log", this.strippedLog);
        builder.registerNamed(Registry.ITEM_REGISTRY, "stripped_log", this.strippedLogItem);
        builder.registerNamed(Registry.BLOCK_REGISTRY, "stripped_wood", this.strippedWood);
        builder.registerNamed(Registry.BLOCK_REGISTRY, "planks", this.plankBlock);
        builder.registerNamed(Registry.BLOCK_REGISTRY, "cracked_log", this.crackedLogBlock);
        builder.registerNamed(Registry.ITEM_REGISTRY, "cracked_log", this.crackedLogItem);

        TreeConfiguration featureConfig = this.getFeatureBuilder().build();
        this.feature = ctx.mod().createHolder(Registry.CONFIGURED_FEATURE_REGISTRY, ctx.id().getPath(), new ConfiguredFeature<>(Feature.TREE, featureConfig));
    }

    //TODO add feature?
    public Holder<ConfiguredFeature<?, ?>> getConfiguredFeature() {
        return Objects.requireNonNull(this.feature, "Feywild tree feature not initialised");
    }

    @Nullable
    @Override
    public Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(@Nonnull RandomSource random, boolean largeHive) {
        return this.getConfiguredFeature();
    }

    public TreeConfiguration.TreeConfigurationBuilder getFeatureBuilder() {
        return new TreeConfiguration.TreeConfigurationBuilder(
                SimpleStateProvider.simple(this.getLogBlock().defaultBlockState()),
                this.getGiantTrunkPlacer(),
                SimpleStateProvider.simple(this.getLeafBlock().defaultBlockState()),
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

    protected TwoLayersFeatureSize getTwoLayerFeature() {
        return new TwoLayersFeatureSize(1, 0, 1);
    }

    public abstract void decorateSaplingGrowth(ServerLevel world, BlockPos pos, RandomSource random);

    public FeyLogBlock getLogBlock() {
        return this.logBlock;
    }

    public FeyWoodBlock getWoodBlock() {
        return this.woodBlock;
    }

    public abstract FeyLeavesBlock getLeafBlock();

    public abstract SimpleParticleType getParticle();

    public FeyStrippedLogBlock getStrippedLogBlock() {
        return this.strippedLog;
    }

    public FeyPlanksBlock getPlankBlock() {
        return this.plankBlock;
    }

    public Block getSapling() {
        return this.sapling;
    }

    public FeyStrippedWoodBlock getStrippedWoodBlock() {
        return this.strippedWood;
    }

    public FeyCrackedLogBlock getCrackedLogBlock() {
        return this.crackedLogBlock;
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
    public boolean growTree(@Nonnull ServerLevel level, @Nonnull ChunkGenerator generator, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull RandomSource random) {
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
        Holder<? extends ConfiguredFeature<?, ?>> holder = this.getConfiguredFeature(random, this.hasFlowers(level, pos));
        if (holder == null) {
            return false;
        } else {
            ConfiguredFeature<?, ?> configuredfeature = holder.value();
            BlockState blockstate = level.getFluidState(pos).createLegacyBlock();
            level.setBlock(pos, blockstate, 4);
            if (configuredfeature.place(level, generator, random, pos)) {
                if (level.getBlockState(pos) == blockstate) {
                    level.sendBlockUpdated(pos, state, blockstate, 2);
                }

                return true;
            } else {
                level.setBlock(pos, state, 4);
                return false;
            }
        }
    }

    private boolean hasFlowers(LevelAccessor level, BlockPos pos) {
        for (BlockPos blockpos : BlockPos.MutableBlockPos.betweenClosed(pos.below().north(2).west(2), pos.above().south(2).east(2))) {
            if (level.getBlockState(blockpos).is(BlockTags.FLOWERS)) {
                return true;
            }
        }

        return false;
    }
}
