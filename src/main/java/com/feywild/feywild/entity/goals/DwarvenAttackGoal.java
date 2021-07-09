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

    private void summonShockWave(int i) {
        BlockPos pos = entity.blockPosition().below();

        FallingBlockEntity fallingBlockEntity;

        if (i == 3) {
            entity.playSound(ModSoundEvents.DWARF_RUBBLE.get(), 1, 1);
            fallingBlockEntity = new FallingBlockEntity(worldLevel, pos.north().west().getX(), pos.north().west().getY(), pos.north().west().getZ(), worldLevel.getBlockState(pos.north().west()));
            fallingBlockEntity.setDeltaMovement(0, 0.3d, 0);
            fallingBlockEntity.setHurtsEntities(true);
            worldLevel.addFreshEntity(fallingBlockEntity);

            fallingBlockEntity = new FallingBlockEntity(worldLevel, pos.south().west().getX(), pos.south().west().getY(), pos.south().west().getZ(), worldLevel.getBlockState(pos.south().west()));
            fallingBlockEntity.setDeltaMovement(0, 0.3d, 0);
            fallingBlockEntity.setHurtsEntities(true);
            worldLevel.addFreshEntity(fallingBlockEntity);
            fallingBlockEntity = new FallingBlockEntity(worldLevel, pos.south().east().getX(), pos.south().east().getY(), pos.south().east().getZ(), worldLevel.getBlockState(pos.south().east()));
            fallingBlockEntity.setDeltaMovement(0, 0.3d, 0);
            fallingBlockEntity.setHurtsEntities(true);
            worldLevel.addFreshEntity(fallingBlockEntity);

            fallingBlockEntity = new FallingBlockEntity(worldLevel, pos.north().east().getX(), pos.north().east().getY(), pos.north().east().getZ(), worldLevel.getBlockState(pos.east().north()));
            fallingBlockEntity.setDeltaMovement(0, 0.3d, 0);
            fallingBlockEntity.setHurtsEntities(true);
            worldLevel.addFreshEntity(fallingBlockEntity);
        } else {
            entity.playSound(ModSoundEvents.DWARF_RUBBLE.get(), 1, 1);
            fallingBlockEntity = new FallingBlockEntity(worldLevel, pos.north(i).getX(), pos.north(i).getY(), pos.north(i).getZ(), worldLevel.getBlockState(pos.north(i)));
            fallingBlockEntity.setDeltaMovement(0, 0.3d, 0);
            fallingBlockEntity.setHurtsEntities(true);
            worldLevel.addFreshEntity(fallingBlockEntity);

            fallingBlockEntity = new FallingBlockEntity(worldLevel, pos.west(i).getX(), pos.west(i).getY(), pos.west(i).getZ(), worldLevel.getBlockState(pos.west(i)));
            fallingBlockEntity.setDeltaMovement(0, 0.3d, 0);
            fallingBlockEntity.setHurtsEntities(true);
            worldLevel.addFreshEntity(fallingBlockEntity);

            fallingBlockEntity = new FallingBlockEntity(worldLevel, pos.south(i).getX(), pos.south(i).getY(), pos.south(i).getZ(), worldLevel.getBlockState(pos.south(i)));
            fallingBlockEntity.setDeltaMovement(0, 0.3d, 0);
            fallingBlockEntity.setHurtsEntities(true);
            worldLevel.addFreshEntity(fallingBlockEntity);

            fallingBlockEntity = new FallingBlockEntity(worldLevel, pos.east(i).getX(), pos.east(i).getY(), pos.east(i).getZ(), worldLevel.getBlockState(pos.east(i)));
            fallingBlockEntity.setDeltaMovement(0, 0.3d, 0);
            fallingBlockEntity.setHurtsEntities(true);
            worldLevel.addFreshEntity(fallingBlockEntity);
        }

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