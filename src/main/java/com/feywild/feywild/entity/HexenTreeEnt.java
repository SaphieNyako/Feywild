package com.feywild.feywild.entity;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModTrees;
import com.feywild.feywild.block.trees.FeyLogBlock;
import com.feywild.feywild.entity.base.TreeEntBase;
import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.network.ParticleMessage;
import com.feywild.feywild.tag.ModItemTags;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;

public class HexenTreeEnt extends TreeEntBase {

    protected HexenTreeEnt(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected FeyLogBlock getWoodBlock() {
        return ModTrees.hexenTree.getLogBlock();
    }

    @Nonnull
    @Override
    public InteractionResult interactAt(@Nonnull Player player, @Nonnull Vec3 hitVec, @Nonnull InteractionHand hand) {
        InteractionResult superResult = super.interactAt(player, hitVec, hand);
        if ((player.getItemInHand(hand).getItem() == getWoodBlock().asItem() || player.getItemInHand(hand).is(ModItemTags.COOKIES))
                && (this.getLastHurtByMob() == null || !this.getLastHurtByMob().isAlive())) {
            this.heal(FEY_WOOD_HEAL_AMOUNT);
            if (!player.isCreative()) {
                player.getItemInHand(hand).shrink(1);
            }
            FeywildMod.getNetwork().sendParticles(this.level, ParticleMessage.Type.FEY_HEART, this.getX(), this.getY() + 4, this.getZ());
            player.swing(hand, true);

            return InteractionResult.sidedSuccess(this.level.isClientSide);

        } else if (player.getItemInHand(hand).getItem() == Items.BONE_MEAL && (this.getLastHurtByMob() == null || !this.getLastHurtByMob().isAlive())
                && this.isTamed() && player instanceof ServerPlayer && this.owner != null && this.owner.equals(player.getUUID())) {
            if (!player.isCreative()) {
                player.getItemInHand(hand).shrink(1);
            }
            FeywildMod.getNetwork().sendParticles(this.level, ParticleMessage.Type.CROPS_GROW, this.getX(), this.getY() + 4, this.getZ());
            player.swing(hand, true);

            this.spawnAtLocation(ModTrees.hexenTree.getSapling());
            this.playSound(SoundEvents.COMPOSTER_READY, 1, 0.6f);

            return InteractionResult.sidedSuccess(this.level.isClientSide);
        } else if (player.getItemInHand(hand).getItem() == ModItems.feyDust && (this.getLastHurtByMob() == null || !this.getLastHurtByMob().isAlive())
                && this.isTamed() && player instanceof ServerPlayer && this.owner != null && this.owner.equals(player.getUUID())) {
            if (!player.isCreative()) {
                player.getItemInHand(hand).shrink(1);
            }
            FeywildMod.getNetwork().sendParticles(this.level, ParticleMessage.Type.CROPS_GROW, this.getX(), this.getY() + 4, this.getZ());
            player.swing(hand, true);

            if (!level.isClientSide) {
                if (random.nextInt(3) < 1) {
                    player.addEffect(randomEnchant());

                    FeywildMod.getNetwork().sendParticles(this.level, ParticleMessage.Type.MAGIC_EFFECT, player.getX(), player.getY(), player.getZ());
                }
            }

            this.playSound(SoundEvents.COMPOSTER_READY, 1, 0.6f);

            return InteractionResult.sidedSuccess(this.level.isClientSide);
        } else {
            return superResult;
        }
    }

    public MobEffectInstance randomEnchant() {

        final int SHORT = 600;
        final int MEDIUM = 1200; // minute
        final int LONG = 12000; // 10 minutes
        final int EXTENDED = 36000; //30 minutes
        //TODO add more effects

        return switch (random.nextInt(29)) {
            case 0 -> new MobEffectInstance(MobEffects.ABSORPTION, LONG);
            case 1 -> new MobEffectInstance(MobEffects.BAD_OMEN, SHORT);
            case 2 -> new MobEffectInstance(MobEffects.BLINDNESS, SHORT);
            case 3 -> new MobEffectInstance(MobEffects.CONDUIT_POWER, LONG);
            case 4 -> new MobEffectInstance(MobEffects.CONFUSION, SHORT);
            case 5 -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, LONG);
            case 6 -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, LONG);
            case 7 -> new MobEffectInstance(MobEffects.DARKNESS, SHORT);
            case 8 -> new MobEffectInstance(MobEffects.DIG_SLOWDOWN, SHORT);
            case 9 -> new MobEffectInstance(MobEffects.DIG_SPEED, LONG);
            case 10 -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, LONG);
            case 11 -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, SHORT);
            case 12 -> new MobEffectInstance(MobEffects.DOLPHINS_GRACE, LONG);
            case 13 -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, LONG);
            case 14 -> new MobEffectInstance(MobEffects.GLOWING, SHORT);
            case 15 -> new MobEffectInstance(MobEffects.HARM, SHORT);
            case 16 -> new MobEffectInstance(MobEffects.HEAL, SHORT);
            case 17 -> new MobEffectInstance(MobEffects.HEALTH_BOOST, LONG);
            case 18 -> new MobEffectInstance(MobEffects.INVISIBILITY, LONG);
            case 19 -> new MobEffectInstance(MobEffects.LUCK, LONG);
            case 20 -> new MobEffectInstance(MobEffects.NIGHT_VISION, LONG);
            case 21 -> new MobEffectInstance(MobEffects.POISON, SHORT);
            case 22 -> new MobEffectInstance(MobEffects.REGENERATION, SHORT);
            case 23 -> new MobEffectInstance(MobEffects.SLOW_FALLING, LONG);
            case 24 -> new MobEffectInstance(MobEffects.UNLUCK, LONG);
            case 25 -> new MobEffectInstance(MobEffects.WEAKNESS, SHORT);
            case 26 -> new MobEffectInstance(MobEffects.WATER_BREATHING, LONG);
            case 27 -> new MobEffectInstance(MobEffects.WITHER, SHORT);
            default -> new MobEffectInstance(MobEffects.LEVITATION, SHORT);
        };


    }

}
