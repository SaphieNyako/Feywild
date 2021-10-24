package com.feywild.feywild.world.gen;

import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

public class FeywildOreGen {

    public static final RuleTest ALFHEIM_STONE = new TagMatchTest(BlockTags.bind(new ResourceLocation("mythicbotany", "base_stone_alfheim").toString()));
}
