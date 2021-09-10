package com.feywild.feywild.entity;

import com.feywild.feywild.config.MobConfig;
import com.feywild.feywild.entity.base.FeyEntity;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.player.CapabilityQuests;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nonnull;

public class BeeKnight extends FeyEntity {

    // Model changes
    boolean aggravated;

    protected BeeKnight(EntityType<? extends FeyEntity> type, World world) {
        super(type, Alignment.SUMMER, world);
    }


    public static AttributeModifierMap.MutableAttribute getDefaultAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.FLYING_SPEED, Attributes.FLYING_SPEED.getDefaultValue())
                .add(Attributes.MAX_HEALTH, 24)
                .add(Attributes.MOVEMENT_SPEED, 0.35)
                .add(Attributes.LUCK, 0.2);
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
        super.readAdditionalSaveData(nbt);
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundNBT nbt) {
        nbt.putBoolean("aggravated", this.aggravated);
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
}
