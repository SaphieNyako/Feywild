package com.saphienyako.feywild.block;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.block.trees.*;
import com.saphienyako.feywild.entity.Alignment;
import com.saphienyako.feywild.item.ModItems;
import com.saphienyako.feywild.particle.ModParticles;
import com.saphienyako.feywild.worldgen.ModConfiguredFeatures;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Feywild.MOD_ID);

    public static final DeferredBlock<@NotNull Block> FEY_GEM_ORE = registerBlock("fey_gem_ore",
            Block::new, properties -> BlockBehaviour.Properties.of().strength(4f).explosionResistance(10f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> FEY_GEM_ORE_DEEP_SLATE = registerBlock("fey_gem_ore_deep_slate",
            Block::new, properties -> BlockBehaviour.Properties.of().strength(4f).explosionResistance(10f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE));

    public static final DeferredBlock<@NotNull Block> MANDRAKE_CROP = BLOCKS.registerBlock("mandrake_crop",
            MandrakeCropBlock::new,
            properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noCollision().noOcclusion());

    public static final DeferredBlock<@NotNull Block> GIANT_SUN_FLOWER = registerBlock("giant_sun_flower",
            properties -> new SunFlowerBlock(4, properties),
            properties -> properties
                    .noOcclusion()
                    .sound(SoundType.BAMBOO)
                    .strength(1, 1)
                    .lightLevel(value -> 8)
                    .pushReaction(PushReaction.DESTROY));

    public static final DeferredBlock<@NotNull Block> GIANT_CROCUS_FLOWER = registerBlock("giant_crocus_flower",
            properties -> new CrocusFlowerBlock(3, properties),
            properties -> properties
                    .noOcclusion()
                    .sound(SoundType.BAMBOO)
                    .strength(1, 1)
                    .lightLevel(value -> 8)
                    .pushReaction(PushReaction.DESTROY));

    public static final DeferredBlock<@NotNull Block> GIANT_DANDELION_FLOWER = registerBlock("giant_dandelion_flower",
            properties -> new DandelionFlowerBlock(4, properties),
            properties -> properties
                    .noOcclusion()
                    .sound(SoundType.BAMBOO)
                    .strength(1, 1)
                    .lightLevel(value -> 8)
                    .pushReaction(PushReaction.DESTROY));

    public static final DeferredBlock<@NotNull Block> FEY_ALTAR = registerBlock("fey_altar",
            FeyAltarBlock::new, properties -> properties
                    .strength(3f, 10f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)
                    .noOcclusion());
    public static final DeferredBlock<@NotNull Block> FEY_ALTAR_QUEEN_TITANIA = registerBlock("fey_altar_queen_titania",
            properties -> new NewFeyAltarBlock(properties, Alignment.SUMMER), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(3f, 10f).requiresCorrectToolForDrops().sound(SoundType.STONE).noOcclusion());

    public static final DeferredBlock<@NotNull Block> FEY_ALTAR_QUEEN_MAB = registerBlock("fey_altar_queen_mab",
            properties -> new NewFeyAltarBlock(properties, Alignment.WINTER), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(3f, 10f).requiresCorrectToolForDrops().sound(SoundType.STONE).noOcclusion());

    public static final DeferredBlock<@NotNull Block> FEY_ALTAR_ASHEN_LORD = registerBlock("fey_altar_ashen_lord",
            properties -> new NewFeyAltarBlock(properties, Alignment.AUTUMN), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(3f, 10f).requiresCorrectToolForDrops().sound(SoundType.STONE).noOcclusion());

    public static final DeferredBlock<@NotNull Block> FEY_ALTAR_OBERON = registerBlock("fey_altar_oberon",
            properties -> new NewFeyAltarBlock(properties, Alignment.SPRING), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(3f, 10f).requiresCorrectToolForDrops().sound(SoundType.STONE).noOcclusion());


    //MUSHROOM BLOCKS
    public static final DeferredBlock<@NotNull Block> ORANGE_MUSHROOM = registerBlock("orange_mushroom",
            properties -> new MushroomBlock(ModConfiguredFeatures.ORANGE_MUSHROOM_KEY, properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM).noCollision()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .pushReaction(PushReaction.DESTROY));

    public static final DeferredBlock<@NotNull Block> YELLOW_MUSHROOM = registerBlock("yellow_mushroom",
            properties -> new MushroomBlock(ModConfiguredFeatures.YELLOW_MUSHROOM_KEY, properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM).noCollision()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .pushReaction(PushReaction.DESTROY));

    public static final DeferredBlock<@NotNull Block> GREEN_MUSHROOM = registerBlock("green_mushroom",
            properties -> new MushroomBlock(ModConfiguredFeatures.GREEN_MUSHROOM_KEY, properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM).noCollision()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .pushReaction(PushReaction.DESTROY));
    public static final DeferredBlock<@NotNull Block> LIGHT_BLUE_MUSHROOM = registerBlock("light_blue_mushroom",
            properties -> new MushroomBlock(ModConfiguredFeatures.LIGHT_BLUE_MUSHROOM_KEY, properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM).noCollision()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .pushReaction(PushReaction.DESTROY));

    public static final DeferredBlock<@NotNull Block> BLUE_MUSHROOM = registerBlock("blue_mushroom",
            properties -> new MushroomBlock(ModConfiguredFeatures.BLUE_MUSHROOM_KEY, properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM).noCollision()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .pushReaction(PushReaction.DESTROY));

    public static final DeferredBlock<@NotNull Block> PURPLE_MUSHROOM = registerBlock("purple_mushroom",
            properties -> new MushroomBlock(ModConfiguredFeatures.PURPLE_MUSHROOM_KEY, properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM).noCollision()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .pushReaction(PushReaction.DESTROY));

    public static final DeferredBlock<@NotNull Block> PINK_MUSHROOM = registerBlock("pink_mushroom",
            properties -> new MushroomBlock(ModConfiguredFeatures.PINK_MUSHROOM_KEY, properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM).noCollision()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .pushReaction(PushReaction.DESTROY));

    public static final DeferredBlock<@NotNull Block> ORANGE_MUSHROOM_BLOCK = registerBlock("orange_mushroom_block",
            HugeMushroomBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM_BLOCK).instrument(NoteBlockInstrument.BASS)
                    .strength(0.2F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava());

    public static final DeferredBlock<@NotNull Block> YELLOW_MUSHROOM_BLOCK = registerBlock("yellow_mushroom_block",
            HugeMushroomBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM_BLOCK).instrument(NoteBlockInstrument.BASS)
                    .strength(0.2F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava());

    public static final DeferredBlock<@NotNull Block> GREEN_MUSHROOM_BLOCK = registerBlock("green_mushroom_block",
            HugeMushroomBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM_BLOCK).instrument(NoteBlockInstrument.BASS)
                    .strength(0.2F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava());

    public static final DeferredBlock<@NotNull Block> LIGHT_BLUE_MUSHROOM_BLOCK = registerBlock("light_blue_mushroom_block",
            HugeMushroomBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM_BLOCK).instrument(NoteBlockInstrument.BASS)
                    .strength(0.2F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava());

    public static final DeferredBlock<@NotNull Block> BLUE_MUSHROOM_BLOCK = registerBlock("blue_mushroom_block",
            HugeMushroomBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM_BLOCK).instrument(NoteBlockInstrument.BASS)
                    .strength(0.2F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava());

    public static final DeferredBlock<@NotNull Block> PURPLE_MUSHROOM_BLOCK = registerBlock("purple_mushroom_block",
            HugeMushroomBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM_BLOCK).instrument(NoteBlockInstrument.BASS)
                    .strength(0.2F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava());

    public static final DeferredBlock<@NotNull Block> PINK_MUSHROOM_BLOCK = registerBlock("pink_mushroom_block",
            HugeMushroomBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM_BLOCK).instrument(NoteBlockInstrument.BASS)
                    .strength(0.2F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava());

    //DEFAULT ELVEN QUARTZ
    public static final DeferredBlock<@NotNull Block> ELVEN_QUARTZ_BLOCK = registerBlock("elven_quartz_block",
            Block::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> ELVEN_QUARTZ_STAIRS = registerBlock("elven_quartz_stairs",
            properties -> new StairBlock(ModBlocks.ELVEN_QUARTZ_BLOCK.get().defaultBlockState(), properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> ELVEN_QUARTZ_SLAB = registerBlock("elven_quartz_slab",
            SlabBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final DeferredBlock<@NotNull Block> ELVEN_QUARTZ_BRICK = registerBlock("elven_quartz_brick",
            Block::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> ELVEN_QUARTZ_BRICK_STAIRS = registerBlock("elven_quartz_brick_stairs",
            properties -> new StairBlock(ModBlocks.ELVEN_QUARTZ_BRICK.get().defaultBlockState(), properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> ELVEN_QUARTZ_BRICK_SLAB = registerBlock("elven_quartz_brick_slab",
            SlabBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final DeferredBlock<@NotNull Block> ELVEN_QUARTZ_MOSSY_BRICK = registerBlock("elven_quartz_mossy_brick",
            Block::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final DeferredBlock<@NotNull Block> ELVEN_QUARTZ_CRACKED_BRICK = registerBlock("elven_quartz_cracked_brick",
            Block::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> ELVEN_QUARTZ_CRACKED_BRICK_STAIRS = registerBlock("elven_quartz_cracked_brick_stairs",
            properties -> new StairBlock(ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK.get().defaultBlockState(), properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> ELVEN_QUARTZ_CRACKED_BRICK_SLAB = registerBlock("elven_quartz_cracked_brick_slab",
            SlabBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final DeferredBlock<@NotNull Block> ELVEN_QUARTZ_PILLAR = registerBlock("elven_quartz_pillar",
            RotatedPillarBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final DeferredBlock<@NotNull Block> ELVEN_QUARTZ_POLISHED = registerBlock("elven_quartz_polished",
            Block::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> ELVEN_QUARTZ_POLISHED_STAIRS = registerBlock("elven_quartz_polished_stairs",
            properties -> new StairBlock(ModBlocks.ELVEN_QUARTZ_POLISHED.get().defaultBlockState(), properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> ELVEN_QUARTZ_POLISHED_SLAB = registerBlock("elven_quartz_polished_slab",
            SlabBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    //SPRING ELVEN QUARTZ
    public static final DeferredBlock<@NotNull Block> SPRING_ELVEN_QUARTZ_BLOCK = registerBlock("spring_elven_quartz_block",
            Block::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> SPRING_ELVEN_QUARTZ_STAIRS = registerBlock("spring_elven_quartz_stairs",
            properties -> new StairBlock(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get().defaultBlockState(), properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> SPRING_ELVEN_QUARTZ_SLAB = registerBlock("spring_elven_quartz_slab",
            SlabBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final DeferredBlock<@NotNull Block> SPRING_ELVEN_QUARTZ_BRICK = registerBlock("spring_elven_quartz_brick",
            Block::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> SPRING_ELVEN_QUARTZ_BRICK_STAIRS = registerBlock("spring_elven_quartz_brick_stairs",
            properties -> new StairBlock(ModBlocks.SPRING_ELVEN_QUARTZ_BRICK.get().defaultBlockState(), properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> SPRING_ELVEN_QUARTZ_BRICK_SLAB = registerBlock("spring_elven_quartz_brick_slab",
            SlabBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final DeferredBlock<@NotNull Block> SPRING_ELVEN_QUARTZ_MOSSY_BRICK = registerBlock("spring_elven_quartz_mossy_brick",
            Block::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final DeferredBlock<@NotNull Block> SPRING_ELVEN_QUARTZ_CRACKED_BRICK = registerBlock("spring_elven_quartz_cracked_brick",
            Block::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> SPRING_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS = registerBlock("spring_elven_quartz_cracked_brick_stairs",
            properties -> new StairBlock(ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK.get().defaultBlockState(), properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> SPRING_ELVEN_QUARTZ_CRACKED_BRICK_SLAB = registerBlock("spring_elven_quartz_cracked_brick_slab",
            SlabBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final DeferredBlock<@NotNull Block> SPRING_ELVEN_QUARTZ_PILLAR = registerBlock("spring_elven_quartz_pillar",
            RotatedPillarBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final DeferredBlock<@NotNull Block> SPRING_ELVEN_QUARTZ_POLISHED = registerBlock("spring_elven_quartz_polished",
            Block::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> SPRING_ELVEN_QUARTZ_POLISHED_STAIRS = registerBlock("spring_elven_quartz_polished_stairs",
            properties -> new StairBlock(ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED.get().defaultBlockState(), properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> SPRING_ELVEN_QUARTZ_POLISHED_SLAB = registerBlock("spring_elven_quartz_polished_slab",
            SlabBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    //SUMMER ELVEN QUARTZ

    public static final DeferredBlock<@NotNull Block> SUMMER_ELVEN_QUARTZ_BLOCK = registerBlock("summer_elven_quartz_block",
            Block::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> SUMMER_ELVEN_QUARTZ_STAIRS = registerBlock("summer_elven_quartz_stairs",
            properties -> new StairBlock(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get().defaultBlockState(), properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> SUMMER_ELVEN_QUARTZ_SLAB = registerBlock("summer_elven_quartz_slab",
            SlabBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final DeferredBlock<@NotNull Block> SUMMER_ELVEN_QUARTZ_BRICK = registerBlock("summer_elven_quartz_brick",
            Block::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> SUMMER_ELVEN_QUARTZ_BRICK_STAIRS = registerBlock("summer_elven_quartz_brick_stairs",
            properties -> new StairBlock(ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK.get().defaultBlockState(), properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> SUMMER_ELVEN_QUARTZ_BRICK_SLAB = registerBlock("summer_elven_quartz_brick_slab",
            SlabBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final DeferredBlock<@NotNull Block> SUMMER_ELVEN_QUARTZ_MOSSY_BRICK = registerBlock("summer_elven_quartz_mossy_brick",
            Block::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final DeferredBlock<@NotNull Block> SUMMER_ELVEN_QUARTZ_CRACKED_BRICK = registerBlock("summer_elven_quartz_cracked_brick",
            Block::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> SUMMER_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS = registerBlock("summer_elven_quartz_cracked_brick_stairs",
            properties -> new StairBlock(ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK.get().defaultBlockState(), properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> SUMMER_ELVEN_QUARTZ_CRACKED_BRICK_SLAB = registerBlock("summer_elven_quartz_cracked_brick_slab",
            SlabBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final DeferredBlock<@NotNull Block> SUMMER_ELVEN_QUARTZ_PILLAR = registerBlock("summer_elven_quartz_pillar",
            RotatedPillarBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final DeferredBlock<@NotNull Block> SUMMER_ELVEN_QUARTZ_POLISHED = registerBlock("summer_elven_quartz_polished",
            Block::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> SUMMER_ELVEN_QUARTZ_POLISHED_STAIRS = registerBlock("summer_elven_quartz_polished_stairs",
            properties -> new StairBlock(ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED.get().defaultBlockState(), properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> SUMMER_ELVEN_QUARTZ_POLISHED_SLAB = registerBlock("summer_elven_quartz_polished_slab",
            SlabBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    //WINTER ELVEN QUARTZ

    public static final DeferredBlock<@NotNull Block> WINTER_ELVEN_QUARTZ_BLOCK = registerBlock("winter_elven_quartz_block",
            Block::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> WINTER_ELVEN_QUARTZ_STAIRS = registerBlock("winter_elven_quartz_stairs",
            properties -> new StairBlock(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get().defaultBlockState(), properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> WINTER_ELVEN_QUARTZ_SLAB = registerBlock("winter_elven_quartz_slab",
            SlabBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final DeferredBlock<@NotNull Block> WINTER_ELVEN_QUARTZ_BRICK = registerBlock("winter_elven_quartz_brick",
            Block::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> WINTER_ELVEN_QUARTZ_BRICK_STAIRS = registerBlock("winter_elven_quartz_brick_stairs",
            properties -> new StairBlock(ModBlocks.WINTER_ELVEN_QUARTZ_BRICK.get().defaultBlockState(), properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> WINTER_ELVEN_QUARTZ_BRICK_SLAB = registerBlock("winter_elven_quartz_brick_slab",
            SlabBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final DeferredBlock<@NotNull Block> WINTER_ELVEN_QUARTZ_MOSSY_BRICK = registerBlock("winter_elven_quartz_mossy_brick",
            Block::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final DeferredBlock<@NotNull Block> WINTER_ELVEN_QUARTZ_CRACKED_BRICK = registerBlock("winter_elven_quartz_cracked_brick",
            Block::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> WINTER_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS = registerBlock("winter_elven_quartz_cracked_brick_stairs",
            properties -> new StairBlock(ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK.get().defaultBlockState(), properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> WINTER_ELVEN_QUARTZ_CRACKED_BRICK_SLAB = registerBlock("winter_elven_quartz_cracked_brick_slab",
            SlabBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final DeferredBlock<@NotNull Block> WINTER_ELVEN_QUARTZ_PILLAR = registerBlock("winter_elven_quartz_pillar",
            RotatedPillarBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final DeferredBlock<@NotNull Block> WINTER_ELVEN_QUARTZ_POLISHED = registerBlock("winter_elven_quartz_polished",
            Block::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> WINTER_ELVEN_QUARTZ_POLISHED_STAIRS = registerBlock("winter_elven_quartz_polished_stairs",
            properties -> new StairBlock(ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED.get().defaultBlockState(), properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> WINTER_ELVEN_QUARTZ_POLISHED_SLAB = registerBlock("winter_elven_quartz_polished_slab",
            SlabBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));



    //AUTUMN ELVEN QUARTZ
    public static final DeferredBlock<@NotNull Block> AUTUMN_ELVEN_QUARTZ_BLOCK = registerBlock("autumn_elven_quartz_block",
            Block::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> AUTUMN_ELVEN_QUARTZ_STAIRS = registerBlock("autumn_elven_quartz_stairs",
            properties -> new StairBlock(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get().defaultBlockState(), properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> AUTUMN_ELVEN_QUARTZ_SLAB = registerBlock("autumn_elven_quartz_slab",
            SlabBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final DeferredBlock<@NotNull Block> AUTUMN_ELVEN_QUARTZ_BRICK = registerBlock("autumn_elven_quartz_brick",
            Block::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> AUTUMN_ELVEN_QUARTZ_BRICK_STAIRS = registerBlock("autumn_elven_quartz_brick_stairs",
            properties -> new StairBlock(ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK.get().defaultBlockState(), properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> AUTUMN_ELVEN_QUARTZ_BRICK_SLAB = registerBlock("autumn_elven_quartz_brick_slab",
            SlabBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final DeferredBlock<@NotNull Block> AUTUMN_ELVEN_QUARTZ_MOSSY_BRICK = registerBlock("autumn_elven_quartz_mossy_brick",
            Block::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final DeferredBlock<@NotNull Block> AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK = registerBlock("autumn_elven_quartz_cracked_brick",
            Block::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS = registerBlock("autumn_elven_quartz_cracked_brick_stairs",
            properties -> new StairBlock(ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK.get().defaultBlockState(), properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK_SLAB = registerBlock("autumn_elven_quartz_cracked_brick_slab",
            SlabBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final DeferredBlock<@NotNull Block> AUTUMN_ELVEN_QUARTZ_PILLAR = registerBlock("autumn_elven_quartz_pillar",
            RotatedPillarBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final DeferredBlock<@NotNull Block> AUTUMN_ELVEN_QUARTZ_POLISHED = registerBlock("autumn_elven_quartz_polished",
            Block::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> AUTUMN_ELVEN_QUARTZ_POLISHED_STAIRS = registerBlock("autumn_elven_quartz_polished_stairs",
            properties -> new StairBlock(ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED.get().defaultBlockState(), properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<@NotNull Block> AUTUMN_ELVEN_QUARTZ_POLISHED_SLAB = registerBlock("autumn_elven_quartz_polished_slab",
            SlabBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));



    //AUTUMN WOOD, PLANKS AND LOGS
    public static final DeferredBlock<@NotNull Block> AUTUMN_TREE_SAPLING = registerBlock("autumn_tree_sapling",
            AutumnTreeSaplingBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_SAPLING));
    public static final DeferredBlock<@NotNull Block> AUTUMN_TREE_LOG = registerBlock("autumn_tree_log",
            FeyFlammableRotatedPillarBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG));

    public static final DeferredBlock<@NotNull Block> AUTUMN_TREE_CRACKED_LOG = registerBlock("autumn_tree_cracked_log",
            FeyCrackedLogBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG));

    public static final DeferredBlock<@NotNull Block> AUTUMN_TREE_STRIPPED_LOG = registerBlock("autumn_tree_stripped_log",
            FeyFlammableRotatedPillarBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG));

    public static final DeferredBlock<@NotNull Block> AUTUMN_TREE_WOOD = registerBlock("autumn_tree_wood",
            FeyFlammableRotatedPillarBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD));

    public static final DeferredBlock<@NotNull Block> AUTUMN_TREE_WOOD_SLAB = registerBlock("autumn_tree_wood_slab",
            SlabBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD));

    public static final DeferredBlock<@NotNull Block> AUTUMN_TREE_WOOD_STAIRS = registerBlock("autumn_tree_wood_stairs",
            properties -> new StairBlock(AUTUMN_TREE_WOOD.get().defaultBlockState(), properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD));

    public static final DeferredBlock<@NotNull Block> AUTUMN_TREE_WOOD_WALL = registerBlock("autumn_tree_wood_wall",
            WallBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD));

    public static final DeferredBlock<@NotNull Block> AUTUMN_TREE_STRIPPED_WOOD = registerBlock("autumn_tree_stripped_wood",
            FeyFlammableRotatedPillarBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD));

    public static final DeferredBlock<@NotNull Block> AUTUMN_TREE_STRIPPED_WOOD_SLAB = registerBlock("autumn_tree_stripped_wood_slab",
            SlabBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD));

    public static final DeferredBlock<@NotNull Block> AUTUMN_TREE_STRIPPED_WOOD_STAIRS = registerBlock("autumn_tree_stripped_wood_stairs",
            properties -> new StairBlock(AUTUMN_TREE_STRIPPED_WOOD.get().defaultBlockState(), properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD));

    public static final DeferredBlock<@NotNull Block> AUTUMN_TREE_STRIPPED_WOOD_WALL = registerBlock("autumn_tree_stripped_wood_wall", WallBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD));

    public static final DeferredBlock<@NotNull Block> AUTUMN_TREE_LEAVES_BROWN = registerBlock("autumn_tree_leaves_brown",
            properties -> new FeyLeavesBlock(properties, ModParticles.AUTUMN_LEAF_PARTICLE, 14), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES));
    public static final DeferredBlock<@NotNull Block> AUTUMN_TREE_LEAVES_RED = registerBlock("autumn_tree_leaves_red",
            properties -> new FeyLeavesBlock(properties, ModParticles.AUTUMN_LEAF_PARTICLE, 14), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES));
    public static final DeferredBlock<@NotNull Block> AUTUMN_TREE_LEAVES_LIGHT_GRAY = registerBlock("autumn_tree_leaves_light_gray",
            properties -> new FeyLeavesBlock(properties, ModParticles.AUTUMN_LEAF_PARTICLE, 14), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES));
    public static final DeferredBlock<@NotNull Block> AUTUMN_TREE_LEAVES_DARK_GRAY = registerBlock("autumn_tree_leaves_dark_gray",
            properties -> new FeyLeavesBlock(properties, ModParticles.AUTUMN_LEAF_PARTICLE, 14), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES));

    public static final DeferredBlock<@NotNull Block> AUTUMN_TREE_PLANKS = registerBlock("autumn_tree_planks",
            FeyPlanksBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));

    public static final DeferredBlock<@NotNull Block> AUTUMN_TREE_PLANKS_SLAB = registerBlock("autumn_tree_planks_slab",
            SlabBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));

    public static final DeferredBlock<@NotNull Block> AUTUMN_TREE_PLANKS_STAIRS = registerBlock("autumn_tree_planks_stairs",
            properties -> new StairBlock(AUTUMN_TREE_PLANKS.get().defaultBlockState(), properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));

    public static final DeferredBlock<@NotNull Block> AUTUMN_TREE_PLANKS_FENCE = registerBlock("autumn_tree_planks_fence", FenceBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE));

    public static final DeferredBlock<@NotNull Block> AUTUMN_TREE_PLANKS_FENCE_GATE = registerBlock("autumn_tree_planks_fence_gate",
            properties -> new FenceGateBlock(WoodType.OAK, properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE));

    public static final DeferredBlock<@NotNull Block> AUTUMN_TREE_PLANKS_DOOR = registerBlock("autumn_tree_planks_door",
            properties -> new DoorBlock(BlockSetType.OAK, properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).noOcclusion());

    public static final DeferredBlock<@NotNull Block> AUTUMN_TREE_PLANKS_TRAPDOOR = registerBlock("autumn_tree_planks_trapdoor",
            properties -> new TrapDoorBlock(BlockSetType.OAK, properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)
                    .noOcclusion()
                    .isRedstoneConductor((state, world, pos) -> false));

    public static final DeferredBlock<@NotNull Block> AUTUMN_TREE_PLANKS_PRESSURE_PLATE = registerBlock("autumn_tree_planks_pressure_plate",
            properties -> new PressurePlateBlock(BlockSetType.OAK, properties), properties -> BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops());
    public static final DeferredBlock<@NotNull Block> AUTUMN_TREE_PLANKS_BUTTON = registerBlock("autumn_tree_planks_button",
            properties -> new ButtonBlock(BlockSetType.OAK, 10, properties), properties -> BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops().noCollision());

    //SPRING WOOD, PLANKS AND LOGS
    public static final DeferredBlock<@NotNull Block> SPRING_TREE_SAPLING = registerBlock("spring_tree_sapling",
            SpringTreeSaplingBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_SAPLING));
    public static final DeferredBlock<@NotNull Block> SPRING_TREE_LOG = registerBlock("spring_tree_log",
            FeyFlammableRotatedPillarBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG));

    public static final DeferredBlock<@NotNull Block> SPRING_TREE_CRACKED_LOG = registerBlock("spring_tree_cracked_log",
            FeyCrackedLogBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG));

    public static final DeferredBlock<@NotNull Block> SPRING_TREE_STRIPPED_LOG = registerBlock("spring_tree_stripped_log",
            FeyFlammableRotatedPillarBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG));

    public static final DeferredBlock<@NotNull Block> SPRING_TREE_WOOD = registerBlock("spring_tree_wood",
            FeyFlammableRotatedPillarBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD));

    public static final DeferredBlock<@NotNull Block> SPRING_TREE_WOOD_SLAB = registerBlock("spring_tree_wood_slab",
            SlabBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD));

    public static final DeferredBlock<@NotNull Block> SPRING_TREE_WOOD_STAIRS = registerBlock("spring_tree_wood_stairs",
            properties -> new StairBlock(SPRING_TREE_WOOD.get().defaultBlockState(), properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD));

    public static final DeferredBlock<@NotNull Block> SPRING_TREE_WOOD_WALL = registerBlock("spring_tree_wood_wall",
            WallBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD));

    public static final DeferredBlock<@NotNull Block> SPRING_TREE_STRIPPED_WOOD = registerBlock("spring_tree_stripped_wood",
            FeyFlammableRotatedPillarBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD));

    public static final DeferredBlock<@NotNull Block> SPRING_TREE_STRIPPED_WOOD_SLAB = registerBlock("spring_tree_stripped_wood_slab",
            SlabBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD));

    public static final DeferredBlock<@NotNull Block> SPRING_TREE_STRIPPED_WOOD_STAIRS = registerBlock("spring_tree_stripped_wood_stairs",
            properties -> new StairBlock(SPRING_TREE_STRIPPED_WOOD.get().defaultBlockState(), properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD));

    public static final DeferredBlock<@NotNull Block> SPRING_TREE_STRIPPED_WOOD_WALL = registerBlock("spring_tree_stripped_wood_wall", WallBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD));

    public static final DeferredBlock<@NotNull Block> SPRING_TREE_LEAVES_CYAN = registerBlock("spring_tree_leaves_cyan",
            properties -> new FeyLeavesBlock(properties, ModParticles.SPRING_LEAF_PARTICLE, 14), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES));
    public static final DeferredBlock<@NotNull Block> SPRING_TREE_LEAVES_GREEN = registerBlock("spring_tree_leaves_green",
            properties -> new FeyLeavesBlock(properties, ModParticles.SPRING_LEAF_PARTICLE, 14), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES));
    public static final DeferredBlock<@NotNull Block> SPRING_TREE_LEAVES_LIME = registerBlock("spring_tree_leaves_lime",
            properties -> new FeyLeavesBlock(properties, ModParticles.SPRING_LEAF_PARTICLE, 14), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES));

    public static final DeferredBlock<@NotNull Block> SPRING_TREE_PLANKS = registerBlock("spring_tree_planks",
            FeyPlanksBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));

    public static final DeferredBlock<@NotNull Block> SPRING_TREE_PLANKS_SLAB = registerBlock("spring_tree_planks_slab",
            SlabBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));

    public static final DeferredBlock<@NotNull Block> SPRING_TREE_PLANKS_STAIRS = registerBlock("spring_tree_planks_stairs",
            properties -> new StairBlock(SPRING_TREE_PLANKS.get().defaultBlockState(), properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));

    public static final DeferredBlock<@NotNull Block> SPRING_TREE_PLANKS_FENCE = registerBlock("spring_tree_planks_fence", FenceBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE));

    public static final DeferredBlock<@NotNull Block> SPRING_TREE_PLANKS_FENCE_GATE = registerBlock("spring_tree_planks_fence_gate",
            properties -> new FenceGateBlock(WoodType.OAK, properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE));

    public static final DeferredBlock<@NotNull Block> SPRING_TREE_PLANKS_DOOR = registerBlock("spring_tree_planks_door",
            properties -> new DoorBlock(BlockSetType.OAK, properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).noOcclusion());

    public static final DeferredBlock<@NotNull Block> SPRING_TREE_PLANKS_TRAPDOOR = registerBlock("spring_tree_planks_trapdoor",
            properties -> new TrapDoorBlock(BlockSetType.OAK, properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)
                    .noOcclusion()
                    .isRedstoneConductor((state, world, pos) -> false));

    public static final DeferredBlock<@NotNull Block> SPRING_TREE_PLANKS_PRESSURE_PLATE = registerBlock("spring_tree_planks_pressure_plate",
            properties -> new PressurePlateBlock(BlockSetType.OAK, properties), properties -> BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops());
    public static final DeferredBlock<@NotNull Block> SPRING_TREE_PLANKS_BUTTON = registerBlock("spring_tree_planks_button",
            properties -> new ButtonBlock(BlockSetType.OAK, 10, properties), properties -> BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops().noCollision());

    //SUMMER WOOD, PLANKS AND LOGS
    public static final DeferredBlock<@NotNull Block> SUMMER_TREE_SAPLING = registerBlock("summer_tree_sapling",
            SummerTreeSaplingBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_SAPLING));
    public static final DeferredBlock<@NotNull Block> SUMMER_TREE_LOG = registerBlock("summer_tree_log",
            FeyFlammableRotatedPillarBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG));

    public static final DeferredBlock<@NotNull Block> SUMMER_TREE_CRACKED_LOG = registerBlock("summer_tree_cracked_log",
            FeyCrackedLogBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG));

    public static final DeferredBlock<@NotNull Block> SUMMER_TREE_STRIPPED_LOG = registerBlock("summer_tree_stripped_log",
            FeyFlammableRotatedPillarBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG));

    public static final DeferredBlock<@NotNull Block> SUMMER_TREE_WOOD = registerBlock("summer_tree_wood",
            FeyFlammableRotatedPillarBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD));

    public static final DeferredBlock<@NotNull Block> SUMMER_TREE_WOOD_SLAB = registerBlock("summer_tree_wood_slab",
            SlabBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD));

    public static final DeferredBlock<@NotNull Block> SUMMER_TREE_WOOD_STAIRS = registerBlock("summer_tree_wood_stairs",
            properties -> new StairBlock(SUMMER_TREE_WOOD.get().defaultBlockState(), properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD));

    public static final DeferredBlock<@NotNull Block> SUMMER_TREE_WOOD_WALL = registerBlock("summer_tree_wood_wall",
            WallBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD));

    public static final DeferredBlock<@NotNull Block> SUMMER_TREE_STRIPPED_WOOD = registerBlock("summer_tree_stripped_wood",
            FeyFlammableRotatedPillarBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD));

    public static final DeferredBlock<@NotNull Block> SUMMER_TREE_STRIPPED_WOOD_SLAB = registerBlock("summer_tree_stripped_wood_slab",
            SlabBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD));

    public static final DeferredBlock<@NotNull Block> SUMMER_TREE_STRIPPED_WOOD_STAIRS = registerBlock("summer_tree_stripped_wood_stairs",
            properties -> new StairBlock(SUMMER_TREE_STRIPPED_WOOD.get().defaultBlockState(), properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD));

    public static final DeferredBlock<@NotNull Block> SUMMER_TREE_STRIPPED_WOOD_WALL = registerBlock("summer_tree_stripped_wood_wall", WallBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD));

    public static final DeferredBlock<@NotNull Block> SUMMER_TREE_LEAVES_ORANGE = registerBlock("summer_tree_leaves_orange",
            properties -> new FeyLeavesBlock(properties, ModParticles.SUMMER_LEAF_PARTICLE, 14), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES));

    public static final DeferredBlock<@NotNull Block> SUMMER_TREE_LEAVES_YELLOW = registerBlock("summer_tree_leaves_yellow",
            properties -> new FeyLeavesBlock(properties, ModParticles.SUMMER_LEAF_PARTICLE, 14), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES));
    public static final DeferredBlock<@NotNull Block> SUMMER_TREE_PLANKS = registerBlock("summer_tree_planks",
            FeyPlanksBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));

    public static final DeferredBlock<@NotNull Block> SUMMER_TREE_PLANKS_SLAB = registerBlock("summer_tree_planks_slab",
            SlabBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));

    public static final DeferredBlock<@NotNull Block> SUMMER_TREE_PLANKS_STAIRS = registerBlock("summer_tree_planks_stairs",
            properties -> new StairBlock(SUMMER_TREE_PLANKS.get().defaultBlockState(), properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));

    public static final DeferredBlock<@NotNull Block> SUMMER_TREE_PLANKS_FENCE = registerBlock("summer_tree_planks_fence", FenceBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE));

    public static final DeferredBlock<@NotNull Block> SUMMER_TREE_PLANKS_FENCE_GATE = registerBlock("summer_tree_planks_fence_gate",
            properties -> new FenceGateBlock(WoodType.OAK, properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE));

    public static final DeferredBlock<@NotNull Block> SUMMER_TREE_PLANKS_DOOR = registerBlock("summer_tree_planks_door",
            properties -> new DoorBlock(BlockSetType.OAK, properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).noOcclusion());

    public static final DeferredBlock<@NotNull Block> SUMMER_TREE_PLANKS_TRAPDOOR = registerBlock("summer_tree_planks_trapdoor",
            properties -> new TrapDoorBlock(BlockSetType.OAK, properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)
                    .noOcclusion()
                    .isRedstoneConductor((state, world, pos) -> false));

    public static final DeferredBlock<@NotNull Block> SUMMER_TREE_PLANKS_PRESSURE_PLATE = registerBlock("summer_tree_planks_pressure_plate",
            properties -> new PressurePlateBlock(BlockSetType.OAK, properties), properties -> BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops());
    public static final DeferredBlock<@NotNull Block> SUMMER_TREE_PLANKS_BUTTON = registerBlock("summer_tree_planks_button",
            properties -> new ButtonBlock(BlockSetType.OAK, 10, properties), properties -> BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops().noCollision());

    //WINTER WOOD, PLANKS AND LOGS
    public static final DeferredBlock<@NotNull Block> WINTER_TREE_SAPLING = registerBlock("winter_tree_sapling",
            WinterTreeSaplingBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_SAPLING));
    public static final DeferredBlock<@NotNull Block> WINTER_TREE_LOG = registerBlock("winter_tree_log",
            FeyFlammableRotatedPillarBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG));

    public static final DeferredBlock<@NotNull Block> WINTER_TREE_CRACKED_LOG = registerBlock("winter_tree_cracked_log",
            FeyCrackedLogBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG));

    public static final DeferredBlock<@NotNull Block> WINTER_TREE_STRIPPED_LOG = registerBlock("winter_tree_stripped_log",
            FeyFlammableRotatedPillarBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG));

    public static final DeferredBlock<@NotNull Block> WINTER_TREE_WOOD = registerBlock("winter_tree_wood",
            FeyFlammableRotatedPillarBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD));

    public static final DeferredBlock<@NotNull Block> WINTER_TREE_WOOD_SLAB = registerBlock("winter_tree_wood_slab",
            SlabBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD));

    public static final DeferredBlock<@NotNull Block> WINTER_TREE_WOOD_STAIRS = registerBlock("winter_tree_wood_stairs",
            properties -> new StairBlock(WINTER_TREE_WOOD.get().defaultBlockState(), properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD));

    public static final DeferredBlock<@NotNull Block> WINTER_TREE_WOOD_WALL = registerBlock("winter_tree_wood_wall",
            WallBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD));

    public static final DeferredBlock<@NotNull Block> WINTER_TREE_STRIPPED_WOOD = registerBlock("winter_tree_stripped_wood",
            FeyFlammableRotatedPillarBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD));

    public static final DeferredBlock<@NotNull Block> WINTER_TREE_STRIPPED_WOOD_SLAB = registerBlock("winter_tree_stripped_wood_slab",
            SlabBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD));

    public static final DeferredBlock<@NotNull Block> WINTER_TREE_STRIPPED_WOOD_STAIRS = registerBlock("winter_tree_stripped_wood_stairs",
            properties -> new StairBlock(WINTER_TREE_STRIPPED_WOOD.get().defaultBlockState(), properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD));

    public static final DeferredBlock<@NotNull Block> WINTER_TREE_STRIPPED_WOOD_WALL = registerBlock("winter_tree_stripped_wood_wall", WallBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD));

    public static final DeferredBlock<@NotNull Block> WINTER_TREE_LEAVES_LIGHT_BLUE = registerBlock("winter_tree_leaves_light_blue",
            properties -> new FeyLeavesBlock(properties, ModParticles.WINTER_LEAF_PARTICLE, 14), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES));

    public static final DeferredBlock<@NotNull Block> WINTER_TREE_LEAVES_BLUE = registerBlock("winter_tree_leaves_blue",
            properties -> new FeyLeavesBlock(properties, ModParticles.WINTER_LEAF_PARTICLE, 14), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES));

    public static final DeferredBlock<@NotNull Block> WINTER_TREE_PLANKS = registerBlock("winter_tree_planks",
            FeyPlanksBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));

    public static final DeferredBlock<@NotNull Block> WINTER_TREE_PLANKS_SLAB = registerBlock("winter_tree_planks_slab",
            SlabBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));

    public static final DeferredBlock<@NotNull Block> WINTER_TREE_PLANKS_STAIRS = registerBlock("winter_tree_planks_stairs",
            properties -> new StairBlock(WINTER_TREE_PLANKS.get().defaultBlockState(), properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));

    public static final DeferredBlock<@NotNull Block> WINTER_TREE_PLANKS_FENCE = registerBlock("winter_tree_planks_fence", FenceBlock::new, properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE));

    public static final DeferredBlock<@NotNull Block> WINTER_TREE_PLANKS_FENCE_GATE = registerBlock("winter_tree_planks_fence_gate",
            properties -> new FenceGateBlock(WoodType.OAK, properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE));

    public static final DeferredBlock<@NotNull Block> WINTER_TREE_PLANKS_DOOR = registerBlock("winter_tree_planks_door",
            properties -> new DoorBlock(BlockSetType.OAK, properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).noOcclusion());

    public static final DeferredBlock<@NotNull Block> WINTER_TREE_PLANKS_TRAPDOOR = registerBlock("winter_tree_planks_trapdoor",
            properties -> new TrapDoorBlock(BlockSetType.OAK, properties), properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)
                    .noOcclusion()
                    .isRedstoneConductor((state, world, pos) -> false));

    public static final DeferredBlock<@NotNull Block> WINTER_TREE_PLANKS_PRESSURE_PLATE = registerBlock("winter_tree_planks_pressure_plate",
            properties -> new PressurePlateBlock(BlockSetType.OAK, properties), properties -> BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops());
    public static final DeferredBlock<@NotNull Block> WINTER_TREE_PLANKS_BUTTON = registerBlock("winter_tree_planks_button",
            properties -> new ButtonBlock(BlockSetType.OAK, 10, properties), properties -> BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops().noCollision());



    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> block, UnaryOperator<BlockBehaviour.Properties> properties) {
        DeferredBlock<T> toReturn = BLOCKS.registerBlock(name, block, properties);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.registerSimpleBlockItem(name, block);
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}