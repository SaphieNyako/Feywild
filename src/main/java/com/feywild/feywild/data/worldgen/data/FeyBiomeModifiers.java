package com.feywild.feywild.data.worldgen.data;

import com.feywild.feywild.entity.ModEntities;
import org.moddingx.libx.datagen.provider.sandbox.BiomeModifierProviderBase;
import net.minecraft.core.Holder;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeModifier;

import io.github.noeppi_noeppi.mods.sandbox.datagen.ext.base.WorldGenData.Properties;

public class FeyBiomeModifiers extends BiomeModifierProviderBase {

    public final Holder<BiomeModifier> overworldSpawn = this.addSpawns(BiomeTags.IS_OVERWORLD)
            .spawn(ModEntities.autumnPixie, 1, 1, 1)
            .spawn(ModEntities.springPixie, 1, 1, 1)
            .spawn(ModEntities.dwarfBlacksmith, 5, 1, 1)
            .build();

    public final Holder<BiomeModifier> hotOverworldSpawn = this.addSpawns(Tags.Biomes.IS_HOT_OVERWORLD)
            .spawn(ModEntities.summerPixie, 1, 1, 1)
            .build();

    public final Holder<BiomeModifier> coldOverworldSpawn = this.addSpawns(Tags.Biomes.IS_COLD_OVERWORLD)
            .spawn(ModEntities.winterPixie, 1, 1, 1)
            .build();

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

    public FeyBiomeModifiers(Properties properties) {
        super(properties);
    }
}
