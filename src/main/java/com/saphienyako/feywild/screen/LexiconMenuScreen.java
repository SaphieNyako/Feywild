package com.saphienyako.feywild.screen;

import com.saphienyako.feywild.compat.ModCompat;
import com.saphienyako.feywild.entity.Alignment;
import com.saphienyako.feywild.network.DismissEntityMessage;
import com.saphienyako.feywild.screen.widget.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

public class LexiconMenuScreen extends Screen {
    private final int entityId;

    public LexiconMenuScreen(int entityId) {
        super(Component.empty());
        this.entityId = entityId;
    }

    @Override
    protected void init() {
        super.init();

        int left = (this.width / 2) - ((EntityWidget.WIDTH + 25 + FeyMenuWidget.WIDTH) / 2);
        int top = (this.height / 2) - (FeyMenuWidget.HEIGHT / 2);

        this.addRenderableWidget(new FeyMenuWidget(left + EntityWidget.WIDTH + 25, top, Alignment.HEXEN));

        Entity entity = Minecraft.getInstance().level == null ? null : Minecraft.getInstance().level.getEntity(this.entityId);
        if (entity instanceof LivingEntity living) {
            this.addRenderableWidget(new EntityWidget(EntityWidget.WIDTH, ((this.height - EntityWidget.HEIGHT) / 2) - 30, living));
        }

        if (ModCompat.QUEST_GIVER_LOADED) {
            this.addRenderableWidget(new QuestButton(left + EntityWidget.WIDTH + 25 + ((FeyMenuWidget.WIDTH - FollowButton.WIDTH)/2), top + 4 +  QuestButton.HEIGHT, this,"sprite", "hexen_quest", true, -1));
        }

        if (ModCompat.PATCHOULI_LOADED) {
            this.addRenderableWidget(new PatchouliButton(left + EntityWidget.WIDTH + 25 + ((FeyMenuWidget.WIDTH - PatchouliButton.WIDTH)/2), top + 12 + BellsnickelScreenButton.HEIGHT * 2, this));
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void onClose() {
        ClientPacketDistributor.sendToServer(new DismissEntityMessage(this.entityId));
        super.onClose();
    }
}
