package com.feywild.feywild.entity;

import com.feywild.feywild.entity.goals.GoToSummoningPositionGoal;
import com.feywild.feywild.entity.goals.SummonSnowManGoal;
import com.feywild.feywild.entity.util.FeyEntity;
import com.feywild.feywild.events.ModEvents;
import com.feywild.feywild.network.FeywildPacketHandler;
import com.feywild.feywild.network.OpenQuestScreen;
import com.feywild.feywild.network.ParticleMessage;
import com.feywild.feywild.network.QuestMessage;
import com.feywild.feywild.quest.QuestMap;
import com.feywild.feywild.util.ModUtil;
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
import net.minecraft.scoreboard.Score;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;
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

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

public class WinterPixieEntity extends FeyEntity implements IAnimatable {

    private static final DataParameter<Boolean> CASTING = EntityDataManager.defineId(WinterPixieEntity.class,
            DataSerializers.BOOLEAN);
    private final AnimationFactory factory = new AnimationFactory(this);
    public BlockPos summonPos;
    private boolean tamed = false;
    private boolean setBehaviors;

    public WinterPixieEntity(EntityType<? extends FeyEntity> type, World worldIn) {
        super(type, worldIn);
        //Geckolib check
        this.noCulling = true;
        this.moveControl = new FlyingMovementController(this, 4, true);
        addGoalsAfterConstructor();

    }

    public WinterPixieEntity(World worldIn, boolean isTamed, BlockPos pos) {
        super(ModEntityTypes.WINTER_PIXIE.get(), worldIn);
        //Geckolib check
        this.noCulling = true;
        this.moveControl = new FlyingMovementController(this, 4, true);
        setTamed(isTamed);
        this.summonPos = pos;
        addGoalsAfterConstructor();
    }


    /* QUEST */

    public void setTag(WinterPixieEntity entity) {
        entity.addTag("winter_quest_pixie");
    }

    @Nonnull
    @Override
    public ActionResultType interactAt(@Nonnull PlayerEntity player, @Nonnull Vector3d vec, @Nonnull Hand hand) {
        if (player.getCommandSenderWorld().isClientSide) return ActionResultType.SUCCESS;

        if (!player.getCommandSenderWorld().isClientSide && !player.getTags().contains(QuestMap.Courts.SpringAligned.toString()) && !player.getTags().contains(QuestMap.Courts.AutumnAligned.toString()) && !player.getTags().contains(QuestMap.Courts.SummerAligned.toString())) {  //&& player.getItemInHand(hand).isEmpty()
            if (player.getItemInHand(hand).isEmpty()) {
                if (this.getTags().contains("winter_quest_pixie")) {

                    Score questId = ModUtil.getOrCreatePlayerScore(player.getName().getString(), QuestMap.Scores.FW_Quest.toString(), player.level, 0);

                    if (!player.getTags().contains(QuestMap.Courts.WinterAligned.toString())) {
                        questId.setScore(300);
                        FeywildPacketHandler.sendToPlayer(new QuestMessage(player.getUUID(), questId.getScore()), player);
                    }

                    if (!QuestMap.getSound(questId.getScore()).equals("NULL"))
                        player.level.playSound(null, player.blockPosition(), Objects.requireNonNull(Registry.SOUND_EVENT.get(new ResourceLocation(QuestMap.getSound(questId.getScore())))), SoundCategory.VOICE, 1, 1);

                    FeywildPacketHandler.sendToPlayer(new OpenQuestScreen(questId.getScore(), QuestMap.getLineNumber(questId.getScore()), QuestMap.getCanSkip(questId.getScore())), player);

                }

            } else {

                if (ModEvents.genericInteract(player, hand, this, true)) {
                    player.sendMessage(new TranslationTextComponent("summer_fey_thanks"), player.getUUID());
                    FeywildPacketHandler.sendToPlayersInRange(player.level, blockPosition(), new ParticleMessage(getX() + 0.5, getY() + 0.5, getZ() + 0.5, 0, 0, 0, 20, 1, 0), 64);
                }
            }
        }
        return ActionResultType.SUCCESS;

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

        AnimationController<WinterPixieEntity> flyingController = new AnimationController<>(this, "flyingController", 0, this::flyingPredicate);
        AnimationController<WinterPixieEntity> castingController = new AnimationController<>(this, "castingController", 0, this::castingPredicate);

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
    protected void registerGoals() {
    }

    protected void addGoalsAfterConstructor() {
        if (this.level.isClientSide())
            return;

        for (PrioritizedGoal goal : getGoals()) {
            this.goalSelector.addGoal(goal.getPriority(), goal.getGoal());
        }
    }

    public List<PrioritizedGoal> getGoals() {
        return this.tamed ? getTamedGoals() : getUntamedGoals();
    }

    public List<PrioritizedGoal> getTamedGoals() {
        List<PrioritizedGoal> list = new ArrayList<>();
        list.add(new PrioritizedGoal(0, new SwimGoal(this)));
        list.add(new PrioritizedGoal(2, new LookAtGoal(this, PlayerEntity.class, 8.0f)));
        list.add(new PrioritizedGoal(3, new GoToSummoningPositionGoal(this, () -> this.summonPos, 10)));
        list.add(new PrioritizedGoal(2, new LookRandomlyGoal(this)));
        list.add(new PrioritizedGoal(3, new WaterAvoidingRandomFlyingGoal(this, 1.0D)));
        list.add(new PrioritizedGoal(1, new SummonSnowManGoal(this, () -> this.summonPos)));

        return list;
    }

    public List<PrioritizedGoal> getUntamedGoals() {
        List<PrioritizedGoal> list = new ArrayList<>();
        list.add(new PrioritizedGoal(4, new FeyWildPanic(this, 0.003D, 13)));
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
    public void addAdditionalSaveData(@Nonnull CompoundNBT tag) {
        super.addAdditionalSaveData(tag);
        if (summonPos != null) {
            tag.putInt("summonPos_X", summonPos.getX());
            tag.putInt("summonPos_Y", summonPos.getY());
            tag.putInt("summonPos_Z", summonPos.getZ());
        }

        tag.putBoolean("tamed", tamed);
    }

    //read
    @Override
    public void readAdditionalSaveData(@Nonnull CompoundNBT tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("summonPos_X"))
            summonPos = new BlockPos(tag.getInt("summonPos_X"), tag.getInt("summonPos_Y"), tag.getInt("summonPos_Z"));

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

    public boolean isTamed() {
        return tamed;
    }

    public void setTamed(boolean tamed) {
        this.tamed = tamed;

    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        this.entityData.define(CASTING, false);

    }

    @Override
    public boolean removeWhenFarAway(double p_213397_1_) {
        return false;
    }

}
