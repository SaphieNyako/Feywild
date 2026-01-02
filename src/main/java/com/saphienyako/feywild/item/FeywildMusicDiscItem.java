package com.saphienyako.feywild.item;

import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class FeywildMusicDiscItem extends RecordItem {

    public FeywildMusicDiscItem() {
        super(1, ModSounds.FEYWILD_MUSIC_DISC, new Item.Properties().tab(ModCreativeModeTab.FEYWILD_TAB).stacksTo(1).rarity(Rarity.RARE));
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        if (level != null) {
            tooltip.add(new TranslatableComponent("message.feywild.music_disc").withStyle(ChatFormatting.GOLD));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }

}
