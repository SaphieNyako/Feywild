package com.saphienyako.feywild.datagen;


import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.block.ModBlocks;
import com.saphienyako.feywild.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.NotNull;

public class ModModelProvider extends ModelProvider {

    private BlockModelGenerators blockModels;
    private ItemModelGenerators itemModels;

    public ModModelProvider(PackOutput output) {
        super(output, Feywild.MOD_ID);
    }

    @Override
    protected void registerModels(@NotNull BlockModelGenerators blockModels, @NotNull ItemModelGenerators itemModels) {
        registerBlockStates();
    }

    private void registerBlockStates() {
        blockWithItem(ModBlocks.FEY_GEM_ORE);
        blockWithItem(ModBlocks.FEY_GEM_ORE_DEEP_SLATE);

        registerElvenQuartz();
        registerTreeBlocks();
    }

    private void registerItemModels() {
        registerBasicItems();
        registerSaplingItems();
    }

    private void registerBasicItems() {
        basicItem(ModItems.FEYWILD_LEXICON.get());
        basicItem(ModItems.FEY_GEM.get());
        basicItem(ModItems.MANDRAKE_ROOT.get());
        basicItem(ModItems.FEY_INK_BOTTLE.get());
        basicItem(ModItems.FEYWILD_MUSIC_DISC.get());
        basicItem(ModItems.EMPTY_SUMMONING_SCROLL.get());
        basicItem(ModItems.PIXIE_ORB.get());
        basicItem(ModItems.PIXIE_WING_TIARA.get());
        basicItem(ModItems.AUTUMN_PIXIE_WING_TIARA.get());
        basicItem(ModItems.SPRING_PIXIE_WING_TIARA.get());
        basicItem(ModItems.SUMMER_PIXIE_WING_TIARA.get());
        basicItem(ModItems.WINTER_PIXIE_WING_TIARA.get());
        basicItem(ModItems.SHADOW_WING_TIARA.get());
        basicItem(ModItems.LIGHT_WING_TIARA.get());
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
        basicItem(ModItems.SUMMONING_SCROLL_QUEEN_TITANIA.get());
        basicItem(ModItems.SUMMONING_SCROLL_QUEEN_MAB.get());
        basicItem(ModItems.SUMMONING_SCROLL_OBERON.get());
        basicItem(ModItems.SUMMONING_SCROLL_ASHEN_LORD.get());
        basicItem(ModItems.FEY_DUST.get());
        basicItem(ModItems.MANDRAKE.get());
        basicItem(ModItems.GIANT_CROCUS_FLOWER_SEED.get());
        basicItem(ModItems.GIANT_DANDELION_FLOWER_SEED.get());
        basicItem(ModItems.GIANT_SUN_FLOWER_SEED.get());
    }

    private void registerSaplingItems(){
        saplingItem(ModBlocks.AUTUMN_TREE_SAPLING);
        saplingItem(ModBlocks.SPRING_TREE_SAPLING);
        saplingItem(ModBlocks.SUMMER_TREE_SAPLING);
        saplingItem(ModBlocks.WINTER_TREE_SAPLING);
    }

    private void registerElvenQuartz() {
        registerDefaultElvenQuartz();
        registerSpringElvenQuartz();
        registerSummerElvenQuartz();
        registerAutumnElvenQuartz();
        registerWinterElvenQuartz();
    }

    private void registerTreeBlocks(){
        registerSpringTreeBlocks();
        registerSummerTreeBlocks();
        registerAutumnTreeBlocks();
        registerWinterTreeBlocks();
    }

    private void registerDefaultElvenQuartz() {

        blockWithItem(ModBlocks.ELVEN_QUARTZ_BLOCK);
        stairsBlock(((StairBlock) ModBlocks.ELVEN_QUARTZ_STAIRS.get()), ModBlocks.ELVEN_QUARTZ_BLOCK.get());
        slabBlock(((SlabBlock) ModBlocks.ELVEN_QUARTZ_SLAB.get()), ModBlocks.ELVEN_QUARTZ_BLOCK.get());
        blockItem(ModBlocks.ELVEN_QUARTZ_STAIRS);
        blockItem(ModBlocks.ELVEN_QUARTZ_SLAB);

        blockWithItem(ModBlocks.ELVEN_QUARTZ_BRICK);
        stairsBlock(((StairBlock) ModBlocks.ELVEN_QUARTZ_BRICK_STAIRS.get()), ModBlocks.ELVEN_QUARTZ_BRICK.get());
        slabBlock(((SlabBlock) ModBlocks.ELVEN_QUARTZ_BRICK_SLAB.get()), ModBlocks.ELVEN_QUARTZ_BRICK.get());
        blockItem(ModBlocks.ELVEN_QUARTZ_BRICK_STAIRS);
        blockItem(ModBlocks.ELVEN_QUARTZ_BRICK_SLAB);

        //SKIP MOSSY

        blockWithItem(ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK);
        stairsBlock(((StairBlock) ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get()), ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK.get());
        slabBlock(((SlabBlock) ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get()),ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK.get());
        blockItem(ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK_STAIRS);
        blockItem(ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK_SLAB);

        axisBlock((RotatedPillarBlock) ModBlocks.ELVEN_QUARTZ_PILLAR.get(), ModBlocks.ELVEN_QUARTZ_PILLAR.get(), Identifier.fromNamespaceAndPath(Feywild.MOD_ID,"block/elven_quartz_pillar_top"));
        blockItem(ModBlocks.ELVEN_QUARTZ_PILLAR);

        blockWithItem(ModBlocks.ELVEN_QUARTZ_POLISHED);
        stairsBlock(((StairBlock) ModBlocks.ELVEN_QUARTZ_POLISHED_STAIRS.get()), ModBlocks.ELVEN_QUARTZ_POLISHED.get());
        slabBlock(((SlabBlock) ModBlocks.ELVEN_QUARTZ_POLISHED_SLAB.get()), ModBlocks.ELVEN_QUARTZ_POLISHED.get());
        blockItem(ModBlocks.ELVEN_QUARTZ_POLISHED_STAIRS);
        blockItem(ModBlocks.ELVEN_QUARTZ_POLISHED_SLAB);
    }

