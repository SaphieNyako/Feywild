package com.feywild.feywild.world.dimension.feywild.teleporter;

import com.feywild.feywild.block.ModBlocks;
import org.moddingx.libx.annotation.registration.RegisterClass;
import net.minecraft.world.entity.ai.village.poi.PoiType;

@RegisterClass
public class PortalPOI {

    //Block is inside of the portal.
    // This is not the same as the block that is added to the tags/blocks/portal_frame_blocks.json

    public static final PoiType feywildPortal = new PoiType("feywild_portal",
            PoiType.getBlockStates(ModBlocks.feyPortalBlock), 0, 1);
}
