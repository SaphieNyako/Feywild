package com.feywild.feywild.item;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.sound.ModSoundEvents;
import com.feywild.feywild.util.TooltipHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.item.Rarity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class FeywildMusicDisc extends MusicDiscItem {

    public FeywildMusicDisc() {
        this(1, ModSoundEvents.feywildSoundtrack, new Item.Properties().tab(FeywildMod.getInstance().tab).rarity(Rarity.RARE));
    }

    public FeywildMusicDisc(int comparatorValue, SoundEvent sound, Properties builder) {
        super(comparatorValue, () -> sound, builder);
    }
    
    @Nonnull
    @Override
    @OnlyIn(Dist.CLIENT)
    public IFormattableTextComponent getDisplayName() {
        return new TranslationTextComponent("message.feywild.music_disc_feywild_2");
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable World world, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flag) {
        TooltipHelper.addTooltip(tooltip, new TranslationTextComponent("message.feywild.music_disc_feywild"));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
