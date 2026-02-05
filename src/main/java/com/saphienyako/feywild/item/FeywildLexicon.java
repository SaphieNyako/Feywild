package com.saphienyako.feywild.item;

import com.saphienyako.feywild.Feywild;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.fml.ModList;
import org.jetbrains.annotations.NotNull;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.List;


public class FeywildLexicon extends Item {
    public FeywildLexicon(Properties pProperties) {
        super(pProperties);
    }



    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player instanceof ServerPlayer serverPlayer) {
            if (ModList.get().isLoaded("patchouli")) {
                PatchouliAPI.get().openBookGUI(serverPlayer, ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "feywild_lexicon"));
            } else {
                player.sendSystemMessage(Component.translatable("message.feywild.no_lexicon")
                );
            }
        }
        return new InteractionResultHolder<>(InteractionResult.FAIL, stack);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        if(Screen.hasShiftDown()){
            tooltip.add(Component.translatable("message.feywild.feywild_lexicon").withStyle(ChatFormatting.BLUE));
        }

        else {
            tooltip.add(Component.translatable("message.feywild.shift_down").withStyle(ChatFormatting.GREEN));
        }
        super.appendHoverText(stack, context, tooltip, flag);
    }
}
