package com.feywild.feywild.screens;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.network.quest.ConfirmQuestSerializer;
import com.feywild.feywild.quest.QuestDisplay;
import com.feywild.feywild.util.TextProcessor;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.RenderComponentsUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

public class DisplayQuestScreen extends Screen {

    private final QuestDisplay display;
    private final boolean hasConfirmationButtons;
    private ITextComponent title;
    private List<IReorderingProcessor> description;

    public DisplayQuestScreen(QuestDisplay display, boolean hasConfirmationButtons) {
        super(display.title);
        this.display = display;
        this.hasConfirmationButtons = hasConfirmationButtons;
    }

    @Override
    protected void init() {
        super.init();
        this.title = TextProcessor.processLine(this.display.title);
        this.description = TextProcessor.process(this.display.description).stream().flatMap(line -> RenderComponentsUtil.wrapComponents(line, this.width - 40, Minecraft.getInstance().font).stream()).collect(Collectors.toList());

        this.buttons.clear();
        if (hasConfirmationButtons) {
            int buttonY = Math.max((int) (this.height * (2 / 3d)), 65 + ((1 + this.description.size()) * (Minecraft.getInstance().font.lineHeight + 2)));
            addButton(new Button(30, buttonY, 20, 20, new StringTextComponent(Character.toString((char) 0x2714)), button -> {
                FeywildMod.getNetwork().instance.sendToServer(new ConfirmQuestSerializer.Message(true));
                this.onClose();
            }));
            addButton(new Button(70, buttonY, 20, 20, new StringTextComponent("x"), button -> {
                FeywildMod.getNetwork().instance.sendToServer(new ConfirmQuestSerializer.Message(false));
                this.onClose();
            }));
        }
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        drawTextLines(matrixStack, mouseX, mouseY);
    }

    private void drawTextLines(MatrixStack matrixStack, int mouseX, int mouseY) {
        if (minecraft != null) {
            drawString(matrixStack, minecraft.font, title, this.width / 2 - (minecraft.font.width(this.title) / 2), 20, 0xFFFFFF);
            for (int i = 0; i < this.description.size(); i++) {
                minecraft.font.drawShadow(matrixStack, this.description.get(i), 20, 55 + ((2 + minecraft.font.lineHeight) * i), 0xFFFFFF);
            }
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
