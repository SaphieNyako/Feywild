package com.feywild.feywild.item;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.config.MiscConfig;
import com.feywild.feywild.network.ParticleMessage;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.sound.FeySound;
import com.feywild.feywild.world.FeywildDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.moddingx.libx.mod.ModX;
import org.moddingx.libx.registration.Registerable;
import org.moddingx.libx.registration.SetupContext;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.world.item.Item.Properties;

public class SummoningScrollAllay<T extends Allay> extends SummoningScroll<T> implements Registerable {
    
    public SummoningScrollAllay(ModX mod, EntityType<T> type, @Nullable FeySound sound, Properties properties) {
        super(mod, type, sound, properties);
    }

    @Override
    public void registerCommon(SetupContext ctx) {
        EmptyScroll.registerCapture(type, this);
    }

    @Override
    protected boolean canSummon(Level level, Player player, BlockPos pos, @Nullable CompoundTag storedTag, T entity) {
        if (player instanceof ServerPlayer serverPlayer) {
            if (MiscConfig.summon_all_fey && serverPlayer.level().dimension() != FeywildDimensions.MARKETPLACE) {
                return true;
            } else {
                Alignment alignment = QuestData.get(serverPlayer).getAlignment();
                if (player.level().dimension() == FeywildDimensions.MARKETPLACE) {
                    player.sendSystemMessage(Component.translatable("message.feywild.summon_market"));
                    return false;
                } else if (alignment == Alignment.WINTER) {
                    player.sendSystemMessage(Component.translatable("message.feywild.summon_fail"));
                    return false;
                } else {
                    return true;
                }
            }
        }
        player.sendSystemMessage(Component.translatable("message.feywild.summon_fail"));
        return false;

    }

    @Override
    protected boolean canCapture(Level level, Player player, T entity) {
        return true;
    }

    @Nonnull
    @Override
    public InteractionResult useOn(@Nonnull UseOnContext context) {
        CompoundTag storedTag = null;
        if (context.getItemInHand().hasTag() && context.getItemInHand().getOrCreateTag().contains("StoredEntityData", Tag.TAG_COMPOUND)) {
            storedTag = context.getItemInHand().getOrCreateTag().getCompound("StoredEntityData");
        }
        if (context.getPlayer() != null) {
            if (!context.getLevel().isClientSide) {
                T entity = this.type.create(context.getLevel());
                if (entity != null && this.canSummon(context.getLevel(), context.getPlayer(), context.getClickedPos().immutable(), storedTag, entity)) {
                    if (storedTag != null) entity.load(storedTag);
                    if (context.getItemInHand().hasCustomHoverName()) {
                        entity.setCustomName(context.getItemInHand().getHoverName());
                    }
                    //    BlockPos offsetPos = context.getClickedPos().relative(context.getClickedFace());
                    entity.setPos(context.getClickedPos().getX(), context.getClickedPos().getY() + 1, context.getClickedPos().getZ());
                    this.prepareEntity(context.getLevel(), context.getPlayer(), context.getClickedPos().immutable(), entity);

                    context.getLevel().addFreshEntity(entity);
                    FeywildMod.getNetwork().sendParticles(context.getLevel(), ParticleMessage.Type.DANDELION_FLUFF, context.getClickedPos().getX(), context.getClickedPos().getY() + 1, context.getClickedPos().getZ());

                    if (this.soundEvent != null) entity.playSound(this.soundEvent, 1, 1);

                    if (!context.getPlayer().isCreative()) {
                        context.getItemInHand().shrink(1);
                    }
                }
            }
            return InteractionResult.sidedSuccess(context.getLevel().isClientSide);
        }
        return InteractionResult.PASS;
    }

}
