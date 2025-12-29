package com.saphienyako.feywild.config;


import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.common.ForgeConfigSpec;



public class ModConfig {

    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        COMMON = new Common(builder);
        COMMON_SPEC = builder.build();
    }

    public static class Common {

        public final ForgeConfigSpec.IntValue fey_dust_duration;
        public final ForgeConfigSpec.BooleanValue spawn_with_lexicon;

        public final ForgeConfigSpec.BooleanValue flower_particles;

        public final ForgeConfigSpec.BooleanValue voice_active;

        public Common(ForgeConfigSpec.Builder builder) {

            builder.comment("Feywild General Config").push("feywild");

            fey_dust_duration = builder
                    .comment("The duration in ticks for the levitation effect applied by fey dust. (Minimum: 1)")
                    .defineInRange("fey_dust_duration", 30, 1, 100);

            spawn_with_lexicon = builder
                    .comment("Whether players should spawn with a Feywild Lexicon")
                    .define("spawn_with_lexicon", true);

            flower_particles = builder
                    .comment("Whether giant flowers should have particles")
                    .define("flower_particles", true);

            voice_active = builder
                    .comment("Whether fey should have voice acting on")
                    .define("voices_active", true);

            builder.pop();
        }
    }
}
