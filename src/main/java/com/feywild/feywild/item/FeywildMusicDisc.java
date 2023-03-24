package com.feywild.feywild.item;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.sound.ModSoundEvents;
import com.feywild.feywild.util.TooltipHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

public class FeywildMusicDisc extends RecordItem {

    public FeywildMusicDisc() {
        super(1, () -> ModSoundEvents.feywildSoundtrack, new Item.Properties().tab(Objects.requireNonNull(FeywildMod.getInstance().tab)).stacksTo(1).rarity(Rarity.RARE), 1880);
    }

    @Nonnull
    @Override
    @OnlyIn(Dist.CLIENT)
    public MutableComponent getDisplayName() {
        return Component.translatable("message.feywild.music_disc_feywild_2");
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        TooltipHelper.addTooltip(tooltip, level, Component.translatable("message.feywild.music_disc_feywild"));
        super.appendHoverText(stack, level, tooltip, flag);
    }
}
