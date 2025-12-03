package com.saphienyako.feywild.screen.widget;

import com.mojang.blaze3d.vertex.PoseStack;
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
    public void renderButton(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        double scale = ((double) this.height / this.entity.getType().getHeight()) * 1.5;

        InventoryScreen.renderEntityInInventory(
                this.x + (this.width / 2),
                this.y + this.height + (int) (scale * 48 / 85),
                (int) scale,
                -(mouseX - this.x - (this.width / 2f)),
                -(mouseY - this.y - (this.height / 2f)),
                this.entity
        );
    }

    @Override
    public void updateNarration(@NotNull NarrationElementOutput output) {
        //
    }
}
