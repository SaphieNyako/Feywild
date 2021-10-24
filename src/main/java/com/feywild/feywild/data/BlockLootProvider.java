package com.feywild.feywild.data;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.ModTrees;
import com.feywild.feywild.block.trees.BaseTree;
import com.feywild.feywild.item.ModItems;
import io.github.noeppi_noeppi.libx.annotation.data.Datagen;
import io.github.noeppi_noeppi.libx.data.provider.BlockLootProviderBase;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;

import static net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties;
import static net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition.hasBlockStateProperties;

@Datagen
public class BlockLootProvider extends BlockLootProviderBase {

    public BlockLootProvider(ModX mod, DataGenerator generator) {
        super(mod, generator);
    }

    @Override
    protected void setup() {
        this.treeDrops(ModTrees.springTree, ModTrees.springTree.getLogBlock(), ModTrees.springTree.getWoodBlock());
        this.treeDrops(ModTrees.autumnTree, ModTrees.autumnTree.getLogBlock(), ModTrees.autumnTree.getWoodBlock());
        this.treeDrops(ModTrees.winterTree, ModTrees.winterTree.getLogBlock(), ModTrees.winterTree.getWoodBlock());

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
        this.drops(ModTrees.summerTree.getLogBlock(), false, this.stack(ModTrees.summerTree.getLogBlock()));
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

    private void treeDrops(BaseTree tree, ItemLike baseLog, ItemLike baseWood) {
        this.drops(tree.getLeafBlock(), this.first(
                this.item().with(this.or(this.silkCondition(), this.matchTool(Tags.Items.SHEARS))),
                this.combine(
                        this.stack(tree.getSapling()).with(this.randomFortune(0.02f)),
                        this.stack(Items.STICK).with(this.count(1, 2)).with(this.randomFortune(0.02f)),
                        this.stack(ModItems.feyDust).with(this.count(1, 2)).with(this.randomFortune(0.01f, 0.011f, 0.0125f, 0.032f, 0.05f)),
                        this.stack(ModBlocks.mandrakeCrop.getSeed()).with(this.randomFortune(0.005f))
                )
        ));
        this.drops(tree.getLogBlock(), false, this.stack(baseLog));
        this.drops(tree.getWoodBlock(), false, this.stack(baseWood));
    }
}
