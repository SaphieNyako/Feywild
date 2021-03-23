package com.feywild.feywild.block;

import com.feywild.feywild.block.entity.FeyAltarBlockEntity;
import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.network.FeywildPacketHandler;
import com.feywild.feywild.network.ItemMessage;
import com.feywild.feywild.network.ParticleMessage;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.*;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.lwjgl.system.CallbackI;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class FeyAltar extends Block {

    //Newly added
    private static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    private static final VoxelShape SHAPE_N = getVoxelN();
    private static final VoxelShape SHAPE_E = getVoxelE();
    private static final VoxelShape SHAPE_S = getVoxelS();
    private static final VoxelShape SHAPE_W = getVoxelW();


    //Constructor
    public FeyAltar() {

        super(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(4f).notSolid().setRequiresTool().harvestTool(ToolType.PICKAXE));
    }

    //Activate on player r click
    @SuppressWarnings("all")
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        //Server check
        if (!worldIn.isRemote && worldIn.getTileEntity(pos) instanceof FeyAltarBlockEntity) {
            //Store data that might get reused
            ItemStack stack = player.getHeldItem(handIn);
            FeyAltarBlockEntity entity = (FeyAltarBlockEntity) worldIn.getTileEntity(pos);
            int flagStack = -1;
            //Remove item from inventory
            if (player.isSneaking()) {
                for (int i = entity.getSizeInventory()-1; i > -1; i--) {
                    if (!entity.getStackInSlot(i).isEmpty()) {
                        player.addItemStackToInventory(entity.getStackInSlot(i));
                        entity.setInventorySlotContents(i, ItemStack.EMPTY);
                        flagStack = i;
                        break;
                    }
                }
            } else
                //Add item to inventory if player is NOT sneaking and is holding an item
                if (!stack.isEmpty()) {
                    for (int i = 0; i < entity.getSizeInventory(); i++) {
                        if (entity.getStackInSlot(i).isEmpty()) {
                            entity.setInventorySlotContents(i, new ItemStack(stack.getItem(), 1));
                            player.getHeldItem(handIn).shrink(1);
                            flagStack = i;
                            break;
                        }
                    }
                }
                //Format and send item data to client
               entity.updateInventory(flagStack);
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBlockHarvested(worldIn, pos, state, player);
        if(worldIn.getTileEntity(pos) instanceof FeyAltarBlockEntity && !worldIn.isRemote()) {
            ItemEntity entity;
            for (ItemStack stack:((FeyAltarBlockEntity) Objects.requireNonNull(worldIn.getTileEntity(pos))).getItems()) {
                entity = new ItemEntity( worldIn,pos.getX(),pos.getY(),pos.getZ(),stack);
                worldIn.addEntity(entity);
            }
        }
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new FeyAltarBlockEntity();
    }

    //Newly Added Define Direction
    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {

        switch (state.get(FACING)){

            case NORTH:
                return SHAPE_N;
            case EAST:
                return SHAPE_E;
            case SOUTH:
                return SHAPE_S;
            case WEST:
                return SHAPE_W;
            default:
                return SHAPE_N;
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {

        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState rotate(BlockState state, Rotation rot) {

        return state.with(FACING, rot.rotate(state.get(FACING)));

    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {

        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {

        builder.add(FACING);
    }

    private static VoxelShape getVoxelN() {
       return Stream.of(
               Block.makeCuboidShape(2.490384615384616, 8.75, 2.522435897435898, 13.490384615384617, 9.25, 13.522435897435898),
               Block.makeCuboidShape(12.990384615384617, 9.25, 2.522435897435898, 13.490384615384617, 10.25, 13.522435897435898),
               Block.makeCuboidShape(2.990384615384616, 9.25, 2.522435897435898, 12.990384615384617, 10.25, 3.022435897435898),
               Block.makeCuboidShape(2.490384615384616, 9.25, 2.522435897435898, 2.990384615384616, 10.25, 13.522435897435898),
               Block.makeCuboidShape(2.990384615384616, 9.25, 13.022435897435898, 12.990384615384617, 10.25, 13.522435897435898),
               Block.makeCuboidShape(7.490384615384616, 0, 0.0224358974358978, 8.490384615384617, 0.5, 3.022435897435898),
               Block.makeCuboidShape(7.490384615384616, 6, 1.0224358974358978, 8.490384615384617, 6.5, 3.022435897435898),
               Block.makeCuboidShape(7.490384615384616, 0, 3.022435897435898, 8.490384615384617, 6.5, 3.522435897435898),
               Block.makeCuboidShape(7.490384615384616, 0.5, 0.0224358974358978, 8.490384615384617, 1.5, 0.5224358974358978),
               Block.makeCuboidShape(7.490384615384616, 6.5, 1.0224358974358978, 8.490384615384617, 7.5, 1.5224358974358978),
               Block.makeCuboidShape(8.490384615384617, 4, 12.522435897435898, 12.490384615384617, 5, 13.022435897435898),
               Block.makeCuboidShape(8.490384615384617, 4, 3.022435897435898, 12.490384615384617, 5, 3.522435897435898),
               Block.makeCuboidShape(3.490384615384616, 4, 12.522435897435898, 7.490384615384616, 5, 13.022435897435898),
               Block.makeCuboidShape(3.490384615384616, 4, 3.022435897435898, 7.490384615384616, 5, 3.522435897435898),
               Block.makeCuboidShape(12.490384615384617, 4, 8.522435897435898, 12.990384615384617, 5, 12.522435897435898),
               Block.makeCuboidShape(2.990384615384616, 4, 8.522435897435898, 3.490384615384616, 5, 12.522435897435898),
               Block.makeCuboidShape(12.490384615384617, 4, 3.522435897435898, 12.990384615384617, 5, 7.522435897435898),
               Block.makeCuboidShape(2.990384615384616, 4, 3.522435897435898, 3.490384615384616, 5, 7.522435897435898),
               Block.makeCuboidShape(-0.009615384615384137, 0, 7.522435897435898, 2.990384615384616, 0.5, 8.522435897435898),
               Block.makeCuboidShape(0.9903846153846159, 6, 7.522435897435898, 2.990384615384616, 6.5, 8.522435897435898),
               Block.makeCuboidShape(2.990384615384616, 0, 7.522435897435898, 3.490384615384616, 6.5, 8.522435897435898),
               Block.makeCuboidShape(-0.009615384615384137, 0.5, 7.522435897435898, 0.49038461538461586, 1.5, 8.522435897435898),
               Block.makeCuboidShape(0.9903846153846159, 6.5, 7.522435897435898, 1.4903846153846159, 7.5, 8.522435897435898),
               Block.makeCuboidShape(12.990384615384617, 0, 7.522435897435898, 15.99038461538462, 0.5, 8.522435897435898),
               Block.makeCuboidShape(12.990384615384617, 6, 7.522435897435898, 14.990384615384617, 6.5, 8.522435897435898),
               Block.makeCuboidShape(12.490384615384617, 0, 7.522435897435898, 12.990384615384617, 6.5, 8.522435897435898),
               Block.makeCuboidShape(15.490384615384617, 0.5, 7.522435897435898, 15.99038461538462, 1.5, 8.522435897435898),
               Block.makeCuboidShape(14.490384615384617, 6.5, 7.522435897435898, 14.990384615384617, 7.5, 8.522435897435898),
               Block.makeCuboidShape(7.490384615384616, 0, 13.022435897435898, 8.490384615384617, 0.5, 16.022435897435898),
               Block.makeCuboidShape(7.490384615384616, 6, 13.022435897435898, 8.490384615384617, 6.5, 15.022435897435898),
               Block.makeCuboidShape(7.490384615384616, 0, 12.522435897435898, 8.490384615384617, 6.5, 13.022435897435898),
               Block.makeCuboidShape(7.490384615384616, 0.5, 15.522435897435898, 8.490384615384617, 1.5, 16.022435897435898),
               Block.makeCuboidShape(7.490384615384616, 6.5, 14.522435897435898, 8.490384615384617, 7.5, 15.022435897435898),
               Block.makeCuboidShape(8.115384615384617, 1.625, 6.272435897435898, 11.115384615384617, 6.625, 10.772435897435898),
               Block.makeCuboidShape(5.115384615384616, 1.625, 6.272435897435898, 8.115384615384617, 7.625, 9.522435897435898),
               Block.makeCuboidShape(8.115384615384617, 6.625, 6.272435897435898, 9.115384615384617, 7.625, 9.522435897435898),
               Block.makeCuboidShape(6.115384615384616, 0.625, 6.272435897435898, 10.115384615384617, 1.625, 9.522435897435898),
               Block.makeCuboidShape(6.115384615384616, 1.625, 5.022435897435898, 9.115384615384617, 5.625, 6.272435897435898),
               Block.makeCuboidShape(6.115384615384616, 1.625, 9.022435897435898, 8.115384615384617, 5.625, 10.272435897435898)
       ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
    }

    private static VoxelShape getVoxelE() {
        return Stream.of(
                Block.makeCuboidShape(2.490384615384616, 8.75, 2.522435897435898, 13.490384615384617, 9.25, 13.522435897435898),
                Block.makeCuboidShape(12.990384615384617, 9.25, 2.522435897435898, 13.490384615384617, 10.25, 13.522435897435898),
                Block.makeCuboidShape(2.990384615384616, 9.25, 2.522435897435898, 12.990384615384617, 10.25, 3.022435897435898),
                Block.makeCuboidShape(2.490384615384616, 9.25, 2.522435897435898, 2.990384615384616, 10.25, 13.522435897435898),
                Block.makeCuboidShape(2.990384615384616, 9.25, 13.022435897435898, 12.990384615384617, 10.25, 13.522435897435898),
                Block.makeCuboidShape(7.490384615384616, 0, 0.0224358974358978, 8.490384615384617, 0.5, 3.022435897435898),
                Block.makeCuboidShape(7.490384615384616, 6, 1.0224358974358978, 8.490384615384617, 6.5, 3.022435897435898),
                Block.makeCuboidShape(7.490384615384616, 0, 3.022435897435898, 8.490384615384617, 6.5, 3.522435897435898),
                Block.makeCuboidShape(7.490384615384616, 0.5, 0.0224358974358978, 8.490384615384617, 1.5, 0.5224358974358978),
                Block.makeCuboidShape(7.490384615384616, 6.5, 1.0224358974358978, 8.490384615384617, 7.5, 1.5224358974358978),
                Block.makeCuboidShape(8.490384615384617, 4, 12.522435897435898, 12.490384615384617, 5, 13.022435897435898),
                Block.makeCuboidShape(8.490384615384617, 4, 3.022435897435898, 12.490384615384617, 5, 3.522435897435898),
                Block.makeCuboidShape(3.490384615384616, 4, 12.522435897435898, 7.490384615384616, 5, 13.022435897435898),
                Block.makeCuboidShape(3.490384615384616, 4, 3.022435897435898, 7.490384615384616, 5, 3.522435897435898),
                Block.makeCuboidShape(12.490384615384617, 4, 8.522435897435898, 12.990384615384617, 5, 12.522435897435898),
                Block.makeCuboidShape(2.990384615384616, 4, 8.522435897435898, 3.490384615384616, 5, 12.522435897435898),
                Block.makeCuboidShape(12.490384615384617, 4, 3.522435897435898, 12.990384615384617, 5, 7.522435897435898),
                Block.makeCuboidShape(2.990384615384616, 4, 3.522435897435898, 3.490384615384616, 5, 7.522435897435898),
                Block.makeCuboidShape(-0.009615384615384137, 0, 7.522435897435898, 2.990384615384616, 0.5, 8.522435897435898),
                Block.makeCuboidShape(0.9903846153846159, 6, 7.522435897435898, 2.990384615384616, 6.5, 8.522435897435898),
                Block.makeCuboidShape(2.990384615384616, 0, 7.522435897435898, 3.490384615384616, 6.5, 8.522435897435898),
                Block.makeCuboidShape(-0.009615384615384137, 0.5, 7.522435897435898, 0.49038461538461586, 1.5, 8.522435897435898),
                Block.makeCuboidShape(0.9903846153846159, 6.5, 7.522435897435898, 1.4903846153846159, 7.5, 8.522435897435898),
                Block.makeCuboidShape(12.990384615384617, 0, 7.522435897435898, 15.99038461538462, 0.5, 8.522435897435898),
                Block.makeCuboidShape(12.990384615384617, 6, 7.522435897435898, 14.990384615384617, 6.5, 8.522435897435898),
                Block.makeCuboidShape(12.490384615384617, 0, 7.522435897435898, 12.990384615384617, 6.5, 8.522435897435898),
                Block.makeCuboidShape(15.490384615384617, 0.5, 7.522435897435898, 15.99038461538462, 1.5, 8.522435897435898),
                Block.makeCuboidShape(14.490384615384617, 6.5, 7.522435897435898, 14.990384615384617, 7.5, 8.522435897435898),
                Block.makeCuboidShape(7.490384615384616, 0, 13.022435897435898, 8.490384615384617, 0.5, 16.022435897435898),
                Block.makeCuboidShape(7.490384615384616, 6, 13.022435897435898, 8.490384615384617, 6.5, 15.022435897435898),
                Block.makeCuboidShape(7.490384615384616, 0, 12.522435897435898, 8.490384615384617, 6.5, 13.022435897435898),
                Block.makeCuboidShape(7.490384615384616, 0.5, 15.522435897435898, 8.490384615384617, 1.5, 16.022435897435898),
                Block.makeCuboidShape(7.490384615384616, 6.5, 14.522435897435898, 8.490384615384617, 7.5, 15.022435897435898),
                Block.makeCuboidShape(6.249999999999999, 1.625, 4.875, 10.75, 6.625, 7.875),
                Block.makeCuboidShape(6.249999999999999, 1.625, 7.875, 9.5, 7.625, 10.875),
                Block.makeCuboidShape(6.249999999999999, 6.625, 6.875, 9.5, 7.625, 7.875),
                Block.makeCuboidShape(6.249999999999999, 0.625, 5.875, 9.5, 1.625, 9.875),
                Block.makeCuboidShape(4.999999999999999, 1.625, 6.875, 6.249999999999999, 5.625, 9.875),
                Block.makeCuboidShape(9, 1.625, 7.875, 10.25, 5.625, 9.875)
        ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
    }

    private static VoxelShape getVoxelS() {
        return Stream.of(
                Block.makeCuboidShape(2.490384615384616, 8.75, 2.522435897435898, 13.490384615384617, 9.25, 13.522435897435898),
                Block.makeCuboidShape(12.990384615384617, 9.25, 2.522435897435898, 13.490384615384617, 10.25, 13.522435897435898),
                Block.makeCuboidShape(2.990384615384616, 9.25, 2.522435897435898, 12.990384615384617, 10.25, 3.022435897435898),
                Block.makeCuboidShape(2.490384615384616, 9.25, 2.522435897435898, 2.990384615384616, 10.25, 13.522435897435898),
                Block.makeCuboidShape(2.990384615384616, 9.25, 13.022435897435898, 12.990384615384617, 10.25, 13.522435897435898),
                Block.makeCuboidShape(7.490384615384616, 0, 0.0224358974358978, 8.490384615384617, 0.5, 3.022435897435898),
                Block.makeCuboidShape(7.490384615384616, 6, 1.0224358974358978, 8.490384615384617, 6.5, 3.022435897435898),
                Block.makeCuboidShape(7.490384615384616, 0, 3.022435897435898, 8.490384615384617, 6.5, 3.522435897435898),
                Block.makeCuboidShape(7.490384615384616, 0.5, 0.0224358974358978, 8.490384615384617, 1.5, 0.5224358974358978),
                Block.makeCuboidShape(7.490384615384616, 6.5, 1.0224358974358978, 8.490384615384617, 7.5, 1.5224358974358978),
                Block.makeCuboidShape(8.490384615384617, 4, 12.522435897435898, 12.490384615384617, 5, 13.022435897435898),
                Block.makeCuboidShape(8.490384615384617, 4, 3.022435897435898, 12.490384615384617, 5, 3.522435897435898),
                Block.makeCuboidShape(3.490384615384616, 4, 12.522435897435898, 7.490384615384616, 5, 13.022435897435898),
                Block.makeCuboidShape(3.490384615384616, 4, 3.022435897435898, 7.490384615384616, 5, 3.522435897435898),
                Block.makeCuboidShape(12.490384615384617, 4, 8.522435897435898, 12.990384615384617, 5, 12.522435897435898),
                Block.makeCuboidShape(2.990384615384616, 4, 8.522435897435898, 3.490384615384616, 5, 12.522435897435898),
                Block.makeCuboidShape(12.490384615384617, 4, 3.522435897435898, 12.990384615384617, 5, 7.522435897435898),
                Block.makeCuboidShape(2.990384615384616, 4, 3.522435897435898, 3.490384615384616, 5, 7.522435897435898),
                Block.makeCuboidShape(-0.009615384615384137, 0, 7.522435897435898, 2.990384615384616, 0.5, 8.522435897435898),
                Block.makeCuboidShape(0.9903846153846159, 6, 7.522435897435898, 2.990384615384616, 6.5, 8.522435897435898),
                Block.makeCuboidShape(2.990384615384616, 0, 7.522435897435898, 3.490384615384616, 6.5, 8.522435897435898),
                Block.makeCuboidShape(-0.009615384615384137, 0.5, 7.522435897435898, 0.49038461538461586, 1.5, 8.522435897435898),
                Block.makeCuboidShape(0.9903846153846159, 6.5, 7.522435897435898, 1.4903846153846159, 7.5, 8.522435897435898),
                Block.makeCuboidShape(12.990384615384617, 0, 7.522435897435898, 15.99038461538462, 0.5, 8.522435897435898),
                Block.makeCuboidShape(12.990384615384617, 6, 7.522435897435898, 14.990384615384617, 6.5, 8.522435897435898),
                Block.makeCuboidShape(12.490384615384617, 0, 7.522435897435898, 12.990384615384617, 6.5, 8.522435897435898),
                Block.makeCuboidShape(15.490384615384617, 0.5, 7.522435897435898, 15.99038461538462, 1.5, 8.522435897435898),
                Block.makeCuboidShape(14.490384615384617, 6.5, 7.522435897435898, 14.990384615384617, 7.5, 8.522435897435898),
                Block.makeCuboidShape(7.490384615384616, 0, 13.022435897435898, 8.490384615384617, 0.5, 16.022435897435898),
                Block.makeCuboidShape(7.490384615384616, 6, 13.022435897435898, 8.490384615384617, 6.5, 15.022435897435898),
                Block.makeCuboidShape(7.490384615384616, 0, 12.522435897435898, 8.490384615384617, 6.5, 13.022435897435898),
                Block.makeCuboidShape(7.490384615384616, 0.5, 15.522435897435898, 8.490384615384617, 1.5, 16.022435897435898),
                Block.makeCuboidShape(7.490384615384616, 6.5, 14.522435897435898, 8.490384615384617, 7.5, 15.022435897435898),
                Block.makeCuboidShape(4.852564102564101, 1.625, 5.240384615384617, 7.852564102564101, 6.625, 9.740384615384617),
                Block.makeCuboidShape(7.852564102564101, 1.625, 6.490384615384617, 10.852564102564102, 7.625, 9.740384615384617),
                Block.makeCuboidShape(6.852564102564101, 6.625, 6.490384615384617, 7.852564102564101, 7.625, 9.740384615384617),
                Block.makeCuboidShape(5.852564102564101, 0.625, 6.490384615384617, 9.852564102564102, 1.625, 9.740384615384617),
                Block.makeCuboidShape(6.852564102564101, 1.625, 9.740384615384617, 9.852564102564102, 5.625, 10.990384615384617),
                Block.makeCuboidShape(7.852564102564101, 1.625, 5.740384615384617, 9.852564102564102, 5.625, 6.990384615384617)
        ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
    }

    private static VoxelShape getVoxelW() {
       return Stream.of(
               Block.makeCuboidShape(2.490384615384616, 8.75, 2.522435897435898, 13.490384615384617, 9.25, 13.522435897435898),
               Block.makeCuboidShape(12.990384615384617, 9.25, 2.522435897435898, 13.490384615384617, 10.25, 13.522435897435898),
               Block.makeCuboidShape(2.990384615384616, 9.25, 2.522435897435898, 12.990384615384617, 10.25, 3.022435897435898),
               Block.makeCuboidShape(2.490384615384616, 9.25, 2.522435897435898, 2.990384615384616, 10.25, 13.522435897435898),
               Block.makeCuboidShape(2.990384615384616, 9.25, 13.022435897435898, 12.990384615384617, 10.25, 13.522435897435898),
               Block.makeCuboidShape(7.490384615384616, 0, 0.0224358974358978, 8.490384615384617, 0.5, 3.022435897435898),
               Block.makeCuboidShape(7.490384615384616, 6, 1.0224358974358978, 8.490384615384617, 6.5, 3.022435897435898),
               Block.makeCuboidShape(7.490384615384616, 0, 3.022435897435898, 8.490384615384617, 6.5, 3.522435897435898),
               Block.makeCuboidShape(7.490384615384616, 0.5, 0.0224358974358978, 8.490384615384617, 1.5, 0.5224358974358978),
               Block.makeCuboidShape(7.490384615384616, 6.5, 1.0224358974358978, 8.490384615384617, 7.5, 1.5224358974358978),
               Block.makeCuboidShape(8.490384615384617, 4, 12.522435897435898, 12.490384615384617, 5, 13.022435897435898),
               Block.makeCuboidShape(8.490384615384617, 4, 3.022435897435898, 12.490384615384617, 5, 3.522435897435898),
               Block.makeCuboidShape(3.490384615384616, 4, 12.522435897435898, 7.490384615384616, 5, 13.022435897435898),
               Block.makeCuboidShape(3.490384615384616, 4, 3.022435897435898, 7.490384615384616, 5, 3.522435897435898),
               Block.makeCuboidShape(12.490384615384617, 4, 8.522435897435898, 12.990384615384617, 5, 12.522435897435898),
               Block.makeCuboidShape(2.990384615384616, 4, 8.522435897435898, 3.490384615384616, 5, 12.522435897435898),
               Block.makeCuboidShape(12.490384615384617, 4, 3.522435897435898, 12.990384615384617, 5, 7.522435897435898),
               Block.makeCuboidShape(2.990384615384616, 4, 3.522435897435898, 3.490384615384616, 5, 7.522435897435898),
               Block.makeCuboidShape(-0.009615384615384137, 0, 7.522435897435898, 2.990384615384616, 0.5, 8.522435897435898),
               Block.makeCuboidShape(0.9903846153846159, 6, 7.522435897435898, 2.990384615384616, 6.5, 8.522435897435898),
               Block.makeCuboidShape(2.990384615384616, 0, 7.522435897435898, 3.490384615384616, 6.5, 8.522435897435898),
               Block.makeCuboidShape(-0.009615384615384137, 0.5, 7.522435897435898, 0.49038461538461586, 1.5, 8.522435897435898),
               Block.makeCuboidShape(0.9903846153846159, 6.5, 7.522435897435898, 1.4903846153846159, 7.5, 8.522435897435898),
               Block.makeCuboidShape(12.990384615384617, 0, 7.522435897435898, 15.99038461538462, 0.5, 8.522435897435898),
               Block.makeCuboidShape(12.990384615384617, 6, 7.522435897435898, 14.990384615384617, 6.5, 8.522435897435898),
               Block.makeCuboidShape(12.490384615384617, 0, 7.522435897435898, 12.990384615384617, 6.5, 8.522435897435898),
               Block.makeCuboidShape(15.490384615384617, 0.5, 7.522435897435898, 15.99038461538462, 1.5, 8.522435897435898),
               Block.makeCuboidShape(14.490384615384617, 6.5, 7.522435897435898, 14.990384615384617, 7.5, 8.522435897435898),
               Block.makeCuboidShape(7.490384615384616, 0, 13.022435897435898, 8.490384615384617, 0.5, 16.022435897435898),
               Block.makeCuboidShape(7.490384615384616, 6, 13.022435897435898, 8.490384615384617, 6.5, 15.022435897435898),
               Block.makeCuboidShape(7.490384615384616, 0, 12.522435897435898, 8.490384615384617, 6.5, 13.022435897435898),
               Block.makeCuboidShape(7.490384615384616, 0.5, 15.522435897435898, 8.490384615384617, 1.5, 16.022435897435898),
               Block.makeCuboidShape(7.490384615384616, 6.5, 14.522435897435898, 8.490384615384617, 7.5, 15.022435897435898),
               Block.makeCuboidShape(5.217948717948718, 1.625, 8.137820512820515, 9.717948717948719, 6.625, 11.137820512820515),
               Block.makeCuboidShape(6.467948717948718, 1.625, 5.1378205128205146, 9.717948717948719, 7.625, 8.137820512820515),
               Block.makeCuboidShape(6.467948717948718, 6.625, 8.137820512820515, 9.717948717948719, 7.625, 9.137820512820515),
               Block.makeCuboidShape(6.467948717948718, 0.625, 6.1378205128205146, 9.717948717948719, 1.625, 10.137820512820515),
               Block.makeCuboidShape(9.717948717948719, 1.625, 6.1378205128205146, 10.967948717948719, 5.625, 9.137820512820515),
               Block.makeCuboidShape(5.717948717948718, 1.625, 6.1378205128205146, 6.967948717948718, 5.625, 8.137820512820515)
       ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
    }


}
