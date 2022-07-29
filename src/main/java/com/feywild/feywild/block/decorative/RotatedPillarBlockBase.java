package com.feywild.feywild.block.decorative;

import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.Registerable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.client.IItemRenderProperties;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.function.Consumer;

public class RotatedPillarBlockBase extends RotatedPillarBlock implements Registerable {

    protected final ModX mod;
    private final Item item;

    public RotatedPillarBlockBase(ModX mod, BlockBehaviour.Properties properties) {
        this(mod, properties, new Item.Properties());
    }

    public RotatedPillarBlockBase(ModX mod, BlockBehaviour.Properties properties, Item.Properties itemProperties) {
        super(properties);
        this.mod = mod;
        if (mod.tab != null) {
            itemProperties.tab(mod.tab);
        }

        this.item = new BlockItem(this, itemProperties) {
            @Override
            public void initializeClient(@Nonnull Consumer<IItemRenderProperties> consumer) {
                RotatedPillarBlockBase.this.initializeItemClient(consumer);
            }
        };
    }

    private void initializeItemClient(Consumer<IItemRenderProperties> consumer) {
    }

    @Override
    public Set<Object> getAdditionalRegisters(ResourceLocation id) {
        return Set.of(this.item);
    }
}
