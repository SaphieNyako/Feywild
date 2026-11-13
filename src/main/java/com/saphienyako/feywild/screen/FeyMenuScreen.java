package com.saphienyako.feywild.screen;

import com.saphienyako.feywild.compat.ModCompat;
import com.saphienyako.feywild.config.FeywildConfig;
import com.saphienyako.feywild.entity.Alignment;
import com.saphienyako.feywild.entity.BeeKnightEntity;
import com.saphienyako.feywild.entity.BeeMountEntity;
import com.saphienyako.feywild.entity.BellsnickelEntity;
import com.saphienyako.feywild.entity.base.PixieBase;
import com.saphienyako.feywild.entity.base.TreeEntBase;
import com.saphienyako.feywild.screen.widget.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class FeyMenuScreen extends Screen {

    private final int entityId;
    private final Alignment alignment;
    private final boolean followingPlayer;
    private final BlockPos currentBlockPos;
    private final boolean abilityActive;

    private final boolean voiceActive;
    public FeyMenuScreen(int entityId, Alignment alignment, boolean followingPlayer, BlockPos currentBlockPos, boolean abilityActive, boolean voiceActive) {
        super(Component.empty());
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

        int left = (this.width / 2) - ((EntityWidget.WIDTH + 25 + FeyMenuWidget.WIDTH) / 2);
        int top = (this.height / 2) - (FeyMenuWidget.HEIGHT / 2);

        this.addRenderableWidget(new FeyMenuWidget(left + EntityWidget.WIDTH + 25, top, this.alignment));

        if (this.entityId == -1 || Minecraft.getInstance().level == null) {
            return;
        }

        Entity entity = Minecraft.getInstance().level.getEntity(this.entityId);

        if (!(entity instanceof LivingEntity living)) {
            return;
        }

        this.addRenderableWidget(new EntityWidget(EntityWidget.WIDTH, ((this.height - EntityWidget.HEIGHT) / 2) - 10, living));

        this.addTopButton(entity, left, top);
        this.addSecondButton(entity, left, top);
        this.addThirdButton(left, top);
        this.addFourthButton(entity, left, top);
    }

    private void addTopButton(Entity entity, int left, int top) {
        int x = left + EntityWidget.WIDTH + 25 + ((FeyMenuWidget.WIDTH - FollowButton.WIDTH) / 2);
        int y = top + 4 + FollowButton.HEIGHT;

        if (entity instanceof TreeEntBase) {
            this.addRenderableWidget(new MountButton(x, y, this, this.entityId));
        } else {
            this.addRenderableWidget(new FollowButton(x, y, this.followingPlayer, this.entityId, this.currentBlockPos));
        }
    }

    private void addSecondButton(Entity entity, int left, int top) {
        if (entity instanceof BellsnickelEntity) {
            int x = left + EntityWidget.WIDTH + 25 + ((FeyMenuWidget.WIDTH - BellsnickelScreenButton.WIDTH) / 2);
            int y = top + 12 + BellsnickelScreenButton.HEIGHT * 2;

            this.addRenderableWidget(new BellsnickelScreenButton(x, y, this.entityId));
        } else if (entity instanceof TreeEntBase) {
            int x = left + EntityWidget.WIDTH + 25 + ((FeyMenuWidget.WIDTH - EffectButton.WIDTH) / 2);
            int y = top + 12 + EffectButton.HEIGHT * 2;

            this.addRenderableWidget(new EffectButton(x, y, this, this.entityId));
        } else {
            int x = left + EntityWidget.WIDTH + 25 + ((FeyMenuWidget.WIDTH - AbilityButton.WIDTH) / 2);
            int y = top + 12 + AbilityButton.HEIGHT * 2;

            this.addRenderableWidget(new AbilityButton(x, y, this.abilityActive, this.entityId));
        }
    }

    private void addThirdButton(int left, int top) {
        int x = left + EntityWidget.WIDTH + 25 + ((FeyMenuWidget.WIDTH - DismissButton.WIDTH) / 2);
        int y = top + 20 + DismissButton.HEIGHT * 3;

        this.addRenderableWidget(new DismissButton(x, y, this, this.entityId));

        if (FeywildConfig.voicesActive) {
            int volumeX = left + EntityWidget.WIDTH + 25 + ((FeyMenuWidget.WIDTH - FollowButton.WIDTH) / 2);
            int volumeY = top + 28 + AbilityButton.HEIGHT * 6;

            this.addRenderableWidget(new VolumeButton(volumeX, volumeY, this.voiceActive, this.entityId));
        }
    }

    private void addFourthButton(Entity entity, int left, int top) {
        int x = left + EntityWidget.WIDTH + 25 + ((FeyMenuWidget.WIDTH - BeeKnightScreenButton.WIDTH) / 2);
        int y = top + 28 + BeeKnightScreenButton.HEIGHT * 4;

        if (entity instanceof BeeMountEntity) {
            this.addRenderableWidget(new BeeKnightScreenButton(x, y, this.entityId));
        } else if (ModCompat.QUEST_GIVER_LOADED && entity instanceof PixieBase pixie) {
            this.addRenderableWidget(new QuestButton(x, y, this, pixie.getQuestLineId(), pixie.getBackground(), false, this.entityId));
        }
    }

    @Override
    public void tick() {
        super.tick();
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.level == null || minecraft.player == null) {
            this.onClose();
            return;
        }

        Entity entity = minecraft.level.getEntity(this.entityId);

        if (!(entity instanceof LivingEntity living) || !living.isAlive() || living.isRemoved()) {
            this.onClose();
            return;
        }

        if (entity instanceof BeeMountEntity mount) {
            BeeKnightEntity knight = mount.getLinkedKnight();

            //noinspection ConstantValue
            if (knight == null || !knight.isAlive() || knight.isRemoved()) {
                this.onClose();
            }
        }
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
