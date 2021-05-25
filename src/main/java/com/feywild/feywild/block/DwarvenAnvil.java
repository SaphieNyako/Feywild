package com.feywild.feywild.block;

import com.feywild.feywild.block.entity.DwarvenAnvilEntity;
import com.feywild.feywild.container.DwarvenAnvilContainer;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class DwarvenAnvil extends Block {

    public DwarvenAnvil() {
        super(AbstractBlock.Properties.of(Material.HEAVY_METAL)
                .strength(3f, 10f)
                .harvestLevel(2)
                .sound(SoundType.ANVIL)
                .lightLevel(value -> 14));

        // this.registerDefaultState(this.stateDefinition.any().setValue(BlockStateProperties.FACING, Direction.NORTH));

    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING, BlockStateProperties.POWERED);
    }

    //TODO: FIX FACING ANVIL- only faces one way.
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return defaultBlockState().setValue(BlockStateProperties.FACING, context.getNearestLookingDirection().getOpposite());
    }

    @Override

    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return 14;

        // return state.hasProperty(BlockStateProperties.POWERED) ? super.getLightValue(state.getBlockState(), world, pos) : 0;

    }

    //TODO: Add correct voxel shapes.

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.box(0.01, 0.01, 0.01, 0.99, 0.99, 0.99);
    }

    /* TILE ENTITY */

    // on player right click
    @SuppressWarnings("deprecation")
    @Override //onBlockActivated
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
