package com.saphienyako.feywild.entity;

import com.saphienyako.feywild.config.FeywildConfig;
import com.saphienyako.feywild.entity.base.FlyingFeyBase;
import com.saphienyako.feywild.entity.base.intereface.ITradeable;
import com.saphienyako.feywild.entity.goals.TradeForGemsGoal;
import com.saphienyako.feywild.item.ModItems;
import com.saphienyako.feywild.network.OpenMenuMessage;
import com.saphienyako.feywild.network.ParticleMessage;
import com.saphienyako.feywild.screen.BeeKnightMenu;
import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Position;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
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
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.Random;
import java.util.UUID;

public class BeeMountEntity extends FlyingFeyBase implements ITradeable, ContainerListener, HasCustomInventoryScreen{
    //MOVEMENT
    //FOLLOW/STAY on FeyBase
    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(BeeMountEntity.class, EntityDataSerializers.INT);

    public static final EntityDataAccessor<Integer> KNIGHT_ID =
            SynchedEntityData.defineId(BeeMountEntity.class, EntityDataSerializers.INT);

    public final AnimationState FLY_ANIMATION = new AnimationState();
    public final AnimationState FLY_IDLE_ANIMATION = new AnimationState();
    private UUID knightUUID; //LINK BEE KNIGHT
    private int movingTicks = 0;
    public static final double MIN_MOVING_SPEED_SQR = 1.0E-6;

    public boolean isBeingRemovedTogether = false;

