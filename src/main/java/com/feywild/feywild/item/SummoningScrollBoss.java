package com.feywild.feywild.item;

import com.feywild.feywild.entity.base.FlyingBossBase;
import com.feywild.feywild.sound.FeySound;
import com.feywild.feywild.util.TooltipHelper;
import com.feywild.feywild.world.FeywildDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.moddingx.libx.mod.ModX;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.world.item.Item.Properties;

public class SummoningScrollBoss<T extends FlyingBossBase> extends SummoningScroll<T> {

    public SummoningScrollBoss(ModX mod, EntityType<T> type, @Nullable FeySound sound, Properties properties) {
        super(mod, type, sound, properties);
    }

    @Override
    protected boolean canCapture(Level level, Player player, FlyingBossBase entity) {
        return false;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        if (level != null) TooltipHelper.addTooltip(tooltip, level, Component.translatable("message.feywild.boss"));
        super.appendHoverText(stack, level, tooltip, flag);
    }

    @Nonnull
    @Override
    public InteractionResult useOn(@Nonnull UseOnContext context) {
        if (context.getPlayer() != null) {
            if (!context.getLevel().isClientSide) {
                T entity = this.type.create(context.getLevel());
                if (entity != null) {
                    BlockPos offsetPos = context.getClickedPos().relative(context.getClickedFace());
                    entity.setPos(offsetPos.getX() + 0.5, offsetPos.getY(), offsetPos.getZ() + 0.5);
                    this.prepareEntity(context.getLevel(), context.getPlayer(), context.getClickedPos().immutable(), entity);
                    context.getLevel().addFreshEntity(entity);
                    if (this.sound != null) entity.playSound(this.sound.getSoundEvent(), 1, 1);
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
}
