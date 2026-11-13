package com.saphienyako.feywild.screen.widget;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.Alignment;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.MouseButtonInfo;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class FeyMenuWidget extends AbstractWidget {

    public static final int WIDTH = 157;
    public static final int HEIGHT = 210;

    private final Alignment alignment;

    private static final Map<Alignment, Identifier> TEXTURE = Map.of(
            Alignment.SPRING, Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/gui/spring_menu.png"),
            Alignment.SUMMER, Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/gui/summer_menu.png"),
            Alignment.AUTUMN, Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/gui/autumn_menu.png"),
            Alignment.WINTER, Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/gui/winter_menu.png"),
            Alignment.HEXEN, Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/gui/hexen_menu.png")
    );

    public FeyMenuWidget(int x, int y, Alignment alignment) {
        super(x, y, WIDTH, HEIGHT, Component.empty());
        this.alignment = alignment;
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE.get(this.alignment), this.getX(), this.getY(), 0, 0, WIDTH, HEIGHT, 256, 256);
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput output) {
        //
    }

    @Override
    protected boolean isValidClickButton(@NotNull MouseButtonInfo buttonInfo) {
        return false;
    }
}
