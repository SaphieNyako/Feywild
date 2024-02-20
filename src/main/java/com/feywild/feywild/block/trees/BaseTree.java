package com.feywild.feywild.block.trees;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
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
import net.minecraftforge.registries.ForgeRegistries;
import org.moddingx.libx.mod.ModX;
import org.moddingx.libx.registration.Registerable;
import org.moddingx.libx.registration.RegistrationContext;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.List;
import java.util.Objects;

public abstract class BaseTree extends AbstractTreeGrower implements Registerable {

    private final FeyLogBlock logBlock;
    private final FeyWoodBlock woodBlock;
    private final BlockItem logItem;
    private final BaseSaplingBlock sapling;
    private final FeyStrippedLogBlock strippedLog;
    private final BlockItem strippedLogItem;
    private final FeyStrippedWoodBlock strippedWood;
    private final FeyPlanksBlock plankBlock;
    private final FeyCrackedLogBlock crackedLog;
    private final BlockItem crackedLogItem;
    
    private TagKey<Block> blockLogTag;
    private TagKey<Item> itemLogTag;

    public BaseTree(ModX mod) {
        this.strippedWood = new FeyStrippedWoodBlock(mod, BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD).strength(2f, 2f).sound(SoundType.WOOD).noOcclusion());
        this.woodBlock = new FeyWoodBlock(mod, this.strippedWood, BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).strength(2f, 2f).sound(SoundType.WOOD).noOcclusion(), new Item.Properties());
        this.plankBlock = new FeyPlanksBlock(mod, BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2f, 2f).sound(SoundType.WOOD).noOcclusion(), new Item.Properties());
        this.strippedLog = new FeyStrippedLogBlock(this.strippedWood, BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).strength(2f, 2f).sound(SoundType.WOOD).noOcclusion());
        this.logBlock = new FeyLogBlock(this.woodBlock, this.strippedLog, BlockBehaviour.Properties.copy(Blocks.OAK_LOG).strength(2f, 2f).sound(SoundType.WOOD).noOcclusion());
        this.logItem = new BlockItem(this.logBlock, new Item.Properties());
        this.strippedLogItem = new BlockItem(this.strippedLog, new Item.Properties());
        this.sapling = new BaseSaplingBlock(this);
        this.crackedLog = new FeyCrackedLogBlock(this.strippedLog, BlockBehaviour.Properties.copy(Blocks.OAK_LOG).strength(2f, 2f).sound(SoundType.WOOD).noOcclusion(), getParticle());
        this.crackedLogItem = new BlockItem(this.crackedLog, new Item.Properties());
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
        builder.registerNamed(Registries.BLOCK, "cracked_log", this.crackedLog);
        builder.registerNamed(Registries.ITEM, "cracked_log", this.crackedLogItem);
        
        this.blockLogTag = BlockTags.create(new ResourceLocation(ctx.id().getNamespace(), ctx.id().getPath() + "_logs"));
        this.itemLogTag = ItemTags.create(this.blockLogTag.location());
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
        return this.crackedLog;
    }
    
    public TagKey<Block> getBlockLogTag() {
        return Objects.requireNonNull(this.blockLogTag, "Tree not yet registered.");
    }
    
    public TagKey<Item> getItemLogTag() {
        return Objects.requireNonNull(this.itemLogTag, "Tree not yet registered.");
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
