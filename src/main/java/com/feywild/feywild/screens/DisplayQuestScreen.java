package com.feywild.feywild.screens;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.quest.QuestDisplay;
import com.feywild.feywild.util.FeywildTextProcessor;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ComponentRenderUtils;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.FormattedCharSequence;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

public class DisplayQuestScreen extends Screen {

    private final QuestDisplay display;
    private final boolean hasConfirmationButtons;
    private Component title;
    private List<FormattedCharSequence> description;

    public DisplayQuestScreen(QuestDisplay display, boolean hasConfirmationButtons) {
        super(display.title);
        this.display = display;
        this.hasConfirmationButtons = hasConfirmationButtons;
    }

    @Override
    protected void init() {
        super.init();
        this.title = FeywildTextProcessor.INSTANCE.processLine(this.display.title);
        this.description = FeywildTextProcessor.INSTANCE.process(this.display.description).stream().flatMap(line -> ComponentRenderUtils.wrapComponents(line, this.width - 40, Minecraft.getInstance().font).stream()).collect(Collectors.toList());

        if (this.hasConfirmationButtons) {
            int buttonY = Math.max((int) (this.height * (2 / 3d)), 65 + ((1 + this.description.size()) * (Minecraft.getInstance().font.lineHeight + 2)));
            this.addRenderableWidget(new Button(30, buttonY, 20, 20, new TextComponent(Character.toString((char) 0x2714)), button -> {
                FeywildMod.getNetwork().channel.sendToServer(new ConfirmQuestSerializer.Message(true));
                this.onClose();
            }));
            this.addRenderableWidget(new Button(70, buttonY, 20, 20, new TextComponent("x"), button -> {
                FeywildMod.getNetwork().channel.sendToServer(new ConfirmQuestSerializer.Message(false));
                this.onClose();
            }));
        }
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        this.drawTextLines(poseStack, mouseX, mouseY);
    }

    private void drawTextLines(PoseStack poseStack, int mouseX, int mouseY) {
        if (this.minecraft != null) {
            drawString(poseStack, this.minecraft.font, this.title, this.width / 2 - (this.minecraft.font.width(this.title) / 2), 20, 0xFFFFFF);
            for (int i = 0; i < this.description.size(); i++) {
                this.minecraft.font.drawShadow(poseStack, this.description.get(i), 20, 55 + ((2 + this.minecraft.font.lineHeight) * i), 0xFFFFFF);
            }
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
