package com.feywild.feywild.screens;

import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.screens.widget.ScrollWidget;
import com.feywild.feywild.util.FeywildTextProcessor;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ComponentRenderUtils;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

public class OpeningScreen extends Screen {

    private final List<ItemStack> stacks;

    private List<FormattedCharSequence> text;

    public OpeningScreen() {
        super(Component.empty());
        this.stacks = List.of(
                new ItemStack(ModItems.summoningScrollSpringPixie),
                new ItemStack(ModItems.summoningScrollSummerPixie),
                new ItemStack(ModItems.summoningScrollAutumnPixie),
                new ItemStack(ModItems.summoningScrollWinterPixie)
        );
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    protected void init() {
        super.init();
        text = FeywildTextProcessor.INSTANCE.process(Component.translatable("screen.feywild.opening_screen")).stream().flatMap(line -> ComponentRenderUtils.wrapComponents(line, this.width - 40, Minecraft.getInstance().font).stream()).collect(Collectors.toList());
        int paddingStart = Math.max(4, (this.width - (4 * (ScrollWidget.WIDTH + 4))) / 2);
        for (int i = 0; i < this.stacks.size(); i++) {
            this.addRenderableWidget(new ScrollWidget(this, paddingStart + (i * (ScrollWidget.WIDTH + 4)) + 2, 150, i, this.stacks.get(i)));
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
        drawTextLines(poseStack, mouseX, mouseY);
    }

    private void drawTextLines(PoseStack poseStack, int mouseX, int mouseY) {
        if (this.minecraft != null && this.text != null && !this.text.isEmpty()) {
            this.minecraft.font.drawShadow(poseStack, this.text.get(0), this.width / 2f - (this.minecraft.font.width(this.text.get(0)) / 2f), 10, 0xFFFFFF);

            for (int i = 1; i < this.text.size(); i++) {
                this.minecraft.font.drawShadow(poseStack, this.text.get(i), 20, 55 + ((2 + this.minecraft.font.lineHeight) * i), 0xFFFFFF);
            }
        }
    }
}
