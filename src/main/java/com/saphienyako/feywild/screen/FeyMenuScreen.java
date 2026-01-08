package com.saphienyako.feywild.screen;


import com.mojang.blaze3d.matrix.MatrixStack;
import com.saphienyako.feywild.config.ModConfig;
import com.saphienyako.feywild.entity.Alignment;
import com.saphienyako.feywild.screen.widget.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;


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

    public FeyMenuScreen(ITextComponent name, int entityId, Alignment alignment, boolean followingPlayer, BlockPos currentBlockPos, boolean abilityActive, boolean voiceActive) {
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

        this.addWidget(new FeyMenuWidget(left + EntityWidget.WIDTH + 25, this.top, this.alignment)); // this.top + (BackgroundWidget.HEIGHT - EntityWidget.HEIGHT) / 2

        if (this.entityId != -1) {
            Entity entity = Minecraft.getInstance().level == null ? null : Minecraft.getInstance().level.getEntity(this.entityId);
            if (entity instanceof LivingEntity) {
                this.addWidget(new EntityWidget(left, (this.height - EntityWidget.HEIGHT) / 2, (LivingEntity)entity));
                this.addButton(new FollowButton(left + EntityWidget.WIDTH + 25 + ((FeyMenuWidget.WIDTH - FollowButton.WIDTH)/2), this.top + 4 +  FollowButton.HEIGHT, this.followingPlayer, this.entityId, this.currentBlockPos));
                this.addButton(new AbilityButton(left + EntityWidget.WIDTH + 25 + ((FeyMenuWidget.WIDTH - AbilityButton.WIDTH)/2), this.top + 12 + AbilityButton.HEIGHT * 2, this.abilityActive, this.entityId));
                this.addButton(new DismissButton(left + EntityWidget.WIDTH + 25 + ((FeyMenuWidget.WIDTH - AbilityButton.WIDTH)/2), this.top + 20 + AbilityButton.HEIGHT * 3, this, this.entityId));
                // TODO Button Quest
                if(ModConfig.CLIENT.voices_active.get()) {
                    this.addButton(new VolumeButton(left + EntityWidget.WIDTH + 25 + ((FeyMenuWidget.WIDTH - FollowButton.WIDTH) / 2), this.top + 28 + AbilityButton.HEIGHT * 6, this.voiceActive, this.entityId));
                }
            }
        }
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTick);
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
