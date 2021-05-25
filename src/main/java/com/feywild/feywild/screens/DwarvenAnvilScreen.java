package com.feywild.feywild.screens;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.container.DwarvenAnvilContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class DwarvenAnvilScreen extends ContainerScreen<DwarvenAnvilContainer> {

    /* ScreenManager.register(ModContainers.DWARVEN_ANVIL_CONTAINER.get(), DwarvenAnvilScreen::new); in CLIENTPROXY */

    private final ResourceLocation GUI = new ResourceLocation(FeywildMod.MOD_ID,
            "textures/gui/dwarven_anvil_gui.png");

    public DwarvenAnvilScreen(DwarvenAnvilContainer container, PlayerInventory inventory, ITextComponent name) {
        super(container, inventory, name);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {

        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override //drawGuiContainerForegroundLayer
    protected void renderLabels(MatrixStack matrixStack, int x, int y) {
        super.renderLabels(matrixStack, x, y);
    }

    @Override //drawGuiContainerBackgroundLayer
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {

        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bind(GUI);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight); //might be wrong

    }
}
