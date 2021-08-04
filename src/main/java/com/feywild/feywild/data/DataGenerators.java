package com.feywild.feywild.data;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.data.recipe.*;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

public class DataGenerators {
    
    public static void gatherData(GatherDataEvent event) {
        event.getGenerator().addProvider(new BlockStates(FeywildMod.getInstance(), event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(new ItemModels(FeywildMod.getInstance(), event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(new Advancements(FeywildMod.getInstance(), event.getGenerator()));
        event.getGenerator().addProvider(new BlockLoot(FeywildMod.getInstance(), event.getGenerator()));
        BlockTagProvider blockTags = new BlockTagProvider(FeywildMod.getInstance(), event.getGenerator(), event.getExistingFileHelper());
        event.getGenerator().addProvider(blockTags);
        event.getGenerator().addProvider(new ItemTagProvider(FeywildMod.getInstance(), event.getGenerator(), event.getExistingFileHelper(), blockTags));
        
        event.getGenerator().addProvider(new CraftingRecipes(FeywildMod.getInstance(), event.getGenerator()));
        event.getGenerator().addProvider(new SmeltingRecipes(FeywildMod.getInstance(), event.getGenerator()));
        event.getGenerator().addProvider(new MiscRecipes(FeywildMod.getInstance(), event.getGenerator()));
        event.getGenerator().addProvider(new AltarRecipes(FeywildMod.getInstance(), event.getGenerator()));
        event.getGenerator().addProvider(new AnvilRecipes(FeywildMod.getInstance(), event.getGenerator()));
        
        event.getGenerator().addProvider(new QuestProvider(FeywildMod.getInstance(), event.getGenerator()));
    }
}
