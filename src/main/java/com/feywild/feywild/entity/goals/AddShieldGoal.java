package com.feywild.feywild.entity.goals;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.effects.ModEffects;
import com.feywild.feywild.entity.AutumnPixieEntity;
import com.feywild.feywild.entity.base.FeyEntity;
import com.feywild.feywild.network.ParticleSerializer;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.AxisAlignedBB;

public class AddShieldGoal extends Goal {

    private final FeyEntity entity;
    private PlayerEntity target;
    private int ticksLeft = 0;

    public AddShieldGoal(AutumnPixieEntity entity) {
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
                this.addShieldEffect();
                this.reset();
            } else if (this.ticksLeft == 110) {
                this.spellCasting();
            } else if (this.ticksLeft <= 100) {
                this.entity.lookAt(EntityAnchorArgument.Type.EYES, this.target.position());
            }
        }
    }

    @Override
    public void start() {
        this.ticksLeft = 120;
        this.entity.setCasting(false);
        this.target = null;
        AxisAlignedBB box = new AxisAlignedBB(this.entity.blockPosition()).inflate(4);
        for (PlayerEntity match : this.entity.level.getEntities(EntityType.PLAYER, box, e -> !e.isSpectator())) {
            this.target = match;
            break;
        }
    }

    private void spellCasting() {
        this.entity.getNavigation().moveTo(this.target.getX(), this.target.getY(), this.target.getZ(), 0.5);
        this.entity.setCasting(true);
        this.entity.playSound(ModSoundEvents.pixieSpellcasting, 1, 1);
    }

    private void addShieldEffect() {
        this.target.addEffect(new EffectInstance(ModEffects.windWalk, 20 * 60, 2));
        this.target.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 20 * 60, 0));
        FeywildMod.getNetwork().sendParticles(this.entity.level, ParticleSerializer.Type.WIND_WALK, this.entity.getX(), this.entity.getY(), this.entity.getZ());
    }

    private void reset() {
        this.entity.setCasting(false);
        this.target = null;
        this.ticksLeft = -1;
    }

    @Override
    public boolean canContinueToUse() {
        return this.ticksLeft > 0;
    }

    @Override
    public boolean canUse() {
        return this.entity.isTamed() && this.entity.level.random.nextFloat() < 0.002f;
    }
}
