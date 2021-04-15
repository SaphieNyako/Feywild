package com.feywild.feywild.block.trees;

import net.minecraft.block.Block;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class WinterTreeLeaves extends LeavesBlock {

    public WinterTreeLeaves() {
        super(Block.Properties.create(Material.LEAVES)
                .hardnessAndResistance(0.2F)
                .tickRandomly()
                .sound(SoundType.PLANT)
                .harvestTool(ToolType.HOE)
                .notSolid()
                .setAllowsSpawn((s, r, p, t) -> false)
                .setSuffocates((s, r, p) -> false)
                .setBlocksVision((s, r, p) -> false));
    }
}
