package com.feywild.feywild.screens;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.ModEntityTypes;
import com.feywild.feywild.entity.SpringPixieEntity;
import com.feywild.feywild.entity.util.FeyEntity;
import com.feywild.feywild.network.FeywildPacketHandler;
import com.feywild.feywild.network.OpenQuestScreen;
import com.feywild.feywild.network.QuestMessage;
import com.feywild.feywild.network.RequestOpenQuestScreen;
import com.feywild.feywild.quest.MessageQuest;
import com.feywild.feywild.screens.widget.LookingGlassWidget;
import com.feywild.feywild.screens.widget.QuestWidget;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
// Ancient's note : good luck reading this and I am sorry >.<
// Tried my best to add comments
public class PixieScreen extends Screen {

    private final ResourceLocation GUI = new ResourceLocation(FeywildMod.MOD_ID, "textures/gui/pixie_quest_gui.png");
    List<StringTextComponent> lines = new LinkedList<>();
    List<MessageQuest> quests = new LinkedList<>();
    List<Widget> widgets = new LinkedList<>();
    StringTextComponent title = new StringTextComponent("");
    HashMap<Button,ResourceLocation> questMap = new HashMap<>();
    // Id dictates which kind of border should the items have
    int id;

    public PixieScreen(ITextComponent name, List<MessageQuest> quest, int id) {
        super(name);
        quests.addAll(quest);
        this.id = id;
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        renderBackground(matrixStack);
        drawTextLines(matrixStack, mouseX, mouseY);
        // render quest icons
        for(Widget child : this.widgets){
            child.render(matrixStack, mouseX, mouseY, partialTicks);
        }
        //render buttons
        for (Widget button : this.buttons) {
            button.render(matrixStack, mouseX, mouseY, partialTicks);
        }

    }

    @Override
    protected void init() {
        super.init();
        //Clear everything
        lines.clear();
        this.buttons.clear();
        this.widgets.clear();
        this.questMap.clear();
        //Wrap text
        if(quests.size() == 1) {
            String[] original = quests.get(0).getText().split("/n");

            for (String value : original) {
                StringTextComponent text = new StringTextComponent("");
                String[] string = value.split(" ");
                for (String s : string) {

                    if (s.contains("g&")) {
                        text.append(new StringTextComponent(s.replaceFirst("g&", "")).withStyle(TextFormatting.GREEN)).append(" ");
                    } else if (s.contains("r&")) {
                        text.append(new StringTextComponent(s.replaceFirst("r&", "")).withStyle(TextFormatting.RED)).append(" ");
                    } else if (s.contains("y&")) {
                        text.append(new StringTextComponent(s.replaceFirst("y&", "")).withStyle(TextFormatting.YELLOW)).append(" ");
                    } else if (s.contains("b&")) {
                        text.append(new StringTextComponent(s.replaceFirst("b&", "")).withStyle(TextFormatting.BLUE)).append(" ");
                    } else
                        text.append(s).withStyle(TextFormatting.WHITE).append(" ");
                }
                lines.add(text);
            }

            //Unicode char for checkmark
            int c = 0x2714;

            addButton(new Button(this.width / 6, this.height / 3 + 9 * lines.size() + 20, 20, 20, new StringTextComponent(Character.toString((char) c)), button -> {
                if(quests.get(0).canSkip())
                FeywildPacketHandler.INSTANCE.sendToServer(new QuestMessage("append&"+quests.get(0).getId().toString(),null));
                this.onClose();
            }));

            addButton(new Button(this.width / 6 + 40, this.height / 3 + 9 * lines.size() + 20, 20, 20, new StringTextComponent("x"), button -> {
                this.onClose();
            }));
        }else{
            //Load widgets if more quests are available
            int widgetSize = 24;
            int columns = 0, rows = 0, textSize = 0;
            for(int i = 0; i < quests.size(); i++){
                this.addWidget(new QuestWidget(this.width/6 + widgetSize * columns + textSize, (int)(this.height / 6 + widgetSize * 1.5 * rows),widgetSize,widgetSize, new StringTextComponent(quests.get(i).getName()), quests.get(i).getIcon(),id));
                bindButtonToQuest(new LookingGlassWidget(this.width/6 + widgetSize * columns  - widgetSize /2 -5 + textSize, (int)(this.height / 6 + widgetSize * 1.5 * rows) + 6, 12,12,new StringTextComponent(""), this::loadQuest), quests.get(i).getId());
                textSize = columns == 0 ? 0 : minecraft.font.width(quests.get(i - columns - rows).getName());
                columns = (i % 5 == 0  && i > 0 )? columns + 4 : columns;
                rows = (i % 5 == 0  && i > 0 )? 0 : rows + 1;
                columns = this.width/6 + widgetSize * i + widgetSize + widgetSize >= this.width ? columns + 4 : columns;
                rows = this.height / 6 + widgetSize * i + widgetSize + widgetSize >= height ? 0 : rows;
            }
        }
    }

    private void addWidget(Widget widget){
        widgets.add(widget);
    }

    private void bindButtonToQuest(Button button, ResourceLocation quest){
        questMap.put(button, quest);
        addButton(button);
    }

    private void loadQuest(Button button){
        FeywildPacketHandler.INSTANCE.sendToServer(new RequestOpenQuestScreen(questMap.get(button), id));
    }

    @Override
    public void renderBackground(@Nonnull MatrixStack matrixStack) {
        super.renderBackground(matrixStack);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bind(GUI);
        this.blit(matrixStack, 0, 0, 0, 0, 256, 256);
    }


    private void drawTextLines(MatrixStack matrixStack, int x , int y) {

        if(quests.size() == 1){
            // Draw quest text
            for (int i = 0; i < lines.size(); i++) {
                drawString(matrixStack,minecraft.font,lines.get(i),this.width/6,this.height / 3 + 9 * i,0xFFFFFF);
            }
        }else{
            // Draw quest names
            for(Widget widget : widgets){
                if(widget instanceof QuestWidget){
                    title = new StringTextComponent(((QuestWidget) widget).getComponent().getString());
                    drawString(matrixStack,minecraft.font,title,widget.x + 28 ,widget.y + 8,0xFFFFFF);
                }
            }
            title = new StringTextComponent("-Available Quests-");
            drawString(matrixStack,minecraft.font,title,this.width / 2 - (minecraft.font.width(title) / 2),this.height / 8,0xFFFFFF);
        }
    }
}
