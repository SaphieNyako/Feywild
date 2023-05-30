package com.feywild.feywild.entity.goals;

import com.feywild.feywild.entity.AutumnPixie;
import com.feywild.feywild.entity.SpringPixie;
import com.feywild.feywild.entity.SummerPixie;
import com.feywild.feywild.entity.WinterPixie;
import com.feywild.feywild.entity.base.Fey;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

public class AddShieldGoal extends Goal {

    private final Fey entity;
    private final MobEffect effect;
    private final int duration;
    private Player target;
    private int ticksLeft = 0;

    public AddShieldGoal(Fey entity, MobEffect effect, int duration) {

        this.entity = entity;
        this.effect = effect;
        this.duration = duration;
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
            } else if (this.ticksLeft == 65) {
                this.spellCasting();
            } else if (this.ticksLeft <= 55) {
                this.entity.lookAt(EntityAnchorArgument.Anchor.EYES, this.target.position());
            }
        }
    }

    @Override
    public void start() {
        this.ticksLeft = 75;
        this.entity.setState(Fey.State.IDLE);
        this.target = null;
        AABB box = new AABB(this.entity.blockPosition()).inflate(4);
        for (Player match : this.entity.level.getEntities(EntityType.PLAYER, box, e -> !e.isSpectator())) {
            this.target = match;
            break;
        }
    }

    private void spellCasting() {
        this.entity.getNavigation().moveTo(this.target.getX(), this.target.getY(), this.target.getZ(), 0.5);
        this.entity.setState(Fey.State.CASTING);
        this.entity.playSound(ModSoundEvents.pixieSpellcasting, 0.7f, 1);
    }

    private void addShieldEffect() {
        this.target.addEffect(new MobEffectInstance(effect, 20 * duration, 2));
        if (entity instanceof AutumnPixie || entity instanceof WinterPixie) {
            this.target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * duration, 0));
            this.entity.setBored(this.entity.getBored() - 1);
        }
        if (entity instanceof SummerPixie) {
            this.target.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20 * duration, 0));
            this.entity.setBored(this.entity.getBored() - 1);
        }
        if (entity instanceof SpringPixie) {
            this.target.addEffect(new MobEffectInstance(MobEffects.LUCK, 20 * 2, 0));
        }
    }

    private void reset() {
        this.entity.setState(Fey.State.IDLE);
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
        if (owning instanceof ServerPlayer serverPlayer) {
            return this.entity.level.random.nextFloat() < 0.01f && QuestData.get(serverPlayer).getAlignment() == entity.alignment;
        } else {
            return false;
        }
    }
}
