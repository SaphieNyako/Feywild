package com.feywild.feywild.util;

import com.feywild.feywild.FeywildMod;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;

import javax.annotation.Nonnull;

public class MenuScreen extends MainMenuScreen {

    private static final ResourceLocation SPLASH = new ResourceLocation(FeywildMod.MOD_ID, "textures/gui/background/panorama_0.png");

    @Override
    public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {

        GlStateManager._enableTexture();
        GlStateManager._color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.getMinecraft().getTextureManager().bind(SPLASH);
        int width = this.width;
        int height = this.height;
        blit(matrixStack, 0, 0, 0, 0, this.width, this.height, width, height);

        ForgeHooksClient.renderMainMenu(this, matrixStack, this.getMinecraft().font, width, height);
        drawString(matrixStack, this.getMinecraft().font, "Copyright Mojang AB. Do not distribute!", width - font.width("Copyright Mojang AB. Do not distribute!") - 2, height - 10, 0xFFFFFFFF);
        for (net.minecraft.client.gui.widget.Widget button : this.buttons) {
            button.render(matrixStack, mouseX, mouseY, partialTicks);
        }
    }
}
