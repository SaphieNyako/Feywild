package com.feywild.feywild.screens.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nonnull;

public class EntityWidget extends AbstractWidget {

    public static final int WIDTH = 33;
    public static final int HEIGHT = 32;

    private final LivingEntity entity;

    public EntityWidget(Screen screen, int x, int y, LivingEntity entity) {
        super(x, y, WIDTH, HEIGHT, Component.empty());
        this.entity = entity;
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        InventoryScreen.renderEntityInInventory(this.x, this.y, 85, (float) -mouseX, 100 - (float) mouseY, this.entity);
    }

    @Override
    public void updateNarration(@Nonnull NarrationElementOutput output) {
        //
    }
}
