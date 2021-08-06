package com.feywild.feywild.entity.goals;

import com.feywild.feywild.entity.WinterPixieEntity;
import com.feywild.feywild.entity.base.FeyEntity;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.util.math.vector.Vector3d;

public class SummonSnowManGoal extends Goal {

    private static final EntityPredicate TARGETING = (new EntityPredicate()).range(8).allowInvulnerable().allowSameTeam().allowUnseeable().selector(living -> !(living instanceof FeyEntity));

    private final FeyEntity entity;
    private int ticksLeft = 0;
    private Vector3d targetPos;


    public SummonSnowManGoal(WinterPixieEntity entity) {
        this.entity = entity;
    }

    @Override
    public void tick() {
        if (ticksLeft > 0) {
            ticksLeft--;
            if (!noSnowManNearby()) {
                reset();
                return;
            }
            if (ticksLeft <= 0) {
                summonSnowMan();
                reset();
            } else if (ticksLeft == 110) {
                spellCasting();
            } else if (ticksLeft <= 100) {
                entity.lookAt(EntityAnchorArgument.Type.EYES, this.targetPos);
            }
        }
    }

    @Override
    public void start() {
        ticksLeft = 120;
        entity.setCasting(false);
    }

    private void spellCasting() {
        this.targetPos = new Vector3d(entity.getX() + entity.getRandom().nextInt(8) - 4, entity.getY() + 2, entity.getZ() + entity.getRandom().nextInt(8) - 4);
        entity.setCasting(true);
        entity.playSound(ModSoundEvents.pixieSpellcasting, 1, 1);
    }

    private void reset() {
        entity.setCasting(false);
        targetPos = null;
        this.ticksLeft = -1;
    }

    private void summonSnowMan() {
        SnowGolemEntity snowman = new SnowGolemEntity(EntityType.SNOW_GOLEM, entity.level);
        snowman.setPos(entity.getX(), entity.getY() + 1, entity.getZ());
        snowman.setDeltaMovement((targetPos.x - entity.getX()) / 8, (targetPos.y - entity.getY()) / 8, (targetPos.z - entity.getZ()) / 8);
        this.entity.level.addFreshEntity(snowman);
    }

    @Override
    public boolean canContinueToUse() {
        return ticksLeft > 0;
    }

    @Override
    public boolean canUse() {
        return entity.isTamed() && entity.level.random.nextFloat() < 0.002f && noSnowManNearby();
    }
    
    private boolean noSnowManNearby() {
        return entity.level.getNearbyEntities(SnowGolemEntity.class, TARGETING, entity, entity.getBoundingBox().inflate(8)).isEmpty();
    }
}
