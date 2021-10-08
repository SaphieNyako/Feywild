package com.feywild.feywild.block.decorative;

import com.google.common.collect.ImmutableSet;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.Registerable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

import java.util.Set;
import java.util.function.Supplier;

public class StairsBlockBase extends StairsBlock implements Registerable {

    protected final ModX mod;
    private final Item item;

    public StairsBlockBase(ModX mod, Supplier<BlockState> supplier, AbstractBlock.Properties properties) {
        this(mod, supplier, properties, new net.minecraft.item.Item.Properties());
    }

    public StairsBlockBase(ModX mod, Supplier<BlockState> supplier, Properties properties, net.minecraft.item.Item.Properties itemProperties) {
        super(supplier, properties);
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
