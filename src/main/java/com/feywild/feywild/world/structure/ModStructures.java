package com.feywild.feywild.world.structure;

import com.feywild.feywild.world.structure.structures.*;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.github.noeppi_noeppi.libx.annotation.RegisterClass;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RegisterClass
public class ModStructures {
    
    public static final BaseStructure springWorldTree = new SpringWorldTreeStructure();
    public static final BaseStructure summerWorldTree = new SummerWorldTreeStructure();
    public static final BaseStructure autumnWorldTree = new AutumnWorldTreeStructure();
    public static final BaseStructure winterWorldTree = new WinterWorldTreeStructure();
    public static final BaseStructure blacksmith = new BlacksmithStructure();
    public static final BaseStructure library = new LibraryStructure();

    public static void setupStructures() {
        setupMapSpacingAndLand(springWorldTree, true);
        setupMapSpacingAndLand(summerWorldTree, true);
        setupMapSpacingAndLand(autumnWorldTree, true);
        setupMapSpacingAndLand(winterWorldTree, true);
        setupMapSpacingAndLand(library, true);
        setupMapSpacingAndLand(blacksmith, true);
    }

    //Checks rarity of structure and determines if land conforms to it
    public static void setupMapSpacingAndLand(BaseStructure structure, boolean transformSurroundingLand) {

        StructureSeparationSettings separationSettings = new StructureSeparationSettings(
                structure.getAverageDistanceBetweenChunks(),
                structure.getMinDistanceBetweenChunks(),
                structure.getSeedModifier()
        );
        
        //add our structures into the map in Structure class
        Structure.STRUCTURES_REGISTRY.put(Objects.requireNonNull(structure.getRegistryName()).toString(), structure);  //Might return Null

        //Whether surrounding land will be modified automatically to conform to the bottom of the structure.
        if (transformSurroundingLand) {
            Structure.NOISE_AFFECTING_FEATURES = ImmutableList.<Structure<?>>builder()
                    .addAll(Structure.NOISE_AFFECTING_FEATURES)
                    .add(structure)
                    .build();

        }

        //DEFAULT
        DimensionStructuresSettings.DEFAULTS = ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
                        .putAll(DimensionStructuresSettings.DEFAULTS)
                        .put(structure, separationSettings)
                        .build();

        //NOISE_GENERATOR_SETTINGS
        WorldGenRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
            Map<Structure<?>, StructureSeparationSettings> structureMap = settings.getValue().structureSettings().structureConfig();
            if (structureMap instanceof ImmutableMap) {
                Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(structureMap);
                tempMap.put(structure, separationSettings);
                settings.getValue().structureSettings().structureConfig = ImmutableMap.copyOf(tempMap);
            } else {
                structureMap.put(structure, separationSettings);
            }
        });
    }
}
