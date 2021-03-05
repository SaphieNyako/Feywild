package com.feywild.feywild.block;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.util.Registration;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static final RegistryObject<Block> FEY_GEM_BLOCK = register("fey_gem_block",
            ()-> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .hardnessAndResistance(3f,10f)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)));

    public static void register() {}

    //Method
    private static <T extends Block>RegistryObject<T> register(String name, Supplier<T> block){
        RegistryObject<T> toReturn = Registration.BLOCKS.register(name,block);
        Registration.ITEMS.register(name, ()-> new BlockItem(toReturn.get(),
                new Item.Properties().group(FeywildMod.FEYWILD_TAB)));

        return toReturn;
    }


}
