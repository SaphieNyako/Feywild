package com.saphienyako.feywild.entity.goals;

import com.saphienyako.feywild.effect.ModEffects;
import com.saphienyako.feywild.entity.base.PixieBase;
import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class BlessingEffectGoal extends Goal {

    private final PixieBase entity;
    private final MobEffect effect;
    private Player target;
    private int ticksLeft = 0;
    private Level level;

    public BlessingEffectGoal(PixieBase entity, MobEffect effect, Level level) {

        this.entity = entity;
        this.effect = effect;
        this.level = level;
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
                this.addEffect();
                this.reset();
            } else if (this.ticksLeft == 55) {
                this.spellCasting();
            } else if (this.ticksLeft <= 45) {
                this.entity.lookAt(EntityAnchorArgument.Anchor.EYES, this.target.position());
            }
        }
    }

    @Override
    public void start() {
        this.ticksLeft = 65;
        this.entity.setState(PixieBase.State.SPELL_CASTING);
        this.target = null;
        AABB box = new AABB(this.entity.blockPosition()).inflate(4);
        for (Player match : this.level.getEntities(EntityType.PLAYER, box, e -> !e.isSpectator())) {
            this.target = match;
            break;
        }
    }

    private void spellCasting() {
       this.entity.playSound(ModSounds.PIXIE_SPELL_CASTING.get(), 0.7f, 1);
    }

    private void addEffect() {
        this.target.addEffect(new MobEffectInstance(effect, 20 * 5, 2));
        if (effect == ModEffects.WINTER_BLESSING.get()) {
            this.target.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20 * 5, 0));
        }

        if (effect == ModEffects.SPRING_BLESSING.get()) {
            this.target.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 20 * 5, 0));
        }
    }

    private void reset() {
        this.entity.setState(PixieBase.State.IDLE);
        this.target = null;
        this.ticksLeft = -1;
    }

    @Override
    public boolean canContinueToUse() {
        return this.ticksLeft > 0;
    }

    @Override
    public boolean canUse() {
        Player owning = this.entity.getOwningPlayer();
        if (owning instanceof ServerPlayer) {
            return this.level.random.nextFloat() < 0.01f;
        } else {
            return false;
        }
    }

}
