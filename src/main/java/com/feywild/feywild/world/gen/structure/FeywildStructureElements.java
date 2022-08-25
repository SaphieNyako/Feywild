package com.feywild.feywild.world.gen.structure;

import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import org.moddingx.libx.annotation.registration.RegisterClass;

@RegisterClass(registry = "STRUCTURE_POOL_ELEMENT_REGISTRY")
public class FeywildStructureElements {
    
    public static final StructurePoolElementType<?> elementType = (StructurePoolElementType<FeywildStructurePoolElement>) () -> FeywildStructurePoolElement.CODEC;
}
