package com.feywild.feywild.block;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.decorative.RotatedPillarBlockBase;
import com.feywild.feywild.block.entity.LibraryBell;
import com.feywild.feywild.block.flower.CrocusBlock;
import com.feywild.feywild.block.flower.DandelionBlock;
import com.feywild.feywild.block.flower.GiantFlowerBlock;
import com.feywild.feywild.block.flower.SunflowerBlock;
import com.feywild.feywild.block.portal.FeyPortalBlock;
import io.github.noeppi_noeppi.libx.annotation.registration.RegisterClass;
import io.github.noeppi_noeppi.libx.base.BlockBase;
import io.github.noeppi_noeppi.libx.base.tile.BlockBE;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

@RegisterClass
public class ModBlocks {

    public static final Block feyGemBlock = new BlockBase(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.STONE).strength(3f, 10f)
            .requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final Block feyGemBlockDeepSlate = new BlockBase(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.STONE).strength(3f, 10f)
            .sound(SoundType.DEEPSLATE));
    public static final Block feyGemBlockLivingrock = new BlockBase(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.STONE).strength(3f, 10f)
            .requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final BlockBE<LibraryBell> libraryBell = new LibraryBellBlock(FeywildMod.getInstance());
    public static final GiantFlowerBlock sunflower = new SunflowerBlock(FeywildMod.getInstance());
    public static final GiantFlowerBlock dandelion = new DandelionBlock(FeywildMod.getInstance());
    public static final GiantFlowerBlock crocus = new CrocusBlock(FeywildMod.getInstance());
    public static final DwarvenAnvilBlock dwarvenAnvil = new DwarvenAnvilBlock(FeywildMod.getInstance());
    public static final FeyAltarBlock feyAltar = new FeyAltarBlock(FeywildMod.getInstance());
    public static final MagicalBrazierBlock magicalBrazier = new MagicalBrazierBlock(FeywildMod.getInstance());

    public static final TreeMushroomBlock treeMushroom = new TreeMushroomBlock(FeywildMod.getInstance());
    public static final MandrakeCrop mandrakeCrop = new MandrakeCrop(FeywildMod.getInstance());
    public static final AncientRunestoneBlock ancientRunestone = new AncientRunestoneBlock(FeywildMod.getInstance());
    public static final DisplayGlassBlock displayGlass = new DisplayGlassBlock(FeywildMod.getInstance());
    public static final FeyMushroomBlock feyMushroom = new FeyMushroomBlock(FeywildMod.getInstance());

    public static final Block elvenQuartz = new ElvenQuartzBlock(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.STONE).strength(2f, 5f)
            .requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final Block elvenQuartzBrick = new ElvenQuartzBlock(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.STONE).strength(2f, 5f)
            .requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final Block elvenQuartzMossyBrick = new BlockBase(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.STONE).strength(2f, 5f)
            .requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final Block elvenQuartzCrackedBrick = new BlockBase(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.STONE).strength(2f, 5f)
            .requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final Block elvenQuartzPillar = new RotatedPillarBlockBase(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.STONE).strength(2f, 5f)
            .requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final Block elvenQuartzPolished = new ElvenQuartzBlock(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.STONE).strength(2f, 5f)
            .requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final Block elvenAutumnQuartz = new ElvenQuartzBlock(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.STONE).strength(2f, 5f)
            .requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final Block elvenAutumnQuartzBrick = new ElvenQuartzBlock(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.STONE).strength(2f, 5f)
            .requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final Block elvenAutumnQuartzMossyBrick = new BlockBase(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.STONE).strength(2f, 5f)
            .requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final Block elvenAutumnQuartzCrackedBrick = new BlockBase(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.STONE).strength(2f, 5f)
            .requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final Block elvenAutumnQuartzPillar = new BlockBase(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.STONE).strength(2f, 5f)
            .requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final Block elvenAutumnQuartzPolished = new ElvenQuartzBlock(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.STONE).strength(2f, 5f)
            .requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final Block elvenSpringQuartz = new ElvenQuartzBlock(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.STONE).strength(2f, 5f)
            .requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final Block elvenSpringQuartzBrick = new ElvenQuartzBlock(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.STONE).strength(2f, 5f)
            .requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final Block elvenSpringQuartzMossyBrick = new BlockBase(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.STONE).strength(2f, 5f)
            .requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final Block elvenSpringQuartzCrackedBrick = new BlockBase(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.STONE).strength(2f, 5f)
            .requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final Block elvenSpringQuartzPillar = new BlockBase(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.STONE).strength(2f, 5f)
            .requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final Block elvenSpringQuartzPolished = new ElvenQuartzBlock(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.STONE).strength(2f, 5f)
            .requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final Block elvenSummerQuartz = new ElvenQuartzBlock(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.STONE).strength(2f, 5f)
            .requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final Block elvenSummerQuartzBrick = new ElvenQuartzBlock(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.STONE).strength(2f, 5f)
            .requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final Block elvenSummerQuartzMossyBrick = new BlockBase(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.STONE).strength(2f, 5f)
            .requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final Block elvenSummerQuartzCrackedBrick = new BlockBase(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.STONE).strength(2f, 5f)
            .requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final Block elvenSummerQuartzPillar = new BlockBase(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.STONE).strength(2f, 5f)
            .requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final Block elvenSummerQuartzPolished = new ElvenQuartzBlock(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.STONE).strength(2f, 5f)
            .requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final Block elvenWinterQuartz = new ElvenQuartzBlock(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.STONE).strength(2f, 5f)
            .requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final Block elvenWinterQuartzBrick = new ElvenQuartzBlock(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.STONE).strength(2f, 5f)
            .requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final Block elvenWinterQuartzMossyBrick = new BlockBase(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.STONE).strength(2f, 5f)
            .requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final Block elvenWinterQuartzCrackedBrick = new BlockBase(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.STONE).strength(2f, 5f)
            .requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final Block elvenWinterQuartzPillar = new BlockBase(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.STONE).strength(2f, 5f)
            .requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final Block elvenWinterQuartzPolished = new ElvenQuartzBlock(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.STONE).strength(2f, 5f)
            .requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final Block feyStarBlockGreen = new BlockBase(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.SAND).strength(2f, 2f).sound(SoundType.SAND));
    public static final Block feyStarBlockLightBlue = new BlockBase(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.SAND).strength(2f, 2f).sound(SoundType.SAND));
    public static final Block feyStarBlockBlue = new BlockBase(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.SAND).strength(2f, 2f).sound(SoundType.SAND));
    public static final Block feyStarBlockPurple = new BlockBase(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.SAND).strength(2f, 2f).sound(SoundType.SAND));
    public static final Block feyStarBlockPink = new BlockBase(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.SAND).strength(2f, 2f).sound(SoundType.SAND));
    public static final Block feyStarBlockOrange = new BlockBase(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.SAND).strength(2f, 2f).sound(SoundType.SAND));
    public static final Block feyStarBlockYellow = new BlockBase(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.SAND).strength(2f, 2f).sound(SoundType.SAND));

    /* TEMPORARY BLOCKS PLACEHOLDERS */
    public static final Block feyPortalBlock = new FeyPortalBlock();
    public static final GrassBlock snowyGrassBlock = new GrassBlock(BlockBehaviour.Properties.of(Material.GRASS));
}
