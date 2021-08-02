package com.feywild.feywild.screens.widget;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.network.FeywildPacketHandler;
import com.feywild.feywild.network.ItemEntityMessage;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.logging.Logger;

@OnlyIn(Dist.CLIENT)
public class BookWidget extends Button {
    ResourceLocation image;
    Minecraft minecraft;
    ItemStack stack;
    boolean close =false;
    public BookWidget(int p_i232255_1_, int p_i232255_2_, int p_i232255_3_, int p_i232255_4_, ITextComponent p_i232255_5_, ItemStack stack) {
        super(p_i232255_1_, p_i232255_2_, p_i232255_3_, p_i232255_4_, p_i232255_5_, button -> FeywildPacketHandler.INSTANCE.sendToServer(new ItemEntityMessage(stack)));
        this.image = new ResourceLocation(FeywildMod.getInstance().modid, "textures/gui/librarian_gui.png");
        this.stack = stack;
        this.minecraft = Minecraft.getInstance();
    }

    public ItemStack getStack() {
        return stack;
    }

    @Override
    public void onPress() {
        super.onPress();
        close = true;
    }

    public boolean isClose() {
        return close;
    }

    @Override
    public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bind(image);
        if (this.isHovered(p_230430_2_,p_230430_3_)) {
            this.blit(p_230430_1_, this.x, this.y, 25, 0, 25, 25);
        }else{
            this.blit(p_230430_1_, this.x, this.y, 0, 0, 25, 25);
        }
        this.minecraft.getItemRenderer().renderGuiItem(stack,this.x+4,this.y+4);
    }

    public boolean isHovered(int x, int y) {
        return this.x < x && this.x + 24 > x && this.y < y && this.y + 24 > y;
    }

}
