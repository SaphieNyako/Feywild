package com.feywild.feywild.data;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.ModTrees;
import com.feywild.feywild.block.trees.BaseTree;
import com.feywild.feywild.item.ModItems;
import io.github.noeppi_noeppi.libx.data.provider.BlockLootProviderBase;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Items;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.common.Tags;

import static net.minecraft.advancements.criterion.StatePropertiesPredicate.Builder.properties;
import static net.minecraft.loot.conditions.BlockStateProperty.hasBlockStateProperties;

public class BlockLoot extends BlockLootProviderBase {

    public BlockLoot(ModX mod, DataGenerator generator) {
        super(mod, generator);
    }

    @Override
    protected void setup() {
        treeDrops(ModTrees.springTree, Blocks.OAK_LOG, Blocks.OAK_WOOD);
        treeDrops(ModTrees.autumnTree, Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_WOOD);
        treeDrops(ModTrees.winterTree, Blocks.SPRUCE_LOG, Blocks.SPRUCE_WOOD);
        
        this.drops(ModTrees.summerTree.getLeafBlock(), first(
                item().with(or(silkCondition(), matchTool(Tags.Items.SHEARS))),
                combine(
                        stack(ModTrees.summerTree.getSapling()).with(randomFortune(0.02f)),
                        stack(Items.STICK).with(count(1, 2)).with(randomFortune(0.02f)),
                        stack(ModItems.feyDust).with(count(1, 2)).with(randomFortune(0.01f, 0.011f, 0.0125f, 0.032f, 0.05f)),
                        stack(ModBlocks.mandrakeCrop.getSeed()).with(randomFortune(0.005f)),
                        stack(Items.SWEET_BERRIES).with(count(1, 2)).with(randomFortune(0.02f))
                )
        ));
        this.drops(ModTrees.summerTree.getLogBlock(), true, stack(Blocks.BIRCH_LOG));
        this.drops(ModTrees.summerTree.getWoodBlock(), true, stack(Blocks.BIRCH_WOOD));
        
        drops(ModBlocks.sunflower, stack(ModBlocks.sunflower).with(count(1, 2)));
        drops(ModBlocks.dandelion, stack(ModBlocks.dandelion).with(count(1, 2)));
        drops(ModBlocks.crocus, stack(ModBlocks.crocus).with(count(1, 2)));
        
        drops(ModBlocks.feyGemBlock, true, first(
                stack(ModItems.lesserFeyGem).with(random(0.6f)),
                stack(ModItems.greaterFeyGem).with(random(0.6f)),
                stack(ModItems.shinyFeyGem).with(random(0.6f)),
                stack(ModItems.brilliantFeyGem)
        ));
        
        drops(ModBlocks.mandrakeCrop, combine(
                stack(ModItems.mandrake),
                stack(ModBlocks.mandrakeCrop.getSeed()).with(fortuneBinomial(0.6f, 1))
        ).with(hasBlockStateProperties(ModBlocks.mandrakeCrop).setProperties(
                properties().hasProperty(BlockStateProperties.AGE_7, 7)
        )));
        
        drops(ModBlocks.treeMushroom, first(
                item().with(or(silkCondition(), matchTool(Tags.Items.SHEARS))),
                stack(Items.RED_MUSHROOM).with(randomFortune(0.5f))
        ));
        
        drops(ModBlocks.dandelion, stack(ModBlocks.dandelion.getSeed()).with(count(1, 2)));
        drops(ModBlocks.sunflower, stack(ModBlocks.sunflower.getSeed()).with(count(1, 2)));
        drops(ModBlocks.crocus, stack(ModBlocks.crocus.getSeed()).with(count(1, 2)));
    }
    
    private void treeDrops(BaseTree tree, IItemProvider baseLog, IItemProvider baseWood) {
        this.drops(tree.getLeafBlock(), first(
                item().with(or(silkCondition(), matchTool(Tags.Items.SHEARS))),
                combine(
                        stack(tree.getSapling()).with(randomFortune(0.02f)),
                        stack(Items.STICK).with(count(1, 2)).with(randomFortune(0.02f)),
                        stack(ModItems.feyDust).with(count(1, 2)).with(randomFortune(0.01f, 0.011f, 0.0125f, 0.032f, 0.05f)),
                        stack(ModBlocks.mandrakeCrop.getSeed()).with(randomFortune(0.005f))
                )
        ));
        this.drops(tree.getLogBlock(), true, stack(baseLog));
        this.drops(tree.getWoodBlock(), true, stack(baseWood));
    }
}
