package com.feywild.feywild.world.structure;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.world.structure.structures.*;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class ModStructures {

    public static final DeferredRegister<Structure<?>> STRUCTURES
            = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, FeywildMod.MOD_ID);

    public static final RegistryObject<Structure<NoFeatureConfig>> SPRING_WORLD_TREE = STRUCTURES.register("spring_world_tree",
            SpringWorldTreeStructure::new);

    public static final RegistryObject<Structure<NoFeatureConfig>> SUMMER_WORLD_TREE = STRUCTURES.register("summer_world_tree",
            SummerWorldTreeStructure::new);

    public static final RegistryObject<Structure<NoFeatureConfig>> AUTUMN_WORLD_TREE = STRUCTURES.register("autumn_world_tree",
            AutumnWorldTreeStructure::new);

    public static final RegistryObject<Structure<NoFeatureConfig>> WINTER_WORLD_TREE = STRUCTURES.register("winter_world_tree",
            WinterWorldTreeStructure::new);

    public static final RegistryObject<Structure<NoFeatureConfig>> BLACKSMITH = STRUCTURES.register("blacksmith",
            BlacksmithStructure::new);

    public static final RegistryObject<Structure<NoFeatureConfig>> LIBRARY = STRUCTURES.register("library",
            LibraryStructure::new);

    public static void setupStructures() {
        setupMapSpacingAndLand(SPRING_WORLD_TREE.get(),
                new StructureSeparationSettings(SpringWorldTreeStructure.AVERAGE_DISTANCE_BETWEEN_CHUNKS,
                        SpringWorldTreeStructure.MIN_DISTANCE_BETWEEN_CHUNKS,
                        SpringWorldTreeStructure.SEED_MODIFIER),
                true);

        setupMapSpacingAndLand(SUMMER_WORLD_TREE.get(),
                new StructureSeparationSettings(SummerWorldTreeStructure.AVERAGE_DISTANCE_BETWEEN_CHUNKS,
                        SummerWorldTreeStructure.MIN_DISTANCE_BETWEEN_CHUNKS,
                        SummerWorldTreeStructure.SEED_MODIFIER),
                true);

        setupMapSpacingAndLand(AUTUMN_WORLD_TREE.get(),
                new StructureSeparationSettings(AutumnWorldTreeStructure.AVERAGE_DISTANCE_BETWEEN_CHUNKS,
                        AutumnWorldTreeStructure.MIN_DISTANCE_BETWEEN_CHUNKS,
                        AutumnWorldTreeStructure.SEED_MODIFIER),
                true);

        setupMapSpacingAndLand(WINTER_WORLD_TREE.get(),
                new StructureSeparationSettings(WinterWorldTreeStructure.AVERAGE_DISTANCE_BETWEEN_CHUNKS,
                        WinterWorldTreeStructure.MIN_DISTANCE_BETWEEN_CHUNKS,
                        WinterWorldTreeStructure.SEED_MODIFIER),
                true);

        setupMapSpacingAndLand(BLACKSMITH.get(),
                new StructureSeparationSettings(BlacksmithStructure.AVERAGE_DISTANCE_BETWEEN_CHUNKS,
                        BlacksmithStructure.MIN_DISTANCE_BETWEEN_CHUNKS,
                        BlacksmithStructure.SEED_MODIFIER),
                true);

        setupMapSpacingAndLand(LIBRARY.get(),
                new StructureSeparationSettings(LibraryStructure.AVERAGE_DISTANCE_BETWEEN_CHUNKS,
                        LibraryStructure.MIN_DISTANCE_BETWEEN_CHUNKS,
                        LibraryStructure.SEED_MODIFIER),
                true);

        // Add more structures here

    }

    //Checks rarity of structure and determines if land conforms to it
    public static <F extends Structure<?>> void setupMapSpacingAndLand(F structure, StructureSeparationSettings structureSeparationSettings,
                                                                       boolean transformSurroundingLand
    ) {

        //add our structures into the map in Structure class
        Structure.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);  //Might return Null

        //Whether surrounding land will be modified automatically to conform to the bottom of the structure.
        if (transformSurroundingLand) {
            Structure.NOISE_AFFECTING_FEATURES = ImmutableList.<Structure<?>>builder()
                    .addAll(Structure.NOISE_AFFECTING_FEATURES)
                    .add(structure)
                    .build();

        }

        //DEFAULT
        DimensionStructuresSettings.DEFAULTS =
                ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
                        .putAll(DimensionStructuresSettings.DEFAULTS)
                        .put(structure, structureSeparationSettings)
                        .build();

        //NOISE_GENERATOR_SETTINGS
        WorldGenRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
            Map<Structure<?>, StructureSeparationSettings> structureMap = settings.getValue().structureSettings().structureConfig();

            if (structureMap instanceof ImmutableMap) {
                Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(structureMap);
                tempMap.put(structure, structureSeparationSettings);
                settings.getValue().structureSettings().structureConfig();

            } else {
                structureMap.put(structure, structureSeparationSettings);
            }

        });
    }

}
