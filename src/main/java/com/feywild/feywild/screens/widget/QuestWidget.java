package com.feywild.feywild.screens.widget;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.network.quest.SelectQuestSerializer;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.util.SelectableQuest;
import com.feywild.feywild.util.TextProcessor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class QuestWidget extends Button {

    public static final int WIDTH = 40;
    public static final int HEIGHT = 24;
    
    public static final ResourceLocation SELECTION_TEXTURE = new ResourceLocation(FeywildMod.getInstance().modid, "textures/gui/looking_glass.png");
    public static final ResourceLocation SLOT_TEXTURE = new ResourceLocation(FeywildMod.getInstance().modid, "textures/gui/quest_atlas.png");
    
    private final Alignment alignment;
    private final SelectableQuest quest;
    private final ItemStack iconStack;

    public QuestWidget(int x, int y, Alignment alignment, SelectableQuest quest) {
        super(x, y, WIDTH, HEIGHT, TextProcessor.processLine(quest.display.title), b -> {});
        this.alignment = alignment;
        this.quest = quest;
        this.iconStack = new ItemStack(quest.icon);
    }

    @Override
    public void onPress() {
        super.onPress();
        FeywildMod.getNetwork().instance.sendToServer(new SelectQuestSerializer.Message(this.quest.id));
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        //noinspection deprecation
        RenderSystem.color4f(1, 1, 1, 1);
        Minecraft.getInstance().getTextureManager().bind(SELECTION_TEXTURE);
        if (this.isHovered(mouseX, mouseY)) {
            this.blit(poseStack, this.x, this.y + 5, 12, 0, 14, 14);
        } else {
            this.blit(poseStack, this.x, this.y + 5, 0, 0, 12, 12);
        }
        Minecraft.getInstance().getTextureManager().bind(SLOT_TEXTURE);
        this.blit(poseStack, this.x + 15, this.y, this.alignment.ordinal() * 25, 0, 24, 24);
        Minecraft.getInstance().getItemRenderer().renderGuiItem(this.iconStack,this.x + 19,this.y + 4);
        Font font = Minecraft.getInstance().font;
        drawString(poseStack, font, this.getMessage(), this.x + 44, this.y + ((HEIGHT - font.lineHeight) / 2), 0xFFFFFF);
    }
    
    public boolean isHovered(int x, int y) {
        return this.x <= x && this.x + WIDTH >= x && this.y <= y && this.y + HEIGHT >= y;
    }
}
