package com.feywild.feywild.item;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.sound.ModSoundEvents;
import com.feywild.feywild.util.TooltipHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class FeywildMusicDisc extends RecordItem {

    public FeywildMusicDisc() {
        this(1, ModSoundEvents.feywildSoundtrack, new Item.Properties().tab(FeywildMod.getInstance().tab).rarity(Rarity.RARE));
    }

    public FeywildMusicDisc(int comparatorValue, SoundEvent sound, Properties builder) {
        super(comparatorValue, () -> sound, builder);
    }
    
    @Nonnull
    @Override
    @OnlyIn(Dist.CLIENT)
    public MutableComponent getDisplayName() {
        return new TranslatableComponent("message.feywild.music_disc_feywild_2");
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        TooltipHelper.addTooltip(tooltip, new TranslatableComponent("message.feywild.music_disc_feywild"));
        super.appendHoverText(stack, level, tooltip, flag);
    }
}
