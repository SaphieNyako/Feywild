package com.feywild.feywild.data.worldgen;

import com.feywild.feywild.entity.ModEntities;
import net.minecraft.core.Holder;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeModifier;
import org.moddingx.libx.datagen.DatagenContext;
import org.moddingx.libx.datagen.provider.sandbox.BiomeModifierProviderBase;

public class BiomeModifierProvider extends BiomeModifierProviderBase {
    
    private final TreeProvider trees = this.context.findRegistryProvider(TreeProvider.class);

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

    public BiomeModifierProvider(DatagenContext ctx) {
        super(ctx);
    }
}
