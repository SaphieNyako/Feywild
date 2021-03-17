package com.feywild.feywild.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class FeyAltar extends Block {
    public FeyAltar() {
        super(AbstractBlock.Properties.create(Material.WOOD).hardnessAndResistance(18000000,3600000));
    }

    //Activate on player r click
    @SuppressWarnings("all")
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        //Server check
        if(!worldIn.isRemote){
            //Store item
            ItemStack stack = player.getHeldItem(handIn);
            // TO DO add special items that can be used for summoning the different fey courts
            // I am trying to move as much as possible to the mod so that we can reduce lag... but I don't know how to port the entities over so they will remove in data pack form which isn't so bad
        }
        return ActionResultType.SUCCESS;
    }
}
