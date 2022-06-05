package com.feywild.feywild.block;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.decorative.*;
import com.feywild.feywild.block.entity.LibraryBell;
import com.feywild.feywild.block.flower.CrocusBlock;
import com.feywild.feywild.block.flower.DandelionBlock;
import com.feywild.feywild.block.flower.GiantFlowerBlock;
import com.feywild.feywild.block.flower.SunflowerBlock;
import io.github.noeppi_noeppi.libx.annotation.RegisterClass;
import io.github.noeppi_noeppi.libx.mod.registration.BlockBase;
import io.github.noeppi_noeppi.libx.mod.registration.BlockTE;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

@RegisterClass
public class ModBlocks {

    public static final Block feyGemBlock = new BlockBase(FeywildMod.getInstance(), AbstractBlock.Properties.of(Material.STONE).strength(3f, 10f)
            .harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final Block feyGemBlockLivingrock = new BlockBase(FeywildMod.getInstance(), AbstractBlock.Properties.of(Material.STONE).strength(3f, 10f)
            .harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final BlockTE<LibraryBell> libraryBell = new LibraryBellBlock(FeywildMod.getInstance());
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

    /* DECORATIVE BLOCKS - comment out when running runData */

    public static final Block autumnStrippedLog = new RotatedPillarBlockBase(FeywildMod.getInstance(), AbstractBlock.Properties.copy(Blocks.STRIPPED_JUNGLE_LOG).sound(SoundType.WOOD));

    public static final Block springStrippedLog = new RotatedPillarBlockBase(FeywildMod.getInstance(), AbstractBlock.Properties.copy(Blocks.STRIPPED_JUNGLE_LOG).sound(SoundType.WOOD));

    public static final Block summerStrippedLog = new RotatedPillarBlockBase(FeywildMod.getInstance(), AbstractBlock.Properties.copy(Blocks.STRIPPED_JUNGLE_LOG).sound(SoundType.WOOD));

    public static final Block winterStrippedLog = new RotatedPillarBlockBase(FeywildMod.getInstance(), AbstractBlock.Properties.copy(Blocks.STRIPPED_JUNGLE_LOG).sound(SoundType.WOOD));

    public static final Block autumnStrippedWood = new RotatedPillarBlockBase(FeywildMod.getInstance(), AbstractBlock.Properties.copy(Blocks.STRIPPED_JUNGLE_WOOD).sound(SoundType.WOOD));

    public static final Block springStrippedWood = new RotatedPillarBlockBase(FeywildMod.getInstance(), AbstractBlock.Properties.copy(Blocks.STRIPPED_JUNGLE_WOOD).sound(SoundType.WOOD));

    public static final Block summerStrippedWood = new RotatedPillarBlockBase(FeywildMod.getInstance(), AbstractBlock.Properties.copy(Blocks.STRIPPED_JUNGLE_WOOD).sound(SoundType.WOOD));

    public static final Block winterStrippedWood = new RotatedPillarBlockBase(FeywildMod.getInstance(), AbstractBlock.Properties.copy(Blocks.STRIPPED_JUNGLE_WOOD).sound(SoundType.WOOD));

    public static final Block autumnPlanks = new BlockBase(FeywildMod.getInstance(), AbstractBlock.Properties.copy(Blocks.JUNGLE_PLANKS).sound(SoundType.WOOD));

    public static final Block springPlanks = new BlockBase(FeywildMod.getInstance(), AbstractBlock.Properties.copy(Blocks.JUNGLE_PLANKS).sound(SoundType.WOOD));

    public static final Block summerPlanks = new BlockBase(FeywildMod.getInstance(), AbstractBlock.Properties.copy(Blocks.JUNGLE_PLANKS).sound(SoundType.WOOD));

    public static final Block winterPlanks = new BlockBase(FeywildMod.getInstance(), AbstractBlock.Properties.copy(Blocks.JUNGLE_PLANKS).sound(SoundType.WOOD));

    // TREE WOOD DECORATIONS

    public static final Block autumnTreeStairs = new StairsBlockBase(FeywildMod.getInstance(),
            () -> ModTrees.autumnTree.getWoodBlock().defaultBlockState(), AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block springTreeStairs = new StairsBlockBase(FeywildMod.getInstance(),
            () -> ModTrees.springTree.getWoodBlock().defaultBlockState(), AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block summerTreeStairs = new StairsBlockBase(FeywildMod.getInstance(),
            () -> ModTrees.summerTree.getWoodBlock().defaultBlockState(), AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block winterTreeStairs = new StairsBlockBase(FeywildMod.getInstance(),
            () -> ModTrees.winterTree.getWoodBlock().defaultBlockState(), AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block autumnTreeSlab = new SlabBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block springTreeSlab = new SlabBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block summerTreeSlab = new SlabBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block winterTreeSlab = new SlabBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block autumnTreeFence = new FenceBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block springTreeFence = new FenceBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block summerTreeFence = new FenceBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block winterTreeFence = new FenceBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block autumnTreeFenceGate = new FenceGateBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block springTreeFenceGate = new FenceGateBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block summerTreeFenceGate = new FenceGateBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block winterTreeFenceGate = new FenceGateBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    // PLANKS DECORATION

    public static final Block autumnPlanksStairs = new StairsBlockBase(FeywildMod.getInstance(),
            () -> ModTrees.autumnTree.getWoodBlock().defaultBlockState(), AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block springPlanksStairs = new StairsBlockBase(FeywildMod.getInstance(),
            () -> ModTrees.springTree.getWoodBlock().defaultBlockState(), AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block summerPlanksStairs = new StairsBlockBase(FeywildMod.getInstance(),
            () -> ModTrees.summerTree.getWoodBlock().defaultBlockState(), AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block winterPlanksStairs = new StairsBlockBase(FeywildMod.getInstance(),
            () -> ModTrees.winterTree.getWoodBlock().defaultBlockState(), AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block autumnPlanksSlab = new SlabBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block springPlanksSlab = new SlabBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block summerPlanksSlab = new SlabBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block winterPlanksSlab = new SlabBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block autumnPlanksFence = new FenceBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block springPlanksFence = new FenceBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block summerPlanksFence = new FenceBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block winterPlanksFence = new FenceBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block autumnPlanksFenceGate = new FenceGateBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block springPlanksFenceGate = new FenceGateBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block summerPlanksFenceGate = new FenceGateBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block winterPlanksFenceGate = new FenceGateBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    // STRIPPED WOOD DECORATION

    public static final Block autumnStrippedWoodStairs = new StairsBlockBase(FeywildMod.getInstance(),
            () -> ModTrees.autumnTree.getWoodBlock().defaultBlockState(), AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block springStrippedWoodStairs = new StairsBlockBase(FeywildMod.getInstance(),
            () -> ModTrees.springTree.getWoodBlock().defaultBlockState(), AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block summerStrippedWoodStairs = new StairsBlockBase(FeywildMod.getInstance(),
            () -> ModTrees.summerTree.getWoodBlock().defaultBlockState(), AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block winterStrippedWoodStairs = new StairsBlockBase(FeywildMod.getInstance(),
            () -> ModTrees.winterTree.getWoodBlock().defaultBlockState(), AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block autumnStrippedWoodSlab = new SlabBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block springStrippedWoodSlab = new SlabBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block summerStrippedWoodSlab = new SlabBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block winterStrippedWoodSlab = new SlabBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block autumnStrippedWoodFence = new FenceBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block springStrippedWoodFence = new FenceBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block summerStrippedWoodFence = new FenceBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block winterStrippedWoodFence = new FenceBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block autumnStrippedWoodFenceGate = new FenceGateBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block springStrippedWoodFenceGate = new FenceGateBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block summerStrippedWoodFenceGate = new FenceGateBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).sound(SoundType.WOOD));

    public static final Block winterStrippedWoodFenceGate = new FenceGateBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).noCollission().sound(SoundType.WOOD));

    // DOORS

    public static final Block autumnDoor = new DoorBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).noOcclusion().sound(SoundType.WOOD));

    public static final Block autumnTrapdoor = new TrapDoorBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).noOcclusion().sound(SoundType.WOOD));

    public static final Block springDoor = new DoorBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).noOcclusion().sound(SoundType.WOOD));

    public static final Block springTrapdoor = new TrapDoorBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).noOcclusion().sound(SoundType.WOOD));

    public static final Block summerDoor = new DoorBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).noOcclusion().sound(SoundType.WOOD));

    public static final Block summerTrapdoor = new TrapDoorBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).noOcclusion().sound(SoundType.WOOD));

    public static final Block winterDoor = new DoorBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).noOcclusion().sound(SoundType.WOOD));

    public static final Block winterTrapdoor = new TrapDoorBlockBase(FeywildMod.getInstance(),
            AbstractBlock.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3).noOcclusion().sound(SoundType.WOOD));

}
