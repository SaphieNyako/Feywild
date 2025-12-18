package com.saphienyako.feywild.block;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(Feywild.MOD_ID);

    public static final DeferredBlock<Block> FEY_GEM_ORE = registerBlock("fey_gem_ore",
            () -> new Block(BlockBehaviour.Properties.of().strength(4f).explosionResistance(10f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> FEY_GEM_ORE_DEEP_SLATE = registerBlock("fey_gem_ore_deep_slate",
            () -> new Block(BlockBehaviour.Properties.of().strength(4f).explosionResistance(10f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));

    public static final DeferredBlock<Block> MANDRAKE_CROP = BLOCKS.register("mandrake_crop",
            () -> new MandrakeCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noCollission().noOcclusion()));

    public static final DeferredBlock<Block> GIANT_SUN_FLOWER = registerBlock("giant_sun_flower",
            ()-> new SunFlowerBlock(4));

    public static final DeferredBlock<Block> GIANT_CROCUS_FLOWER = registerBlock("giant_crocus_flower",
            ()-> new CrocusFlowerBlock(3));

    public static final DeferredBlock<Block> GIANT_DANDELION_FLOWER = registerBlock("giant_dandelion_flower",
            ()-> new DandelionFlowerBlock(4));

    public static final DeferredBlock<Block> FEY_ALTAR = registerBlock("fey_altar",
            () -> new FeyAltarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(3f, 10f).requiresCorrectToolForDrops().sound(SoundType.STONE).noOcclusion()));


    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
