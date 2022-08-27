package com.feywild.feywild.data.loot;

import com.feywild.feywild.loot.AdditionLootModifier;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;
import org.moddingx.libx.annotation.data.Datagen;
import org.moddingx.libx.mod.ModX;

@Datagen
public class LootModifierProvider extends GlobalLootModifierProvider {

    private final ModX mod;
    
    public LootModifierProvider(ModX mod, DataGenerator generator) {
        super(generator, mod.modid);
        this.mod = mod;
    }

    @Override
    protected void start() {
        this.add("mineshaft_chest_additions", new AdditionLootModifier(
                this.mod.resource("chests/mineshaft_chest_additions"),
                new LootTableIdCondition.Builder(new ResourceLocation("minecraft", "chests/abandoned_mineshaft")).build()
        ));
    }
}
