package com.saphienyako.feywild.screen.widget;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.Alignment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class FeyMenuWidget extends AbstractWidget {

    public static final int WIDTH = 157;
    public static final int HEIGHT = 210;

    private final Alignment alignment;

    private static final Map<Alignment, ResourceLocation> TEXTURE = Map.of(
            Alignment.SPRING, ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "textures/gui/spring_menu.png"),
            Alignment.SUMMER, ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "textures/gui/summer_menu.png"),
            Alignment.AUTUMN, ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "textures/gui/autumn_menu.png"),
            Alignment.WINTER, ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "textures/gui/winter_menu.png")
    );

    public FeyMenuWidget(int x, int y, Alignment alignment) {
        super(x, y, WIDTH, HEIGHT, Component.empty());
        this.alignment = alignment;
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
        graphics.blit(TEXTURE.get(this.alignment), this.getX(), this.getY(), 0, 0, WIDTH, HEIGHT);
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput output) {
        //
    }

    @Override
    protected boolean isValidClickButton(int pButton) {
        return false;
    }
}
