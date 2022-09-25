package com.feywild.feywild.screens.widget;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.quest.Alignment;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Map;

public class BackgroundWidget extends AbstractWidget {

    public static final int WIDTH = 350;
    public static final int HEIGHT = 145;
    public static final int HORIZONTAL_PADDING = 35;
    public static final int VERTICAL_PADDING = 25;
    
    private static final Map<Alignment, ResourceLocation> TEXTURES1 = Map.of(
            Alignment.SPRING, FeywildMod.getInstance().resource("textures/gui/spring_quest_background_01.png"),
            Alignment.SUMMER, FeywildMod.getInstance().resource("textures/gui/summer_quest_background_01.png"),
            Alignment.AUTUMN, FeywildMod.getInstance().resource("textures/gui/autumn_quest_background_01.png"),
            Alignment.WINTER, FeywildMod.getInstance().resource("textures/gui/winter_quest_background_01.png")
    );
    
    private static final Map<Alignment, ResourceLocation> TEXTURES2 = Map.of(
            Alignment.SPRING, FeywildMod.getInstance().resource("textures/gui/spring_quest_background_02.png"),
            Alignment.SUMMER, FeywildMod.getInstance().resource("textures/gui/summer_quest_background_02.png"),
            Alignment.AUTUMN, FeywildMod.getInstance().resource("textures/gui/autumn_quest_background_02.png"),
            Alignment.WINTER, FeywildMod.getInstance().resource("textures/gui/winter_quest_background_02.png")
    );
    
    private final Alignment alignment;

    public BackgroundWidget(int x, int y, Alignment alignment) {
        super(x, y, WIDTH, HEIGHT, Component.empty());
        this.alignment = alignment;
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();

        RenderSystem.setShaderTexture(0, TEXTURES1.get(this.alignment));
        this.blit(poseStack, this.x, this.y, 0, 0, 240, HEIGHT);
        RenderSystem.setShaderTexture(0, TEXTURES2.get(this.alignment));
        this.blit(poseStack, this.x + 240, this.y, 0, 0, WIDTH - 240, HEIGHT);
        
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();
    }

    @Override
    public void updateNarration(@Nonnull NarrationElementOutput output) {
        //
    }
}
