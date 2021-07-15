package com.feywild.feywild.item;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.sound.ModSoundEvents;
import com.feywild.feywild.util.KeyboardHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.item.Rarity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.KeybindTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Supplier;

public class FeywildMusicDisc extends MusicDiscItem {

    public FeywildMusicDisc() {
        this(1, ModSoundEvents.FEYWILD_SOUNDTRACK, new Item.Properties().tab(FeywildMod.FEYWILD_TAB).rarity(Rarity.RARE));
    }

    public FeywildMusicDisc(int comparatorValue, Supplier<SoundEvent> soundSupplier, Properties builder) {
        super(comparatorValue, soundSupplier, builder);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, World world, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flag) {
        if (KeyboardHelper.isHoldingShift()) {
            tooltip.add(new TranslationTextComponent("message.feywild.music_disc_feywild"));
        } else {
            tooltip.add(new TranslationTextComponent("message.feywild.itemmessage", new KeybindTextComponent("key.sneak")));
        }
    }

    @Nonnull
    @Override
    @OnlyIn(Dist.CLIENT)
    public IFormattableTextComponent getDisplayName() {
        return new TranslationTextComponent("message.feywild.music_disc_feywild_2");
    }

}
