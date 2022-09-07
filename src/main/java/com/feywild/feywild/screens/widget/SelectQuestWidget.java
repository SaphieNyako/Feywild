package com.feywild.feywild.screens.widget;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.network.quest.SelectQuestMessage;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.util.SelectableQuest;
import com.feywild.feywild.util.FeywildTextProcessor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class SelectQuestWidget extends Button {

    public static final int WIDTH = 185;
    public static final int HEIGHT = 65;

    public static final ResourceLocation AUTUMN_TEXTURE_03 = new ResourceLocation(FeywildMod.getInstance().modid, "textures/gui/autumn_quest_background_03.png");
    public static final ResourceLocation SPRING_TEXTURE_03 = new ResourceLocation(FeywildMod.getInstance().modid, "textures/gui/spring_quest_background_03.png");
    public static final ResourceLocation SUMMER_TEXTURE_03 = new ResourceLocation(FeywildMod.getInstance().modid, "textures/gui/summer_quest_background_03.png");
    public static final ResourceLocation WINTER_TEXTURE_03 = new ResourceLocation(FeywildMod.getInstance().modid, "textures/gui/winter_quest_background_03.png");

    private final Alignment alignment;
    private final SelectableQuest quest;
    private final ItemStack iconStack;
    private final int id;
    private final Component title;

    public SelectQuestWidget(int x, int y, SelectableQuest quest, Component title, int id, Alignment alignment) {
        super(x, y, WIDTH, HEIGHT, FeywildTextProcessor.INSTANCE.processLine(quest.display().title), b -> {
        });
        this.quest = quest;
        this.iconStack = new ItemStack(quest.icon());
        this.id = id;
        this.title = title;
        this.alignment = alignment;
    }

    @Override
    public void onPress() {
        super.onPress();
        FeywildMod.getNetwork().channel.sendToServer(new SelectQuestMessage(this.quest.id(), title, id, alignment));
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        if (alignment == Alignment.AUTUMN) {
            RenderSystem.setShaderTexture(0, AUTUMN_TEXTURE_03);
        }
        if (alignment == Alignment.SPRING) {
            RenderSystem.setShaderTexture(0, SPRING_TEXTURE_03);
        }
        if (alignment == Alignment.SUMMER) {
            RenderSystem.setShaderTexture(0, SUMMER_TEXTURE_03);
        }
        if (alignment == Alignment.WINTER) {
            RenderSystem.setShaderTexture(0, WINTER_TEXTURE_03);
        }
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.blit(poseStack, this.x, this.y, 0, 0, WIDTH, HEIGHT);

        Minecraft.getInstance().getItemRenderer().renderGuiItem(this.iconStack, this.x + WIDTH / 2, this.y - 10);

        if (quest.display().title.getString().length() < 20) {
            String title_string = quest.display().title.getContents().toString();
            drawString(poseStack, font, title_string, this.x + 35, this.y + ((HEIGHT - font.lineHeight) / 2), 0xFFFFFF);
        } else {
            String title_string = quest.display().title.getString().substring(0, 20) + "...";
            drawString(poseStack, font, title_string, this.x + 35, this.y + ((HEIGHT - font.lineHeight) / 2), 0xFFFFFF);
        }
    }
}
