package com.feywild.feywild.world.feature.structure.load;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.world.feature.structure.piece.BlacksmithStructurePiece;
import net.minecraft.core.Registry;

public class ModStructurePieces {

    //REGISTER HERE

    public static void setup() {
        Registry.register(Registry.STRUCTURE_POOL_ELEMENT, FeywildMod.getInstance().resource("blacksmith"), BlacksmithStructurePiece.TYPE);
        Registry.register(Registry.STRUCTURE_POOL_ELEMENT, FeywildMod.getInstance().resource("library"), BlacksmithStructurePiece.TYPE);
    }

}
