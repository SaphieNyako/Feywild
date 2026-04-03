package com.saphienyako.feywild.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.BeeMountEntity;
import com.saphienyako.feywild.screen.widget.EntityWidget;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.Nonnull;

public class BeeKnightScreen extends AbstractContainerScreen<BeeKnightMenu> {

    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID,"textures/gui/bee_knight_menu.png");

    public static final int WIDTH = 208;
    public static final int HEIGHT = 192;
    private final BeeMountEntity beeKnight;
    private float xMouse;
    private float yMouse;
    public BeeKnightScreen(BeeKnightMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, Component.translatable("message.feywild.bee_knight_menu_description").withStyle(ChatFormatting.AQUA));
        this.beeKnight = menu.beeKnight;
        this.imageWidth = WIDTH;
        this.imageHeight = HEIGHT;
    }

    @Override
    protected void init() {
        super.init();
        titleLabelX = 25;
        titleLabelY = 64;

        inventoryLabelX = 1000;

        int entityX = this.leftPos - EntityWidget.WIDTH;
        int entityY = this.topPos + (this.imageHeight - EntityWidget.HEIGHT) / 2;

        this.addRenderableWidget(new EntityWidget(entityX, entityY, beeKnight));
    }

    @Override
    protected void renderBg(@Nonnull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int x = (width  - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        guiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(@Nonnull GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics, mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

}
