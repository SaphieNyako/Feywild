package com.saphienyako.feywild.block;

import com.saphienyako.feywild.block.entity.FeyAltarBlockEntity;
import com.saphienyako.feywild.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Objects;

public class FeyAltarBlock extends BaseEntityBlock{
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final VoxelShape FEY_ALTAR = Block.box(0, 0, 0, 16, 32, 16);

    protected FeyAltarBlock(Properties pProperties) {
        super(pProperties);
      this.registerDefaultState(this.getStateDefinition().any()
              .setValue(FACING, Direction.EAST)
              );
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return 1;
        //Solves Shadow problem, not solid solution
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING);
    }

    @javax.annotation.Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        if (context.getLevel().getBlockState(pos.above()).canBeReplaced(context)) {
            return this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, context.getHorizontalDirection().getCounterClockWise());
        }

        return null;
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return FEY_ALTAR;
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public RenderShape getRenderShape(@Nonnull BlockState state) {
        return  RenderShape.MODEL;
    }
    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }
    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(@Nonnull BlockState oldState, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean moving) {
        if (oldState.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof FeyAltarBlockEntity) {
                ((FeyAltarBlockEntity) blockEntity).drops();
            }
        }

        super.onRemove(oldState, level, pos, newState, moving);
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull InteractionResult use(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hit) {
        if (!level.isClientSide()) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (!player.getItemInHand(hand).isEmpty()) {
                for (int slot = 0; slot < ((FeyAltarBlockEntity) Objects.requireNonNull(entity)).getInventory().getSlots(); slot++) {
                    if (((FeyAltarBlockEntity) Objects.requireNonNull(entity)).getInventory().getStackInSlot(slot).isEmpty()) {
                        ItemStack insertStack = player.getItemInHand(hand).copy();
                        insertStack.setCount(1);
                        if (((FeyAltarBlockEntity) Objects.requireNonNull(entity)).getInventory().insertItem(slot, insertStack, true).isEmpty() && slot != 5) {
                            ((FeyAltarBlockEntity) Objects.requireNonNull(entity)).getInventory().insertItem(slot, insertStack, false);
                            player.getItemInHand(hand).shrink(1);
                            NetworkHooks.openScreen(((ServerPlayer)player), (FeyAltarBlockEntity)entity, pos);
                            return InteractionResult.CONSUME;
                        }
                    }
                }
                return InteractionResult.FAIL;
            } else if(entity instanceof FeyAltarBlockEntity) {
                NetworkHooks.openScreen(((ServerPlayer)player), (FeyAltarBlockEntity)entity, pos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
        if(level.isClientSide()) {
            return null;
        }
        //Creating Tick Method in Block Entity Class
        return createTickerHelper(blockEntityType, ModBlockEntities.FEY_ALTAR_BLOCK_ENTITY.get(),
                (level1, pos, state1, blockEntity) -> blockEntity.tick(level1, pos, state1));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState blockState) {
        return new FeyAltarBlockEntity(pos, blockState);
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction) {
        return super.rotate(state, level, pos, direction);
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull PushReaction getPistonPushReaction(@NotNull BlockState state) {
        return PushReaction.BLOCK;
    }
}
