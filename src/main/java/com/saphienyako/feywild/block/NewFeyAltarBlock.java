package com.saphienyako.feywild.block;

import com.mojang.serialization.MapCodec;
import com.saphienyako.feywild.block.entity.FeyAltarBlockEntity;
import com.saphienyako.feywild.block.entity.ModBlockEntities;
import com.saphienyako.feywild.entity.Alignment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NewFeyAltarBlock extends BaseEntityBlock {

    public static final MapCodec<NewFeyAltarBlock> CODEC = simpleCodec(NewFeyAltarBlock::new);

    public static final VoxelShape BOTTOM_SHAPE = box(0, 0, 0, 16, 16, 16);
    public static final VoxelShape UPPER_SHAPE = box(0, 0, 0, 16, 16, 16);
    // 0 - 1 = 2 parts
    public static final IntegerProperty PART = IntegerProperty.create("part", 0, 1);

    public static final EnumProperty<@NotNull Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    private final Alignment alignment;
    public NewFeyAltarBlock(Properties properties, Alignment alignment) {
        super(properties);
        this.alignment = alignment;
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(PART, 0)
                .setValue(FACING, Direction.NORTH));
    }

    public NewFeyAltarBlock(Properties properties) {
        this(properties, Alignment.SPRING);
    }


    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public int getLightEmission(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return 1;
    }

    @Override
    protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, @NotNull BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(PART, FACING);
    }

    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return state.getValue(PART) == 1 ? UPPER_SHAPE : BOTTOM_SHAPE;
    }

    @Override
    public @NotNull VoxelShape getVisualShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return Shapes.empty();
    }


    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void setPlacedBy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, LivingEntity placer, @NotNull ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (!level.isClientSide()) {
            level.setBlock(pos.above(), state.setValue(PART, 1), Block.UPDATE_ALL);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();

        if (!level.getBlockState(pos.above()).canBeReplaced(context)) {
            return null;
        }

        return this.defaultBlockState()
                .setValue(PART, 0)
                .setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull LevelReader level, @NotNull ScheduledTickAccess ticks, @NotNull BlockPos pos, @NotNull Direction direction, @NotNull BlockPos neighbourPos, @NotNull BlockState neighbourState, @NotNull RandomSource random) {
        int part = state.getValue(PART);

        if (part == 0 && direction == Direction.UP) {
            if (neighbourState.getBlock() != this || neighbourState.getValue(PART) != 1) {
                return Blocks.AIR.defaultBlockState();
            }
        }

        if (part == 1 && direction == Direction.DOWN) {
            if (neighbourState.getBlock() != this || neighbourState.getValue(PART) != 0) {
                return Blocks.AIR.defaultBlockState();
            }
        }

        return super.updateShape(state, level, ticks, pos, direction, neighbourPos, neighbourState, random);
    }


    @Override
    protected @NotNull List<ItemStack> getDrops(@NotNull BlockState state, LootParams.@NotNull Builder params) {
        List<ItemStack> drops = new ArrayList<>(super.getDrops(state, params));

        BlockEntity blockEntity = params.getOptionalParameter(LootContextParams.BLOCK_ENTITY);

        if (blockEntity instanceof FeyAltarBlockEntity altar) {
            ItemStackHandler inventory = altar.getInventory();

            for (int slot = 0; slot < inventory.getSlots(); slot++) {
                ItemStack stack = inventory.getStackInSlot(slot);

                if (!stack.isEmpty()) {
                    drops.add(stack.copy());
                }
            }
        }

        return drops;
    }

    /* TODO is this still needed?
    protected void removeOthers(Level level, BlockState state, BlockPos pos) {
        BlockPos otherPos;

        if (state.getValue(PART) == 0) {
            // Bottom part -> remove upper
            otherPos = pos.above();
        } else {
            // Upper part -> remove bottom
            otherPos = pos.below();
        }

        BlockState otherState = level.getBlockState(otherPos);

        if (otherState.getBlock() == this) {
            level.setBlock(otherPos, Blocks.AIR.defaultBlockState(), 2);
        }
    } */

    @Override
    protected @NotNull InteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {

        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        BlockPos controllerPos = getBasePos(state, pos);
        BlockEntity be = level.getBlockEntity(controllerPos);

        if (!(be instanceof FeyAltarBlockEntity altar)) {
            return InteractionResult.CONSUME;
        }

        player.openMenu(altar, controllerPos);

        return InteractionResult.CONSUME;
    }

    private static BlockPos getBasePos(BlockState state, BlockPos pos) {
        return state.getValue(PART) == 0 ? pos : pos.below();
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
        if(level.isClientSide()) {
            return null;
        }
        return createTickerHelper(blockEntityType, ModBlockEntities.FEY_ALTAR_BLOCK_ENTITY.get(),
                (level1, pos, state1, blockEntity) -> blockEntity.tick(level1, pos, state1));
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, BlockState state) {
        return state.getValue(PART) == 0
                ? new FeyAltarBlockEntity(pos, state)
                : null;
    }

    @Nullable
    @Override
    public PushReaction getPistonPushReaction(@NotNull BlockState state) {
        return PushReaction.BLOCK;
    }

    public Alignment getAlignment() {
        return alignment;
    }
}
