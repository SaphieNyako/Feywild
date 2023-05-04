package com.feywild.feywild.entity;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModTrees;
import com.feywild.feywild.block.trees.FeyLogBlock;
import com.feywild.feywild.config.MiscConfig;
import com.feywild.feywild.entity.base.TreeEntBase;
import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.network.ParticleMessage;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.player.QuestData;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.Objects;

public class SummerTreeEnt extends TreeEntBase {

    public final Alignment alignment = Alignment.SUMMER;
    private int unalignedTicks = 0;

    protected SummerTreeEnt(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected FeyLogBlock getWoodBlock() {
        return ModTrees.summerTree.getLogBlock();
    }

    @Override
    public void tick() {
        super.tick();
        if (!MiscConfig.summon_all_fey) {
            Player owner = this.getOwningPlayer();
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
        if ((player.getItemInHand(hand).getItem() == getWoodBlock().asItem() || player.getItemInHand(hand).getItem() == Items.COOKIE)
                && (this.getLastHurtByMob() == null || !this.getLastHurtByMob().isAlive())) {
            this.heal(FEY_WOOD_HEAL_AMOUNT);
            if (!player.isCreative()) {
                player.getItemInHand(hand).shrink(1);
            }
            FeywildMod.getNetwork().sendParticles(this.level, ParticleMessage.Type.FEY_HEART, this.getX(), this.getY() + 4, this.getZ());
            player.swing(hand, true);

            return InteractionResult.sidedSuccess(this.level.isClientSide);

        } else if (player.getItemInHand(hand).getItem() == ModItems.magicalHoneyCookie && (this.getLastHurtByMob() == null || !this.getLastHurtByMob().isAlive())
                && this.isTamed() && player instanceof ServerPlayer && this.owner != null && this.owner.equals(player.getUUID())) {
            this.heal(FEY_WOOD_HEAL_AMOUNT);
            if (!player.isCreative()) {
                player.getItemInHand(hand).shrink(1);
            }
            FeywildMod.getNetwork().sendParticles(this.level, ParticleMessage.Type.FEY_HEART, this.getX(), this.getY() + 4, this.getZ());
            player.swing(hand, true);

            Bee bee = new Bee(EntityType.BEE, level);
            bee.setPos(this.getX(), this.getY() + 2, this.getZ());
            level.addFreshEntity(bee);

            this.playSound(SoundEvents.BEEHIVE_EXIT, 1, 0.6f);

            return InteractionResult.sidedSuccess(this.level.isClientSide);
        } else if (player.getItemInHand(hand).getItem() == Items.BONE_MEAL && (this.getLastHurtByMob() == null || !this.getLastHurtByMob().isAlive())
                && this.isTamed() && player instanceof ServerPlayer && this.owner != null && this.owner.equals(player.getUUID())) {
            if (!player.isCreative()) {
                player.getItemInHand(hand).shrink(1);
            }
            FeywildMod.getNetwork().sendParticles(this.level, ParticleMessage.Type.CROPS_GROW, this.getX(), this.getY() + 4, this.getZ());
            player.swing(hand, true);

            this.spawnAtLocation(ModTrees.summerTree.getSapling());
            this.playSound(SoundEvents.COMPOSTER_READY, 1, 0.6f);

            if (!level.isClientSide) {
                if (random.nextInt(5) < 1) {
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 12000));
                    FeywildMod.getNetwork().sendParticles(this.level, ParticleMessage.Type.MAGIC_EFFECT, player.getX(), player.getY(), player.getZ());
                }
            }

            return InteractionResult.sidedSuccess(this.level.isClientSide);
        } else {
            return superResult;
        }
    }

}
