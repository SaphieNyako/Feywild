package com.feywild.feywild.entity.goals;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.Titania;
import com.feywild.feywild.network.ParticleMessage;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

public class BossTargetFireGoal extends Goal {

    private static final TargetingConditions TARGETING = TargetingConditions.forCombat().range(8.0D).ignoreLineOfSight();

    private final Titania entity;
    private Player target;
    private int ticksLeft = 0;


    public BossTargetFireGoal(Titania entity) {
        this.entity = entity;
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
                this.target.setSecondsOnFire(60);
                FeywildMod.getNetwork().sendParticles(this.entity.level, ParticleMessage.Type.MONSTER_FIRE, this.entity.getX(), this.entity.getY() + 3, this.entity.getZ(), this.target.getX(), this.target.getY(), this.target.getZ());
                this.reset();
            } else if (this.ticksLeft == 35) {
                this.spellCasting();
            }
        }
    }

    @Override
    public void start() {
        this.ticksLeft = 36;
        this.target = null;
        AABB box = new AABB(this.entity.blockPosition()).inflate(32);
        for (Player match : this.entity.level.getEntities(EntityType.PLAYER, box, e -> !e.isSpectator())) {
            this.target = match;
            break;
        }
    }

    protected void reset() {
        this.entity.setState(Titania.State.IDLE);
        this.target = null;
        this.ticksLeft = -1;
    }

    @Override
    public boolean canContinueToUse() {
        return this.ticksLeft > 0;
    }

    @Override
    public boolean canUse() {
        return this.entity.level.random.nextFloat() < 0.01f && (this.entity.getState() == Titania.State.IDLE || !(this.entity.getState() == Titania.State.CASTING) || !(this.entity.getState() == Titania.State.ENCHANTING));
    }

    private void spellCasting() {
        this.entity.lookAt(EntityAnchorArgument.Anchor.EYES, this.target.position());
        this.entity.setState(Titania.State.CASTING);
        this.entity.playSound(ModSoundEvents.spellcastingShort, 0.7f, 1.0f);
        this.entity.playSound(ModSoundEvents.titaniaFireAttack, 1.0f, 1.1f);
    }
}
