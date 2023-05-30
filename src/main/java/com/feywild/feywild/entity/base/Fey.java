package com.feywild.feywild.entity.base;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModTrees;
import com.feywild.feywild.entity.goals.FeywildPanicGoal;
import com.feywild.feywild.entity.goals.TameCheckingGoal;
import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.network.ParticleMessage;
import com.feywild.feywild.network.quest.OpenQuestDisplayMessage;
import com.feywild.feywild.network.quest.OpenQuestSelectionMessage;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.QuestDisplay;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.quest.task.FeyGiftTask;
import com.feywild.feywild.quest.task.SpecialTask;
import com.feywild.feywild.quest.util.AlignmentStack;
import com.feywild.feywild.quest.util.SelectableQuest;
import com.feywild.feywild.quest.util.SpecialTaskAction;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.SaplingGrowTreeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.PacketDistributor;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.List;
import java.util.Random;

public abstract class Fey extends FlyingFeyBase {

    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(Fey.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> BORED = SynchedEntityData.defineId(Fey.class, EntityDataSerializers.INT);

    protected Fey(EntityType<? extends Fey> type, Alignment alignment, Level level) {
        super(type, alignment, level);
        setBored(4);
        if (!level.isClientSide)
            MinecraftForge.EVENT_BUS.register(this);
    }

    public static double distanceFrom(BlockPos start, BlockPos end) {
        if (start == null || end == null)
            return 0;
        return Math.sqrt(Math.pow(start.getX() - end.getX(), 2) + Math.pow(start.getY() - end.getY(), 2) + Math.pow(start.getZ() - end.getZ(), 2));
    }

    @Override
    public boolean canFollowPlayer() {
        return true;
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(50, new FeywildPanicGoal(this, 0.003, 13));
        this.goalSelector.addGoal(10, new TameCheckingGoal(this, false, new TemptGoal(this, 1.25, Ingredient.of(Items.COOKIE), false)));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(STATE, 0);
        this.entityData.define(BORED, 0);
    }

    @Nonnull
    @Override
    @OverridingMethodsMustInvokeSuper
    public InteractionResult interactAt(@Nonnull Player player, @Nonnull Vec3 hitVec, @Nonnull InteractionHand hand) {
        InteractionResult superResult = super.interactAt(player, hitVec, hand);
        ItemStack stack = player.getItemInHand(hand);
        if (superResult == InteractionResult.PASS) {
            if (!stack.isEmpty() && player instanceof ServerPlayer && this.tryAcceptGift((ServerPlayer) player, hand)) {
                player.swing(hand, true);

            } else if (player.getItemInHand(hand).getItem() == Items.COOKIE && (this.getLastHurtByMob() == null || !this.getLastHurtByMob().isAlive())) {
                this.heal(getBoredCount());
                this.getBoredCount();
                if (!this.isTamed() && player instanceof ServerPlayer && this.owner == null) {
                    Random random = new Random();
                    if (random.nextInt(4) <= 0) {
                        this.spawnAtLocation(new ItemStack(ModItems.feyDust));
                        this.playSound(SoundEvents.ENDERMAN_TELEPORT);
                        this.discard();
                        //TODO Change message for each court/add voice
                        player.sendSystemMessage(Component.literal("Come find me!"));
                    }
                }
                if (!player.isCreative()) {
                    player.getItemInHand(hand).shrink(1);
                }
                FeywildMod.getNetwork().sendParticles(this.level, ParticleMessage.Type.FEY_HEART, this.getX(), this.getY() + 1, this.getZ());
                player.swing(hand, true);

            } else if (player.getItemInHand(hand).getItem() == Items.NAME_TAG) {
                setCustomName(player.getItemInHand(hand).getHoverName().copy());
                setCustomNameVisible(true);
                if (!level.isClientSide) {
                    player.sendSystemMessage(Component.translatable("message.feywild." + this.alignment.id + "_fey_name"));
                }

            } else if (!this.isTamed() && this.getOwner() == null && player instanceof ServerPlayer && player.getItemInHand(hand).getItem() == Items.ENDER_EYE) {
                player.swing(player.getUsedItemHand(), true);
                if (!player.isCreative()) {
                    player.getItemInHand(hand).shrink(1);
                }
                player.addItem(new ItemStack(ModItems.pixieOrb));
                this.remove(RemovalReason.DISCARDED);

            } else if (this.isTamed() && player instanceof ServerPlayer && this.owner != null && this.owner.equals(player.getUUID())) {

                if (stack.isEmpty() && !player.isShiftKeyDown()) {
                    this.interactQuest((ServerPlayer) player, hand);
                }
            }
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        } else {
            return superResult;
        }
    }

    public int getBoredCount() {
        return 20;
    }

    private void interactQuest(ServerPlayer player, InteractionHand hand) {
        QuestData quests = QuestData.get(player);
        if (quests.canComplete(this.alignment)) {
            QuestDisplay completionDisplay = quests.completePendingQuest();
            if (completionDisplay != null) {
                FeywildMod.getNetwork().channel.send(PacketDistributor.PLAYER.with(() -> player), new OpenQuestDisplayMessage(completionDisplay, false, this.getId(), this.alignment));
                player.swing(hand, true);
            } else {
                List<SelectableQuest> active = quests.getActiveQuests();
                if (active.size() == 1) {
                    FeywildMod.getNetwork().channel.send(PacketDistributor.PLAYER.with(() -> player), new OpenQuestDisplayMessage(active.get(0).display(), false, this.getId(), this.alignment));
                    player.swing(hand, true);
                } else if (!active.isEmpty()) {
                    FeywildMod.getNetwork().channel.send(PacketDistributor.PLAYER.with(() -> player), new OpenQuestSelectionMessage(this.getDisplayName(), active, this.getId(), this.alignment));
                    player.swing(hand, true);
                }
            }
        } else {
            QuestDisplay initDisplay = quests.initialize(this.alignment);
            if (initDisplay != null) {
                FeywildMod.getNetwork().channel.send(PacketDistributor.PLAYER.with(() -> player), new OpenQuestDisplayMessage(initDisplay, true, this.getId(), this.alignment));
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
                player.sendSystemMessage(Component.translatable("message.feywild." + this.alignment.id + "_fey_thanks"));
                return true;
            }
        }
        return false;
    }

    public Fey.State getState() {
        Fey.State[] states = Fey.State.values();
        return states[Mth.clamp(this.entityData.get(STATE), 0, states.length - 1)];
    }

    public void setState(Fey.State state) {
        this.entityData.set(STATE, state.ordinal());
    }

    public int getBored() {
        return this.entityData.get(BORED);
    }

    public void setBored(int bored) {
        this.entityData.set(BORED, bored);
    }


    private <E extends IAnimatable> PlayState animationPredicate(AnimationEvent<E> event) {
        if (!this.dead && !this.isDeadOrDying()) {
            if (this.getState() == State.CASTING) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("spellcasting", true));
                return PlayState.CONTINUE;
            }
        }

        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("fly", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::animationPredicate));
    }

    @SubscribeEvent
    public void treeGrow(SaplingGrowTreeEvent event) {
        BlockPos pos = event.getPos();
        Block block = event.getLevel().getBlockState(pos).getBlock();
        Player player = this.getOwningPlayer();

        if (this.isTamed() && distanceFrom(this.blockPosition(), event.getPos()) <= 20) {
            if (block == ModTrees.springTree.getSapling()) {
                QuestData.get((ServerPlayer) player).checkComplete(SpecialTask.INSTANCE, SpecialTaskAction.GROW_SPRING_TREE);
            }
            if (block == ModTrees.summerTree.getSapling()) {
                QuestData.get((ServerPlayer) player).checkComplete(SpecialTask.INSTANCE, SpecialTaskAction.GROW_SUMMER_TREE);
            }
            if (block == ModTrees.autumnTree.getSapling()) {
                QuestData.get((ServerPlayer) player).checkComplete(SpecialTask.INSTANCE, SpecialTaskAction.GROW_AUTUMN_TREE);
            }
            if (block == ModTrees.winterTree.getSapling()) {
                QuestData.get((ServerPlayer) player).checkComplete(SpecialTask.INSTANCE, SpecialTaskAction.GROW_WINTER_TREE);
            }
        }
    }

    public enum State {
        IDLE, FLYING, CASTING
    }
}
