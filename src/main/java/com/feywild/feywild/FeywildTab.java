package com.feywild.feywild;

import net.minecraft.world.item.CreativeModeTab;
import org.moddingx.libx.creativetab.CreativeTabX;
import org.moddingx.libx.mod.ModX;

public class FeywildTab extends CreativeTabX {

    public FeywildTab(ModX mod) {
        super(mod);
    }

    @Override
    protected void buildTab(CreativeModeTab.Builder builder) {
        super.buildTab(builder);
        builder.withTabsImage(this.mod.resource("textures/gui/tab_icon.png"));
    }

    @Override
    protected void addItems(TabContext ctx) {
        this.addModItems(ctx);
    }
}
