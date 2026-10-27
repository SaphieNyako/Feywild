package com.saphienyako.feywild.events;

import com.mojang.blaze3d.platform.InputConstants;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.block.ModBlocks;
import com.saphienyako.feywild.compat.ModCompat;
import com.saphienyako.feywild.item.BossSummoningScrollItem;
import com.saphienyako.feywild.item.PixieWingTiaraItem;
import com.saphienyako.feywild.item.base.ToolTipBaseItem;
import com.saphienyako.feywild.item.base.ToolTipBaseItemNameBlockItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber(modid = Feywild.MOD_ID, value = Dist.CLIENT)
public class ClientEventListener {

    /*
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        Window window = Minecraft.getInstance().getWindow();
        //TODO check if this works?
        boolean shiftDown = InputConstants.isKeyDown(window, 340) || InputConstants.isKeyDown(window, 344);
        ItemStack stack = event.getItemStack();

        if (stack.is(ModBlocks.FEY_ALTAR.get().asItem())
                || stack.is(ModBlocks.FEY_ALTAR_QUEEN_TITANIA.get().asItem())
                || stack.is(ModBlocks.FEY_ALTAR_QUEEN_MAB.get().asItem())
                || stack.is(ModBlocks.FEY_ALTAR_ASHEN_LORD.get().asItem())
                || stack.is(ModBlocks.FEY_ALTAR_OBERON.get().asItem())) {

            if (shiftDown) {
                event.getToolTip().add(Component.translatable("message.feywild.fey_altar").withStyle(ChatFormatting.BLUE));
            } else {
                event.getToolTip().add(Component.translatable("message.feywild.shift_down").withStyle(ChatFormatting.GREEN));
            }
        }
    } */

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();

        Component tooltip = null;

        if (item instanceof ToolTipBaseItem tooltipItem) {
            tooltip = tooltipItem.getTooltipComponent();
        } else if (item instanceof ToolTipBaseItemNameBlockItem tooltipBlockItem) {
            tooltip = tooltipBlockItem.getTooltipComponent();
        }

        if (tooltip == null) {
            return;
        }

        Window window = Minecraft.getInstance().getWindow();
        boolean shiftDown = InputConstants.isKeyDown(window, 340) || InputConstants.isKeyDown(window, 344);
        //TODO Is Shift Down needed? Make a separate Tooltip with Recipe??
        if (shiftDown) {
            event.getToolTip().add(tooltip.copy().withStyle(ChatFormatting.BLUE));
        } else {
            event.getToolTip().add(Component.translatable("message.feywild.shift_down").withStyle(ChatFormatting.GREEN));

            if (item instanceof BossSummoningScrollItem) {
                event.getToolTip().add(Component.translatable("message.feywild.boss_fight").withStyle(ChatFormatting.RED));
            }

            if (item instanceof PixieWingTiaraItem) {
                String translationKey = ModCompat.CURIOS_LOADED ? "message.feywild.curios_head_slot" : "message.feywild.off_hand_slot";
                event.getToolTip().add(Component.translatable(translationKey).withStyle(ChatFormatting.LIGHT_PURPLE));
            }
        }
    }

}
