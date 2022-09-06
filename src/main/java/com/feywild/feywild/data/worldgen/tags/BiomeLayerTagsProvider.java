package com.feywild.feywild.data.worldgen.tags;

import com.feywild.feywild.tag.ModBiomeTags;
import com.feywild.feywild.world.FeywildDimensions;
import io.github.noeppi_noeppi.mods.sandbox.biome.BiomeLayer;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.moddingx.libx.datagen.provider.TagProviderBase;
import org.moddingx.libx.mod.ModX;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BiomeLayerTagsProvider extends TagProviderBase<BiomeLayer> {
    
    public BiomeLayerTagsProvider(ModX mod, DataGenerator generator, @Nonnull Registry<BiomeLayer> registry, @Nullable ExistingFileHelper fileHelper) {
        super(mod, generator, registry, fileHelper);
    }

    @Override
    protected void setup() {
        this.tag(ModBiomeTags.FEYWILD_LAYERS).add(FeywildDimensions.FEYWILD_LAYER);
    }
}
