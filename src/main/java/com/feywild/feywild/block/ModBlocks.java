package com.feywild.feywild.block;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.decorative.FenceBlockBase;
import com.feywild.feywild.block.decorative.FenceGateBlockBase;
import com.feywild.feywild.block.decorative.SlabBlockBase;
import com.feywild.feywild.block.decorative.StairsBlockBase;
import com.feywild.feywild.block.entity.LibraryBell;
import com.feywild.feywild.block.flower.CrocusBlock;
import com.feywild.feywild.block.flower.DandelionBlock;
import com.feywild.feywild.block.flower.GiantFlowerBlock;
import com.feywild.feywild.block.flower.SunflowerBlock;
import io.github.noeppi_noeppi.libx.annotation.registration.RegisterClass;
import io.github.noeppi_noeppi.libx.base.BlockBase;
import io.github.noeppi_noeppi.libx.base.tile.BlockBE;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.ToolType;

@RegisterClass
public class ModBlocks {

    public static final Block feyGemBlock = new BlockBase(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.STONE).strength(3f, 10f)
            .harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final Block feyGemBlockLivingrock = new BlockBase(FeywildMod.getInstance(), BlockBehaviour.Properties.of(Material.STONE).strength(3f, 10f)
            .harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final BlockBE<LibraryBell> libraryBell = new LibraryBellBlock(FeywildMod.getInstance());
    public static final GiantFlowerBlock sunflower = new SunflowerBlock(FeywildMod.getInstance());
    public static final GiantFlowerBlock dandelion = new DandelionBlock(FeywildMod.getInstance());
    public static final GiantFlowerBlock crocus = new CrocusBlock(FeywildMod.getInstance());

    public static final DwarvenAnvilBlock dwarvenAnvil = new DwarvenAnvilBlock(FeywildMod.getInstance());
    public static final FeyAltarBlock feyAltar = new FeyAltarBlock(FeywildMod.getInstance());

    public static final TreeMushroomBlock treeMushroom = new TreeMushroomBlock(FeywildMod.getInstance());
    public static final MandrakeCrop mandrakeCrop = new MandrakeCrop(FeywildMod.getInstance());

    public static final AncientRunestoneBlock ancientRunestone = new AncientRunestoneBlock(FeywildMod.getInstance());

    public static final DisplayGlassBlock displayGlass = new DisplayGlassBlock(FeywildMod.getInstance());

    /* DECORATIVE BLOCKS - comment out when running runData */

    public static final Block autumnTreeStairs = new StairsBlockBase(FeywildMod.getInstance(),
            () -> ModTrees.autumnTree.getWoodBlock().defaultBlockState(), BlockBehaviour.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3));

    public static final Block springTreeStairs = new StairsBlockBase(FeywildMod.getInstance(),
            () -> ModTrees.springTree.getWoodBlock().defaultBlockState(), BlockBehaviour.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3));

    public static final Block summerTreeStairs = new StairsBlockBase(FeywildMod.getInstance(),
            () -> ModTrees.summerTree.getWoodBlock().defaultBlockState(), BlockBehaviour.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3));

    public static final Block winterTreeStairs = new StairsBlockBase(FeywildMod.getInstance(),
            () -> ModTrees.winterTree.getWoodBlock().defaultBlockState(), BlockBehaviour.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3));

    public static final Block autumnTreeSlab = new SlabBlockBase(FeywildMod.getInstance(),
            BlockBehaviour.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3));

    public static final Block springTreeSlab = new SlabBlockBase(FeywildMod.getInstance(),
            BlockBehaviour.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3));

    public static final Block summerTreeSlab = new SlabBlockBase(FeywildMod.getInstance(),
            BlockBehaviour.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3));

    public static final Block winterTreeSlab = new SlabBlockBase(FeywildMod.getInstance(),
            BlockBehaviour.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3));

    public static final Block autumnTreeFence = new FenceBlockBase(FeywildMod.getInstance(),
            BlockBehaviour.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3));

    public static final Block springTreeFence = new FenceBlockBase(FeywildMod.getInstance(),
            BlockBehaviour.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3));

    public static final Block summerTreeFence = new FenceBlockBase(FeywildMod.getInstance(),
            BlockBehaviour.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3));

    public static final Block winterTreeFence = new FenceBlockBase(FeywildMod.getInstance(),
            BlockBehaviour.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3));

    public static final Block autumnTreeFenceGate = new FenceGateBlockBase(FeywildMod.getInstance(),
            BlockBehaviour.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3));

    public static final Block springTreeFenceGate = new FenceGateBlockBase(FeywildMod.getInstance(),
            BlockBehaviour.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3));

    public static final Block summerTreeFenceGate = new FenceGateBlockBase(FeywildMod.getInstance(),
            BlockBehaviour.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3));

    public static final Block winterTreeFenceGate = new FenceGateBlockBase(FeywildMod.getInstance(),
            BlockBehaviour.Properties.of(Material.WOOD).harvestLevel(3).harvestTool(ToolType.AXE).strength(3));

}
