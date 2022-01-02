package com.feywild.feywild.item;

import com.feywild.feywild.entity.DwarfBlacksmith;
import com.feywild.feywild.util.TooltipHelper;
import io.github.noeppi_noeppi.libx.base.ItemBase;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public abstract class SummoningScroll<T extends LivingEntity> extends ItemBase {
    
    protected final EntityType<T> type;

    @Nullable
    private final SoundEvent soundEvent;

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
                    BlockPos offsetPos = context.getClickedPos().relative(context.getClickedFace());
                    entity.setPos(offsetPos.getX() + 0.5, offsetPos.getY(), offsetPos.getZ() + 0.5);
                    this.prepareEntity(context.getLevel(), context.getPlayer(), context.getClickedPos().immutable(), entity);
                    if (this instanceof SummoningScrollFey || this instanceof SummoningScrollDwarfBlacksmith) {
                        context.getLevel().addFreshEntity(entity);
                        if (this.soundEvent != null) entity.playSound(this.soundEvent, 1, 1);
                    }
                    if (!context.getPlayer().isCreative()) {
                        context.getItemInHand().shrink(1);
                        if (!(entity instanceof DwarfBlacksmith))
                        context.getPlayer().addItem(new ItemStack(ModItems.summoningScroll));
                    }
                }else
                    context.getPlayer().sendMessage(new TranslatableComponent("message.feywild.summon_fail"), context.getPlayer().getUUID());
            }
            return InteractionResult.sidedSuccess(context.getLevel().isClientSide);
        }
        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        TooltipHelper.addTooltip(tooltip, new TranslatableComponent("message.feywild.summoning_scroll"));
        super.appendHoverText(stack, level, tooltip, flag);
    }
}

