package com.saphienyako.feywild.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.BellsnickelEntity;
import com.saphienyako.feywild.screen.widget.EntityWidget;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.Nonnull;

public class BellsnickelScreen extends AbstractContainerScreen<BellsnickelMenu> {

    private static final ResourceLocation GUI_TEXTURE =
           new ResourceLocation(Feywild.MOD_ID,"textures/gui/bellsnickel_menu.png");

    public static final int WIDTH = 208;
    public static final int HEIGHT = 188;
    private final BellsnickelEntity bellsnickel;
    private float xMouse;
    private float yMouse;
    public BellsnickelScreen(BellsnickelMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, Component.translatable("message.feywild.bellsnickel_menu_description").withStyle(ChatFormatting.AQUA));
        this.bellsnickel = menu.bellsnickel;
        this.imageWidth = WIDTH;
        this.imageHeight = HEIGHT;
    }

    @Override
    protected void init() {
        super.init();
        titleLabelX = 25;
        titleLabelY = 64;

        inventoryLabelX = 1000;

        int entityX = this.leftPos - EntityWidget.WIDTH - 30;
        int entityY = this.topPos + (this.imageHeight - EntityWidget.HEIGHT) / 2 + 10;

        this.addRenderableWidget(new EntityWidget(entityX, entityY, bellsnickel));
        //TODO add Buttons
    }


    @Override
    protected void renderBg(@Nonnull PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int x = (width  - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        blit(poseStack,x, y, 0, 0, imageWidth, imageHeight);
    }


    @Override
    public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float delta) {
        renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, delta);
        renderTooltip(poseStack, mouseX, mouseY);
    }
}
