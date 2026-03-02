package com.saphienyako.feywild.entity;

import com.saphienyako.feywild.config.ModConfig;
import com.saphienyako.feywild.data.BellsnickelItems;
import com.saphienyako.feywild.entity.base.FeyBase;
import com.saphienyako.feywild.entity.base.intereface.GroundEntity;
import com.saphienyako.feywild.entity.base.intereface.ITradeable;
import com.saphienyako.feywild.entity.goals.GroundIronPanicGoal;
import com.saphienyako.feywild.entity.goals.GroundPanicGoal;
import com.saphienyako.feywild.entity.goals.TradeForGemsGoal;
import com.saphienyako.feywild.item.ModItems;
import com.saphienyako.feywild.network.FeywildNetwork;
import com.saphienyako.feywild.network.OpenMenuMessage;
import com.saphienyako.feywild.network.ParticleMessage;
import com.saphienyako.feywild.screen.BellsnickelMenu;
import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.List;
import java.util.Random;

public class BellsnickelEntity extends FeyBase implements GroundEntity, ITradeable, ContainerListener, HasCustomInventoryScreen  {

    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(BellsnickelEntity.class, EntityDataSerializers.INT);

    public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(BellsnickelEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> HAS_BOOK =
            SynchedEntityData.defineId(BellsnickelEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> HAS_FEY_INK_BOTTLE =
            SynchedEntityData.defineId(BellsnickelEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> HAS_COPY =
            SynchedEntityData.defineId(BellsnickelEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> HAS_OUTPUT =
            SynchedEntityData.defineId(BellsnickelEntity.class, EntityDataSerializers.BOOLEAN);

    protected SimpleContainer inventory;

    private ItemStack[] lastInventoryCheck;
    public static final int INVENTORY_SIZE = 22; //2*9 + 4

    private final int BOOK_SLOT = 0;
    private final int FEY_INK_BOTTLE_SLOT = 1;
    private final int OUT_PUT_SLOT= 2;
    private final int COPY_SLOT= 3;

    public final AnimationState IDLE_ANIMATION = new AnimationState();
    public final AnimationState TRADE_ANIMATION = new AnimationState();

    public final AnimationState POSE_ANIMATION = new AnimationState();

    public final AnimationState WALK_ANIMATION = new AnimationState();

    private int movingTicks = 0;

    public static final double MIN_MOVING_SPEED_SQR = 1.0E-6;

    private BlockPos lanternLightPos;

    protected BellsnickelEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.noCulling = true;
        this.createInventory();
        this.entityData.set(VARIANT, BellsnickelVariant.DEFAULT.ordinal());
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void registerGoals() {
        super.registerGoals();
        this.registerGroundGoals(this);
        this.goalSelector.addGoal(0, new GroundPanicGoal(this));
        this.goalSelector.addGoal(1, new GroundIronPanicGoal(this, this.level, 0.25, 6));
        this.goalSelector.addGoal(10, new TemptGoal(this, 1.25, Ingredient.of(Items.COOKIE), false));
        this.goalSelector.addGoal(5, new TradeForGemsGoal(this));
    }

    public static AttributeSupplier.Builder getDefaultAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 12)
                .add(Attributes.MOVEMENT_SPEED, 0.10)
                .add(Attributes.LUCK, 0.2)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.FOLLOW_RANGE, 24D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(STATE,0);
        this.entityData.define(VARIANT, BellsnickelVariant.DEFAULT.ordinal());
        this.entityData.define(HAS_BOOK, false);
        this.entityData.define(HAS_FEY_INK_BOTTLE, false);
        this.entityData.define(HAS_COPY, false);
        this.entityData.define(HAS_OUTPUT, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("BellsnickelVariant", this.entityData.get(VARIANT));
        //Inventory
        ListTag listtag = new ListTag();
        for (int x = 0; x < this.inventory.getContainerSize(); x++) {
            ItemStack itemstack = this.inventory.getItem(x);
            if (!itemstack.isEmpty()) {
                CompoundTag compoundtag = new CompoundTag();
                compoundtag.putByte("Slot", (byte)(x));
                listtag.add(itemstack.save(compoundtag));
            }
        }
        nbt.put("Items", listtag);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        if (nbt.contains("BellsnickelVariant")) {
            this.entityData.set(VARIANT, nbt.getInt("BellsnickelVariant"));
        }
        //Inventory
        this.createInventory();
        ListTag listtag = nbt.getList("Items", 10); //TODO

        for (int x = 0; x < listtag.size(); x++) {
            CompoundTag compoundtag = listtag.getCompound(x);
            int j = compoundtag.getByte("Slot") & 255;
            if (j < this.inventory.getContainerSize()) {
                this.inventory.setItem(j, ItemStack.of(compoundtag));
            }
        }
    }

    @Override
    public boolean isDamageSourceBlocked(DamageSource damageSource) {
        Entity attacker = damageSource.getEntity();
        if (attacker instanceof LivingEntity living && !this.isTamed()) {
            living.setTicksFrozen(living.getTicksRequiredToFreeze());
            living.setTicksFrozen(living.getTicksFrozen() + 60);
            living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 140, 2));
            FeywildNetwork.sendParticles(this.level, ParticleMessage.Type.DANDELION_FLUFF, this.getOnPos().above());
        }

        return super.isDamageSourceBlocked(damageSource);
    }

    @SuppressWarnings("resource")
    @Override
    public void tick() {
        super.tick();
        if(this.level.isClientSide()) {
            setupAnimationStates();
            handleLanternLight();
        }
        if (level.isClientSide && this.getParticle() != null && random.nextInt(25) == 0) {
                level.addParticle(this.getParticle(),
                        this.getX() + (Math.random() - 0.5),
                        this.getY() + 1 + (Math.random() - 0.5),
                        this.getZ() + (Math.random() - 0.5),
                        0, 0, 0
                );

        }
    }

    private void handleLanternLight() {
        boolean shouldHaveLight = POSE_ANIMATION.isStarted() && this.isAlive();

        if (!shouldHaveLight) {
            removeLanternLight();
            return;
        }

        BlockPos newPos = this.blockPosition().above();

        if (newPos.equals(lanternLightPos)) {
            return;
        }

        removeLanternLight();

        if (level.getBlockState(newPos).isAir()) {
            level.setBlock(newPos, Blocks.LIGHT.defaultBlockState().setValue(LightBlock.LEVEL, 13), 3);
            lanternLightPos = newPos;
        }
    }

    private void removeLanternLight() {
        if (lanternLightPos != null) {
            if (level.getBlockState(lanternLightPos).is(Blocks.LIGHT)) {
                level.removeBlock(lanternLightPos, false);
            }
            lanternLightPos = null;
        }
    }



    @Override
    public void remove(RemovalReason reason) {
        if (!this.level.isClientSide) {
            if (reason == RemovalReason.KILLED || reason == RemovalReason.DISCARDED) {
                dropInventory();
            }

            removeLanternLight();
        }

        super.remove(reason);
    }


    private boolean isMoving() {
        return this.getDeltaMovement().horizontalDistanceSqr() > MIN_MOVING_SPEED_SQR;
    }

    private boolean isActuallyMoving() {
        if (isMoving()) {
            movingTicks = 5;
        } else {
            movingTicks = Math.max(0, movingTicks - 1);
        }
        return movingTicks > 0;
    }

    private void setupAnimationStates() {

        // SING
        if (getState() == State.TRADE) {
            if (!TRADE_ANIMATION.isStarted()) {
                TRADE_ANIMATION.start(this.tickCount);
            }
        } else {
            TRADE_ANIMATION.stop();
        }


        if (getState() != State.TRADE) {
            if (isActuallyMoving()) {
                if (!WALK_ANIMATION.isStarted()) {
                    WALK_ANIMATION.start(this.tickCount);
                    POSE_ANIMATION.start(this.tickCount);
                }
                IDLE_ANIMATION.stop();
            } else {
                if (!IDLE_ANIMATION.isStarted()) {
                    IDLE_ANIMATION.start(this.tickCount);
                    POSE_ANIMATION.start(this.tickCount);
                }
                WALK_ANIMATION.stop();

            }
        }
    }

    @SuppressWarnings("resource")
    @Nonnull
    @Override
    @OverridingMethodsMustInvokeSuper
    public InteractionResult interactAt(@Nonnull Player player, @Nonnull Vec3 hitVec, @Nonnull InteractionHand hand) {
        InteractionResult superResult = super.interactAt(player, hitVec, hand);
        if (superResult == InteractionResult.PASS) {

            //GIVE COOKIE, HEAL
            if (player.getItemInHand(hand).is(Items.COOKIE) && (this.getLastHurtByMob() == null || !this.getLastHurtByMob().isAlive())) {
                this.heal(3);
                if (!this.isTamed() && player instanceof ServerPlayer serverPlayer && this.owner == null) {
                    Random random = new Random();
                    if (random.nextInt(3) == 0) {
                        this.spawnAtLocation(new ItemStack(ModItems.FEY_DUST.get()));
                        this.playSound(SoundEvents.ENDERMAN_TELEPORT);
                        if(ModConfig.COMMON.voice_active.get()) {
                            serverPlayer.playNotifySound(
                                    this.getCookieSound(),
                                    SoundSource.NEUTRAL,
                                    1.0F,
                                    1.0F
                            );
                        }
                        this.discard();
                        player.sendSystemMessage(getFeyCookieMessage());
                    }
                }
                if (!player.isCreative()) {
                    player.getItemInHand(hand).shrink(1);
                }
                if (!level.isClientSide) {
                    FeywildNetwork.sendParticles(this.level, ParticleMessage.Type.FEY_HEART, this.getOnPos());
                }

                player.swing(hand, true);

                //NAME TAG
            } else if (player.getItemInHand(hand).getItem() == Items.NAME_TAG) {
                setCustomName(player.getItemInHand(hand).getHoverName().copy());
                setCustomNameVisible(true);
                if (!level.isClientSide) {
                    player.sendSystemMessage(getFeyNameMessage());
                    if(ModConfig.COMMON.voice_active.get() && this.getVoiceActive()) {
                        player.playNotifySound(
                                this.getNameSound(),
                                SoundSource.NEUTRAL,
                                1.0F,
                                1.0F
                        );
                    }
                }

                //PIXIE ORB OPENS BELLSNICKEL MENU
            } else if (player.getItemInHand(hand).getItem() == ModItems.PIXIE_ORB.get() && this.isTamed() && player instanceof ServerPlayer && this.owner != null && this.owner.equals(player.getUUID())) {
                FeywildNetwork.sendToPlayer(new OpenMenuMessage(
                                this.getName(),
                                this.getId(),
                                this.getAlignment(),
                                this.getFollowingPlayer(),
                                this.blockPosition(),
                                this.getAbilityActive(),
                                this.getVoiceActive()),
                        (ServerPlayer) player);
                player.swing(hand, true);
            } //UNTAMED MESSAGE
            else if (!this.isTamed() || !player.getUUID().equals(this.owner)) {
                if (player instanceof ServerPlayer serverPlayer) {
                    player.displayClientMessage(
                            Component.translatable("message.feywild.pixie_whisper")
                                    .withStyle(ChatFormatting.LIGHT_PURPLE)
                                    .append(Component.translatable("message.feywild.pixie_orb_untamed").withStyle(ChatFormatting.ITALIC)),
                            true
                    );
                }
                player.swing(hand, true);
            }
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        } else {
            return superResult;
        }
    }

    //INVENTORY

    protected void createInventory() {
        SimpleContainer simplecontainer = this.inventory;
        this.inventory = new SimpleContainer(this.getInventorySize());
        if (simplecontainer != null) {
            simplecontainer.removeListener(this);
            int i = Math.min(simplecontainer.getContainerSize(), this.inventory.getContainerSize());

            for (int j = 0; j < i; j++) {
                ItemStack itemstack = simplecontainer.getItem(j);
                if (!itemstack.isEmpty()) {
                    this.inventory.setItem(j, itemstack.copy());
                }
            }
        }

        this.lastInventoryCheck = new ItemStack[this.inventory.getContainerSize()];
        for (int i = 0; i < this.inventory.getContainerSize(); i++) {
            this.lastInventoryCheck[i] = this.inventory.getItem(i).copy();
        }

        this.inventory.addListener(this);
    }

    @Override
    public void containerChanged(@Nonnull Container container) {
        this.entityData.set(HAS_BOOK,
                container.getItem(BOOK_SLOT).is(Items.BOOK));

        this.entityData.set(HAS_FEY_INK_BOTTLE,
                container.getItem(FEY_INK_BOTTLE_SLOT).is(ModItems.FEY_INK_BOTTLE.get()));

        this.entityData.set(HAS_COPY,
                container.getItem(COPY_SLOT).is(Items.ENCHANTED_BOOK));

        this.entityData.set(HAS_OUTPUT,
                container.getItem(OUT_PUT_SLOT).is(Items.ENCHANTED_BOOK));


        for (int slot = 4; slot < container.getContainerSize(); slot++) {
            ItemStack oldStack = lastInventoryCheck[slot];
            ItemStack newStack = container.getItem(slot);

            boolean added = oldStack.isEmpty() && !newStack.isEmpty() || (!oldStack.isEmpty() && !newStack.isEmpty() && newStack.getCount() > oldStack.getCount());

            if (added && this.random.nextFloat() < 0.1F) {
                level.playSound(null,
                        this.blockPosition(),
                        ModSounds.BELLSNICKEL_CARRY_STUFF.get(),
                        SoundSource.NEUTRAL,
                        0.6F, 1.0F);
                break;
            }
        }

        for (int i = 0; i < container.getContainerSize(); i++) {
            lastInventoryCheck[i] = container.getItem(i).copy();
        }

        updateOutputSlot();
    }

    @Override
    public void openCustomInventoryScreen(@Nonnull Player player) {
        if (!this.level.isClientSide && this.isTamed()) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            if (player.containerMenu != player.inventoryMenu) {
                player.closeContainer();
            }

            NetworkHooks.openScreen(
                    serverPlayer,
                    new SimpleMenuProvider(
                            (ix, playerInventory, playerEntity) ->
                                    new BellsnickelMenu(ix, playerInventory, this.inventory, this),
                            this.getDisplayName()
                    ),
                    buf -> buf.writeUUID(this.getUUID())
            );
        }
    }

