package com.feywild.feywild.block;

import com.feywild.feywild.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class LibraryBell extends Block {

    private VillagerEntity entity = null;
    private int count;

    public LibraryBell() {
        super(Properties.of(Material.METAL)
                .strength(99999999f, 99999999f).noDrops()
                .noCollission()
                .randomTicks()
                .sound(SoundType.STONE));

    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
        count++;
        if (count > 3 && entity != null) {
            entity.remove();
            count = 0;
        }
    }

    @Override
    public void onRemove(BlockState p_196243_1_, World p_196243_2_, BlockPos p_196243_3_, BlockState p_196243_4_, boolean p_196243_5_) {
        if (entity != null)
            entity.remove();
        super.onRemove(p_196243_1_, p_196243_2_, p_196243_3_, p_196243_4_, p_196243_5_);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.box(0.01, 0.01, 0.01, 0.99, 0.99, 0.99);

    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult trace) {
        if (world.isClientSide) {
            world.playSound(playerEntity, pos, SoundEvents.NOTE_BLOCK_BELL, SoundCategory.BLOCKS, 1f, 1.2f);
        } else {
            if (entity != null)
                entity.remove();
            entity = new VillagerEntity(EntityType.VILLAGER, world);
            entity.addTag("spawn_librarian");
            if (world.getBlockState(pos.north()).isAir()) {
                entity.setPos(pos.getX() + 0.5, pos.getY() - 1, pos.getZ() - 1);
            } else if (world.getBlockState(pos.south()).isAir()) {
                entity.setPos(pos.getX() + 0.5, pos.getY() - 1, pos.getZ() + 2);
            } else if (world.getBlockState(pos.east()).isAir()) {
                entity.setPos(pos.getX() + 2, pos.getY() - 1, pos.getZ() + 0.5);
            } else if (world.getBlockState(pos.west()).isAir()) {
                entity.setPos(pos.getX() - 1, pos.getY() - 1, pos.getZ() + 0.5);
            }
            world.addFreshEntity(entity);
            ModUtil.librarians.add(entity);
        }
        return ActionResultType.SUCCESS;

    }
}
