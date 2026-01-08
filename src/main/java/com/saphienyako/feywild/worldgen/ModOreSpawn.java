package com.saphienyako.feywild.worldgen;

import com.saphienyako.feywild.Feywild;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Feywild.MOD_ID)
public class ModOreSpawn {

    @SubscribeEvent
    public static void generateOres(BiomeLoadingEvent event) {

        for (ModOreType ore : ModOreType.values()) {

            ConfiguredFeature<?, ?> feature = Feature.ORE
                    .configured(new OreFeatureConfig(
                            OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                            ore.getBlock().defaultBlockState(),
                            ore.getMaxVeinSize()
                    ))
                    .decorated(Placement.RANGE.configured(
                            new TopSolidRangeConfig(
                                    ore.getMinHeight(),
                                    ore.getMinHeight(),
                                    ore.getMaxHeight()
                            )
                    ))
                    .squared()
                    .count(ore.getMaxVeinSize());

            event.getGeneration().addFeature(
                    GenerationStage.Decoration.UNDERGROUND_ORES,
                    feature
            );
        }
    }
}
