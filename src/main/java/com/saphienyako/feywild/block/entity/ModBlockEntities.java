package com.saphienyako.feywild.block.entity;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Feywild.MOD_ID);

    public static final RegistryObject<BlockEntityType<FeyAltarBlockEntity>> FEY_ALTAR_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("fey_altar_block_entity", ()->
            BlockEntityType.Builder.of(FeyAltarBlockEntity::new,ModBlocks.FEY_ALTAR.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

}
