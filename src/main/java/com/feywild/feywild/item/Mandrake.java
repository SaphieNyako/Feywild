package com.feywild.feywild.item;

import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.moddingx.libx.mod.ModX;

import javax.annotation.Nonnull;

public class Mandrake extends TooltipItem {

    public Mandrake(ModX mod, Properties properties) {
        super(mod, properties, Component.translatable("message.feywild.mandrake"));
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level, @Nonnull Player player, @Nonnull InteractionHand hand) {
        if (level.isClientSide) {
            player.playSound(ModSoundEvents.mandrakeScream, 0.6f, 0.8f);
        }
        return super.use(level, player, hand);
    }
}
