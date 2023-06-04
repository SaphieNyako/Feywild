package com.feywild.feywild.entity;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModTrees;
import com.feywild.feywild.block.trees.FeyLogBlock;
import com.feywild.feywild.config.MiscConfig;
import com.feywild.feywild.entity.base.TreeEntBase;
import com.feywild.feywild.network.ParticleMessage;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.tag.ModItemTags;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.Objects;

public class AutumnTreeEnt extends TreeEntBase {

    public final Alignment alignment = Alignment.AUTUMN;
    private int unalignedTicks = 0;

    public AutumnTreeEnt(EntityType<? extends TreeEntBase> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected FeyLogBlock getWoodBlock() {
        return ModTrees.autumnTree.getLogBlock();
    }

    @Override
    public void tick() {
        super.tick();

        Player owner = this.getOwningPlayer();
        if (!MiscConfig.summon_all_fey) {
            if (owner instanceof ServerPlayer serverPlayer) {
                Alignment ownerAlignment = QuestData.get(serverPlayer).getAlignment();
                if (ownerAlignment != null && ownerAlignment != this.alignment) {
                    unalignedTicks += 1;
                    if (unalignedTicks >= 300) {
                        owner.sendSystemMessage(Component.translatable("message.feywild." + Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(this.getType())).getPath() + ".disappear"));
                        this.remove(RemovalReason.DISCARDED);
                    }
                } else {
                    unalignedTicks = 0;
                }
            } else {
                unalignedTicks = 0;
            }
        }
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
            if (random.nextInt(3) < 1) {
                this.spawnAtLocation(new ItemStack(Blocks.MYCELIUM));
            }
            this.spawnAtLocation(ModTrees.autumnTree.getSapling());
            this.playSound(SoundEvents.COMPOSTER_READY, 1, 0.6f);

            return InteractionResult.sidedSuccess(this.level.isClientSide);
        } else {
            return superResult;
        }
    }
}
