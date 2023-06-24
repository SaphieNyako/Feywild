package com.feywild.feywild.screens;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.menu.DwarvenAnvilMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.moddingx.libx.render.RenderHelper;

import javax.annotation.Nonnull;

public class DwarvenAnvilScreen extends AbstractContainerScreen<DwarvenAnvilMenu> {

    private static final ResourceLocation TEXTURE = FeywildMod.getInstance().resource("textures/gui/dwarven_anvil_gui.png");

    private final DwarvenAnvilMenu menu;

    public DwarvenAnvilScreen(DwarvenAnvilMenu menu, Inventory container, Component name) {
        super(menu, container, name);
        this.menu = menu;
    }

    @Override
    public void render(@Nonnull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        graphics.pose().pushPose();
        this.renderBackground(graphics);
        graphics.pose().translate(0, 0, 20);
        super.render(graphics, mouseX, mouseY, partialTicks);
        graphics.pose().translate(0, 0, 20);
        this.renderTooltip(graphics, mouseX, mouseY);
        graphics.pose().popPose();
    }

    @Override
    protected void renderLabels(@Nonnull GuiGraphics graphics, int x, int y) {
        graphics.drawString(Minecraft.getInstance().font, Component.translatable("screen.feywild.mana_amount", this.menu.getBlockEntity().getMana()), 118, 8, 0xffffff, false);
    }

    @Override
    protected void renderBg(@Nonnull GuiGraphics graphics, float partialTicks, int x, int y) {
        RenderHelper.resetColor();
        graphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        graphics.blit(TEXTURE, this.leftPos + 13, this.topPos + 9, 176, 0, 11, 64 - (int) (this.menu.getBlockEntity().getMana() / 15.6f));
    }
}
