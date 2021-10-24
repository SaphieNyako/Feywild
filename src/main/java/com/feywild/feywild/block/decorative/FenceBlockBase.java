package com.feywild.feywild.block.decorative;

import com.google.common.collect.ImmutableSet;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.Registerable;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.FenceBlock;

import java.util.Set;

public class FenceBlockBase extends FenceBlock implements Registerable {

    protected final ModX mod;
    private final Item item;

    public FenceBlockBase(ModX mod, Properties properties) {
        this(mod, properties, new net.minecraft.world.item.Item.Properties());
    }

    public FenceBlockBase(ModX mod, Properties properties, net.minecraft.world.item.Item.Properties itemProperties) {
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
}
