package com.feywild.feywild.block;

import com.feywild.feywild.block.entity.ElectrifiedGroundTileEntity;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ElectrifiedGround extends Block {

    public ElectrifiedGround() {
        super(AbstractBlock.Properties.of(Material.AIR).noCollission());
    }
   
    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState p_220053_1_, @Nonnull IBlockReader p_220053_2_, @Nonnull BlockPos p_220053_3_, @Nonnull ISelectionContext p_220053_4_) {
        return  VoxelShapes.box(0, 0, 0, 0, 0, 0);
    }

    @Nonnull
    @Override
    public BlockRenderType getRenderShape(@Nonnull BlockState state) {
            return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }
    
    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ElectrifiedGroundTileEntity();
    }
}
