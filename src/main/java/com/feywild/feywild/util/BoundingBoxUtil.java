package com.feywild.feywild.util;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public class BoundingBoxUtil {
    
    public static AABB get(BoundingBox box) {
        return new AABB(box.minX(), box.minY(), box.minZ(), box.maxX(), box.maxY(), box.maxZ());
    }
}
