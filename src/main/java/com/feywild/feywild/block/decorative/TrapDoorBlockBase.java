package com.feywild.feywild.block.decorative;

import com.google.common.collect.ImmutableSet;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.Registerable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Set;
import java.util.function.Consumer;

public class TrapDoorBlockBase extends TrapDoorBlock implements Registerable {

    protected final ModX mod;
    private final Item item;

    public TrapDoorBlockBase(ModX mod, AbstractBlock.Properties properties) {
        this(mod, properties, new net.minecraft.item.Item.Properties());
    }

    public TrapDoorBlockBase(ModX mod, AbstractBlock.Properties properties, net.minecraft.item.Item.Properties itemProperties) {
        super(properties);
        this.mod = mod;
        if (mod.tab != null) {
            itemProperties.tab(mod.tab);
        }

        this.item = new BlockItem(this, itemProperties);
    }

    public Set<Object> getAdditionalRegisters() {
        return ImmutableSet.of(this.item);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void registerClient(ResourceLocation id, Consumer<Runnable> defer) {
        defer.accept(() -> RenderTypeLookup.setRenderLayer(this, RenderType.cutout()));
    }

}
