package com.feywild.feywild.util;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.recipes.ModRecipeTypes;
import com.feywild.feywild.world.structure.ModStructures;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.particles.ParticleType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

// TODO remove
public class Registration {

    public static final DeferredRegister<Block> BLOCKS
            = DeferredRegister.create(ForgeRegistries.BLOCKS, FeywildMod.getInstance().modid);

    public static final DeferredRegister<ParticleType<?>> PARTICLES
            = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES,FeywildMod.getInstance().modid);

    public static final DeferredRegister<Effect> EFFECTS
            = DeferredRegister.create(ForgeRegistries.POTIONS,FeywildMod.getInstance().modid);

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS
            = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, FeywildMod.getInstance().modid);

    public static final DeferredRegister<Item> ITEMS
            = DeferredRegister.create(ForgeRegistries.ITEMS, FeywildMod.getInstance().modid);

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES
            = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, FeywildMod.getInstance().modid);

    public static final DeferredRegister<EntityType<?>> ENTITIES
            = DeferredRegister.create(ForgeRegistries.ENTITIES, FeywildMod.getInstance().modid);

    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZER
            = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, FeywildMod.getInstance().modid);

    public static final DeferredRegister<Biome> BIOMES
            = DeferredRegister.create(ForgeRegistries.BIOMES, FeywildMod.getInstance().modid);

    public static final DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDERS
            = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, FeywildMod.getInstance().modid);

    public static final DeferredRegister<ContainerType<?>> CONTAINERS
            = DeferredRegister.create(ForgeRegistries.CONTAINERS, FeywildMod.getInstance().modid);

    public static void init() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(eventBus);
        TILE_ENTITY_TYPES.register(eventBus);
        CONTAINERS.register(eventBus);
        ENTITIES.register(eventBus);
        SOUND_EVENTS.register(eventBus);
        ITEMS.register(eventBus);
        RECIPE_SERIALIZER.register(eventBus);
        ModRecipeTypes.registerRecipes();
        SURFACE_BUILDERS.register(eventBus);
        BIOMES.register(eventBus);
        PARTICLES.register(eventBus);
        EFFECTS.register(eventBus);
    }

}
