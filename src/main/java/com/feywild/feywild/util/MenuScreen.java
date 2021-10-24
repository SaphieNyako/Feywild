package com.feywild.feywild.util;

import com.feywild.feywild.FeywildMod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;

import javax.annotation.Nonnull;

public class MenuScreen extends TitleScreen {

    private static final ResourceLocation SPLASH = new ResourceLocation(FeywildMod.getInstance().modid, "textures/gui/background/panorama_0.png");

    @Override
    public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {

        RenderSystem.enableTexture();
        //noinspection deprecation
        RenderSystem.color4f(1, 1, 1, 1);
        this.getMinecraft().getTextureManager().bind(SPLASH);
        int width = this.width;
        int height = this.height;
        blit(poseStack, 0, 0, 0, 0, this.width, this.height, width, height);

        ForgeHooksClient.renderMainMenu(this, poseStack, this.getMinecraft().font, width, height, -1);
        drawString(poseStack, this.getMinecraft().font, "Copyright Mojang AB. Do not distribute!", width - this.font.width("Copyright Mojang AB. Do not distribute!") - 2, height - 10, 0xFFFFFFFF);
        for (net.minecraft.client.gui.components.AbstractWidget button : this.buttons) {
            button.render(poseStack, mouseX, mouseY, partialTicks);
        }
    }
}
