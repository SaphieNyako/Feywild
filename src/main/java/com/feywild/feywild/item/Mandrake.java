package com.feywild.feywild.item;

import com.feywild.feywild.sound.ModSoundEvents;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class Mandrake extends TooltipItem {

    public Mandrake(ModX mod, Properties properties) {
        super(mod, properties, new TranslationTextComponent("message.feywild.mandrake"));
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> use(@Nonnull World world, @Nonnull PlayerEntity player, @Nonnull Hand hand) {
        if (world.isClientSide) {
            player.playSound(ModSoundEvents.mandrakeScream, 1.0f, 0.8f);
        }

        return super.use(world, player, hand);
    }
}
