package com.feywild.feywild.world.gen;

import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.feature.template.TagMatchRuleTest;

public class FeywildOreGen {

    public static final RuleTest ALFHEIM_STONE = new TagMatchRuleTest(BlockTags.bind(new ResourceLocation("mythicbotany", "base_stone_alfheim").toString()));
}
