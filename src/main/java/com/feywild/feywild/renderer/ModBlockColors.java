package com.feywild.feywild.renderer;

import com.feywild.feywild.block.ModBlocks;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.GrassColor;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModBlockColors {

    public static final BlockColor GRASS_COLOR = setDynamicBlockColorProviderGrass(1, 0.5);

    public static BlockColor setDynamicBlockColorProviderGrass(double temp, double humidity) {
        return (unknown, lightReader, pos, unknown2) -> {
            return BiomeColors.getAverageGrassColor(lightReader, pos);
        };
    }

    @SubscribeEvent
    public static void onBlockColorsInit(ColorHandlerEvent.Item event) {
        BlockColors blockColors = event.getBlockColors();

        blockColors.register((state, reader, pos, color) -> {
            return reader != null && pos != null ? BiomeColors.getAverageGrassColor(reader, pos) : GrassColor.get(0.5, 0.5);
        }, ModBlocks.elvenQuartzMossyBrick, ModBlocks.elvenAutumnQuartzMossyBrick, ModBlocks.elvenSpringQuartzMossyBrick, ModBlocks.elvenSummerQuartzMossyBrick, ModBlocks.elvenWinterQuartzMossyBrick);
    }

}