    public final int getInventorySize() {
        return getInventorySize(2);
    }

    public static int getInventorySize(int columns) {
        return INVENTORY_SIZE;
    }

    private void dropInventory() {
        if (this.level.isClientSide || this.inventory == null) {
            return;
        }

        for (int i = 0; i < this.inventory.getContainerSize(); i++) {
            ItemStack stack = this.inventory.getItem(i);
            if (!stack.isEmpty()) {
                this.spawnAtLocation(stack);
            }
        }

        this.inventory.clearContent();
    }

    private void updateOutputSlot() {
        if (level.isClientSide || inventory == null) return;

        inventory.removeListener(this);

        ItemStack book = inventory.getItem(BOOK_SLOT);
        ItemStack ink = inventory.getItem(FEY_INK_BOTTLE_SLOT);
        ItemStack copyBook = inventory.getItem(COPY_SLOT);
        ItemStack out_put = inventory.getItem(OUT_PUT_SLOT);

        if (book.is(Items.BOOK) && ink.is(ModItems.FEY_INK_BOTTLE.get()) && copyBook.is(Items.ENCHANTED_BOOK) && !out_put.is(Items.ENCHANTED_BOOK)) {
            ItemStack output = copyBook.copy();
            output.setCount(1);
            inventory.setItem(OUT_PUT_SLOT, output);
            book.shrink(1);
            ink.shrink(1);

            level.playSound(null, this.blockPosition(), SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0F, 1.0F);
            if (this.random.nextFloat() < 0.3F) {level.playSound(null, this.blockPosition(), ModSounds.BELLSNICKEL_AMBIANCE.get(), SoundSource.NEUTRAL, 0.6F, 1.0F);
            }
        }

        inventory.addListener(this);
    }


