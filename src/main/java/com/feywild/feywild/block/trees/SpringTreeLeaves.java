package com.feywild.feywild.block.trees;

import com.feywild.feywild.FeywildMod;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

import java.util.Random;

public class SpringTreeLeaves extends LeavesBlock
{
    private static final int maxDistance = 15;

    public static final IntegerProperty DISTANCE = IntegerProperty.create("more_distance", 0, maxDistance);

    public SpringTreeLeaves() {
        super(AbstractBlock.Properties.create(Material.WOOD).hardnessAndResistance(3f,10f).harvestTool(ToolType.AXE).setRequiresTool().sound(SoundType.WOOD));
        this.setDefaultState(this.stateContainer.getBaseState()
                .with(DISTANCE, Integer.valueOf(maxDistance))
                .with(PERSISTENT, Boolean.valueOf(false)));
    }

    public boolean ticksRandomly(BlockState state) {
        return state.get(DISTANCE) == maxDistance && !state.get(PERSISTENT);
    }

    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (!state.get(PERSISTENT) && state.get(DISTANCE) == maxDistance) {
            spawnDrops(state, worldIn, pos);
            worldIn.removeBlock(pos, false);
        }
    }

    private static BlockState updateDistance(BlockState state, IWorld worldIn, BlockPos pos) {
        int i = maxDistance;
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

        for(Direction direction : Direction.values()) {
            blockpos$mutable.setAndMove(pos, direction);
            i = Math.min(i, getDistance(worldIn.getBlockState(blockpos$mutable)) + 1);
            if (i == 1) {
                break;
            }
        }

        return state.with(DISTANCE, Integer.valueOf(i));
    }

    private static int getDistance(BlockState neighbor) {
        if (BlockTags.LOGS.contains(neighbor.getBlock())) {
            return 0;
        } else {
            return neighbor.getBlock() instanceof LeavesBlock ? neighbor.get(DISTANCE) : maxDistance;
        }
    }

    protected void fillStateContainer(StateContainer.Builder builder) {
        builder.add(DISTANCE);
    }
}
