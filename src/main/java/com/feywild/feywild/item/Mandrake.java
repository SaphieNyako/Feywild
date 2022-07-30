package com.feywild.feywild.item;

import com.feywild.feywild.sound.ModSoundEvents;
import org.moddingx.libx.mod.ModX;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

import net.minecraft.world.item.Item.Properties;

public class Mandrake extends TooltipItem {

    public Mandrake(ModX mod, Properties properties) {
        super(mod, properties, new TranslatableComponent("message.feywild.mandrake"));
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level, @Nonnull Player player, @Nonnull InteractionHand hand) {
        if (level.isClientSide) {
            player.playSound(ModSoundEvents.mandrakeScream, 1.0f, 0.8f);
        }

        return super.use(level, player, hand);
    }
}
