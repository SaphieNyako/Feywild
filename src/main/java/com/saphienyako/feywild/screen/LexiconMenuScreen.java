package com.saphienyako.feywild.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.saphienyako.feywild.entity.Alignment;
import com.saphienyako.feywild.entity.ModEntities;
import com.saphienyako.feywild.entity.SpriteEntity;
import com.saphienyako.feywild.network.DismissEntityMessage;
import com.saphienyako.feywild.network.FeywildNetwork;
import com.saphienyako.feywild.screen.widget.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.NotNull;

public class LexiconMenuScreen extends Screen {

    @SuppressWarnings("FieldCanBeLocal")
    private int left;
    private int top;

    private final int entityId;

    public LexiconMenuScreen(int entityId) {
        super(Component.empty());
        this.entityId = entityId;
    }

    @Override
    protected void init() {
        super.init();

        this.left = (this.width / 2) - ((EntityWidget.WIDTH + 25 + FeyMenuWidget.WIDTH) / 2);
        this.top = (this.height / 2) - (FeyMenuWidget.HEIGHT / 2);

        this.addRenderableWidget(new FeyMenuWidget(left + EntityWidget.WIDTH, this.top, Alignment.HEXEN));

        Entity entity = Minecraft.getInstance().level == null ? null : Minecraft.getInstance().level.getEntity(this.entityId);
        if (entity instanceof LivingEntity living) {
            this.addRenderableWidget(new EntityWidget(EntityWidget.WIDTH, ((this.height - EntityWidget.HEIGHT) / 2) , living));
        }

        if (ModList.get().isLoaded("quest_giver")) {
            SpriteEntity sprite = ModEntities.SPRITE.get().create(Minecraft.getInstance().level);
            this.addRenderableWidget(new QuestButton(left + EntityWidget.WIDTH + ((FeyMenuWidget.WIDTH - FollowButton.WIDTH)/2), this.top + 4 +  QuestButton.HEIGHT, this,"sprite", "hexen_quest", true, -1));

        }

        if (ModList.get().isLoaded("patchouli")) {
            this.addRenderableWidget(new PatchouliButton(left + EntityWidget.WIDTH  + ((FeyMenuWidget.WIDTH - PatchouliButton.WIDTH)/2), this.top + 12 + BellsnickelScreenButton.HEIGHT * 2, this));
        }
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        poseStack.pushPose();
        this.renderBackground(poseStack);
        poseStack.translate(0, 0, 20);
        super.render(poseStack, mouseX, mouseY, partialTick);
        poseStack.translate(0, 0, 20);
        poseStack.popPose();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void onClose() {
        FeywildNetwork.sendToServer(new DismissEntityMessage(this.entityId));
        super.onClose();
    }
}
