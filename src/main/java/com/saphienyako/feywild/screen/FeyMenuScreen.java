package com.saphienyako.feywild.screen;

import com.saphienyako.feywild.compat.ModCompat;
import com.saphienyako.feywild.config.ModConfig;
import com.saphienyako.feywild.entity.Alignment;
import com.saphienyako.feywild.entity.BeeKnightEntity;
import com.saphienyako.feywild.entity.BeeMountEntity;
import com.saphienyako.feywild.entity.BellsnickelEntity;
import com.saphienyako.feywild.entity.base.FeyBase;
import com.saphienyako.feywild.entity.base.PixieBase;
import com.saphienyako.feywild.entity.base.TreeEntBase;
import com.saphienyako.feywild.screen.widget.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.fml.ModList;
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
                this.addRenderableWidget(new EntityWidget(left, (this.height - EntityWidget.HEIGHT) / 2 + 30, living));
                //TOP BUTTON
                if(entity instanceof TreeEntBase){
                    this.addRenderableWidget(new MountButton(left + EntityWidget.WIDTH + 25 + ((FeyMenuWidget.WIDTH - FollowButton.WIDTH)/2), this.top + 4 +  FollowButton.HEIGHT, this,  this.entityId));
                } else {
                    this.addRenderableWidget(new FollowButton(left + EntityWidget.WIDTH + 25 + ((FeyMenuWidget.WIDTH - FollowButton.WIDTH)/2), this.top + 4 +  FollowButton.HEIGHT, this.followingPlayer, this.entityId, this.currentBlockPos));
                }

                //SECOND BUTTON
                if (entity instanceof BellsnickelEntity bellsnickel) {
                    this.addRenderableWidget(new BellsnickelScreenButton(left + EntityWidget.WIDTH + 25 + ((FeyMenuWidget.WIDTH - BellsnickelScreenButton.WIDTH)/2), this.top + 12 + BellsnickelScreenButton.HEIGHT * 2, this.entityId));
                } else if(entity instanceof TreeEntBase) {
                    this.addRenderableWidget(new EffectButton(left + EntityWidget.WIDTH + 25 + ((FeyMenuWidget.WIDTH - EffectButton.WIDTH)/2), this.top + 12 +  EffectButton.HEIGHT * 2, this,  this.entityId));

                } else {
                    this.addRenderableWidget(new AbilityButton(left + EntityWidget.WIDTH + 25 + ((FeyMenuWidget.WIDTH - AbilityButton.WIDTH) / 2), this.top + 12 + AbilityButton.HEIGHT * 2, this.abilityActive, this.entityId));
                }
                //THIRD BUTTON
                this.addRenderableWidget(new DismissButton(left + EntityWidget.WIDTH + 25 + ((FeyMenuWidget.WIDTH - AbilityButton.WIDTH)/2), this.top + 20 + AbilityButton.HEIGHT * 3, this, this.entityId));
                // TODO Button Quest
                //VOLUME BUTTON
                if(ModConfig.COMMON.voice_active.get()) {
                    this.addRenderableWidget(new VolumeButton(left + EntityWidget.WIDTH + 25 + ((FeyMenuWidget.WIDTH - FollowButton.WIDTH) / 2), this.top + 28 + AbilityButton.HEIGHT * 6, this.voiceActive, this.entityId));
                }
                //FOURTH BUTTON
                if(entity instanceof BeeMountEntity){
                    this.addRenderableWidget(new BeeKnightScreenButton(left + EntityWidget.WIDTH + 25 + ((FeyMenuWidget.WIDTH - BeeKnightScreenButton.WIDTH)/2), this.top + 28 + BeeKnightScreenButton.HEIGHT * 4, this.entityId));
                } else if (ModCompat.QUEST_GIVER_LOADED && entity instanceof PixieBase pixie){
                    //TODO QUEST BUTTON
                    this.addRenderableWidget(new QuestButton(left + EntityWidget.WIDTH + 25 + ((FeyMenuWidget.WIDTH - BeeKnightScreenButton.WIDTH)/2),this.top + 28 + BeeKnightScreenButton.HEIGHT * 4, this, pixie.getQuestLineId(), pixie.getBackground(), false, this.entityId));
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
    public void tick() {
        super.tick();
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.level == null || minecraft.player == null) {
            this.onClose();
            return;
        }

        Entity entity = minecraft.level.getEntity(this.entityId);

        if (!(entity instanceof LivingEntity living)
                || !living.isAlive()
                || living.isRemoved()) {
            this.onClose();
        }

        if (entity instanceof BeeMountEntity mount) {
            BeeKnightEntity knight = mount.getLinkedKnight();

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
