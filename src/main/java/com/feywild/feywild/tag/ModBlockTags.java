package com.feywild.feywild.tag;

import com.feywild.feywild.FeywildMod;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {

    public static final TagKey<Block> FEY_LOGS = BlockTags.create(FeywildMod.getInstance().resource("fey_logs"));
    public static final TagKey<Block> AUTUMN_LOGS = BlockTags.create(FeywildMod.getInstance().resource("autumn_logs"));
    public static final TagKey<Block> SPRING_LOGS = BlockTags.create(FeywildMod.getInstance().resource("spring_logs"));
    public static final TagKey<Block> SUMMER_LOGS = BlockTags.create(FeywildMod.getInstance().resource("summer_logs"));
    public static final TagKey<Block> WINTER_LOGS = BlockTags.create(FeywildMod.getInstance().resource("winter_logs"));
}
