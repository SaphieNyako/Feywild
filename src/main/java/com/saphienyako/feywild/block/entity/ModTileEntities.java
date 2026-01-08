package com.saphienyako.feywild.block.entity;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.block.ModBlocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntities {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES =
            DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Feywild.MOD_ID);

    public static final RegistryObject<TileEntityType<FeyAltarBlockEntity>> FEY_ALTAR_BLOCK_ENTITY =
            TILE_ENTITIES.register("fey_altar_block_entity", ()->
            TileEntityType.Builder.of(FeyAltarBlockEntity::new,ModBlocks.FEY_ALTAR.get()).build(null));

    public static void register(IEventBus eventBus) {
        TILE_ENTITIES.register(eventBus);
    }

}
