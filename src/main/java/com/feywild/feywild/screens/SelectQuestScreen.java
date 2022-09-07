package com.feywild.feywild.screens;

import com.feywild.feywild.events.ClientEvents;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.util.SelectableQuest;
import com.feywild.feywild.screens.widget.CharacterWidget;
import com.feywild.feywild.screens.widget.SelectQuestWidget;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

public class SelectQuestScreen extends Screen {

    private final List<SelectableQuest> quests;
    private final Alignment alignment;
    int CHARACTER_POSITION_Y = 200;
    int CHARACTER_POSITION_X = 95;
    int id;

    public SelectQuestScreen(Component name, List<SelectableQuest> quests, int id, Alignment alignment) {
        super(name);
        this.quests = ImmutableList.copyOf(quests);
        this.id = id;
        this.alignment = alignment;
    }

    @Override
    protected void init() {
        super.init();

        //Change Quest Widget
        for (int i = 0; i < this.quests.size(); i++) {
            this.addRenderableWidget(new SelectQuestWidget(this.width / 2 - (160 / 2), 40 + ((SelectQuestWidget.HEIGHT + 4) * i), this.quests.get(i), this.title, id, alignment));
        }
        //add Character Widget
        this.addRenderableWidget(new CharacterWidget(this, CHARACTER_POSITION_X, CHARACTER_POSITION_Y, (LivingEntity) Objects.requireNonNull(Objects.requireNonNull(minecraft).level).getEntity(id)));
        //ClientEvent
        ClientEvents.setShowGui(false);
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        super.render(poseStack, mouseX, mouseY, partialTicks);
        this.drawTextLines(poseStack, mouseX, mouseY);
    }

    private void drawTextLines(PoseStack poseStack, int mouseX, int mouseY) {
        if (this.minecraft != null) {
            drawString(poseStack, this.minecraft.font, this.title, (this.width / 2 - (this.minecraft.font.width(this.title) / 2)) + 20, 10, 0xFFFFFF);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    //on close
    @Override
    public void onClose() {
        ClientEvents.setShowGui(true);
        super.onClose();
    }
}
