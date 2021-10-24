package com.feywild.feywild.block.decorative;

import com.google.common.collect.ImmutableSet;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.Registerable;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.util.Set;
import java.util.function.Supplier;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class StairsBlockBase extends StairBlock implements Registerable {

    protected final ModX mod;
    private final Item item;

    public StairsBlockBase(ModX mod, Supplier<BlockState> supplier, BlockBehaviour.Properties properties) {
        this(mod, supplier, properties, new net.minecraft.world.item.Item.Properties());
    }

    public StairsBlockBase(ModX mod, Supplier<BlockState> supplier, Properties properties, net.minecraft.world.item.Item.Properties itemProperties) {
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
