package com.feywild.feywild.entity;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.goals.GoToSummoningPositionGoal;
import com.feywild.feywild.entity.goals.AddShieldGoal;
import com.feywild.feywild.entity.util.FeyEntity;
import com.feywild.feywild.events.ModEvents;
import com.feywild.feywild.network.FeywildPacketHandler;
import com.feywild.feywild.network.OpenQuestScreen;
import com.feywild.feywild.network.ParticleMessage;
import com.feywild.feywild.quest.MessageQuest;
import com.feywild.feywild.quest.Quest;
import com.feywild.feywild.quest.QuestMap;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SStopSoundPacket;
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
import java.util.*;

// Pretty sure there's much more code that could go in FeyEntity
public class AutumnPixieEntity extends FeyEntity implements IAnimatable {

    private static final DataParameter<Boolean> CASTING = EntityDataManager.defineId(AutumnPixieEntity.class,
            DataSerializers.BOOLEAN);
    private final AnimationFactory factory = new AnimationFactory(this);
    public BlockPos summonPos;
    FeyEntity entity = this;
    private boolean tamed = false;
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
        setTamed(isTamed);
        this.summonPos = pos;
        addGoalsAfterConstructor();
    }

    /* QUEST */

    public void setTag(AutumnPixieEntity entity) {
        entity.addTag("autumn_quest_pixie");
    }

    @Nonnull
    @Override
    public ActionResultType interactAt(@Nonnull PlayerEntity player, @Nonnull Vector3d vec, @Nonnull Hand hand) {
        if (player.getCommandSenderWorld().isClientSide) return ActionResultType.SUCCESS;

        if (!player.getCommandSenderWorld().isClientSide && !player.getTags().contains(QuestMap.Courts.SpringAligned.toString()) && !player.getTags().contains(QuestMap.Courts.WinterAligned.toString()) && !player.getTags().contains(QuestMap.Courts.SummerAligned.toString())) {  //&& player.getItemInHand(hand).isEmpty()
            if (player.getItemInHand(hand).isEmpty()) {
                if (this.getTags().contains("autumn_quest_pixie")) {

                    String questProgressData = player.getPersistentData().getString("FWQuest");
                    FeywildMod.LOGGER.debug(questProgressData);

                    if (!player.getTags().contains(QuestMap.Courts.AutumnAligned.toString()) && questProgressData.equalsIgnoreCase("/")) {
                        // initial quest
                        ResourceLocation res = new ResourceLocation(FeywildMod.MOD_ID,"autumn_init");

                        Quest quest =QuestMap.getQuest(res.toString());
                        assert quest != null;
                        FeywildPacketHandler.sendToPlayer(new OpenQuestScreen(Collections.singletonList(new MessageQuest(res, quest.getText(), quest.getName(), quest.getIcon(),quest.canSkip())), 2), player);

                        if(!quest.getSound().equals("NULL")){
                            ((ServerPlayerEntity)player).connection.send(new SStopSoundPacket(new ResourceLocation(quest.getSound()),SoundCategory.VOICE));
                            player.level.playSound(null, player.blockPosition(), Objects.requireNonNull(Registry.SOUND_EVENT.get(new ResourceLocation(quest.getSound()))), SoundCategory.VOICE, 1, 1);
                        }

                    }else{
                        // Send over available quests
                        List<MessageQuest> list = new LinkedList<>();
                        Quest quest;
                            for (String s : questProgressData.split("/")[0].split("-")) {
                                quest = QuestMap.getQuest(s);
                                list.add(new MessageQuest(new ResourceLocation(s), Objects.requireNonNull(quest).getText(), Objects.requireNonNull(quest).getName(), Objects.requireNonNull(quest).getIcon(), quest.canSkip()));
                            }
                            FeywildPacketHandler.sendToPlayer(new OpenQuestScreen(list,2), player);
                        }
                }
            } else {

                if (ModEvents.genericInteract(player, hand, this, true)) {
                    player.sendMessage(new TranslationTextComponent("spring_fey_thanks"), player.getUUID());
                    FeywildPacketHandler.sendToPlayersInRange(player.level, blockPosition(), new ParticleMessage(getX(), getY() + 0.5, getZ(), 0, 0, 0, 10, 1, 0), 64);
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

        AnimationController<AutumnPixieEntity> flyingController = new AnimationController<>(this, "flyingController", 0, this::flyingPredicate);
        AnimationController<AutumnPixieEntity> castingController = new AnimationController<>(this, "castingController", 0, this::castingPredicate);

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
        list.add(new PrioritizedGoal(1, new AddShieldGoal(this)));

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