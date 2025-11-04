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
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Objects;

public class FeyAltarBlock extends BaseEntityBlock{
    //TODO fix shading problem

    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 32, 16);

    protected FeyAltarBlock(Properties pProperties) {
        super(pProperties);
      this.registerDefaultState(this.getStateDefinition().any().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING);
    }

    @javax.annotation.Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if (!context.getLevel().getBlockState(context.getClickedPos().above()).canBeReplaced(context)) return null;
        return this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());

    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return SHAPE;
    }

    @Nonnull
    @Override
    public RenderShape getRenderShape(@Nonnull BlockState state) {
     return RenderShape.MODEL;
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
}
