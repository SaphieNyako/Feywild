package com.feywild.feywild.world;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.world.structure.ModStructures;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class StructureLoader {

    public static void addStructureSettings(final WorldEvent.Load event) {
        if (event.getWorld() instanceof ServerLevel level) {
            if (level.getChunkSource().generator instanceof FlatLevelSource && level.dimension().equals(Level.OVERWORLD)) {
                return;
            }

            try {
                Method method = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "m_6909_");
                method.setAccessible(true);
                //noinspection unchecked
                ResourceLocation generatorId = Registry.CHUNK_GENERATOR.getKey((Codec<? extends ChunkGenerator>) method.invoke(level.getChunkSource().generator));
                if (generatorId != null && generatorId.getNamespace().equals("terraforged")) return;
            } catch (Exception e) {
                FeywildMod.getInstance().logger.error("Was unable to check if " + level.dimension().location() + " is using a Terraforged ChunkGenerator.");
            }

            Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(level.getChunkSource().generator.getSettings().structureConfig());
            tempMap.putIfAbsent(ModStructures.springWorldTree, StructureSettings.DEFAULTS.get(ModStructures.springWorldTree));
            tempMap.putIfAbsent(ModStructures.summerWorldTree, StructureSettings.DEFAULTS.get(ModStructures.summerWorldTree));
            tempMap.putIfAbsent(ModStructures.autumnWorldTree, StructureSettings.DEFAULTS.get(ModStructures.autumnWorldTree));
            tempMap.putIfAbsent(ModStructures.winterWorldTree, StructureSettings.DEFAULTS.get(ModStructures.autumnWorldTree));
            tempMap.putIfAbsent(ModStructures.blacksmith, StructureSettings.DEFAULTS.get(ModStructures.blacksmith));
            tempMap.putIfAbsent(ModStructures.library, StructureSettings.DEFAULTS.get(ModStructures.library));
            tempMap.putIfAbsent(ModStructures.beekeep, StructureSettings.DEFAULTS.get(ModStructures.beekeep));
            level.getChunkSource().generator.getSettings().structureConfig = tempMap;
        }

    }
}
