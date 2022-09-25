package com.feywild.feywild.screens;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.network.quest.ConfirmQuestMessage;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.QuestDisplay;
import com.feywild.feywild.screens.button.FancyButton;
import com.feywild.feywild.screens.util.AnimatedText;
import com.feywild.feywild.screens.widget.BackgroundWidget;
import com.feywild.feywild.screens.widget.EntityWidget;
import com.feywild.feywild.util.FeywildTextProcessor;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nonnull;

public class DisplayQuestScreen extends Screen {
    
    @SuppressWarnings("FieldCanBeLocal")
    private final QuestDisplay display;
    private final boolean hasConfirmationButtons;
    private final Alignment alignment;
    private final int entityId;
    private final Component title;
    private final AnimatedText text;

    private int left;
    private int top;

    public DisplayQuestScreen(QuestDisplay display, boolean hasConfirmationButtons, int entityId, Alignment alignment) {
        super(display.title);
        this.display = display;
        this.hasConfirmationButtons = hasConfirmationButtons;
        this.entityId = entityId;
        this.alignment = alignment;

        this.title = FeywildTextProcessor.INSTANCE.processLine(this.display.title);
        this.text = new AnimatedText(BackgroundWidget.WIDTH - (2 * BackgroundWidget.HORIZONTAL_PADDING), BackgroundWidget.HEIGHT - (2 * BackgroundWidget.VERTICAL_PADDING), 1, FeywildTextProcessor.INSTANCE.process(this.display.description));
    }

    @Override
    protected void init() {
        super.init();
        
        this.left = (this.width / 2) - ((EntityWidget.WIDTH + BackgroundWidget.WIDTH) / 2);
        this.top = (this.height / 2) - (BackgroundWidget.HEIGHT / 2);
        
        this.addRenderableOnly(new BackgroundWidget(this.left + EntityWidget.WIDTH, this.top, alignment));
        
        if (this.entityId != -1) {
            Entity entity = Minecraft.getInstance().level == null ? null : Minecraft.getInstance().level.getEntity(this.entityId);
            if (entity instanceof LivingEntity living) {
                this.addRenderableWidget(new EntityWidget(this.left, this.top + (BackgroundWidget.HEIGHT - EntityWidget.HEIGHT) / 2, living));
            }
        }
        
        this.addRenderableWidget(FancyButton.makeSmall(this.left + EntityWidget.WIDTH + 320, this.top + 58, Component.literal(this.text.isOnLastPage() ? "x" : ">>"), this.text::canContinue, button -> {
            if (this.text.isOnLastPage()) {
                this.onClose();
            } else {
                this.text.nextPage();
                button.setMessage(Component.literal(this.text.isOnLastPage() ? "x" : ">>"));
            }
        }));

        if (this.hasConfirmationButtons) {
            this.addRenderableWidget(FancyButton.makeLarge(this.left + EntityWidget.WIDTH + 80, this.top + 123, Component.translatable("message.feywild.quest_accept"), button -> {
                FeywildMod.getNetwork().channel.sendToServer(new ConfirmQuestMessage(true));
                this.onClose();
            }));
            this.addRenderableWidget(FancyButton.makeLarge(this.left + EntityWidget.WIDTH + 180, this.top + 123, Component.translatable("message.feywild.quest_decline"), button -> {
                FeywildMod.getNetwork().channel.sendToServer(new ConfirmQuestMessage(false));
                this.onClose();
            }));
        }
    }

    @Override
    public void onClose() {
        super.onClose();
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        this.drawTextLines(poseStack, mouseX, mouseY);
    }

    private void drawTextLines(PoseStack poseStack, int mouseX, int mouseY) {
        if (this.minecraft != null) {
            drawString(poseStack, this.minecraft.font, this.title, (this.width / 2) - (this.minecraft.font.width(this.title) / 2), this.top - this.minecraft.font.lineHeight - 6, 0xFFFFFF);
            this.text.render(poseStack, this.left + EntityWidget.WIDTH + BackgroundWidget.HORIZONTAL_PADDING, this.top + BackgroundWidget.VERTICAL_PADDING);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.text != null) {
            this.text.tick();
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return !hasConfirmationButtons;
    }
}
