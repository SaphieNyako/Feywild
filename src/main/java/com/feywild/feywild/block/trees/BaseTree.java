package com.feywild.feywild.block.trees;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.MegaJungleTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraftforge.registries.ForgeRegistries;
import org.moddingx.libx.mod.ModX;
import org.moddingx.libx.registration.Registerable;
import org.moddingx.libx.registration.RegistrationContext;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.*;

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
    private final BaseSaplingBlock sapling;
    private final FeyStrippedLogBlock strippedLog;
    private final BlockItem strippedLogItem;
    private final FeyStrippedWoodBlock strippedWood;
    private final FeyPlanksBlock plankBlock;
    private final FeyCrackedLogBlock crackedLogBlock;
    private final BlockItem crackedLogItem;

    public BaseTree(ModX mod) { //Supplier<? extends FeyLeavesBlock> leavesFactory
        this.strippedWood = new FeyStrippedWoodBlock(mod, BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD).strength(2f, 2f).sound(SoundType.WOOD).noOcclusion());
        this.woodBlock = new FeyWoodBlock(mod, this.strippedWood, BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).strength(2f, 2f).sound(SoundType.WOOD).noOcclusion(), new Item.Properties());
        this.plankBlock = new FeyPlanksBlock(mod, BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2f, 2f).sound(SoundType.WOOD).noOcclusion(), new Item.Properties());
        this.strippedLog = new FeyStrippedLogBlock(this.strippedWood, BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).strength(2f, 2f).sound(SoundType.WOOD).noOcclusion());
        this.logBlock = new FeyLogBlock(this.woodBlock, this.strippedLog, BlockBehaviour.Properties.copy(Blocks.OAK_LOG).strength(2f, 2f).sound(SoundType.WOOD).noOcclusion());
        this.logItem = new BlockItem(this.logBlock, new Item.Properties());
        this.strippedLogItem = new BlockItem(this.strippedLog, new Item.Properties());
        this.sapling = new BaseSaplingBlock(this);
        this.crackedLogBlock = new FeyCrackedLogBlock(this.strippedLog, BlockBehaviour.Properties.copy(Blocks.OAK_LOG).strength(2f, 2f).sound(SoundType.WOOD).noOcclusion(), getParticle());
        this.crackedLogItem = new BlockItem(this.crackedLogBlock, new Item.Properties());
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void registerAdditional(RegistrationContext ctx, EntryCollector builder) {
        builder.registerNamed(Registries.BLOCK, "log", this.logBlock);
        builder.registerNamed(Registries.ITEM, "log", this.logItem);
        builder.registerNamed(Registries.BLOCK, "wood", this.woodBlock);
        builder.registerNamed(Registries.BLOCK, "sapling", this.sapling);
        builder.registerNamed(Registries.BLOCK, "stripped_log", this.strippedLog);
        builder.registerNamed(Registries.ITEM, "stripped_log", this.strippedLogItem);
        builder.registerNamed(Registries.BLOCK, "stripped_wood", this.strippedWood);
        builder.registerNamed(Registries.BLOCK, "planks", this.plankBlock);
        builder.registerNamed(Registries.BLOCK, "cracked_log", this.crackedLogBlock);
        builder.registerNamed(Registries.ITEM, "cracked_log", this.crackedLogItem);
    }

    @Override
    protected final ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@Nonnull RandomSource random, boolean hasFlowers) {
        List<Block> leaves = this.getAllLeaves();
        ResourceLocation id = ForgeRegistries.BLOCKS.getKey(leaves.get(random.nextInt(leaves.size())));
        if (id == null) throw new IllegalStateException("BaseTree leaf not registered");
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, id);
    }

    public final Holder<ConfiguredFeature<?, ?>> getConfiguredFeature(RegistryAccess registries, @Nonnull RandomSource random, boolean hasFlowers) {
        ResourceKey<ConfiguredFeature<?, ?>> key = this.getConfiguredFeature(random, hasFlowers);
        return registries.registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder(key).orElseThrow(() -> new NullPointerException("Feywild tree feature not registered: " + key.location()));
    }

    public Map<ResourceLocation, TreeConfiguration.TreeConfigurationBuilder> getFeatureBuilders() {
        Map<ResourceLocation, TreeConfiguration.TreeConfigurationBuilder> map = new HashMap<>();
        List<Block> leaves = this.getAllLeaves();
        if (leaves.isEmpty()) throw new IllegalStateException("Tree has no leaves");
        for (Block leaf : leaves) {
            ResourceLocation id = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(leaf));
            TreeConfiguration.TreeConfigurationBuilder builder = this.getFeatureBuilder(leaf);
            map.put(id, builder);
        }
        return Collections.unmodifiableMap(map);
    }
    
    public TreeConfiguration.TreeConfigurationBuilder getFeatureBuilder(Block leavesBlock) {
        return new TreeConfiguration.TreeConfigurationBuilder(
                SimpleStateProvider.simple(this.getLogBlock().defaultBlockState()),
                this.getGiantTrunkPlacer(),
                BlockStateProvider.simple(leavesBlock),
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
    
    public abstract List<Block> getAllLeaves();

    public abstract void decorateSaplingGrowth(ServerLevel world, BlockPos pos, RandomSource random);

    public abstract SimpleParticleType getParticle();

    public FeyLogBlock getLogBlock() {
        return this.logBlock;
    }

    public FeyWoodBlock getWoodBlock() {
        return this.woodBlock;
    }

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
        return this.growTree(level, generator, pos, state, random, this.getConfiguredFeature(level.registryAccess(), random, false));
    }
    
    public boolean growTree(@Nonnull ServerLevel level, @Nonnull ChunkGenerator generator, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull RandomSource random, Holder<ConfiguredFeature<?, ?>> feature) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (!(i == 0 && j == 0)) {
                    if (!level.isStateAtPosition(pos.offset(i, 0, j), BlockBehaviour.BlockStateBase::isAir) && !level.isStateAtPosition(pos.offset(i, 0, j), BlockBehaviour.BlockStateBase::canBeReplaced)) {
                        return false;
                    }
                }
            }
        }
        BlockState blockstate = level.getFluidState(pos).createLegacyBlock();
        level.setBlock(pos, blockstate, 4);
        if (feature.value().place(level, generator, random, pos)) {
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
