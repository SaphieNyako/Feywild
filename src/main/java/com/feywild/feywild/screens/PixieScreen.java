package com.feywild.feywild.screens;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.container.PixieContainer;
import com.feywild.feywild.network.FeywildPacketHandler;
import com.feywild.feywild.network.QuestMessage;
import com.feywild.feywild.quest.QuestMap;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;
import net.minecraftforge.server.command.TextComponentHelper;

public class PixieScreen extends ContainerScreen<PixieContainer> {

    private final ResourceLocation GUI = new ResourceLocation(FeywildMod.MOD_ID,
            "textures/gui/pixie_quest_gui.png");

    private PixieContainer container;

    public PixieScreen(PixieContainer container, PlayerInventory inventory, ITextComponent name) {
        super(container, inventory, name);
        this.container = container;
    }

    @Override
    protected void init() {
        super.init();
        //Ancient's note : test button will be repurposed for quest completion
        if (QuestMap.getCanSkip(container.getQuest()))
            addButton(new Button(this.width / 2 - width / 3 + 20, this.height / 2 + this.getYSize() / 3, 20, 20, new StringTextComponent("X"), button -> {
                this.onClose();
            }));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {

        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    public void onClose() {
        if (QuestMap.getCanSkip(container.getQuest()))
            FeywildPacketHandler.INSTANCE.sendToServer(new QuestMessage(null, container.getQuest()));
        super.onClose();
    }

    @Override //drawGuiContainerForegroundLayer
    protected void renderLabels(MatrixStack matrixStack, int x, int y) {
        int xPos = 0;
        // Add system for different quest branches
        for (int i = 1; i <= container.getLines(); i++) {
            for(int j = 0; j < I18n.get("quest.feywild.quest_" + Math.abs(container.getQuest()) + "_message_" + i).split(" ").length; j++ ){
                String[] string = I18n.get("quest.feywild.quest_" + Math.abs(container.getQuest()) + "_message_" + i).split(" ");
                if(string[j].startsWith("g&")){
                    drawString(matrixStack, Minecraft.getInstance().font,string[j].replace("g&",""),  -width / 6 + xPos, 9 * i, 0xA1FB59);
                }else if(string[j].startsWith("r&")){
                    drawString(matrixStack, Minecraft.getInstance().font,string[j].replace("r&",""), -width / 6 + xPos , 9 * i, 0xD14959);
                }else
                    drawString(matrixStack, Minecraft.getInstance().font,string[j],  -width / 6 + xPos, 9 * i, 0xFFFFFF);
                xPos += Minecraft.getInstance().font.width(string[j].replace("g&", "").replace("r&", "")) + 5;
            }
            xPos = 0;
        }
    }

    @Override //drawGuiContainerBackgroundLayer
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {

        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bind(GUI);
        int i = this.leftPos - (this.imageWidth / 4);
        int j = this.topPos;
        this.blit(matrixStack, i, j, 0, 0, 256, this.imageHeight); //might be wrong
    }
}
