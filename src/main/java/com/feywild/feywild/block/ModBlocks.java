package com.feywild.feywild.block;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.entity.DwarvenAnvilEntity;
import com.feywild.feywild.block.entity.FeyAltarBlockEntity;
import com.feywild.feywild.block.entity.LibraryBellEntity;
import com.feywild.feywild.block.flower.Crocus;
import com.feywild.feywild.block.flower.Dandelion;
import com.feywild.feywild.block.flower.GiantFlowerBlock;
import com.feywild.feywild.block.flower.Sunflower;
import com.feywild.feywild.block.trees.*;
import com.feywild.feywild.util.Registration;
import io.github.noeppi_noeppi.libx.annotation.RegisterClass;
import io.github.noeppi_noeppi.libx.mod.registration.BlockBase;
import io.github.noeppi_noeppi.libx.mod.registration.BlockTE;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

@RegisterClass
public class ModBlocks {

    public static final Block feyGemBlock = new BlockBase(FeywildMod.getInstance(), AbstractBlock.Properties.of(Material.STONE).strength(3f, 10f)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.STONE));
    
    public static final BlockTE<LibraryBellEntity> libraryBell = new LibraryBell(FeywildMod.getInstance());
    public static final GiantFlowerBlock sunflower = new Sunflower(FeywildMod.getInstance());
    public static final GiantFlowerBlock dandelion = new Dandelion(FeywildMod.getInstance());
    public static final GiantFlowerBlock crocus = new Crocus(FeywildMod.getInstance());
    
    public static final DwarvenAnvil dwarvenAnvil = new DwarvenAnvil(FeywildMod.getInstance());
    public static final FeyAltar feyAltar = new FeyAltar(FeywildMod.getInstance());
    
    public static final TreeMushroomBlock treeMushroom = new TreeMushroomBlock(FeywildMod.getInstance());
    public static final MandrakeCrop mandrakeCrop = new MandrakeCrop(FeywildMod.getInstance());
}
