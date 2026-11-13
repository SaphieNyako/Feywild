package com.saphienyako.feywild.screen;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.BellsnickelEntity;
import com.saphienyako.feywild.screen.widget.EntityWidget;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class BellsnickelScreen extends AbstractContainerScreen<@NotNull BellsnickelMenu> {

    private static final Identifier GUI_TEXTURE = Identifier.fromNamespaceAndPath(Feywild.MOD_ID,"textures/gui/bellsnickel_menu.png");

    public static final int WIDTH = 208;
    public static final int HEIGHT = 188;
    private final BellsnickelEntity bellsnickel;
    public BellsnickelScreen(BellsnickelMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, Component.translatable("message.feywild.bellsnickel_menu_description").withStyle(ChatFormatting.AQUA), WIDTH, HEIGHT);

        this.bellsnickel = menu.bellsnickel;
    }

    @Override
    protected void init() {
        super.init();
        titleLabelX = 25;
        titleLabelY = 64;

        inventoryLabelX = 1000;

        int entityX = this.leftPos - EntityWidget.WIDTH - 30;
        int entityY = this.topPos + (this.imageHeight - EntityWidget.HEIGHT) / 2;

        this.addRenderableWidget(new EntityWidget(entityX, entityY, bellsnickel));
    }

    @Override
    public void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, 256, 256);
        super.extractContents(graphics, mouseX, mouseY, partialTick);
    }
}
