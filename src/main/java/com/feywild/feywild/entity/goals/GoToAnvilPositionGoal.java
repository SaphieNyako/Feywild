package com.feywild.feywild.entity.goals;

import com.feywild.feywild.block.entity.DwarvenAnvil;
import com.feywild.feywild.entity.DwarfBlacksmithEntity;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import java.util.function.Supplier;

public class GoToAnvilPositionGoal extends MovementRestrictionGoal {

    private final DwarfBlacksmithEntity entity;
    private DwarvenAnvil tile;
    private int ticksLeft = 0;

    public GoToAnvilPositionGoal(DwarfBlacksmithEntity entity, Supplier<BlockPos> pos, int maxMovementRange) {
        super(asVector(pos), maxMovementRange);
        this.entity = entity;
    }

    @Override
    public void tick() {
        this.init();
        if (!this.entity.isTamed()) {
            this.reset();
        } else if (this.tile != null && this.ticksLeft > 0) {
            if (!this.tile.canCraft()) {
                this.reset();
                return;
            }
            this.ticksLeft--;
            if (this.ticksLeft == 0) {
                this.tile.craft();
                this.reset();
            } else {
                Vector3d target = this.targetPosition.get();
                if (target != null && this.tile.canCraft()) {
                    if (this.ticksLeft == 20) {
                        this.entity.playSound(SoundEvents.ANVIL_USE, 1, 1);
                    } else if (this.ticksLeft == 50) {
                        this.entity.setState(DwarfBlacksmithEntity.State.WORKING);
                    } else if (this.ticksLeft <= 110) {
                        this.entity.getNavigation().moveTo(target.x, target.y, target.z, 0.5);
                        this.entity.lookAt(EntityAnchorArgument.Type.EYES, target);
                    }
                } else {
                    this.reset();
                }
            }
        }
    }

    @Override
    public void start() {
        this.ticksLeft = 120;
    }

    protected void reset() {
        this.entity.setState(DwarfBlacksmithEntity.State.IDLE);
        this.ticksLeft = -1;
    }

    @Override
    public boolean canContinueToUse() {
        this.init();
        return this.ticksLeft > 0 && this.entity.isTamed() && this.tile != null && this.targetPosition.get() != null;
    }

    @Override
    public boolean canUse() {
        this.init();
        return this.tile != null && this.targetPosition.get() != null && this.tile.canCraft();
    }

    private void init() {
        if (this.tile == null && this.entity.getSummonPos() != null) {
            TileEntity tile = this.entity.level.getBlockEntity(this.entity.getSummonPos());
            this.tile = tile instanceof DwarvenAnvil ? (DwarvenAnvil) tile : null;
        }
    }
}

