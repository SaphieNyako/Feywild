package com.saphienyako.feywild.worldgen.processor;

import com.saphienyako.feywild.Feywild;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FeywildProcessors {


    public static final DeferredRegister<StructureProcessorType<?>> PROCESSORS =
            DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, Feywild.MOD_ID);
    public static final Supplier<StructureProcessorType<AutumnTreeProcessor>> AUTUMN_TREE =
            PROCESSORS.register("autumn_tree", () -> () -> AutumnTreeProcessor.MAP_CODEC);

    public static final Supplier<StructureProcessorType<SpringTreeProcessor>> SPRING_TREE =
            PROCESSORS.register("spring_tree", () -> () -> SpringTreeProcessor.MAP_CODEC);

    public static final Supplier<StructureProcessorType<SummerTreeProcessor>> SUMMER_TREE =
            PROCESSORS.register("summer_tree", () -> () -> SummerTreeProcessor.MAP_CODEC);

    public static final Supplier<StructureProcessorType<WinterTreeProcessor>> WINTER_TREE =
            PROCESSORS.register("winter_tree", () -> () -> WinterTreeProcessor.MAP_CODEC);

}
