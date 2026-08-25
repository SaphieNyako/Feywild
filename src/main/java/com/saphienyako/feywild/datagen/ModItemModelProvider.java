package com.saphienyako.feywild.datagen;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.block.ModBlocks;
import com.saphienyako.feywild.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Feywild.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.FEYWILD_LEXICON);
        simpleItem(ModItems.FEY_GEM);
        simpleItem(ModItems.MANDRAKE_ROOT);
        simpleItem(ModItems.FEY_INK_BOTTLE);
        simpleItem(ModItems.FEYWILD_MUSIC_DISC);
        simpleItem(ModItems.EMPTY_SUMMONING_SCROLL);
        simpleItem(ModItems.PIXIE_ORB);
        basicItem(ModItems.PIXIE_WING_TIARA.get());
        basicItem(ModItems.AUTUMN_PIXIE_WING_TIARA.get());
        basicItem(ModItems.SPRING_PIXIE_WING_TIARA.get());
        basicItem(ModItems.SUMMER_PIXIE_WING_TIARA.get());
        basicItem(ModItems.WINTER_PIXIE_WING_TIARA.get());
        basicItem(ModItems.SHADOW_WING_TIARA.get());
        basicItem(ModItems.LIGHT_WING_TIARA.get());
        simpleItem(ModItems.SUMMONING_SCROLL_SPRING_PIXIE);
        simpleItem(ModItems.SUMMONING_SCROLL_AUTUMN_PIXIE);
        simpleItem(ModItems.SUMMONING_SCROLL_SUMMER_PIXIE);
        simpleItem(ModItems.SUMMONING_SCROLL_WINTER_PIXIE);
        simpleItem(ModItems.SUMMONING_SCROLL_SHROOMLING);
        simpleItem(ModItems.SUMMONING_SCROLL_MANDRAGORA);
        simpleItem(ModItems.SUMMONING_SCROLL_BELLSNICKEL);
        simpleItem(ModItems.SUMMONING_SCROLL_BEE_KNIGHT);
        simpleItem(ModItems.SUMMONING_SCROLL_SPRING_TREE_ENT);
        simpleItem(ModItems.SUMMONING_SCROLL_AUTUMN_TREE_ENT);
        simpleItem(ModItems.SUMMONING_SCROLL_SUMMER_TREE_ENT);
        simpleItem(ModItems.SUMMONING_SCROLL_WINTER_TREE_ENT);
        basicItem(ModItems.SUMMONING_SCROLL_QUEEN_TITANIA.get());
        basicItem(ModItems.SUMMONING_SCROLL_QUEEN_MAB.get());
        basicItem(ModItems.SUMMONING_SCROLL_OBERON.get());
        basicItem(ModItems.SUMMONING_SCROLL_ASHEN_LORD.get());

        simpleItem(ModItems.FEY_DUST);
        simpleItem(ModItems.MANDRAKE);
        simpleItem(ModItems.GIANT_CROCUS_FLOWER_SEED);
        simpleItem(ModItems.GIANT_DANDELION_FLOWER_SEED);
        simpleItem(ModItems.GIANT_SUN_FLOWER_SEED);

        saplingItem(ModBlocks.AUTUMN_TREE_SAPLING);
        saplingItem(ModBlocks.SPRING_TREE_SAPLING);
        saplingItem(ModBlocks.SUMMER_TREE_SAPLING);
        saplingItem(ModBlocks.WINTER_TREE_SAPLING);
    }

    private ItemModelBuilder saplingItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
              new  ResourceLocation("item/generated")).texture("layer0",
              new  ResourceLocation(Feywild.MOD_ID,"block/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Feywild.MOD_ID,"item/" + item.getId().getPath()));
    }
}
