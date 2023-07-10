package com.feywild.feywild.entity;

import com.feywild.feywild.block.ModTrees;
import com.feywild.feywild.block.trees.BaseTree;
import com.feywild.feywild.entity.base.TreeEnt;
import com.feywild.feywild.quest.Alignment;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class AutumnTreeEnt extends TreeEnt {

    public AutumnTreeEnt(EntityType<? extends TreeEnt> entityType, Level level) {
        super(entityType, Alignment.AUTUMN, level);
    }

    @Override
    public BaseTree getTree() {
        return ModTrees.autumnTree;
    }

    @Override
    protected ItemStack getRewardItem() {
        return this.random.nextInt(3) == 0 ? new ItemStack(Blocks.MYCELIUM) : ItemStack.EMPTY;
    }
}