    private void registerSpringElvenQuartz(){
        blockWithItem(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK);
        stairsBlock(((StairBlock) ModBlocks.SPRING_ELVEN_QUARTZ_STAIRS.get()), ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get());
        slabBlock(((SlabBlock) ModBlocks.SPRING_ELVEN_QUARTZ_SLAB.get()), ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get());
        blockItem(ModBlocks.SPRING_ELVEN_QUARTZ_STAIRS);
        blockItem(ModBlocks.SPRING_ELVEN_QUARTZ_SLAB);

        blockWithItem(ModBlocks.SPRING_ELVEN_QUARTZ_BRICK);
        stairsBlock(((StairBlock) ModBlocks.SPRING_ELVEN_QUARTZ_BRICK_STAIRS.get()), ModBlocks.SPRING_ELVEN_QUARTZ_BRICK.get());
        slabBlock(((SlabBlock) ModBlocks.SPRING_ELVEN_QUARTZ_BRICK_SLAB.get()), ModBlocks.SPRING_ELVEN_QUARTZ_BRICK.get());
        blockItem(ModBlocks.SPRING_ELVEN_QUARTZ_BRICK_STAIRS);
        blockItem(ModBlocks.SPRING_ELVEN_QUARTZ_BRICK_SLAB);

        //SKIP MOSSY

        blockWithItem(ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK);
        stairsBlock(((StairBlock) ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get()), ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK.get());
        slabBlock(((SlabBlock) ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get()), ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK.get());
        blockItem(ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS);
        blockItem(ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK_SLAB);

        axisBlock((RotatedPillarBlock) ModBlocks.SPRING_ELVEN_QUARTZ_PILLAR.get(), ModBlocks.SPRING_ELVEN_QUARTZ_PILLAR.get(),Identifier.fromNamespaceAndPath(Feywild.MOD_ID,"block/spring_elven_quartz_pillar_top"));
        blockItem(ModBlocks.SPRING_ELVEN_QUARTZ_PILLAR);

        blockWithItem(ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED);
        stairsBlock(((StairBlock) ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED_STAIRS.get()), ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED.get());
        slabBlock(((SlabBlock) ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED_SLAB.get()), ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED.get());
        blockItem(ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED_STAIRS);
        blockItem(ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED_SLAB);
    }

    private void registerSummerElvenQuartz(){
        blockWithItem(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK);
        stairsBlock(((StairBlock) ModBlocks.SUMMER_ELVEN_QUARTZ_STAIRS.get()), ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get());
        slabBlock(((SlabBlock) ModBlocks.SUMMER_ELVEN_QUARTZ_SLAB.get()), ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get());
        blockItem(ModBlocks.SUMMER_ELVEN_QUARTZ_STAIRS);
        blockItem(ModBlocks.SUMMER_ELVEN_QUARTZ_SLAB);

        blockWithItem(ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK);
        stairsBlock(((StairBlock) ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK_STAIRS.get()), ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK.get());
        slabBlock(((SlabBlock) ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK_SLAB.get()), ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK.get());
        blockItem(ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK_STAIRS);
        blockItem(ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK_SLAB);

        //SKIP MOSSY

        blockWithItem(ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK);
        stairsBlock(((StairBlock) ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get()), ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK.get());
        slabBlock(((SlabBlock) ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get()), ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK.get());
        blockItem(ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS);
        blockItem(ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK_SLAB);

        axisBlock((RotatedPillarBlock) ModBlocks.SUMMER_ELVEN_QUARTZ_PILLAR.get(), ModBlocks.SUMMER_ELVEN_QUARTZ_PILLAR.get(),Identifier.fromNamespaceAndPath(Feywild.MOD_ID,"block/summer_elven_quartz_pillar_top"));
        blockItem(ModBlocks.SUMMER_ELVEN_QUARTZ_PILLAR);

        blockWithItem(ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED);
        stairsBlock(((StairBlock) ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED_STAIRS.get()), ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED.get());
        slabBlock(((SlabBlock) ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED_SLAB.get()), ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED.get());
        blockItem(ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED_STAIRS);
        blockItem(ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED_SLAB);
    }

