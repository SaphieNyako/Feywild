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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class SelectQuestWidget extends Button {

    public static final int WIDTH = 210;
    public static final int HEIGHT = 65;
    
    private static final Map<Alignment, ResourceLocation> TEXTURES3 = Map.of(
            Alignment.SPRING, FeywildMod.getInstance().resource("textures/gui/spring_quest_background_03.png"),
            Alignment.SUMMER, FeywildMod.getInstance().resource("textures/gui/summer_quest_background_03.png"),
            Alignment.AUTUMN, FeywildMod.getInstance().resource("textures/gui/autumn_quest_background_03.png"),
            Alignment.WINTER, FeywildMod.getInstance().resource("textures/gui/winter_quest_background_03.png")
    );
    
    private final Alignment alignment;
    private final SelectableQuest quest;
    private final ItemStack iconStack;

    public SelectQuestWidget(int x, int y, SelectableQuest quest, Alignment alignment) {
        super(x, y, WIDTH, HEIGHT, FeywildTextProcessor.INSTANCE.processLine(quest.display().title), b -> {});
        this.quest = quest;
        this.iconStack = new ItemStack(quest.icon());
        this.alignment = alignment;
    }

    @Override
    public void onPress() {
        super.onPress();
        FeywildMod.getNetwork().channel.sendToServer(new SelectQuestMessage(this.quest.id()));
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, TEXTURES3.get(alignment));
        
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        
        this.blit(poseStack, this.x, this.y, 0, 0, WIDTH, HEIGHT);
        
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();
        
        Minecraft.getInstance().getItemRenderer().renderGuiItem(this.iconStack, this.x + 20, this.y + (this.height - 16) / 2);

        font.drawShadow(poseStack, quest.display().title, this.x + 38, this.y + ((HEIGHT - font.lineHeight) / 2f), 0xFFFFFF);
    }
}
