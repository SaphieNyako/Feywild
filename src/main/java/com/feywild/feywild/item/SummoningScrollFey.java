package com.feywild.feywild.item;

import com.feywild.feywild.entity.util.FeyEntity;
import com.feywild.feywild.util.TooltipHelper;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class SummoningScrollFey<T extends FeyEntity> extends SummoningScroll<T> {

    public SummoningScrollFey(ModX mod, EntityType<T> type, @Nullable SoundEvent soundEvent, Properties properties) {
        super(mod, type, soundEvent, properties);
    }

    @Override
    protected boolean canSummon(World world, PlayerEntity player, BlockPos pos) {
        return super.canSummon(world, player, pos);
    }

    @Override
    protected void prepareEntity(World world, PlayerEntity player, BlockPos pos, T entity) {
        entity.setTamed(true);
        entity.setSummonPos(pos);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable World world, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flag) {
        // TODO check that tooltips match
        TooltipHelper.addTooltip(tooltip, new TranslationTextComponent("message.feywild." + Objects.requireNonNull(this.type.getRegistryName()).getPath()));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
