package com.feywild.feywild.screens.widget;

import com.feywild.feywild.FeywildMod;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LookingGlassWidget extends Button {
    ResourceLocation image;
    Minecraft minecraft;
    public LookingGlassWidget(int p_i232255_1_, int p_i232255_2_, int p_i232255_3_, int p_i232255_4_, ITextComponent p_i232255_5_, IPressable p_i232255_6_) {
        super(p_i232255_1_, p_i232255_2_, p_i232255_3_, p_i232255_4_, p_i232255_5_, p_i232255_6_);
        this.image = new ResourceLocation(FeywildMod.MOD_ID, "textures/gui/looking_glass.png");

        this.minecraft = Minecraft.getInstance();
    }

    @Override
    public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bind(image);
        if (this.isHovered(p_230430_2_,p_230430_3_)) {
            this.blit(p_230430_1_, this.x -1, this.y -1, 12, 0, 14, 14);

        }else{
            this.blit(p_230430_1_, this.x, this.y, 0, 0, 12, 12);
        }
    }

    public boolean isHovered(int x, int y) {
        return this.x < x && this.x + 12 > x && this.y < y && this.y + 12 > y;
    }

}
