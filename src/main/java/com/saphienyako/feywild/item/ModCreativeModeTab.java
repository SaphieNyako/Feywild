package com.saphienyako.feywild.item;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTab {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Feywild.MOD_ID);

    public static final RegistryObject<CreativeModeTab> FEYWILD_TAB = CREATIVE_MODE_TAB.register("feywild_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.SUMMONING_SCROLL_SPRING_PIXIE.get()))
                    .title(Component.translatable("creative_tab.feywild_creative_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.FEYWILD_LEXICON.get());
                        pOutput.accept(ModItems.FEY_DUST.get());
                        pOutput.accept(ModItems.FEY_GEM.get());
                        pOutput.accept(ModItems.MANDRAKE_ROOT.get());
                        pOutput.accept(ModItems.MANDRAKE.get());
                        pOutput.accept(ModItems.FEY_INK_BOTTLE.get());
                        pOutput.accept(ModBlocks.FEY_ALTAR.get());
                        pOutput.accept(ModItems.FEYWILD_MUSIC_DISC.get());
                        pOutput.accept(ModItems.EMPTY_SUMMONING_SCROLL.get());
                        pOutput.accept(ModItems.PIXIE_ORB.get());
                        pOutput.accept(ModItems.SUMMONING_SCROLL_SPRING_PIXIE.get());
                        pOutput.accept(ModItems.SUMMONING_SCROLL_SUMMER_PIXIE.get());
                        pOutput.accept(ModItems.SUMMONING_SCROLL_AUTUMN_PIXIE.get());
                        pOutput.accept(ModItems.SUMMONING_SCROLL_WINTER_PIXIE.get());
                        pOutput.accept(ModItems.SUMMONING_SCROLL_SHROOMLING.get());
                        pOutput.accept(ModItems.SUMMONING_SCROLL_MANDRAGORA.get());
                        pOutput.accept(ModItems.GIANT_SUN_FLOWER_SEED.get());
                        pOutput.accept(ModItems.GIANT_CROCUS_FLOWER_SEED.get());
                        pOutput.accept(ModItems.GIANT_DANDELION_FLOWER_SEED.get());
                        pOutput.accept(ModBlocks.FEY_GEM_ORE.get());
                        pOutput.accept(ModBlocks.FEY_GEM_ORE_DEEP_SLATE.get());
                        pOutput.accept(ModBlocks.ELVEN_QUARTZ_BLOCK.get());
                        pOutput.accept(ModBlocks.ELVEN_QUARTZ_STAIRS.get());
                        pOutput.accept(ModBlocks.ELVEN_QUARTZ_SLAB.get());
                        pOutput.accept(ModBlocks.ELVEN_QUARTZ_BRICK.get());
                        pOutput.accept(ModBlocks.ELVEN_QUARTZ_BRICK_STAIRS.get());
                        pOutput.accept(ModBlocks.ELVEN_QUARTZ_BRICK_SLAB.get());
                        pOutput.accept(ModBlocks.ELVEN_QUARTZ_MOSSY_BRICK.get());
                        pOutput.accept(ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK.get());
                        pOutput.accept(ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get());
                        pOutput.accept(ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get());
                        pOutput.accept(ModBlocks.ELVEN_QUARTZ_PILLAR.get());
                        pOutput.accept(ModBlocks.ELVEN_QUARTZ_POLISHED.get());
                        pOutput.accept(ModBlocks.ELVEN_QUARTZ_POLISHED_STAIRS.get());
                        pOutput.accept(ModBlocks.ELVEN_QUARTZ_POLISHED_SLAB.get());

                        pOutput.accept(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get());
                        pOutput.accept(ModBlocks.SPRING_ELVEN_QUARTZ_STAIRS.get());
                        pOutput.accept(ModBlocks.SPRING_ELVEN_QUARTZ_SLAB.get());
                        pOutput.accept(ModBlocks.SPRING_ELVEN_QUARTZ_BRICK.get());
                        pOutput.accept(ModBlocks.SPRING_ELVEN_QUARTZ_BRICK_STAIRS.get());
                        pOutput.accept(ModBlocks.SPRING_ELVEN_QUARTZ_BRICK_SLAB.get());
                        pOutput.accept(ModBlocks.SPRING_ELVEN_QUARTZ_MOSSY_BRICK.get());
                        pOutput.accept(ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK.get());
                        pOutput.accept(ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get());
                        pOutput.accept(ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get());
                        pOutput.accept(ModBlocks.SPRING_ELVEN_QUARTZ_PILLAR.get());
                        pOutput.accept(ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED.get());
                        pOutput.accept(ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED_STAIRS.get());
                        pOutput.accept(ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED_SLAB.get());

                        pOutput.accept(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get());
                        pOutput.accept(ModBlocks.SUMMER_ELVEN_QUARTZ_STAIRS.get());
                        pOutput.accept(ModBlocks.SUMMER_ELVEN_QUARTZ_SLAB.get());
                        pOutput.accept(ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK.get());
                        pOutput.accept(ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK_STAIRS.get());
                        pOutput.accept(ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK_SLAB.get());
                        pOutput.accept(ModBlocks.SUMMER_ELVEN_QUARTZ_MOSSY_BRICK.get());
                        pOutput.accept(ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK.get());
                        pOutput.accept(ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get());
                        pOutput.accept(ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get());
                        pOutput.accept(ModBlocks.SUMMER_ELVEN_QUARTZ_PILLAR.get());
                        pOutput.accept(ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED.get());
                        pOutput.accept(ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED_STAIRS.get());
                        pOutput.accept(ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED_SLAB.get());

                        pOutput.accept(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get());
                        pOutput.accept(ModBlocks.WINTER_ELVEN_QUARTZ_STAIRS.get());
                        pOutput.accept(ModBlocks.WINTER_ELVEN_QUARTZ_SLAB.get());
                        pOutput.accept(ModBlocks.WINTER_ELVEN_QUARTZ_BRICK.get());
                        pOutput.accept(ModBlocks.WINTER_ELVEN_QUARTZ_BRICK_STAIRS.get());
                        pOutput.accept(ModBlocks.WINTER_ELVEN_QUARTZ_BRICK_SLAB.get());
                        pOutput.accept(ModBlocks.WINTER_ELVEN_QUARTZ_MOSSY_BRICK.get());
                        pOutput.accept(ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK.get());
                        pOutput.accept(ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get());
                        pOutput.accept(ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get());
                        pOutput.accept(ModBlocks.WINTER_ELVEN_QUARTZ_PILLAR.get());
                        pOutput.accept(ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED.get());
                        pOutput.accept(ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED_STAIRS.get());
                        pOutput.accept(ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED_SLAB.get());

                        pOutput.accept(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get());
                        pOutput.accept(ModBlocks.AUTUMN_ELVEN_QUARTZ_STAIRS.get());
                        pOutput.accept(ModBlocks.AUTUMN_ELVEN_QUARTZ_SLAB.get());
                        pOutput.accept(ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK.get());
                        pOutput.accept(ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK_STAIRS.get());
                        pOutput.accept(ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK_SLAB.get());
                        pOutput.accept(ModBlocks.AUTUMN_ELVEN_QUARTZ_MOSSY_BRICK.get());
                        pOutput.accept(ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK.get());
                        pOutput.accept(ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get());
                        pOutput.accept(ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get());
                        pOutput.accept(ModBlocks.AUTUMN_ELVEN_QUARTZ_PILLAR.get());
                        pOutput.accept(ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED.get());
                        pOutput.accept(ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED_STAIRS.get());
                        pOutput.accept(ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED_SLAB.get());

                        pOutput.accept(ModBlocks.ORANGE_MUSHROOM.get());
                        pOutput.accept(ModBlocks.ORANGE_MUSHROOM_BLOCK.get());
                        pOutput.accept(ModBlocks.YELLOW_MUSHROOM.get());
                        pOutput.accept(ModBlocks.YELLOW_MUSHROOM_BLOCK.get());
                        pOutput.accept(ModBlocks.GREEN_MUSHROOM.get());
                        pOutput.accept(ModBlocks.GREEN_MUSHROOM_BLOCK.get());
                        pOutput.accept(ModBlocks.LIGHT_BLUE_MUSHROOM.get());
                        pOutput.accept(ModBlocks.LIGHT_BLUE_MUSHROOM_BLOCK.get());
                        pOutput.accept(ModBlocks.BLUE_MUSHROOM.get());
                        pOutput.accept(ModBlocks.BLUE_MUSHROOM_BLOCK.get());
                        pOutput.accept(ModBlocks.PURPLE_MUSHROOM.get());
                        pOutput.accept(ModBlocks.PURPLE_MUSHROOM_BLOCK.get());
                        pOutput.accept(ModBlocks.PINK_MUSHROOM.get());
                        pOutput.accept(ModBlocks.PINK_MUSHROOM_BLOCK.get());
                        pOutput.accept(ModItems.SPAWN_EGG_SPRING_PIXIE.get());
                        pOutput.accept(ModItems.SPAWN_EGG_AUTUMN_PIXIE.get());
                        pOutput.accept(ModItems.SPAWN_EGG_SUMMER_PIXIE.get());
                        pOutput.accept(ModItems.SPAWN_EGG_WINTER_PIXIE.get());
                        pOutput.accept(ModItems.SPAWN_EGG_SHROOMLING.get());
                        pOutput.accept(ModItems.SPAWN_EGG_MANDRAGORA.get());
                    })
                    .build());


    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
