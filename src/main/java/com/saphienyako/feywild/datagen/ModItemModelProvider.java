package com.saphienyako.feywild.datagen;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Feywild.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.FEYWILD_LEXICON.get());
        basicItem(ModItems.FEY_GEM.get());
        basicItem(ModItems.MANDRAKE_ROOT.get());
        basicItem(ModItems.FEY_INK_BOTTLE.get());
        basicItem(ModItems.FEYWILD_MUSIC_DISC.get());
        basicItem(ModItems.EMPTY_SUMMONING_SCROLL.get());
        basicItem(ModItems.PIXIE_ORB.get());
        basicItem(ModItems.SUMMONING_SCROLL_SPRING_PIXIE.get());
        basicItem(ModItems.SUMMONING_SCROLL_AUTUMN_PIXIE.get());
        basicItem(ModItems.SUMMONING_SCROLL_SUMMER_PIXIE.get());
        basicItem(ModItems.SUMMONING_SCROLL_WINTER_PIXIE.get());
        basicItem(ModItems.SUMMONING_SCROLL_SHROOMLING.get());
        basicItem(ModItems.SUMMONING_SCROLL_MANDRAGORA.get());
        basicItem(ModItems.SUMMONING_SCROLL_BELLSNICKEL.get());
        basicItem(ModItems.SUMMONING_SCROLL_BEE_KNIGHT.get());
        basicItem(ModItems.SUMMONING_SCROLL_SPRING_TREE_ENT.get());
        basicItem(ModItems.SUMMONING_SCROLL_SUMMER_TREE_ENT.get());
        basicItem(ModItems.SUMMONING_SCROLL_AUTUMN_TREE_ENT.get());
        basicItem(ModItems.SUMMONING_SCROLL_WINTER_TREE_ENT.get());
        basicItem(ModItems.FEY_DUST.get());
        basicItem(ModItems.MANDRAKE.get());
        basicItem(ModItems.GIANT_CROCUS_FLOWER_SEED.get());
        basicItem(ModItems.GIANT_DANDELION_FLOWER_SEED.get());
        basicItem(ModItems.GIANT_SUN_FLOWER_SEED.get());
    }
    @SuppressWarnings("unused")
    public void flowerItem(DeferredBlock<Block> block) {
        this.withExistingParent(block.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0",  ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID,
                        "block/" + block.getId().getPath()));
    }
    @SuppressWarnings("unused")
    public void buttonItem(DeferredBlock<Block> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }
    @SuppressWarnings("unused")
    public void fenceItem(DeferredBlock<Block> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/fence_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }
    @SuppressWarnings("unused")
    public void wallItem(DeferredBlock<Block> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }

}
