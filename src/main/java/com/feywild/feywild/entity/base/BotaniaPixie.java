package com.feywild.feywild.entity.base;

import com.feywild.feywild.entity.goals.FeywildPanicGoal;
import com.feywild.feywild.quest.Alignment;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;

public abstract class BotaniaPixie extends FlyingFeyBase {

    protected BotaniaPixie(EntityType<? extends FeyBase> entityType, Alignment alignment, Level level) {
        super(entityType, alignment, level);
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(50, new FeywildPanicGoal(this, 0.003, 13));
    }

    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide && getParticle() != null && random.nextInt(11) == 0) {
            for (int i = 0; i < 4; i++) {
                this.level.addParticle(getParticle(), this.getX() + (Math.random() - 0.5) * 0.25,
                        this.getY() + (Math.random() - 0.5) * 0.25,
                        this.getZ() + (Math.random() - 0.5) * 0.25,
                        0, 0, 0);
            }
        }
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damageSource) {
        return null;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return null;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<BotaniaPixie> flyingController = new AnimationController<>(this, "flyingController", 0, this::flyingPredicate);
        data.addAnimationController(flyingController);
    }

    private <E extends IAnimatable> PlayState flyingPredicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.botania_pixie.fly", true));
        return PlayState.CONTINUE;
    }
}