    private void registerAutumnElvenQuartz(){
        blockWithItem(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK);
        stairsBlock(((StairBlock) ModBlocks.AUTUMN_ELVEN_QUARTZ_STAIRS.get()), ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get());
        slabBlock(((SlabBlock) ModBlocks.AUTUMN_ELVEN_QUARTZ_SLAB.get()), ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get());
        blockItem(ModBlocks.AUTUMN_ELVEN_QUARTZ_STAIRS);
        blockItem(ModBlocks.AUTUMN_ELVEN_QUARTZ_SLAB);

        blockWithItem(ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK);
        stairsBlock(((StairBlock) ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK_STAIRS.get()), ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK.get());
        slabBlock(((SlabBlock) ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK_SLAB.get()), ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK.get());
        blockItem(ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK_STAIRS);
        blockItem(ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK_SLAB);

        //SKIP MOSSY

        blockWithItem(ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK);
        stairsBlock(((StairBlock) ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get()), ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK.get());
        slabBlock(((SlabBlock) ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get()), ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK.get());
        blockItem(ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS);
        blockItem(ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK_SLAB);

        axisBlock((RotatedPillarBlock) ModBlocks.AUTUMN_ELVEN_QUARTZ_PILLAR.get(), ModBlocks.AUTUMN_ELVEN_QUARTZ_PILLAR.get(),Identifier.fromNamespaceAndPath(Feywild.MOD_ID,"block/autumn_elven_quartz_pillar_top"));
        blockItem(ModBlocks.AUTUMN_ELVEN_QUARTZ_PILLAR);

        blockWithItem(ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED);
        stairsBlock(((StairBlock) ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED_STAIRS.get()), ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED.get());
        slabBlock(((SlabBlock) ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED_SLAB.get()), ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED.get());
        blockItem(ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED_STAIRS);
        blockItem(ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED_SLAB);
    }

    private void registerWinterElvenQuartz(){
        blockWithItem(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK);
        stairsBlock(((StairBlock) ModBlocks.WINTER_ELVEN_QUARTZ_STAIRS.get()), ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get());
        slabBlock(((SlabBlock) ModBlocks.WINTER_ELVEN_QUARTZ_SLAB.get()), ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get());
        blockItem(ModBlocks.WINTER_ELVEN_QUARTZ_STAIRS);
        blockItem(ModBlocks.WINTER_ELVEN_QUARTZ_SLAB);

        blockWithItem(ModBlocks.WINTER_ELVEN_QUARTZ_BRICK);
        stairsBlock(((StairBlock) ModBlocks.WINTER_ELVEN_QUARTZ_BRICK_STAIRS.get()), ModBlocks.WINTER_ELVEN_QUARTZ_BRICK.get());
        slabBlock(((SlabBlock) ModBlocks.WINTER_ELVEN_QUARTZ_BRICK_SLAB.get()), ModBlocks.WINTER_ELVEN_QUARTZ_BRICK.get());
        blockItem(ModBlocks.WINTER_ELVEN_QUARTZ_BRICK_STAIRS);
        blockItem(ModBlocks.WINTER_ELVEN_QUARTZ_BRICK_SLAB);

        //SKIP MOSSY

        blockWithItem(ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK);
        stairsBlock(((StairBlock) ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get()), ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK.get());
        slabBlock(((SlabBlock) ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get()), ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK.get());
        blockItem(ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS);
        blockItem(ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK_SLAB);

        axisBlock((RotatedPillarBlock) ModBlocks.WINTER_ELVEN_QUARTZ_PILLAR.get(), ModBlocks.WINTER_ELVEN_QUARTZ_PILLAR.get(),Identifier.fromNamespaceAndPath(Feywild.MOD_ID,"block/winter_elven_quartz_pillar_top"));
        blockItem(ModBlocks.WINTER_ELVEN_QUARTZ_PILLAR);

        blockWithItem(ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED);
        stairsBlock(((StairBlock) ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED_STAIRS.get()), ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED.get());
        slabBlock(((SlabBlock) ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED_SLAB.get()), ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED.get());
        blockItem(ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED_STAIRS);
        blockItem(ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED_SLAB);
    }

