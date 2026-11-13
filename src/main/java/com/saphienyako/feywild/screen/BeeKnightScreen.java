package com.saphienyako.feywild.screen;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.BeeMountEntity;
import com.saphienyako.feywild.screen.widget.EntityWidget;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class BeeKnightScreen extends AbstractContainerScreen<@NotNull BeeKnightMenu> {

    private static final Identifier GUI_TEXTURE = Identifier.fromNamespaceAndPath(Feywild.MOD_ID,"textures/gui/bee_knight_menu.png");

    public static final int WIDTH = 208;
    public static final int HEIGHT = 192;
    private final BeeMountEntity beeMount;
    private float xMouse;
    private float yMouse;


    public BeeKnightScreen(BeeKnightMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, Component.translatable("message.feywild.bee_knight_menu_description").withStyle(ChatFormatting.AQUA), WIDTH, HEIGHT);

        this.beeMount = menu.beeMount;
    }

    @Override
    protected void init() {
        super.init();
        titleLabelX = 60;
        titleLabelY = 23;

        inventoryLabelX = 1000;

        int entityX = this.leftPos - EntityWidget.WIDTH;
        int entityY = this.topPos + (this.imageHeight - EntityWidget.HEIGHT) / 2;

        int left = (this.width / 2) - ((EntityWidget.WIDTH + 125 + WIDTH) / 2);
        int top = (this.height / 2) - (HEIGHT / 2);

        this.addRenderableWidget(new EntityWidget(entityX, entityY, beeMount));
    }

    @Override
    public void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, 256, 256);

        super.extractContents(graphics, mouseX, mouseY, partialTick);
    }

}
