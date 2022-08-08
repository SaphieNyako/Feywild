package com.feywild.feywild.world.dimension.feywild.setup;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class FeywildSurfaceBuilder {

    public static SurfaceRules.RuleSource buildSurface(SurfaceRules.RuleSource biomeData) {
        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)),
                        SurfaceRules.state(Blocks.BEDROCK.defaultBlockState())
                ),
                biomeData
        );
    }
}
