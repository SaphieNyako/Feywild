package com.saphienyako.feywild.block;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
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
