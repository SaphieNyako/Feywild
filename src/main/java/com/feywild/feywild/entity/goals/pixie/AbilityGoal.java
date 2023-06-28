package com.feywild.feywild.entity.goals.pixie;

import com.feywild.feywild.entity.ability.Ability;
import com.feywild.feywild.entity.base.Pixie;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class AbilityGoal extends Goal {

    private final Pixie pixie;
    
    @Nullable private Ability<Object> ability;
    @Nullable private Object data;
    
    private int ticksLeft;
    
    public AbilityGoal(Pixie pixie) {
        this.pixie = pixie;
        this.ticksLeft = -1;
    }

    @Override
    public boolean canUse() {
        if (!(this.pixie.getOwningPlayer() instanceof ServerPlayer serverPlayer)) return false;
        if (QuestData.get(serverPlayer).getAlignment() != this.pixie.alignment) return false;
        if (this.pixie.level().random.nextFloat() > 0.01f) return false;
        this.ability = null;
        this.initIfNeeded();
        return this.ability != null;
    }

    @Override
    public void start() {
        this.initIfNeeded();
        this.ticksLeft = 75;
        this.pixie.setState(Pixie.State.IDLE);
    }

    @Override
    public boolean canContinueToUse() {
        return this.ability != null && this.ticksLeft > 0 && this.ability.stillValid(this.pixie.level(), this.pixie, this.data);
    }

    @Override
    public void stop() {
        this.ticksLeft = -1;
        this.ability = null;
        this.data = null;
        this.pixie.setState(Pixie.State.IDLE);
    }

    @Override
    public void tick() {
        if (this.ticksLeft > 0) {
            this.ticksLeft -= 1;
            if (this.ability != null) {
                if (this.ticksLeft <= 0) {
                    this.pixie.adjustBoredom(1);
                    this.ability.perform(this.pixie.level(), this.pixie, this.data);
                    this.stop();
                } else if (this.ticksLeft == 50) {
                    this.pixie.playSound(ModSoundEvents.pixieSpellcasting.getSoundEvent(), 0.7f, 1);
                } else if (this.ticksLeft == 45) {
                    Vec3 target = this.ability.target(this.pixie.level(), this.pixie, this.data);
                    if (target != null) {
                        this.pixie.getNavigation().moveTo(target.x, target.y, target.z, 0.5);
                    }
                    this.pixie.setState(Pixie.State.CASTING);
                } else if (this.ticksLeft <= 40) {
                    Vec3 target = this.ability.target(this.pixie.level(), this.pixie, this.data);
                    if (target != null) {
                        this.pixie.lookAt(EntityAnchorArgument.Anchor.EYES, target);
                    }
                }
            }
        }
    }

    private void initIfNeeded() {
        if (this.ability != null) return;
        Ability<?> ability = this.pixie.getAbility();
        Object data = ability.init(this.pixie.level(), this.pixie);
        if (data != null) {
            //noinspection unchecked
            this.ability = (Ability<Object>) ability;
            this.data = data;
        } else {
            this.ability = null;
            this.data = null;
        }
    }
}
