package com.feywild.feywild.world;

import mythicbotany.alfheim.AlfheimBiomeManager;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.ModList;

import java.util.Set;

public class AlfheimCompat {
    
    public static boolean isAlfheim(Set<BiomeDictionary.Type> types) {
        if (ModList.get().isLoaded("mythicbotany")) {
            try {
                return types.contains(AlfheimBiomeManager.ALFHEIM);
            } catch (NoClassDefFoundError e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }
}
