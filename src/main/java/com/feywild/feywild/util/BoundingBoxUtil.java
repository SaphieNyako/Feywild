package com.feywild.feywild.util;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MutableBoundingBox;

public class BoundingBoxUtil {
    
    public static AxisAlignedBB get(MutableBoundingBox box) {
        return new AxisAlignedBB(box.x0, box.y0, box.z0, box.x1, box.y1, box.z1);
    }
}
