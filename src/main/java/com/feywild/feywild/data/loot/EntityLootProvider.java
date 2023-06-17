package com.feywild.feywild.data.loot;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.ModTrees;
import com.feywild.feywild.entity.ModEntities;
import com.feywild.feywild.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.moddingx.libx.annotation.data.Datagen;
import org.moddingx.libx.datagen.provider.loot.EntityLootProviderBase;
import org.moddingx.libx.mod.ModX;

@Datagen
public class EntityLootProvider extends EntityLootProviderBase {

    public EntityLootProvider(ModX mod, DataGenerator generator) {
        super(mod, generator);
    }

    @Override
    protected void setup() {
        this.drops(ModEntities.springPixie, new ItemStack(ModItems.feyDust));
        this.drops(ModEntities.summerPixie, new ItemStack(ModItems.feyDust));
        this.drops(ModEntities.autumnPixie, new ItemStack(ModItems.feyDust));
        this.drops(ModEntities.winterPixie, new ItemStack(ModItems.feyDust));
        this.drops(ModEntities.beeKnight, this.combine(
                this.stack(ModItems.feyDust),
                this.stack(ModItems.honeycomb).with(this.random(0.01f))
        ));
        this.drops(ModEntities.mandragora, this.combine(
                this.stack(ModItems.feyDust),
                this.stack(Items.MELON_SEEDS).with(this.random(0.25f)),
                this.stack(Items.PUMPKIN_SEEDS).with(this.random(0.25f)),
                this.stack(Items.WHEAT_SEEDS).with(this.random(0.25f)),
                this.stack(Items.BEETROOT_SEEDS).with(this.random(0.25f))
        ));

        this.drops(ModEntities.mab, new ItemStack(ModItems.pixieWingTiara));
        this.drops(ModEntities.titania, new ItemStack(ModItems.pixieWingTiara));

        this.drops(ModEntities.dwarfBlacksmith, new ItemStack(ItemStack.EMPTY.getItem()));
        this.drops(ModEntities.dwarfArtificer, new ItemStack(ItemStack.EMPTY.getItem()));
        this.drops(ModEntities.dwarfBaker, new ItemStack(ItemStack.EMPTY.getItem()));
        this.drops(ModEntities.dwarfMiner, new ItemStack(ItemStack.EMPTY.getItem()));
        this.drops(ModEntities.dwarfDragonHunter, new ItemStack(ItemStack.EMPTY.getItem()));
        this.drops(ModEntities.dwarfShepherd, new ItemStack(ItemStack.EMPTY.getItem()));
        this.drops(ModEntities.dwarfToolsmith, new ItemStack(ItemStack.EMPTY.getItem()));

        this.drops(ModEntities.autumnTreeEnt,
                this.combine(this.stack(ModItems.feyDust),
                        this.stack(ModTrees.autumnTree.getWoodBlock()).with(this.random(0.25f)),
                        this.stack(ModTrees.autumnTree.getSapling()).with(this.random(0.25f)),
                        this.stack(ModTrees.autumnTree.getLogBlock()).with(this.random(0.25f)),
                        this.stack(ModBlocks.autumnBrownLeaves).with(this.random(0.25f)),
                        this.stack(ModBlocks.autumnDarkGrayLeaves).with(this.random(0.25f)),
                        this.stack(ModBlocks.autumnLightGrayLeaves).with(this.random(0.25f)),
                        this.stack(ModBlocks.autumnRedLeaves).with(this.random(0.25f))
                ));

        this.drops(ModEntities.summerTreeEnt,
                this.combine(this.stack(ModItems.feyDust),
                        this.stack(ModTrees.springTree.getWoodBlock()).with(this.random(0.25f)),
                        this.stack(ModTrees.springTree.getSapling()).with(this.random(0.25f)),
                        this.stack(ModTrees.springTree.getLogBlock()).with(this.random(0.25f)),
                        this.stack(ModBlocks.summerOrangeLeaves).with(this.random(0.25f)),
                        this.stack(ModBlocks.summerOrangeLeaves).with(this.random(0.25f))
                ));

        this.drops(ModEntities.springTreeEnt,
                this.combine(this.stack(ModItems.feyDust),
                        this.stack(ModTrees.springTree.getWoodBlock()).with(this.random(0.25f)),
                        this.stack(ModTrees.springTree.getSapling()).with(this.random(0.25f)),
                        this.stack(ModTrees.springTree.getLogBlock()).with(this.random(0.25f)),
                        this.stack(ModBlocks.springCyanLeaves).with(this.random(0.25f)),
                        this.stack(ModBlocks.springGreenLeaves).with(this.random(0.25f)),
                        this.stack(ModBlocks.springLimeLeaves).with(this.random(0.25f))
                ));

        this.drops(ModEntities.winterTreeEnt,
                this.combine(this.stack(ModItems.feyDust),
                        this.stack(ModTrees.winterTree.getWoodBlock()).with(this.random(0.25f)),
                        this.stack(ModTrees.winterTree.getSapling()).with(this.random(0.25f)),
                        this.stack(ModTrees.winterTree.getLogBlock()).with(this.random(0.25f)),
                        this.stack(ModBlocks.winterBlueLeaves).with(this.random(0.25f)),
                        this.stack(ModBlocks.winterLightBlueLeaves).with(this.random(0.25f))
                ));

        this.drops(ModEntities.blossomTreeEnt,
                this.combine(this.stack(ModItems.feyDust),
                        this.stack(ModTrees.blossomTree.getWoodBlock()).with(this.random(0.25f)),
                        this.stack(ModTrees.blossomTree.getSapling()).with(this.random(0.25f)),
                        this.stack(ModTrees.blossomTree.getLogBlock()).with(this.random(0.25f)),
                        this.stack(ModBlocks.blossomMagentaLeaves).with(this.random(0.25f)),
                        this.stack(ModBlocks.blossomPinkLeaves).with(this.random(0.25f)),
                        this.stack(ModBlocks.blossomWhiteLeaves).with(this.random(0.25f))
                ));

        this.drops(ModEntities.hexenTreeEnt,
                this.combine(this.stack(ModItems.feyDust),
                        this.stack(ModTrees.hexenTree.getWoodBlock()).with(this.random(0.25f)),
                        this.stack(ModTrees.hexenTree.getSapling()).with(this.random(0.25f)),
                        this.stack(ModTrees.hexenTree.getLogBlock()).with(this.random(0.25f)),
                        this.stack(ModBlocks.hexBlackLeaves).with(this.random(0.25f)),
                        this.stack(ModBlocks.hexPurpleLeaves).with(this.random(0.25f))
                ));
    }
}
