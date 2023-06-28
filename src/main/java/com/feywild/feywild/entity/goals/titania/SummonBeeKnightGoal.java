package com.feywild.feywild.entity.goals.titania;

import com.feywild.feywild.entity.BeeKnight;
import com.feywild.feywild.entity.ModEntities;
import com.feywild.feywild.entity.Titania;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.Random;

public class SummonBeeKnightGoal extends Goal {

    private static final TargetingConditions TARGETING = TargetingConditions.forCombat().range(8.0D).ignoreLineOfSight();
    protected final Level level;
    protected final Titania entity;
    private int ticksLeft = 0;
    private Player target;

    public SummonBeeKnightGoal(Titania titania) {
        this.entity = titania;
        this.level = titania.level();
    }

    @Override
    public void tick() {
        if (this.ticksLeft > 0) {
            if (this.target == null) {
                this.reset();
                return;
            }
            this.ticksLeft--;
            if (this.ticksLeft <= 0) {
                this.summonBeeKnight(target);
                reset();
            } else if (this.ticksLeft <= 30) {
                this.entity.lookAt(EntityAnchorArgument.Anchor.EYES, this.target.position());
                this.entity.getNavigation().moveTo(this.target, 0.5);
            } else if (this.ticksLeft == 35) {
                this.spellCasting();
            }
        }
    }

    private Player findTarget() {
        double distance = Double.MAX_VALUE;
        Player current = null;
        for (Player player : this.entity.level().getNearbyEntities(Player.class, TARGETING, this.entity, this.entity.getBoundingBox().inflate(32))) {
            if (this.entity.distanceToSqr(player) < distance) {
                current = player;
                distance = this.entity.distanceToSqr(player);
            }
        }
        return current;
    }

    private void spellCasting() {
        this.entity.lookAt(EntityAnchorArgument.Anchor.EYES, this.target.position());
        this.entity.setState(Titania.State.ENCHANTING);
        this.entity.playSound(ModSoundEvents.spellcastingShort.getSoundEvent(), 0.7f, 1.0f);
        this.entity.playSound(ModSoundEvents.titaniaSummonBee.getSoundEvent(), 1.0f, 1.1f);
    }

    private void summonBeeKnight(Player target) {
        BeeKnight beeKnight = new BeeKnight(ModEntities.beeKnight, this.level);
        Random random = new Random();
        beeKnight.setPos(this.entity.getX() + (random.nextInt(3)), this.entity.getY() + (random.nextInt(3)), this.entity.getZ() + (random.nextInt(3)));
        this.level.addFreshEntity(beeKnight);
        this.entity.playSound(SoundEvents.BEEHIVE_EXIT, 1, 1);
        BeeKnight.anger(entity.level(), target, target.blockPosition());
    }

    @Override
    public void start() {
        this.ticksLeft = 36;
        this.target = null;
        AABB box = new AABB(this.entity.blockPosition()).inflate(32);
        for (Player match : this.entity.level().getEntities(EntityType.PLAYER, box, e -> !e.isSpectator())) {
            this.target = match;
            break;
        }
    }

    private void reset() {
        this.entity.setState(Titania.State.IDLE);
        this.target = null;
        this.ticksLeft = -1;
    }

    @Override
    public boolean canUse() {
        return level.random.nextFloat() < 0.01f && (this.entity.getState() == Titania.State.IDLE || !(this.entity.getState() == Titania.State.CASTING) || !(this.entity.getState() == Titania.State.ENCHANTING));
    }

    @Override
    public boolean canContinueToUse() {
        return this.ticksLeft > 0;
    }
}
