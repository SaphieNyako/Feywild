package com.feywild.feywild.world.structure;

import com.feywild.feywild.world.structure.structures.*;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.github.noeppi_noeppi.libx.annotation.registration.RegisterClass;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;

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
    public static final BaseStructure beekeep = new BeekeepStructure();

    public static void setupStructures() {
        setupMapSpacingAndLand(springWorldTree, true);
        setupMapSpacingAndLand(summerWorldTree, true);
        setupMapSpacingAndLand(autumnWorldTree, true);
        setupMapSpacingAndLand(winterWorldTree, true);
        setupMapSpacingAndLand(library, true);
        setupMapSpacingAndLand(blacksmith, true);
        setupMapSpacingAndLand(beekeep, true);
    }

    //Checks rarity of structure and determines if land conforms to it
    public static void setupMapSpacingAndLand(BaseStructure structure, boolean transformSurroundingLand) {
        StructureFeatureConfiguration separationSettings = structure.getSettings();
        
        //add our structures into the map in Structure class
        StructureFeature.STRUCTURES_REGISTRY.put(Objects.requireNonNull(structure.getRegistryName()).toString(), structure);  //Might return Null

        //Whether surrounding land will be modified automatically to conform to the bottom of the structure.
        if (transformSurroundingLand) {
            StructureFeature.NOISE_AFFECTING_FEATURES = ImmutableList.<StructureFeature<?>>builder()
                    .addAll(StructureFeature.NOISE_AFFECTING_FEATURES)
                    .add(structure)
                    .build();

        }

        //DEFAULT
        StructureSettings.DEFAULTS = ImmutableMap.<StructureFeature<?>, StructureFeatureConfiguration>builder()
                        .putAll(StructureSettings.DEFAULTS)
                        .put(structure, separationSettings)
                        .build();

        //NOISE_GENERATOR_SETTINGS
        BuiltinRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
            Map<StructureFeature<?>, StructureFeatureConfiguration> structureMap = settings.getValue().structureSettings().structureConfig();
            if (structureMap instanceof ImmutableMap) {
                Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(structureMap);
                tempMap.put(structure, separationSettings);
                settings.getValue().structureSettings().structureConfig = ImmutableMap.copyOf(tempMap);
            } else {
                structureMap.put(structure, separationSettings);
            }
        });
    }
}
