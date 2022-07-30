package com.feywild.feywild.renderer;

import com.feywild.feywild.block.ModBlocks;
import net.minecraft.world.level.GrassColor;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModItemColors {

    private ModItemColors() {
    }

    @SubscribeEvent
    public static void initItemColors(RegisterColorHandlersEvent.Item event) {

        event.getItemColors().register((stack, color) -> {
            return GrassColor.get(0, 0);
        }, ModBlocks.elvenQuartzMossyBrick, ModBlocks.elvenAutumnQuartzMossyBrick, ModBlocks.elvenSpringQuartzMossyBrick, ModBlocks.elvenSummerQuartzMossyBrick, ModBlocks.elvenWinterQuartzMossyBrick);

    }
}
