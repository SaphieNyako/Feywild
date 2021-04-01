package com.feywild.feywild.block;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.entity.FeyAltarBlockEntity;
import com.feywild.feywild.util.Registration;
import com.mojang.datafixers.types.Type;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class ModBlocks {

    public static final RegistryObject<Block> FEY_GEM_BLOCK = register("fey_gem_block",
            ()-> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .hardnessAndResistance(3f,10f)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().sound(SoundType.STONE)), true);

    // passing a new instance of the class DwarvenAnvil instead of creating a new block instance here
    public static final RegistryObject<Block> DWARVEN_ANVIL = register ("dwarven_anvil", DwarvenAnvil::new, true);



    public static final RegistryObject<Block> FEY_ALTAR = register("fey_altar", FeyAltar::new, true);
    //Properties in FeyAltar

    public static final RegistryObject<TileEntityType<FeyAltarBlockEntity>> FEY_ALTAR_ENTITY = registerTile("fey_altar_entity",
            () ->TileEntityType.Builder.create(FeyAltarBlockEntity::new, FEY_ALTAR.get()).build(null));

    public static final RegistryObject<Block> MANDRAKE_CROP=
            Registration.BLOCKS.register("mandrake_crop",
                    () -> new MandrakeCrop(AbstractBlock.Properties.from(Blocks.WHEAT)));


    public static void register() {}

    private static <T extends Block>RegistryObject<T> register(String name, Supplier<T> block, boolean createItem){
        RegistryObject<T> toReturn = Registration.BLOCKS.register(name,block);
        if(createItem)
        Registration.ITEMS.register(name, ()-> new BlockItem(toReturn.get(),
                new Item.Properties().group(FeywildMod.FEYWILD_TAB)));

        return toReturn;
    }
    //for tile entities !!! you must pass a TileEntityType.Builder
    private static <T extends TileEntityType<?>>RegistryObject<T> registerTile(String name, Supplier<T> type){
        return Registration.TILE_ENTITY_TYPES.register(name,type);
    }

}
