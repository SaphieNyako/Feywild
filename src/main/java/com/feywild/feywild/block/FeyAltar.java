package com.feywild.feywild.block;

import com.feywild.feywild.block.entity.FeyAltarBlockEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class FeyAltar extends Block {
    public FeyAltar() {
        super(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(4f).harvestTool(ToolType.PICKAXE));
    }

    //Activate on player r click
    @SuppressWarnings("all")
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        //Server check
        if(!worldIn.isRemote && worldIn.getTileEntity(pos) instanceof FeyAltarBlockEntity){
            //Store data that might get reused
            ItemStack stack = player.getHeldItem(handIn);
            FeyAltarBlockEntity entity = (FeyAltarBlockEntity) worldIn.getTileEntity(pos);
            LazyOptional<ItemStackHandler> handler = entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).cast();

            //Remove item from inventory
            if(player.isSneaking()){
                handler.ifPresent(itemStackHandler -> {
                    for (int i = itemStackHandler.getSlots() -1; i >-1; i--) {
                        if (!itemStackHandler.getStackInSlot(i).isEmpty()) {
                            player.addItemStackToInventory(itemStackHandler.getStackInSlot(i));
                            itemStackHandler.setStackInSlot(i, ItemStack.EMPTY);
                            break;
                        }
                    }
                });
            }else
            //Add item to inventory if player is NOT sneaking and is holding an item
            if(!stack.isEmpty()) {
                handler.ifPresent(itemStackHandler -> {
                    for (int i = 0; i < itemStackHandler.getSlots(); i++) {
                        if (itemStackHandler.getStackInSlot(i).isEmpty()) {
                            itemStackHandler.setStackInSlot(i, new ItemStack(stack.getItem(), 1));
                            player.getHeldItem(handIn).shrink(1);
                            break;
                        }
                    }
                });
            }
            //Here we should mark this dirty... when I add the method for it
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
        return new FeyAltarBlockEntity();
    }
}
