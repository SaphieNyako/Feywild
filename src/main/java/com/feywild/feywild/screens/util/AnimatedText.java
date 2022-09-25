package com.feywild.feywild.screens.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.ComponentRenderUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import org.moddingx.libx.util.game.ComponentUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AnimatedText {
    
    private final Font font;
    private final int ticksPerStep;
    private final int charsPerStep;
    private final List<Page> pages;
    
    private int tickCount = 0;
    private int currentPage = 0;

    public AnimatedText(int width, int height, int speed, List<Component> lines) {
        this(width, height, 1, speed, lines);
    }
    
    public AnimatedText(int width, int height, int ticksPerStep, int charsPerStep, List<Component> lines) {
        this(Minecraft.getInstance().font, width, height, ticksPerStep, charsPerStep, lines);
    }
    
    public AnimatedText(Font font, int width, int height, int ticksPerStep, int charsPerStep, List<Component> lines) {
        this.font = font;
        this.ticksPerStep = ticksPerStep;
        this.charsPerStep = charsPerStep;
        
        List<FormattedCharSequence> wrapped = lines.stream()
                .flatMap(line -> ComponentRenderUtils.wrapComponents(line, width, font).stream())
                .toList();
        
        int linesPerPage = Math.max(1, height / (font.lineHeight + 2));
        
        List<Page> pages = new ArrayList<>();
        int idx = 0;
        while (idx < wrapped.size()) {
            int end = Math.min(idx + linesPerPage, wrapped.size());
            pages.add(new Page(wrapped.subList(idx, end)));
            idx = end;
        }
        
        if (pages.isEmpty()) pages.add(new Page(List.of(FormattedCharSequence.EMPTY)));
        
        this.pages = List.copyOf(pages);
    }
    
    public void tick() {
        this.tickCount += 1;
        if (this.tickCount >= this.ticksPerStep) {
            this.tickCount = 0;
            this.pages.get(this.currentPage).advance(this.charsPerStep);
        }
    }
    
    public boolean canContinue() {
        return this.pages.get(this.currentPage).fullyDisplayed();
    }
    
    public boolean isOnLastPage() {
        return this.currentPage >= this.pages.size() - 1;
    }
    
    public void nextPage() {
        if (!this.isOnLastPage()) this.currentPage += 1;
    }
    
    public void render(PoseStack poseStack, int x, int y) {
        this.pages.get(this.currentPage).render(poseStack, x, y);
    }

    private class Page {
        
        private final List<FormattedCharSequence> lines;
        private int visibleLines;
        private int visibleChars;
        private int currentLength;

        private Page(List<FormattedCharSequence> lines) {
            this.lines = List.copyOf(lines);
            this.visibleLines = 0;
            this.visibleChars = 0;
            this.updateCurrentLength();
        }
        
        public void advance(int amount) {
            if (this.visibleLines < this.lines.size()) {
                this.visibleChars += amount;
                if (this.visibleChars >= this.currentLength) {
                    this.visibleLines += 1;
                    this.visibleChars = 0;
                    this.updateCurrentLength();
                }
            }
        }
        
        private void updateCurrentLength() {
            if (this.visibleLines >= this.lines.size()) {
                this.currentLength = 0;
            } else {
                FormattedCharSequence fsq = this.lines.get(this.visibleLines);
                AtomicInteger len = new AtomicInteger(0);
                fsq.accept((pos, style, chr) -> {
                    len.incrementAndGet();
                    return true;
                });
                this.currentLength = len.get();
            }
        }
        
        public boolean fullyDisplayed() {
            return this.visibleLines >= this.lines.size();
        }

        public void render(PoseStack poseStack, int x, int y) {
            for (int i = 0; i < Math.min(this.visibleLines, this.lines.size()); i++) {
                AnimatedText.this.font.drawShadow(poseStack, this.lines.get(i), x, y + i * (AnimatedText.this.font.lineHeight + 2), 0xFFFFFF);
            }
            if (this.visibleLines < this.lines.size() && this.visibleChars > 0) {
                FormattedCharSequence fsq = ComponentUtil.subSequence(this.lines.get(this.visibleLines), 0, this.visibleChars);
                AnimatedText.this.font.drawShadow(poseStack, fsq, x, y + this.visibleLines * (AnimatedText.this.font.lineHeight + 2), 0xFFFFFF);
            }
        }
    }
}
