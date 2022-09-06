package com.feywild.feywild.data.worldgen.data;

import com.feywild.feywild.entity.ModEntities;
import io.github.noeppi_noeppi.mods.sandbox.datagen.ext.BiomeModifierData;
import net.minecraft.core.Holder;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeModifier;

public class FeyBiomeModifiers extends BiomeModifierData {
    
    private final FeyTrees trees = this.resolve(FeyTrees.class);
    
    public final Holder<BiomeModifier> springTreePatches = this.addFeatures(Tags.Biomes.IS_PLAINS, GenerationStep.Decoration.VEGETAL_DECORATION)
            .feature(trees.springTreePatches)
            .build();
    
    public final Holder<BiomeModifier> summerTreePatches = this.addFeatures(Tags.Biomes.IS_HOT_OVERWORLD, GenerationStep.Decoration.VEGETAL_DECORATION)
            .feature(trees.summerTreePatches)
            .build();
    
    public final Holder<BiomeModifier> autumnTreePatches = this.addFeatures(BiomeTags.IS_TAIGA, GenerationStep.Decoration.VEGETAL_DECORATION)
            .feature(trees.autumnTreePatches)
            .build();
    
    public final Holder<BiomeModifier> winterTreePatches = this.addFeatures(Tags.Biomes.IS_COLD_OVERWORLD, GenerationStep.Decoration.VEGETAL_DECORATION)
            .feature(trees.winterTreePatches)
            .build();
    
    public final Holder<BiomeModifier> overworldPixies = this.addSpawns(BiomeTags.IS_OVERWORLD)
            .spawn(ModEntities.springPixie, 5, 1, 1)
            .spawn(ModEntities.summerPixie, 5, 1, 1)
            .spawn(ModEntities.autumnPixie, 5, 1, 1)
            .spawn(ModEntities.winterPixie, 5, 1, 1)
            .build();
    
    public FeyBiomeModifiers(Properties properties) {
        super(properties);
    }
}
