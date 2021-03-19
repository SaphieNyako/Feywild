package com.feywild.feywild.block.entity;

import com.feywild.feywild.block.ModBlocks;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class FeyAltarBlockEntity extends TileEntity implements ITickableTileEntity {
    public FeyAltarBlockEntity() {
        super(ModBlocks.FEY_ALTAR_ENTITY.get());
    }

    @Override
    public void tick() {
        if(!world.isRemote)
        System.out.println("I am here");
    }
}
