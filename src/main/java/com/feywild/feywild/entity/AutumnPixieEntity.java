package com.feywild.feywild.entity;

import com.feywild.feywild.entity.goals.GoToSummoningPositionGoal;
import com.feywild.feywild.entity.goals.PumpkinCarverGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class AutumnPixieEntity extends FeyEntity implements IAnimatable {

    //TODO: Add names for the fey in lang folder so it can be seen by HWYLA. - Update - names are added to lang but still not displayed by HWYLA

    public static final DataParameter<Boolean> TAMED = EntityDataManager.defineId(AutumnPixieEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> CASTING = EntityDataManager.defineId(AutumnPixieEntity.class,
            DataSerializers.BOOLEAN);
    public BlockPos summonPos;
    //Geckolib variable
    private AnimationFactory factory = new AnimationFactory(this);
    private boolean setBehaviors;

    public AutumnPixieEntity(EntityType<? extends FeyEntity> entityEntityType, World world) {
        super(entityEntityType, world);
        //Geckolib check
        this.noCulling = true;
        this.moveControl = new FlyingMovementController(this, 4, true);
        addGoalsAfterConstructor();
    }

    public AutumnPixieEntity(World worldIn, boolean isTamed, BlockPos pos) {
        super(ModEntityTypes.AUTUMN_PIXIE.get(), worldIn);
        //Geckolib check
        this.noCulling = true;
        this.moveControl = new FlyingMovementController(this, 4, true);
        this.entityData.set(TAMED, isTamed);
        this.summonPos = pos;
        addGoalsAfterConstructor();
    }

    /* QUEST */

    public void setTag(AutumnPixieEntity entity) {
        entity.addTag("autumn_quest_pixie");
    }


    /* Animation */

    private <E extends IAnimatable> PlayState flyingPredicate(AnimationEvent<E> event) {

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.pixie.fly", true));
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState castingPredicate(AnimationEvent<E> event) {
        if (this.entityData.get(CASTING) && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.pixie.spellcasting", true));

            return PlayState.CONTINUE;
        }

        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData animationData) {

        AnimationController flyingController = new AnimationController(this, "flyingController", 0, this::flyingPredicate);
        AnimationController castingController = new AnimationController(this, "castingController", 0, this::castingPredicate);

        animationData.addAnimationController(flyingController);
        animationData.addAnimationController(castingController);

    }

    @Override
    public AnimationFactory getFactory() {

        return this.factory;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isCasting() {
        return this.entityData.get(CASTING);
    }

    public void setCasting(boolean casting) {
        this.entityData.set(CASTING, casting);
    }


    /* GOALS */

    @Override
    protected void registerGoals() {}

    protected void addGoalsAfterConstructor() {
        if (this.level.isClientSide())
            return;

        for (PrioritizedGoal goal : getGoals()) {
            this.goalSelector.addGoal(goal.getPriority(), goal.getGoal());
        }
    }

    public List<PrioritizedGoal> getGoals() {
        return this.entityData.get(TAMED) ? getTamedGoals() : getUntamedGoals();
    }

    public List<PrioritizedGoal> getTamedGoals() {
        List<PrioritizedGoal> list = new ArrayList<>();
        list.add(new PrioritizedGoal(0, new SwimGoal(this)));
        list.add(new PrioritizedGoal(2, new LookAtGoal(this, PlayerEntity.class, 8.0f)));
        list.add(new PrioritizedGoal(3, new GoToSummoningPositionGoal(this, () -> this.summonPos, 10)));
        list.add(new PrioritizedGoal(2, new LookRandomlyGoal(this)));
        list.add(new PrioritizedGoal(3, new WaterAvoidingRandomFlyingGoal(this, 1.0D)));
        list.add(new PrioritizedGoal(1, new PumpkinCarverGoal(this)));

        return list;
    }

    public List<PrioritizedGoal> getUntamedGoals() {
        List<PrioritizedGoal> list = new ArrayList<>();
        list.add(new PrioritizedGoal(0, new SwimGoal(this)));
        list.add(new PrioritizedGoal(2, new LookAtGoal(this, PlayerEntity.class, 8.0f)));
        list.add(new PrioritizedGoal(1, new TemptGoal(this, 1.25D,
                Ingredient.of(Items.COOKIE), false)));
        list.add(new PrioritizedGoal(3, new WaterAvoidingRandomFlyingGoal(this, 1.0D)));
        list.add(new PrioritizedGoal(2, new LookRandomlyGoal(this)));

        return list;
    }


    /* SAVE DATA */

    //write
    @Override
    public void addAdditionalSaveData(CompoundNBT tag) {
        super.addAdditionalSaveData(tag);
        if (summonPos != null) {
            tag.putInt("summonPos_X", summonPos.getX());
            tag.putInt("summonPos_Y", summonPos.getY());
            tag.putInt("summonPos_Z", summonPos.getZ());
        }

        this.entityData.set(TAMED, tag.getBoolean("tamed"));
    }

    //read
    @Override
    public void readAdditionalSaveData(CompoundNBT tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("summoner_x"))
            summonPos = new BlockPos(tag.getInt("summonPos_X"), tag.getInt("summonPos_Y"), tag.getInt("summonPos_Z"));

        // this.entityData.set(TAMED, tag.getBoolean("tamed"));

        if (tag.contains("tamed")) {
            this.setTamed(tag.getBoolean("tamed"));
        }

        if (!setBehaviors) {
            tryResetGoals();
            setBehaviors = true;
        }
    }

    public void tryResetGoals() {
        this.goalSelector.availableGoals = new LinkedHashSet<>();
        this.addGoalsAfterConstructor();
    }

    public void setTamed(boolean isTamed) {
        this.entityData.set(TAMED, isTamed);

    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        this.entityData.define(TAMED, false);
        this.entityData.define(CASTING, false);
    }

    @Override
    public boolean removeWhenFarAway(double p_213397_1_) {
        return false;
    }

}