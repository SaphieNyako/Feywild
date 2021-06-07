package com.feywild.feywild.block;

import com.feywild.feywild.block.entity.DwarvenAnvilEntity;
import com.feywild.feywild.container.DwarvenAnvilContainer;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class DwarvenAnvil extends Block {

    private static final DirectionProperty FACING = HorizontalBlock.FACING;

    public DwarvenAnvil() {
        super(AbstractBlock.Properties.of(Material.HEAVY_METAL)
                .strength(3f, 10f)
                .harvestLevel(2)
                .sound(SoundType.ANVIL)
                .lightLevel(value -> 14));

        //  StateContainer.Builder<Block, BlockState> builder = new StateContainer.Builder<>(this);
        //  this.createBlockStateDefinition(builder.add(FACING));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));

    }

    public static Direction getFacingFromEntity(BlockPos clickedBlock, LivingEntity entity) {
        Vector3d vec = entity.position();
        Direction direction = Direction.getNearest((float) (vec.x - clickedBlock.getX()), (float) (vec.y - clickedBlock.getY()), (float) (vec.z - clickedBlock.getZ()));
        if (direction == Direction.UP || direction == Direction.DOWN)
            direction = Direction.NORTH;
        return direction;
    }

    @Override
    public void setPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        if (entity != null) {
            world.setBlock(pos, state.setValue(FACING, getFacingFromEntity(pos, entity)), 2);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
    
  /*  @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        //  return defaultBlockState().setValue(BlockStateProperties.FACING, context.getNearestLookingDirection().getOpposite());
        return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite());
    } */

    //TODO: Add correct voxel shapes.
    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.box(0.01, 0.01, 0.01, 0.99, 0.99, 0.99);
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState rotate(BlockState state, Rotation direction) {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return 14;

        // return state.hasProperty(BlockStateProperties.POWERED) ? super.getLightValue(state.getBlockState(), world, pos) : 0;

    }

    /* TILE ENTITY */

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {

        if (!worldIn.isClientSide) {
            TileEntity tileEntity = worldIn.getBlockEntity(pos);

            if (tileEntity instanceof DwarvenAnvilEntity) {
                INamedContainerProvider containerProvider = new INamedContainerProvider() {
                    @Override
                    public ITextComponent getDisplayName() {
                        return new TranslationTextComponent("screen.feywild.dwarven_anvil");
                    }

                    @Nullable
                    @Override
                    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {

                        return new DwarvenAnvilContainer(i, worldIn, pos, playerInventory, playerEntity);
                    }
                };

                NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, tileEntity.getBlockPos());

            } else {

                throw new IllegalStateException("Our container provider is missing!");
            }

        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void playerWillDestroy(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        IItemHandler cap = world.getBlockEntity(pos).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);

        for (int i = 0; i < cap.getSlots(); i++) {
            ItemStack toDrop = cap.getStackInSlot(i);
            if (!toDrop.isEmpty()) {
                InventoryHelper.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), toDrop);
            }
        }
        super.playerWillDestroy(world, pos, state, player);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {

        //  return ModBlocks.DWARVEN_ANVIL_ENTITY.get().create();
        return new DwarvenAnvilEntity();
    }

}
