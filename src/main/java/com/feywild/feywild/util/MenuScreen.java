package com.feywild.feywild.util;

import com.feywild.feywild.FeywildMod;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;

import javax.annotation.Nonnull;

public class MenuScreen extends MainMenuScreen {

    private static final ResourceLocation SPLASH = new ResourceLocation(FeywildMod.getInstance().modid, "textures/gui/background/panorama_0.png");

    @Override
    public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {

        RenderSystem.enableTexture();
        //noinspection deprecation
        RenderSystem.color4f(1, 1, 1, 1);
        this.getMinecraft().getTextureManager().bind(SPLASH);
        int width = this.width;
        int height = this.height;
        blit(matrixStack, 0, 0, 0, 0, this.width, this.height, width, height);

        ForgeHooksClient.renderMainMenu(this, matrixStack, this.getMinecraft().font, width, height, -1);
        drawString(matrixStack, this.getMinecraft().font, "Copyright Mojang AB. Do not distribute!", width - font.width("Copyright Mojang AB. Do not distribute!") - 2, height - 10, 0xFFFFFFFF);
        for (net.minecraft.client.gui.widget.Widget button : this.buttons) {
            button.render(matrixStack, mouseX, mouseY, partialTicks);
        }
    }
}
