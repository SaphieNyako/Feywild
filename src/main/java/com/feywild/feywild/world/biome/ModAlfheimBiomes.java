package com.feywild.feywild.world.biome;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.config.WorldGenConfig;
import com.feywild.feywild.world.biome.biomes.AlfheimAutumnBiome;
import com.feywild.feywild.world.biome.biomes.AlfheimSpringBiome;
import com.feywild.feywild.world.biome.biomes.AlfheimSummerBiome;
import com.feywild.feywild.world.biome.biomes.AlfheimWinterBiome;
import mythicbotany.alfheim.AlfheimBiomeManager;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

// No @RegisterClass as this may not be loaded if MythicBotany is
// not installed
public class ModAlfheimBiomes {

    // Add 4 blank alfheim biomes that are then customised by BiomeLoader
    public static final Biome alfheimSpring = AlfheimSpringBiome.INSTANCE.biomeSetup(() -> ModConfiguredSurfaceBuilders.SPRING_SURFACE, 0.125f, WorldGenConfig.biomes.spring.size);

            /*
            AlfheimBiomes.alfheimBiome()
            .depth(0.125f).scale(WorldGenConfig.biomes.spring.size)
            .specialEffects(SpringBiome.ambience().build())
            .biomeCategory(Biome.Category.FOREST)
            .mobSpawnSettings(AlfheimBiomes.alfheimMobs().build())
            .generationSettings(
                    AlfheimBiomes.alfheimGen(AlfheimBiomes.AlfBiomeType.GRASSY)
                            .build()
            )
            .build(); */

    public static final Biome alfheimSummer = AlfheimSummerBiome.INSTANCE.biomeSetup(() -> ModConfiguredSurfaceBuilders.SUMMER_SURFACE, 0.125f, WorldGenConfig.biomes.summer.size);
    /*
            AlfheimBiomes.alfheimBiome()
            .depth(0.125f).scale(WorldGenConfig.biomes.summer.size)
            .specialEffects(SummerBiome.ambience().build())
            .biomeCategory(Biome.Category.SAVANNA).temperature(0.9F)
            .mobSpawnSettings(AlfheimBiomes.alfheimMobs().build())
            .generationSettings(
                    AlfheimBiomes.alfheimGen(AlfheimBiomes.AlfBiomeType.GRASSY)
                            .build()
            )
            .build(); */

    public static final Biome alfheimAutumn = AlfheimAutumnBiome.INSTANCE.biomeSetup(() -> ModConfiguredSurfaceBuilders.AUTUMN_SURFACE, 0.125f, WorldGenConfig.biomes.autumn.size);

            /*
            .depth(0.125f).scale(WorldGenConfig.biomes.autumn.size)
            .specialEffects(AutumnBiome.ambience().build())
            .biomeCategory(Biome.Category.MUSHROOM).temperature(0.8F)
            .mobSpawnSettings(AlfheimBiomes.alfheimMobs().build())
            .generationSettings(
                    AlfheimBiomes.alfheimGen(AlfheimBiomes.AlfBiomeType.GRASSY)
                            .build()
            )
            .build(); */

    public static final Biome alfheimWinter = AlfheimWinterBiome.INSTANCE.biomeSetup(() -> ModConfiguredSurfaceBuilders.WINTER_SURFACE, 0.125f, WorldGenConfig.biomes.winter.size);

            /* AlfheimBiomes.alfheimBiome()
            .depth(0.125f).scale(WorldGenConfig.biomes.winter.size)
            .specialEffects(WinterBiome.ambience().build())
            .biomeCategory(Biome.Category.ICY).temperature(0)
            .mobSpawnSettings(AlfheimBiomes.alfheimMobs().build())
            .generationSettings(
                    AlfheimBiomes.alfheimGen(AlfheimBiomes.AlfBiomeType.GRASSY)
                            .addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, Features.FREEZE_TOP_LAYER)
                            .build()
            )
            .build(); */

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
