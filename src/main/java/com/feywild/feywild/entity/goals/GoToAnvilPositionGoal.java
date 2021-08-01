package com.feywild.feywild.entity.goals;

import com.feywild.feywild.block.entity.DwarvenAnvilEntity;
import com.feywild.feywild.entity.DwarfBlacksmithEntity;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import java.util.function.Supplier;

public class GoToAnvilPositionGoal extends MovementRestrictionGoal {

    DwarfBlacksmithEntity entity;
    private DwarvenAnvilEntity tile;
    private int ticksLeft = 0;

    public GoToAnvilPositionGoal(DwarfBlacksmithEntity entity, Supplier<BlockPos> pos, int maxMovementRange) {
        super(pos, maxMovementRange);
        this.entity = entity;
    }

    @Override
    public void tick() {
        this.init();
        if (!entity.isTamed()) {
            reset();
        } else if (tile != null) {
            ticksLeft--;
            if (ticksLeft <= 0) {
                tile.craft();
                reset();
            } else if (targetPosition != null && tile.canCraft()) {
                if (ticksLeft == 20) {
                    entity.playSound(SoundEvents.ANVIL_USE, 1.0f, 1.0f);
                } else if (ticksLeft == 50) {
                    tile.craft();
                    entity.setState(DwarfBlacksmithEntity.State.WORKING);
                } else if (ticksLeft <= 110) {
                    entity.getNavigation().moveTo(this.targetPosition.getX(), this.targetPosition.getY(), this.targetPosition.getZ(), 0.5);
                    entity.lookAt(EntityAnchorArgument.Type.EYES, new Vector3d(targetPosition.getX(), targetPosition.getY(), targetPosition.getZ()));
                }
            } else {
                reset();
            }
        }
    }

    @Override
    public void start() {
        ticksLeft = 120;
    }

    protected void reset() {
        entity.setState(DwarfBlacksmithEntity.State.IDLE);
    }

    @Override
    public boolean canContinueToUse() {
        init();
        return entity.isTamed() && tile != null && targetPosition != null && tile.canCraft();
    }

    @Override
    public boolean canUse() {
        init();
        return tile != null && targetPosition != null && entity.level.random.nextFloat() < 0.05f && tile.canCraft();
    }
    
    private void init() {
        if (this.tile == null && entity.getSummonPos() != null) {
            TileEntity tile = entity.level.getBlockEntity(entity.getSummonPos());
            this.tile = tile instanceof DwarvenAnvilEntity ? (DwarvenAnvilEntity) tile : null;
        }
    }
}

