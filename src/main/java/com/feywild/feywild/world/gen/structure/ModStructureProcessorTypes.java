package com.feywild.feywild.world.gen.structure;

import com.feywild.feywild.world.gen.tree.TreeProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import org.moddingx.libx.annotation.registration.RegisterClass;

@RegisterClass(registry = "STRUCTURE_PROCESSOR")
public class ModStructureProcessorTypes {
    
    public static final StructureProcessorType<?> treeProcessor = TreeProcessor.TYPE;
}
