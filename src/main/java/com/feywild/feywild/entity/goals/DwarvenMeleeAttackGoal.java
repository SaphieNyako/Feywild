package com.feywild.feywild.entity.goals;

import com.feywild.feywild.entity.DwarfBlacksmith;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class DwarvenMeleeAttackGoal extends MeleeAttackGoal {

    protected final DwarfBlacksmith entity;

    public DwarvenMeleeAttackGoal(DwarfBlacksmith entity, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(entity, speedModifier, followingTargetEvenIfNotSeen);
        this.entity = entity;
    }

    @Override
    protected void checkAndPerformAttack(@Nonnull LivingEntity enemy, double distToEnemySqr) {
        double d0 = this.getAttackReachSqr(enemy);
        if (distToEnemySqr <= d0 + 2 && getTicksUntilNextAttack() <= 0) {
            this.summonShockWave(false);
            this.resetAttackCooldown();
            this.mob.doHurtTarget(enemy);
            this.entity.playSound(ModSoundEvents.dwarfAttack, 0.7f, 1);
        }
    }

    @Override
    public void start() {
        this.entity.setState(DwarfBlacksmith.State.ATTACKING);
        super.start();
    }

    @Override
    public void stop() {
        this.entity.setState(DwarfBlacksmith.State.IDLE);
        super.stop();
    }

    private void summonShockWave(boolean stage) {
        BlockPos centerPos = this.entity.blockPosition().below();

        int size = 2;
        List<FallingBlockEntity> entityList = new ArrayList<>();

        for (int xd = 0; xd <= size; xd++) {
            for (int zd = 0; zd <= size; zd++) {
                int dist = Math.abs(xd) + Math.abs(zd);
                if (dist <= size && !this.entity.level.getBlockState(centerPos.offset(xd, -1, zd)).isAir() && this.entity.level.getBlockState(new BlockPos(centerPos.offset(xd, 0, zd))).isAir() && (xd != 0 || zd != 0)) {
                    if (stage) {
                        this.waveBlock(entityList, centerPos.offset(xd, 0, zd));
                        this.waveBlock(entityList, centerPos.offset(-xd, 0, -zd));
                    } else {
                        this.waveBlock(entityList, centerPos.offset(xd, 0, -zd));
                        this.waveBlock(entityList, centerPos.offset(-xd, 0, zd));
                    }
                }
            }
        }

        entityList.forEach(block -> {
            this.entity.playSound(ModSoundEvents.dwarfAttack, 1, 1);
            block.setDeltaMovement(0, 0.3d, 0);
            block.setHurtsEntities(2, 40);
            this.entity.level.addFreshEntity(block);
        });
    }

    private void waveBlock(List<FallingBlockEntity> entityList, BlockPos pos) {
        BlockState state = this.entity.level.getBlockState(pos);
        if (!state.hasBlockEntity() && state.getDestroySpeed(this.entity.level, pos) >= 0) {
            // No blocks with tile entities and no unbreakable blocks.
            entityList.add(FallingBlockEntity.fall(this.entity.level, pos, state));

        }
    }

}
