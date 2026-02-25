package com.saphienyako.feywild.worldgen.processor;

import com.saphienyako.feywild.Feywild;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FeywildProcessors {


    public static final DeferredRegister<StructureProcessorType<?>> PROCESSORS =
            DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, Feywild.MOD_ID);
    public static final Supplier<StructureProcessorType<FeyTreeProcessor>> FEY_TREE =
            PROCESSORS.register("fey_tree", () -> () -> FeyTreeProcessor.MAP_CODEC);
}