    private void registerSpringTreeBlocks(){
        //LOG
        logBlock(((RotatedPillarBlock) ModBlocks.SPRING_TREE_LOG.get()));

        //CRACKED_LOG BLOCK AND ITEM MANUAL
        //DOOR BLOCK AND ITEM MANUAL
        //TRAPDOOR BLOCK AND ITEM MANUAL

        //WOOD
        axisBlock((RotatedPillarBlock) ModBlocks.SPRING_TREE_WOOD.get(), ModBlocks.SPRING_TREE_LOG.get());
        stairsBlock((StairBlock) ModBlocks.SPRING_TREE_WOOD_STAIRS.get(), ModBlocks.SPRING_TREE_LOG.get());
        slabBlock((SlabBlock) ModBlocks.SPRING_TREE_WOOD_SLAB.get(), ModBlocks.SPRING_TREE_WOOD.get());
        wallBlock((WallBlock) ModBlocks.SPRING_TREE_WOOD_WALL.get(), ModBlocks.SPRING_TREE_LOG.get());

        // STRIPPED LOG
        logBlock(((RotatedPillarBlock) ModBlocks.SPRING_TREE_STRIPPED_LOG.get()));

        //STRIPPED WOOD
        axisBlock((RotatedPillarBlock) ModBlocks.SPRING_TREE_STRIPPED_WOOD.get(), ModBlocks.SPRING_TREE_STRIPPED_LOG.get());
        stairsBlock((StairBlock) ModBlocks.SPRING_TREE_STRIPPED_WOOD_STAIRS.get(), ModBlocks.SPRING_TREE_STRIPPED_LOG.get());
        slabBlock((SlabBlock) ModBlocks.SPRING_TREE_STRIPPED_WOOD_SLAB.get(), ModBlocks.SPRING_TREE_STRIPPED_WOOD.get());
        wallBlock((WallBlock) ModBlocks.SPRING_TREE_STRIPPED_WOOD_WALL.get(), ModBlocks.SPRING_TREE_STRIPPED_LOG.get());

        //PLANKS
        blockWithItem(ModBlocks.SPRING_TREE_PLANKS);
        stairsBlock((StairBlock) ModBlocks.SPRING_TREE_PLANKS_STAIRS.get(), ModBlocks.SPRING_TREE_PLANKS.get());
        slabBlock((SlabBlock) ModBlocks.SPRING_TREE_PLANKS_SLAB.get(), ModBlocks.SPRING_TREE_PLANKS.get());
        fenceBlock((FenceBlock) ModBlocks.SPRING_TREE_PLANKS_FENCE.get(), ModBlocks.SPRING_TREE_PLANKS.get());
        fenceGateBlock((FenceGateBlock) ModBlocks.SPRING_TREE_PLANKS_FENCE_GATE.get(), ModBlocks.SPRING_TREE_PLANKS.get());
        pressurePlateBlock(((PressurePlateBlock) ModBlocks.SPRING_TREE_PLANKS_PRESSURE_PLATE.get()), ModBlocks.SPRING_TREE_PLANKS.get());
        buttonBlock(((ButtonBlock) ModBlocks.SPRING_TREE_PLANKS_BUTTON.get()), ModBlocks.SPRING_TREE_PLANKS.get());

        //LEAVES AND SAPLING
        leavesBlock(ModBlocks.SPRING_TREE_LEAVES_CYAN);
        leavesBlock(ModBlocks.SPRING_TREE_LEAVES_GREEN);
        leavesBlock(ModBlocks.SPRING_TREE_LEAVES_LIME);
        saplingBlock(ModBlocks.SPRING_TREE_SAPLING);

        // ITEMS
        blockItem(ModBlocks.SPRING_TREE_LOG);
        blockItem(ModBlocks.SPRING_TREE_WOOD);
        blockItem(ModBlocks.SPRING_TREE_WOOD_STAIRS);
        blockItem(ModBlocks.SPRING_TREE_WOOD_SLAB);
        wallItem(ModBlocks.SPRING_TREE_WOOD_WALL, ModBlocks.SPRING_TREE_LOG);
        blockItem(ModBlocks.SPRING_TREE_STRIPPED_LOG);
        blockItem(ModBlocks.SPRING_TREE_STRIPPED_WOOD);
        blockItem(ModBlocks.SPRING_TREE_STRIPPED_WOOD_STAIRS);
        blockItem(ModBlocks.SPRING_TREE_STRIPPED_WOOD_SLAB);
        wallItem(ModBlocks.SPRING_TREE_STRIPPED_WOOD_WALL, ModBlocks.SPRING_TREE_STRIPPED_LOG);
        blockItem(ModBlocks.SPRING_TREE_PLANKS_SLAB);
        blockItem(ModBlocks.SPRING_TREE_PLANKS_STAIRS);
        fenceItem(ModBlocks.SPRING_TREE_PLANKS_FENCE, ModBlocks.SPRING_TREE_PLANKS);
        blockItem(ModBlocks.SPRING_TREE_PLANKS_FENCE_GATE);
        blockItem(ModBlocks.SPRING_TREE_PLANKS_PRESSURE_PLATE);
        blockItem(ModBlocks.SPRING_TREE_PLANKS_BUTTON);
    }

