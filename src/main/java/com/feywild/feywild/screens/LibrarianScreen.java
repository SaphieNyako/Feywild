package com.feywild.feywild.screens;

import com.feywild.feywild.screens.widget.BookWidget;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class LibrarianScreen extends Screen {

    private final TextComponent title = new TextComponent("-Available Books-");
    private final List<ItemStack> stacks;
    private final List<AbstractWidget> widgets;

    public LibrarianScreen(Component name, List<ItemStack> stacks) {
        super(name);
        this.stacks = stacks;
        this.widgets = new ArrayList<>();
    }

    @Override
    protected void init() {
        super.init();
        widgets.clear();
        int buttonsPerRow = Math.max(1, Math.min((this.width - 40) / (BookWidget.WIDTH + 4), this.stacks.size()));
        int paddingStart = (this.width - (buttonsPerRow * 29)) / 2;
        for (int i = 0; i < this.stacks.size(); i++) {
            AbstractWidget widget = new BookWidget(this, paddingStart + ((i % buttonsPerRow) * (BookWidget.WIDTH + 4)) + 2, 40 + ((BookWidget.HEIGHT + 4) * (i / buttonsPerRow)), i, this.stacks.get(i));
            this.addRenderableWidget(widget);
            widgets.add(widget);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        this.drawTextLines(poseStack, mouseX, mouseY);
    }

    private void drawTextLines(PoseStack poseStack, int x, int y) {
        if (this.minecraft != null) {
            drawString(poseStack, this.minecraft.font, this.title, this.width / 2 - (this.minecraft.font.width(this.title) / 2), 10, 0xFFFFFF);
            for (AbstractWidget widget : widgets) {
                if (widget instanceof BookWidget && ((BookWidget) widget).isHovered(x, y)) {
                    Component name = widget.getMessage();
                    drawString(poseStack, this.minecraft.font, name, this.width / 2 - (this.minecraft.font.width(name) / 2), 10 + this.minecraft.font.lineHeight + 2, 0xFFFFFF);
                    break;
                }
            }
        }
    }
}
