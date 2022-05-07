package com.feywild.feywild.world.feature.structure;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.world.feature.structure.structures.BlacksmithStructure;
import com.feywild.feywild.world.feature.structure.structures.LibraryStructure;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModStructures {

    public static final DeferredRegister<StructureFeature<?>> STRUCTURE_FEATURE_DEFERRED_REGISTERD_REGISTRY =
            DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, FeywildMod.getInstance().modid);

    public static final RegistryObject<StructureFeature<?>> BLACKSMITH =
            STRUCTURE_FEATURE_DEFERRED_REGISTERD_REGISTRY.register("blacksmith", BlacksmithStructure::new);

    public static final RegistryObject<StructureFeature<?>> LIBRARY =
            STRUCTURE_FEATURE_DEFERRED_REGISTERD_REGISTRY.register("library", LibraryStructure::new);

    public static void register(IEventBus eventBus) {
        STRUCTURE_FEATURE_DEFERRED_REGISTERD_REGISTRY.register(eventBus);
    }

    /*

    public static void addStructureSettings(final WorldEvent.Load event) {
        if (event.getWorld() instanceof ServerLevel level) {
            if (level.getChunkSource().getGenerator() instanceof FlatLevelSource && level.dimension().equals(Level.OVERWORLD)) {
                return;
            }

            try {
                Method method = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "m_6909_");
                method.setAccessible(true);
                //noinspection unchecked
                ResourceLocation generatorId = Registry.CHUNK_GENERATOR.getKey((Codec<? extends ChunkGenerator>) method.invoke(level.getChunkSource().getGenerator()));
                if (generatorId != null && generatorId.getNamespace().equals("terraforged")) return;
            } catch (Exception e) {
                FeywildMod.getInstance().logger.error("Was unable to check if " + level.dimension().location() + " is using a Terraforged ChunkGenerator.");
            }

            Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(level.getChunkSource().getGenerator().getSettings().structureConfig());
            tempMap.putIfAbsent(ModStructures.springWorldTree, StructureSettings.DEFAULTS.get(ModStructures.springWorldTree));
            tempMap.putIfAbsent(ModStructures.summerWorldTree, StructureSettings.DEFAULTS.get(ModStructures.summerWorldTree));
            tempMap.putIfAbsent(ModStructures.autumnWorldTree, StructureSettings.DEFAULTS.get(ModStructures.autumnWorldTree));
            tempMap.putIfAbsent(ModStructures.winterWorldTree, StructureSettings.DEFAULTS.get(ModStructures.autumnWorldTree));
            tempMap.putIfAbsent(ModStructures.blacksmith, StructureSettings.DEFAULTS.get(ModStructures.blacksmith));
            tempMap.putIfAbsent(ModStructures.library, StructureSettings.DEFAULTS.get(ModStructures.library));
            tempMap.putIfAbsent(ModStructures.beekeep, StructureSettings.DEFAULTS.get(ModStructures.beekeep));
            level.getChunkSource().getGenerator().getSettings().structureConfig = tempMap;
        }
    }

     */
}
