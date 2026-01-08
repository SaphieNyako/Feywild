package com.saphienyako.feywild.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import vazkii.patchouli.api.PatchouliAPI;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class FeywildLexicon extends Item {
    public FeywildLexicon(Properties pProperties) {
        super(pProperties);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> use(@Nonnull World level, PlayerEntity player,@Nonnull Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player instanceof ServerPlayerEntity) {
            if (ModList.get().isLoaded("patchouli")) {
                PatchouliAPI.get().openBookGUI((ServerPlayerEntity) player, Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this)));
            } else {
                player.sendMessage(new TranslationTextComponent("message.feywild.no_lexicon"), player.getUUID());
            }
        }
        return new ActionResult<>(ActionResultType.FAIL, stack);
    }

    @Nonnull
    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable World level,@Nonnull List<ITextComponent> tooltip,@Nonnull ITooltipFlag flag) {
        if (level != null) {
            tooltip.add(new TranslationTextComponent("message.feywild.feywild_lexicon"));
        }
        super.appendHoverText(stack,level,tooltip,flag);
    }
}
