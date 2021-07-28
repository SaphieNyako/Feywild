package com.feywild.feywild.item;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import vazkii.patchouli.api.PatchouliAPI;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

public class FeywildLexicon extends TooltipItem {

    public FeywildLexicon(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> use(@Nonnull World worldIn, PlayerEntity playerIn, @Nonnull Hand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);

        if (playerIn instanceof ServerPlayerEntity) {
            PatchouliAPI.get().openBookGUI((ServerPlayerEntity) playerIn, Objects.requireNonNull(this.getRegistryName()));
        }

        return new ActionResult<>(ActionResultType.PASS, stack);
    }

    @Override
    public List<ITextComponent> getTooltip(ItemStack stack, World world) {
        return ImmutableList.of(new TranslationTextComponent("message.feywild.feywild_lexicon"));
    }
}
