package com.saphienyako.feywild.block.entity;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Feywild.MOD_ID);

    public static final Supplier<BlockEntityType<FeyAltarBlockEntity>> FEY_ALTAR_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("fey_altar_block_entity", () -> BlockEntityType.Builder.of(
                   FeyAltarBlockEntity::new, ModBlocks.FEY_ALTAR.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

}
