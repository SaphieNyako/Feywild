package com.feywild.feywild.screens.widget;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.network.quest.SelectQuestMessage;
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

    public static final int WIDTH = 160;
    public static final int HEIGHT = 40;

    //remove texture/gui/quest_atlas and looking_glass
    public static final ResourceLocation SELECTION_TEXTURE = new ResourceLocation(FeywildMod.getInstance().modid, "textures/gui/quest_background_05.png");

    // private final Alignment alignment;
    private final SelectableQuest quest;
    private final ItemStack iconStack;
    private final int id;
    private final Component title;

    public SelectQuestWidget(int x, int y, SelectableQuest quest, Component title, int id) {
        super(x, y, WIDTH, HEIGHT, FeywildTextProcessor.INSTANCE.processLine(quest.display().title), b -> {
        });
        this.quest = quest;
        this.iconStack = new ItemStack(quest.icon());
        this.id = id;
        this.title = title;
    }

    @Override
    public void onPress() {
        super.onPress();
        FeywildMod.getNetwork().channel.sendToServer(new SelectQuestMessage(this.quest.id(), title, id));
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, SELECTION_TEXTURE);
        RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 0.5F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.blit(poseStack, this.x, this.y, 0, 0, WIDTH, HEIGHT);

        Minecraft.getInstance().getItemRenderer().renderGuiItem(this.iconStack, this.x + 8, this.y + 10);

        //TODO check if below works as entended
        if (quest.display().title.getString().length() < 20) {
            String title_string = quest.display().title.getContents().toString();
            drawString(poseStack, font, title_string, this.x + 30, this.y + ((HEIGHT - font.lineHeight) / 2), 0xFFFFFF);
        } else {
            String title_string = quest.display().title.getString().substring(0, 20) + "...";
            drawString(poseStack, font, title_string, this.x + 30, this.y + ((HEIGHT - font.lineHeight) / 2), 0xFFFFFF);
        }
    }
}
