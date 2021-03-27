package com.feywild.feywild.util;

import com.feywild.feywild.FeywildMod;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Registration {


    //Letting Forge know which entities we want to be registered.

    public static final DeferredRegister<Block> BLOCKS
            = DeferredRegister.create(ForgeRegistries.BLOCKS, FeywildMod.MOD_ID);

    public static final DeferredRegister<Item> ITEMS
            = DeferredRegister.create(ForgeRegistries.ITEMS, FeywildMod.MOD_ID);

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES
            = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, FeywildMod.MOD_ID);

    public static final DeferredRegister<EntityType<?>> ENTITIES
            = DeferredRegister.create(ForgeRegistries.ENTITIES, FeywildMod.MOD_ID);

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS
            = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, FeywildMod.MOD_ID);


    public static void init()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        TILE_ENTITY_TYPES.register(eventBus);
        ENTITIES.register(eventBus);
        SOUND_EVENTS.register(eventBus);

    }

}
