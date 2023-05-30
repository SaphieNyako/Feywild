package com.feywild.feywild.data.loot;

import com.feywild.feywild.item.ModItems;
import net.minecraft.data.DataGenerator;
import org.moddingx.libx.annotation.data.Datagen;
import org.moddingx.libx.datagen.provider.loot.ChestLootProviderBase;
import org.moddingx.libx.mod.ModX;

@Datagen
@SuppressWarnings("unchecked")
public class ChestLootProvider extends ChestLootProviderBase {

    public ChestLootProvider(ModX mod, DataGenerator generator) {
        super(mod, generator);
    }

    @Override
    protected void setup() {
        this.drops("mineshaft_chest_additions",
                this.stack(ModItems.inactiveMarketRuneStone)
                        .with(this.random(0.2f)),
                this.stack(ModItems.lesserFeyGem)
                        .with(this.random(0.6f))
                        .with(this.count(2, 4)),
                this.stack(ModItems.greaterFeyGem)
                        .with(this.random(0.4f))
                        .with(this.count(1, 3)),
                this.stack(ModItems.schematicsGemTransmutation)
                        .with(this.random(0.15f)),
                this.stack(ModItems.schematicsElvenQuartz)
                        .with(this.random(0.15f)),
                this.stack(ModItems.shinyFeyGem)
                        .with(this.random(0.15f))
                        .with(this.count(1, 2)),
                this.stack(ModItems.brilliantFeyGem)
                        .with(this.random(0.08f)),
                this.stack(ModItems.feywildMusicDisc)
                        .with(this.random(0.04f))
        );
    }
}
