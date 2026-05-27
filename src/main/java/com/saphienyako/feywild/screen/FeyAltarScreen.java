package com.saphienyako.feywild.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.saphienyako.feywild.Feywild;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class FeyAltarScreen extends AbstractContainerScreen<FeyAltarMenu> {

    private final FeyAltarMenu menu;


    public FeyAltarScreen(FeyAltarMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);

        imageWidth = 220;
        imageHeight = 200;

        this.menu = menu;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        ResourceLocation texture =
                ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID,
                        "textures/gui/fey_altar_" + menu.blockEntity.getAlignment().id + ".png");

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, texture);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(texture, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(guiGraphics, x, y);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        ResourceLocation texture =
                ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID,
                        "textures/gui/fey_altar_" + menu.blockEntity.getAlignment().id + ".png");
        if(menu.isCrafting()) {
            guiGraphics.blit(texture, x + 158, y + 50, 221, 13, 15, menu.getScaledProgress(), 256, 256);
        }
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics, mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        //
    }
}
