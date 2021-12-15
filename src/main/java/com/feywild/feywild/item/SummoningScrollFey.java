package com.feywild.feywild.item;

import com.feywild.feywild.entity.ShroomlingEntity;
import com.feywild.feywild.entity.base.FeyBase;
import com.feywild.feywild.entity.base.PixieEntity;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.util.TooltipHelper;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.Registerable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

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
        SummoningScroll.registerCapture(type, this);
    }

    @Override
    protected boolean canSummon(World world, PlayerEntity player, BlockPos pos, @Nullable CompoundNBT storedTag, T entity) {
        if (player instanceof ServerPlayerEntity) {
            return QuestData.get((ServerPlayerEntity) player).getAlignment() == entity.alignment || (QuestData.get((ServerPlayerEntity) player).getAlignment() == null && entity instanceof PixieEntity);
        }
        return false;
    }

    @Override
    protected void prepareEntity(World world, PlayerEntity player, BlockPos pos, T entity) {
        entity.setOwner(player);
        (entity).setCurrentTargetPos(pos);
        if (entity instanceof PixieEntity) {
            ((PixieEntity) entity).setTamed(true);
            entity.setCurrentTargetPos((BlockPos) null);
        }
        if (entity instanceof ShroomlingEntity) {
            ((ShroomlingEntity) entity).setTamed(true);
            entity.setCurrentTargetPos((BlockPos) null);
        }
    }

    @Override
    protected boolean canCapture(World world, PlayerEntity player, T entity) {
        return (!(entity instanceof PixieEntity) || ((PixieEntity) entity).isTamed()) && player.getUUID().equals(entity.getOwnerId());
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable World world, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flag) {
        TooltipHelper.addTooltip(tooltip, new TranslationTextComponent("message.feywild." + Objects.requireNonNull(this.type.getRegistryName()).getPath()));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