    //Particles and Sounds

    @Nullable
    @Override
    public SimpleParticleType getParticle() {
        return ParticleTypes.SOUL_FIRE_FLAME;
    }


    @Override
    public Alignment getAlignment() {
        return Alignment.WINTER;
    }

    @Override
    public ItemLike getDismissItem() {
        return ModItems.SUMMONING_SCROLL_BELLSNICKEL.get();
    }

    @Override
    public SoundEvent getCookieSound() {
        return ModSounds.BELLSNICKEL_COOKIE.get();
    }

    @Override
    public SoundEvent getNameSound() {
        return ModSounds.BELLSNICKEL_NAME.get();
    }

    @Override
    public SoundEvent getSummonSound() {
        return ModSounds.BELLSNICKEL_SUMMON.get();
    }

    @Override
    public SoundEvent getDismissSound() {
        return ModSounds.BELLSNICKEL_DISMISS.get();
    }

    @Override
    public SoundEvent getFollowSound() {
        return ModSounds.BELLSNICKEL_FOLLOW.get();
    }

    @Override
    public SoundEvent getStaySound() {
        Random random = new Random();
            if(random.nextInt(4) == 0) return ModSounds.BELLSNICKEL_STAY_02.get();
            else return ModSounds.BELLSNICKEL_STAY_01.get();
    }

