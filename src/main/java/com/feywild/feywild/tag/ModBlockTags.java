package com.feywild.feywild.tag;

import com.feywild.feywild.FeywildMod;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {

    public static final Tag.Named<Block> FEY_LOGS = BlockTags.bind(FeywildMod.getInstance().resource("fey_logs").toString());
    public static final Tag.Named<Block> AUTUMN_LOGS = BlockTags.bind(FeywildMod.getInstance().resource("autumn_logs").toString());
    public static final Tag.Named<Block> SPRING_LOGS = BlockTags.bind(FeywildMod.getInstance().resource("spring_logs").toString());
    public static final Tag.Named<Block> SUMMER_LOGS = BlockTags.bind(FeywildMod.getInstance().resource("summer_logs").toString());
    public static final Tag.Named<Block> WINTER_LOGS = BlockTags.bind(FeywildMod.getInstance().resource("winter_logs").toString());
}