   private void registerSummerTreeBlocks(){
       //LOG
       logBlock(((RotatedPillarBlock) ModBlocks.SUMMER_TREE_LOG.get()));

       //CRACKED_LOG BLOCK AND ITEM MANUAL
       //DOOR BLOCK AND ITEM MANUAL
       //TRAPDOOR BLOCK AND ITEM MANUAL

       //WOOD
       axisBlock((RotatedPillarBlock) ModBlocks.SUMMER_TREE_WOOD.get(), ModBlocks.SUMMER_TREE_LOG.get());
       stairsBlock((StairBlock) ModBlocks.SUMMER_TREE_WOOD_STAIRS.get(), ModBlocks.SUMMER_TREE_LOG.get());
       slabBlock((SlabBlock) ModBlocks.SUMMER_TREE_WOOD_SLAB.get(), ModBlocks.SUMMER_TREE_WOOD.get());
       wallBlock((WallBlock) ModBlocks.SUMMER_TREE_WOOD_WALL.get(), ModBlocks.SUMMER_TREE_LOG.get());

       // STRIPPED LOG
       logBlock(((RotatedPillarBlock) ModBlocks.SUMMER_TREE_STRIPPED_LOG.get()));

       //STRIPPED WOOD
       axisBlock((RotatedPillarBlock) ModBlocks.SUMMER_TREE_STRIPPED_WOOD.get(), ModBlocks.SUMMER_TREE_STRIPPED_LOG.get());
       stairsBlock((StairBlock) ModBlocks.SUMMER_TREE_STRIPPED_WOOD_STAIRS.get(), ModBlocks.SUMMER_TREE_STRIPPED_LOG.get());
       slabBlock((SlabBlock) ModBlocks.SUMMER_TREE_STRIPPED_WOOD_SLAB.get(), ModBlocks.SUMMER_TREE_STRIPPED_WOOD.get());
       wallBlock((WallBlock) ModBlocks.SUMMER_TREE_STRIPPED_WOOD_WALL.get(), ModBlocks.SUMMER_TREE_STRIPPED_LOG.get());

       //PLANKS
       blockWithItem(ModBlocks.SUMMER_TREE_PLANKS);
       stairsBlock((StairBlock) ModBlocks.SUMMER_TREE_PLANKS_STAIRS.get(), ModBlocks.SUMMER_TREE_PLANKS.get());
       slabBlock((SlabBlock) ModBlocks.SUMMER_TREE_PLANKS_SLAB.get(), ModBlocks.SUMMER_TREE_PLANKS.get());
       fenceBlock((FenceBlock) ModBlocks.SUMMER_TREE_PLANKS_FENCE.get(), ModBlocks.SUMMER_TREE_PLANKS.get());
       fenceGateBlock((FenceGateBlock) ModBlocks.SUMMER_TREE_PLANKS_FENCE_GATE.get(), ModBlocks.SUMMER_TREE_PLANKS.get());
       pressurePlateBlock(((PressurePlateBlock) ModBlocks.SUMMER_TREE_PLANKS_PRESSURE_PLATE.get()), ModBlocks.SUMMER_TREE_PLANKS.get());
       buttonBlock(((ButtonBlock) ModBlocks.SUMMER_TREE_PLANKS_BUTTON.get()), ModBlocks.SUMMER_TREE_PLANKS.get());

       //LEAVES AND SAPLING
       leavesBlock(ModBlocks.SUMMER_TREE_LEAVES_ORANGE);
       leavesBlock(ModBlocks.SUMMER_TREE_LEAVES_YELLOW);
       saplingBlock(ModBlocks.SUMMER_TREE_SAPLING);

       // ITEMS
       blockItem(ModBlocks.SUMMER_TREE_LOG);
       blockItem(ModBlocks.SUMMER_TREE_WOOD);
       blockItem(ModBlocks.SUMMER_TREE_WOOD_STAIRS);
       blockItem(ModBlocks.SUMMER_TREE_WOOD_SLAB);
       wallItem(ModBlocks.SUMMER_TREE_WOOD_WALL, ModBlocks.SUMMER_TREE_LOG);
       blockItem(ModBlocks.SUMMER_TREE_STRIPPED_LOG);
       blockItem(ModBlocks.SUMMER_TREE_STRIPPED_WOOD);
       blockItem(ModBlocks.SUMMER_TREE_STRIPPED_WOOD_STAIRS);
       blockItem(ModBlocks.SUMMER_TREE_STRIPPED_WOOD_SLAB);
       wallItem(ModBlocks.SUMMER_TREE_STRIPPED_WOOD_WALL, ModBlocks.SUMMER_TREE_STRIPPED_LOG);
       blockItem(ModBlocks.SUMMER_TREE_PLANKS_SLAB);
       blockItem(ModBlocks.SUMMER_TREE_PLANKS_STAIRS);
       fenceItem(ModBlocks.SUMMER_TREE_PLANKS_FENCE, ModBlocks.SUMMER_TREE_PLANKS);
       blockItem(ModBlocks.SUMMER_TREE_PLANKS_FENCE_GATE);
       blockItem(ModBlocks.SUMMER_TREE_PLANKS_PRESSURE_PLATE);
       blockItem(ModBlocks.SUMMER_TREE_PLANKS_BUTTON);
   }

