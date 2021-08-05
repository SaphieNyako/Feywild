package com.feywild.feywild.entity.goals;

import com.feywild.feywild.entity.WinterPixieEntity;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Supplier;

public class SummonSnowManGoal extends Goal {

    private static final EntityPredicate TARGETING = (new EntityPredicate()).range(8.0D).allowInvulnerable().allowSameTeam().allowUnseeable();
    protected final World worldLevel;
    public BlockPos summoningPosition;
    protected WinterPixieEntity entity;

    protected int count = 0;

    private Vector3d targetPos;

    public SummonSnowManGoal(WinterPixieEntity entity, Supplier<BlockPos> pos) {
        this.summoningPosition = pos.get();
        this.entity = entity;
        this.worldLevel = entity.level;
    }

    private boolean lookForSnowman(WinterPixieEntity entity) {

        List<LivingEntity> snowmanNearEntity = entity.level.getNearbyEntities(LivingEntity.class, TARGETING, entity, entity.getBoundingBox().inflate(8.0D));
        double d0 = Double.MAX_VALUE;
        boolean snowmanPresent = false;

        for (LivingEntity targetSnowman : snowmanNearEntity) {
            if (entity.distanceToSqr(targetSnowman) < d0 && targetSnowman instanceof SnowGolemEntity) {
                snowmanPresent = true;
                d0 = entity.distanceToSqr(targetSnowman);

            }
        }

        return snowmanPresent;
    }

    @Override
    public void tick() {

        count--;

        if (count <= 0) {
            summonSnowMan();
            reset();

        } else if (count == 110) {
            spellCasting();
        } else if (count <= 100) {
            entity.lookAt(EntityAnchorArgument.Type.EYES, this.targetPos);
        }

    }

    @Override
    public void start() {
        count = 120;
        entity.setCasting(false);
    }

    private void spellCasting() {

        this.targetPos = new Vector3d(entity.getX() + entity.getRandom().nextInt(4 * 2) - 4, entity.getY() + 2, entity.getZ() + entity.getRandom().nextInt(4 * 2) - 4);

        entity.setCasting(true);
        entity.playSound(ModSoundEvents.pixieSpellcasting, 1, 1);
    }

    private void reset() {
        entity.setCasting(false);
        targetPos = null;

    }

    private void summonSnowMan() {

        SnowGolemEntity snowman = new SnowGolemEntity(EntityType.SNOW_GOLEM, worldLevel);
        snowman.setPos(entity.getX(), entity.getY() + 1, entity.getZ());
        snowman.setDeltaMovement((targetPos.x - entity.getX()) / 8, (targetPos.y - entity.getY()) / 8, (targetPos.z - entity.getZ()) / 8);
        worldLevel.addFreshEntity(snowman);
    }

    @Override
    public boolean canContinueToUse() {
        return !lookForSnowman(entity);
    }

    @Override
    public boolean canUse() {
        return entity.isTamed() && entity.level.random.nextFloat() < 0.002f && !lookForSnowman(entity);
    }
}
