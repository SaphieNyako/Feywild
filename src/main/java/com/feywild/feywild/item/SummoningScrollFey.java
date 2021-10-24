package com.feywild.feywild.item;

import com.feywild.feywild.entity.BeeKnight;
import com.feywild.feywild.entity.base.FeyBase;
import com.feywild.feywild.entity.base.Fey;
import com.feywild.feywild.util.TooltipHelper;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.Registerable;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import net.minecraft.world.item.Item.Properties;

public class SummoningScrollFey<T extends FeyBase> extends SummoningScroll<T> implements Registerable {

    public SummoningScrollFey(ModX mod, EntityType<T> type, @Nullable SoundEvent soundEvent, Properties properties) {
        super(mod, type, soundEvent, properties);
    }

    @Override
    public void registerCommon(ResourceLocation id, Consumer<Runnable> defer) {
        SummoningScroll.registerCapture(type, this);
    }

    @Override
    protected void prepareEntity(Level level, Player player, BlockPos pos, T entity) {
        if (entity instanceof Fey) {
            ((Fey) entity).setTamed(true);
        }
        entity.setOwner(player);
        if (entity instanceof BeeKnight) {
            ((BeeKnight) entity).setTreasurePos(pos);
        }
    }

    @Override
    protected boolean canCapture(Level level, Player player, T entity) {
        return (!(entity instanceof Fey) || ((Fey) entity).isTamed()) && player.getUUID().equals(entity.getOwnerId());
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        TooltipHelper.addTooltip(tooltip, new TranslatableComponent("message.feywild." + Objects.requireNonNull(this.type.getRegistryName()).getPath()));
        super.appendHoverText(stack, level, tooltip, flag);
    }
}
