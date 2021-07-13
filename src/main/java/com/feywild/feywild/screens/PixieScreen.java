package com.feywild.feywild.screens;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.network.FeywildPacketHandler;
import com.feywild.feywild.network.QuestMessage;
import com.feywild.feywild.quest.QuestMap;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class PixieScreen extends Screen {

    private final ResourceLocation GUI = new ResourceLocation(FeywildMod.MOD_ID,
            "textures/gui/pixie_quest_gui.png");
    int lines, quest;
    int xPos = 0;
    boolean canSkip;

    public PixieScreen(ITextComponent name, int quest, int lines, boolean canSkip) {
        super(name);
        this.lines = lines;
        this.quest = quest;
        this.canSkip = canSkip;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        renderBackground(matrixStack);
        // Add system for different quest branches
        if (!this.minecraft.getWindow().isFullscreen()) {
            drawTextLines(this.width / 12, matrixStack);
        } else {
            drawTextLines(this.width / 4, matrixStack);
        }
    }

    @Override
    public void onClose() {
        if (canSkip)
            FeywildPacketHandler.INSTANCE.sendToServer(new QuestMessage(null, quest));
        super.onClose();
    }

    @Override
    public void renderBackground(MatrixStack matrixStack) {
        super.renderBackground(matrixStack);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bind(GUI);
        this.blit(matrixStack, 0, 0, 0, 0, 256, 256);
    }

    private void drawTextLines(int startingPosition, MatrixStack matrixStack) {

        for (int i = 1; i <= lines; i++) {

            xPos = 0;

            for (int j = 0; j < I18n.get("quest.feywild.quest_" + Math.abs(quest) + "_message_" + i).split(" ").length; j++) {
                String[] string = I18n.get("quest.feywild.quest_" + Math.abs(quest) + "_message_" + i).split(" ");
                if (string[j].startsWith("g&")) {
                    drawString(matrixStack, Minecraft.getInstance().font, string[j].replace("g&", ""), startingPosition + xPos, this.height / 3 + 9 * i, 0x66cc99);
                } else if (string[j].startsWith("r&")) {
                    drawString(matrixStack, Minecraft.getInstance().font, string[j].replace("r&", ""), startingPosition + xPos, this.height / 3 + 9 * i, 0xcc3333);
                } else if (string[j].startsWith("y&")) {
                    drawString(matrixStack, Minecraft.getInstance().font, string[j].replace("y&", ""), startingPosition + xPos, this.height / 3 + 9 * i, 0xffcc00);
                } else if (string[j].startsWith("b&")) {
                    drawString(matrixStack, Minecraft.getInstance().font, string[j].replace("b&", ""), startingPosition + xPos, this.height / 3 + 9 * i, 0x74deeb);
                } else
                    drawString(matrixStack, Minecraft.getInstance().font, string[j], startingPosition + xPos, this.height / 3 + 9 * i, 0xFFFFFF);
                xPos += Minecraft.getInstance().font.width(string[j].replace("g&", "").replace("r&", "").replace("y&", "").replace("b&", "")) + 5;
            }
        }

    }
}
