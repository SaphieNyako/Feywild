package com.feywild.feywild.block;

import com.feywild.feywild.block.entity.FeyAltar;
import com.feywild.feywild.block.render.FeyAltarRenderer;
import com.feywild.feywild.item.FeyAltarItem;
import com.feywild.feywild.quest.Alignment;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.moddingx.libx.base.tile.BlockBE;
import org.moddingx.libx.mod.ModX;
import org.moddingx.libx.registration.RegistrationContext;
import org.moddingx.libx.registration.SetupContext;
import software.bernie.geckolib.core.animatable.GeoAnimatable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;

public class FeyAltarBlock extends BlockBE<FeyAltar> {
    
    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 32, 16);
    private static final VoxelShape SHAPE_TOP = SHAPE.move(0, -1, 0);
    private final Alignment alignment;
    private final FeyAltarItem item;

    public FeyAltarBlock(ModX mod, Alignment alignment) {
        super(mod, FeyAltar.class, BlockBehaviour.Properties.copy(Blocks.STONE).strength(3f, 10f).requiresCorrectToolForDrops().sound(SoundType.STONE).noOcclusion(), null);
        this.alignment = alignment;
        this.item = new FeyAltarItem(this, alignment);
        
        this.registerDefaultState(this.getStateDefinition().any().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH).setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER));
    }

    public Alignment getAlignment() {
        return alignment;
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void registerAdditional(RegistrationContext ctx, EntryCollector builder) {
        super.registerAdditional(ctx, builder);
        builder.register(Registries.ITEM, this.item);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void registerClient(SetupContext ctx) {
        ctx.enqueue(() -> BlockEntityRenderers.register(this.getBlockEntityType(), c -> new FeyAltarRenderer<>()));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING).add(BlockStateProperties.DOUBLE_BLOCK_HALF);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if (!context.getLevel().getBlockState(context.getClickedPos().above()).canBeReplaced(context)) return null;
        return this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite()).setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER);
    }

    @Override
    public void setPlacedBy(@Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nullable LivingEntity placer, @Nonnull ItemStack stack) {
        if (!level.isClientSide) {
            level.setBlock(pos.above(), state.setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER), 3);
            level.blockUpdated(pos, Blocks.AIR);
            state.updateNeighbourShapes(level, pos, 3);
        }
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hit) {
        FeyAltar tile;
        if (state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER) {
            tile = this.getBlockEntity(level, pos);
        } else {
            BlockState below = level.getBlockState(pos.below());
            if (below.getBlock() != this || below.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) != DoubleBlockHalf.LOWER) {
                return InteractionResult.FAIL;
            } else {
                tile = this.getBlockEntity(level, pos.below());
            }
        }
        if (!level.isClientSide) {
            if (player.isShiftKeyDown()) {
                for (int slot = tile.getInventory().getSlots() - 1; slot >= 0; slot--) {
                    if (!tile.getInventory().getStackInSlot(slot).isEmpty()) {
                        player.addItem(tile.getInventory().extractItem(slot, 64, false));
                        return InteractionResult.CONSUME;
                    }
                }
                return InteractionResult.FAIL;
            } else if (!player.getItemInHand(hand).isEmpty()) {
                for (int slot = 0; slot < tile.getInventory().getSlots(); slot++) {
                    if (tile.getInventory().getStackInSlot(slot).isEmpty()) {
                        ItemStack insertStack = player.getItemInHand(hand).copy();
                        insertStack.setCount(1);
                        if (tile.getInventory().insertItem(slot, insertStack, true).isEmpty()) {
                            tile.getInventory().insertItem(slot, insertStack, false);
                            player.getItemInHand(hand).shrink(1);
                            return InteractionResult.CONSUME;
                        }
                    }
                }
                return InteractionResult.FAIL;
            } else {
                return InteractionResult.PASS;
            }
        } else {
            if (player.isShiftKeyDown()) {
                // Will succeed on server if inventory is non-empty
                // We need to return SUCCESS in that case to swing the arm
                for (int slots = 0; slots < tile.getInventory().getSlots(); slots++) {
                    if (!tile.getInventory().getStackInSlot(slots).isEmpty()) {
                        return InteractionResult.SUCCESS;
                    }
                }
                return InteractionResult.FAIL;
            } else if (!player.getItemInHand(hand).isEmpty()) {
                // We succeed when there's at least one free slot
                for (int slots = 0; slots < tile.getInventory().getSlots(); slots++) {
                    if (tile.getInventory().getStackInSlot(slots).isEmpty()) {
                        return InteractionResult.SUCCESS;
                    }
                }
                return InteractionResult.FAIL;
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    @Override
    public void onRemove(@Nonnull BlockState oldState, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean moving) {
        if (oldState.getBlock() != newState.getBlock()) {
            Direction dir = oldState.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER ? Direction.UP : Direction.DOWN;
            if (level.getBlockState(pos.relative(dir)).getBlock() == this) {
                // No block update
                level.setBlock(pos.relative(dir), Blocks.AIR.defaultBlockState(), 2);
            }
        }
        super.onRemove(oldState, level, pos, newState, moving);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER ? SHAPE : SHAPE_TOP;
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
        return state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER ? RenderShape.ENTITYBLOCK_ANIMATED : RenderShape.INVISIBLE;
    }

    @Nullable
    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }

    public interface FeyAltarModelProperties extends GeoAnimatable {

        Alignment getAlignment();
    }
}
