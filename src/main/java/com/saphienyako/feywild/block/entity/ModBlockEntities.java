package com.saphienyako.feywild.block.entity;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Feywild.MOD_ID);
    @SuppressWarnings("ConstantConditions")
    public static final Supplier<BlockEntityType<@NotNull FeyAltarBlockEntity>> FEY_ALTAR_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("fey_altar_block_entity", () ->
                    new BlockEntityType<>(
                            FeyAltarBlockEntity::new,
                            ModBlocks.FEY_ALTAR.get(),
                            ModBlocks.FEY_ALTAR_QUEEN_TITANIA.get(),
                            ModBlocks.FEY_ALTAR_QUEEN_MAB.get(),
                            ModBlocks.FEY_ALTAR_ASHEN_LORD.get(),
                            ModBlocks.FEY_ALTAR_OBERON.get()));

    public static final Supplier<BlockEntityType<@NotNull FeyCrackedLogBlockEntity>> FEY_CRACKED_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("fey_cracked_block_entity", () ->
                    new BlockEntityType<>(
                            FeyCrackedLogBlockEntity::new,
                            ModBlocks.AUTUMN_TREE_CRACKED_LOG.get(),
                            ModBlocks.SPRING_TREE_CRACKED_LOG.get(),
                            ModBlocks.SUMMER_TREE_CRACKED_LOG.get(),
                            ModBlocks.WINTER_TREE_CRACKED_LOG.get()));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

}
