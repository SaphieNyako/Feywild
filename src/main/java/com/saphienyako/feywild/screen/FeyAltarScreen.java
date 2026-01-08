package com.saphienyako.feywild.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.saphienyako.feywild.Feywild;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;


public class FeyAltarScreen extends ContainerScreen<FeyAltarMenu> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Feywild.MOD_ID, "textures/gui/fey_altar_gui.png");

    public FeyAltarScreen(FeyAltarMenu pMenu, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderBg(@Nonnull MatrixStack matrixStack, float v, int i, int i1) {
        this.minecraft.getTextureManager().getTexture(TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        this.blit(matrixStack, x, y, 0, 0, imageWidth, imageHeight);
        renderProgressArrow(matrixStack, x, y);
    }

    private void renderProgressArrow(MatrixStack matrixStack, int x, int y) {
        if(menu.isCrafting()) {
            blit(matrixStack, x + 143, y + 37, 178, 0, 15, menu.getScaledProgress());
        }
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTick) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTick);
        renderTooltip(matrixStack, mouseX, mouseY);
    }

}
