package com.feywild.feywild.screens;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.events.ClientEvents;
import com.feywild.feywild.network.quest.ConfirmQuestMessage;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.QuestDisplay;
import com.feywild.feywild.screens.button.AcceptButton;
import com.feywild.feywild.screens.button.NextPageButton;
import com.feywild.feywild.screens.widget.BackgroundWidget;
import com.feywild.feywild.screens.widget.CharacterWidget;
import com.feywild.feywild.util.FeywildTextProcessor;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ComponentRenderUtils;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.LivingEntity;
import org.moddingx.libx.util.game.ComponentUtil;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DisplayQuestScreen extends Screen {

    private final QuestDisplay display;
    private final boolean hasConfirmationButtons;
    public int textBlockNumber;
    protected List<List<FormattedCharSequence>> textBlocks;
    int QUEST_WINDOW_POSITION_Y = 100;
    int QUEST_WINDOW_POSITION_X = 70;
    int ACCEPT_POSITION_Y = 223;
    int ACCEPT_POSITION_X = 150;
    int DECLINE_POSITION_Y = 223;
    int DECLINE_POSITION_X = 250;
    int NEXT_POSITION_Y = 158;
    int NEXT_POSITION_X = 390;
    int CHARACTER_POSITION_Y = 240;
    int CHARACTER_POSITION_X = 37;
    int DESCRIPTION_POSITION_Y = 128;
    int DESCRIPTION_POSITION_X = 100;
    int TITLE_POSITION_Y = 95;
    int WIDTH_SCREEN = 323;
    int animationCount = 0;
    int lineCount = 0;
    int secondAnimationCount = 0;
    int id;
    Alignment alignment;
    private Component title;
    private List<FormattedCharSequence> description;
    private List<FormattedCharSequence> currentTextBlock;


    public DisplayQuestScreen(QuestDisplay display, boolean hasConfirmationButtons, int id, Alignment alignment) {
        super(display.title);
        this.display = display;
        this.hasConfirmationButtons = hasConfirmationButtons;
        this.id = id;
        this.alignment = alignment;
    }

    @Override
    protected void init() {
        super.init();

        //background contains title (now on top of screen):
        this.title = FeywildTextProcessor.INSTANCE.processLine(this.display.title);
        //add background widget
        this.addRenderableWidget(new BackgroundWidget(this, QUEST_WINDOW_POSITION_X, QUEST_WINDOW_POSITION_Y, alignment));
        //add character widget
        this.addRenderableWidget(new CharacterWidget(this, CHARACTER_POSITION_X, CHARACTER_POSITION_Y, (LivingEntity) Objects.requireNonNull(Objects.requireNonNull(minecraft).level).getEntity(id)));

        //add discription:
        this.description = FeywildTextProcessor.INSTANCE.process(this.display.description).stream().flatMap(
                line -> ComponentRenderUtils.wrapComponents(line, 280, Minecraft.getInstance().font).stream()).collect(Collectors.toList());

        this.textBlocks = getTextBlocks(this.description, 8);
        this.textBlockNumber = 0;
        this.currentTextBlock = this.textBlocks.get(this.textBlockNumber);

        //add button to go to next page of discription
        if (textBlocks.size() > 1) {

            //add QuestButton to go to next page
            this.addRenderableWidget(new NextPageButton(NEXT_POSITION_X, NEXT_POSITION_Y, Component.literal(">>"), button -> {
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

        //comformation buttons
        if (this.hasConfirmationButtons) {
            this.addRenderableWidget(new AcceptButton(ACCEPT_POSITION_X, ACCEPT_POSITION_Y, Component.literal("Accept!"), button -> {
                FeywildMod.getNetwork().channel.sendToServer(new ConfirmQuestMessage(true));
                this.onClose();
            }));
            this.addRenderableWidget(new AcceptButton(DECLINE_POSITION_X, DECLINE_POSITION_Y, Component.literal("Decline!"), button -> {
                FeywildMod.getNetwork().channel.sendToServer(new ConfirmQuestMessage(false));
                this.onClose();
            }));
        }

        //on opening window close the GUI
        ClientEvents.setShowGui(false);
    }

    @Override
    public void onClose() {
        //on closing open the GUI, add client event:
        ClientEvents.setShowGui(true);
        this.currentTextBlock = this.textBlocks.get(0);
        reset();
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
                        this.minecraft.font.drawShadow(poseStack, getCurrentTextBlock().get(i - 1), this.DESCRIPTION_POSITION_X,
                                this.DESCRIPTION_POSITION_Y + ((2 + this.minecraft.font.lineHeight) * (i - 1)), 0xFFFFFF);
                    }

                    for (int j = 0; j < secondAnimationCount; j++) {

                        this.minecraft.font.drawShadow(poseStack, ComponentUtil.subSequence(getCurrentTextBlock().get(i), j, j + 1),
                                this.DESCRIPTION_POSITION_X + x,
                                this.DESCRIPTION_POSITION_Y + ((2 + this.minecraft.font.lineHeight) * i), 0xFFFFFF);

                        x += this.minecraft.font.width(ComponentUtil.subSequence(getCurrentTextBlock().get(i), j, j + 1)); //add width length.
                    }
                }

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
            } else {
                for (int i = 0; i < getCurrentTextBlock().size(); i++) {
                    this.minecraft.font.drawShadow(poseStack, getCurrentTextBlock().get(i), this.DESCRIPTION_POSITION_X, this.DESCRIPTION_POSITION_Y + ((2 + this.minecraft.font.lineHeight) * i), 0xFFFFFF);
                }
            }
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
