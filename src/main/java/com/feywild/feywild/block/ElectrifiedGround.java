package com.feywild.feywild.block;

import com.feywild.feywild.block.entity.ElectrifiedGroundTileEntity;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.lwjgl.system.CallbackI;

import javax.annotation.Nullable;
import java.util.Random;

public class ElectrifiedGround extends Block {

    public ElectrifiedGround() {
        super(AbstractBlock.Properties.of(Material.AIR).noCollission());
    }
    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return  VoxelShapes.box(0, 0, 0, 0, 0, 0);
    }



    @Override
    public BlockRenderType getRenderShape(BlockState state) {
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
