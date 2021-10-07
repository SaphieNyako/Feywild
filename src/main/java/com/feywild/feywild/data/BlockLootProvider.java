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

public class BlockLootProvider extends BlockLootProviderBase {

    public BlockLootProvider(ModX mod, DataGenerator generator) {
        super(mod, generator);
    }

    @Override
    protected void setup() {
        this.treeDrops(ModTrees.springTree, Blocks.OAK_LOG, ModTrees.springTree.getWoodBlock());
        this.treeDrops(ModTrees.autumnTree, Blocks.DARK_OAK_LOG, ModTrees.autumnTree.getWoodBlock());
        this.treeDrops(ModTrees.winterTree, Blocks.SPRUCE_LOG, ModTrees.winterTree.getWoodBlock());

        this.drops(ModTrees.summerTree.getLeafBlock(), this.first(
                this.item().with(this.or(this.silkCondition(), this.matchTool(Tags.Items.SHEARS))),
                this.combine(
                        this.stack(ModTrees.summerTree.getSapling()).with(this.randomFortune(0.02f)),
                        this.stack(Items.STICK).with(this.count(1, 2)).with(this.randomFortune(0.02f)),
                        this.stack(ModItems.feyDust).with(this.count(1, 2)).with(this.randomFortune(0.01f, 0.011f, 0.0125f, 0.032f, 0.05f)),
                        this.stack(ModBlocks.mandrakeCrop.getSeed()).with(this.randomFortune(0.005f)),
                        this.stack(Items.SWEET_BERRIES).with(this.count(1, 2)).with(this.randomFortune(0.02f))
                )
        ));
        this.drops(ModTrees.summerTree.getLogBlock(), true, this.stack(Blocks.BIRCH_LOG));
        this.drops(ModTrees.summerTree.getWoodBlock(), false, this.stack(ModTrees.summerTree.getWoodBlock()));

        this.drops(ModBlocks.sunflower, this.stack(ModBlocks.sunflower).with(this.count(1, 2)));
        this.drops(ModBlocks.dandelion, this.stack(ModBlocks.dandelion).with(this.count(1, 2)));
        this.drops(ModBlocks.crocus, this.stack(ModBlocks.crocus).with(this.count(1, 2)));

        this.drops(ModBlocks.feyGemBlock, true, this.first(
                this.stack(ModItems.lesserFeyGem).with(this.random(0.6f)),
                this.stack(ModItems.greaterFeyGem).with(this.random(0.6f)),
                this.stack(ModItems.shinyFeyGem).with(this.random(0.6f)),
                this.stack(ModItems.brilliantFeyGem)
        ));

        this.drops(ModBlocks.feyGemBlockLivingrock, true, this.first(
                this.stack(ModItems.lesserFeyGem).with(this.random(0.6f)),
                this.stack(ModItems.greaterFeyGem).with(this.random(0.6f)),
                this.stack(ModItems.shinyFeyGem).with(this.random(0.6f)),
                this.stack(ModItems.brilliantFeyGem)
        ));

        this.drops(ModBlocks.mandrakeCrop, this.combine(
                this.stack(ModItems.mandrake),
                this.stack(ModBlocks.mandrakeCrop.getSeed()).with(this.fortuneBinomial(0.6f, 1))
        ).with(hasBlockStateProperties(ModBlocks.mandrakeCrop).setProperties(
                properties().hasProperty(BlockStateProperties.AGE_7, 7)
        )));

        this.drops(ModBlocks.treeMushroom, this.first(
                this.item().with(this.or(this.silkCondition(), this.matchTool(Tags.Items.SHEARS))),
                this.stack(Items.RED_MUSHROOM).with(this.randomFortune(0.5f))
        ));

        this.drops(ModBlocks.dandelion, this.stack(ModBlocks.dandelion.getSeed()).with(this.count(1, 2)));
        this.drops(ModBlocks.sunflower, this.stack(ModBlocks.sunflower.getSeed()).with(this.count(1, 2)));
        this.drops(ModBlocks.crocus, this.stack(ModBlocks.crocus.getSeed()).with(this.count(1, 2)));
    }

    private void treeDrops(BaseTree tree, IItemProvider baseLog, IItemProvider baseWood) {
        this.drops(tree.getLeafBlock(), this.first(
                this.item().with(this.or(this.silkCondition(), this.matchTool(Tags.Items.SHEARS))),
                this.combine(
                        this.stack(tree.getSapling()).with(this.randomFortune(0.02f)),
                        this.stack(Items.STICK).with(this.count(1, 2)).with(this.randomFortune(0.02f)),
                        this.stack(ModItems.feyDust).with(this.count(1, 2)).with(this.randomFortune(0.01f, 0.011f, 0.0125f, 0.032f, 0.05f)),
                        this.stack(ModBlocks.mandrakeCrop.getSeed()).with(this.randomFortune(0.005f))
                )
        ));
        this.drops(tree.getLogBlock(), true, this.stack(baseLog));
        this.drops(tree.getWoodBlock(), false, this.stack(baseWood));
    }
}
