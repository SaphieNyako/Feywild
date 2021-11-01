package com.feywild.feywild.data;

import com.feywild.feywild.entity.ModEntityTypes;
import com.feywild.feywild.item.ModItems;
import io.github.noeppi_noeppi.libx.annotation.data.Datagen;
import io.github.noeppi_noeppi.libx.data.provider.EntityLootProviderBase;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.ItemStack;

@Datagen
public class EntityLootProvider extends EntityLootProviderBase {

    public EntityLootProvider(ModX mod, DataGenerator generator) {
        super(mod, generator);
    }

    @Override
    protected void setup() {
        this.drops(ModEntityTypes.springPixie, new ItemStack(ModItems.feyDust));
        this.drops(ModEntityTypes.summerPixie, new ItemStack(ModItems.feyDust));
        this.drops(ModEntityTypes.autumnPixie, new ItemStack(ModItems.feyDust));
        this.drops(ModEntityTypes.winterPixie, new ItemStack(ModItems.feyDust));
    }
}
