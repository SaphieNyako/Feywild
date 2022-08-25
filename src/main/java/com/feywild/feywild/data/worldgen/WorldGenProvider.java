package com.feywild.feywild.data.worldgen;

import io.github.noeppi_noeppi.mods.sandbox.datagen.WorldGenProviderBase;
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
        this.addData(FeyOverworldBiomeModifiers::new);
        this.addData(FeyBiomes::new);
        this.addData(FeySurface::new);
        this.addData(FeyBiomeLayers::new);
    }
}
