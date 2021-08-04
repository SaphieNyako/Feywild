package com.feywild.feywild.block.flower;

import com.google.common.collect.ImmutableMap;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.Registerable;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

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
        super(Properties.of(Material.PLANT).noOcclusion().harvestTool(ToolType.AXE).sound(SoundType.BAMBOO).strength(1, 1).lightLevel(value -> 8));
        this.height = height;
        this.registerDefaultState(this.stateDefinition.any().setValue(PART, 3));
        this.item = new GiantFlowerSeedItem(mod, this);
    }

    @Override
    public Map<String, Object> getNamedAdditionalRegisters() {
        return ImmutableMap.of("seed", this.item);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void registerClient(ResourceLocation id, Consumer<Runnable> defer) {
        defer.accept(() -> RenderTypeLookup.setRenderLayer(this, RenderType.cutout()));
    }

    @Override
    protected void createBlockStateDefinition(@Nonnull StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(PART);
    }

    public GiantFlowerSeedItem getSeed() {
        return item;
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader world, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        return state.getValue(PART) == 3 ? FLOWER_SHAPE : STEM_SHAPE;
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getVisualShape(@Nonnull BlockState state, @Nonnull IBlockReader world, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        return VoxelShapes.empty();
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public BlockRenderType getRenderShape(@Nonnull BlockState state) {
        return state.getValue(PART) == 1 || state.getValue(PART) == 3 ? BlockRenderType.MODEL : BlockRenderType.INVISIBLE;
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
        return new ItemStack(getSeed());
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(@Nonnull BlockState oldState, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean moving) {
        if (oldState.getBlock() != newState.getBlock()) {
            removeOthers(world, oldState, pos);
        }
        super.onRemove(oldState, world, pos, newState, moving);
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
    public void randomTick(@Nonnull BlockState state, @Nonnull ServerWorld world, @Nonnull BlockPos pos, @Nonnull Random random) {
        super.randomTick(state, world, pos, random);
        if (state.getValue(PART) == 3) tickFlower(state, world, pos, random);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Random random) {
        super.animateTick(state, world, pos, random);
        if (state.getValue(PART) == 3) animateFlower(state, world, pos, random);
    }

    protected abstract void tickFlower(BlockState state, ServerWorld world, BlockPos pos, Random random);

    @OnlyIn(Dist.CLIENT)
    protected abstract void animateFlower(BlockState state, World world, BlockPos pos, Random random);

    public abstract BlockState flowerState(IWorld world, BlockPos pos, Random random);

    protected void removeOthers(World world, BlockState state, BlockPos pos) {
        int blocksBelow = state.getValue(PART) - (4 - height);
        int blocksAbove = 3 - state.getValue(PART);

        for (int i = 1; i <= blocksBelow; i++) {
            BlockPos target = pos.offset(0, -i, 0);
            if (world.getBlockState(target).getBlock() == this) {
                // No block update
                world.setBlock(target, Blocks.AIR.defaultBlockState(), 2);
            }
        }

        for (int i = 1; i <= blocksAbove; i++) {
            BlockPos target = pos.offset(0, i, 0);
            if (world.getBlockState(target).getBlock() == this) {
                // No block update
                world.setBlock(target, Blocks.AIR.defaultBlockState(), 2);
            }
        }
    }
}
