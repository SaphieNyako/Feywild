package com.feywild.feywild.data;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.trees.BaseSaplingBlock;
import com.feywild.feywild.item.Schematics;
import io.github.noeppi_noeppi.libx.data.provider.ItemModelProviderBase;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelProvider extends ItemModelProviderBase {

    public ItemModelProvider(ModX mod, DataGenerator generator, ExistingFileHelper fileHelper) {
        super(mod, generator, fileHelper);
    }

    @Override
    protected void setup() {
        
    }

    @Override
    protected void defaultItem(ResourceLocation id, Item item) {
        if (item instanceof Schematics) {
            this.withExistingParent(id.getPath(), GENERATED).texture("layer0", new ResourceLocation(id.getNamespace(), "item/schematic"));
        } else {
            super.defaultItem(id, item);
        }
    }

    @Override
    protected void defaultBlock(ResourceLocation id, BlockItem item) {
        if (item.getBlock() instanceof BaseSaplingBlock) {
            this.withExistingParent(id.getPath(), GENERATED).texture("layer0", new ResourceLocation(id.getNamespace(), "block/" + id.getPath()));
        } else if (item == ModBlocks.mandrakeCrop.getSeed()) {
            this.withExistingParent(id.getPath(), GENERATED).texture("layer0", new ResourceLocation(id.getNamespace(), "item/" + id.getPath()));
        } else {
            super.defaultBlock(id, item);
        }
    }
}
