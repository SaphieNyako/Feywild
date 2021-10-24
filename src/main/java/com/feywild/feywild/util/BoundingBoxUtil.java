package com.feywild.feywild.util;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public class BoundingBoxUtil {
    
    public static AABB get(BoundingBox box) {
        return new AABB(box.x0, box.y0, box.z0, box.x1, box.y1, box.z1);
    }
}
