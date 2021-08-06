package com.feywild.feywild.world;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.world.structure.ModStructures;
import com.mojang.serialization.Codec;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class StructureLoader {

    public static void addStructureSettings(final WorldEvent.Load event) {
        if (event.getWorld() instanceof ServerWorld) {
            ServerWorld world = (ServerWorld) event.getWorld();

            if (world.getChunkSource().generator instanceof FlatChunkGenerator && world.dimension().equals(World.OVERWORLD)) {
                return;
            }
            
            try {
                Method method = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "func_230347_a_");
                method.setAccessible(true);
                //noinspection unchecked
                ResourceLocation generatorId = Registry.CHUNK_GENERATOR.getKey((Codec<? extends ChunkGenerator>) method.invoke(world.getChunkSource().generator));
                if (generatorId != null && generatorId.getNamespace().equals("terraforged")) return;
            } catch (Exception e) {
                FeywildMod.getInstance().logger.error("Was unable to check if " + world.dimension().location() + " is using Terraforged's ChunkGenerator.");
            }
            
            Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(world.getChunkSource().generator.getSettings().structureConfig());
            tempMap.putIfAbsent(ModStructures.springWorldTree, DimensionStructuresSettings.DEFAULTS.get(ModStructures.springWorldTree));
            tempMap.putIfAbsent(ModStructures.summerWorldTree, DimensionStructuresSettings.DEFAULTS.get(ModStructures.summerWorldTree));
            tempMap.putIfAbsent(ModStructures.autumnWorldTree, DimensionStructuresSettings.DEFAULTS.get(ModStructures.autumnWorldTree));
            tempMap.putIfAbsent(ModStructures.winterWorldTree, DimensionStructuresSettings.DEFAULTS.get(ModStructures.autumnWorldTree));
            tempMap.putIfAbsent(ModStructures.blacksmith, DimensionStructuresSettings.DEFAULTS.get(ModStructures.blacksmith));
            tempMap.putIfAbsent(ModStructures.library, DimensionStructuresSettings.DEFAULTS.get(ModStructures.library));
            world.getChunkSource().generator.getSettings().structureConfig = tempMap;
        }
    }
}
