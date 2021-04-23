package com.feywild.feywild.block.trees;

import com.feywild.feywild.entity.AutumnPixieEntity;
import com.feywild.feywild.entity.ModEntityTypes;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class SummerTreeLog extends Block {

    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
    public static final BooleanProperty GROWN = BooleanProperty.create("grown_by_sapling");

    public SummerTreeLog() {
        super(AbstractBlock.Properties.from(Blocks.JUNGLE_WOOD));

        this.setDefaultState(this.stateContainer.getBaseState()
                .with(GROWN, true)
                .with(AXIS, Direction.Axis.Y));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder builder) {

        builder.add(GROWN, AXIS);

    }

    //WHEN PLACED BY PLAYER SHOULD BE FALSE.

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(GROWN, Boolean.valueOf(false))
                .with(AXIS, context.getFace().getAxis());
    }


    public BlockState rotate(BlockState state, Rotation rot) {
        switch(rot) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
                switch((Direction.Axis)state.get(AXIS)) {
                    case X:
                        return state.with(AXIS, Direction.Axis.Z);
                    case Z:
                        return state.with(AXIS, Direction.Axis.X);
                    default:
                        return state;
                }
            default:
                return state;
        }
    }


    @Override
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {

        Random rand = new Random();

        if(!worldIn.isRemote && pos.getY() >= 68 && state.get(SummerTreeLog.GROWN)) // && GROWN.equals(true)
            if (rand.nextDouble() < 0.5) { //0.01

                if (worldIn.isAirBlock(pos.north())) {

                    worldIn.setBlockState(pos.north(), Blocks.BEE_NEST.getDefaultState());

                    //doesn't work.
                    spawnBee(worldIn, pos);
                }
            }
    }

    public void spawnBee(World worldIn, BlockPos pos) {

            BeeEntity entity = new BeeEntity(EntityType.BEE, worldIn);
            entity.setPosition(pos.getX() + 5, pos.getY(), pos.getX() + 10);
            worldIn.addEntity(entity);
    }
}
