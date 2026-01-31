package com.saphienyako.feywild.entity;

import com.saphienyako.feywild.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class MooShroomCowEntity extends MushroomCow {

    public static final EntityDataAccessor<Integer> MOO_SHROOM_VARIANT = SynchedEntityData.defineId(MooShroomCowEntity.class, EntityDataSerializers.INT);

    @Nullable
    private SuspiciousStewEffects stewEffects;
    public MooShroomCowEntity(EntityType<? extends MushroomCow> entityType, Level level) {
        super(entityType, level);
        this.entityData.set(MOO_SHROOM_VARIANT, MooShroomCowVariant.BLUE.ordinal());
    }

    public static boolean canSpawn(EntityType<? extends MushroomCow> entity, LevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return isBrightEnoughToSpawn(level, pos);
    }

    @Override
    public void thunderHit(@Nonnull ServerLevel level,@Nonnull LightningBolt bolt) {
       //Do Nothing
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(MOO_SHROOM_VARIANT, MOO_SHROOM_VARIANT.id());
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player,@Nonnull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.is(Items.BOWL) && !this.isBaby()) {
            boolean flag = false;
            ItemStack itemstack2;
            MooShroomCowVariant variant = this.getMooShroomVariant();
            this.stewEffects = stewEffectForVariant(variant);

            flag = true;
            itemstack2 = new ItemStack(Items.SUSPICIOUS_STEW);
            itemstack2.set(DataComponents.SUSPICIOUS_STEW_EFFECTS, this.stewEffects);
            this.stewEffects = null;

            ItemStack itemstack1 = ItemUtils.createFilledResult(itemstack, player, itemstack2, false);
            player.setItemInHand(hand, itemstack1);
            SoundEvent soundevent;
            if (flag) {
                soundevent = SoundEvents.MOOSHROOM_MILK_SUSPICIOUSLY;
            } else {
                soundevent = SoundEvents.MOOSHROOM_MILK;
            }

            this.playSound(soundevent, 1.0F, 1.0F);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else if (itemstack.is(Items.SHEARS) && this.readyForShearing()) {
            this.shear(SoundSource.PLAYERS);
            //TODO shear returns correct mushrooms
            this.gameEvent(GameEvent.SHEAR, player);
            if (!this.level().isClientSide) {
                itemstack.hurtAndBreak(1, player, getSlotForHand(hand));
            }

            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }   else {
            return super.mobInteract(player, hand);
        }
    }

    @Override
    public void shear(@Nonnull SoundSource source) {
        this.level().playSound(null, this, SoundEvents.MOOSHROOM_SHEAR, source, 1.0F, 1.0F);
        if (!this.level().isClientSide()) {
            if (!net.neoforged.neoforge.event.EventHooks.canLivingConvert(this, EntityType.COW, (timer) -> {})) return;
            Cow cow = EntityType.COW.create(this.level());
            if (cow != null) {
                net.neoforged.neoforge.event.EventHooks.onLivingConvert(this, cow);
                ((ServerLevel)this.level()).sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY(0.5), this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
                this.discard();
                cow.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                cow.setHealth(this.getHealth());
                cow.yBodyRot = this.yBodyRot;
                if (this.hasCustomName()) {
                    cow.setCustomName(this.getCustomName());
                    cow.setCustomNameVisible(this.isCustomNameVisible());
                }

                if (this.isPersistenceRequired()) {
                    cow.setPersistenceRequired();
                }

                cow.setInvulnerable(this.isInvulnerable());
                this.level().addFreshEntity(cow);

                for (int i = 0; i < 5; i++) {
                    //Neo: Change from addFreshEntity to spawnAtLocation to ensure captureDrops can capture this, we also need to unset the default pickup delay from the item
                    ItemEntity item = spawnAtLocation(new ItemStack(this.getMooShroomVariant().getShroomBlock().asItem()), getBbHeight());
                    if (item != null) item.setNoPickUpDelay();
                }
            }
        }
    }

    public MooShroomCowVariant getMooShroomVariant() {
        return MooShroomCowVariant.values()[this.entityData.get(MOO_SHROOM_VARIANT)];
    }

    public void setMooShroomVariant(MooShroomCowVariant variant) {this.entityData.set(MOO_SHROOM_VARIANT, variant.ordinal());}

    private static SuspiciousStewEffects stewEffectForVariant(MooShroomCowVariant variant) {
        List<SuspiciousStewEffects.Entry> effects = new ArrayList<>();

        switch (variant) {
            case ORANGE ->
                    effects.add(new SuspiciousStewEffects.Entry(MobEffects.FIRE_RESISTANCE, 120));
            case YELLOW ->
                    effects.add(new SuspiciousStewEffects.Entry(MobEffects.GLOWING, 180));
            case GREEN ->
                    effects.add(new SuspiciousStewEffects.Entry(MobEffects.ABSORPTION, 100));
            case LIGHT_BLUE ->
                    effects.add(new SuspiciousStewEffects.Entry(MobEffects.JUMP, 120));
            case BLUE ->
                    effects.add(new SuspiciousStewEffects.Entry(MobEffects.NIGHT_VISION, 200));
            case PURPLE ->
                    effects.add(new SuspiciousStewEffects.Entry(MobEffects.SLOW_FALLING, 180));
            case PINK ->
                    effects.add(new SuspiciousStewEffects.Entry(MobEffects.REGENERATION, 120));
        }

        return new SuspiciousStewEffects(effects);
    }

    public enum State {
        IDLE, POSE, WALK, SING
    }

    public enum MooShroomCowVariant {
        RED(Blocks.RED_MUSHROOM),
        BROWN(Blocks.BROWN_MUSHROOM),
        ORANGE(ModBlocks.ORANGE_MUSHROOM.get()),
        YELLOW(ModBlocks.YELLOW_MUSHROOM.get()),
        GREEN(ModBlocks.GREEN_MUSHROOM.get()),
        LIGHT_BLUE(ModBlocks.LIGHT_BLUE_MUSHROOM.get()),
        BLUE(ModBlocks.BLUE_MUSHROOM.get()),
        PURPLE(ModBlocks.PURPLE_MUSHROOM.get()),
        PINK(ModBlocks.PINK_MUSHROOM.get());

        private final Block shroomBlock;

        MooShroomCowVariant(Block shroomItem) {
            this.shroomBlock = shroomItem;
        }

        public Block getShroomBlock() {
            return shroomBlock;
        }
    }
}
