package com.feywild.feywild.data;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.ModTrees;
import com.feywild.feywild.block.flower.GiantFlowerBlock;
import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.item.Schematics;
import com.feywild.feywild.tag.ModBlockTags;
import com.feywild.feywild.tag.ModItemTags;
import com.feywild.feywild.block.trees.BaseTree;
import org.moddingx.libx.annotation.data.Datagen;
import org.moddingx.libx.datagen.provider.CommonTagsProviderBase;
import org.moddingx.libx.mod.ModX;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

@Datagen
public class CommonTagsProvider extends CommonTagsProviderBase {

    public CommonTagsProvider(ModX mod, DataGenerator generator, ExistingFileHelper fileHelper) {
        super(mod, generator, fileHelper);
    }

    @Override
    public void setup() {
        this.item(ItemTags.CREEPER_DROP_MUSIC_DISCS).add(ModItems.feywildMusicDisc);

        this.item(ModItemTags.YGGDRASIL_BOOKS).add(ModItems.schematicsYggdrasilRuneCrafting);
        this.item(ModItemTags.DEADLY_BOOKS).add(ModItems.schematicsDeadlyRuneCrafting);
        this.item(ModItemTags.DEADLY_BOOKS).addTag(ModItemTags.YGGDRASIL_BOOKS);
        this.item(ModItemTags.SEASONAL_BOOKS).add(ModItems.schematicsSeasonalRuneCrafting);
        this.item(ModItemTags.SEASONAL_BOOKS).addTag(ModItemTags.DEADLY_BOOKS);
        this.item(ModItemTags.ELEMENTAL_BOOKS).add(ModItems.schematicsElementalRuneCrafting);
        this.item(ModItemTags.ELEMENTAL_BOOKS).addTag(ModItemTags.SEASONAL_BOOKS);

        tool(ModBlocks.feyGemBlock, BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL);
        tool(ModBlocks.feyGemBlockDeepSlate, BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL);
        tool(ModBlocks.feyGemBlockLivingrock, BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL);
        tool(ModBlocks.dwarvenAnvil, BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL);

        treeTags(ModTrees.springTree, ModBlockTags.SPRING_LOGS, ModItemTags.SPRING_LOGS);
        treeTags(ModTrees.summerTree, ModBlockTags.SUMMER_LOGS, ModItemTags.SUMMER_LOGS);
        treeTags(ModTrees.autumnTree, ModBlockTags.AUTUMN_LOGS, ModItemTags.AUTUMN_LOGS);
        treeTags(ModTrees.winterTree, ModBlockTags.WINTER_LOGS, ModItemTags.WINTER_LOGS);

        this.block(BlockTags.LOGS_THAT_BURN).addTag(ModBlockTags.FEY_LOGS);
        this.copyBlock(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
    }

    private void tool(Block block, TagKey<Block> tool, @Nullable TagKey<Block> level) {
        this.block(tool).add(block);
        if (level != null) this.block(level).add(block);
    }

    private void treeTags(BaseTree tree, TagKey<Block> logs, TagKey<Item> logItems) {
        this.block(logs).add(
                tree.getLogBlock(),
                tree.getStrippedLogBlock(),
                tree.getWoodBlock(),
                tree.getStrippedWoodBlock()
        );
        this.block(ModBlockTags.FEY_LOGS).addTag(logs);
        this.block(BlockTags.PLANKS).add(tree.getPlankBlock());
        this.block(BlockTags.LEAVES).add(tree.getLeafBlock());
        this.block(BlockTags.SAPLINGS).add(tree.getSapling());
        tool(tree.getLeafBlock(), BlockTags.MINEABLE_WITH_HOE, null);

        this.copyBlock(logs, logItems);
        this.copyBlock(ModBlockTags.FEY_LOGS, ModItemTags.FEY_LOGS);
        this.copyBlock(BlockTags.PLANKS, ItemTags.PLANKS);
        this.copyBlock(BlockTags.LEAVES, ItemTags.LEAVES);
        this.copyBlock(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
    }

    @Override
    public void defaultItemTags(Item item) {
        if (item instanceof Schematics) {
            this.item(ModItemTags.SCHEMATICS).add(item);
        }
    }

    @Override
    public void defaultBlockTags(Block block) {
        if (block instanceof GiantFlowerBlock) {
            this.block(BlockTags.TALL_FLOWERS).add(block);
            this.block(BlockTags.MINEABLE_WITH_AXE).add(block);
        }
    }
}
