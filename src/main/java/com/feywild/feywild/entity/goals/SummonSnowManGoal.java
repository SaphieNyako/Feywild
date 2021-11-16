package com.feywild.feywild.entity.goals;

import com.feywild.feywild.entity.WinterPixieEntity;
import com.feywild.feywild.entity.base.FeyEntity;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.vector.Vector3d;

import java.util.Objects;

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
        if (this.ticksLeft > 0) {
            this.ticksLeft--;
            if (!this.noSnowManNearby()) {
                this.reset();
                return;
            }
            if (this.ticksLeft <= 0) {
                this.summonSnowMan();
                this.reset();
            } else if (this.ticksLeft == 110) {
                this.spellCasting();
            } else if (this.ticksLeft <= 100) {
                this.entity.lookAt(EntityAnchorArgument.Type.EYES, this.targetPos);
            }
        }
    }

    @Override
    public void start() {
        this.ticksLeft = 120;
        this.entity.setCasting(false);
    }

    private void spellCasting() {
        this.targetPos = new Vector3d(this.entity.getX() + this.entity.getRandom().nextInt(8) - 4, this.entity.getY() + 2, this.entity.getZ() + this.entity.getRandom().nextInt(8) - 4);
        this.entity.setCasting(true);
        this.entity.playSound(ModSoundEvents.pixieSpellcasting, 1, 1);
    }

    private void reset() {
        this.entity.setCasting(false);
        this.targetPos = null;
        this.ticksLeft = -1;
    }

    private void summonSnowMan() {
        SnowGolemEntity snowman = new SnowGolemEntity(EntityType.SNOW_GOLEM, this.entity.level);
        snowman.setPos(this.entity.getX(), this.entity.getY() + 1, this.entity.getZ());
        snowman.setDeltaMovement((this.targetPos.x - this.entity.getX()) / 8, (this.targetPos.y - this.entity.getY()) / 8, (this.targetPos.z - this.entity.getZ()) / 8);
        this.entity.level.addFreshEntity(snowman);
    }

    @Override
    public boolean canContinueToUse() {
        return this.ticksLeft > 0;
    }

    @Override
    public boolean canUse() {
        return this.entity.isTamed() && this.entity.level.random.nextFloat() < 0.002f && this.noSnowManNearby() && QuestData.get((ServerPlayerEntity) Objects.requireNonNull(entity.getOwner())).getAlignment() == entity.alignment;
    }
    
    private boolean noSnowManNearby() {
        return this.entity.level.getNearbyEntities(SnowGolemEntity.class, TARGETING, this.entity, this.entity.getBoundingBox().inflate(8)).isEmpty();
    }
}
