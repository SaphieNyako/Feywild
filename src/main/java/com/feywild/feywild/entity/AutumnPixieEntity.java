package com.feywild.feywild.entity;

import com.feywild.feywild.item.ModItems;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class AutumnPixieEntity extends FeyEntity implements IAnimatable{

    //Geckolib variable
    private AnimationFactory factory = new AnimationFactory(this);

    /* CONSTRUCTOR */
    public AutumnPixieEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
        super(type, worldIn);
        //Geckolib check

        this.ignoreFrustumCheck = true;
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

