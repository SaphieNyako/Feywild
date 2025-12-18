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
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.IntValue FEY_DUST_DURATION;
    private static final ModConfigSpec.BooleanValue SPAWN_WITH_LEXICON;

    static {
        BUILDER
                .comment("Feywild General Config")
                .push("feywild");

        FEY_DUST_DURATION = BUILDER
                .comment("The duration in ticks for the levitation effect applied by fey dust. (Minimum: 1)")
                .defineInRange("fey_dust_duration", 30, 1, 100);

        SPAWN_WITH_LEXICON = BUILDER
                .comment("Whether players should spawn with a Feywild Lexicon")
                .define("spawn_with_lexicon", true);

        BUILDER.pop();
    }

    public static final ModConfigSpec COMMON_SPEC = BUILDER.build();

    public static int feyDustDuration;
    public static boolean spawnWithLexicon;

    private static boolean validateItemName(final Object obj) {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() != COMMON_SPEC) return;

        feyDustDuration = FEY_DUST_DURATION.get();
        spawnWithLexicon = SPAWN_WITH_LEXICON.get();
    }

}

