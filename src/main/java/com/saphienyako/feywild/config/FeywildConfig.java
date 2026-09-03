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
    public static int feyDustDuration;

    private static final ModConfigSpec.BooleanValue SPAWN_WITH_LEXICON;
    public static boolean spawnWithLexicon;

    private static final ModConfigSpec.BooleanValue VOICES_ACTIVE;
    public static boolean voicesActive;

    private static final ModConfigSpec.DoubleValue TREE_ENT_ATTACK_DAMAGE;
    public static double treeEntAttackDamage;

    private static final ModConfigSpec.IntValue TREE_ENT_SPAWN_SAPLING;
    public static int treeEntSpawnSapling;

    private static final ModConfigSpec.IntValue TREE_ENT_SPAWN_WORLD;
    public static int treeEntSpawnWorld;

    private static final ModConfigSpec.BooleanValue TREE_ENT_ATTACK_PLAYERS;
    public static boolean treeEntAttackPlayers;

    private static final ModConfigSpec.IntValue AUTUMN_PIXIE_SPAWN_WEIGHT;
    private static final ModConfigSpec.IntValue SPRING_PIXIE_SPAWN_WEIGHT;
    private static final ModConfigSpec.IntValue SUMMER_PIXIE_SPAWN_WEIGHT;
    private static final ModConfigSpec.IntValue WINTER_PIXIE_SPAWN_WEIGHT;
    private static final ModConfigSpec.IntValue MANDRAGORA_SPAWN_WEIGHT;

    private static final ModConfigSpec.IntValue BEE_KNIGHT_SPAWN_WEIGHT;
    private static final ModConfigSpec.IntValue BELLSNICKEL_SPAWN_WEIGHT;
    private static final ModConfigSpec.IntValue SHROOMLING_SPAWN_WEIGHT;

    public static int autumnPixieSpawnWeight;
    public static int springPixieSpawnWeight;
    public static int summerPixieSpawnWeight;
    public static int winterPixieSpawnWeight;
    public static int mandragoraSpawnWeight;
    public static int beeKnightSpawnWeight;
    public static int bellsnickelSpawnWeight;
    public static int shroomlingSpawnWeight;

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

        AUTUMN_PIXIE_SPAWN_WEIGHT = COMMON_BUILDER
                .comment("Spawn weight of Autumn Pixies. Set to 0 to disable natural spawning.")
                .defineInRange("autumn_pixie_spawn_weight", 2, 0, 1000);

        SPRING_PIXIE_SPAWN_WEIGHT = COMMON_BUILDER
                .comment("Spawn weight of Spring Pixies. Set to 0 to disable natural spawning.")
                .defineInRange("spring_pixie_spawn_weight", 2, 0, 1000);

        SUMMER_PIXIE_SPAWN_WEIGHT = COMMON_BUILDER
                .comment("Spawn weight of Summer Pixies. Set to 0 to disable natural spawning.")
                .defineInRange("summer_pixie_spawn_weight", 2, 0, 1000);

        WINTER_PIXIE_SPAWN_WEIGHT = COMMON_BUILDER
                .comment("Spawn weight of Winter Pixies. Set to 0 to disable natural spawning.")
                .defineInRange("winter_pixie_spawn_weight", 2, 0, 1000);

        MANDRAGORA_SPAWN_WEIGHT = COMMON_BUILDER
                .comment("Spawn weight of Mandragoras. Set to 0 to disable natural spawning.")
                .defineInRange("mandragora_spawn_weight", 3, 0, 1000);

        BEE_KNIGHT_SPAWN_WEIGHT = COMMON_BUILDER
                .comment("Spawn weight of the Bee Knights. Set to 0 to disable natural spawning.")
                .defineInRange("bee_knight_spawn_weight", 3, 0, 1000);

        BELLSNICKEL_SPAWN_WEIGHT = COMMON_BUILDER
                .comment("Spawn weight of Bellsnickels. Set to 0 to disable natural spawning.")
                .defineInRange("bellsnickel_spawn_weight", 5, 0, 1000);

        SHROOMLING_SPAWN_WEIGHT = COMMON_BUILDER
                .comment("Spawn weight of Shroomlings. Set to 0 to disable natural spawning.")
                .defineInRange("shroomling_spawn_weight", 5, 0, 1000);


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
            autumnPixieSpawnWeight = AUTUMN_PIXIE_SPAWN_WEIGHT.get();
            springPixieSpawnWeight = SPRING_PIXIE_SPAWN_WEIGHT.get();
            summerPixieSpawnWeight = SUMMER_PIXIE_SPAWN_WEIGHT.get();
            winterPixieSpawnWeight = WINTER_PIXIE_SPAWN_WEIGHT.get();
            mandragoraSpawnWeight = MANDRAGORA_SPAWN_WEIGHT.get();
            beeKnightSpawnWeight = BEE_KNIGHT_SPAWN_WEIGHT.get();
            bellsnickelSpawnWeight = BELLSNICKEL_SPAWN_WEIGHT.get();
            shroomlingSpawnWeight = SHROOMLING_SPAWN_WEIGHT.get();
        }

        if (event.getConfig().getSpec() == CLIENT_SPEC) {
            flowerParticles = FLOWER_PARTICLES.get();
        }
    }

}

