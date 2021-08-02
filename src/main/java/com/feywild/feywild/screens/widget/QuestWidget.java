package com.feywild.feywild.screens.widget;

import com.feywild.feywild.FeywildMod;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.system.CallbackI;

@OnlyIn(Dist.CLIENT)
public class QuestWidget extends Widget {
    ItemStack icon;
    ResourceLocation image;
    ITextComponent component;
    int xPos;
    Minecraft minecraft;
    public QuestWidget(int p_i232254_1_, int p_i232254_2_, int p_i232254_3_, int p_i232254_4_, ITextComponent p_i232254_5_, ItemStack icon, int type) {
        super(p_i232254_1_, p_i232254_2_, p_i232254_3_, p_i232254_4_, p_i232254_5_);
        this.icon = icon;
        this.image = new ResourceLocation(FeywildMod.getInstance().modid, "textures/gui/quest_atlas.png");
        this.component = p_i232254_5_;
        //type = 0 - Spring ; 1 - Summer ; 2 - Autumn ; 3 - Winter
        xPos = 24 * type + type;

        this.minecraft = Minecraft.getInstance();

    }
    @Override
    public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bind(image);
        this.blit(p_230430_1_, this.x, this.y, this.xPos, 0, 24, 24);
        this.minecraft.getItemRenderer().renderGuiItem(icon,this.x + 4,this.y + 4);
    }


    public boolean isHovered(int x, int y) {
        return this.x < x && this.x + 24 > x && this.y < y && this.y + 24 > y;
    }

    public ITextComponent getComponent() {
        return component;
    }
}
