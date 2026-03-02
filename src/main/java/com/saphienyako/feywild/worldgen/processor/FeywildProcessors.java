package com.saphienyako.feywild.worldgen.processor;

import com.mojang.serialization.Codec;
import com.saphienyako.feywild.Feywild;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class FeywildProcessors {

    // Autumn Tree Processor
    public static final StructureProcessorType<AutumnTreeProcessor> AUTUMN_TREE =
            Registry.register(
                    Registry.STRUCTURE_PROCESSOR,
                    new ResourceLocation(Feywild.MOD_ID, "autumn_tree"),
                    new StructureProcessorType<AutumnTreeProcessor>() {
                        @Override
                        public Codec<AutumnTreeProcessor> codec() {
                            return AutumnTreeProcessor.CODEC;
                        }
                    }
            );

    // Spring Tree Processor
    public static final StructureProcessorType<SpringTreeProcessor> SPRING_TREE =
            Registry.register(
                    Registry.STRUCTURE_PROCESSOR,
                    new ResourceLocation(Feywild.MOD_ID, "spring_tree"),
                    new StructureProcessorType<SpringTreeProcessor>() {
                        @Override
                        public Codec<SpringTreeProcessor> codec() {
                            return SpringTreeProcessor.CODEC;
                        }
                    }
            );

    // Summer Tree Processor
    public static final StructureProcessorType<SummerTreeProcessor> SUMMER_TREE =
            Registry.register(
                    Registry.STRUCTURE_PROCESSOR,
                    new ResourceLocation(Feywild.MOD_ID, "summer_tree"),
                    new StructureProcessorType<SummerTreeProcessor>() {
                        @Override
                        public Codec<SummerTreeProcessor> codec() {
                            return SummerTreeProcessor.CODEC;
                        }
                    }
            );

    // Winter Tree Processor
    public static final StructureProcessorType<WinterTreeProcessor> WINTER_TREE =
            Registry.register(
                    Registry.STRUCTURE_PROCESSOR,
                    new ResourceLocation(Feywild.MOD_ID, "winter_tree"),
                    new StructureProcessorType<WinterTreeProcessor>() {
                        @Override
                        public Codec<WinterTreeProcessor> codec() {
                            return WinterTreeProcessor.CODEC;
                        }
                    }
            );

    public static void register() {

    }

}
