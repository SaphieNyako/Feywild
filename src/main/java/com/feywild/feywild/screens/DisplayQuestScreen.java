package com.feywild.feywild.screens;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.network.quest.ConfirmQuestMessage;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.QuestDisplay;
import com.feywild.feywild.screens.button.FancyButton;
import com.feywild.feywild.screens.widget.BackgroundWidget;
import com.feywild.feywild.screens.widget.EntityWidget;
import com.feywild.feywild.util.FeywildTextProcessor;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ComponentRenderUtils;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.moddingx.libx.util.game.ComponentUtil;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DisplayQuestScreen extends Screen {

    private static final int QUEST_WINDOW_POSITION_Y = 100;
    private static final int QUEST_WINDOW_POSITION_X = 70;
    private static final int ACCEPT_POSITION_Y = 223;
    private static final int ACCEPT_POSITION_X = 150;
    private static final int DECLINE_POSITION_Y = 223;
    private static final int DECLINE_POSITION_X = 250;
    private static final int NEXT_POSITION_Y = 158;
    private static final int NEXT_POSITION_X = 390;
    private static final int CHARACTER_POSITION_Y = 240;
    private static final int CHARACTER_POSITION_X = 40;
    private static final int DESCRIPTION_POSITION_Y = 128;
    private static final int DESCRIPTION_POSITION_X = 100;
    private static final int TITLE_POSITION_Y = 95;
    private static final int WIDTH_SCREEN = 323;
    
    private final QuestDisplay display;
    private final boolean hasConfirmationButtons;
    private final Alignment alignment;
    private final int entityId;
    protected List<List<FormattedCharSequence>> textBlocks;
    public int textBlockNumber;
    private int animationCount = 0;
    private int lineCount = 0;
    private int secondAnimationCount = 0;
    private Component title;
    private List<FormattedCharSequence> currentTextBlock;


    public DisplayQuestScreen(QuestDisplay display, boolean hasConfirmationButtons, int entityId, Alignment alignment) {
        super(display.title);
        this.display = display;
        this.hasConfirmationButtons = hasConfirmationButtons;
        this.entityId = entityId;
        this.alignment = alignment;
    }

    @Override
    protected void init() {
        super.init();

        this.title = FeywildTextProcessor.INSTANCE.processLine(this.display.title);

        this.addRenderableOnly(new BackgroundWidget(QUEST_WINDOW_POSITION_X, QUEST_WINDOW_POSITION_Y, alignment));
        
        if (this.entityId != -1) {
            Entity entity = Minecraft.getInstance().level == null ? null : Minecraft.getInstance().level.getEntity(this.entityId);
            if (entity instanceof LivingEntity living) {
                this.addRenderableWidget(new EntityWidget(this, CHARACTER_POSITION_X, CHARACTER_POSITION_Y, living));
            }
        }

        List<FormattedCharSequence> description = FeywildTextProcessor.INSTANCE.process(this.display.description).stream().flatMap(
                line -> ComponentRenderUtils.wrapComponents(line, 280, Minecraft.getInstance().font).stream()).collect(Collectors.toList());

        this.textBlocks = getTextBlocks(description, 8);
        this.textBlockNumber = 0;
        this.currentTextBlock = this.textBlocks.get(this.textBlockNumber);

        if (textBlocks.size() > 1) {

            this.addRenderableWidget(FancyButton.makeSmall(NEXT_POSITION_X, NEXT_POSITION_Y, Component.literal(">>"), button -> {
                this.textBlockNumber++;
                reset();
                if (textBlockNumber == textBlocks.size() - 1) {
                    button.setMessage(Component.literal("x"));
                }
                if (textBlockNumber < textBlocks.size()) {
                    this.currentTextBlock = this.textBlocks.get(this.textBlockNumber);
                } else {
                    this.onClose();
                }
            }));
        }

        if (this.hasConfirmationButtons) {
            this.addRenderableWidget(FancyButton.makeLarge(ACCEPT_POSITION_X, ACCEPT_POSITION_Y, Component.translatable("message.feywild.quest_accept"), button -> {
                FeywildMod.getNetwork().channel.sendToServer(new ConfirmQuestMessage(true));
                this.onClose();
            }));
            this.addRenderableWidget(FancyButton.makeLarge(DECLINE_POSITION_X, DECLINE_POSITION_Y, Component.translatable("message.feywild.quest_decline"), button -> {
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
            drawString(poseStack, this.minecraft.font, this.title, (QUEST_WINDOW_POSITION_X + WIDTH_SCREEN / 2 - (this.minecraft.font.width(this.title) / 2)) + 10, TITLE_POSITION_Y, 0xFFFFFF);

            if (lineCount < getCurrentTextBlock().size()) {

                for (int i = 0; i < lineCount + 1; i++) {
                    int x = 0;
                    //show previous line
                    if (i != 0) {
                        this.minecraft.font.drawShadow(poseStack, getCurrentTextBlock().get(i - 1), DESCRIPTION_POSITION_X,
                                DESCRIPTION_POSITION_Y + ((2 + this.minecraft.font.lineHeight) * (i - 1)), 0xFFFFFF);
                    }

                    for (int j = 0; j < secondAnimationCount; j++) {

                        this.minecraft.font.drawShadow(poseStack, ComponentUtil.subSequence(getCurrentTextBlock().get(i), j, j + 1),
                                DESCRIPTION_POSITION_X + x,
                                DESCRIPTION_POSITION_Y + ((2 + this.minecraft.font.lineHeight) * i), 0xFFFFFF);

                        x += this.minecraft.font.width(ComponentUtil.subSequence(getCurrentTextBlock().get(i), j, j + 1)); //add width length.
                    }
                }
            } else {
                for (int i = 0; i < getCurrentTextBlock().size(); i++) {
                    this.minecraft.font.drawShadow(poseStack, getCurrentTextBlock().get(i), DESCRIPTION_POSITION_X, DESCRIPTION_POSITION_Y + ((2 + this.minecraft.font.lineHeight) * i), 0xFFFFFF);
                }
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.animationCount != 2) {
            this.animationCount++;
        } else {
            if (secondAnimationCount < 30) {
                secondAnimationCount++;
            } else {
                if (lineCount < getCurrentTextBlock().size()) {
                    lineCount++;
                }
                secondAnimationCount = 0;
            }
            animationCount = 0;
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

    public List<List<FormattedCharSequence>> getTextBlocks(List<FormattedCharSequence> description, int numberOfMaxTextBlocks) {
        int i = 0;
        List<List<FormattedCharSequence>> textBlocks = new ArrayList<>();
        while (i < description.size()) {
            int nextInc = Math.min(description.size() - i, numberOfMaxTextBlocks);
            List<FormattedCharSequence> textBlock = description.subList(i, i + nextInc);
            textBlocks.add(textBlock);
            i = i + nextInc;
        }
        return textBlocks;
    }

    public List<FormattedCharSequence> getCurrentTextBlock() {
        return currentTextBlock;
    }

    public void reset() {
        this.lineCount = 0;
        this.animationCount = 0;
        this.secondAnimationCount = 0;
    }
}
