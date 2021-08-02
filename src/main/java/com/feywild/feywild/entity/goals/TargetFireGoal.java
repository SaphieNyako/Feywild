package com.feywild.feywild.entity.goals;

import com.feywild.feywild.entity.SummerPixieEntity;
import com.feywild.feywild.network.FeywildPacketHandler;
import com.feywild.feywild.network.ParticleMessage;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;

public class TargetFireGoal extends Goal {

    private static final EntityPredicate TARGETING = (new EntityPredicate()).range(8.0D).allowInvulnerable().allowSameTeam().allowUnseeable();
    protected final World worldLevel;
    protected SummerPixieEntity entity;
    protected MonsterEntity targetMonster;
    protected boolean enchantMonstersNearby = false;
    protected int count = 0;
    private Vector3d targetPos;

    public TargetFireGoal(SummerPixieEntity entity) {
        this.entity = entity;
        this.worldLevel = entity.level;

    }

    //LOOK FOR MONSTERS IN AREA
    private MonsterEntity selectTargetMonster(SummerPixieEntity entity) {
        List<MonsterEntity> monsterNearEntity = worldLevel.getNearbyEntities(MonsterEntity.class, TARGETING, entity, entity.getBoundingBox().inflate(8.0D));
        double d0 = Double.MAX_VALUE;
        MonsterEntity monster = null;

        for (MonsterEntity targetMonster : monsterNearEntity) {
            if (entity.distanceToSqr(targetMonster) < d0) {
                monster = targetMonster;
                d0 = entity.distanceToSqr(targetMonster);
            }
        }

        return monster;
    }

    @Override
    public void tick() {
        if (selectTargetMonster(entity) != null) {
            targetMonster = selectTargetMonster(entity);
            enchantMonstersNearby = true;
        } else {
            reset();
        }

        if (enchantMonstersNearby && !targetMonster.isOnFire()) {
            count++;

            if (count >= 120) {
                setTargetOnFire(targetMonster);

                reset();

            } else if (count == 1) {
                spellCasting();
            }
        }
    }

    private void spellCasting() {
        this.targetPos = new Vector3d(targetMonster.getX(), targetMonster.getY(), targetMonster.getZ());
        entity.lookAt(EntityAnchorArgument.Type.EYES, this.targetPos);
        entity.setCasting(true);
        entity.playSound(ModSoundEvents.pixieSpellcasting, 1, 1);
    }

    protected void setTargetOnFire(MonsterEntity monsterEntity) {
        monsterEntity.setSecondsOnFire(120);

        FeywildPacketHandler.sendToPlayersInRange(worldLevel, entity.blockPosition()
                , new ParticleMessage(entity.blockPosition().getX(), entity.blockPosition().getY() + 1, entity.blockPosition().getZ(), targetMonster.getX(), targetMonster.getY() + 1, targetMonster.getZ(), -20, 5, 0.12f)
                , 64);

    }

    protected void reset() {
        enchantMonstersNearby = false;
        entity.setCasting(false);
        targetMonster = null;
        count = 0;
    }

    @Override
    public boolean canContinueToUse() {
        return enchantMonstersNearby;
    }

    @Override
    public boolean canUse() {
        return entity.level.random.nextFloat() < 0.005f;
    }
}