   private void registerAutumnTreeBlocks(){
       //LOG
       logBlock(((RotatedPillarBlock) ModBlocks.AUTUMN_TREE_LOG.get()));

       //CRACKED_LOG BLOCK AND ITEM MANUAL
       //DOOR BLOCK AND ITEM MANUAL
       //TRAPDOOR BLOCK AND ITEM MANUAL

       //WOOD
       axisBlock((RotatedPillarBlock) ModBlocks.AUTUMN_TREE_WOOD.get(), ModBlocks.AUTUMN_TREE_LOG.get());
       stairsBlock((StairBlock) ModBlocks.AUTUMN_TREE_WOOD_STAIRS.get(), ModBlocks.AUTUMN_TREE_LOG.get());
       slabBlock((SlabBlock) ModBlocks.AUTUMN_TREE_WOOD_SLAB.get(), ModBlocks.AUTUMN_TREE_WOOD.get());
       wallBlock((WallBlock) ModBlocks.AUTUMN_TREE_WOOD_WALL.get(), ModBlocks.AUTUMN_TREE_LOG.get());

       // STRIPPED LOG
       logBlock(((RotatedPillarBlock) ModBlocks.AUTUMN_TREE_STRIPPED_LOG.get()));

       //STRIPPED WOOD
       axisBlock((RotatedPillarBlock) ModBlocks.AUTUMN_TREE_STRIPPED_WOOD.get(), ModBlocks.AUTUMN_TREE_STRIPPED_LOG.get());
       stairsBlock((StairBlock) ModBlocks.AUTUMN_TREE_STRIPPED_WOOD_STAIRS.get(), ModBlocks.AUTUMN_TREE_STRIPPED_LOG.get());
       slabBlock((SlabBlock) ModBlocks.AUTUMN_TREE_STRIPPED_WOOD_SLAB.get(), ModBlocks.AUTUMN_TREE_STRIPPED_WOOD.get());
       wallBlock((WallBlock) ModBlocks.AUTUMN_TREE_STRIPPED_WOOD_WALL.get(), ModBlocks.AUTUMN_TREE_STRIPPED_LOG.get());

       //PLANKS
       blockWithItem(ModBlocks.AUTUMN_TREE_PLANKS);
       stairsBlock((StairBlock) ModBlocks.AUTUMN_TREE_PLANKS_STAIRS.get(), ModBlocks.AUTUMN_TREE_PLANKS.get());
       slabBlock((SlabBlock) ModBlocks.AUTUMN_TREE_PLANKS_SLAB.get(), ModBlocks.AUTUMN_TREE_PLANKS.get());
       fenceBlock((FenceBlock) ModBlocks.AUTUMN_TREE_PLANKS_FENCE.get(), ModBlocks.AUTUMN_TREE_PLANKS.get());
       fenceGateBlock((FenceGateBlock) ModBlocks.AUTUMN_TREE_PLANKS_FENCE_GATE.get(), ModBlocks.AUTUMN_TREE_PLANKS.get());
       pressurePlateBlock(((PressurePlateBlock) ModBlocks.AUTUMN_TREE_PLANKS_PRESSURE_PLATE.get()), ModBlocks.AUTUMN_TREE_PLANKS.get());
       buttonBlock(((ButtonBlock) ModBlocks.AUTUMN_TREE_PLANKS_BUTTON.get()), ModBlocks.AUTUMN_TREE_PLANKS.get());

       //LEAVES AND SAPLING
       leavesBlock(ModBlocks.AUTUMN_TREE_LEAVES_BROWN);
       leavesBlock(ModBlocks.AUTUMN_TREE_LEAVES_RED);
       leavesBlock(ModBlocks.AUTUMN_TREE_LEAVES_LIGHT_GRAY);
       leavesBlock(ModBlocks.AUTUMN_TREE_LEAVES_DARK_GRAY);
       saplingBlock(ModBlocks.AUTUMN_TREE_SAPLING);

       // ITEMS
       blockItem(ModBlocks.AUTUMN_TREE_LOG);
       blockItem(ModBlocks.AUTUMN_TREE_WOOD);
       blockItem(ModBlocks.AUTUMN_TREE_WOOD_STAIRS);
       blockItem(ModBlocks.AUTUMN_TREE_WOOD_SLAB);
       wallItem(ModBlocks.AUTUMN_TREE_WOOD_WALL, ModBlocks.AUTUMN_TREE_LOG);
       blockItem(ModBlocks.AUTUMN_TREE_STRIPPED_LOG);
       blockItem(ModBlocks.AUTUMN_TREE_STRIPPED_WOOD);
       blockItem(ModBlocks.AUTUMN_TREE_STRIPPED_WOOD_STAIRS);
       blockItem(ModBlocks.AUTUMN_TREE_STRIPPED_WOOD_SLAB);
       wallItem(ModBlocks.AUTUMN_TREE_STRIPPED_WOOD_WALL, ModBlocks.AUTUMN_TREE_STRIPPED_LOG);
       blockItem(ModBlocks.AUTUMN_TREE_PLANKS_SLAB);
       blockItem(ModBlocks.AUTUMN_TREE_PLANKS_STAIRS);
       fenceItem(ModBlocks.AUTUMN_TREE_PLANKS_FENCE, ModBlocks.AUTUMN_TREE_PLANKS);
       blockItem(ModBlocks.AUTUMN_TREE_PLANKS_FENCE_GATE);
       blockItem(ModBlocks.AUTUMN_TREE_PLANKS_PRESSURE_PLATE);
       blockItem(ModBlocks.AUTUMN_TREE_PLANKS_BUTTON);

   }

