package com.feywild.feywild.data;

import com.feywild.feywild.entity.ModEntityTypes;
import com.feywild.feywild.item.ModItems;
import io.github.noeppi_noeppi.libx.annotation.data.Datagen;
import io.github.noeppi_noeppi.libx.data.provider.EntityLootProviderBase;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

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
        this.drops(ModEntityTypes.beeKnight, this.combine(
                this.stack(ModItems.feyDust),
                this.stack(ModItems.honeycomb).with(this.random(0.01f))
        ));
        this.drops(ModEntityTypes.mandragora, this.combine(
                this.stack(ModItems.feyDust),
                this.stack(Items.MELON_SEEDS).with(this.random(0.25f)),
                this.stack(Items.PUMPKIN_SEEDS).with(this.random(0.25f)),
                this.stack(Items.WHEAT_SEEDS).with(this.random(0.25f)),
                this.stack(Items.BEETROOT_SEEDS).with(this.random(0.25f))
        ));
        this.drops(ModEntityTypes.dwarfBlacksmith, new ItemStack(ItemStack.EMPTY.getItem()));
        this.drops(ModEntityTypes.dwarfArtificer, new ItemStack(ItemStack.EMPTY.getItem()));
        this.drops(ModEntityTypes.dwarfBaker, new ItemStack(ItemStack.EMPTY.getItem()));
        this.drops(ModEntityTypes.dwarfMiner, new ItemStack(ItemStack.EMPTY.getItem()));
        this.drops(ModEntityTypes.dwarfDragonHunter, new ItemStack(ItemStack.EMPTY.getItem()));
        this.drops(ModEntityTypes.dwarfShepherd, new ItemStack(ItemStack.EMPTY.getItem()));
        this.drops(ModEntityTypes.dwarfToolsmith, new ItemStack(ItemStack.EMPTY.getItem()));

    }
}
