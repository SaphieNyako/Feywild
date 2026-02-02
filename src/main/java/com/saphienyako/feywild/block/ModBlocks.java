package com.saphienyako.feywild.block;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.item.ModItems;
import com.saphienyako.feywild.worldgen.ModConfiguredFeatures;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Feywild.MOD_ID);

    public static final RegistryObject<Block> FEY_GEM_ORE = registerBlockAndItem("fey_gem_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(3f, 10f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistryObject<Block> FEY_GEM_ORE_DEEP_SLATE = registerBlockAndItem("fey_gem_ore_deep_slate",
            ()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(3f, 10f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));

    public static final RegistryObject<Block> GIANT_SUN_FLOWER = registerBlock("giant_sun_flower",
            ()-> new SunFlowerBlock(4));

    public static final RegistryObject<Block> GIANT_CROCUS_FLOWER = registerBlock("giant_crocus_flower",
            ()-> new CrocusFlowerBlock(3));

    public static final RegistryObject<Block> GIANT_DANDELION_FLOWER = registerBlock("giant_dandelion_flower",
            ()-> new DandelionFlowerBlock(4));

    public static final RegistryObject<Block> MANDRAKE_CROP = BLOCKS.register("mandrake_crop",
            ()-> new MandrakeCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion().noCollission()));

    public static final RegistryObject<Block> FEY_ALTAR = registerBlockAndItem("fey_altar",
            () -> new FeyAltarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(3f, 10f).requiresCorrectToolForDrops().sound(SoundType.STONE).noOcclusion()));

    //MUSHROOM BLOCKS
    public static final RegistryObject<Block> ORANGE_MUSHROOM = registerBlock("orange_mushroom",
            () -> new MushroomBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM).noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .pushReaction(PushReaction.DESTROY), ModConfiguredFeatures.ORANGE_MUSHROOM_KEY));

    public static final RegistryObject<Block> YELLOW_MUSHROOM = registerBlock("yellow_mushroom",
            () -> new MushroomBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM).noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .pushReaction(PushReaction.DESTROY),
                    ModConfiguredFeatures.YELLOW_MUSHROOM_KEY));

    public static final RegistryObject<Block> GREEN_MUSHROOM = registerBlock("green_mushroom",
            () -> new MushroomBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM).noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .pushReaction(PushReaction.DESTROY),
                    ModConfiguredFeatures.GREEN_MUSHROOM_KEY));
    public static final RegistryObject<Block> LIGHT_BLUE_MUSHROOM = registerBlock("light_blue_mushroom",
            () -> new MushroomBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM).noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .pushReaction(PushReaction.DESTROY),
                    ModConfiguredFeatures.LIGHT_BLUE_MUSHROOM_KEY));

    public static final RegistryObject<Block> BLUE_MUSHROOM = registerBlock("blue_mushroom",
            () -> new MushroomBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM).noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .pushReaction(PushReaction.DESTROY),
                    ModConfiguredFeatures.BLUE_MUSHROOM_KEY));

    public static final RegistryObject<Block> PURPLE_MUSHROOM = registerBlock("purple_mushroom",
            () -> new MushroomBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM).noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .pushReaction(PushReaction.DESTROY),
                    ModConfiguredFeatures.PURPLE_MUSHROOM_KEY));

    public static final RegistryObject<Block> PINK_MUSHROOM = registerBlock("pink_mushroom",
            () -> new MushroomBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM).noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .pushReaction(PushReaction.DESTROY),
                    ModConfiguredFeatures.PINK_MUSHROOM_KEY));

    public static final RegistryObject<Block> ORANGE_MUSHROOM_BLOCK = registerBlock("orange_mushroom_block",
            () -> new HugeMushroomBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM_BLOCK).instrument(NoteBlockInstrument.BASS)
                    .strength(0.2F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final RegistryObject<Block> YELLOW_MUSHROOM_BLOCK = registerBlock("yellow_mushroom_block",
            () -> new HugeMushroomBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM_BLOCK).instrument(NoteBlockInstrument.BASS)
                    .strength(0.2F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final RegistryObject<Block> GREEN_MUSHROOM_BLOCK = registerBlock("green_mushroom_block",
            () -> new HugeMushroomBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM_BLOCK).instrument(NoteBlockInstrument.BASS)
                    .strength(0.2F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final RegistryObject<Block> LIGHT_BLUE_MUSHROOM_BLOCK = registerBlock("light_blue_mushroom_block",
            () -> new HugeMushroomBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM_BLOCK).instrument(NoteBlockInstrument.BASS)
                    .strength(0.2F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final RegistryObject<Block> BLUE_MUSHROOM_BLOCK = registerBlock("blue_mushroom_block",
            () -> new HugeMushroomBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM_BLOCK).instrument(NoteBlockInstrument.BASS)
                    .strength(0.2F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final RegistryObject<Block> PURPLE_MUSHROOM_BLOCK = registerBlock("purple_mushroom_block",
            () -> new HugeMushroomBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM_BLOCK).instrument(NoteBlockInstrument.BASS)
                    .strength(0.2F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final RegistryObject<Block> PINK_MUSHROOM_BLOCK = registerBlock("pink_mushroom_block",
            () -> new HugeMushroomBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM_BLOCK).instrument(NoteBlockInstrument.BASS)
                    .strength(0.2F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    //DEFAULT ELVEN QUARTZ
    public static final RegistryObject<Block> ELVEN_QUARTZ_BLOCK = registerBlock("elven_quartz_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> ELVEN_QUARTZ_STAIRS = registerBlock("elven_quartz_stairs",
            () -> new StairBlock(ModBlocks.ELVEN_QUARTZ_BLOCK.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> ELVEN_QUARTZ_SLAB = registerBlock("elven_quartz_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistryObject<Block> ELVEN_QUARTZ_BRICK = registerBlock("elven_quartz_brick",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> ELVEN_QUARTZ_BRICK_STAIRS = registerBlock("elven_quartz_brick_stairs",
            () -> new StairBlock(ModBlocks.ELVEN_QUARTZ_BRICK.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> ELVEN_QUARTZ_BRICK_SLAB = registerBlock("elven_quartz_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistryObject<Block> ELVEN_QUARTZ_MOSSY_BRICK = registerBlock("elven_quartz_mossy_brick",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistryObject<Block> ELVEN_QUARTZ_CRACKED_BRICK = registerBlock("elven_quartz_cracked_brick",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> ELVEN_QUARTZ_CRACKED_BRICK_STAIRS = registerBlock("elven_quartz_cracked_brick_stairs",
            () -> new StairBlock(ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> ELVEN_QUARTZ_CRACKED_BRICK_SLAB = registerBlock("elven_quartz_cracked_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistryObject<Block> ELVEN_QUARTZ_PILLAR = registerBlock("elven_quartz_pillar",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistryObject<Block> ELVEN_QUARTZ_POLISHED = registerBlock("elven_quartz_polished",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> ELVEN_QUARTZ_POLISHED_STAIRS = registerBlock("elven_quartz_polished_stairs",
            () -> new StairBlock(ModBlocks.ELVEN_QUARTZ_POLISHED.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> ELVEN_QUARTZ_POLISHED_SLAB = registerBlock("elven_quartz_polished_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    //SPRING ELVEN QUARTZ
    public static final RegistryObject<Block> SPRING_ELVEN_QUARTZ_BLOCK = registerBlock("spring_elven_quartz_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> SPRING_ELVEN_QUARTZ_STAIRS = registerBlock("spring_elven_quartz_stairs",
            () -> new StairBlock(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> SPRING_ELVEN_QUARTZ_SLAB = registerBlock("spring_elven_quartz_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistryObject<Block> SPRING_ELVEN_QUARTZ_BRICK = registerBlock("spring_elven_quartz_brick",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> SPRING_ELVEN_QUARTZ_BRICK_STAIRS = registerBlock("spring_elven_quartz_brick_stairs",
            () -> new StairBlock(ModBlocks.SPRING_ELVEN_QUARTZ_BRICK.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> SPRING_ELVEN_QUARTZ_BRICK_SLAB = registerBlock("spring_elven_quartz_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistryObject<Block> SPRING_ELVEN_QUARTZ_MOSSY_BRICK = registerBlock("spring_elven_quartz_mossy_brick",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistryObject<Block> SPRING_ELVEN_QUARTZ_CRACKED_BRICK = registerBlock("spring_elven_quartz_cracked_brick",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> SPRING_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS = registerBlock("spring_elven_quartz_cracked_brick_stairs",
            () -> new StairBlock(ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> SPRING_ELVEN_QUARTZ_CRACKED_BRICK_SLAB = registerBlock("spring_elven_quartz_cracked_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistryObject<Block> SPRING_ELVEN_QUARTZ_PILLAR = registerBlock("spring_elven_quartz_pillar",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistryObject<Block> SPRING_ELVEN_QUARTZ_POLISHED = registerBlock("spring_elven_quartz_polished",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> SPRING_ELVEN_QUARTZ_POLISHED_STAIRS = registerBlock("spring_elven_quartz_polished_stairs",
            () -> new StairBlock(ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> SPRING_ELVEN_QUARTZ_POLISHED_SLAB = registerBlock("spring_elven_quartz_polished_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    //SUMMER ELVEN QUARTZ

    public static final RegistryObject<Block> SUMMER_ELVEN_QUARTZ_BLOCK = registerBlock("summer_elven_quartz_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> SUMMER_ELVEN_QUARTZ_STAIRS = registerBlock("summer_elven_quartz_stairs",
            () -> new StairBlock(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> SUMMER_ELVEN_QUARTZ_SLAB = registerBlock("summer_elven_quartz_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistryObject<Block> SUMMER_ELVEN_QUARTZ_BRICK = registerBlock("summer_elven_quartz_brick",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> SUMMER_ELVEN_QUARTZ_BRICK_STAIRS = registerBlock("summer_elven_quartz_brick_stairs",
            () -> new StairBlock(ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> SUMMER_ELVEN_QUARTZ_BRICK_SLAB = registerBlock("summer_elven_quartz_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistryObject<Block> SUMMER_ELVEN_QUARTZ_MOSSY_BRICK = registerBlock("summer_elven_quartz_mossy_brick",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistryObject<Block> SUMMER_ELVEN_QUARTZ_CRACKED_BRICK = registerBlock("summer_elven_quartz_cracked_brick",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> SUMMER_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS = registerBlock("summer_elven_quartz_cracked_brick_stairs",
            () -> new StairBlock(ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> SUMMER_ELVEN_QUARTZ_CRACKED_BRICK_SLAB = registerBlock("summer_elven_quartz_cracked_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistryObject<Block> SUMMER_ELVEN_QUARTZ_PILLAR = registerBlock("summer_elven_quartz_pillar",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistryObject<Block> SUMMER_ELVEN_QUARTZ_POLISHED = registerBlock("summer_elven_quartz_polished",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> SUMMER_ELVEN_QUARTZ_POLISHED_STAIRS = registerBlock("summer_elven_quartz_polished_stairs",
            () -> new StairBlock(ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> SUMMER_ELVEN_QUARTZ_POLISHED_SLAB = registerBlock("summer_elven_quartz_polished_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    //WINTER ELVEN QUARTZ

    public static final RegistryObject<Block> WINTER_ELVEN_QUARTZ_BLOCK = registerBlock("winter_elven_quartz_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> WINTER_ELVEN_QUARTZ_STAIRS = registerBlock("winter_elven_quartz_stairs",
            () -> new StairBlock(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> WINTER_ELVEN_QUARTZ_SLAB = registerBlock("winter_elven_quartz_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistryObject<Block> WINTER_ELVEN_QUARTZ_BRICK = registerBlock("winter_elven_quartz_brick",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> WINTER_ELVEN_QUARTZ_BRICK_STAIRS = registerBlock("winter_elven_quartz_brick_stairs",
            () -> new StairBlock(ModBlocks.WINTER_ELVEN_QUARTZ_BRICK.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> WINTER_ELVEN_QUARTZ_BRICK_SLAB = registerBlock("winter_elven_quartz_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistryObject<Block> WINTER_ELVEN_QUARTZ_MOSSY_BRICK = registerBlock("winter_elven_quartz_mossy_brick",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistryObject<Block> WINTER_ELVEN_QUARTZ_CRACKED_BRICK = registerBlock("winter_elven_quartz_cracked_brick",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> WINTER_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS = registerBlock("winter_elven_quartz_cracked_brick_stairs",
            () -> new StairBlock(ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> WINTER_ELVEN_QUARTZ_CRACKED_BRICK_SLAB = registerBlock("winter_elven_quartz_cracked_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistryObject<Block> WINTER_ELVEN_QUARTZ_PILLAR = registerBlock("winter_elven_quartz_pillar",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistryObject<Block> WINTER_ELVEN_QUARTZ_POLISHED = registerBlock("winter_elven_quartz_polished",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> WINTER_ELVEN_QUARTZ_POLISHED_STAIRS = registerBlock("winter_elven_quartz_polished_stairs",
            () -> new StairBlock(ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> WINTER_ELVEN_QUARTZ_POLISHED_SLAB = registerBlock("winter_elven_quartz_polished_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));



    //AUTUMN ELVEN QUARTZ
    public static final RegistryObject<Block> AUTUMN_ELVEN_QUARTZ_BLOCK = registerBlock("autumn_elven_quartz_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> AUTUMN_ELVEN_QUARTZ_STAIRS = registerBlock("autumn_elven_quartz_stairs",
            () -> new StairBlock(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> AUTUMN_ELVEN_QUARTZ_SLAB = registerBlock("autumn_elven_quartz_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistryObject<Block> AUTUMN_ELVEN_QUARTZ_BRICK = registerBlock("autumn_elven_quartz_brick",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> AUTUMN_ELVEN_QUARTZ_BRICK_STAIRS = registerBlock("autumn_elven_quartz_brick_stairs",
            () -> new StairBlock(ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> AUTUMN_ELVEN_QUARTZ_BRICK_SLAB = registerBlock("autumn_elven_quartz_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistryObject<Block> AUTUMN_ELVEN_QUARTZ_MOSSY_BRICK = registerBlock("autumn_elven_quartz_mossy_brick",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistryObject<Block> AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK = registerBlock("autumn_elven_quartz_cracked_brick",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS = registerBlock("autumn_elven_quartz_cracked_brick_stairs",
            () -> new StairBlock(ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK_SLAB = registerBlock("autumn_elven_quartz_cracked_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistryObject<Block> AUTUMN_ELVEN_QUARTZ_PILLAR = registerBlock("autumn_elven_quartz_pillar",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistryObject<Block> AUTUMN_ELVEN_QUARTZ_POLISHED = registerBlock("autumn_elven_quartz_polished",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> AUTUMN_ELVEN_QUARTZ_POLISHED_STAIRS = registerBlock("autumn_elven_quartz_polished_stairs",
            () -> new StairBlock(ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> AUTUMN_ELVEN_QUARTZ_POLISHED_SLAB = registerBlock("autumn_elven_quartz_polished_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));





    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    private static<T extends Block> RegistryObject<T> registerBlockAndItem(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
