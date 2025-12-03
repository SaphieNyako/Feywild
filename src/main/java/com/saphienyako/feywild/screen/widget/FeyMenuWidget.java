package com.saphienyako.feywild.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.Alignment;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class FeyMenuWidget extends AbstractWidget {

    public static final int WIDTH = 157;
    public static final int HEIGHT = 210;

    private final Alignment alignment;

    private static final Map<Alignment, ResourceLocation> TEXTURE = Map.of(
            Alignment.SPRING, new ResourceLocation(Feywild.MOD_ID, "textures/gui/spring_menu.png"),
            Alignment.SUMMER, new ResourceLocation(Feywild.MOD_ID, "textures/gui/summer_menu.png"),
            Alignment.AUTUMN, new ResourceLocation(Feywild.MOD_ID, "textures/gui/autumn_menu.png"),
            Alignment.WINTER, new ResourceLocation(Feywild.MOD_ID, "textures/gui/winter_menu.png")
    );

    public FeyMenuWidget(int x, int y, Alignment alignment) {
        super(x, y, WIDTH, HEIGHT, Component.empty());
        this.alignment = alignment;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE.get(this.alignment));
        blit(poseStack, this.x, this.y, 0,0, WIDTH, HEIGHT);
    }

    @Override
    public void updateNarration(@NotNull NarrationElementOutput output) {

    }

    @Override
    protected boolean isValidClickButton(int pButton) {
        return false;
    }
}
