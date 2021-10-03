package com.feywild.feywild.screens;

import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.screens.widget.BookWidget;
import com.feywild.feywild.util.LibraryBooks;
import com.feywild.feywild.util.TextProcessor;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.RenderComponentsUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class OpeningScreen extends Screen {

    List<IReorderingProcessor> text;
    List<ItemStack> itemStacks;
    int size;
    public OpeningScreen(int size) {
        super(new StringTextComponent(""));
        itemStacks = new LinkedList<>();
        this.size = size;
    }


    @Override
    protected void init() {
        super.init();
        text = TextProcessor.process(new TranslationTextComponent("screen.feywild.opening_screen")).stream().flatMap(line -> RenderComponentsUtil.wrapComponents(line, this.width - 40, Minecraft.getInstance().font).stream()).collect(Collectors.toList());
// pinnacle of laziness
        itemStacks.add(new ItemStack(ModItems.summoningScrollWinterPixie));
        itemStacks.add(new ItemStack(ModItems.summoningScrollAutumnPixie));
        itemStacks.add(new ItemStack(ModItems.summoningScrollSpringPixie));
        itemStacks.add(new ItemStack(ModItems.summoningScrollSummerPixie));

        int buttonsPerRow = Math.max(1, Math.min((this.width - 40) / (BookWidget.WIDTH + 4), this.itemStacks.size()));
        int paddingStart = (this.width - (buttonsPerRow * 29)) / 2;
        for (int i = 0; i < this.itemStacks.size(); i++) {
            this.addButton(new BookWidget(this, paddingStart + ((i % buttonsPerRow) * (BookWidget.WIDTH + 4)) + 2, 200 + ((BookWidget.HEIGHT + 4) * (i / buttonsPerRow)), i + size, this.itemStacks.get(i)));
        }
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        drawTextLines(matrixStack,mouseX,mouseY);
    }


    private void drawTextLines(MatrixStack matrixStack, int x, int y) {
        if (this.minecraft != null) {
            for (int i = 0; i < this.text.size(); i++) {
                this.minecraft.font.drawShadow(matrixStack, this.text.get(i), 20, 55 + ((2 + this.minecraft.font.lineHeight) * i), 0xFFFFFF);
            }

            for (Widget widget : this.buttons) {
                if (widget instanceof BookWidget && ((BookWidget) widget).isHovered(x, y)) {
                    ITextComponent name = widget.getMessage();
                    drawString(matrixStack, this.minecraft.font, name, this.width / 2 - (this.minecraft.font.width(name) / 2), 10 + this.minecraft.font.lineHeight + 2, 0xFFFFFF);
                    break;
                }
            }
        }
    }
}
