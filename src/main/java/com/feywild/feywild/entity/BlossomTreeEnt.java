package com.feywild.feywild.entity;

import com.feywild.feywild.block.trees.BaseTree;
import com.feywild.feywild.entity.base.TreeEnt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class BlossomTreeEnt extends TreeEnt {

    public BlossomTreeEnt(EntityType<? extends TreeEnt> entityType, Level level) {
        super(entityType, null, level);
    }

    @Override
    public BaseTree getTree() {
        return null;
    }

    @Override
    protected ItemStack getRewardItem() {
        return this.random.nextInt(3) != 0 ? ItemStack.EMPTY : switch (random.nextInt(66)) {
            case 0 -> new ItemStack(Items.ENCHANTED_GOLDEN_APPLE);
            case 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 -> new ItemStack(Items.GOLDEN_APPLE);
            default -> new ItemStack(Items.APPLE);
        };
    }
}
