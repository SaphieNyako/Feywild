package com.feywild.feywild.entity.goals.pixie;

import com.feywild.feywild.effects.ModEffects;
import com.feywild.feywild.entity.base.Pixie;
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

    private final Pixie entity;
    private final MobEffect effect;
    private Player target;
    private int ticksLeft = 0;

    public AddShieldGoal(Pixie entity, MobEffect effect) {

        this.entity = entity;
        this.effect = effect;
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
            } else if (this.ticksLeft == 50) {
                this.entity.playSound(ModSoundEvents.pixieSpellcasting, 0.7f, 1);
            } else if (this.ticksLeft == 45) {
                this.spellCasting();
            } else if (this.ticksLeft <= 40) {
                this.entity.lookAt(EntityAnchorArgument.Anchor.EYES, this.target.position());
            }
        }
    }

    @Override
    public void start() {
        this.ticksLeft = 75;
        this.entity.setState(Pixie.State.IDLE);
        this.target = null;
        AABB box = new AABB(this.entity.blockPosition()).inflate(4);
        for (Player match : this.entity.level.getEntities(EntityType.PLAYER, box, e -> !e.isSpectator())) {
            this.target = match;
            break;
        }
    }

    private void spellCasting() {
        this.entity.getNavigation().moveTo(this.target.getX(), this.target.getY(), this.target.getZ(), 0.5);
        this.entity.setState(Pixie.State.CASTING);
        // this.entity.playSound(ModSoundEvents.pixieSpellcasting, 0.7f, 1);
    }

    private void addShieldEffect() {
        this.target.addEffect(new MobEffectInstance(effect, 20 * entity.getBoredCount(), 2));
        this.entity.setBored(this.entity.getBored() - 1);
        if (effect == ModEffects.fireWalk) {
            this.target.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20 * entity.getBoredCount(), 0));
        }
        if (effect == ModEffects.flowerWalk) {
            this.target.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 20 * 5, 0));
        }
    }

    private void reset() {
        this.entity.setState(Pixie.State.IDLE);
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
