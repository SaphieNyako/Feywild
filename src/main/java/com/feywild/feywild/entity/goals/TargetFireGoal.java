package com.feywild.feywild.entity.goals;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.base.Fey;
import com.feywild.feywild.network.ParticleSerializer;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;

public class TargetFireGoal extends Goal {

    private static final TargetingConditions TARGETING = TargetingConditions.forCombat().range(8.0D).ignoreLineOfSight();

    private final Fey entity;
    private Monster targetMonster;
    private int ticksLeft = 0;

    public TargetFireGoal(Fey entity) {
        this.entity = entity;
    }

    @Override
    public void tick() {
        if (this.ticksLeft > 0) {
            this.ticksLeft--;
            if (this.targetMonster == null) {
                this.targetMonster = this.findTarget();
                if (this.targetMonster == null) {
                    this.reset();
                    return;
                }
            }
            if (this.ticksLeft <= 0) {
                this.targetMonster.setSecondsOnFire(120);
                FeywildMod.getNetwork().sendParticles(this.entity.level, ParticleSerializer.Type.MONSTER_FIRE, this.entity.getX(), this.entity.getY(), this.entity.getZ(), this.targetMonster.getX(), this.targetMonster.getY(), this.targetMonster.getZ());
                this.reset();
            } else if (this.ticksLeft == 110) {
                this.spellCasting();
            }
        }
    }

    private void spellCasting() {
        this.entity.lookAt(EntityAnchorArgument.Anchor.EYES, this.targetMonster.position());
        this.entity.setCasting(true);
        this.entity.playSound(ModSoundEvents.pixieSpellcasting, 1, 1);
    }

    @Override
    public void start() {
        this.ticksLeft = 120;
        this.targetMonster = null;
    }

    protected void reset() {
        this.entity.setCasting(false);
        this.targetMonster = null;
        this.ticksLeft = -1;
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

    private Monster findTarget() {
        double distance = Double.MAX_VALUE;
        Monster current = null;
        for (Monster monster : this.entity.level.getNearbyEntities(Monster.class, TARGETING, this.entity, this.entity.getBoundingBox().inflate(8))) {
            if (this.entity.distanceToSqr(monster) < distance && !monster.isOnFire()) {
                current = monster;
                distance = this.entity.distanceToSqr(monster);
            }
        }
        return current;
    }
}
