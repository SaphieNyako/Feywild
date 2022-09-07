package com.feywild.feywild.screens.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.entity.LivingEntity;
import org.moddingx.libx.screen.Panel;

import javax.annotation.Nonnull;

public class CharacterWidget extends Panel {

    //Better to make this independent?
    public static final int WIDTH = 33;
    public static final int HEIGHT = 32;

    private final LivingEntity entity;

    public CharacterWidget(Screen screen, int x, int y, LivingEntity entity) {
        super(screen, x, y, WIDTH, HEIGHT);
        this.entity = entity;
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        float xMouse = (float) mouseX;
        float yMouse = (float) mouseY;

        InventoryScreen.renderEntityInInventory(this.x, this.y, 65,
                (float) 0 - xMouse, (float) 100 - yMouse, this.entity);
    }
}
