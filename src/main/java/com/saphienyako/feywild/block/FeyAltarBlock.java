package com.saphienyako.feywild.block;

import com.saphienyako.feywild.block.entity.FeyAltarBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FeyAltarBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final VoxelShape FEY_ALTAR = Block.box(0, 0, 0, 16, 32, 16);

    protected FeyAltarBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(FACING, Direction.EAST)
        );
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return 1;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING);
    }



    @Nonnull
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos pos = context.getClickedPos();
        if (context.getLevel().getBlockState(pos.above()).canBeReplaced(context)) {
            return this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, context.getHorizontalDirection().getCounterClockWise());
        }

        return null;
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape( @Nonnull BlockState state, @Nonnull IBlockReader reader, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        return FEY_ALTAR;
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState rotate(BlockState state, IWorld world, BlockPos pos, Rotation rotation){
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }
    @Nonnull
    @SuppressWarnings("deprecation")
    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState oldState, @Nonnull World level, @Nonnull BlockPos pos, BlockState newState, boolean moving) {
        if (oldState.getBlock() != newState.getBlock()) {
            TileEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof FeyAltarBlockEntity) {
                ((FeyAltarBlockEntity) blockEntity).drops();
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public ActionResultType use(@Nonnull BlockState state,@Nonnull World level,@Nonnull BlockPos pos,@Nonnull PlayerEntity player,@Nonnull Hand hand,@Nonnull BlockRayTraceResult hit) {
        if (!level.isClientSide()) {
            TileEntity entity = level.getBlockEntity(pos);
            if (!player.getItemInHand(hand).isEmpty()) {
                NetworkHooks.openGui((ServerPlayerEntity) player, (FeyAltarBlockEntity) entity, buf -> buf.writeBlockPos(pos));
            } else if(entity instanceof FeyAltarBlockEntity) {
                NetworkHooks.openGui((ServerPlayerEntity) player, (FeyAltarBlockEntity) entity, buf -> buf.writeBlockPos(pos));
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return ActionResultType.sidedSuccess(level.isClientSide);
    }

    //Add TileEntity
    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new FeyAltarBlockEntity();
    }

    /*
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
        if(level.isClientSide()) {
            return null;
        }
        //Creating Tick Method in Block Entity Class
        return createTickerHelper(blockEntityType, ModBlockEntities.FEY_ALTAR_BLOCK_ENTITY.get(),
                (level1, pos, state1, blockEntity) -> blockEntity.tick(level1, pos, state1));
    }
    */

    //rotate
    @Nonnull
    @SuppressWarnings("deprecation")
    @Override
    public PushReaction getPistonPushReaction(@Nonnull BlockState state) {
        return PushReaction.BLOCK;
    }
}
