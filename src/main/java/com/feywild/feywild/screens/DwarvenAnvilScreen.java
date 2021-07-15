package com.feywild.feywild.screens;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.container.DwarvenAnvilContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;

public class DwarvenAnvilScreen extends ContainerScreen<DwarvenAnvilContainer> {

    /* ScreenManager.register(ModContainers.DWARVEN_ANVIL_CONTAINER.get(), DwarvenAnvilScreen::new); in CLIENTPROXY */

    private final ResourceLocation GUI = new ResourceLocation(FeywildMod.MOD_ID,
            "textures/gui/dwarven_anvil_gui.png");
    private DwarvenAnvilContainer container;

    public DwarvenAnvilScreen(DwarvenAnvilContainer container, PlayerInventory inventory, ITextComponent name) {
        super(container, inventory, name);
        this.container = container;
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {

        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override //drawGuiContainerForegroundLayer
    protected void renderLabels(@Nonnull MatrixStack matrixStack, int x, int y) {

        //set text, position x, y, color
        drawString(matrixStack, Minecraft.getInstance().font, "Mana: " + container.getMana(), 118, 8, 0xffffff);

    }

    @Override //drawGuiContainerBackgroundLayer
    protected void renderBg(@Nonnull MatrixStack matrixStack, float partialTicks, int x, int y) {

        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bind(GUI);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight); //might be wrong

        //position to place x, y position to get x, y width height and height is determined by the manalevel
        this.blit(matrixStack, i + 13, j + 9, 176, 0, 11, 64 - (int) (container.getMana() / 15.6f));

    }

}
