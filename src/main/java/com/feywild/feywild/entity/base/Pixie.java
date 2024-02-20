package com.feywild.feywild.entity.base;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.ability.Ability;
import com.feywild.feywild.entity.goals.FeywildPanicGoal;
import com.feywild.feywild.entity.goals.TameCheckingGoal;
import com.feywild.feywild.entity.goals.pixie.AbilityGoal;
import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.item.RuneStone;
import com.feywild.feywild.network.ParticleMessage;
import com.feywild.feywild.network.quest.OpenQuestDisplayMessage;
import com.feywild.feywild.network.quest.OpenQuestSelectionMessage;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.QuestDisplay;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.quest.task.FeyGiftTask;
import com.feywild.feywild.quest.util.AlignmentStack;
import com.feywild.feywild.quest.util.SelectableQuest;
import com.feywild.feywild.tag.ModItemTags;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import org.moddingx.libx.util.data.NbtX;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public abstract class Pixie extends FlyingFeyBase {

    public static final int MAX_BOREDOM = 5;

    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(Pixie.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> BOREDOM = SynchedEntityData.defineId(Pixie.class, EntityDataSerializers.INT);

    private final Alignment alignment;

    // private int boredom;
    private Ability<?> ability;

    protected Pixie(EntityType<? extends Pixie> type, @Nonnull Alignment alignment, Level level) {
        super(type, Objects.requireNonNull(alignment), level);
        this.alignment = alignment;
    }

    @Nonnull
    @Override
    public Alignment alignment() {
        return this.alignment;
    }

    @Override
    public boolean canFollowPlayer() {
        return true;
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(20, new AbilityGoal(this));
        this.goalSelector.addGoal(50, new FeywildPanicGoal(this, 0.003, 13));
        this.goalSelector.addGoal(10, new TameCheckingGoal(this, false, new TemptGoal(this, 1.25, Ingredient.of(Items.COOKIE), false)));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(STATE, 0);
        this.entityData.define(BOREDOM, 0);
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
            } else if (player.getItemInHand(hand).is(ModItemTags.COOKIES) && (this.getLastHurtByMob() == null || !this.getLastHurtByMob().isAlive())) {
                this.heal(MAX_BOREDOM);
                this.setBoredom(0);
                if (!this.isTamed() && player instanceof ServerPlayer && this.owner == null) {
                    Random random = new Random();
                    if (random.nextInt(4) == 0) {
                        this.spawnAtLocation(new ItemStack(ModItems.feyDust));
                        this.playSound(SoundEvents.ENDERMAN_TELEPORT);
                        this.discard();
                        player.sendSystemMessage(Component.translatable("message.feywild." + this.alignment.id + "_feed"));
                    }
                }
                if (!player.isCreative()) {
                    player.getItemInHand(hand).shrink(1);
                }
                FeywildMod.getNetwork().sendParticles(this.level(), ParticleMessage.Type.FEY_HEART, this.getX(), this.getY() + 1, this.getZ());
                player.swing(hand, true);
            } else if (player.getItemInHand(hand).getItem() == Items.NAME_TAG) {
                setCustomName(player.getItemInHand(hand).getHoverName().copy());
                setCustomNameVisible(true);
                if (!level().isClientSide) {
                    player.sendSystemMessage(Component.translatable("message.feywild." + this.alignment.id + "_fey_name"));
                }
            } else if (!this.isTamed() && this.getOwner() == null && player instanceof ServerPlayer && player.getItemInHand(hand).getItem() == Items.ENDER_EYE) {
                player.swing(player.getUsedItemHand(), true);
                if (!player.isCreative()) {
                    player.getItemInHand(hand).shrink(1);
                }
                player.addItem(new ItemStack(ModItems.pixieOrb));
                this.remove(RemovalReason.DISCARDED);
            } else if (this.isTamed() && player instanceof ServerPlayer && this.owner != null && this.owner.equals(player.getUUID())
                    && player.getItemInHand(hand).getItem() instanceof RuneStone runeStone) {
                player.swing(player.getUsedItemHand(), true);
                if (!player.isCreative()) {
                    player.getItemInHand(hand).shrink(1);
                    player.addItem(new ItemStack(runeStone.replacedBy));
                }
                this.setAbility(runeStone.ability);
                if (!level().isClientSide) {
                    player.sendSystemMessage(Component.translatable("message.feywild.pixie.ability.gain"));
                }
            } else if (this.isTamed() && player instanceof ServerPlayer && this.owner != null && this.owner.equals(player.getUUID())) {
                if (stack.isEmpty() && !player.isShiftKeyDown()) {
                    this.interactQuest((ServerPlayer) player, hand);
                }
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            return superResult;
        }
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

    public Pixie.State getState() {
        Pixie.State[] states = Pixie.State.values();
        return states[Mth.clamp(this.entityData.get(STATE), 0, states.length - 1)];
    }

    public void setState(Pixie.State state) {
        this.entityData.set(STATE, state.ordinal());
    }

    public int getBoredom() {
        return this.entityData.get(BOREDOM);
    }

    public void setBoredom(int boredom) {
        this.entityData.set(BOREDOM, boredom);
    }

    public void adjustBoredom(int adjustment) {
        this.entityData.set(BOREDOM, getBoredom() + adjustment);
        if (this.getBoredom() >= 5 && getOwningPlayer() != null) {
            //TODO add different versions
            this.getOwningPlayer().sendSystemMessage(Component.translatable("message.feywild." + this.alignment.id + "_bored"));
        }
    }

    protected abstract Ability<?> getDefaultAbility();

    public Ability<?> getAbility() {
        if (this.ability == null) this.ability = this.getDefaultAbility();
        return this.ability;
    }

    public void setAbility(Ability<?> ability) {
        this.ability = ability;
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        NbtX.putResource(nbt, "PixieAbility", this.getAbility().id());
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setAbility(Ability.get(NbtX.getResource(nbt, "PixieAbility"), this.getDefaultAbility()));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, event -> {
            if (!this.dead && !this.isDeadOrDying()) {
                if (this.getState() == State.CASTING) {
                    event.getController().setAnimation(RawAnimation.begin().thenLoop("spellcasting"));
                    return PlayState.CONTINUE;
                }
            }

            if (event.isMoving()) {
                event.getController().setAnimation(RawAnimation.begin().thenLoop("fly"));
            } else {
                event.getController().setAnimation(RawAnimation.begin().thenLoop("idle"));
            }
            return PlayState.CONTINUE;
        }));
    }

    public enum State {
        IDLE, FLYING, CASTING
    }
}
