package com.saphienyako.feywild.screen.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class EntityWidget extends AbstractWidget {

    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;

    private final LivingEntity entity;
    public EntityWidget(int x, int y, LivingEntity entity) {
        super(x, y, WIDTH, HEIGHT, new TextComponent(""));
        this.entity = entity;
    }


    @Override
    public void renderButton(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        //Note; in 1.19 make all intermediate calculations double before casting to int to prevent flickering of the model.

        double scale = ((double) this.height / this.entity.getType().getHeight()) * 1.5;
        double posX = this.x + this.width / 2.0;
        double posY = this.y + this.height + scale * 48.0 / 85.0;
        double centerX = this.x + this.width / 2.0;
        double centerY = this.y + this.height / 2.0;

        float deltaX = (float)(centerX - mouseX);
        float deltaY = (float)(centerY - mouseY);

        InventoryScreen.renderEntityInInventory(
                (int) posX,
                (int) posY,
                (int) scale,
                (float) deltaX,
                (float) deltaY,
                this.entity
        );
    }

    @Override
    public void updateNarration(@NotNull NarrationElementOutput output) {
        //
    }
}
