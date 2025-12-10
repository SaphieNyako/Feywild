package com.saphienyako.feywild.data;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider,
                              CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, Feywild.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        this.tag(ItemTags.CREEPER_DROP_MUSIC_DISCS).add(ModItems.FEYWILD_MUSIC_DISC.get());
        this.tag(Tags.Items.MUSIC_DISCS).add(ModItems.FEYWILD_MUSIC_DISC.get());
        this.tag(Tags.Items.CROPS).add(ModItems.MANDRAKE.get());
        this.tag(Tags.Items.DUSTS).add(ModItems.FEY_DUST.get());
        this.tag(Tags.Items.ENCHANTING_FUELS).add(ModItems.FEY_DUST.get());
        this.tag(Tags.Items.GEMS).add(ModItems.FEY_GEM.get());
    }
}
