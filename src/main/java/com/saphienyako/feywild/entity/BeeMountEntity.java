package com.saphienyako.feywild.entity;

import com.google.common.collect.Multimap;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.config.FeywildConfig;
import com.saphienyako.feywild.data.BellsnickelItems;
import com.saphienyako.feywild.entity.base.FlyingFeyBase;
import com.saphienyako.feywild.entity.base.intereface.ITradeable;
import com.saphienyako.feywild.entity.goals.*;
import com.saphienyako.feywild.item.ModItems;
import com.saphienyako.feywild.network.OpenMenuMessage;
import com.saphienyako.feywild.network.ParticleMessage;
import com.saphienyako.feywild.screen.BeeKnightMenu;
import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class BeeMountEntity extends FlyingFeyBase implements ContainerListener, HasCustomInventoryScreen{
    //MOVEMENT
    //FOLLOW/STAY on FeyBase
    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(BeeMountEntity.class, EntityDataSerializers.INT);

    public static final EntityDataAccessor<Integer> KNIGHT_ID =
            SynchedEntityData.defineId(BeeMountEntity.class, EntityDataSerializers.INT);

    public final AnimationState FLY_ANIMATION = new AnimationState();
    public final AnimationState FLY_IDLE_ANIMATION = new AnimationState();
    private UUID knightUUID;
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
    public static final EntityDataAccessor<Boolean> KNIGHT_HAS_GOLD_SPEAR =
            SynchedEntityData.defineId(BeeMountEntity.class, EntityDataSerializers.BOOLEAN);

    public static final EntityDataAccessor<Boolean> KNIGHT_HAS_DIAMOND_SPEAR =
            SynchedEntityData.defineId(BeeMountEntity.class, EntityDataSerializers.BOOLEAN);

    public static final EntityDataAccessor<Boolean> KNIGHT_HAS_NETHERITE_SPEAR =
            SynchedEntityData.defineId(BeeMountEntity.class, EntityDataSerializers.BOOLEAN);


    protected SimpleContainer inventory;

    private ItemStack[] lastInventoryCheck;
    public static final int INVENTORY_SIZE = 3; //mount armor, knight armor, lance

    private final int MOUNT_ARMOR_SLOT = 0;
    private final int KNIGHT_ARMOR_SLOT = 1;
    private final int SPEAR_SLOT = 2;

    protected BeeMountEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.createInventory();
    }


    @Override
    @OverridingMethodsMustInvokeSuper
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(10, new TemptGoal(this, 1.25, Ingredient.of(Items.COOKIE), false));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(30, new LookAtPlayerGoal(this, Player.class, 8f));

        this.getNavigation().setCanFloat(true);
    }


    //TODO Determines Stats
    public static AttributeSupplier.Builder getDefaultAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.FLYING_SPEED, 0.35)
                .add(Attributes.MAX_HEALTH, 24)
                .add(Attributes.MOVEMENT_SPEED, 0.35)
                .add(Attributes.LUCK, 0.2)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.ARMOR, 0.0)
                .add(Attributes.ARMOR_TOUGHNESS, 0.0);
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
        builder.define(KNIGHT_HAS_GOLD_SPEAR, false);
        builder.define(KNIGHT_HAS_DIAMOND_SPEAR, false);
        builder.define(KNIGHT_HAS_NETHERITE_SPEAR, false);
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
    public boolean isDamageSourceBlocked(DamageSource damageSource) {
        if (this.isBlocking() && !damageSource.is(DamageTypeTags.BYPASSES_SHIELD)) {
            Vec3 vec32 = damageSource.getSourcePosition();
            if (vec32 != null) {
                Vec3 vec3 = this.calculateViewVector(0.0F, this.getYHeadRot());
                Vec3 vec31 = vec32.vectorTo(this.position());
                vec31 = new Vec3(vec31.x, 0.0, vec31.z).normalize();
                return vec31.dot(vec3) < 0.0;
            }
        }

        Entity attacker = damageSource.getEntity();
        if(this.isTamed() && attacker instanceof Player player && player == getOwningPlayer()) {
                ItemStack held = player.getMainHandItem();
                if (!isIronTool(held)) {
                    return true;
                }
        }

        return false;
    }


    @Override
    public void tick() {
        super.tick();
        if(this.level().isClientSide()) {
            setupAnimationStates();
        }

        if (!this.level().isClientSide && this.getNavigation().isDone()) {
            Vec3 wander = this.position().add(
                    (random.nextDouble() - 0.5) * 10,
                    0,
                    (random.nextDouble() - 0.5) * 10
            );

            this.getNavigation().moveTo(wander.x, wander.y, wander.z, 1.0);
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
            if(this.isTamed()){
                    knight.setSummonPos(this.getSummonPos());
                    knight.setOwner(Objects.requireNonNull(this.getOwner()));
            }
            this.level().addFreshEntity(knight);
            this.setAbilityActive(true);
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

    private boolean isIronWeaponOrArmor(ItemStack stack) {
        return stack.is(Items.IRON_SWORD) || stack.is(Items.IRON_CHESTPLATE);
    }

    @Override
    public void containerChanged(@Nonnull Container container) {

        if(container.getItem(KNIGHT_ARMOR_SLOT).is(Items.IRON_CHESTPLATE)){
            BeeKnightEntity knight = getLinkedKnight();
            knight.hurt(this.damageSources().generic(), 1.0F);
            knight.playSound(ModSounds.BEE_KNIGHT_HURT.get(), 1.0F, 1.0F);

            this.spawnAtLocation(container.getItem(KNIGHT_ARMOR_SLOT));
            container.setItem(KNIGHT_ARMOR_SLOT, ItemStack.EMPTY);
        }

        if(container.getItem(SPEAR_SLOT).is(Items.IRON_SWORD)){
            BeeKnightEntity knight = getLinkedKnight();
            knight.hurt(this.damageSources().generic(), 1.0F);
            knight.playSound(ModSounds.BEE_KNIGHT_HURT.get(), 1.0F, 1.0F);

            this.spawnAtLocation(container.getItem(SPEAR_SLOT));
            container.setItem(SPEAR_SLOT, ItemStack.EMPTY);
        }

        if(container.getItem(MOUNT_ARMOR_SLOT).is(Items.IRON_HORSE_ARMOR)){
            this.hurt(this.damageSources().generic(), 1.0F);
            this.playSound(SoundEvents.BEE_HURT, 1.0F, 1.0F);

            this.spawnAtLocation(container.getItem(MOUNT_ARMOR_SLOT));
            container.setItem(MOUNT_ARMOR_SLOT, ItemStack.EMPTY);
        }

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

        this.entityData.set(KNIGHT_HAS_GOLD_SPEAR,
                container.getItem(SPEAR_SLOT).is(Items.GOLDEN_SWORD));

        this.entityData.set(KNIGHT_HAS_DIAMOND_SPEAR,
                container.getItem(SPEAR_SLOT).is(Items.DIAMOND_SWORD));

        this.entityData.set(KNIGHT_HAS_NETHERITE_SPEAR,
                container.getItem(SPEAR_SLOT).is(Items.NETHERITE_SWORD));

        for (int i = 0; i < container.getContainerSize(); i++) {
            lastInventoryCheck[i] = container.getItem(i).copy();
        }
        updateKnightStats();
    }

    @Override
    public void openCustomInventoryScreen(@Nonnull Player player) {
        if (!this.level().isClientSide && this.isTamed()) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            if (player.containerMenu != player.inventoryMenu) {
                player.closeContainer();
            }
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

    private void updateKnightStats() {
        BeeKnightEntity knight = getLinkedKnight();
        if (knight == null) return;

        ItemStack spear = inventory.getItem(SPEAR_SLOT);
        ItemStack chestPiece = inventory.getItem(KNIGHT_ARMOR_SLOT);
        ItemStack mountArmor = inventory.getItem(MOUNT_ARMOR_SLOT);

        AttributeInstance attack = knight.getAttribute(Attributes.ATTACK_DAMAGE);
        AttributeInstance armor = knight.getAttribute(Attributes.ARMOR);
        AttributeInstance toughness = knight.getAttribute(Attributes.ARMOR_TOUGHNESS);
        AttributeInstance health = knight.getAttribute(Attributes.MAX_HEALTH);
        AttributeInstance mount_armor = this.getAttribute(Attributes.ARMOR);
        AttributeInstance mount_toughness = this.getAttribute(Attributes.ARMOR_TOUGHNESS);
        AttributeInstance mount_health = this.getAttribute(Attributes.MAX_HEALTH);

        if (attack == null || armor == null || toughness == null || health == null || mount_armor == null || mount_toughness == null || mount_health == null) return;

        ResourceLocation spearId = ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "spear_modifier");
        ResourceLocation armorId = ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "armor_modifier");
        ResourceLocation toughnessId = ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "armor_toughness_modifier");
        ResourceLocation healthId = ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID,"health_modifier");
        ResourceLocation mountArmorId = ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "mount_armor_modifier");
        ResourceLocation mountToughnessId = ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "mount_armor_toughness_modifier");
        ResourceLocation mountHealthId = ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "mount_health_modifier");


        // Remove old modifier
        attack.removeModifier(spearId);
        armor.removeModifier(armorId);
        toughness.removeModifier(toughnessId);
        health.removeModifier(healthId);
        mount_armor.removeModifier(mountArmorId);
        mount_toughness.removeModifier(mountToughnessId);
        mount_health.removeModifier(mountHealthId);

        //WEAPON DAMAGE KNIGHT

        double damageBonus = 0.0;

        if (!spear.isEmpty()) {
            if (spear.is(Items.GOLDEN_SWORD)) damageBonus = 4.0;
            else if (spear.is(Items.DIAMOND_SWORD)) damageBonus = 7.0;
            else if (spear.is(Items.NETHERITE_SWORD)) damageBonus = 8.0;

            if (damageBonus > 0) {
                attack.addTransientModifier(new AttributeModifier(
                        spearId,
                        damageBonus,
                        AttributeModifier.Operation.ADD_VALUE
                ));

                System.out.println("Applied spear damage: +" + damageBonus);
            }
        }

        //ARMOR KNIGHT
        double armorBonus = 0.0;
        double toughnessBonus = 0.0;
        double healthBonus = 0.0;

        if (!chestPiece.isEmpty()) {
            if (chestPiece.is(Items.GOLDEN_CHESTPLATE)) armorBonus = 10.0;
            else if (chestPiece.is(Items.DIAMOND_CHESTPLATE)) {
                armorBonus = 16.0;
                toughnessBonus = 4.0;
                healthBonus = 10;
            }
            else if (chestPiece.is(Items.NETHERITE_CHESTPLATE)) {
                armorBonus = 20.0;
                toughnessBonus = 6.0;
                healthBonus = 20;
            }

            if (armorBonus > 0) {
                armor.addTransientModifier(new AttributeModifier(
                        armorId,
                        armorBonus,
                        AttributeModifier.Operation.ADD_VALUE
                ));

                System.out.println("Applied armor: +" + armorBonus);
            }

            if (toughnessBonus > 0) {
                toughness.addTransientModifier(new AttributeModifier(
                        toughnessId,
                        toughnessBonus,
                        AttributeModifier.Operation.ADD_VALUE
                ));

                System.out.println("Applied toughness: +" + toughnessBonus);
            }

            if(healthBonus > 0){
                health.addTransientModifier(new AttributeModifier(
                        healthId,
                        healthBonus,
                        AttributeModifier.Operation.ADD_VALUE
                ));

                System.out.println("Applied health: +" + healthBonus);
            }
        }

        //MOUNT ARMOR

        double mountArmorBonus = 0.0;
        double mountToughnessBonus = 0.0;
        double mountHealthBonus = 0.0;

        if (!mountArmor.isEmpty()) {
            if (mountArmor.is(Items.GOLDEN_HORSE_ARMOR)) {
                mountArmorBonus = 10.0;
                mountHealthBonus = 10;
            }
            else if (mountArmor.is(Items.DIAMOND_HORSE_ARMOR)) {
                mountArmorBonus = 16.0;
                mountToughnessBonus = 4.0;
                mountHealthBonus = 20;
            }

            if (mountArmorBonus > 0) {
                mount_armor.addTransientModifier(new AttributeModifier(
                        mountArmorId,
                        mountArmorBonus,
                        AttributeModifier.Operation.ADD_VALUE
                ));

                System.out.println("Applied mount armor: +" + mountArmorBonus);
            }

            if (mountToughnessBonus > 0) {
                mount_toughness.addTransientModifier(new AttributeModifier(
                        mountToughnessId,
                        mountToughnessBonus,
                        AttributeModifier.Operation.ADD_VALUE
                ));

                System.out.println("Applied mount toughness: +" + mountToughnessBonus);
            }

            if (mountHealthBonus > 0) {
                mount_health.addTransientModifier(new AttributeModifier(
                        mountHealthId,
                        mountHealthBonus,
                        AttributeModifier.Operation.ADD_VALUE
                ));

                System.out.println("Applied mount health: +" + mountHealthBonus);
            }
        }
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
        if (this.isVehicle() && this.getControllingPassenger() != null) {
            super.travel(travelVector);
            return;
        }

        if (this.getNavigation().isDone()) {
            super.travel(travelVector);
            return;
        }

        this.flyingTravel(this, travelVector);
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

    @Override
    public SoundEvent getAbilityOnSound() {
        return ModSounds.BEE_KNIGHT_PROTECT.get();
    }

    @Override
    public SoundEvent getAbilityOffSound() {
        return ModSounds.BEE_KNIGHT_GUARD.get();
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


    public enum State {
        FLY, FLY_IDLE
    }
}
