package com.feywild.feywild.block.trees;

import com.feywild.feywild.entity.AutumnPixieEntity;
import com.feywild.feywild.entity.ModEntityTypes;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.CowEntity;
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
        super(AbstractBlock.Properties.copy(Blocks.JUNGLE_WOOD));

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(GROWN, true)
                .setValue(AXIS, Direction.Axis.Y));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder builder) {

        builder.add(GROWN, AXIS);

    }

    //WHEN PLACED BY PLAYER SHOULD BE FALSE.
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(GROWN, Boolean.valueOf(false))
                .setValue(AXIS, context.getClickedFace().getAxis());
    }


    public BlockState rotate(BlockState state, Rotation rot) {
        switch(rot) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
                switch((Direction.Axis)state.getValue(AXIS)) {
                    case X:
                        return state.setValue(AXIS, Direction.Axis.Z);
                    case Z:
                        return state.setValue(AXIS, Direction.Axis.X);
                    default:
                        return state;
                }
            default:
                return state;
        }
    }
}
