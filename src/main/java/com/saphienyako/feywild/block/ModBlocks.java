package com.saphienyako.feywild.block;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.item.ModCreativeModeTab;
import com.saphienyako.feywild.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Feywild.MOD_ID);

    public static final RegistryObject<Block> FEY_GEM_ORE = registerBlockAndItem("fey_gem_ore",
            () -> new Block(Block.Properties.copy(Blocks.STONE).strength(3f, 10f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    //No Deepslate in 1.16


    public static final RegistryObject<Block> GIANT_SUN_FLOWER = registerBlock("giant_sun_flower",
            ()-> new SunFlowerBlock(4));

    public static final RegistryObject<Block> GIANT_CROCUS_FLOWER = registerBlock("giant_crocus_flower",
            ()-> new CrocusFlowerBlock(3));

    public static final RegistryObject<Block> GIANT_DANDELION_FLOWER = registerBlock("giant_dandelion_flower",
            ()-> new DandelionFlowerBlock(4));

    public static final RegistryObject<Block> MANDRAKE_CROP = BLOCKS.register("mandrake_crop",
            ()-> new MandrakeCropBlock(Block.Properties.copy(Blocks.WHEAT).noCollission().noOcclusion()));

    public static final RegistryObject<Block> FEY_ALTAR = registerBlockAndItem("fey_altar",
            () -> new FeyAltarBlock(Block.Properties.copy(Blocks.STONE).strength(3f, 10f).requiresCorrectToolForDrops().sound(SoundType.STONE).noOcclusion()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    private static<T extends Block> RegistryObject<T> registerBlockAndItem(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(ModCreativeModeTab.FEYWILD_TAB)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
