package com.feywild.feywild.data.tags;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.ModTrees;
import com.feywild.feywild.block.flower.GiantFlowerBlock;
import com.feywild.feywild.block.trees.BaseTree;
import com.feywild.feywild.block.trees.FeyLeavesBlock;
import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.item.Schematics;
import com.feywild.feywild.tag.ModBlockTags;
import com.feywild.feywild.tag.ModItemTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import org.moddingx.libx.datagen.DatagenContext;
import org.moddingx.libx.datagen.provider.tags.CommonTagsProviderBase;

import javax.annotation.Nullable;

public class CommonTagsProvider extends CommonTagsProviderBase {

    public CommonTagsProvider(DatagenContext ctx) {
        super(ctx);
    }

    @Override
    public void setup() {
        this.item(ItemTags.CREEPER_DROP_MUSIC_DISCS).add(ModItems.feywildMusicDisc);

        this.item(ModItemTags.PIXIE_WING_COMPONENTS).add(ModItems.pixieWingTiara);
        this.item(ModItemTags.PIXIE_WING_COMPONENTS).add(ModItems.feyWingsWinter);
        this.item(ModItemTags.PIXIE_WING_COMPONENTS).add(ModItems.feyWingsSummer);
        this.item(ModItemTags.PIXIE_WING_COMPONENTS).add(ModItems.feyWingsAutumn);
        this.item(ModItemTags.PIXIE_WING_COMPONENTS).add(ModItems.feyWingsSpring);
        this.item(ModItemTags.PIXIE_WING_COMPONENTS).add(ModItems.feyWingsLight);
        this.item(ModItemTags.PIXIE_WING_COMPONENTS).add(ModItems.feyWingsShadow);

        this.item(ModItemTags.YGGDRASIL_BOOKS).add(ModItems.schematicsYggdrasilRuneCrafting);
        this.item(ModItemTags.DEADLY_BOOKS).add(ModItems.schematicsDeadlyRuneCrafting);
        this.item(ModItemTags.DEADLY_BOOKS).addTag(ModItemTags.YGGDRASIL_BOOKS);
        this.item(ModItemTags.SEASONAL_BOOKS).add(ModItems.schematicsSeasonalRuneCrafting);
        this.item(ModItemTags.SEASONAL_BOOKS).addTag(ModItemTags.DEADLY_BOOKS);
        this.item(ModItemTags.ELEMENTAL_BOOKS).add(ModItems.schematicsElementalRuneCrafting);
        this.item(ModItemTags.ELEMENTAL_BOOKS).addTag(ModItemTags.SEASONAL_BOOKS);

        tool(ModBlocks.feyGemOre, BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL);
        tool(ModBlocks.feyGemOreDeepSlate, BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL);
        tool(ModBlocks.feyGemOreLivingrock, BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL);
        tool(ModBlocks.dwarvenAnvil, BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL);
        tool(ModBlocks.feyStarBlockGreen, BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL);
        tool(ModBlocks.feyStarBlockLightBlue, BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL);
        tool(ModBlocks.feyStarBlockBlue, BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL);
        tool(ModBlocks.feyStarBlockPurple, BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL);
        tool(ModBlocks.feyStarBlockPink, BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL);
        tool(ModBlocks.feyStarBlockOrange, BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL);
        tool(ModBlocks.feyStarBlockYellow, BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL);
        tool(ModBlocks.summerFeyAltar, BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL);
        tool(ModBlocks.winterFeyAltar, BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL);
        tool(ModBlocks.autumnFeyAltar, BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL);
        tool(ModBlocks.treeMushroom, BlockTags.MINEABLE_WITH_HOE, null);
        
        treeTags(ModTrees.springTree);
        treeTags(ModTrees.summerTree);
        treeTags(ModTrees.autumnTree);
        treeTags(ModTrees.winterTree);
        treeTags(ModTrees.blossomTree);
        treeTags(ModTrees.hexenTree);

        this.item(ModItemTags.COOKIES).add(Items.COOKIE);
        this.item(ModItemTags.COOKIES).add(ModItems.magicalHoneyCookie);
        
        this.block(Tags.Blocks.ORES).add(ModBlocks.feyGemOre, ModBlocks.feyGemOreDeepSlate, ModBlocks.feyGemOreLivingrock);

        this.copyBlock(BlockTags.LOGS, ItemTags.LOGS);
        this.copyBlock(ModBlockTags.FEY_LOGS, ModItemTags.FEY_LOGS);
        this.copyBlock(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
        this.copyBlock(BlockTags.PLANKS, ItemTags.PLANKS);
        this.copyBlock(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
        this.copyBlock(BlockTags.LEAVES, ItemTags.LEAVES);
    }

    private void tool(Block block, TagKey<Block> tool, @Nullable TagKey<Block> level) {
        this.block(tool).add(block);
        if (level != null) this.block(level).add(block);
    }

    private void treeTags(BaseTree tree) {
        this.block(tree.getBlockLogTag()).add(
                tree.getLogBlock(),
                tree.getStrippedLogBlock(),
                tree.getWoodBlock(),
                tree.getStrippedWoodBlock(),
                tree.getCrackedLogBlock()
        );

        this.block(BlockTags.LOGS).addTag(tree.getBlockLogTag());
        this.block(ModBlockTags.FEY_LOGS).addTag(tree.getBlockLogTag());
        this.block(BlockTags.LOGS_THAT_BURN).addTag(tree.getBlockLogTag());
        this.block(BlockTags.PLANKS).add(tree.getPlankBlock());
        this.block(BlockTags.SAPLINGS).add(tree.getSapling());
        
        this.copyBlock(tree.getBlockLogTag(), tree.getItemLogTag());
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
        } else if (block instanceof FeyLeavesBlock) {
            this.block(BlockTags.LEAVES).add(block);
            this.tool(block, BlockTags.MINEABLE_WITH_HOE, null);
        }
    }
}
