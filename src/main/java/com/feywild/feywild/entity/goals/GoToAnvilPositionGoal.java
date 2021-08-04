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
        } else if (tile != null && ticksLeft > 0) {
            ticksLeft--;
            if (ticksLeft == 0) {
                tile.craft();
                reset();
            } else {
                BlockPos target = targetPosition.get();
                if (target != null && tile.canCraft()) {
                    if (ticksLeft == 20) {
                        entity.playSound(SoundEvents.ANVIL_USE, 1.0f, 1.0f);
                        entity.setState(DwarfBlacksmithEntity.State.IDLE);
                    } else if (ticksLeft == 50) {
                        entity.setState(DwarfBlacksmithEntity.State.WORKING);
                    } else if (ticksLeft <= 110) {
                        entity.getNavigation().moveTo(target.getX(), target.getY(), target.getZ(), 0.5);
                        entity.lookAt(EntityAnchorArgument.Type.EYES, new Vector3d(target.getX(), target.getY(), target.getZ()));
                    }
                } else {
                    reset();
                }
            }
        }
    }

    @Override
    public void start() {
        ticksLeft = 120;
    }

    protected void reset() {
        entity.setState(DwarfBlacksmithEntity.State.IDLE);
        ticksLeft = -1;
    }

    @Override
    public boolean canContinueToUse() {
        init();
        return ticksLeft > 0 && entity.isTamed() && tile != null && targetPosition.get() != null && tile.canCraft();
    }

    @Override
    public boolean canUse() {
        init();
        return tile != null && targetPosition.get() != null && tile.canCraft();
    }

    private void init() {
        if (this.tile == null && entity.getSummonPos() != null) {
            TileEntity tile = entity.level.getBlockEntity(entity.getSummonPos());
            this.tile = tile instanceof DwarvenAnvilEntity ? (DwarvenAnvilEntity) tile : null;
        }
    }
}