    @Override
    public SoundEvent getAbilityOnSound() {
        return ModSounds.BELLSNICKEL_CARRY_STUFF.get();
    }

    @Override
    public SoundEvent getAbilityOffSound() {
        return ModSounds.BELLSNICKEL_CARRY_STUFF.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource source) {
        return ModSounds.BELLSNICKEL_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.BELLSNICKEL_DEATH.get();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        Random random = new Random();
        if (random.nextFloat() < 0.1f) {
            return ModSounds.BELLSNICKEL_AMBIANCE.get();
        } else return null;
    }

    @Override
    public SoundEvent getTradeSound() {
        return ModSounds.BELLSNICKEL_TRADE.get();
    }

    @Override
    public ItemStack getTradeItem() {
        return ModItems.FEY_GEM.get().getDefaultInstance();
    }

    @Override
    public boolean isTradeItem(ItemStack stack) {
        return stack.is(ModItems.FEY_GEM.get());
    }

    @Override
    public ItemStack getTradeResult() {
        List<ItemStack> items = BellsnickelItems.bellsnickelItems();
        if (items.isEmpty()) return ItemStack.EMPTY;

        Random random = new Random();
        int index = random.nextInt(items.size());
        return items.get(index).copy();
    }

    public State getState() {
        State[] states = State.values();
        return states[Mth.clamp(this.entityData.get(STATE), 0, states.length - 1)];
    }


    public void setState(State state) {
        this.entityData.set(STATE, state.ordinal());
    }


    public BellsnickelVariant getVariant() {
        return BellsnickelVariant.values()[this.entityData.get(VARIANT)];
    }

    public void setVariant(BellsnickelVariant variant) {this.entityData.set(VARIANT, variant.ordinal());}



    public enum State {
        IDLE, POSE, WALK, TRADE
    }

    public enum BellsnickelVariant {
        DEFAULT, RED_CAP, LORE_MASTER
    }
}
