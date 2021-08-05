package com.feywild.feywild.screens;

import com.feywild.feywild.screens.widget.BookWidget;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nonnull;
import java.util.List;

public class LibrarianScreen extends Screen {

    private final StringTextComponent title = new StringTextComponent("-Available Books-");
    private final List<ItemStack> stacks;

    public LibrarianScreen(ITextComponent name, List<ItemStack> stacks) {
        super(name);
        this.stacks = stacks;
    }

    @Override
    protected void init() {
        super.init();
        this.buttons.clear();
        int buttonsPerRow = Math.max(1, Math.min((this.width - 40) / (BookWidget.WIDTH + 4), this.stacks.size()));
        int paddingStart = (this.width - (buttonsPerRow * 29)) / 2;
        for (int i = 0; i < stacks.size(); i++) {
            addButton(new BookWidget(this, paddingStart + ((i % buttonsPerRow) * (BookWidget.WIDTH + 4)) + 2, 40 + ((BookWidget.HEIGHT + 4) * (i / buttonsPerRow)), i, stacks.get(i)));
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        drawTextLines(matrixStack, mouseX, mouseY);
    }

    private void drawTextLines(MatrixStack matrixStack, int x, int y) {
        if (minecraft != null) {
            drawString(matrixStack, minecraft.font, title, this.width / 2 - (minecraft.font.width(title) / 2), 10, 0xFFFFFF);
            for (Widget widget : buttons) {
                if (widget instanceof BookWidget && ((BookWidget) widget).isHovered(x, y)) {
                    ITextComponent name = widget.getMessage();
                    drawString(matrixStack, minecraft.font, name, this.width / 2 - (minecraft.font.width(name) / 2), 10 + minecraft.font.lineHeight + 2, 0xFFFFFF);
                    break;
                }
            }
        }
    }
}
