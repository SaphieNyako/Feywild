package com.feywild.feywild.block;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.decoration.ElvenQuartzBlock;
import com.feywild.feywild.block.entity.LibraryBell;
import com.feywild.feywild.block.flower.CrocusBlock;
import com.feywild.feywild.block.flower.DandelionBlock;
import com.feywild.feywild.block.flower.GiantFlowerBlock;
import com.feywild.feywild.block.flower.SunflowerBlock;
import com.feywild.feywild.block.trees.FeyLeavesBlock;
import com.feywild.feywild.particles.ModParticles;
import com.feywild.feywild.quest.Alignment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.moddingx.libx.annotation.registration.RegisterClass;
import org.moddingx.libx.base.BlockBase;
import org.moddingx.libx.base.tile.BlockBE;

@RegisterClass(registry = "BLOCK_REGISTRY")
public class ModBlocks {

    public static final Block feyGemOre = new BlockBase(FeywildMod.getInstance(), BlockBehaviour.Properties.copy(Blocks.STONE).strength(3f, 10f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final Block feyGemOreDeepSlate = new BlockBase(FeywildMod.getInstance(), BlockBehaviour.Properties.copy(Blocks.STONE).strength(3f, 10f).sound(SoundType.DEEPSLATE));
    public static final Block feyGemOreLivingrock = new BlockBase(FeywildMod.getInstance(), BlockBehaviour.Properties.copy(Blocks.STONE).strength(3f, 10f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final BlockBE<LibraryBell> libraryBell = new LibraryBellBlock(FeywildMod.getInstance());
    public static final GiantFlowerBlock sunflower = new SunflowerBlock(FeywildMod.getInstance());
    public static final GiantFlowerBlock dandelion = new DandelionBlock(FeywildMod.getInstance());
    public static final GiantFlowerBlock crocus = new CrocusBlock(FeywildMod.getInstance());
    public static final DwarvenAnvilBlock dwarvenAnvil = new DwarvenAnvilBlock(FeywildMod.getInstance());

    public static final FeyAltarBlock summerFeyAltar = new FeyAltarBlock(FeywildMod.getInstance(), Alignment.SUMMER);
    public static final FeyAltarBlock winterFeyAltar = new FeyAltarBlock(FeywildMod.getInstance(), Alignment.WINTER);
    public static final FeyAltarBlock autumnFeyAltar = new FeyAltarBlock(FeywildMod.getInstance(), Alignment.AUTUMN);

    public static final MagicalBrazierBlock magicalBrazier = new MagicalBrazierBlock(FeywildMod.getInstance());

    public static final TreeMushroomBlock treeMushroom = new TreeMushroomBlock(FeywildMod.getInstance());
    public static final MandrakeCrop mandrakeCrop = new MandrakeCrop();
    public static final AncientRunestoneBlock ancientRunestone = new AncientRunestoneBlock(FeywildMod.getInstance());
    public static final DisplayGlassBlock displayGlass = new DisplayGlassBlock(FeywildMod.getInstance());
    public static final FeyMushroomBlock feyMushroom = new FeyMushroomBlock();

    public static final ElvenQuartzBlock elvenQuartz = new ElvenQuartzBlock(FeywildMod.getInstance(), BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final ElvenQuartzBlock elvenSpringQuartz = new ElvenQuartzBlock(FeywildMod.getInstance(), BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final ElvenQuartzBlock elvenSummerQuartz = new ElvenQuartzBlock(FeywildMod.getInstance(), BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final ElvenQuartzBlock elvenAutumnQuartz = new ElvenQuartzBlock(FeywildMod.getInstance(), BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final ElvenQuartzBlock elvenWinterQuartz = new ElvenQuartzBlock(FeywildMod.getInstance(), BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f, 5f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final Block feyStarBlockGreen = new BlockBase(FeywildMod.getInstance(), BlockBehaviour.Properties.copy(Blocks.STONE).strength(1f, 3f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST).lightLevel(value -> 14));
    public static final Block feyStarBlockLightBlue = new BlockBase(FeywildMod.getInstance(), BlockBehaviour.Properties.copy(Blocks.STONE).strength(1f, 3f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST).lightLevel(value -> 14));
    public static final Block feyStarBlockBlue = new BlockBase(FeywildMod.getInstance(), BlockBehaviour.Properties.copy(Blocks.STONE).strength(1f, 3f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST).lightLevel(value -> 14));
    public static final Block feyStarBlockPurple = new BlockBase(FeywildMod.getInstance(), BlockBehaviour.Properties.copy(Blocks.STONE).strength(1f, 3f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST).lightLevel(value -> 14));
    public static final Block feyStarBlockPink = new BlockBase(FeywildMod.getInstance(), BlockBehaviour.Properties.copy(Blocks.STONE).strength(1f, 3f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST).lightLevel(value -> 14));
    public static final Block feyStarBlockOrange = new BlockBase(FeywildMod.getInstance(), BlockBehaviour.Properties.copy(Blocks.STONE).strength(1f, 3f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST).lightLevel(value -> 14));
    public static final Block feyStarBlockYellow = new BlockBase(FeywildMod.getInstance(), BlockBehaviour.Properties.copy(Blocks.STONE).strength(1f, 3f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST).lightLevel(value -> 14));
}
