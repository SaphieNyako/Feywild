package com.feywild.feywild.data.loot;

import net.minecraft.resources.ResourceLocation;
import org.moddingx.libx.datagen.DatagenContext;
import org.moddingx.libx.datagen.provider.loot.GlobalLootProviderBase;

public class LootModifierProvider extends GlobalLootProviderBase {
    
    public LootModifierProvider(DatagenContext ctx) {
        super(ctx);
    }

    @Override
    protected void setup() {
        this.addition("mineshaft_chest_additions", this.mod.resource("chests/mineshaft_chest_additions"))
                .forLootTable(new ResourceLocation("minecraft", "chests/abandoned_mineshaft"))
                .build();
    }
}
