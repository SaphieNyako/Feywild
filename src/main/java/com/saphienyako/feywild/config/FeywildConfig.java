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
    private static final ModConfigSpec.BooleanValue VOICES_ACTIVE;

    private static final ModConfigSpec.DoubleValue TREE_ENT_ATTACK_DAMAGE;

    private static final ModConfigSpec.IntValue TREE_ENT_SPAWN_SAPLING;
    private static final ModConfigSpec.IntValue TREE_ENT_SPAWN_WORLD;

    private static final ModConfigSpec.BooleanValue TREE_ENT_ATTACK_PLAYERS;
    public static int feyDustDuration;
    public static boolean spawnWithLexicon;

    public static boolean voicesActive;
    public static double treeEntAttackDamage;

    public static int treeEntSpawnSapling;
    public static int treeEntSpawnWorld;
    public static boolean treeEntAttackPlayers;

    //CLIENT
    private static final ModConfigSpec.Builder CLIENT_BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec CLIENT_SPEC;

    private static final ModConfigSpec.BooleanValue FLOWER_PARTICLES;


    public static boolean flowerParticles;


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

        VOICES_ACTIVE = COMMON_BUILDER
                .comment("Whether fey should have voice acting on")
                .define("voices_active", true);

        TREE_ENT_ATTACK_DAMAGE = COMMON_BUILDER
                .comment("Attack damage of Tree Ents")
                .defineInRange("tree_ent_attack_damage", 12.0, 0.0, 30.0);


        TREE_ENT_SPAWN_SAPLING = COMMON_BUILDER
                .comment("Chance for Tree Ent logs to be cracked when grown from a sapling. 1 in N chance")
                .defineInRange("tree_ent_spawn_sapling", 1200, 1, 10000);

        TREE_ENT_SPAWN_WORLD = COMMON_BUILDER
                .comment("Chance for Tree Ent logs to be cracked when spawned naturally in world. 1 in N chance")
                .defineInRange("tree_ent_spawn_world", 100, 1, 10000);

        TREE_ENT_ATTACK_PLAYERS = COMMON_BUILDER
                .comment("Whether Tree Ents should attack players")
                .define("tree_ent_attack_players", true);

        COMMON_BUILDER.pop();
        COMMON_SPEC = COMMON_BUILDER.build();

        //CLIENT
        CLIENT_BUILDER
                .comment("Feywild Client Config")
                .push("client");

        FLOWER_PARTICLES = CLIENT_BUILDER
                .comment("Whether giant flowers should have particles")
                .define("flower_particles", true);

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
            voicesActive = VOICES_ACTIVE.get();
            treeEntAttackDamage = TREE_ENT_ATTACK_DAMAGE.get();
            treeEntSpawnSapling = TREE_ENT_SPAWN_SAPLING.get();
            treeEntSpawnWorld = TREE_ENT_SPAWN_WORLD.get();
            treeEntAttackPlayers = TREE_ENT_ATTACK_PLAYERS.get();
        }

        if (event.getConfig().getSpec() == CLIENT_SPEC) {
            flowerParticles = FLOWER_PARTICLES.get();

        }
    }

}

