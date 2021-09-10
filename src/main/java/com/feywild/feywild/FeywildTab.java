package com.feywild.feywild;

import com.feywild.feywild.item.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public class FeywildTab extends ItemGroup {
    
    private final ResourceLocation texture;
    
    public FeywildTab(String modid) {
        super(modid);
        this.texture = new ResourceLocation(modid, "textures/gui/tab_icon.png");
    }
    
    @Nonnull
    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ModItems.summoningScrollSpringPixie);
    }

    @Nonnull
    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack getIconItem() {
        // This one is used for display, but we handle display ourselves
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getTabsImage() {
        return texture;
    }
}
