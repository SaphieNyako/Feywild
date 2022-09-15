package com.feywild.feywild.screens.widget;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.quest.Alignment;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.moddingx.libx.screen.Panel;

import javax.annotation.Nonnull;

public class BackgroundWidget extends Panel {

    public static final int WIDTH = 390;
    public static final int HEIGHT = 145;
    public static final ResourceLocation AUTUMN_TEXTURE_01 = new ResourceLocation(FeywildMod.getInstance().modid, "textures/gui/autumn_quest_background_01.png");
    public static final ResourceLocation AUTUMN_TEXTURE_02 = new ResourceLocation(FeywildMod.getInstance().modid, "textures/gui/autumn_quest_background_02.png");
    public static final ResourceLocation SPRING_TEXTURE_01 = new ResourceLocation(FeywildMod.getInstance().modid, "textures/gui/spring_quest_background_01.png");
    public static final ResourceLocation SPRING_TEXTURE_02 = new ResourceLocation(FeywildMod.getInstance().modid, "textures/gui/spring_quest_background_02.png");
    public static final ResourceLocation SUMMER_TEXTURE_01 = new ResourceLocation(FeywildMod.getInstance().modid, "textures/gui/summer_quest_background_01.png");
    public static final ResourceLocation SUMMER_TEXTURE_02 = new ResourceLocation(FeywildMod.getInstance().modid, "textures/gui/summer_quest_background_02.png");
    public static final ResourceLocation WINTER_TEXTURE_01 = new ResourceLocation(FeywildMod.getInstance().modid, "textures/gui/winter_quest_background_01.png");
    public static final ResourceLocation WINTER_TEXTURE_02 = new ResourceLocation(FeywildMod.getInstance().modid, "textures/gui/winter_quest_background_02.png");
    private final Alignment alignment;

    public BackgroundWidget(Screen screen, int x, int y, Alignment alignment) {
        super(screen, x, y, WIDTH, HEIGHT);
        this.alignment = alignment;

    }

    @Override
    public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTick) {

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();

        if (alignment == Alignment.SPRING) {
            RenderSystem.setShaderTexture(0, SPRING_TEXTURE_01);
            this.blit(poseStack, this.x, this.y, 0, 0, 240, HEIGHT);

            RenderSystem.setShaderTexture(0, SPRING_TEXTURE_02);
            this.blit(poseStack, this.x + 240, this.y, 0, 0, 140, HEIGHT);
        }
        if (alignment == Alignment.AUTUMN) {
            RenderSystem.setShaderTexture(0, AUTUMN_TEXTURE_01);
            this.blit(poseStack, this.x, this.y, 0, 0, 240, HEIGHT);

            RenderSystem.setShaderTexture(0, AUTUMN_TEXTURE_02);
            this.blit(poseStack, this.x + 240, this.y, 0, 0, 140, HEIGHT);
        }
        if (alignment == Alignment.SUMMER) {
            RenderSystem.setShaderTexture(0, SUMMER_TEXTURE_01);
            this.blit(poseStack, this.x, this.y, 0, 0, 240, HEIGHT);

            RenderSystem.setShaderTexture(0, SUMMER_TEXTURE_02);
            this.blit(poseStack, this.x + 240, this.y, 0, 0, 140, HEIGHT);
        }
        if (alignment == Alignment.WINTER) {
            RenderSystem.setShaderTexture(0, WINTER_TEXTURE_01);
            this.blit(poseStack, this.x, this.y, 0, 0, 240, HEIGHT);

            RenderSystem.setShaderTexture(0, WINTER_TEXTURE_02);
            this.blit(poseStack, this.x + 240, this.y, 0, 0, 140, HEIGHT);
        }
    }


}
