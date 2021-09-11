package com.feywild.feywild.entity;

import com.feywild.feywild.config.MobConfig;
import com.feywild.feywild.entity.base.FeyEntity;
import com.feywild.feywild.entity.goals.GoToTargetPositionGoal;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.player.CapabilityQuests;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.PhantomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

//TODO make another base class for fey that doesn't have quest behaviour
//TODO make aggravated a data param and make it sync so that the texture can change
public class BeeKnight extends FeyEntity {

    // Model changes
    private boolean aggravated;

    private BlockPos treasureBlock;

    protected BeeKnight(EntityType<? extends FeyEntity> type, World world) {
        super(type, Alignment.SUMMER, world);
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance p_213386_2_, SpawnReason p_213386_3_, @Nullable ILivingEntityData p_213386_4_, @Nullable CompoundNBT p_213386_5_) {
        setTreasureBlock(this.blockPosition());
        return super.finalizeSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
    }

    public void setTreasureBlock(BlockPos treasureBlock) {
        this.treasureBlock = treasureBlock;
    }

    public static AttributeModifierMap.MutableAttribute getDefaultAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.FLYING_SPEED, Attributes.FLYING_SPEED.getDefaultValue())
                .add(Attributes.MAX_HEALTH, 24)
                .add(Attributes.MOVEMENT_SPEED, 0.35)
                .add(Attributes.LUCK, 0.2)
                .add(Attributes.FOLLOW_RANGE, 40)
                .add(Attributes.ATTACK_DAMAGE, 4);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(30, new LookAtGoal(this, PlayerEntity.class, 8f));
        this.goalSelector.addGoal(11, new GoToTargetPositionGoal(this, this::getTreasureVector, 10, 1.5f));
        this.goalSelector.addGoal(30, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(50, new WaterAvoidingRandomFlyingGoal(this, 1));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<PlayerEntity>(this, PlayerEntity.class, true));
        this.goalSelector.addGoal(1, new RestrictedAttackGoal(this,1.2f,true));
    }

    @Override
    public void tick() {
        super.tick();
        if(hurtTime > 0){
            setAggravated(true);
        }
    }

    @Nonnull
    @Override
    public ActionResultType interactAt(@Nonnull PlayerEntity player, @Nonnull Vector3d hitVec, @Nonnull Hand hand) {
        if(!player.level.isClientSide){
            player.getCapability(CapabilityQuests.QUESTS).ifPresent(cap -> {
                if(cap.getReputation() >= MobConfig.summer_bee_knight.required_reputation && cap.getAlignment() == Alignment.SUMMER){
                    player.sendMessage(new TranslationTextComponent("message.feywild.bee_knight_pass"), player.getUUID());
                }else{
                    player.sendMessage(new TranslationTextComponent("message.feywild.bee_knight_fail"), player.getUUID());
                }
            });
        }
        return ActionResultType.SUCCESS;
    }
    @Override
    public void readAdditionalSaveData(@Nonnull CompoundNBT nbt) {
        this.aggravated = nbt.getBoolean("aggravated");
        this.setTreasureBlock(new BlockPos(nbt.getInt("TreasureX"),nbt.getInt("TreasureY"),nbt.getInt("TreasureZ")));
        super.readAdditionalSaveData(nbt);
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundNBT nbt) {
        nbt.putBoolean("aggravated", this.aggravated);
        nbt.putInt("TreasureX", treasureBlock.getX());
        nbt.putInt("TreasureY", treasureBlock.getY());
        nbt.putInt("TreasureZ", treasureBlock.getZ());
        super.addAdditionalSaveData(nbt);
    }

    private <E extends IAnimatable> PlayState flyingPredicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bee_knight.fly", true));
        return PlayState.CONTINUE;
    }
    @Override
    public void registerControllers(AnimationData animationData) {
        AnimationController<FeyEntity> flyingController = new AnimationController<>(this, "flyingController", 0, this::flyingPredicate);
        animationData.addAnimationController(flyingController);
    }

    public boolean isAggravated() {
        return aggravated;
    }

    public void setAggravated(boolean aggravated) {
        this.aggravated = aggravated;
    }

    public BlockPos getTreasureBlock() {
        return treasureBlock;
    }


    public Vector3d getTreasureVector() {
        return new Vector3d(treasureBlock.getX(),treasureBlock.getY(),treasureBlock.getZ());
    }

    class RestrictedAttackGoal extends MeleeAttackGoal{
        BeeKnight creature;
        public RestrictedAttackGoal(BeeKnight creature, double speed, boolean visualContact) {
            super(creature, speed, visualContact);
            this.creature = creature;
        }

        @Override
        public boolean canUse() {
            return creature.isAggravated();
        }

        @Override
        public void start() {
            super.start();
            if(creature.getTreasureBlock() == null || creature.getTreasureBlock().closerThan(new BlockPos(0,0,0),1)){
                creature.setTreasureBlock(creature.blockPosition());
            }
        }

        @Override
        public boolean canContinueToUse() {
            return creature.isAggravated() && creature.getTreasureBlock().closerThan(creature.blockPosition(),10);
        }

        @Override
        public void stop() {
            super.stop();
            creature.setAggravated(false);
        }
    }

}
