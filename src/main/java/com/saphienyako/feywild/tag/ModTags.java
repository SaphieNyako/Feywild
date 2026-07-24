package com.saphienyako.feywild.tag;

import com.saphienyako.feywild.Feywild;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public final class ModTags {

    private ModTags() {
    }

    public static final class Blocks {

        public static final TagKey<Block> AUTUMN_LOGS =
                create("autumn_logs");

        public static final TagKey<Block> SPRING_LOGS =
                create("spring_logs");

        public static final TagKey<Block> SUMMER_LOGS =
                create("summer_logs");

        public static final TagKey<Block> WINTER_LOGS =
                create("winter_logs");

        public static final TagKey<Block> FEY_ALTARS =
                create("fey_altars");

        private static TagKey<Block> create(String name) {
            return TagKey.create(
                    Registries.BLOCK,
                    ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, name)
            );
        }

        private Blocks() {
        }
    }

    public static final class Items {

        public static final TagKey<Item> AUTUMN_LOGS =
                create("autumn_logs");

        public static final TagKey<Item> SPRING_LOGS =
                create("spring_logs");

        public static final TagKey<Item> SUMMER_LOGS =
                create("summer_logs");

        public static final TagKey<Item> WINTER_LOGS =
                create("winter_logs");

        public static final TagKey<Item> FEY_ALTARS =
                create("fey_altars");


        public static final TagKey<Item> PIXIE_WINGS =
                create("pixie_wings");

        private static TagKey<Item> create(String name) {
            return TagKey.create(
                    Registries.ITEM,
                    ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, name)
            );
        }

        private Items() {
        }
    }
}
