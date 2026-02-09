package com.saphienyako.feywild.item;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;


public class ModCreativeModeTab {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Feywild.MOD_ID);

    @SuppressWarnings("unused")
    public static final Supplier<CreativeModeTab> FEYWILD_TAB =
            CREATIVE_MODE_TABS.register("feywild_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("creative_tab.feywild_creative_tab"))
                    .icon(() -> new ItemStack(ModItems.SUMMONING_SCROLL_SPRING_PIXIE.get()))
                    .displayItems((displayParameters, output) -> {
                        //TODO change icon
                        output.accept(ModItems.FEYWILD_LEXICON.get());
                        output.accept(ModItems.FEY_DUST.get());
                        output.accept(ModItems.FEY_GEM.get());
                        output.accept(ModItems.MANDRAKE_ROOT.get());
                        output.accept(ModItems.MANDRAKE.get());
                        output.accept(ModItems.FEY_INK_BOTTLE.get());
                        output.accept(ModBlocks.FEY_ALTAR.get());
                        output.accept(ModItems.FEYWILD_MUSIC_DISC.get());
                        output.accept(ModItems.EMPTY_SUMMONING_SCROLL.get());
                        output.accept(ModItems.PIXIE_ORB.get());
                        output.accept(ModItems.SUMMONING_SCROLL_SPRING_PIXIE.get());
                        output.accept(ModItems.SUMMONING_SCROLL_SUMMER_PIXIE.get());
                        output.accept(ModItems.SUMMONING_SCROLL_AUTUMN_PIXIE.get());
                        output.accept(ModItems.SUMMONING_SCROLL_WINTER_PIXIE.get());
                        output.accept(ModItems.SUMMONING_SCROLL_SHROOMLING.get());
                        output.accept(ModItems.SUMMONING_SCROLL_MANDRAGORA.get());
                        output.accept(ModItems.SUMMONING_SCROLL_BELLSNICKEL.get());
                        output.accept(ModItems.GIANT_SUN_FLOWER_SEED.get());
                        output.accept(ModItems.GIANT_CROCUS_FLOWER_SEED.get());
                        output.accept(ModItems.GIANT_DANDELION_FLOWER_SEED.get());
                        output.accept(ModBlocks.FEY_GEM_ORE.get());
                        output.accept(ModBlocks.FEY_GEM_ORE_DEEP_SLATE.get());
                        output.accept(ModBlocks.ELVEN_QUARTZ_BLOCK.get());
                        output.accept(ModBlocks.ELVEN_QUARTZ_STAIRS.get());
                        output.accept(ModBlocks.ELVEN_QUARTZ_SLAB.get());
                        output.accept(ModBlocks.ELVEN_QUARTZ_BRICK.get());
                        output.accept(ModBlocks.ELVEN_QUARTZ_BRICK_STAIRS.get());
                        output.accept(ModBlocks.ELVEN_QUARTZ_BRICK_SLAB.get());
                        output.accept(ModBlocks.ELVEN_QUARTZ_MOSSY_BRICK.get());
                        output.accept(ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK.get());
                        output.accept(ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get());
                        output.accept(ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get());
                        output.accept(ModBlocks.ELVEN_QUARTZ_PILLAR.get());
                        output.accept(ModBlocks.ELVEN_QUARTZ_POLISHED.get());
                        output.accept(ModBlocks.ELVEN_QUARTZ_POLISHED_STAIRS.get());
                        output.accept(ModBlocks.ELVEN_QUARTZ_POLISHED_SLAB.get());

                        output.accept(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get());
                        output.accept(ModBlocks.SPRING_ELVEN_QUARTZ_STAIRS.get());
                        output.accept(ModBlocks.SPRING_ELVEN_QUARTZ_SLAB.get());
                        output.accept(ModBlocks.SPRING_ELVEN_QUARTZ_BRICK.get());
                        output.accept(ModBlocks.SPRING_ELVEN_QUARTZ_BRICK_STAIRS.get());
                        output.accept(ModBlocks.SPRING_ELVEN_QUARTZ_BRICK_SLAB.get());
                        output.accept(ModBlocks.SPRING_ELVEN_QUARTZ_MOSSY_BRICK.get());
                        output.accept(ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK.get());
                        output.accept(ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get());
                        output.accept(ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get());
                        output.accept(ModBlocks.SPRING_ELVEN_QUARTZ_PILLAR.get());
                        output.accept(ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED.get());
                        output.accept(ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED_STAIRS.get());
                        output.accept(ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED_SLAB.get());

                        output.accept(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get());
                        output.accept(ModBlocks.SUMMER_ELVEN_QUARTZ_STAIRS.get());
                        output.accept(ModBlocks.SUMMER_ELVEN_QUARTZ_SLAB.get());
                        output.accept(ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK.get());
                        output.accept(ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK_STAIRS.get());
                        output.accept(ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK_SLAB.get());
                        output.accept(ModBlocks.SUMMER_ELVEN_QUARTZ_MOSSY_BRICK.get());
                        output.accept(ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK.get());
                        output.accept(ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get());
                        output.accept(ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get());
                        output.accept(ModBlocks.SUMMER_ELVEN_QUARTZ_PILLAR.get());
                        output.accept(ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED.get());
                        output.accept(ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED_STAIRS.get());
                        output.accept(ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED_SLAB.get());

                        output.accept(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get());
                        output.accept(ModBlocks.WINTER_ELVEN_QUARTZ_STAIRS.get());
                        output.accept(ModBlocks.WINTER_ELVEN_QUARTZ_SLAB.get());
                        output.accept(ModBlocks.WINTER_ELVEN_QUARTZ_BRICK.get());
                        output.accept(ModBlocks.WINTER_ELVEN_QUARTZ_BRICK_STAIRS.get());
                        output.accept(ModBlocks.WINTER_ELVEN_QUARTZ_BRICK_SLAB.get());
                        output.accept(ModBlocks.WINTER_ELVEN_QUARTZ_MOSSY_BRICK.get());
                        output.accept(ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK.get());
                        output.accept(ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get());
                        output.accept(ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get());
                        output.accept(ModBlocks.WINTER_ELVEN_QUARTZ_PILLAR.get());
                        output.accept(ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED.get());
                        output.accept(ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED_STAIRS.get());
                        output.accept(ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED_SLAB.get());

                        output.accept(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get());
                        output.accept(ModBlocks.AUTUMN_ELVEN_QUARTZ_STAIRS.get());
                        output.accept(ModBlocks.AUTUMN_ELVEN_QUARTZ_SLAB.get());
                        output.accept(ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK.get());
                        output.accept(ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK_STAIRS.get());
                        output.accept(ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK_SLAB.get());
                        output.accept(ModBlocks.AUTUMN_ELVEN_QUARTZ_MOSSY_BRICK.get());
                        output.accept(ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK.get());
                        output.accept(ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get());
                        output.accept(ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get());
                        output.accept(ModBlocks.AUTUMN_ELVEN_QUARTZ_PILLAR.get());
                        output.accept(ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED.get());
                        output.accept(ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED_STAIRS.get());
                        output.accept(ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED_SLAB.get());

                        output.accept(ModBlocks.ORANGE_MUSHROOM.get());
                        output.accept(ModBlocks.ORANGE_MUSHROOM_BLOCK.get());
                        output.accept(ModBlocks.YELLOW_MUSHROOM.get());
                        output.accept(ModBlocks.YELLOW_MUSHROOM_BLOCK.get());
                        output.accept(ModBlocks.GREEN_MUSHROOM.get());
                        output.accept(ModBlocks.GREEN_MUSHROOM_BLOCK.get());
                        output.accept(ModBlocks.LIGHT_BLUE_MUSHROOM.get());
                        output.accept(ModBlocks.LIGHT_BLUE_MUSHROOM_BLOCK.get());
                        output.accept(ModBlocks.BLUE_MUSHROOM.get());
                        output.accept(ModBlocks.BLUE_MUSHROOM_BLOCK.get());
                        output.accept(ModBlocks.PURPLE_MUSHROOM.get());
                        output.accept(ModBlocks.PURPLE_MUSHROOM_BLOCK.get());
                        output.accept(ModBlocks.PINK_MUSHROOM.get());
                        output.accept(ModBlocks.PINK_MUSHROOM_BLOCK.get());

                        output.accept(ModItems.SPAWN_EGG_SPRING_PIXIE.get());
                        output.accept(ModItems.SPAWN_EGG_AUTUMN_PIXIE.get());
                        output.accept(ModItems.SPAWN_EGG_SUMMER_PIXIE.get());
                        output.accept(ModItems.SPAWN_EGG_WINTER_PIXIE.get());
                        output.accept(ModItems.SPAWN_EGG_SHROOMLING.get());
                        output.accept(ModItems.SPAWN_EGG_MANDRAGORA.get());
                        output.accept(ModItems.SPAWN_EGG_BELLSNICKEL.get());

                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
