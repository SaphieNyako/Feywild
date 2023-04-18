package com.feywild.feywild.entity.goals;

import com.feywild.feywild.entity.WinterPixie;
import com.feywild.feywild.entity.base.Fey;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class SummonSnowManGoal extends Goal {

    private static final TargetingConditions TARGETING = TargetingConditions.forNonCombat().range(8).ignoreLineOfSight().selector(living -> !(living instanceof Fey));

    private final Fey entity;
    private int ticksLeft = 0;
    private Vec3 targetPos;

    public SummonSnowManGoal(WinterPixie entity) {
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
            } else if (this.ticksLeft == 65) {
                this.spellCasting();
            } else if (this.ticksLeft <= 55) {
                this.entity.lookAt(EntityAnchorArgument.Anchor.EYES, this.targetPos);
            }
        }
    }

    @Override
    public void start() {
        this.ticksLeft = 75;
        this.entity.setCasting(false);
    }

    private void spellCasting() {
        this.targetPos = new Vec3(this.entity.getX() + this.entity.getRandom().nextInt(8) - 4, this.entity.getY() + 2, this.entity.getZ() + this.entity.getRandom().nextInt(8) - 4);
        this.entity.setCasting(true);
        this.entity.playSound(ModSoundEvents.pixieSpellcasting, 0.7f, 1);
    }

    private void reset() {
        this.entity.setCasting(false);
        this.targetPos = null;
        this.ticksLeft = -1;
    }

    private void summonSnowMan() {
        SnowGolem snowman = new SnowGolem(EntityType.SNOW_GOLEM, this.entity.level);
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
        Player owning = this.entity.getOwningPlayer();
        if (owning instanceof ServerPlayer serverPlayer) {
            return this.entity.level.random.nextFloat() < 0.005f && QuestData.get(serverPlayer).getAlignment() == entity.alignment;
        } else {
            return false;
        }
    }

    private boolean noSnowManNearby() {
        return this.entity.level.getNearbyEntities(SnowGolem.class, TARGETING, this.entity, this.entity.getBoundingBox().inflate(8)).isEmpty();
    }
}
