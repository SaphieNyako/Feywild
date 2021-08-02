package com.feywild.feywild.data;

import com.feywild.feywild.FeywildMod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

public class DataGenerators {
    
    public static void gatherData(GatherDataEvent event) {
        event.getGenerator().addProvider(new BlockStates(FeywildMod.getInstance(), event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(new ItemModels(FeywildMod.getInstance(), event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(new Advancements(FeywildMod.getInstance(), event.getGenerator()));
        event.getGenerator().addProvider(new BlockLoot(FeywildMod.getInstance(), event.getGenerator()));
    }
}