   private void registerWinterTreeBlocks(){
       //LOG
       logBlock(((RotatedPillarBlock) ModBlocks.WINTER_TREE_LOG.get()));

       //CRACKED_LOG BLOCK AND ITEM MANUAL
       //DOOR BLOCK AND ITEM MANUAL
       //TRAPDOOR BLOCK AND ITEM MANUAL

       //WOOD
       axisBlock((RotatedPillarBlock) ModBlocks.WINTER_TREE_WOOD.get(), ModBlocks.WINTER_TREE_LOG.get());
       stairsBlock((StairBlock) ModBlocks.WINTER_TREE_WOOD_STAIRS.get(), ModBlocks.WINTER_TREE_LOG.get());
       slabBlock((SlabBlock) ModBlocks.WINTER_TREE_WOOD_SLAB.get(), ModBlocks.WINTER_TREE_WOOD.get());
       wallBlock((WallBlock) ModBlocks.WINTER_TREE_WOOD_WALL.get(), ModBlocks.WINTER_TREE_LOG.get());

       // STRIPPED LOG
       logBlock(((RotatedPillarBlock) ModBlocks.WINTER_TREE_STRIPPED_LOG.get()));

       //STRIPPED WOOD
       axisBlock((RotatedPillarBlock) ModBlocks.WINTER_TREE_STRIPPED_WOOD.get(), ModBlocks.WINTER_TREE_STRIPPED_LOG.get());
       stairsBlock((StairBlock) ModBlocks.WINTER_TREE_STRIPPED_WOOD_STAIRS.get(), ModBlocks.WINTER_TREE_STRIPPED_LOG.get());
       slabBlock((SlabBlock) ModBlocks.WINTER_TREE_STRIPPED_WOOD_SLAB.get(), ModBlocks.WINTER_TREE_STRIPPED_WOOD.get());
       wallBlock((WallBlock) ModBlocks.WINTER_TREE_STRIPPED_WOOD_WALL.get(), ModBlocks.WINTER_TREE_STRIPPED_LOG.get());

       //PLANKS
       blockWithItem(ModBlocks.WINTER_TREE_PLANKS);
       stairsBlock((StairBlock) ModBlocks.WINTER_TREE_PLANKS_STAIRS.get(), ModBlocks.WINTER_TREE_PLANKS.get());
       slabBlock((SlabBlock) ModBlocks.WINTER_TREE_PLANKS_SLAB.get(), ModBlocks.WINTER_TREE_PLANKS.get());
       fenceBlock((FenceBlock) ModBlocks.WINTER_TREE_PLANKS_FENCE.get(), ModBlocks.WINTER_TREE_PLANKS.get());
       fenceGateBlock((FenceGateBlock) ModBlocks.WINTER_TREE_PLANKS_FENCE_GATE.get(), ModBlocks.WINTER_TREE_PLANKS.get());
       pressurePlateBlock(((PressurePlateBlock) ModBlocks.WINTER_TREE_PLANKS_PRESSURE_PLATE.get()), ModBlocks.WINTER_TREE_PLANKS.get());
       buttonBlock(((ButtonBlock) ModBlocks.WINTER_TREE_PLANKS_BUTTON.get()), ModBlocks.WINTER_TREE_PLANKS.get());

       //LEAVES AND SAPLING
       leavesBlock(ModBlocks.WINTER_TREE_LEAVES_LIGHT_BLUE);
       leavesBlock(ModBlocks.WINTER_TREE_LEAVES_BLUE);
       saplingBlock(ModBlocks.WINTER_TREE_SAPLING);

       // ITEMS
       blockItem(ModBlocks.WINTER_TREE_LOG);
       blockItem(ModBlocks.WINTER_TREE_WOOD);
       blockItem(ModBlocks.WINTER_TREE_WOOD_STAIRS);
       blockItem(ModBlocks.WINTER_TREE_WOOD_SLAB);
       wallItem(ModBlocks.WINTER_TREE_WOOD_WALL, ModBlocks.WINTER_TREE_LOG);
       blockItem(ModBlocks.WINTER_TREE_STRIPPED_LOG);
       blockItem(ModBlocks.WINTER_TREE_STRIPPED_WOOD);
       blockItem(ModBlocks.WINTER_TREE_STRIPPED_WOOD_STAIRS);
       blockItem(ModBlocks.WINTER_TREE_STRIPPED_WOOD_SLAB);
       wallItem(ModBlocks.WINTER_TREE_STRIPPED_WOOD_WALL, ModBlocks.WINTER_TREE_STRIPPED_LOG);
       blockItem(ModBlocks.WINTER_TREE_PLANKS_SLAB);
       blockItem(ModBlocks.WINTER_TREE_PLANKS_STAIRS);
       fenceItem(ModBlocks.WINTER_TREE_PLANKS_FENCE, ModBlocks.WINTER_TREE_PLANKS);
       blockItem(ModBlocks.WINTER_TREE_PLANKS_FENCE_GATE);
       blockItem(ModBlocks.WINTER_TREE_PLANKS_PRESSURE_PLATE);
       blockItem(ModBlocks.WINTER_TREE_PLANKS_BUTTON);
   }



