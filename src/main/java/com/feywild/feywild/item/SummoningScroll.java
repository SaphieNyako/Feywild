package com.feywild.feywild.item;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.config.MiscConfig;
import com.feywild.feywild.entity.base.IOwnable;
import com.feywild.feywild.entity.base.ISummonable;
import com.feywild.feywild.entity.base.ITameable;
import com.feywild.feywild.network.ParticleMessage;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.sound.FeySound;
import com.feywild.feywild.tag.ModEntityTags;
import com.feywild.feywild.util.TooltipHelper;
import com.feywild.feywild.world.FeywildDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.moddingx.libx.base.ItemBase;
import org.moddingx.libx.mod.ModX;
import org.moddingx.libx.registration.Registerable;
import org.moddingx.libx.registration.SetupContext;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public abstract class SummoningScroll<T extends LivingEntity> extends ItemBase implements Registerable {

    protected final EntityType<T> type;

    @Nullable protected final FeySound sound;

    public SummoningScroll(ModX mod, EntityType<T> type, @Nullable FeySound sound, Properties properties) {
        super(mod, properties);
        this.type = type;
        this.sound = sound;
    }
    
    @Override
    public void registerCommon(SetupContext ctx) {
        EmptyScroll.registerCapture(type, this);
    }

    protected boolean canSummon(Level level, Player player, BlockPos pos, @Nullable CompoundTag storedTag, T entity) {
        return true;
    }

    protected boolean canCapture(Level level, Player player, T entity) {
        if (this.type.is(ModEntityTags.BOSSES)) return false;
        if (entity instanceof OwnableEntity ownable) {
            return Objects.equals(ownable.getOwnerUUID(), player.getUUID());
        } else {
            return true;
        }
    }
    
    @Nullable
    protected Alignment requiredAlignment(Level level, Player player, T entity) {
        return null;
    }

    protected void prepareEntity(Level level, Player player, BlockPos pos, T entity) {
        if (entity instanceof ISummonable summonable) {
            summonable.setSummonPos(pos.immutable());
        }
        if (entity instanceof ITameable tameable) {
            tameable.trySetTamed(true);
        }
        if (entity instanceof IOwnable ownable) {
            ownable.setOwner(player);
        }
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
                if (entity != null && context.getLevel().dimension() != FeywildDimensions.MARKETPLACE && this.canSummon(context.getLevel(), context.getPlayer(), context.getClickedPos().immutable(), storedTag, entity)) {
                    Alignment requiredAlignment = requiredAlignment(context.getLevel(), context.getPlayer(), entity);
                    if (MiscConfig.summon_all_fey || requiredAlignment == null || (context.getPlayer() instanceof ServerPlayer serverPlayer && requiredAlignment == QuestData.get(serverPlayer).getAlignment())) {
                        if (storedTag != null) entity.load(storedTag);
                        if (context.getItemInHand().hasCustomHoverName()) {
                            entity.setCustomName(context.getItemInHand().getHoverName());
                        }
                        //  BlockPos offsetPos = context.getClickedPos().relative(context.getClickedFace());
                        entity.setPos(context.getClickedPos().getX(), context.getClickedPos().getY() + 1, context.getClickedPos().getZ());

                        this.prepareEntity(context.getLevel(), context.getPlayer(), context.getClickedPos().immutable(), entity);
                        if (this instanceof SummoningScrollFey) {
                            context.getLevel().addFreshEntity(entity);
                            FeywildMod.getNetwork().sendParticles(context.getLevel(), ParticleMessage.Type.DANDELION_FLUFF, context.getClickedPos().getX(), context.getClickedPos().getY() + 1, context.getClickedPos().getZ());
                            if (this.sound != null) entity.playSound(this.sound.getSoundEvent(), 1, 1);
                        }
                        if (!context.getPlayer().isCreative()) {
                            context.getItemInHand().shrink(1);
                            context.getPlayer().addItem(new ItemStack(ModItems.summoningScroll));
                        }
                    } else {
                        context.getPlayer().sendSystemMessage(Component.translatable("message.feywild.summon_fail"));
                    }
                }
            }
            return InteractionResult.sidedSuccess(context.getLevel().isClientSide);
        }
        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        if (level != null) {
            if (this.type.is(ModEntityTags.BOSSES)) {
                TooltipHelper.addTooltip(tooltip, level, Component.translatable("message.feywild.summoning_scroll"), Component.translatable("message.feywild.boss"));
            } else {
                TooltipHelper.addTooltip(tooltip, level, Component.translatable("message.feywild.summoning_scroll"));
            }
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }
}

