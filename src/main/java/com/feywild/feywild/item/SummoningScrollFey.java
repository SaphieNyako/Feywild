package com.feywild.feywild.item;

import com.feywild.feywild.entity.Shroomling;
import com.feywild.feywild.entity.base.Fey;
import com.feywild.feywild.entity.base.FeyBase;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.util.TooltipHelper;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.Registerable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class SummoningScrollFey<T extends FeyBase> extends SummoningScroll<T> implements Registerable {

    public SummoningScrollFey(ModX mod, EntityType<T> type, @Nullable SoundEvent soundEvent, Properties properties) {
        super(mod, type, soundEvent, properties);
    }

    @Override
    public void registerCommon(ResourceLocation id, Consumer<Runnable> defer) {
        EmptyScroll.registerCapture(type, this);
    }

    @Override
    protected boolean canSummon(Level level, Player player, BlockPos pos, @Nullable CompoundTag storedTag, T entity) {
        if (player instanceof ServerPlayer serverPlayer) {
            return QuestData.get(serverPlayer).getAlignment() == entity.alignment || (QuestData.get(serverPlayer).getAlignment() == null && entity instanceof Fey);
        }

        return false;
    }

    @Override
    protected void prepareEntity(Level level, Player player, BlockPos pos, T entity) {
        // General setters
        entity.setCurrentTargetPos(pos);
        entity.setOwner(player);

        // Specific setters
        if (entity instanceof Fey) {
            ((Fey) entity).setTamed(true);
            entity.setCurrentTargetPos((BlockPos) null);
        }
        if (entity instanceof Shroomling) {
            ((Shroomling) entity).setTamed(true);
            entity.setCurrentTargetPos((BlockPos) null);
            //TODO needs rework in 1.18
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
