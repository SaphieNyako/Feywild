package com.feywild.feywild.data.worldgen;

import com.feywild.feywild.data.worldgen.data.*;
import com.feywild.feywild.data.worldgen.tags.BiomeLayerTagsProvider;
import com.feywild.feywild.data.worldgen.tags.BiomeTagsProvider;
import io.github.noeppi_noeppi.mods.sandbox.SandBox;
import io.github.noeppi_noeppi.mods.sandbox.datagen.WorldGenProviderBase;
import io.github.noeppi_noeppi.mods.sandbox.datagen.registry.WorldGenRegistries;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.moddingx.libx.annotation.data.Datagen;
import org.moddingx.libx.mod.ModX;

@Datagen
public class WorldGenProvider extends WorldGenProviderBase {

    public WorldGenProvider(ModX mod, DataGenerator generator, ExistingFileHelper fileHelper) {
        super(mod, generator, fileHelper);
    }

    @Override
    protected void addAdditionalProviders(ModX mod, DataGenerator generator, ExistingFileHelper fileHelper, WorldGenRegistries registries) {
        // These providers must use the worldgen registry set, so they can add tags for biomes added through the world gen provider.
        generator.addProvider(true, new BiomeTagsProvider(mod, generator, registries.registry(Registry.BIOME_REGISTRY), fileHelper));
        generator.addProvider(true, new BiomeLayerTagsProvider(mod, generator, registries.registry(SandBox.BIOME_LAYER_REGISTRY), fileHelper));
    }

    @Override
    protected void setup() {
        
        // Features
        this.addData(FeyTrees::new);
        this.addData(FeyFeatures::new);
        this.addData(FeyPlacements::new);
        
        // Structures
        this.addData(FeyTemplates::new);
        this.addData(FeyStructures::new);
        this.addData(FeyStructureSets::new);
        
        // Biome data
        this.addData(FeyBiomeModifiers::new);
        this.addData(FeyBiomes::new);
        this.addData(FeySurface::new);
        this.addData(FeyBiomeLayers::new);
        
        // Dimensions
        this.addData(FeyNoiseSettings::new);
        this.addData(FeyDimensionTypes::new);
        this.addData(FeyDimensions::new);
    }
}
