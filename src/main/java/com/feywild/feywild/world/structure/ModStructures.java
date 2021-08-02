package com.feywild.feywild.world.structure;

import com.feywild.feywild.world.structure.structures.*;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.github.noeppi_noeppi.libx.annotation.RegisterClass;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;

import java.util.HashMap;
import java.util.Map;

@RegisterClass
public class ModStructures {
    

    public static final Structure<NoFeatureConfig> springWorldTree = new SpringWorldTreeStructure();
    public static final Structure<NoFeatureConfig> summerWorldTree = new SummerWorldTreeStructure();
    public static final Structure<NoFeatureConfig> autumnWorldTree = new AutumnWorldTreeStructure();
    public static final Structure<NoFeatureConfig> winterWorldTree = new WinterWorldTreeStructure();
    public static final Structure<NoFeatureConfig> blacksmith = new BlacksmithStructure();
    public static final Structure<NoFeatureConfig> library = new LibraryStructure();

    public static void setupStructures() {
        setupMapSpacingAndLand(springWorldTree,
                new StructureSeparationSettings(SpringWorldTreeStructure.AVERAGE_DISTANCE_BETWEEN_CHUNKS,
                        SpringWorldTreeStructure.MIN_DISTANCE_BETWEEN_CHUNKS,
                        SpringWorldTreeStructure.SEED_MODIFIER),
                true);

        setupMapSpacingAndLand(summerWorldTree,
                new StructureSeparationSettings(SummerWorldTreeStructure.AVERAGE_DISTANCE_BETWEEN_CHUNKS,
                        SummerWorldTreeStructure.MIN_DISTANCE_BETWEEN_CHUNKS,
                        SummerWorldTreeStructure.SEED_MODIFIER),
                true);

        setupMapSpacingAndLand(autumnWorldTree,
                new StructureSeparationSettings(AutumnWorldTreeStructure.AVERAGE_DISTANCE_BETWEEN_CHUNKS,
                        AutumnWorldTreeStructure.MIN_DISTANCE_BETWEEN_CHUNKS,
                        AutumnWorldTreeStructure.SEED_MODIFIER),
                true);

        setupMapSpacingAndLand(winterWorldTree,
                new StructureSeparationSettings(WinterWorldTreeStructure.AVERAGE_DISTANCE_BETWEEN_CHUNKS,
                        WinterWorldTreeStructure.MIN_DISTANCE_BETWEEN_CHUNKS,
                        WinterWorldTreeStructure.SEED_MODIFIER),
                true);

        setupMapSpacingAndLand(blacksmith,
                new StructureSeparationSettings(BlacksmithStructure.AVERAGE_DISTANCE_BETWEEN_CHUNKS,
                        BlacksmithStructure.MIN_DISTANCE_BETWEEN_CHUNKS,
                        BlacksmithStructure.SEED_MODIFIER),
                true);

        setupMapSpacingAndLand(library,
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
