package com.feywild.feywild.block.flower;

import com.feywild.feywild.config.ClientConfig;
import com.google.common.collect.ImmutableMap;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.Registerable;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;

public abstract class GiantFlowerBlock extends Block implements Registerable {

    public static final VoxelShape STEM_SHAPE = box(4, 0, 4, 12, 16, 12);
    public static final VoxelShape FLOWER_SHAPE = box(1, 0, 1, 15, 15, 15);

    // 0 - 2 = stem, 3 = flower
    public static final IntegerProperty PART = IntegerProperty.create("part", 0, 3);
    public final int height;
    private final GiantFlowerSeedItem item;

    public GiantFlowerBlock(ModX mod, int height) {
        super(Properties.of(Material.PLANT).noOcclusion().sound(SoundType.BAMBOO).strength(1, 1).lightLevel(value -> 8));
        this.height = height;
        this.registerDefaultState(this.stateDefinition.any().setValue(PART, 3));
        this.item = new GiantFlowerSeedItem(mod, this);
    }

    @Override
    public Map<String, Object> getNamedAdditionalRegisters(ResourceLocation id) {
        return ImmutableMap.of("seed", this.item);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void registerClient(ResourceLocation id, Consumer<Runnable> defer) {
        defer.accept(() -> ItemBlockRenderTypes.setRenderLayer(this, RenderType.cutout()));
    }

    @Override
    protected void createBlockStateDefinition(@Nonnull StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(PART);
    }

    public GiantFlowerSeedItem getSeed() {
        return this.item;
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return state.getValue(PART) == 3 ? FLOWER_SHAPE : STEM_SHAPE;
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getVisualShape(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return Shapes.empty();
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public RenderShape getRenderShape(@Nonnull BlockState state) {
        return state.getValue(PART) == 1 || state.getValue(PART) == 3 ? RenderShape.MODEL : RenderShape.INVISIBLE;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        return new ItemStack(this.getSeed());
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(@Nonnull BlockState oldState, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean moving) {
        if (oldState.getBlock() != newState.getBlock()) {
            this.removeOthers(level, oldState, pos);
        }
        super.onRemove(oldState, level, pos, newState, moving);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public PushReaction getPistonPushReaction(@Nonnull BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    public boolean isRandomlyTicking(@Nonnull BlockState state) {
        // Only tick flower head
        return state.getValue(PART) == 3;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(@Nonnull BlockState state, @Nonnull ServerLevel level, @Nonnull BlockPos pos, @Nonnull Random random) {
        super.randomTick(state, level, pos, random);
        if (state.getValue(PART) == 3) this.tickFlower(state, level, pos, random);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Random random) {
        super.animateTick(state, level, pos, random);
        if (state.getValue(PART) == 3 && ClientConfig.flower_particles) this.animateFlower(state, level, pos, random);
    }

    protected abstract void tickFlower(BlockState state, ServerLevel world, BlockPos pos, Random random);

    @OnlyIn(Dist.CLIENT)
    protected abstract void animateFlower(BlockState state, Level world, BlockPos pos, Random random);

    public abstract BlockState flowerState(LevelAccessor world, BlockPos pos, Random random);

    protected void removeOthers(Level level, BlockState state, BlockPos pos) {
        int blocksBelow = state.getValue(PART) - (4 - this.height);
        int blocksAbove = 3 - state.getValue(PART);

        for (int i = 1; i <= blocksBelow; i++) {
            BlockPos target = pos.offset(0, -i, 0);
            if (level.getBlockState(target).getBlock() == this) {
                // No block update
                level.setBlock(target, Blocks.AIR.defaultBlockState(), 2);
            }
        }

        for (int i = 1; i <= blocksAbove; i++) {
            BlockPos target = pos.offset(0, i, 0);
            if (level.getBlockState(target).getBlock() == this) {
                // No block update
                level.setBlock(target, Blocks.AIR.defaultBlockState(), 2);
            }
        }
    }
}
