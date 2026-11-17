package com.saphienyako.feywild.datagen;


import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.block.ModBlocks;
import com.saphienyako.feywild.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public class ModModelProvider extends ModelProvider {

    private static final Set<Identifier> MANUAL_BLOCK_STATES = Set.of(
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "mandrake_crop"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "giant_sun_flower"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "giant_crocus_flower"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "giant_dandelion_flower"),

            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "fey_altar"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "fey_altar_queen_titania"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "fey_altar_queen_mab"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "fey_altar_ashen_lord"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "fey_altar_oberon"),

            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "orange_mushroom"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "yellow_mushroom"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "green_mushroom"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "light_blue_mushroom"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "blue_mushroom"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "purple_mushroom"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "pink_mushroom"),

            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "orange_mushroom_block"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "yellow_mushroom_block"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "green_mushroom_block"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "light_blue_mushroom_block"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "blue_mushroom_block"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "purple_mushroom_block"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "pink_mushroom_block"),

            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "elven_quartz_mossy_brick"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "spring_elven_quartz_mossy_brick"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "summer_elven_quartz_mossy_brick"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "autumn_elven_quartz_mossy_brick"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "winter_elven_quartz_mossy_brick"),

            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "autumn_tree_cracked_log"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "autumn_tree_planks_door"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "autumn_tree_planks_trapdoor"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "spring_tree_cracked_log"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "spring_tree_planks_door"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "spring_tree_planks_trapdoor"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "summer_tree_cracked_log"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "summer_tree_planks_door"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "summer_tree_planks_trapdoor"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "winter_tree_cracked_log"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "winter_tree_planks_door"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "winter_tree_planks_trapdoor")
    );

    private static final Set<Identifier> MANUAL_ITEM_MODELS = Set.of(
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "autumn_tree_cracked_log"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "autumn_tree_planks_door"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "autumn_tree_planks_trapdoor"),

            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "spring_tree_cracked_log"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "spring_tree_planks_door"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "spring_tree_planks_trapdoor"),

            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "summer_tree_cracked_log"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "summer_tree_planks_door"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "summer_tree_planks_trapdoor"),

            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "winter_tree_cracked_log"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "winter_tree_planks_door"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "winter_tree_planks_trapdoor"),

            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "blue_mushroom"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "blue_mushroom_block"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "green_mushroom"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "green_mushroom_block"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "light_blue_mushroom"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "light_blue_mushroom_block"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "orange_mushroom"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "orange_mushroom_block"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "pink_mushroom"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "pink_mushroom_block"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "purple_mushroom"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "purple_mushroom_block"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "yellow_mushroom"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "yellow_mushroom_block"),

            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "fey_altar"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "fey_altar_ashen_lord"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "fey_altar_oberon"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "fey_altar_queen_mab"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "fey_altar_queen_titania"),

            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "mandrake_crop"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "giant_sun_flower"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "giant_crocus_flower"),
            Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "giant_dandelion_flower")
    );

    private BlockModelGenerators blockModels;
    private ItemModelGenerators itemModels;

    public ModModelProvider(PackOutput output) {
        super(output, Feywild.MOD_ID);
    }

    @Override
    protected @NotNull Stream<? extends Holder<Block>> getKnownBlocks() {
        return super.getKnownBlocks().filter(holder -> !MANUAL_BLOCK_STATES.contains(Objects.requireNonNull(holder.getKey()).identifier()));
    }

    @Override
    protected @NotNull Stream<? extends Holder<Item>> getKnownItems() {
        return super.getKnownItems().filter(holder -> !MANUAL_ITEM_MODELS.contains(Objects.requireNonNull(holder.getKey()).identifier()));
    }

    @Override
    protected void registerModels(@NotNull BlockModelGenerators blockModels, @NotNull ItemModelGenerators itemModels) {
        this.blockModels = blockModels;
        this.itemModels = itemModels;

        registerBlockStates();
        registerBasicItems();
        registerSpawnEggs(itemModels);
    }

    private void registerBlockStates() {
        blockWithItem(ModBlocks.FEY_GEM_ORE);
        blockWithItem(ModBlocks.FEY_GEM_ORE_DEEP_SLATE);

        registerElvenQuartz();
        registerTreeBlocks();
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

        blockWithItem(ModBlocks.ELVEN_QUARTZ_BRICK);
        stairsBlock(((StairBlock) ModBlocks.ELVEN_QUARTZ_BRICK_STAIRS.get()), ModBlocks.ELVEN_QUARTZ_BRICK.get());
        slabBlock(((SlabBlock) ModBlocks.ELVEN_QUARTZ_BRICK_SLAB.get()), ModBlocks.ELVEN_QUARTZ_BRICK.get());

        mossyBrickItem(ModBlocks.ELVEN_QUARTZ_MOSSY_BRICK);

        blockWithItem(ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK);
        stairsBlock(((StairBlock) ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get()), ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK.get());
        slabBlock(((SlabBlock) ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get()),ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK.get());

        axisBlock((RotatedPillarBlock) ModBlocks.ELVEN_QUARTZ_PILLAR.get(), ModBlocks.ELVEN_QUARTZ_PILLAR.get(), Identifier.fromNamespaceAndPath(Feywild.MOD_ID,"block/elven_quartz_pillar_top"));
        blockItem(ModBlocks.ELVEN_QUARTZ_PILLAR);

        blockWithItem(ModBlocks.ELVEN_QUARTZ_POLISHED);
        stairsBlock(((StairBlock) ModBlocks.ELVEN_QUARTZ_POLISHED_STAIRS.get()), ModBlocks.ELVEN_QUARTZ_POLISHED.get());
        slabBlock(((SlabBlock) ModBlocks.ELVEN_QUARTZ_POLISHED_SLAB.get()), ModBlocks.ELVEN_QUARTZ_POLISHED.get());

    }

    private void registerSpringElvenQuartz(){
        blockWithItem(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK);
        stairsBlock(((StairBlock) ModBlocks.SPRING_ELVEN_QUARTZ_STAIRS.get()), ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get());
        slabBlock(((SlabBlock) ModBlocks.SPRING_ELVEN_QUARTZ_SLAB.get()), ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get());

        blockWithItem(ModBlocks.SPRING_ELVEN_QUARTZ_BRICK);
        stairsBlock(((StairBlock) ModBlocks.SPRING_ELVEN_QUARTZ_BRICK_STAIRS.get()), ModBlocks.SPRING_ELVEN_QUARTZ_BRICK.get());
        slabBlock(((SlabBlock) ModBlocks.SPRING_ELVEN_QUARTZ_BRICK_SLAB.get()), ModBlocks.SPRING_ELVEN_QUARTZ_BRICK.get());

        mossyBrickItem(ModBlocks.SPRING_ELVEN_QUARTZ_MOSSY_BRICK);

        blockWithItem(ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK);
        stairsBlock(((StairBlock) ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get()), ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK.get());
        slabBlock(((SlabBlock) ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get()), ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK.get());

        axisBlock((RotatedPillarBlock) ModBlocks.SPRING_ELVEN_QUARTZ_PILLAR.get(), ModBlocks.SPRING_ELVEN_QUARTZ_PILLAR.get(),Identifier.fromNamespaceAndPath(Feywild.MOD_ID,"block/spring_elven_quartz_pillar_top"));
        blockItem(ModBlocks.SPRING_ELVEN_QUARTZ_PILLAR);

        blockWithItem(ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED);
        stairsBlock(((StairBlock) ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED_STAIRS.get()), ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED.get());
        slabBlock(((SlabBlock) ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED_SLAB.get()), ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED.get());

    }

    private void registerSummerElvenQuartz(){
        blockWithItem(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK);
        stairsBlock(((StairBlock) ModBlocks.SUMMER_ELVEN_QUARTZ_STAIRS.get()), ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get());
        slabBlock(((SlabBlock) ModBlocks.SUMMER_ELVEN_QUARTZ_SLAB.get()), ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get());

        blockWithItem(ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK);
        stairsBlock(((StairBlock) ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK_STAIRS.get()), ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK.get());
        slabBlock(((SlabBlock) ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK_SLAB.get()), ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK.get());

        mossyBrickItem(ModBlocks.SUMMER_ELVEN_QUARTZ_MOSSY_BRICK);

        blockWithItem(ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK);
        stairsBlock(((StairBlock) ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get()), ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK.get());
        slabBlock(((SlabBlock) ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get()), ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK.get());

        axisBlock((RotatedPillarBlock) ModBlocks.SUMMER_ELVEN_QUARTZ_PILLAR.get(), ModBlocks.SUMMER_ELVEN_QUARTZ_PILLAR.get(),Identifier.fromNamespaceAndPath(Feywild.MOD_ID,"block/summer_elven_quartz_pillar_top"));
        blockItem(ModBlocks.SUMMER_ELVEN_QUARTZ_PILLAR);

        blockWithItem(ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED);
        stairsBlock(((StairBlock) ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED_STAIRS.get()), ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED.get());
        slabBlock(((SlabBlock) ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED_SLAB.get()), ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED.get());

    }

    private void registerAutumnElvenQuartz(){
        blockWithItem(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK);
        stairsBlock(((StairBlock) ModBlocks.AUTUMN_ELVEN_QUARTZ_STAIRS.get()), ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get());
        slabBlock(((SlabBlock) ModBlocks.AUTUMN_ELVEN_QUARTZ_SLAB.get()), ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get());

        blockWithItem(ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK);
        stairsBlock(((StairBlock) ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK_STAIRS.get()), ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK.get());
        slabBlock(((SlabBlock) ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK_SLAB.get()), ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK.get());

        mossyBrickItem(ModBlocks.AUTUMN_ELVEN_QUARTZ_MOSSY_BRICK);

        blockWithItem(ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK);
        stairsBlock(((StairBlock) ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get()), ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK.get());
        slabBlock(((SlabBlock) ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get()), ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK.get());

        axisBlock((RotatedPillarBlock) ModBlocks.AUTUMN_ELVEN_QUARTZ_PILLAR.get(), ModBlocks.AUTUMN_ELVEN_QUARTZ_PILLAR.get(),Identifier.fromNamespaceAndPath(Feywild.MOD_ID,"block/autumn_elven_quartz_pillar_top"));
        blockItem(ModBlocks.AUTUMN_ELVEN_QUARTZ_PILLAR);

        blockWithItem(ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED);
        stairsBlock(((StairBlock) ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED_STAIRS.get()), ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED.get());
        slabBlock(((SlabBlock) ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED_SLAB.get()), ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED.get());
    }

    private void registerWinterElvenQuartz(){
        blockWithItem(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK);
        stairsBlock(((StairBlock) ModBlocks.WINTER_ELVEN_QUARTZ_STAIRS.get()), ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get());
        slabBlock(((SlabBlock) ModBlocks.WINTER_ELVEN_QUARTZ_SLAB.get()), ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get());

        blockWithItem(ModBlocks.WINTER_ELVEN_QUARTZ_BRICK);
        stairsBlock(((StairBlock) ModBlocks.WINTER_ELVEN_QUARTZ_BRICK_STAIRS.get()), ModBlocks.WINTER_ELVEN_QUARTZ_BRICK.get());
        slabBlock(((SlabBlock) ModBlocks.WINTER_ELVEN_QUARTZ_BRICK_SLAB.get()), ModBlocks.WINTER_ELVEN_QUARTZ_BRICK.get());

        mossyBrickItem(ModBlocks.WINTER_ELVEN_QUARTZ_MOSSY_BRICK);

        blockWithItem(ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK);
        stairsBlock(((StairBlock) ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get()), ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK.get());
        slabBlock(((SlabBlock) ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get()), ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK.get());


        axisBlock((RotatedPillarBlock) ModBlocks.WINTER_ELVEN_QUARTZ_PILLAR.get(), ModBlocks.WINTER_ELVEN_QUARTZ_PILLAR.get(),Identifier.fromNamespaceAndPath(Feywild.MOD_ID,"block/winter_elven_quartz_pillar_top"));
        blockItem(ModBlocks.WINTER_ELVEN_QUARTZ_PILLAR);

        blockWithItem(ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED);
        stairsBlock(((StairBlock) ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED_STAIRS.get()), ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED.get());
        slabBlock(((SlabBlock) ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED_SLAB.get()), ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED.get());

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
        blockItem(ModBlocks.SPRING_TREE_WOOD);
        blockItem(ModBlocks.SPRING_TREE_STRIPPED_WOOD);
        blockItem(ModBlocks.SPRING_TREE_PLANKS_FENCE_GATE);
        blockItem(ModBlocks.SPRING_TREE_PLANKS_PRESSURE_PLATE);
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
       blockItem(ModBlocks.SUMMER_TREE_WOOD);
       blockItem(ModBlocks.SUMMER_TREE_STRIPPED_WOOD);
       blockItem(ModBlocks.SUMMER_TREE_PLANKS_FENCE_GATE);
       blockItem(ModBlocks.SUMMER_TREE_PLANKS_PRESSURE_PLATE);
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
       blockItem(ModBlocks.AUTUMN_TREE_WOOD);
       blockItem(ModBlocks.AUTUMN_TREE_STRIPPED_WOOD);
       blockItem(ModBlocks.AUTUMN_TREE_PLANKS_FENCE_GATE);
       blockItem(ModBlocks.AUTUMN_TREE_PLANKS_PRESSURE_PLATE);

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
       blockItem(ModBlocks.WINTER_TREE_WOOD);
       blockItem(ModBlocks.WINTER_TREE_STRIPPED_WOOD);
       blockItem(ModBlocks.WINTER_TREE_PLANKS_FENCE_GATE);
       blockItem(ModBlocks.WINTER_TREE_PLANKS_PRESSURE_PLATE);
   }

    private void registerSpawnEggs(ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ModItems.SPAWN_EGG_SPRING_PIXIE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SPAWN_EGG_AUTUMN_PIXIE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SPAWN_EGG_SUMMER_PIXIE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SPAWN_EGG_WINTER_PIXIE.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.SPAWN_EGG_SHROOMLING.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SPAWN_EGG_MANDRAGORA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SPAWN_EGG_BELLSNICKEL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SPAWN_EGG_BEE_KNIGHT.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.SPAWN_EGG_SPRING_TREE_ENT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SPAWN_EGG_SUMMER_TREE_ENT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SPAWN_EGG_AUTUMN_TREE_ENT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SPAWN_EGG_WINTER_TREE_ENT.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.SPAWN_EGG_QUEEN_TITANIA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SPAWN_EGG_QUEEN_MAB.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SPAWN_EGG_OBERON.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SPAWN_EGG_ASHEN_LORD.get(), ModelTemplates.FLAT_ITEM);
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
        TextureMapping mapping = TextureMapping.columnWithWall(baseBlock);

        var post = BlockModelGenerators.plainVariant(ModelTemplates.WALL_POST.create(wall, mapping, blockModels.modelOutput));
        var low = BlockModelGenerators.plainVariant(ModelTemplates.WALL_LOW_SIDE.create(wall, mapping, blockModels.modelOutput));
        var high = BlockModelGenerators.plainVariant(ModelTemplates.WALL_TALL_SIDE.create(wall, mapping, blockModels.modelOutput));

        blockModels.blockStateOutput.accept(BlockModelGenerators.createWall(wall, post, low, high));

        Identifier inventory = ModelTemplates.WALL_INVENTORY.create(wall, mapping, blockModels.modelOutput);
        blockModels.registerSimpleItemModel(wall, inventory);
    }

    private void fenceBlock(FenceBlock fence, Block baseBlock) {
        blockModels.familyWithExistingFullBlock(baseBlock).fence(fence);
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
        blockModels.familyWithExistingFullBlock(baseBlock).button(button);
    }
    private void pressurePlateBlock(PressurePlateBlock pressurePlate, Block baseBlock) {
        blockModels.familyWithExistingFullBlock(baseBlock).pressurePlate(pressurePlate);
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

    private void mossyBrickItem(DeferredBlock<@NotNull Block> block) {
        Item item = block.get().asItem();
        Identifier model = ModelLocationUtils.getModelLocation(block.get());

        itemModels.itemModelOutput.accept(item, ItemModelUtils.tintedModel(model,
                ItemModelUtils.constantTint(0xFFFFFF), // 0: base brick
                ItemModelUtils.constantTint(0x91BD59), // 1: default
                ItemModelUtils.constantTint(0xA3E48C), // 2: spring
                ItemModelUtils.constantTint(0xFFD35B), // 3: summer
                ItemModelUtils.constantTint(0xA0D8FF), // 4: winter
                ItemModelUtils.constantTint(0xD88C3F)  // 5: autumn
        ));
    }

}