    private void leavesBlock(DeferredBlock<@NotNull Block> deferredBlock) {
        Block block = deferredBlock.get();
        TextureMapping mapping = TextureMapping.cube(block);

        Identifier model = ModelTemplates.LEAVES.create(block, mapping, blockModels.modelOutput);

        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, BlockModelGenerators.plainVariant(model)));

        blockModels.registerSimpleItemModel(block, model);
    }


    private void blockItem(DeferredBlock<@NotNull Block> deferredBlock) {
        blockModels.registerSimpleItemModel(deferredBlock.get(), Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "block/" + deferredBlock.getId().getPath()));
    }

    private void blockItem(DeferredBlock<@NotNull Block> deferredBlock, String appendix) {
        blockModels.registerSimpleItemModel(deferredBlock.get(), Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "block/" + deferredBlock.getId().getPath() + appendix));
    }
    private void saplingBlock(DeferredBlock<@NotNull Block> deferredBlock) {
        blockModels.createCrossBlock(deferredBlock.get(), BlockModelGenerators.PlantType.NOT_TINTED);
    }

    private void blockWithItem(DeferredBlock<@NotNull Block> deferredBlock) {
        blockModels.createTrivialCube(deferredBlock.get());
    }

    private void logBlock(RotatedPillarBlock block) {
        blockModels.woodProvider(block).log(block);
    }

    private void woodBlock(RotatedPillarBlock block) {
        blockModels.woodProvider(block).wood(block);
    }

    private void stairsBlock(StairBlock stairs, Block baseBlock) {
        blockModels.familyWithExistingFullBlock(baseBlock).stairs(stairs);
    }

    private void slabBlock(SlabBlock slab, Block baseBlock) {
        blockModels.familyWithExistingFullBlock(baseBlock).slab(slab);
    }

    private void wallBlock(WallBlock wall, Block baseBlock) {
        blockModels.family(baseBlock).wall(wall);
    }

    private void fenceBlock(FenceBlock fence, Block baseBlock) {
        blockModels.family(baseBlock).fence(fence);
    }

    private void fenceGateBlock(FenceGateBlock fenceGate, Block baseBlock) {
        blockModels.familyWithExistingFullBlock(baseBlock).fenceGate(fenceGate);
    }

    private void wallItem(DeferredBlock<@NotNull Block> wall, DeferredBlock<@NotNull Block> baseBlock) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.WALL, TextureMapping.getBlockTexture(baseBlock.get()));

        Identifier model = ModelTemplates.WALL_INVENTORY.create(wall.get(), mapping, blockModels.modelOutput);

        blockModels.registerSimpleItemModel(wall.get(), model);
    }

    private void fenceItem(DeferredBlock<@NotNull Block> fence, DeferredBlock<@NotNull Block> baseBlock) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.TEXTURE, TextureMapping.getBlockTexture(baseBlock.get()));

        Identifier model = ModelTemplates.FENCE_INVENTORY.create(fence.get(), mapping, blockModels.modelOutput);

        blockModels.registerSimpleItemModel(fence.get(), model);
    }

    private void buttonBlock(ButtonBlock button, Block baseBlock) {
        blockModels.family(baseBlock).button(button);
    }

    private void pressurePlateBlock(PressurePlateBlock pressurePlate, Block baseBlock) {
        blockModels.family(baseBlock).pressurePlate(pressurePlate);
    }

    private void axisBlock(RotatedPillarBlock block, Block sideBlock, Identifier endTexture) {
        TextureMapping mapping = TextureMapping.column(TextureMapping.getBlockTexture(sideBlock), new Material(endTexture));

        Identifier model = ModelTemplates.CUBE_COLUMN.create(block, mapping, blockModels.modelOutput);

        blockModels.createAxisAlignedPillarBlockCustomModel(block, BlockModelGenerators.plainVariant(model));
    }

    private void axisBlock(RotatedPillarBlock block, Block log) {
        TextureMapping mapping = TextureMapping.logColumn(log);

        Identifier model = ModelTemplates.CUBE_COLUMN.create(block, mapping, blockModels.modelOutput);

        blockModels.createAxisAlignedPillarBlockCustomModel(block, BlockModelGenerators.plainVariant(model));
    }

    private void basicItem(Item item) {
        itemModels.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
    }

    private void saplingItem(DeferredBlock<@NotNull Block> block) {
        Identifier model = ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(block.get()), TextureMapping.layer0(TextureMapping.getBlockTexture(block.get())), itemModels.modelOutput);

        itemModels.itemModelOutput.accept(block.get().asItem(), ItemModelUtils.plainModel(model));
    }

}