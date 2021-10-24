package com.feywild.feywild.screens;

import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.screens.widget.BookWidget;
import com.feywild.feywild.screens.widget.ScrollWidget;
import com.feywild.feywild.util.FeywildTextProcessor;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ComponentRenderUtils;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class OpeningScreen extends Screen {

    List<FormattedCharSequence> text;
    List<ItemStack> itemStacks;
    int size;
    public OpeningScreen(int size) {
        super(new TextComponent(""));
        itemStacks = new LinkedList<>();
        this.size = size;
        // pinnacle of laziness
        itemStacks.add(new ItemStack(ModItems.summoningScrollWinterPixie));
        itemStacks.add(new ItemStack(ModItems.summoningScrollAutumnPixie));
        itemStacks.add(new ItemStack(ModItems.summoningScrollSpringPixie));
        itemStacks.add(new ItemStack(ModItems.summoningScrollSummerPixie));
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    protected void init() {
        super.init();
        text = FeywildTextProcessor.INSTANCE.process(new TranslatableComponent("screen.feywild.opening_screen")).stream().flatMap(line -> ComponentRenderUtils.wrapComponents(line, this.width - 40, Minecraft.getInstance().font).stream()).collect(Collectors.toList());
        int buttonsPerRow = Math.max(1, Math.min((this.width - 40) / (BookWidget.WIDTH + 4), this.itemStacks.size()));
        int paddingStart = (this.width - (buttonsPerRow * 29)) / 2 - 54;
        for (int i = 0; i < this.itemStacks.size(); i++) {
            this.addRenderableWidget(new ScrollWidget(this, paddingStart + ((i % buttonsPerRow) * (BookWidget.WIDTH + 4)) + 2, 200 + ((BookWidget.HEIGHT + 4) * (i / buttonsPerRow)) - 50, i , this.itemStacks.get(i)));
        }
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        drawTextLines(poseStack,mouseX,mouseY);
    }


    private void drawTextLines(PoseStack poseStack, int x, int y) {
        if (this.minecraft != null) {

            this.minecraft.font.drawShadow(poseStack, this.text.get(0),  this.width / 2f - ( this.minecraft.font.width(this.text.get(0)) /2f ), 10, 0xFFFFFF);

            for (int i = 1; i < this.text.size(); i++) {
                this.minecraft.font.drawShadow(poseStack, this.text.get(i), 20, 55 + ((2 + this.minecraft.font.lineHeight) * i), 0xFFFFFF);
            }
        }
    }
}
