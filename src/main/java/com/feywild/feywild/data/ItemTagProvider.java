package com.feywild.feywild.data;

import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.item.Schematics;
import com.feywild.feywild.tag.ModBlockTags;
import com.feywild.feywild.tag.ModItemTags;
import io.github.noeppi_noeppi.libx.data.provider.BlockTagProviderBase;
import io.github.noeppi_noeppi.libx.data.provider.ItemTagProviderBase;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemTagProvider extends ItemTagProviderBase {

    public ItemTagProvider(ModX mod, DataGenerator generator, ExistingFileHelper fileHelper, BlockTagProviderBase blockTags) {
        super(mod, generator, fileHelper, blockTags);
    }

    @Override
    protected void setup() {
        this.tag(ItemTags.CREEPER_DROP_MUSIC_DISCS).add(ModItems.feywildMusicDisc);
        
        this.copy(BlockTags.LOGS, ItemTags.LOGS);
        this.copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
        this.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
        this.copy(BlockTags.LEAVES, ItemTags.LEAVES);
        this.copy(ModBlockTags.FEY_LOGS, ModItemTags.FEY_LOGS);
    }

    @Override
    public void defaultItemTags(Item item) {
        if (item instanceof Schematics) {
            this.tag(ModItemTags.SCHEMATICS).add(item);
        } 
    }
}
