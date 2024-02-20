package com.feywild.feywild.data.loot;

import com.feywild.feywild.block.ModTrees;
import com.feywild.feywild.block.trees.BaseTree;
import com.feywild.feywild.entity.ModEntities;
import com.feywild.feywild.item.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import org.moddingx.libx.datagen.DatagenContext;
import org.moddingx.libx.datagen.provider.loot.EntityLootProviderBase;
import org.moddingx.libx.datagen.provider.loot.entry.LootFactory;

import java.util.ArrayList;
import java.util.List;

public class EntityLootProvider extends EntityLootProviderBase {

    public EntityLootProvider(DatagenContext ctx) {
        super(ctx);
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

        this.drops(ModEntities.springTreeEnt, this.stack(ModItems.feyDust), this.treeDrops(ModTrees.springTree));
        this.drops(ModEntities.summerTreeEnt, this.stack(ModItems.feyDust), this.treeDrops(ModTrees.summerTree));
        this.drops(ModEntities.autumnTreeEnt, this.stack(ModItems.feyDust), this.treeDrops(ModTrees.autumnTree));
        this.drops(ModEntities.winterTreeEnt, this.stack(ModItems.feyDust), this.treeDrops(ModTrees.winterTree));
        this.drops(ModEntities.blossomTreeEnt, this.stack(ModItems.feyDust), this.treeDrops(ModTrees.blossomTree));
        this.drops(ModEntities.hexenTreeEnt, this.stack(ModItems.feyDust), this.treeDrops(ModTrees.hexenTree));
    }
    
    private LootFactory<EntityType<?>> treeDrops(BaseTree tree) {
        return this.combine(
                this.stack(tree.getWoodBlock()).with(this.random(0.25f)),
                this.stack(tree.getSapling()).with(this.random(0.25f)),
                this.stack(tree.getLogBlock()).with(this.random(0.25f)),
                this.treeLeaves(tree)
        );
    }
    
    private LootFactory<EntityType<?>> treeLeaves(BaseTree tree) {
        List<LootFactory<EntityType<?>>> entries = new ArrayList<>();
        for (Block leaves : tree.getAllLeaves()) {
            entries.add(this.stack(leaves));
        }
        return this.random(entries).with(this.random(0.5f));
    }
}
