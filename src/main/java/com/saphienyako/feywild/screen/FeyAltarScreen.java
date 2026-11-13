package com.saphienyako.feywild.screen;

import com.saphienyako.feywild.Feywild;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class FeyAltarScreen extends AbstractContainerScreen<@NotNull FeyAltarMenu> {

    public FeyAltarScreen(FeyAltarMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, 220, 200);
    }

    private Identifier getTexture() {
        return Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/gui/fey_altar_" + this.menu.blockEntity.getAlignment().id + ".png");
    }

    @Override
    public void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        Identifier texture = getTexture();

        graphics.blit(RenderPipelines.GUI_TEXTURED, texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, 256, 256);

        this.renderProgressArrow(graphics, texture);
        super.extractContents(graphics, mouseX, mouseY, partialTick);
    }


    private void renderProgressArrow(GuiGraphicsExtractor graphics, Identifier texture) {
        if (this.menu.isCrafting()) {
            graphics.blit(RenderPipelines.GUI_TEXTURED, texture, this.leftPos + 158, this.topPos + 50, 221, 13, 15, this.menu.getScaledProgress(), 256, 256);
        }
    }
}
