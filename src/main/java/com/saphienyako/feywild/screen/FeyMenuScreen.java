package com.saphienyako.feywild.screen;

import com.saphienyako.feywild.config.ModConfig;
import com.saphienyako.feywild.entity.Alignment;
import com.saphienyako.feywild.entity.BellsnickelEntity;
import com.saphienyako.feywild.entity.base.FeyBase;
import com.saphienyako.feywild.screen.widget.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class FeyMenuScreen extends Screen {

    private final int entityId;

    @SuppressWarnings("FieldCanBeLocal")
    private int left;
    private int top;
    private final Alignment alignment;
    private boolean followingPlayer;
    private BlockPos currentBlockPos;
    private boolean abilityActive;
    private boolean voiceActive;
    public FeyMenuScreen(Component name, int entityId, Alignment alignment, boolean followingPlayer, BlockPos currentBlockPos, boolean abilityActive, boolean voiceActive) {
        super(name);
        this.entityId = entityId;
        this.alignment = alignment;
        this.followingPlayer = followingPlayer;
        this.currentBlockPos = currentBlockPos;
        this.abilityActive = abilityActive;
        this.voiceActive = voiceActive;
    }

    @Override
    protected void init() {
        super.init();

        this.left = (this.width / 2) - ((EntityWidget.WIDTH + 25 + FeyMenuWidget.WIDTH) / 2);
        this.top = (this.height / 2) - (FeyMenuWidget.HEIGHT / 2);

        this.addRenderableWidget(new FeyMenuWidget(left + EntityWidget.WIDTH + 25, this.top, this.alignment)); // this.top + (BackgroundWidget.HEIGHT - EntityWidget.HEIGHT) / 2

        if (this.entityId != -1) {
            Entity entity = Minecraft.getInstance().level == null ? null : Minecraft.getInstance().level.getEntity(this.entityId);
            if (entity instanceof LivingEntity living) {
                this.addRenderableWidget(new EntityWidget(left, (this.height - EntityWidget.HEIGHT) / 2, living));
                this.addRenderableWidget(new FollowButton(left + EntityWidget.WIDTH + 25 + ((FeyMenuWidget.WIDTH - FollowButton.WIDTH)/2), this.top + 4 +  FollowButton.HEIGHT, this.followingPlayer, this.entityId, this.currentBlockPos));
                if (entity instanceof BellsnickelEntity bellsnickel) {
                    this.addRenderableWidget(new BellsnickelScreenButton(left + EntityWidget.WIDTH + 25 + ((FeyMenuWidget.WIDTH - BellsnickelScreenButton.WIDTH)/2), this.top + 12 + BellsnickelScreenButton.HEIGHT * 2, this.entityId));
                } else {
                    this.addRenderableWidget(new AbilityButton(left + EntityWidget.WIDTH + 25 + ((FeyMenuWidget.WIDTH - AbilityButton.WIDTH) / 2), this.top + 12 + AbilityButton.HEIGHT * 2, this.abilityActive, this.entityId));
                } this.addRenderableWidget(new DismissButton(left + EntityWidget.WIDTH + 25 + ((FeyMenuWidget.WIDTH - AbilityButton.WIDTH)/2), this.top + 20 + AbilityButton.HEIGHT * 3, this, this.entityId));
                // TODO Button Quest
                if(ModConfig.COMMON.voice_active.get()) {
                    this.addRenderableWidget(new VolumeButton(left + EntityWidget.WIDTH + 25 + ((FeyMenuWidget.WIDTH - FollowButton.WIDTH) / 2), this.top + 28 + AbilityButton.HEIGHT * 6, this.voiceActive, this.entityId));
                }
            }
        }
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        graphics.pose().pushPose();
        this.renderBackground(graphics);
        graphics.pose().translate(0, 0, 20);
        super.render(graphics, mouseX, mouseY, partialTicks);
        graphics.pose().translate(0, 0, 20);
        graphics.pose().popPose();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void onClose() {
        super.onClose();
    }
}
