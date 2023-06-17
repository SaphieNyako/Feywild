package com.feywild.feywild;

import net.minecraft.world.item.CreativeModeTab;
import org.moddingx.libx.creativetab.CreativeTabX;
import org.moddingx.libx.mod.ModX;

public class FeywildTab extends CreativeTabX {

    private final ModX mod;

    public FeywildTab(ModX mod) {
        super(mod);
        this.mod = mod;
    }

    @Override
    protected void buildTab(CreativeModeTab.Builder builder) {
        super.buildTab(builder);
        builder.withBackgroundLocation(this.mod.resource("textures/gui/tab_icon.png"));
    }

    @Override
    protected void addItems(TabContext ctx) {
        this.addModItems(ctx);
    }
}
