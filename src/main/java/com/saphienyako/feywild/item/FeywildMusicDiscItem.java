package com.saphienyako.feywild.item;

import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.item.Rarity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class FeywildMusicDiscItem extends MusicDiscItem {


    public FeywildMusicDiscItem() {
        super(1, ModSounds.FEYWILD_MUSIC_DISC, new Item.Properties().tab(ModCreativeModeTab.FEYWILD_TAB).stacksTo(1).rarity(Rarity.RARE));
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable World level, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flag) {
        if (level != null) {
            TranslationTextComponent text = new TranslationTextComponent("message.feywild.music_disc");
            text.getStyle().withColor(Color.fromLegacyFormat(TextFormatting.GOLD));
            tooltip.add(text);
        }

        super.appendHoverText(stack,level,tooltip,flag);
    }
}
