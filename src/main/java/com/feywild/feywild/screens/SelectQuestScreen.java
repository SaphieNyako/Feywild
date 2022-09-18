package com.feywild.feywild.screens;

import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.util.SelectableQuest;
import com.feywild.feywild.screens.widget.EntityWidget;
import com.feywild.feywild.screens.widget.SelectQuestWidget;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nonnull;
import java.util.List;

public class SelectQuestScreen extends Screen {

    private static final int CHARACTER_POSITION_Y = 200;
    private static final int CHARACTER_POSITION_X = 95;
    
    private final List<SelectableQuest> quests;
    private final Alignment alignment;
    private final int entityId;

    public SelectQuestScreen(Component name, List<SelectableQuest> quests, int entityId, Alignment alignment) {
        super(name);
        this.quests = ImmutableList.copyOf(quests);
        this.entityId = entityId;
        this.alignment = alignment;
    }

    @Override
    protected void init() {
        super.init();
        for (int i = 0; i < this.quests.size(); i++) {
            this.addRenderableWidget(new SelectQuestWidget(this.width / 2 - (160 / 2), 40 + ((SelectQuestWidget.HEIGHT + 4) * i), this.quests.get(i), alignment));
        }

        if (this.entityId != -1) {
            Entity entity = Minecraft.getInstance().level == null ? null : Minecraft.getInstance().level.getEntity(this.entityId);
            if (entity instanceof LivingEntity living) {
                this.addRenderableWidget(new EntityWidget(this, CHARACTER_POSITION_X, CHARACTER_POSITION_Y, living));
            }
        }
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
}
