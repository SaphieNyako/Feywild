package com.feywild.feywild.screens.widget;

import com.feywild.feywild.FeywildMod;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.moddingx.libx.screen.Panel;

import javax.annotation.Nonnull;

public class BackgroundWidget extends Panel {

    public static final int WIDTH = 390;
    public static final int HEIGHT = 120;

    //TODO change textures
    public static final ResourceLocation BACKGROUND_TEXTURE_01 = new ResourceLocation(FeywildMod.getInstance().modid, "textures/gui/quest_background_01.png");
    public static final ResourceLocation BACKGROUND_TEXTURE_02 = new ResourceLocation(FeywildMod.getInstance().modid, "textures/gui/quest_background_02.png");
    public static final ResourceLocation BACKGROUND_TEXTURE_03 = new ResourceLocation(FeywildMod.getInstance().modid, "textures/gui/quest_background_03.png");
    public static final ResourceLocation BACKGROUND_TEXTURE_04 = new ResourceLocation(FeywildMod.getInstance().modid, "textures/gui/quest_background_04.png");


    public BackgroundWidget(Screen screen, int x, int y) {
        super(screen, x, y, WIDTH, HEIGHT);

    }

    @Override
    public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTick) {

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();

        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE_01);
        RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 0.5F);
        this.blit(poseStack, this.x, this.y, 0, 0, 240, HEIGHT);

        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE_02);
        RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 0.5F);
        this.blit(poseStack, this.x + 240, this.y, 0, 0, 140, HEIGHT);

        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE_03);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1F);
        this.blit(poseStack, this.x, this.y + 2, 0, 0, 240, HEIGHT);

        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE_04);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1F);
        this.blit(poseStack, this.x + 240, this.y + 2, 0, 0, 140, HEIGHT);
    }


}
