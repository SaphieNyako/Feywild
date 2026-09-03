package com.saphienyako.feywild.config;


import net.minecraftforge.common.ForgeConfigSpec;



public class ModConfig {

    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final Client CLIENT;


    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        COMMON = new Common(builder);
        COMMON_SPEC = builder.build();

        ForgeConfigSpec.Builder clientBuilder = new ForgeConfigSpec.Builder();
        CLIENT = new Client(clientBuilder);
        CLIENT_SPEC = clientBuilder.build();
    }

    public static class Common {

        public final ForgeConfigSpec.IntValue fey_dust_duration;
        public final ForgeConfigSpec.BooleanValue spawn_with_lexicon;

        public final ForgeConfigSpec.BooleanValue voice_active;

        public final ForgeConfigSpec.DoubleValue treeEntAttackDamage;

        public final ForgeConfigSpec.IntValue treeEntSpawnSapling;
        public final ForgeConfigSpec.IntValue treeEntSpawnWorld;
        public final ForgeConfigSpec.BooleanValue treeEntAttackPlayers;

        public final ForgeConfigSpec.IntValue autumnPixieSpawnWeight;
        public final ForgeConfigSpec.IntValue springPixieSpawnWeight;
        public final ForgeConfigSpec.IntValue summerPixieSpawnWeight;
        public final ForgeConfigSpec.IntValue winterPixieSpawnWeight;

        public final ForgeConfigSpec.IntValue mandragoraSpawnWeight;

        public final ForgeConfigSpec.IntValue beeKnightSpawnWeight;
        public final ForgeConfigSpec.IntValue bellsnickelSpawnWeight;
        public final ForgeConfigSpec.IntValue shroomlingSpawnWeight;

        public Common(ForgeConfigSpec.Builder builder) {

            builder.comment("Feywild General Config").push("feywild");

            fey_dust_duration = builder
                    .comment("The duration in ticks for the levitation effect applied by fey dust. (Minimum: 1)")
                    .defineInRange("fey_dust_duration", 30, 1, 100);

            spawn_with_lexicon = builder
                    .comment("Whether players should spawn with a Feywild Lexicon")
                    .define("spawn_with_lexicon", true);

            voice_active = builder
                    .comment("Whether fey should have voice acting on")
                    .define("voices_active", true);

            treeEntAttackDamage = builder
                    .comment("Attack damage of Tree Ents")
                    .defineInRange("tree_ent_attack_damage", 12.0, 0.0, 30.0);


            treeEntSpawnSapling = builder
                    .comment("Chance for Tree Ent logs to be cracked when grown from a sapling. 1 in N chance")
                    .defineInRange("tree_ent_spawn_sapling", 1200, 1, 10000);

            treeEntSpawnWorld = builder
                    .comment("Chance for Tree Ent logs to be cracked when spawned naturally in world. 1 in N chance")
                    .defineInRange("tree_ent_spawn_world", 100, 1, 10000);

            treeEntAttackPlayers = builder
                    .comment("Whether Tree Ents should attack players")
                    .define("tree_ent_attack_players", true);

            autumnPixieSpawnWeight = builder
                    .comment("Spawn weight of Autumn Pixies. Set to 0 to disable natural spawning.")
                    .defineInRange("autumn_pixie_spawn_weight", 2, 0, 1000);

            springPixieSpawnWeight = builder
                    .comment("Spawn weight of Spring Pixies. Set to 0 to disable natural spawning.")
                    .defineInRange("spring_pixie_spawn_weight", 2, 0, 1000);

            summerPixieSpawnWeight = builder
                    .comment("Spawn weight of Summer Pixies. Set to 0 to disable natural spawning.")
                    .defineInRange("summer_pixie_spawn_weight", 2, 0, 1000);

            winterPixieSpawnWeight = builder
                    .comment("Spawn weight of Winter Pixies. Set to 0 to disable natural spawning.")
                    .defineInRange("winter_pixie_spawn_weight", 2, 0, 1000);

            mandragoraSpawnWeight = builder
                    .comment("Spawn weight of Mandragoras. Set to 0 to disable natural spawning.")
                    .defineInRange("mandragora_spawn_weight", 3, 0, 1000);

            beeKnightSpawnWeight = builder
                    .comment("Spawn weight of Bee Knights. Set to 0 to disable natural spawning.")
                    .defineInRange("bee_knight_spawn_weight", 3, 0, 1000);

            bellsnickelSpawnWeight = builder
                    .comment("Spawn weight of Bellsnickels. Set to 0 to disable natural spawning.")
                    .defineInRange("bellsnickel_spawn_weight", 5, 0, 1000);

            shroomlingSpawnWeight = builder
                    .comment("Spawn weight of Shroomlings. Set to 0 to disable natural spawning.")
                    .defineInRange("shroomling_spawn_weight", 5, 0, 1000);

            builder.pop();
        }
    }

    public static class Client {

        public final ForgeConfigSpec.BooleanValue flower_particles;

        public Client(ForgeConfigSpec.Builder builder) {
            builder.comment("Feywild Client Config").push("client");

            flower_particles = builder
                    .comment("Whether giant flowers should have particles")
                    .define("flower_particles", true);


            builder.pop();
        }
    }
}
