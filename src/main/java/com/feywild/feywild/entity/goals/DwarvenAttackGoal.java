package com.feywild.feywild.entity.goals;

import com.feywild.feywild.entity.DwarfBlacksmithEntity;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class DwarvenAttackGoal extends Goal {

    protected DwarfBlacksmithEntity entity;
    protected LivingEntity target;
    protected boolean sendShock = false;
    protected int ticksLeft = 0;

    public DwarvenAttackGoal(DwarfBlacksmithEntity entity) {
        this.entity = entity;
    }

    @Override
    public void tick() {
        if (entity.getLastHurtByMob() instanceof MonsterEntity) {
            target = entity.getLastHurtByMob();
            ticksLeft--;
            if (ticksLeft == 0) {
                reset();
            } else if (ticksLeft == 10) {
                sendShock = attackTarget();
                entity.playSound(ModSoundEvents.dwarfAttack, 1, 1.2f);
                entity.setState(DwarfBlacksmithEntity.State.IDLE);
            } else if (ticksLeft == 30) {
                entity.setState(DwarfBlacksmithEntity.State.ATTACKING);
                entity.getNavigation().moveTo(target.getX(), target.getY(), target.getZ(), 0.5);
            } else if (ticksLeft <= 30) {
                entity.lookAt(EntityAnchorArgument.Type.EYES, target.position());
                if (sendShock) {
                    switch (ticksLeft) {
                        case 4:
                        case 8:
                            summonShockWave(true);
                            break;
                        case 6:
                            summonShockWave(false);
                            break;
                    }
                }
            }
        } else {
            reset();
        }
    }

    @Override
    public void start() {
        ticksLeft = 70;
    }

    protected boolean attackTarget() {
        if (entity.getRandom().nextDouble() > 0.6) {
            target.hurt(DamageSource.mobAttack(this.entity), 15);
            return false;
        } else {
            target.hurt(DamageSource.mobAttack(this.entity), 20);
            return true;
        }
    }

    private void summonShockWave(boolean stage) {
        BlockPos centerPos = entity.blockPosition().below();

        int size = 2;
        List<FallingBlockEntity> entityList = new ArrayList<>();

        for (int xd = 0; xd <= size; xd++) {
            for (int zd = 0; zd <= size; zd++) {
                int dist = Math.abs(xd) + Math.abs(zd);
                //noinspection deprecation
                if (dist <= size && !this.entity.level.getBlockState(centerPos.offset(xd, -1, zd)).isAir() && this.entity.level.getBlockState(new BlockPos(centerPos.offset(xd, 0, zd))).isAir() && (xd != 0 || zd != 0)) {
                    if (stage) {
                        waveBlock(entityList, centerPos.offset(xd, 0, zd));
                        waveBlock(entityList, centerPos.offset(-xd, 0, -zd));
                    } else {
                        waveBlock(entityList, centerPos.offset(xd, 0, -zd));
                        waveBlock(entityList, centerPos.offset(-xd, 0, zd));
                    }
                }
            }
        }

        entityList.forEach(block -> {
            entity.playSound(ModSoundEvents.dwarfAttack, 1, 1);
            block.setDeltaMovement(0, 0.3d, 0);
            block.setHurtsEntities(true);
            this.entity.level.addFreshEntity(block);
        });
    }

    private void waveBlock(List<FallingBlockEntity> entityList, BlockPos pos) {
        BlockState state = this.entity.level.getBlockState(pos);
        if (!state.hasTileEntity() && state.getDestroySpeed(this.entity.level, pos) >= 0) {
            // No blocks with tile entities and no unbreakable blocks.
            entityList.add(new FallingBlockEntity(this.entity.level, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, state));
        }
    }

    protected void reset() {
        entity.setState(DwarfBlacksmithEntity.State.IDLE);
        target = null;
        sendShock = false;
        ticksLeft = -1;
    }

    @Override
    public boolean canContinueToUse() {
        return ticksLeft > 0 && entity.getLastHurtByMob() instanceof MobEntity && !entity.getLastHurtByMob().isInvulnerable();
    }

    @Override
    public boolean canUse() {
        return entity.getLastHurtByMob() instanceof MobEntity && !entity.getLastHurtByMob().isInvulnerable() && entity.canSee(entity.getLastHurtByMob());
    }
}