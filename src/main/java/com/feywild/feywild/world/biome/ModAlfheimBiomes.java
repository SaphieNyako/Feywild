package com.feywild.feywild.world.biome;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.config.WorldGenConfig;
import com.feywild.feywild.world.biome.biomes.AutumnBiome;
import com.feywild.feywild.world.biome.biomes.SpringBiome;
import com.feywild.feywild.world.biome.biomes.SummerBiome;
import com.feywild.feywild.world.biome.biomes.WinterBiome;
import mythicbotany.alfheim.AlfheimBiomeManager;
import mythicbotany.alfheim.AlfheimBiomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

// No @RegisterClass as this may not be loaded if MythicBotany is
// not installed
public class ModAlfheimBiomes {
    
    // Add 4 blank alfheim biomes that are then customised by BiomeLoader
    public static final Biome alfheimSpring = AlfheimBiomes.alfheimBiome()
            .depth(0.125f).scale(WorldGenConfig.biomes.spring.size)
            .specialEffects(SpringBiome.ambience().build())
            .biomeCategory(Biome.Category.FOREST)
            .mobSpawnSettings(AlfheimBiomes.alfheimMobs().build())
            .generationSettings(
                    AlfheimBiomes.alfheimGen(AlfheimBiomes.AlfBiomeType.GRASSY)
                            .build()
            )
            .build();

    public static final Biome alfheimSummer = AlfheimBiomes.alfheimBiome()
            .depth(0.125f).scale(WorldGenConfig.biomes.summer.size)
            .specialEffects(SummerBiome.ambience().build())
            .biomeCategory(Biome.Category.FOREST)
            .mobSpawnSettings(AlfheimBiomes.alfheimMobs().build())
            .generationSettings(
                    AlfheimBiomes.alfheimGen(AlfheimBiomes.AlfBiomeType.GRASSY)
                            .build()
            )
            .build();

    public static final Biome alfheimAutumn = AlfheimBiomes.alfheimBiome()
            .depth(0.125f).scale(WorldGenConfig.biomes.autumn.size)
            .specialEffects(AutumnBiome.ambience().build())
            .biomeCategory(Biome.Category.FOREST)
            .mobSpawnSettings(AlfheimBiomes.alfheimMobs().build())
            .generationSettings(
                    AlfheimBiomes.alfheimGen(AlfheimBiomes.AlfBiomeType.GRASSY)
                            .build()
            )
            .build();

    public static final Biome alfheimWinter = AlfheimBiomes.alfheimBiome()
            .depth(0.125f).scale(WorldGenConfig.biomes.winter.size)
            .specialEffects(WinterBiome.ambience().build())
            .biomeCategory(Biome.Category.FOREST)
            .mobSpawnSettings(AlfheimBiomes.alfheimMobs().build())
            .generationSettings(
                    AlfheimBiomes.alfheimGen(AlfheimBiomes.AlfBiomeType.GRASSY)
                            .addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, Features.FREEZE_TOP_LAYER)
                            .build()
            )
            .build();
    
    public static void register() {
        FeywildMod.getInstance().register("alfheim_spring", alfheimSpring);
        FeywildMod.getInstance().register("alfheim_summer", alfheimSummer);
        FeywildMod.getInstance().register("alfheim_autumn", alfheimAutumn);
        FeywildMod.getInstance().register("alfheim_winter", alfheimWinter);
    }
    
    public static void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            AlfheimBiomeManager.addUncommonBiome(alfheimSpring.getRegistryName());
            AlfheimBiomeManager.addUncommonBiome(alfheimSummer.getRegistryName());
            AlfheimBiomeManager.addUncommonBiome(alfheimAutumn.getRegistryName());
            AlfheimBiomeManager.addUncommonBiome(alfheimWinter.getRegistryName());
        });
    }
}
