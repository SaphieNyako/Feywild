package com.feywild.feywild.data.worldgen.tags;

import com.feywild.feywild.tag.ModBiomeTags;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.moddingx.libx.datagen.provider.TagProviderBase;
import org.moddingx.libx.mod.ModX;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BiomeTagsProvider extends TagProviderBase<Biome> {
    
    public BiomeTagsProvider(ModX mod, DataGenerator generator, @Nonnull Registry<Biome> registry, @Nullable ExistingFileHelper fileHelper) {
        super(mod, generator, registry, fileHelper);
    }

    @Override
    protected void setup() {
        this.tag(ModBiomeTags.IS_FEYWILD_DIMENSION).add(
                FeywildBiomes.SPRING_BIOME,
                FeywildBiomes.SUMMER_BIOME,
                FeywildBiomes.AUTUMN_BIOME,
                FeywildBiomes.WINTER_BIOME,
                FeywildBiomes.FEY_OCEAN
        );
        
        this.tag(BiomeTags.IS_FOREST).add(FeywildBiomes.SPRING_BIOME, FeywildBiomes.SUMMER_BIOME, FeywildBiomes.AUTUMN_BIOME, FeywildBiomes.WINTER_BIOME);
        this.tag(BiomeTags.IS_OCEAN).add(FeywildBiomes.FEY_OCEAN);
        this.tag(Tags.Biomes.IS_HOT).add(FeywildBiomes.SUMMER_BIOME);
        this.tag(Tags.Biomes.IS_COLD).add(FeywildBiomes.WINTER_BIOME);
        this.tag(Tags.Biomes.IS_MAGICAL).add(FeywildBiomes.SPRING_BIOME, FeywildBiomes.SUMMER_BIOME, FeywildBiomes.AUTUMN_BIOME, FeywildBiomes.WINTER_BIOME);
        
        this.tag(ModBiomeTags.IS_SPRING).add(FeywildBiomes.SPRING_BIOME);
        this.tag(ModBiomeTags.IS_SUMMER).add(FeywildBiomes.SUMMER_BIOME);
        this.tag(ModBiomeTags.IS_AUTUMN).add(FeywildBiomes.AUTUMN_BIOME);
        this.tag(ModBiomeTags.IS_WINTER).add(FeywildBiomes.WINTER_BIOME);
    }
}
