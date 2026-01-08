package com.saphienyako.feywild.screen.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.Alignment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class FeyMenuWidget extends Widget {

    public static final int WIDTH = 157;
    public static final int HEIGHT = 210;

    private final Alignment alignment;

    private static final Map<Alignment, ResourceLocation> TEXTURE = new HashMap<>();

    static {
        TEXTURE.put(Alignment.SPRING, new ResourceLocation(Feywild.MOD_ID, "textures/gui/spring_menu.png"));
        TEXTURE.put(Alignment.SUMMER, new ResourceLocation(Feywild.MOD_ID, "textures/gui/summer_menu.png"));
        TEXTURE.put(Alignment.AUTUMN, new ResourceLocation(Feywild.MOD_ID, "textures/gui/autumn_menu.png"));
        TEXTURE.put(Alignment.WINTER, new ResourceLocation(Feywild.MOD_ID, "textures/gui/winter_menu.png"));
    }

    public FeyMenuWidget(int x, int y, Alignment alignment) {
        super(x, y, WIDTH, HEIGHT, new TranslationTextComponent(""));
        this.alignment = alignment;
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().getTexture(TEXTURE.get(this.alignment));
        blit(matrixStack, this.x, this.y, 0,0, WIDTH, HEIGHT);
    }

    @Override
    protected boolean isValidClickButton(int pButton) {
        return false;
    }
}
