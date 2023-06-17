package com.feywild.feywild.entity.goals.dwarf;

import com.feywild.feywild.block.entity.DwarvenAnvil;
import com.feywild.feywild.entity.DwarfBlacksmith;
import com.feywild.feywild.entity.goals.MovementRestrictionGoal;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

import java.util.function.Supplier;

public class GoToAnvilPositionGoal extends MovementRestrictionGoal {

    private final DwarfBlacksmith entity;
    private DwarvenAnvil tile;
    private int ticksLeft = 0;

    public GoToAnvilPositionGoal(DwarfBlacksmith entity, Supplier<BlockPos> pos, int maxMovementRange) {
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
                Vec3 target = this.targetPosition.get();
                if (target != null && this.tile.canCraft()) {
                    if (this.ticksLeft == 10) {
                        this.entity.playSound(SoundEvents.ANVIL_USE, 1, 1);
                    } else if (this.ticksLeft == 25) {
                        this.entity.setState(DwarfBlacksmith.State.WORKING);
                    } else if (this.ticksLeft <= 65) {
                        this.entity.getNavigation().moveTo(target.x, target.y, target.z, 0.5);
                        this.entity.lookAt(EntityAnchorArgument.Anchor.EYES, target);
                    }
                } else {
                    this.reset();
                }
            }
        }
    }

    @Override
    public void start() {
        this.ticksLeft = 75;
    }

    protected void reset() {
        this.entity.setState(DwarfBlacksmith.State.IDLE);
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
            BlockEntity tile = this.entity.level.getBlockEntity(this.entity.getSummonPos());
            this.tile = tile instanceof DwarvenAnvil ? (DwarvenAnvil) tile : null;
        }
    }
}

