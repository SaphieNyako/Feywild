package com.feywild.feywild.entity.goals;

import com.feywild.feywild.effects.ModEffects;
import com.feywild.feywild.entity.AutumnPixie;
import com.feywild.feywild.entity.base.Fey;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

public class AddShieldGoal extends Goal {

    private final Fey entity;
    private Player target;
    private int ticksLeft = 0;

    public AddShieldGoal(AutumnPixie entity) {
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
        this.target.addEffect(new MobEffectInstance(ModEffects.windWalk, 20 * 60, 2));
        this.target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 60, 0));
        // FeywildMod.getNetwork().sendParticles(this.entity.level, ParticleMessage.Type.WIND_WALK, this.entity.getX(), this.entity.getY(), this.entity.getZ());
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
