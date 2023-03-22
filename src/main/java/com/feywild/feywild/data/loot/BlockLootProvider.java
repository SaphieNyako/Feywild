package com.feywild.feywild.data.loot;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.ModTrees;
import com.feywild.feywild.block.trees.BaseTree;
import com.feywild.feywild.block.trees.FeyLeavesBlock;
import com.feywild.feywild.item.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraftforge.common.Tags;
import org.moddingx.libx.annotation.data.Datagen;
import org.moddingx.libx.base.decoration.DecorationType;
import org.moddingx.libx.datagen.provider.loot.BlockLootProviderBase;
import org.moddingx.libx.mod.ModX;

import static net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties;
import static net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition.hasBlockStateProperties;

@Datagen
@SuppressWarnings("unchecked")
public class BlockLootProvider extends BlockLootProviderBase {

    public BlockLootProvider(ModX mod, DataGenerator generator) {
        super(mod, generator);
    }

    @Override
    protected void setup() {

        this.leavesDrops(ModBlocks.autumnBrownLeaves, ModTrees.autumnTree);
        this.leavesDrops(ModBlocks.autumnRedLeaves, ModTrees.autumnTree);
        this.leavesDrops(ModBlocks.autumnDarkGrayLeaves, ModTrees.autumnTree);
        this.leavesDrops(ModBlocks.autumnLightGrayLeaves, ModTrees.autumnTree);
        this.leavesDrops(ModBlocks.springCyanLeaves, ModTrees.springTree);
        this.leavesDrops(ModBlocks.springGreenLeaves, ModTrees.springTree);
        this.leavesDrops(ModBlocks.springLimeLeaves, ModTrees.springTree);
        this.leavesDrops(ModBlocks.winterBlueLeaves, ModTrees.winterTree);
        this.leavesDrops(ModBlocks.winterLightBlueLeaves, ModTrees.winterTree);
        this.leavesDrops(ModBlocks.summerOrangeLeaves, ModTrees.summerTree);
        this.leavesDrops(ModBlocks.summerYellowLeaves, ModTrees.summerTree);
        this.leavesDrops(ModBlocks.hexBlackLeaves, ModTrees.hexenTree);
        this.leavesDrops(ModBlocks.hexPurpleLeaves, ModTrees.hexenTree);
        this.leavesDrops(ModBlocks.blossomMagentaLeaves, ModTrees.blossomTree);
        this.leavesDrops(ModBlocks.blossomPinkLeaves, ModTrees.blossomTree);
        this.leavesDrops(ModBlocks.blossomWhiteLeaves, ModTrees.blossomTree);

        this.drops(ModBlocks.feyAltar, this.stack(ModBlocks.feyAltar));

        DoorBlock doorAutumn = ModTrees.autumnTree.getPlankBlock().get(DecorationType.DOOR);
        this.drops(doorAutumn, false, this.identity().with(LootItemBlockStatePropertyCondition.hasBlockStateProperties(doorAutumn)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER))));
        DoorBlock doorSpring = ModTrees.springTree.getPlankBlock().get(DecorationType.DOOR);
        this.drops(doorSpring, false, this.identity().with(LootItemBlockStatePropertyCondition.hasBlockStateProperties(doorSpring)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER))));
        DoorBlock doorSummer = ModTrees.summerTree.getPlankBlock().get(DecorationType.DOOR);
        this.drops(doorSummer, false, this.identity().with(LootItemBlockStatePropertyCondition.hasBlockStateProperties(doorSummer)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER))));
        DoorBlock doorWinter = ModTrees.winterTree.getPlankBlock().get(DecorationType.DOOR);
        this.drops(doorWinter, false, this.identity().with(LootItemBlockStatePropertyCondition.hasBlockStateProperties(doorWinter)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER))));
        DoorBlock doorHexen = ModTrees.hexenTree.getPlankBlock().get(DecorationType.DOOR);
        this.drops(doorHexen, false, this.identity().with(LootItemBlockStatePropertyCondition.hasBlockStateProperties(doorHexen)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER))));
        DoorBlock doorBlossom = ModTrees.blossomTree.getPlankBlock().get(DecorationType.DOOR);
        this.drops(doorBlossom, false, this.identity().with(LootItemBlockStatePropertyCondition.hasBlockStateProperties(doorBlossom)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER))));


        this.drops(ModBlocks.sunflower, this.stack(ModBlocks.sunflower).with(this.count(1, 2)));
        this.drops(ModBlocks.dandelion, this.stack(ModBlocks.dandelion).with(this.count(1, 2)));
        this.drops(ModBlocks.crocus, this.stack(ModBlocks.crocus).with(this.count(1, 2)));

        this.drops(ModBlocks.feyStarBlockGreen, true, this.combine(

                this.stack(ModItems.rawElvenQuartz).with(this.count(2, 4)),
                this.stack(ModItems.rawSpringElvenQuartz).with(this.count(2, 4))

        ));

        this.drops(ModBlocks.feyStarBlockLightBlue, true, this.combine(

                this.stack(ModItems.rawWinterElvenQuartz).with(this.count(2, 4)),
                this.stack(ModItems.rawSpringElvenQuartz).with(this.count(2, 4))

        ));

        this.drops(ModBlocks.feyStarBlockBlue, true, this.combine(

                this.stack(ModItems.rawElvenQuartz).with(this.count(2, 4)),
                this.stack(ModItems.rawWinterElvenQuartz).with(this.count(2, 4))

        ));

        this.drops(ModBlocks.feyStarBlockPink, true, this.combine(

                this.stack(ModItems.rawSpringElvenQuartz).with(this.count(2, 4)),
                this.stack(ModItems.rawSummerElvenQuartz).with(this.count(2, 4))

        ));

        this.drops(ModBlocks.feyStarBlockOrange, true, this.combine(

                this.stack(ModItems.rawElvenQuartz).with(this.count(2, 4)),
                this.stack(ModItems.rawAutumnElvenQuartz).with(this.count(2, 4))

        ));

        this.drops(ModBlocks.feyStarBlockYellow, true, this.combine(

                this.stack(ModItems.rawElvenQuartz).with(this.count(2, 4)),
                this.stack(ModItems.rawSummerElvenQuartz).with(this.count(2, 4))

        ));

        this.drops(ModBlocks.feyStarBlockPurple, true, this.combine(

                this.stack(ModItems.rawWinterElvenQuartz).with(this.count(2, 4)),
                this.stack(ModItems.rawAutumnElvenQuartz).with(this.count(2, 4))

        ));


        this.drops(ModBlocks.feyGemOre, true, this.first(
                this.stack(ModItems.lesserFeyGem).with(this.random(0.6f)),
                this.stack(ModItems.greaterFeyGem).with(this.random(0.6f)),
                this.stack(ModItems.shinyFeyGem).with(this.random(0.6f)),
                this.stack(ModItems.brilliantFeyGem)
        ));

        this.drops(ModBlocks.feyGemOreDeepSlate, true, this.first(
                this.stack(ModItems.lesserFeyGem).with(this.random(0.5f)),
                this.stack(ModItems.greaterFeyGem).with(this.random(0.5f)),
                this.stack(ModItems.shinyFeyGem).with(this.random(0.5f)),
                this.stack(ModItems.brilliantFeyGem)
        ));

        this.drops(ModBlocks.feyGemOreLivingrock, true, this.first(
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
                this.element().with(this.or(this.silkCondition(), this.matchTool(Tags.Items.SHEARS))),
                this.stack(Items.RED_MUSHROOM).with(this.randomFortune(0.5f))
        ));

        this.drops(ModBlocks.dandelion, this.stack(ModBlocks.dandelion.getSeed()).with(this.count(1, 2)));
        this.drops(ModBlocks.sunflower, this.stack(ModBlocks.sunflower.getSeed()).with(this.count(1, 2)));
        this.drops(ModBlocks.crocus, this.stack(ModBlocks.crocus.getSeed()).with(this.count(1, 2)));
    }

    private void leavesDrops(FeyLeavesBlock leaves, BaseTree tree) {
        this.drops(leaves, this.first(
                this.element().with(this.or(this.silkCondition(), this.matchTool(Tags.Items.SHEARS))),
                this.combine(
                        this.stack(tree.getSapling()).with(this.randomFortune(0.02f)),
                        this.stack(Items.STICK).with(this.count(1, 2)).with(this.randomFortune(0.02f)),
                        this.stack(ModItems.feyDust).with(this.count(1, 2)).with(this.randomFortune(0.01f, 0.011f, 0.0125f, 0.032f, 0.05f)),
                        this.stack(ModBlocks.mandrakeCrop.getSeed()).with(this.randomFortune(0.005f))
                )
        ));
    }
}
