package com.feywild.feywild.screens.widget;

import com.feywild.feywild.entity.ModEntities;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nonnull;

public class EntityWidget extends AbstractWidget {

    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;

    private final LivingEntity entity;

    public EntityWidget(int x, int y, LivingEntity entity) {
        super(x, y, WIDTH, HEIGHT, Component.empty());
        this.entity = entity;
    }

    @Override
    public void renderWidget(@Nonnull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        double scale = ((this.height) / this.entity.getType().getHeight()) * scale(this.entity.getType());
        InventoryScreen.renderEntityInInventoryFollowsAngle(
                graphics,
                this.getX() + (this.width / 2),
                this.getY() + this.height + (int) (scale * offset(this.entity.getType()) / 85),
                (int) scale,
                -(mouseX - this.getX() - (this.width / 2f)),
                -(mouseY - this.getY() - (this.height / 2f)),
                this.entity
        );
    }
    
    private static int offset(EntityType<?> type) {
        // Pixies have a lot of empty space below them that is part of their hitbox
        // Move them down a bit
        if (type == ModEntities.springPixie || type == ModEntities.summerPixie || type == ModEntities.autumnPixie || type == ModEntities.winterPixie) {
            return 48;
        } else {
            return 0;
        }
    }
    
    private static double scale(EntityType<?> type) {
        // Pixies have a lot of empty space below them that is part of their hitbox
        // Make them a bit bigger
        if (type == ModEntities.springPixie || type == ModEntities.summerPixie || type == ModEntities.autumnPixie || type == ModEntities.winterPixie) {
            return 1.8;
        } else {
            return 1;
        }
    }

    @Override
    protected void updateWidgetNarration(@Nonnull NarrationElementOutput output) {
        
    }
}
