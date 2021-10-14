package com.feywild.feywild.entity.base;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.goals.FeyWildPanicGoal;
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
import io.github.noeppi_noeppi.libx.util.NBTX;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.network.PacketDistributor;
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

public abstract class FeyEntity extends FeyBase implements ITameable {

    public static final DataParameter<Boolean> CASTING = EntityDataManager.defineId(FeyEntity.class, DataSerializers.BOOLEAN);

    @Nullable
    private BlockPos currentTargetPos;
    private boolean isTamed;

    protected FeyEntity(EntityType<? extends FeyEntity> type, Alignment alignment, World world) {
        super(type, alignment, world);
    }

    public static boolean canSpawn(EntityType<? extends FeyEntity> entity, IWorld world, SpawnReason reason, BlockPos pos, Random random) {
        return Tags.Blocks.DIRT.contains(world.getBlockState(pos.below()).getBlock()) || Tags.Blocks.SAND.contains(world.getBlockState(pos.below()).getBlock());
    }

    @Nullable
    public BlockPos getCurrentTargetPos() {
        return this.currentTargetPos;
    }

    public void setCurrentTargetPos(@Nullable BlockPos currentTargetPos) {
        this.currentTargetPos = currentTargetPos == null ? null : currentTargetPos.immutable();
    }

    @Override
    public boolean isTamed() {
        return this.isTamed;
    }

    public void setTamed(boolean tamed) {
        this.isTamed = tamed;
    }

    @Nullable
    @Override
    public Vector3d getCurrentPointOfInterest() {
        if (!this.isTamed()) {
            return null;
        } else if (this.currentTargetPos != null) {
            return new Vector3d(this.currentTargetPos.getX() + 0.5, this.currentTargetPos.getY() + 0.5, this.currentTargetPos.getZ() + 0.5);
        } else {
            PlayerEntity player = this.getOwner();
            if (player != null) {
                return player.position();
            }
        }
        return null;
    }

    public boolean isCasting() {
        return this.entityData.get(CASTING);
    }

    public void setCasting(boolean casting) {
        this.entityData.set(CASTING, casting);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CASTING, false);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(50, new FeyWildPanicGoal(this, 0.003, 13));
        this.goalSelector.addGoal(10, new TameCheckingGoal(this, false, new TemptGoal(this, 1.25, Ingredient.of(Items.COOKIE), false)));
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("Tamed", this.isTamed);
        if (this.currentTargetPos != null) {
            NBTX.putPos(nbt, "CurrentTarget", this.currentTargetPos);
        }
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);
        this.isTamed = nbt.getBoolean("Tamed");
        this.currentTargetPos = NBTX.getPos(nbt, "CurrentTarget", null);
    }

    @Nonnull
    @Override
    public ActionResultType interactAt(@Nonnull PlayerEntity player, @Nonnull Vector3d hitVec, @Nonnull Hand hand) {
        if (!this.level.isClientSide) {
            if (player.isShiftKeyDown()) {
                if (this.owner != null && this.owner.equals(player.getUUID())) {
                    if (this.getCurrentTargetPos() == null) {
                        this.setCurrentTargetPos(this.blockPosition());
                        player.sendMessage(new TranslationTextComponent("message.feywild." + this.alignment.id + "_fey_stay").append(new TranslationTextComponent("message.feywild.fey_stay").withStyle(TextFormatting.ITALIC)), player.getUUID());
                    } else {
                        this.setCurrentTargetPos(null);
                        player.sendMessage(new TranslationTextComponent("message.feywild." + this.alignment.id + "_fey_follow").append(new TranslationTextComponent("message.feywild.fey_follow").withStyle(TextFormatting.ITALIC)), player.getUUID());
                    }
                }
            } else if (player instanceof ServerPlayerEntity && this.tryAcceptGift((ServerPlayerEntity) player, hand)) {
                player.swing(hand, true);
            } else if (player.getItemInHand(hand).getItem() == Items.COOKIE && (this.getLastHurtByMob() == null || !this.getLastHurtByMob().isAlive())) {
                this.heal(4);
                if (!player.isCreative()) player.getItemInHand(hand).shrink(1);
                FeywildMod.getNetwork().sendParticles(this.level, ParticleSerializer.Type.FEY_HEART, this.getX(), this.getY(), this.getZ());
                player.swing(hand, true);
            } else if (player.getItemInHand(hand).getItem() == Items.NAME_TAG) {
                setCustomName(new StringTextComponent(player.getItemInHand(hand).getDisplayName().getString()
                        .substring(1, player.getItemInHand(hand).getDisplayName().getString().length() - 1)));
                setCustomNameVisible(true);
                player.sendMessage(new TranslationTextComponent("message.feywild." + this.alignment.id + "_fey_name"), player.getUUID());
            } else if (this.isTamed() && player instanceof ServerPlayerEntity && this.owner != null && this.owner.equals(player.getUUID())) {
                ItemStack stack = player.getItemInHand(hand);
                if (stack.isEmpty()) {
                    this.interactQuest((ServerPlayerEntity) player, hand);
                }
            }
        }
        return ActionResultType.CONSUME;
    }

    private void interactQuest(ServerPlayerEntity player, Hand hand) {
        QuestData quests = QuestData.get(player);
        if (quests.canComplete(this.alignment)) {
            QuestDisplay completionDisplay = quests.completePendingQuest();
            if (completionDisplay != null) {
                FeywildMod.getNetwork().instance.send(PacketDistributor.PLAYER.with(() -> player), new OpenQuestDisplaySerializer.Message(completionDisplay, false));
                player.swing(hand, true);
            } else {
                List<SelectableQuest> active = quests.getActiveQuests();
                if (active.size() == 1) {
                    FeywildMod.getNetwork().instance.send(PacketDistributor.PLAYER.with(() -> player), new OpenQuestDisplaySerializer.Message(active.get(0).display, false));
                    player.swing(hand, true);
                } else if (!active.isEmpty()) {
                    FeywildMod.getNetwork().instance.send(PacketDistributor.PLAYER.with(() -> player), new OpenQuestSelectionSerializer.Message(this.getDisplayName(), this.alignment, active));
                    player.swing(hand, true);
                }
            }
        } else {
            QuestDisplay initDisplay = quests.initialize(this.alignment);
            if (initDisplay != null) {
                FeywildMod.getNetwork().instance.send(PacketDistributor.PLAYER.with(() -> player), new OpenQuestDisplaySerializer.Message(initDisplay, true));
                player.swing(hand, true);
            }
        }
    }

    private boolean tryAcceptGift(ServerPlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!stack.isEmpty()) {
            AlignmentStack input = new AlignmentStack(this.alignment, stack);
            if (QuestData.get(player).checkComplete(FeyGiftTask.INSTANCE, input)) {
                if (!player.isCreative()) stack.shrink(1);
                player.sendMessage(new TranslationTextComponent("message.feywild." + this.alignment.id + "_fey_thanks"), player.getUUID());
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
        AnimationController<FeyEntity> flyingController = new AnimationController<>(this, "flyingController", 0, this::flyingPredicate);
        AnimationController<FeyEntity> castingController = new AnimationController<>(this, "castingController", 0, this::castingPredicate);
        animationData.addAnimationController(flyingController);
        animationData.addAnimationController(castingController);
    }
}



