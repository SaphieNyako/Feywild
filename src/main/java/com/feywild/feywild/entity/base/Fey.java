package com.feywild.feywild.entity.base;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.goals.FeywildPanicGoal;
import com.feywild.feywild.entity.goals.TameCheckingGoal;
import com.feywild.feywild.network.ParticleSerializer;
import com.feywild.feywild.network.quest.OpenQuestDisplaySerializer;
import com.feywild.feywild.network.quest.OpenQuestSelectionSerializer;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.QuestDisplay;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.quest.task.FeyGiftTask;
import com.feywild.feywild.quest.util.AlignmentStack;
import com.feywild.feywild.quest.util.SelectableQuest;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import net.minecraftforge.network.PacketDistributor;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public abstract class Fey extends FlyingFeyBase {

    public static final EntityDataAccessor<Boolean> CASTING = SynchedEntityData.defineId(Fey.class, EntityDataSerializers.BOOLEAN);
    
    private boolean followingPlayer;
    
    protected Fey(EntityType<? extends Fey> type, Alignment alignment, Level level) {
        super(type, alignment, level);
    }

    public static AttributeSupplier.Builder getDefaultAttributes() {
        return Mob.createMobAttributes().add(Attributes.FLYING_SPEED, Attributes.FLYING_SPEED.getDefaultValue())
                .add(Attributes.MAX_HEALTH, 12)
                .add(Attributes.MOVEMENT_SPEED, 0.35)
                .add(Attributes.LUCK, 0.2);
    }

    public static boolean canSpawn(EntityType<? extends Fey> entity, LevelAccessor level, MobSpawnType reason, BlockPos pos, Random random) {
        return Tags.Blocks.DIRT.contains(level.getBlockState(pos.below()).getBlock()) || Tags.Blocks.SAND.contains(level.getBlockState(pos.below()).getBlock());
    }

    @Nullable
    @Override
    public Vec3 getCurrentPointOfInterest() {
        if (this.followingPlayer) {
            Player player = this.getOwningPlayer();
            return player == null ? null : player.position();
        } else {
            return super.getCurrentPointOfInterest();
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(50, new FeywildPanicGoal(this, 0.003, 13));
        this.goalSelector.addGoal(10, new TameCheckingGoal(this, false, new TemptGoal(this, 1.25, Ingredient.of(Items.COOKIE), false)));
    }
    
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CASTING, false);
    }
    
    public boolean isCasting() {
        return this.entityData.get(CASTING);
    }

    public void setCasting(boolean casting) {
        this.entityData.set(CASTING, casting);
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("FollowingPlayer", this.followingPlayer);
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.followingPlayer = nbt.getBoolean("FollowingPlayer");
    }

    @Nonnull
    @Override
    public InteractionResult interactAt(@Nonnull Player player, @Nonnull Vec3 hitVec, @Nonnull InteractionHand hand) {
        if (!this.level.isClientSide) {
            if (player.isShiftKeyDown()) {
                if (this.owner != null && this.owner.equals(player.getUUID())) {
                    if (this.followingPlayer) {
                        this.followingPlayer = false;
                        this.setSummonPos(this.blockPosition());
                        player.sendMessage(new TranslatableComponent("message.feywild." + this.alignment.id + "_fey_stay").append(new TranslatableComponent("message.feywild.fey_stay").withStyle(ChatFormatting.ITALIC)), player.getUUID());
                    } else {
                        this.followingPlayer = true;
                        player.sendMessage(new TranslatableComponent("message.feywild." + this.alignment.id + "_fey_follow").append(new TranslatableComponent("message.feywild.fey_follow").withStyle(ChatFormatting.ITALIC)), player.getUUID());
                    }
                }
            } else if (player instanceof ServerPlayer && this.tryAcceptGift((ServerPlayer) player, hand)) {
                player.swing(hand, true);
            } else if (player.getItemInHand(hand).getItem() == Items.COOKIE && (this.getLastHurtByMob() == null || !this.getLastHurtByMob().isAlive())) {
                this.heal(4);
                if (!player.isCreative()) player.getItemInHand(hand).shrink(1);
                FeywildMod.getNetwork().sendParticles(this.level, ParticleSerializer.Type.FEY_HEART, this.getX(), this.getY(), this.getZ());
                player.swing(hand, true);
            } else if (player.getItemInHand(hand).getItem() == Items.NAME_TAG) {
                setCustomName(player.getItemInHand(hand).getHoverName().copy());
                setCustomNameVisible(true);
                player.sendMessage(new TranslatableComponent("message.feywild." + this.alignment.id + "_fey_name"), player.getUUID());
            } else if (this.isTamed() && player instanceof ServerPlayer && this.owner != null && this.owner.equals(player.getUUID())) {
                ItemStack stack = player.getItemInHand(hand);
                if (stack.isEmpty()) {
                    this.interactQuest((ServerPlayer) player, hand);
                }
            }
        }
        return InteractionResult.CONSUME;
    }

    private void interactQuest(ServerPlayer player, InteractionHand hand) {
        QuestData quests = QuestData.get(player);
        if (quests.canComplete(this.alignment)) {
            QuestDisplay completionDisplay = quests.completePendingQuest();
            if (completionDisplay != null) {
                FeywildMod.getNetwork().channel.send(PacketDistributor.PLAYER.with(() -> player), new OpenQuestDisplaySerializer.Message(completionDisplay, false));
                player.swing(hand, true);
            } else {
                List<SelectableQuest> active = quests.getActiveQuests();
                if (active.size() == 1) {
                    FeywildMod.getNetwork().channel.send(PacketDistributor.PLAYER.with(() -> player), new OpenQuestDisplaySerializer.Message(active.get(0).display, false));
                    player.swing(hand, true);
                } else if (!active.isEmpty()) {
                    FeywildMod.getNetwork().channel.send(PacketDistributor.PLAYER.with(() -> player), new OpenQuestSelectionSerializer.Message(this.getDisplayName(), this.alignment, active));
                    player.swing(hand, true);
                }
            }
        } else {
            QuestDisplay initDisplay = quests.initialize(this.alignment);
            if (initDisplay != null) {
                FeywildMod.getNetwork().channel.send(PacketDistributor.PLAYER.with(() -> player), new OpenQuestDisplaySerializer.Message(initDisplay, true));
                player.swing(hand, true);
            }
        }
    }

    private boolean tryAcceptGift(ServerPlayer player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!stack.isEmpty()) {
            AlignmentStack input = new AlignmentStack(this.alignment, stack);
            if (QuestData.get(player).checkComplete(FeyGiftTask.INSTANCE, input)) {
                if (!player.isCreative()) stack.shrink(1);
                player.sendMessage(new TranslatableComponent("message.feywild." + this.alignment.id + "_fey_thanks"), player.getUUID());
                return true;
            }
        }
        return false;
    }

    private <E extends IAnimatable> PlayState flyingPredicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.pixie.fly", true));
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState castingPredicate(AnimationEvent<E> event) {
        if (this.isCasting() && !(this.dead || this.isDeadOrDying())) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.pixie.spellcasting", true));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        AnimationController<Fey> flyingController = new AnimationController<>(this, "flyingController", 0, this::flyingPredicate);
        AnimationController<Fey> castingController = new AnimationController<>(this, "castingController", 0, this::castingPredicate);
        animationData.addAnimationController(flyingController);
        animationData.addAnimationController(castingController);
    }
}



