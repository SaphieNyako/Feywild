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
