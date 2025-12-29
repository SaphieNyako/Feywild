package com.saphienyako.feywild.config;

import com.saphienyako.feywild.Feywild;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = Feywild.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class FeywildConfig {
    //COMMON
    private static final ModConfigSpec.Builder COMMON_BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec COMMON_SPEC;

    private static final ModConfigSpec.IntValue FEY_DUST_DURATION;
    private static final ModConfigSpec.BooleanValue SPAWN_WITH_LEXICON;

    public static int feyDustDuration;
    public static boolean spawnWithLexicon;

    //CLIENT
    private static final ModConfigSpec.Builder CLIENT_BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec CLIENT_SPEC;

    private static final ModConfigSpec.BooleanValue FLOWER_PARTICLES;
    private static final ModConfigSpec.BooleanValue VOICES_ACTIVE;

    public static boolean flowerParticles;
    public static boolean voicesActive;

    static {
        //COMMON
        COMMON_BUILDER
                .comment("Feywild General Config")
                .push("feywild");

        FEY_DUST_DURATION = COMMON_BUILDER
                .comment("The duration in ticks for the levitation effect applied by fey dust. (Minimum: 1)")
                .defineInRange("fey_dust_duration", 30, 1, 100);

        SPAWN_WITH_LEXICON = COMMON_BUILDER
                .comment("Whether players should spawn with a Feywild Lexicon")
                .define("spawn_with_lexicon", true);

        COMMON_BUILDER.pop();
        COMMON_SPEC = COMMON_BUILDER.build();

        //CLIENT
        CLIENT_BUILDER
                .comment("Feywild Client Config")
                .push("client");

        FLOWER_PARTICLES = CLIENT_BUILDER
                .comment("Whether giant flowers should have particles")
                .define("flower_particles", true);

        VOICES_ACTIVE = CLIENT_BUILDER
                .comment("Whether fey should have voice acting on")
                .define("voices_active", true);

        CLIENT_BUILDER.pop();
        CLIENT_SPEC = CLIENT_BUILDER.build();

    }

    private static boolean validateItemName(final Object obj) {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event) {

        if (event.getConfig().getSpec() == COMMON_SPEC) {
            feyDustDuration = FEY_DUST_DURATION.get();
            spawnWithLexicon = SPAWN_WITH_LEXICON.get();
        }

        if (event.getConfig().getSpec() == CLIENT_SPEC) {
            flowerParticles = FLOWER_PARTICLES.get();
            voicesActive = VOICES_ACTIVE.get();
        }
    }

}

