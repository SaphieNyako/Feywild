package com.saphienyako.feywild.block;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.block.trees.*;
import com.saphienyako.feywild.item.ModItems;
import com.saphienyako.feywild.worldgen.ModConfiguredFeatures;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(Feywild.MOD_ID);

    public static final DeferredBlock<Block> FEY_GEM_ORE = registerBlock("fey_gem_ore",
            () -> new Block(BlockBehaviour.Properties.of().strength(4f).explosionResistance(10f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> FEY_GEM_ORE_DEEP_SLATE = registerBlock("fey_gem_ore_deep_slate",
            () -> new Block(BlockBehaviour.Properties.of().strength(4f).explosionResistance(10f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));

    public static final DeferredBlock<Block> MANDRAKE_CROP = BLOCKS.register("mandrake_crop",
            () -> new MandrakeCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noCollission().noOcclusion()));

    public static final DeferredBlock<Block> GIANT_SUN_FLOWER = registerBlock("giant_sun_flower",
            ()-> new SunFlowerBlock(4));

    public static final DeferredBlock<Block> GIANT_CROCUS_FLOWER = registerBlock("giant_crocus_flower",
            ()-> new CrocusFlowerBlock(3));

    public static final DeferredBlock<Block> GIANT_DANDELION_FLOWER = registerBlock("giant_dandelion_flower",
            ()-> new DandelionFlowerBlock(4));

    public static final DeferredBlock<Block> FEY_ALTAR = registerBlock("fey_altar",
            () -> new FeyAltarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(3f, 10f).requiresCorrectToolForDrops().sound(SoundType.STONE).noOcclusion()));

    //MUSHROOM BLOCKS
    public static final DeferredBlock<Block> ORANGE_MUSHROOM = registerBlock("orange_mushroom",
            () -> new MushroomBlock(ModConfiguredFeatures.ORANGE_MUSHROOM_KEY, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM).noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> YELLOW_MUSHROOM = registerBlock("yellow_mushroom",
            () -> new MushroomBlock(ModConfiguredFeatures.YELLOW_MUSHROOM_KEY, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM).noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> GREEN_MUSHROOM = registerBlock("green_mushroom",
            () -> new MushroomBlock(ModConfiguredFeatures.GREEN_MUSHROOM_KEY, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM).noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> LIGHT_BLUE_MUSHROOM = registerBlock("light_blue_mushroom",
            () -> new MushroomBlock(ModConfiguredFeatures.LIGHT_BLUE_MUSHROOM_KEY, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM).noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> BLUE_MUSHROOM = registerBlock("blue_mushroom",
            () -> new MushroomBlock(ModConfiguredFeatures.BLUE_MUSHROOM_KEY, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM).noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> PURPLE_MUSHROOM = registerBlock("purple_mushroom",
            () -> new MushroomBlock(ModConfiguredFeatures.PURPLE_MUSHROOM_KEY, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM).noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> PINK_MUSHROOM = registerBlock("pink_mushroom",
            () -> new MushroomBlock(ModConfiguredFeatures.PINK_MUSHROOM_KEY, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM).noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> ORANGE_MUSHROOM_BLOCK = registerBlock("orange_mushroom_block",
            () -> new HugeMushroomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM_BLOCK).instrument(NoteBlockInstrument.BASS)
                    .strength(0.2F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final DeferredBlock<Block> YELLOW_MUSHROOM_BLOCK = registerBlock("yellow_mushroom_block",
            () -> new HugeMushroomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM_BLOCK).instrument(NoteBlockInstrument.BASS)
                    .strength(0.2F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final DeferredBlock<Block> GREEN_MUSHROOM_BLOCK = registerBlock("green_mushroom_block",
            () -> new HugeMushroomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM_BLOCK).instrument(NoteBlockInstrument.BASS)
                    .strength(0.2F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final DeferredBlock<Block> LIGHT_BLUE_MUSHROOM_BLOCK = registerBlock("light_blue_mushroom_block",
            () -> new HugeMushroomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM_BLOCK).instrument(NoteBlockInstrument.BASS)
                    .strength(0.2F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final DeferredBlock<Block> BLUE_MUSHROOM_BLOCK = registerBlock("blue_mushroom_block",
            () -> new HugeMushroomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM_BLOCK).instrument(NoteBlockInstrument.BASS)
                    .strength(0.2F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final DeferredBlock<Block> PURPLE_MUSHROOM_BLOCK = registerBlock("purple_mushroom_block",
            () -> new HugeMushroomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM_BLOCK).instrument(NoteBlockInstrument.BASS)
                    .strength(0.2F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final DeferredBlock<Block> PINK_MUSHROOM_BLOCK = registerBlock("pink_mushroom_block",
            () -> new HugeMushroomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM_BLOCK).instrument(NoteBlockInstrument.BASS)
                    .strength(0.2F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    //DEFAULT ELVEN QUARTZ
    public static final DeferredBlock<Block> ELVEN_QUARTZ_BLOCK = registerBlock("elven_quartz_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> ELVEN_QUARTZ_STAIRS = registerBlock("elven_quartz_stairs",
            () -> new StairBlock(ModBlocks.ELVEN_QUARTZ_BLOCK.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> ELVEN_QUARTZ_SLAB = registerBlock("elven_quartz_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredBlock<Block> ELVEN_QUARTZ_BRICK = registerBlock("elven_quartz_brick",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> ELVEN_QUARTZ_BRICK_STAIRS = registerBlock("elven_quartz_brick_stairs",
            () -> new StairBlock(ModBlocks.ELVEN_QUARTZ_BRICK.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> ELVEN_QUARTZ_BRICK_SLAB = registerBlock("elven_quartz_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredBlock<Block> ELVEN_QUARTZ_MOSSY_BRICK = registerBlock("elven_quartz_mossy_brick",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredBlock<Block> ELVEN_QUARTZ_CRACKED_BRICK = registerBlock("elven_quartz_cracked_brick",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> ELVEN_QUARTZ_CRACKED_BRICK_STAIRS = registerBlock("elven_quartz_cracked_brick_stairs",
            () -> new StairBlock(ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> ELVEN_QUARTZ_CRACKED_BRICK_SLAB = registerBlock("elven_quartz_cracked_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredBlock<Block> ELVEN_QUARTZ_PILLAR = registerBlock("elven_quartz_pillar",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredBlock<Block> ELVEN_QUARTZ_POLISHED = registerBlock("elven_quartz_polished",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> ELVEN_QUARTZ_POLISHED_STAIRS = registerBlock("elven_quartz_polished_stairs",
            () -> new StairBlock(ModBlocks.ELVEN_QUARTZ_POLISHED.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> ELVEN_QUARTZ_POLISHED_SLAB = registerBlock("elven_quartz_polished_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    //SPRING ELVEN QUARTZ
    public static final DeferredBlock<Block> SPRING_ELVEN_QUARTZ_BLOCK = registerBlock("spring_elven_quartz_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> SPRING_ELVEN_QUARTZ_STAIRS = registerBlock("spring_elven_quartz_stairs",
            () -> new StairBlock(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> SPRING_ELVEN_QUARTZ_SLAB = registerBlock("spring_elven_quartz_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredBlock<Block> SPRING_ELVEN_QUARTZ_BRICK = registerBlock("spring_elven_quartz_brick",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> SPRING_ELVEN_QUARTZ_BRICK_STAIRS = registerBlock("spring_elven_quartz_brick_stairs",
            () -> new StairBlock(ModBlocks.SPRING_ELVEN_QUARTZ_BRICK.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> SPRING_ELVEN_QUARTZ_BRICK_SLAB = registerBlock("spring_elven_quartz_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredBlock<Block> SPRING_ELVEN_QUARTZ_MOSSY_BRICK = registerBlock("spring_elven_quartz_mossy_brick",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredBlock<Block> SPRING_ELVEN_QUARTZ_CRACKED_BRICK = registerBlock("spring_elven_quartz_cracked_brick",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> SPRING_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS = registerBlock("spring_elven_quartz_cracked_brick_stairs",
            () -> new StairBlock(ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> SPRING_ELVEN_QUARTZ_CRACKED_BRICK_SLAB = registerBlock("spring_elven_quartz_cracked_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredBlock<Block> SPRING_ELVEN_QUARTZ_PILLAR = registerBlock("spring_elven_quartz_pillar",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredBlock<Block> SPRING_ELVEN_QUARTZ_POLISHED = registerBlock("spring_elven_quartz_polished",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> SPRING_ELVEN_QUARTZ_POLISHED_STAIRS = registerBlock("spring_elven_quartz_polished_stairs",
            () -> new StairBlock(ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> SPRING_ELVEN_QUARTZ_POLISHED_SLAB = registerBlock("spring_elven_quartz_polished_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    //SUMMER ELVEN QUARTZ

    public static final DeferredBlock<Block> SUMMER_ELVEN_QUARTZ_BLOCK = registerBlock("summer_elven_quartz_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> SUMMER_ELVEN_QUARTZ_STAIRS = registerBlock("summer_elven_quartz_stairs",
            () -> new StairBlock(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> SUMMER_ELVEN_QUARTZ_SLAB = registerBlock("summer_elven_quartz_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredBlock<Block> SUMMER_ELVEN_QUARTZ_BRICK = registerBlock("summer_elven_quartz_brick",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> SUMMER_ELVEN_QUARTZ_BRICK_STAIRS = registerBlock("summer_elven_quartz_brick_stairs",
            () -> new StairBlock(ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> SUMMER_ELVEN_QUARTZ_BRICK_SLAB = registerBlock("summer_elven_quartz_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredBlock<Block> SUMMER_ELVEN_QUARTZ_MOSSY_BRICK = registerBlock("summer_elven_quartz_mossy_brick",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredBlock<Block> SUMMER_ELVEN_QUARTZ_CRACKED_BRICK = registerBlock("summer_elven_quartz_cracked_brick",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> SUMMER_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS = registerBlock("summer_elven_quartz_cracked_brick_stairs",
            () -> new StairBlock(ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> SUMMER_ELVEN_QUARTZ_CRACKED_BRICK_SLAB = registerBlock("summer_elven_quartz_cracked_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredBlock<Block> SUMMER_ELVEN_QUARTZ_PILLAR = registerBlock("summer_elven_quartz_pillar",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredBlock<Block> SUMMER_ELVEN_QUARTZ_POLISHED = registerBlock("summer_elven_quartz_polished",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> SUMMER_ELVEN_QUARTZ_POLISHED_STAIRS = registerBlock("summer_elven_quartz_polished_stairs",
            () -> new StairBlock(ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> SUMMER_ELVEN_QUARTZ_POLISHED_SLAB = registerBlock("summer_elven_quartz_polished_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    //WINTER ELVEN QUARTZ

    public static final DeferredBlock<Block> WINTER_ELVEN_QUARTZ_BLOCK = registerBlock("winter_elven_quartz_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> WINTER_ELVEN_QUARTZ_STAIRS = registerBlock("winter_elven_quartz_stairs",
            () -> new StairBlock(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> WINTER_ELVEN_QUARTZ_SLAB = registerBlock("winter_elven_quartz_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredBlock<Block> WINTER_ELVEN_QUARTZ_BRICK = registerBlock("winter_elven_quartz_brick",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> WINTER_ELVEN_QUARTZ_BRICK_STAIRS = registerBlock("winter_elven_quartz_brick_stairs",
            () -> new StairBlock(ModBlocks.WINTER_ELVEN_QUARTZ_BRICK.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> WINTER_ELVEN_QUARTZ_BRICK_SLAB = registerBlock("winter_elven_quartz_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredBlock<Block> WINTER_ELVEN_QUARTZ_MOSSY_BRICK = registerBlock("winter_elven_quartz_mossy_brick",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredBlock<Block> WINTER_ELVEN_QUARTZ_CRACKED_BRICK = registerBlock("winter_elven_quartz_cracked_brick",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> WINTER_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS = registerBlock("winter_elven_quartz_cracked_brick_stairs",
            () -> new StairBlock(ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> WINTER_ELVEN_QUARTZ_CRACKED_BRICK_SLAB = registerBlock("winter_elven_quartz_cracked_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredBlock<Block> WINTER_ELVEN_QUARTZ_PILLAR = registerBlock("winter_elven_quartz_pillar",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredBlock<Block> WINTER_ELVEN_QUARTZ_POLISHED = registerBlock("winter_elven_quartz_polished",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> WINTER_ELVEN_QUARTZ_POLISHED_STAIRS = registerBlock("winter_elven_quartz_polished_stairs",
            () -> new StairBlock(ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> WINTER_ELVEN_QUARTZ_POLISHED_SLAB = registerBlock("winter_elven_quartz_polished_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));



    //AUTUMN ELVEN QUARTZ
    public static final DeferredBlock<Block> AUTUMN_ELVEN_QUARTZ_BLOCK = registerBlock("autumn_elven_quartz_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> AUTUMN_ELVEN_QUARTZ_STAIRS = registerBlock("autumn_elven_quartz_stairs",
            () -> new StairBlock(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> AUTUMN_ELVEN_QUARTZ_SLAB = registerBlock("autumn_elven_quartz_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredBlock<Block> AUTUMN_ELVEN_QUARTZ_BRICK = registerBlock("autumn_elven_quartz_brick",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> AUTUMN_ELVEN_QUARTZ_BRICK_STAIRS = registerBlock("autumn_elven_quartz_brick_stairs",
            () -> new StairBlock(ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> AUTUMN_ELVEN_QUARTZ_BRICK_SLAB = registerBlock("autumn_elven_quartz_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredBlock<Block> AUTUMN_ELVEN_QUARTZ_MOSSY_BRICK = registerBlock("autumn_elven_quartz_mossy_brick",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredBlock<Block> AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK = registerBlock("autumn_elven_quartz_cracked_brick",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS = registerBlock("autumn_elven_quartz_cracked_brick_stairs",
            () -> new StairBlock(ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK_SLAB = registerBlock("autumn_elven_quartz_cracked_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredBlock<Block> AUTUMN_ELVEN_QUARTZ_PILLAR = registerBlock("autumn_elven_quartz_pillar",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredBlock<Block> AUTUMN_ELVEN_QUARTZ_POLISHED = registerBlock("autumn_elven_quartz_polished",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> AUTUMN_ELVEN_QUARTZ_POLISHED_STAIRS = registerBlock("autumn_elven_quartz_polished_stairs",
            () -> new StairBlock(ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> AUTUMN_ELVEN_QUARTZ_POLISHED_SLAB = registerBlock("autumn_elven_quartz_polished_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));



    //AUTUMN WOOD, PLANKS AND LOGS
    public static final DeferredBlock<Block> AUTUMN_TREE_SAPLING = registerBlock("autumn_tree_sapling",
            () -> new AutumnTreeSaplingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_SAPLING)));
    public static final DeferredBlock<Block> AUTUMN_TREE_LOG = registerBlock("autumn_tree_log",
            () -> new FeyFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)));

    public static final DeferredBlock<Block> AUTUMN_TREE_CRACKED_LOG = registerBlock("autumn_tree_cracked_log",
    ()-> new FeyFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)));

    public static final DeferredBlock<Block> AUTUMN_TREE_STRIPPED_LOG = registerBlock("autumn_tree_stripped_log",
            () -> new FeyFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG)));

    public static final DeferredBlock<Block> AUTUMN_TREE_WOOD = registerBlock("autumn_tree_wood",
            () -> new FeyFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));

    public static final DeferredBlock<Block> AUTUMN_TREE_WOOD_SLAB = registerBlock("autumn_tree_wood_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));

    public static final DeferredBlock<Block> AUTUMN_TREE_WOOD_STAIRS = registerBlock("autumn_tree_wood_stairs",
            () -> new StairBlock(AUTUMN_TREE_WOOD.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));

    public static final DeferredBlock<Block> AUTUMN_TREE_WOOD_WALL = registerBlock("autumn_tree_wood_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));

    public static final DeferredBlock<Block> AUTUMN_TREE_STRIPPED_WOOD = registerBlock("autumn_tree_stripped_wood",
            () -> new FeyFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));

    public static final DeferredBlock<Block> AUTUMN_TREE_STRIPPED_WOOD_SLAB = registerBlock("autumn_tree_stripped_wood_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));

    public static final DeferredBlock<Block> AUTUMN_TREE_STRIPPED_WOOD_STAIRS = registerBlock("autumn_tree_stripped_wood_stairs",
            () -> new StairBlock(AUTUMN_TREE_STRIPPED_WOOD.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));

    public static final DeferredBlock<Block> AUTUMN_TREE_STRIPPED_WOOD_WALL = registerBlock("autumn_tree_stripped_wood_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));

    public static final DeferredBlock<Block> AUTUMN_TREE_LEAVES = registerBlock("autumn_tree_leaves",
            () -> new FeyLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)));

    public static final DeferredBlock<Block> AUTUMN_TREE_PLANKS = registerBlock("autumn_tree_planks",
            () -> new FeyPlanksBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));

    public static final DeferredBlock<Block> AUTUMN_TREE_PLANKS_SLAB = registerBlock("autumn_tree_planks_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));

    public static final DeferredBlock<Block> AUTUMN_TREE_PLANKS_STAIRS = registerBlock("autumn_tree_planks_stairs",
            () -> new StairBlock(AUTUMN_TREE_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));

    public static final DeferredBlock<Block> AUTUMN_TREE_PLANKS_FENCE = registerBlock("autumn_tree_planks_fence", () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE)));

    public static final DeferredBlock<Block> AUTUMN_TREE_PLANKS_FENCE_GATE = registerBlock("autumn_tree_planks_fence_gate",
            () -> new FenceGateBlock(WoodType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)));

    public static final DeferredBlock<Block> AUTUMN_TREE_PLANKS_DOOR = registerBlock("autumn_tree_planks_door",
            () -> new DoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).noOcclusion()));

    public static final DeferredBlock<Block> AUTUMN_TREE_PLANKS_TRAPDOOR = registerBlock("autumn_tree_planks_trapdoor",
            () -> new TrapDoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)
                            .noOcclusion()
                            .isRedstoneConductor((state, world, pos) -> false)));

    public static final DeferredBlock<Block> AUTUMN_TREE_PLANKS_PRESSURE_PLATE = registerBlock("autumn_tree_planks_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.OAK, BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> AUTUMN_TREE_PLANKS_BUTTON = registerBlock("autumn_tree_planks_button",
            () -> new ButtonBlock(BlockSetType.OAK, 10, BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops().noCollission()));

    //SPRING WOOD, PLANKS AND LOGS
    public static final DeferredBlock<Block> SPRING_TREE_SAPLING = registerBlock("spring_tree_sapling",
            () -> new SpringTreeSaplingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_SAPLING)));
    public static final DeferredBlock<Block> SPRING_TREE_LOG = registerBlock("spring_tree_log",
            () -> new FeyFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)));

    public static final DeferredBlock<Block> SPRING_TREE_CRACKED_LOG = registerBlock("spring_tree_cracked_log",
            ()-> new FeyFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)));

    public static final DeferredBlock<Block> SPRING_TREE_STRIPPED_LOG = registerBlock("spring_tree_stripped_log",
            () -> new FeyFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG)));

    public static final DeferredBlock<Block> SPRING_TREE_WOOD = registerBlock("spring_tree_wood",
            () -> new FeyFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));

    public static final DeferredBlock<Block> SPRING_TREE_WOOD_SLAB = registerBlock("spring_tree_wood_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));

    public static final DeferredBlock<Block> SPRING_TREE_WOOD_STAIRS = registerBlock("spring_tree_wood_stairs",
            () -> new StairBlock(SPRING_TREE_WOOD.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));

    public static final DeferredBlock<Block> SPRING_TREE_WOOD_WALL = registerBlock("spring_tree_wood_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));

    public static final DeferredBlock<Block> SPRING_TREE_STRIPPED_WOOD = registerBlock("spring_tree_stripped_wood",
            () -> new FeyFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));

    public static final DeferredBlock<Block> SPRING_TREE_STRIPPED_WOOD_SLAB = registerBlock("spring_tree_stripped_wood_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));

    public static final DeferredBlock<Block> SPRING_TREE_STRIPPED_WOOD_STAIRS = registerBlock("spring_tree_stripped_wood_stairs",
            () -> new StairBlock(SPRING_TREE_STRIPPED_WOOD.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));

    public static final DeferredBlock<Block> SPRING_TREE_STRIPPED_WOOD_WALL = registerBlock("spring_tree_stripped_wood_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));

    public static final DeferredBlock<Block> SPRING_TREE_LEAVES = registerBlock("spring_tree_leaves",
            () -> new FeyLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)));

    public static final DeferredBlock<Block> SPRING_TREE_PLANKS = registerBlock("spring_tree_planks",
            () -> new FeyPlanksBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));

    public static final DeferredBlock<Block> SPRING_TREE_PLANKS_SLAB = registerBlock("spring_tree_planks_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));

    public static final DeferredBlock<Block> SPRING_TREE_PLANKS_STAIRS = registerBlock("spring_tree_planks_stairs",
            () -> new StairBlock(SPRING_TREE_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));

    public static final DeferredBlock<Block> SPRING_TREE_PLANKS_FENCE = registerBlock("spring_tree_planks_fence", () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE)));

    public static final DeferredBlock<Block> SPRING_TREE_PLANKS_FENCE_GATE = registerBlock("spring_tree_planks_fence_gate",
            () -> new FenceGateBlock(WoodType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)));

    public static final DeferredBlock<Block> SPRING_TREE_PLANKS_DOOR = registerBlock("spring_tree_planks_door",
            () -> new DoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).noOcclusion()));

    public static final DeferredBlock<Block> SPRING_TREE_PLANKS_TRAPDOOR = registerBlock("spring_tree_planks_trapdoor",
            () -> new TrapDoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)
                    .noOcclusion()
                    .isRedstoneConductor((state, world, pos) -> false)));

    public static final DeferredBlock<Block> SPRING_TREE_PLANKS_PRESSURE_PLATE = registerBlock("spring_tree_planks_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.OAK, BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> SPRING_TREE_PLANKS_BUTTON = registerBlock("spring_tree_planks_button",
            () -> new ButtonBlock(BlockSetType.OAK, 10, BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops().noCollission()));

    //SUMMER WOOD, PLANKS AND LOGS
    public static final DeferredBlock<Block> SUMMER_TREE_SAPLING = registerBlock("summer_tree_sapling",
            () -> new SummerTreeSaplingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_SAPLING)));
    public static final DeferredBlock<Block> SUMMER_TREE_LOG = registerBlock("summer_tree_log",
            () -> new FeyFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)));

    public static final DeferredBlock<Block> SUMMER_TREE_CRACKED_LOG = registerBlock("summer_tree_cracked_log",
            ()-> new FeyFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)));

    public static final DeferredBlock<Block> SUMMER_TREE_STRIPPED_LOG = registerBlock("summer_tree_stripped_log",
            () -> new FeyFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG)));

    public static final DeferredBlock<Block> SUMMER_TREE_WOOD = registerBlock("summer_tree_wood",
            () -> new FeyFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));

    public static final DeferredBlock<Block> SUMMER_TREE_WOOD_SLAB = registerBlock("summer_tree_wood_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));

    public static final DeferredBlock<Block> SUMMER_TREE_WOOD_STAIRS = registerBlock("summer_tree_wood_stairs",
            () -> new StairBlock(SUMMER_TREE_WOOD.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));

    public static final DeferredBlock<Block> SUMMER_TREE_WOOD_WALL = registerBlock("summer_tree_wood_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));

    public static final DeferredBlock<Block> SUMMER_TREE_STRIPPED_WOOD = registerBlock("summer_tree_stripped_wood",
            () -> new FeyFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));

    public static final DeferredBlock<Block> SUMMER_TREE_STRIPPED_WOOD_SLAB = registerBlock("summer_tree_stripped_wood_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));

    public static final DeferredBlock<Block> SUMMER_TREE_STRIPPED_WOOD_STAIRS = registerBlock("summer_tree_stripped_wood_stairs",
            () -> new StairBlock(SUMMER_TREE_STRIPPED_WOOD.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));

    public static final DeferredBlock<Block> SUMMER_TREE_STRIPPED_WOOD_WALL = registerBlock("summer_tree_stripped_wood_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));

    public static final DeferredBlock<Block> SUMMER_TREE_LEAVES = registerBlock("summer_tree_leaves",
            () -> new FeyLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)));

    public static final DeferredBlock<Block> SUMMER_TREE_PLANKS = registerBlock("summer_tree_planks",
            () -> new FeyPlanksBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));

    public static final DeferredBlock<Block> SUMMER_TREE_PLANKS_SLAB = registerBlock("summer_tree_planks_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));

    public static final DeferredBlock<Block> SUMMER_TREE_PLANKS_STAIRS = registerBlock("summer_tree_planks_stairs",
            () -> new StairBlock(SUMMER_TREE_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));

    public static final DeferredBlock<Block> SUMMER_TREE_PLANKS_FENCE = registerBlock("summer_tree_planks_fence", () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE)));

    public static final DeferredBlock<Block> SUMMER_TREE_PLANKS_FENCE_GATE = registerBlock("summer_tree_planks_fence_gate",
            () -> new FenceGateBlock(WoodType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)));

    public static final DeferredBlock<Block> SUMMER_TREE_PLANKS_DOOR = registerBlock("summer_tree_planks_door",
            () -> new DoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).noOcclusion()));

    public static final DeferredBlock<Block> SUMMER_TREE_PLANKS_TRAPDOOR = registerBlock("summer_tree_planks_trapdoor",
            () -> new TrapDoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)
                    .noOcclusion()
                    .isRedstoneConductor((state, world, pos) -> false)));

    public static final DeferredBlock<Block> SUMMER_TREE_PLANKS_PRESSURE_PLATE = registerBlock("summer_tree_planks_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.OAK, BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> SUMMER_TREE_PLANKS_BUTTON = registerBlock("summer_tree_planks_button",
            () -> new ButtonBlock(BlockSetType.OAK, 10, BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops().noCollission()));

    //WINTER WOOD, PLANKS AND LOGS
    public static final DeferredBlock<Block> WINTER_TREE_SAPLING = registerBlock("winter_tree_sapling",
            () -> new WinterTreeSaplingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_SAPLING)));
    public static final DeferredBlock<Block> WINTER_TREE_LOG = registerBlock("winter_tree_log",
            () -> new FeyFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)));

    public static final DeferredBlock<Block> WINTER_TREE_CRACKED_LOG = registerBlock("winter_tree_cracked_log",
            ()-> new FeyFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)));

    public static final DeferredBlock<Block> WINTER_TREE_STRIPPED_LOG = registerBlock("winter_tree_stripped_log",
            () -> new FeyFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG)));

    public static final DeferredBlock<Block> WINTER_TREE_WOOD = registerBlock("winter_tree_wood",
            () -> new FeyFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));

    public static final DeferredBlock<Block> WINTER_TREE_WOOD_SLAB = registerBlock("winter_tree_wood_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));

    public static final DeferredBlock<Block> WINTER_TREE_WOOD_STAIRS = registerBlock("winter_tree_wood_stairs",
            () -> new StairBlock(WINTER_TREE_WOOD.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));

    public static final DeferredBlock<Block> WINTER_TREE_WOOD_WALL = registerBlock("winter_tree_wood_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));

    public static final DeferredBlock<Block> WINTER_TREE_STRIPPED_WOOD = registerBlock("winter_tree_stripped_wood",
            () -> new FeyFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));

    public static final DeferredBlock<Block> WINTER_TREE_STRIPPED_WOOD_SLAB = registerBlock("winter_tree_stripped_wood_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));

    public static final DeferredBlock<Block> WINTER_TREE_STRIPPED_WOOD_STAIRS = registerBlock("winter_tree_stripped_wood_stairs",
            () -> new StairBlock(WINTER_TREE_STRIPPED_WOOD.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));

    public static final DeferredBlock<Block> WINTER_TREE_STRIPPED_WOOD_WALL = registerBlock("winter_tree_stripped_wood_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));

    public static final DeferredBlock<Block> WINTER_TREE_LEAVES = registerBlock("winter_tree_leaves",
            () -> new FeyLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)));

    public static final DeferredBlock<Block> WINTER_TREE_PLANKS = registerBlock("winter_tree_planks",
            () -> new FeyPlanksBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));

    public static final DeferredBlock<Block> WINTER_TREE_PLANKS_SLAB = registerBlock("winter_tree_planks_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));

    public static final DeferredBlock<Block> WINTER_TREE_PLANKS_STAIRS = registerBlock("winter_tree_planks_stairs",
            () -> new StairBlock(WINTER_TREE_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));

    public static final DeferredBlock<Block> WINTER_TREE_PLANKS_FENCE = registerBlock("winter_tree_planks_fence", () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE)));

    public static final DeferredBlock<Block> WINTER_TREE_PLANKS_FENCE_GATE = registerBlock("winter_tree_planks_fence_gate",
            () -> new FenceGateBlock(WoodType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)));

    public static final DeferredBlock<Block> WINTER_TREE_PLANKS_DOOR = registerBlock("winter_tree_planks_door",
            () -> new DoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).noOcclusion()));

    public static final DeferredBlock<Block> WINTER_TREE_PLANKS_TRAPDOOR = registerBlock("winter_tree_planks_trapdoor",
            () -> new TrapDoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)
                    .noOcclusion()
                    .isRedstoneConductor((state, world, pos) -> false)));

    public static final DeferredBlock<Block> WINTER_TREE_PLANKS_PRESSURE_PLATE = registerBlock("winter_tree_planks_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.OAK, BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> WINTER_TREE_PLANKS_BUTTON = registerBlock("winter_tree_planks_button",
            () -> new ButtonBlock(BlockSetType.OAK, 10, BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops().noCollission()));

    

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
