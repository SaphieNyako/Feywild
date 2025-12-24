package com.saphienyako.feywild.screen.widget;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class EntityWidget extends AbstractWidget {

    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;

    private final LivingEntity entity;
    public EntityWidget(int x, int y, LivingEntity entity) {
        super(x, y, WIDTH, HEIGHT, Component.empty());
        this.entity = entity;
    }

    @Override
    public void renderWidget(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        double scale = ((double) this.height / this.entity.getType().getHeight()) * 1.5;

        int x1 = this.getX();
        int y1 = this.getY();
        int x2 = x1 + (this.width * 2);
        int y2 = y1 + this.height + (int) (scale * 48 / 85);

        float yOffset = 0f;

        InventoryScreen.renderEntityInInventoryFollowsMouse(
                graphics,
                x1, y1,
                x2, y2,
                (int) scale,
                yOffset,
                mouseX,
                mouseY,
                this.entity
        );
    }


    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput output) {
        //
    }
}
