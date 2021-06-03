package com.feywild.feywild.block;

import com.feywild.feywild.block.entity.FeyAltarBlockEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;

public class FeyAltar extends Block {

    Random random = new Random();

    public FeyAltar() {
        
        super(AbstractBlock.Properties.of(Material.STONE).strength(0f).noOcclusion());
    }

    //Activate on player r click
    @SuppressWarnings("all")
    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        //Server check
        if (!worldIn.isClientSide && worldIn.getBlockEntity(pos) instanceof FeyAltarBlockEntity) {
            //Store data that might get reused
            ItemStack stack = player.getItemInHand(handIn);
            FeyAltarBlockEntity entity = (FeyAltarBlockEntity) worldIn.getBlockEntity(pos);
            int flagStack = -1;
            //Remove item from inventory
            if (player.isShiftKeyDown()) {
                for (int i = entity.getContainerSize() - 1; i > -1; i--) {
                    if (!entity.getItem(i).isEmpty()) {
                        player.addItem(entity.getItem(i));
                        entity.setItem(i, ItemStack.EMPTY);
                        flagStack = i;
                        break;
                    }
                }
            } else
                //Add item to inventory if player is NOT sneaking and is holding an item
                if (!stack.isEmpty()) {
                    for (int i = 0; i < entity.getContainerSize(); i++) {
                        if (entity.getItem(i).isEmpty()) {
                            entity.setItem(i, new ItemStack(stack.getItem(), 1));
                            player.getItemInHand(handIn).shrink(1);
                            flagStack = i;
                            break;
                        }
                    }
                }
            //Format and send item data to client
            entity.updateInventory(flagStack, true);
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void playerWillDestroy(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if (worldIn.isClientSide) {
            worldIn.playSound(player, pos, SoundEvents.STONE_BREAK, SoundCategory.BLOCKS, 1, 1);
            for (int i = 0; i < 20; i++) {
                worldIn.addParticle(ParticleTypes.POOF, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, (random.nextDouble() - 0.5) / 10, (random.nextDouble() - 0.5) / 10, (random.nextDouble() - 0.5) / 10);
            }
        } else if (worldIn.getBlockEntity(pos) instanceof FeyAltarBlockEntity) {
            ItemEntity entity;
            for (ItemStack stack : ((FeyAltarBlockEntity) Objects.requireNonNull(worldIn.getBlockEntity(pos))).getItems()) {
                entity = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack);
                worldIn.addFreshEntity(entity);
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

    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

}