    public static final EntityDataAccessor<Boolean> MOUNT_HAS_GOLD_ARMOR =
            SynchedEntityData.defineId(BeeMountEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> MOUNT_HAS_DIAMOND_ARMOR =
            SynchedEntityData.defineId(BeeMountEntity.class, EntityDataSerializers.BOOLEAN);

    public static final EntityDataAccessor<Boolean> KNIGHT_HAS_GOLD_ARMOR =
            SynchedEntityData.defineId(BeeMountEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> KNIGHT_HAS_DIAMOND_ARMOR =
            SynchedEntityData.defineId(BeeMountEntity.class, EntityDataSerializers.BOOLEAN);

    public static final EntityDataAccessor<Boolean> KNIGHT_HAS_NETHERITE_ARMOR =
            SynchedEntityData.defineId(BeeMountEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> KNIGHT_HAS_GOLD_LANCE =
            SynchedEntityData.defineId(BeeMountEntity.class, EntityDataSerializers.BOOLEAN);

    public static final EntityDataAccessor<Boolean> KNIGHT_HAS_DIAMOND_LANCE =
            SynchedEntityData.defineId(BeeMountEntity.class, EntityDataSerializers.BOOLEAN);

    public static final EntityDataAccessor<Boolean> KNIGHT_HAS_NETHERITE_LANCE =
            SynchedEntityData.defineId(BeeMountEntity.class, EntityDataSerializers.BOOLEAN);

    public static final EntityDataAccessor<Boolean> HAS_MAGICAL_HONEY_COMB =
            SynchedEntityData.defineId(BeeMountEntity.class, EntityDataSerializers.BOOLEAN);


    protected SimpleContainer inventory;

    private ItemStack[] lastInventoryCheck;
    public static final int INVENTORY_SIZE = 4; //mount armor, knight armor, lance, honey comb

    private final int MOUNT_ARMOR_SLOT = 0;
    private final int KNIGHT_ARMOR_SLOT = 1;
    private final int LANCE_SLOT= 2;
    private final int MAGICAL_HONEY_COMB_SLOT= 3;



    protected BeeMountEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.createInventory();
    }


    @Override
    @OverridingMethodsMustInvokeSuper
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(10, new TemptGoal(this, 1.25, Ingredient.of(Items.COOKIE), false));
        this.goalSelector.addGoal(5, new TradeForGemsGoal(this));
    }


    //TODO Determines Stats
    public static AttributeSupplier.Builder getDefaultAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.FLYING_SPEED, 0.35)
                .add(Attributes.MAX_HEALTH, 12)
                .add(Attributes.MOVEMENT_SPEED, 0.35)
                .add(Attributes.LUCK, 0.2)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.FOLLOW_RANGE, 24D);


    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(STATE,0);
        builder.define(KNIGHT_ID, -1);
        builder.define(MOUNT_HAS_GOLD_ARMOR, false);
        builder.define(MOUNT_HAS_DIAMOND_ARMOR, false);
        builder.define(KNIGHT_HAS_GOLD_ARMOR, false);
        builder.define(KNIGHT_HAS_DIAMOND_ARMOR, false);
        builder.define(KNIGHT_HAS_NETHERITE_ARMOR, false);
        builder.define(KNIGHT_HAS_GOLD_LANCE, false);
        builder.define(KNIGHT_HAS_DIAMOND_LANCE, false);
        builder.define(KNIGHT_HAS_NETHERITE_LANCE, false);
        builder.define(HAS_MAGICAL_HONEY_COMB, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);

        if (this.knightUUID != null) {
            tag.putUUID("KnightUUID", this.knightUUID);
        }
        //Inventory
        ListTag listtag = new ListTag();
        for (int x = 0; x < this.inventory.getContainerSize(); x++) {
            ItemStack itemstack = this.inventory.getItem(x);
            if (!itemstack.isEmpty()) {
                CompoundTag compoundtag = new CompoundTag();
                compoundtag.putByte("Slot", (byte)(x));
                listtag.add(itemstack.save(this.registryAccess(), compoundtag));
            }
        }
        tag.put("Items", listtag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);

        if (tag.hasUUID("KnightUUID")) {
            this.knightUUID = tag.getUUID("KnightUUID");
        }
        //Inventory
        this.createInventory();
        ListTag listtag = tag.getList("Items", 10);

        for (int x = 0; x < listtag.size(); x++) {
            CompoundTag compoundtag = listtag.getCompound(x);
            int j = compoundtag.getByte("Slot") & 255;
            if (j < this.inventory.getContainerSize()) {
                this.inventory.setItem(j, ItemStack.parse(this.registryAccess(), compoundtag).orElse(ItemStack.EMPTY));
            }
        }
    }


    @Override
    public void tick() {
        super.tick();
        if(this.level().isClientSide()) {
            setupAnimationStates();
        }

        if (this.level().isClientSide) return;

        if (this.getPassengers().isEmpty()) {

            BeeKnightEntity knight = this.getLinkedKnight();

            if (knight == null) {
               knight = this.spawnKnight();

                if (knight != null) {
                    this.knightUUID = knight.getUUID();
                    this.entityData.set(KNIGHT_ID, knight.getId());
                }
            }

            if (knight != null && !knight.isPassenger()) {
                knight.startRiding(this, true);
            }
        }
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
        if (isActuallyMoving()) {
            if (!FLY_ANIMATION.isStarted()) {
               FLY_ANIMATION.start(this.tickCount);
            }
            FLY_IDLE_ANIMATION.stop();
        } else {
            if (!FLY_IDLE_ANIMATION.isStarted()) {
                FLY_IDLE_ANIMATION.start(this.tickCount);
            }
           FLY_ANIMATION.stop();
        }
    }

    //LINKING KNIGHT
    @Nonnull
    public BeeKnightEntity getLinkedKnight() {
        int id = this.entityData.get(KNIGHT_ID);
        if (id == -1) return null;

        Entity entity = this.level().getEntity(id);
        return entity instanceof BeeKnightEntity knight ? knight : null;
    }

    private BeeKnightEntity spawnKnight() {
        BeeKnightEntity knight = ModEntities.BEE_KNIGHT.get().create(this.level());

        if (knight != null) {
            knight.moveTo(this.position());
            this.level().addFreshEntity(knight);
        }
        return knight;

    }

    //INTERACT

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
                        if(FeywildConfig.voicesActive) {
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

                if (!level().isClientSide) {
                    PacketDistributor.sendToPlayersTrackingEntity(
                            this,
                            new ParticleMessage(
                                    ParticleMessage.Particles.FEY_HEART,
                                    this.getOnPos().above()
                            )
                    );
                }

                player.swing(hand, true);

                //NAME TAG ON MOUNT
            } else if (player.getItemInHand(hand).getItem() == Items.NAME_TAG) {
                setCustomName(player.getItemInHand(hand).getHoverName().copy());
                setCustomNameVisible(true);
                if (!level().isClientSide) {
                    player.sendSystemMessage(getFeyNameMessage());
                    if (FeywildConfig.voicesActive && this.getVoiceActive()) {
                        player.playNotifySound(
                                this.getNameSound(),
                                SoundSource.NEUTRAL,
                                1.0F,
                                1.0F
                        );
                    }
                }

                //PIXIE ORB OPENS BEE MOUNT MENU
            }  else if (player.getItemInHand(hand).getItem() == ModItems.PIXIE_ORB.get() && this.isTamed() && player instanceof ServerPlayer && this.owner != null && this.owner.equals(player.getUUID())) {
                //openCustomInventoryScreen(player);
                PacketDistributor.sendToPlayer(
                        (ServerPlayer)player,
                        new OpenMenuMessage(
                                this.getId(),
                                this.getAlignment(),
                                this.getFollowingPlayer(),
                                this.blockPosition(),
                                this.getAbilityActive(),
                                this.getVoiceActive()
                        )
                );
                player.swing(hand, true);
            } else if (!this.isTamed() || !player.getUUID().equals(this.owner)) {
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
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            return superResult;
        }
    }


    //INVENTORY

    protected void createInventory() {
        SimpleContainer simplecontainer = this.inventory;
        this.inventory = new SimpleContainer(INVENTORY_SIZE);
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
        this.entityData.set(MOUNT_HAS_GOLD_ARMOR,
                container.getItem(MOUNT_ARMOR_SLOT).is(Items.GOLDEN_HORSE_ARMOR));

        this.entityData.set(MOUNT_HAS_DIAMOND_ARMOR,
                container.getItem(MOUNT_ARMOR_SLOT).is(Items.DIAMOND_HORSE_ARMOR));

        this.entityData.set(KNIGHT_HAS_GOLD_ARMOR,
                container.getItem(KNIGHT_ARMOR_SLOT).is(Items.GOLDEN_CHESTPLATE));

        this.entityData.set(KNIGHT_HAS_DIAMOND_ARMOR,
                container.getItem(KNIGHT_ARMOR_SLOT).is(Items.DIAMOND_CHESTPLATE));

        this.entityData.set(KNIGHT_HAS_NETHERITE_ARMOR,
                container.getItem(KNIGHT_ARMOR_SLOT).is(Items.NETHERITE_CHESTPLATE));

        this.entityData.set(KNIGHT_HAS_GOLD_LANCE,
                container.getItem(LANCE_SLOT).is(Items.GOLDEN_SWORD)); //TODO custom Item

        this.entityData.set(KNIGHT_HAS_DIAMOND_LANCE,
                container.getItem(LANCE_SLOT).is(Items.DIAMOND_SWORD));

        this.entityData.set(KNIGHT_HAS_NETHERITE_LANCE,
                container.getItem(LANCE_SLOT).is(Items.NETHERITE_SWORD));

        this.entityData.set(HAS_MAGICAL_HONEY_COMB,
                container.getItem(MAGICAL_HONEY_COMB_SLOT).is(Items.HONEYCOMB)); //TODO custom Item

        for (int i = 0; i < container.getContainerSize(); i++) {
            lastInventoryCheck[i] = container.getItem(i).copy();
        }
         //  updateOutputSlot();
    }

    @Override
    public void openCustomInventoryScreen(@Nonnull Player player) {
        if (!this.level().isClientSide && this.isTamed()) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            if (player.containerMenu != player.inventoryMenu) {
                player.closeContainer();
            }
            //TODO BeeKnight Menu requires both UUID of Mount and Bee Knight
            serverPlayer.openMenu(new SimpleMenuProvider((ix, playerInventory, playerEntity) ->
                    new BeeKnightMenu(ix, playerInventory, this.inventory, this), this.getDisplayName()), buf -> {
                buf.writeUUID(getUUID());
            });
        }
    }

    @Override
    public void remove(RemovalReason reason) {
        if (!this.level().isClientSide && !this.isBeingRemovedTogether) {
            this.isBeingRemovedTogether = true;

            BeeKnightEntity knight = getLinkedKnight();
            if (knight != null && !knight.isRemoved()) {
                knight.isBeingRemovedTogether = true;
                knight.remove(reason);
            }

            dropInventory();
        }

        super.remove(reason);
    }

    private void dropInventory() {
        if (this.level().isClientSide || this.inventory == null) {
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
        if (level().isClientSide || inventory == null) return;

        inventory.removeListener(this);

        ItemStack mount_armor = inventory.getItem(MOUNT_ARMOR_SLOT);
        ItemStack knight_armor = inventory.getItem(KNIGHT_ARMOR_SLOT);
        ItemStack lance = inventory.getItem(LANCE_SLOT);
        ItemStack honey_comb = inventory.getItem(MAGICAL_HONEY_COMB_SLOT);

        //Do something

        inventory.addListener(this);
    }

    //MOUNT/TRAVEL

    @Override
    public boolean isControlledByLocalInstance() {
        return false;
    }

    @Override
    public boolean isEffectiveAi() {
        return true;
    }

    @Override
    public LivingEntity getControllingPassenger() {
        if (this.getFirstPassenger() instanceof LivingEntity living) {
            return living;
        }
        return null;
    }

    @Override
    public void travel(Vec3 travelVector) {
        LivingEntity controller = this.getControllingPassenger();

        if (this.isVehicle() && controller != null) {
            this.setYRot(controller.getYRot());
            this.yRotO = this.getYRot();

            float forward = controller.zza;
            float strafe = controller.xxa;

            float speed = (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED);

            Vec3 input = new Vec3(strafe, 0, forward);
            input = input.scale(speed);
            System.out.println("Delta: " + this.getDeltaMovement());
            super.travel(input);
            return;
        }

        super.travel(travelVector);
    }

    @Override
    public void rideTick() {
        super.rideTick();

        if (this.isVehicle()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0, 0, 0));
            this.flyingTravel(this, Vec3.ZERO);
        }
    }

    @Override
    protected void positionRider(Entity passenger, Entity.MoveFunction moveFunction) {
        if (this.hasPassenger(passenger)) {
            double yOffset = 0.45D;
            moveFunction.accept(passenger, this.getX(), this.getY() + yOffset, this.getZ());
        }
    }


    @Override
    public boolean isVehicle() {
        return false;
    }

    @Override
    public Alignment getAlignment() {
        return Alignment.SUMMER;
    }

    @Override
    public ItemLike getDismissItem() {
        return ModItems.SUMMONING_SCROLL_BEE_KNIGHT.get();
    }

    //TODO SOUND IF HAS_BEE_KNIGHT RETURN BEE KNIGHT SOUND ELSE RETURN BEE SOUND BUZZ BUZZ

    @Override
    public SoundEvent getCookieSound() {
        return ModSounds.BEE_KNIGHT_COOKIE.get();
    }

    @Override
    public SoundEvent getNameSound() {
        return SoundEvents.BEE_LOOP;
    }

    @Override
    public SoundEvent getSummonSound() {
        return ModSounds.BEE_KNIGHT_SUMMON.get();
    }

    @Override
    public SoundEvent getDismissSound() {
        return ModSounds.BEE_KNIGHT_DISMISS.get();
    }

    @Override
    public SoundEvent getFollowSound() {
        return ModSounds.BEE_KNIGHT_FOLLOW.get();
    }

    @Override
    public SoundEvent getStaySound() {
        return ModSounds.BEE_KNIGHT_STAY.get();
    }

    public SoundEvent getProtectSound() {return ModSounds.BEE_KNIGHT_PROTECT.get();}

    public SoundEvent getGuardSound() {return ModSounds.BEE_KNIGHT_GUARD.get();}

    @Override
    public SoundEvent getAbilityOnSound() {
        return ModSounds.BEE_KNIGHT_PROTECT.get();
    }

    @Override
    public SoundEvent getAbilityOffSound() {
        return ModSounds.BEE_KNIGHT_STAY.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.BEE_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BEE_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.BEE_LOOP;
    }

    @Override
    public int getAmbientSoundInterval() {
        return 400;
    }

    public BeeKnightEntity.State getState() {
        BeeKnightEntity.State[] states = BeeKnightEntity.State.values();
        return states[Mth.clamp(this.entityData.get(STATE), 0, states.length - 1)];
    }

    public void setState(BeeKnightEntity.State state) {
        this.entityData.set(STATE, state.ordinal());
    }

    @Override
    public SoundEvent getTradeSound() {
        return null;
    }

    @Override
    public ItemStack getTradeItem() {
        return null;
    }

    @Override
    public boolean isTradeItem(ItemStack stack) {
        return false;
    }

    @Override
    public ItemStack getTradeResult() {
        return null;
    }

    public enum State {
        FLY, FLY_IDLE
    }
}
