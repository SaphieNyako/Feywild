package com.feywild.feywild.entity;

import com.feywild.feywild.item.ModItems;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class WinterPixieEntity extends FeyEntity implements IAnimatable {

    //Geckolib variable
    private AnimationFactory factory = new AnimationFactory(this);

    /* CONSTRUCTOR */
    protected WinterPixieEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
        super(type, worldIn);

        //Geckolib check
        this.ignoreFrustumCheck = true;
    }

    //Attributes (could be moved in FeyEntities)
    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {

        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.FLYING_SPEED, Attributes.FLYING_SPEED.getDefaultValue())
                .createMutableAttribute(Attributes.MAX_HEALTH, 12.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.35D)
                .createMutableAttribute(Attributes.LUCK, 0.2D);
    }


    /* SOUND EFFECTS */
    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_FOX_HURT;
    }


    //Ancient's note : we can keep them but adjust the pitch
    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        //Implement other Sound
        return SoundEvents.ENTITY_FOX_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        //Implement other Sound
        return SoundEvents.ENTITY_FOX_AMBIENT;
    }

    //Pitch
    @Override
    protected float getSoundPitch() {
        return 1.4f;
    }

    /* GOALS */
    @Override
    protected void registerGoals() {
        super.registerGoals();
        //Tempt goal doesn't work due to the constant movement ()
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D,
                Ingredient.fromItems(ModItems.FEY_DUST.get()),false));
    }

    /* Animation */

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        //Crash is fix when playing animation

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.pixie.fly", true));

        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        //Animation controller
        animationData.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {

        return this.factory;
    }
}
