package com.feywild.feywild.world.feature.structure.load;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.world.feature.structure.piece.*;
import net.minecraft.core.Registry;

public class ModStructurePieces {

    //REGISTER HERE

    public static void setup() {
        Registry.register(Registry.STRUCTURE_POOL_ELEMENT, FeywildMod.getInstance().resource("blacksmith"), BlacksmithStructurePiece.TYPE);
        Registry.register(Registry.STRUCTURE_POOL_ELEMENT, FeywildMod.getInstance().resource("library"), BlacksmithStructurePiece.TYPE);
        Registry.register(Registry.STRUCTURE_POOL_ELEMENT, FeywildMod.getInstance().resource("beekeep"), BeeKeepStructurePiece.TYPE);
        Registry.register(Registry.STRUCTURE_POOL_ELEMENT, FeywildMod.getInstance().resource("spring_world_tree"), SpringWorldTreePiece.TYPE);
        Registry.register(Registry.STRUCTURE_POOL_ELEMENT, FeywildMod.getInstance().resource("autumn_world_tree"), AutumnWorldTreePiece.TYPE);
        Registry.register(Registry.STRUCTURE_POOL_ELEMENT, FeywildMod.getInstance().resource("summer_world_tree"), SummerWorldTreePiece.TYPE);
        Registry.register(Registry.STRUCTURE_POOL_ELEMENT, FeywildMod.getInstance().resource("winter_world_tree"), WinterWorldTreePiece.TYPE);
        Registry.register(Registry.STRUCTURE_POOL_ELEMENT, FeywildMod.getInstance().resource("fey_circle"), FeyCircleStructurePiece.TYPE);
    }

}
