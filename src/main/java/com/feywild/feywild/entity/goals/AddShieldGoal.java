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
        if (ticksLeft > 0) {
            if (target == null) {
                reset();
                return;
            }
            ticksLeft--;
            if (ticksLeft <= 0) {
                addShieldEffect();
                reset();
            } else if (ticksLeft == 110) {
                spellCasting();
            } else if (ticksLeft <= 100) {
                entity.lookAt(EntityAnchorArgument.Type.EYES, target.position());
            }
        }
    }

    @Override
    public void start() {
        ticksLeft = 120;
        entity.setCasting(false);
        target = null;
        AxisAlignedBB box = new AxisAlignedBB(entity.blockPosition()).inflate(4);
        for (PlayerEntity match : entity.level.getEntities(EntityType.PLAYER, box, e -> !e.isSpectator())) {
            target = match;
            break;
        }
    }

    private void spellCasting() {
        entity.getNavigation().moveTo(target.getX(), target.getY(), target.getZ(), 0.5);
        entity.setCasting(true);
        entity.playSound(ModSoundEvents.pixieSpellcasting, 1, 1);
    }

    private void addShieldEffect() {
        target.addEffect(new EffectInstance(ModEffects.windWalk, 20 * 60, 2));
        target.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 20 * 60, 2));
        FeywildMod.getNetwork().sendParticles(entity.level, ParticleSerializer.Type.WIND_WALK, entity.getX(), entity.getY(), entity.getZ());
    }

    private void reset() {
        entity.setCasting(false);
        target = null;
        ticksLeft = -1;
    }

    @Override
    public boolean canContinueToUse() {
        return ticksLeft > 0;
    }

    @Override
    public boolean canUse() {
        return entity.isTamed() && entity.level.random.nextFloat() < 0.002f;
    }
}
