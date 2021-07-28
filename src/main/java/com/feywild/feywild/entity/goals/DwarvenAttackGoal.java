package com.feywild.feywild.entity.goals;

import com.feywild.feywild.entity.DwarfBlacksmithEntity;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.LinkedList;
import java.util.List;

public class DwarvenAttackGoal extends Goal {

    // private static final EntityPredicate TARGETING = (new EntityPredicate()).range(8.0D).allowInvulnerable().allowSameTeam().allowUnseeable();
    protected final World worldLevel;
    protected DwarfBlacksmithEntity entity;
    protected LivingEntity targetMonster;
    protected boolean enchantMonstersNearby = false;
    protected int count = 0;
    protected boolean sendShock = false;
    private Vector3d targetPos;

    // Ancient's note : do not touch
    private int strength = 2;

    public DwarvenAttackGoal(DwarfBlacksmithEntity entity) {
        this.entity = entity;
        this.worldLevel = entity.level;
    }

    @Override
    public void tick() {
        if (entity.getLastHurtByMob() != null) {
            targetMonster = entity.getLastHurtByMob();
            enchantMonstersNearby = true;
        }

        if (enchantMonstersNearby && targetMonster instanceof MonsterEntity) {
            count--;

            if (count == 0) {
                reset();
            } else if (count == 10) {
                sendShock = attackTarget();
                entity.playSound(ModSoundEvents.DWARF_ATTACK.get(), 1, 1.2f);

            } else if (count == 30) {
                entity.setState(1);
                entity.getNavigation().moveTo(targetMonster.getX(), targetMonster.getY(), targetMonster.getZ(), 0.5);

            } else if (count <= 30) {
                entity.lookAt(EntityAnchorArgument.Type.EYES, targetMonster.position());
                if (sendShock) {

                    switch (count) {
                        case 8:
                            summonShockWave(1);
                            break;
                        case 6:
                            summonShockWave(3);
                            break;
                        case 4:
                            summonShockWave(2);
                            break;
                    }
                }
            }
        }
    }

    @Override
    public void start() {
        count = 70;
    }

    protected boolean attackTarget() {
        if (entity.getRandom().nextDouble() > 0.6) {
            targetMonster.hurt(DamageSource.GENERIC, 15.0f);
            return false;
        } else {
            targetMonster.hurt(DamageSource.GENERIC, 20.0f);
            return true;
        }
    }

    private void summonShockWave(int stage) {
        BlockPos pos = entity.blockPosition().below();

        // Explosion size
        int size = 2;
        List<FallingBlockEntity> entityList = new LinkedList<>();

        for (int i = 0; i <= size; i++) {
            for (int j = 0; j <= size; j++) {
                int val = Math.abs(i) + Math.abs(j);
                if (!(val > size) && !worldLevel.getBlockState(new BlockPos(pos.getX() + i, pos.below().getY(), pos.getZ() + j)).isAir() && worldLevel.getBlockState(new BlockPos(pos.getX() + i, pos.above().getY(), pos.getZ() + j)).isAir() && !(i == 0 && j == 0)) {
                    if (stage == 1 || stage == 2) {
                        entityList.add(new FallingBlockEntity(worldLevel, pos.getX() + i, pos.getY(), pos.getZ() + j, worldLevel.getBlockState(new BlockPos(pos.getX() + i, pos.getY(), pos.getZ() + j))));
                        entityList.add(new FallingBlockEntity(worldLevel, pos.getX() - i, pos.getY(), pos.getZ() - j, worldLevel.getBlockState(new BlockPos(pos.getX() - i, pos.getY(), pos.getZ() - j))));
                    }

                    if (stage == 3) {
                        entityList.add(new FallingBlockEntity(worldLevel, pos.getX() + i, pos.getY(), pos.getZ() - j, worldLevel.getBlockState(new BlockPos(pos.getX() + i, pos.getY(), pos.getZ() - j))));
                        entityList.add(new FallingBlockEntity(worldLevel, pos.getX() - i, pos.getY(), pos.getZ() + j, worldLevel.getBlockState(new BlockPos(pos.getX() - i, pos.getY(), pos.getZ() + j))));
                    }

                }
            }
        }


        entityList.forEach(fallingBlockEntity -> {
            entity.playSound(ModSoundEvents.DWARF_RUBBLE.get(), 1, 1);
            fallingBlockEntity.setDeltaMovement(0, 0.3d, 0);
            fallingBlockEntity.setHurtsEntities(true);
            worldLevel.addFreshEntity(fallingBlockEntity);
        });
    }

    protected void reset() {
        enchantMonstersNearby = false;
        entity.setState(0);
        targetMonster = null;
        sendShock = false;
    }

    @Override
    public boolean canContinueToUse() {
        return enchantMonstersNearby;
    }

    @Override
    public boolean canUse() {
        return entity.level.random.nextFloat() < 0.3f;
    }
}