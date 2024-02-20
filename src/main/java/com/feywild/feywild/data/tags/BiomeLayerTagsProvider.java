package com.feywild.feywild.data.tags;

import com.feywild.feywild.data.worldgen.BiomeLayerProvider;
import com.feywild.feywild.tag.ModBiomeTags;
import org.moddingx.libx.datagen.DatagenContext;
import org.moddingx.libx.datagen.provider.tags.TagProviderBase;
import org.moddingx.libx.sandbox.SandBox;
import org.moddingx.libx.sandbox.generator.BiomeLayer;

public class BiomeLayerTagsProvider extends TagProviderBase<BiomeLayer> {
    
    private final BiomeLayerProvider layers;
    
    public BiomeLayerTagsProvider(DatagenContext ctx) {
        super(ctx, SandBox.BIOME_LAYER);
        this.layers = ctx.findRegistryProvider(BiomeLayerProvider.class);
    }

    @Override
    protected void setup() {
        this.tag(ModBiomeTags.FEYWILD_LAYERS).add(this.layers.layer.value());
    }
}
