package com.feywild.feywild.entity;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModTrees;
import com.feywild.feywild.block.trees.FeyLogBlock;
import com.feywild.feywild.entity.base.TreeEntBase;
import com.feywild.feywild.network.ParticleMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import java.util.Random;

public class BlossomTreeEnt extends TreeEntBase {

    protected BlossomTreeEnt(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected FeyLogBlock getWoodBlock() {
        return ModTrees.blossomTree.getLogBlock();
    }

    @Nonnull
    @Override
    public InteractionResult interactAt(@Nonnull Player player, @Nonnull Vec3 hitVec, @Nonnull InteractionHand hand) {
        InteractionResult superResult = super.interactAt(player, hitVec, hand);
        if ((player.getItemInHand(hand).getItem() == getWoodBlock().asItem() || player.getItemInHand(hand).getItem() == Items.COOKIE)
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
            Random random = new Random();
            if (random.nextInt(3) < 1) {
                this.spawnAtLocation(returnItemStack());
            }
            this.spawnAtLocation(ModTrees.blossomTree.getSapling());
            this.playSound(SoundEvents.COMPOSTER_READY, 1, 0.6f);

            return InteractionResult.sidedSuccess(this.level.isClientSide);
        } else {
            return superResult;
        }
    }

    private ItemStack returnItemStack() {
        return switch (random.nextInt(66)) {
            case 0 -> new ItemStack(Items.ENCHANTED_GOLDEN_APPLE);
            case 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 -> new ItemStack(Items.GOLDEN_APPLE);
            default -> new ItemStack(Items.APPLE);
        };
    }

}
