package com.feywild.feywild.tag;

import com.feywild.feywild.FeywildMod;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {

    public static final TagKey<Block> FEY_LOGS = BlockTags.create(FeywildMod.getInstance().resource("fey_logs"));
    public static final TagKey<Block> FLOWER_WALK_FLOWERS = BlockTags.create(FeywildMod.getInstance().resource("flower_walk_flowers"));
}
