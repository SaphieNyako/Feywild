package com.feywild.feywild.data;

import com.feywild.feywild.block.FeyAltarBlock;
import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.trees.BaseSaplingBlock;
import com.feywild.feywild.item.ReaperScythe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import org.moddingx.libx.datagen.DatagenContext;
import org.moddingx.libx.datagen.provider.model.ItemModelProviderBase;

public class ItemModelProvider extends ItemModelProviderBase {

    public ItemModelProvider(DatagenContext ctx) {
        super(ctx);
    }

    @Override
    protected void setup() {

    }

    @Override
    protected void defaultItem(ResourceLocation id, Item item) {
        if (!(item instanceof ReaperScythe)) {
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
            if (!(item.getBlock() instanceof FeyAltarBlock)) {
                super.defaultBlock(id, item);
            }
        }
    }
}
