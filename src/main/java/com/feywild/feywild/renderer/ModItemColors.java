package com.feywild.feywild.renderer;

import com.feywild.feywild.block.ModBlocks;
import net.minecraft.world.level.GrassColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItemColors {

    private ModItemColors() {}

    @SubscribeEvent
    public static void initItemColors(ColorHandlerEvent.Item event) {

        event.getItemColors().register((stack, color) -> {
            return GrassColor.get(0.0D, 0.5D);
        }, ModBlocks.elvenQuartzMossyBrick, ModBlocks.elvenAutumnQuartzMossyBrick, ModBlocks.elvenSpringQuartzMossyBrick, ModBlocks.elvenSummerQuartzMossyBrick, ModBlocks.elvenWinterQuartzMossyBrick);

    }
}
