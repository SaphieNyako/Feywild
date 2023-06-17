package com.feywild.feywild.item;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.base.IOwnable;
import com.feywild.feywild.entity.base.ISummonable;
import com.feywild.feywild.entity.base.ITameable;
import com.feywild.feywild.network.ParticleMessage;
import com.feywild.feywild.util.TooltipHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.moddingx.libx.base.ItemBase;
import org.moddingx.libx.mod.ModX;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public abstract class SummoningScroll<T extends LivingEntity> extends ItemBase {

    protected final EntityType<T> type;

    @Nullable
    protected final SoundEvent soundEvent;

    public SummoningScroll(ModX mod, EntityType<T> type, @Nullable SoundEvent soundEvent, Properties properties) {
        super(mod, properties);
        this.type = type;
        this.soundEvent = soundEvent;
    }

    protected boolean canSummon(Level level, Player player, BlockPos pos, @Nullable CompoundTag storedTag, T entity) {
        return true;
    }

    protected boolean canCapture(Level level, Player player, T entity) {
        return true;
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
                if (entity != null && this.canSummon(context.getLevel(), context.getPlayer(), context.getClickedPos().immutable(), storedTag, entity)) {
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
                        if (this.soundEvent != null) entity.playSound(this.soundEvent, 1, 1);
                    }
                    if (!context.getPlayer().isCreative()) {
                        context.getItemInHand().shrink(1);
                        context.getPlayer().addItem(new ItemStack(ModItems.summoningScroll));
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
            TooltipHelper.addTooltip(tooltip, level, Component.translatable("message.feywild.summoning_scroll"));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }
}

