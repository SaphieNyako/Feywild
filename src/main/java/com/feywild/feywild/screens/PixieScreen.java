package com.feywild.feywild.screens;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.container.PixieContainer;
import com.feywild.feywild.network.FeywildPacketHandler;
import com.feywild.feywild.network.QuestMessage;
import com.feywild.feywild.quest.QuestMap;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;
import net.minecraftforge.server.command.TextComponentHelper;

public class PixieScreen extends Screen {

    private final ResourceLocation GUI = new ResourceLocation(FeywildMod.MOD_ID,
            "textures/gui/pixie_quest_gui.png");
    int lines, quest;

    public PixieScreen(ITextComponent name, int quest, int lines) {
        super(name);
        this.lines = lines;
        this.quest = quest;
    }
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        int xPos = 0;
        renderBackground(matrixStack);
        // Add system for different quest branches
        if(!this.minecraft.getWindow().isFullscreen()) {
            for (int i = 1; i <= lines; i++) {
                for (int j = 0; j < I18n.get("quest.feywild.quest_" + Math.abs(quest) + "_message_" + i).split(" ").length; j++) {
                    String[] string = I18n.get("quest.feywild.quest_" + Math.abs(quest) + "_message_" + i).split(" ");
                    if (string[j].startsWith("g&")) {
                        drawString(matrixStack, Minecraft.getInstance().font, string[j].replace("g&", ""), this.width / 12 + xPos, this.height / 3 + 9 * i, 0xA1FB59);
                    } else if (string[j].startsWith("r&")) {
                        drawString(matrixStack, Minecraft.getInstance().font, string[j].replace("r&", ""), this.width / 12 + xPos, this.height / 3 + 9 * i, 0xD14959);
                    } else
                        drawString(matrixStack, Minecraft.getInstance().font, string[j], this.width / 12 + xPos, this.height / 3 + 9 * i, 0xFFFFFF);
                    xPos += Minecraft.getInstance().font.width(string[j].replace("g&", "").replace("r&", "")) + 5;
                }
                xPos = 0;
            }
        }else{
            for (int i = 1; i <= lines; i++) {
                for (int j = 0; j < I18n.get("quest.feywild.quest_" + Math.abs(quest) + "_message_" + i).split(" ").length; j++) {
                    String[] string = I18n.get("quest.feywild.quest_" + Math.abs(quest) + "_message_" + i).split(" ");
                    if (string[j].startsWith("g&")) {
                        drawString(matrixStack, Minecraft.getInstance().font, string[j].replace("g&", ""), this.width / 4 + xPos, this.height / 3 + 9 * i, 0xA1FB59);
                    } else if (string[j].startsWith("r&")) {
                        drawString(matrixStack, Minecraft.getInstance().font, string[j].replace("r&", ""), this.width / 4 + xPos, this.height / 3 + 9 * i, 0xD14959);
                    } else
                        drawString(matrixStack, Minecraft.getInstance().font, string[j], this.width / 4 + xPos, this.height / 3 + 9 * i, 0xFFFFFF);
                    xPos += Minecraft.getInstance().font.width(string[j].replace("g&", "").replace("r&", "")) + 5;
                }
                xPos = 0;
            }
        }
    }

    @Override
    public void onClose() {
        if (QuestMap.getCanSkip(quest))
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
}
