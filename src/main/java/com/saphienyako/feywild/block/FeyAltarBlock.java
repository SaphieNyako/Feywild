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
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Objects;

public class FeyAltarBlock extends BaseEntityBlock{
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    //TODO updateShape, Voxel Shape doesn't rotate with placement
    private static final VoxelShape FEY_ALTAR = Shapes.or(
            box(5.5, 21.5, 3.5, 10.5, 26.5, 8.5),
            box(5.75, 26.5, 3.5, 10.25, 30, 3.5),
            box(10.25, 26.5, 3.5, 10.25, 30, 7),
            box(5.75, 26.5, 3.5, 5.75, 30, 7),
            box(5.5, 22.5, 5, 5.5, 25, 7.5),
            box(10.5, 22.5, 5, 13, 25, 5),
            box(7.25, 21, 5, 8.75, 21.5, 7),
            box(8.05, 25.3, 3.175, 10.95, 26.7, 4.575),
            box(10.3, 21.8, 2.925, 11.7, 26.2, 4.325),
            box(10.25, 20.125, 3.625, 11.75, 22.125, 3.625),
            box(5.05, 25.3, 3.175, 7.95, 26.7, 4.575),
            box(4.3, 21.8, 2.925, 5.7, 26.2, 4.325),
            box(4.25, 20.125, 3.625, 5.75, 22.125, 3.625),
            box(5.25, 21.25, 3.25, 10.75, 26.75, 8.75),
            box(5, 19.5, 8.5, 11, 26.5, 9),
            box(5, 16, 8.75, 11, 19.5, 8.75),
            box(5.75, 16.5, 4.25, 10.25, 21, 7.75),
            box(5.75, 18, 3.75, 7.75, 20.5, 4.25),
            box(8.25, 18, 3.75, 10.25, 20.5, 4.25),
            box(5.5, 16.25, 4, 10.5, 21.25, 8),
            box(6.25, 13.5, 4.75, 9.75, 16.5, 7.25),
            box(6, 13.25, 4.5, 10, 16.75, 7.5),
            box(2.25, 17.5, 4.25, 5.75, 21, 7.75),
            box(3.25, 11.5, 5, 5.25, 17.5, 7),
            box(2.21263, 15.50127, 7.25, 5.21263, 17.50127, 7.25),
            box(2.21263, 15.50127, 4.75, 2.21263, 17.50127, 7.25),
            box(2.21263, 15.50127, 4.75, 5.21263, 17.50127, 4.75),
            box(10.25, 17.5, 4.25, 13.75, 21, 7.75),
            box(10.75, 11.5, 5, 12.75, 17.5, 7),
            box(10.78763, 15.50127, 4.75, 13.78763, 17.50127, 4.75),
            box(13.78763, 15.50127, 4.75, 13.78763, 17.50127, 7.25),
            box(10.78763, 15.50127, 7.25, 13.78763, 17.50127, 7.25),
            box(-5.125, -0.5, 9.25, 6.875, 30.5, 9.25),
            box(9.125, -0.5, 9.25, 21.125, 30.5, 9.25),
            box(3, -1, 4.25, 8, 13.5, 8.75),
            box(2.75, -1.25, 4, 8.25, 13.75, 9),
            box(8, -1, 4.25, 13, 13.5, 8.75),
            box(7.75, -1.25, 4, 13.25, 13.75, 9),
            box(5.75, 4, 5,7.75, 13.5, 7),
            box(5.5, 3.75, 4.75, 8, 13.75, 7.25),
            box(8.25, 4, 5, 10.25, 13.5, 7),
            box(8, 3.75, 4.75, 10.5, 13.75, 7.25),
            box(0,0,0, 16, 2, 16),
            box(1, 1, 1, 15, 3, 15),
            box(3, 2, 3, 13, 4, 13)
    ).optimize();


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
        builder.add(BlockStateProperties.HORIZONTAL_FACING); //, HALF
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
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
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


}
