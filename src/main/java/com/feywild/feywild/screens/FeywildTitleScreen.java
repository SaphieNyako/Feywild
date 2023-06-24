package com.feywild.feywild.screens;

import com.feywild.feywild.FeywildMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import org.moddingx.libx.render.RenderHelper;

import javax.annotation.Nonnull;

public class FeywildTitleScreen extends TitleScreen {

    private static final ResourceLocation SPLASH = FeywildMod.getInstance().resource("textures/gui/background/panorama_0.png");

    @Override
    public void render(@Nonnull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        graphics.pose().pushPose();
        RenderHelper.resetColor();
        graphics.blit(SPLASH, 0, 0, 0, 0, this.width, this.height, width, height);
        graphics.pose().translate(0, 0, 20);
        //noinspection UnstableApiUsage
        ForgeHooksClient.renderMainMenu(this, graphics, this.getMinecraft().font, width, height, -1);
        graphics.pose().translate(0, 0, 20);
        graphics.drawString(this.getMinecraft().font, "Copyright Mojang AB. Do not distribute!", width - this.font.width("Copyright Mojang AB. Do not distribute!") - 2, height - 10, 0xFFFFFFFF, false);
        graphics.pose().translate(0, 0, 20);
        for (Renderable widget : this.renderables) {
            widget.render(graphics, mouseX, mouseY, partialTicks);
        }
        graphics.pose().popPose();
    }
}
