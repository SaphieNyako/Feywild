package com.feywild.feywild.screens;

import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.util.SelectableQuest;
import com.feywild.feywild.screens.widget.QuestWidget;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import javax.annotation.Nonnull;
import java.util.List;

public class SelectQuestScreen extends Screen {

    private final Alignment alignment;
    private final List<SelectableQuest> quests;

    public SelectQuestScreen(Component name, Alignment alignment, List<SelectableQuest> quests) {
        super(name);
        this.alignment = alignment;
        this.quests = ImmutableList.copyOf(quests);
    }

    @Override
    protected void init() {
        super.init();
        this.buttons.clear();
        for (int i = 0; i < this.quests.size(); i++) {
            this.addButton(new QuestWidget(20, 40 + ((QuestWidget.HEIGHT + 4) * i), this.alignment, this.quests.get(i)));
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
            drawString(poseStack, this.minecraft.font, this.title, this.width / 2 - (this.minecraft.font.width(this.title) / 2), 10, 0xFFFFFF);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
