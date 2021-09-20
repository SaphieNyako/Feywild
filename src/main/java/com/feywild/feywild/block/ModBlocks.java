package com.feywild.feywild.block;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.entity.LibraryBell;
import com.feywild.feywild.block.flower.CrocusBlock;
import com.feywild.feywild.block.flower.DandelionBlock;
import com.feywild.feywild.block.flower.GiantFlowerBlock;
import com.feywild.feywild.block.flower.SunflowerBlock;
import com.feywild.feywild.block.runestone.AncientRunestoneBlock;
import io.github.noeppi_noeppi.libx.annotation.RegisterClass;
import io.github.noeppi_noeppi.libx.mod.registration.BlockBase;
import io.github.noeppi_noeppi.libx.mod.registration.BlockTE;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
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

    public static final TreeMushroomBlock treeMushroom = new TreeMushroomBlock(FeywildMod.getInstance());
    public static final MandrakeCrop mandrakeCrop = new MandrakeCrop(FeywildMod.getInstance());

    public static final AncientRunestoneBlock ancientRunestone = new AncientRunestoneBlock(FeywildMod.getInstance());

    public static final DisplayGlassBlock displayGlass = new DisplayGlassBlock(FeywildMod.getInstance());
}
