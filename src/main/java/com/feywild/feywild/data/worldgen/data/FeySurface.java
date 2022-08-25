package com.feywild.feywild.data.worldgen.data;

import io.github.noeppi_noeppi.mods.sandbox.datagen.ext.SurfaceData;
import io.github.noeppi_noeppi.mods.sandbox.surface.SurfaceRuleSet;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class FeySurface extends SurfaceData {

    private final FeyBiomes biomes = this.resolve(FeyBiomes.class);
    
    public final Holder<SurfaceRuleSet> mainSurface = this.ruleSet()
            .beforeBiomes(
                    SurfaceRules.ifTrue(
                            SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), 
                            SurfaceRules.state(Blocks.BEDROCK.defaultBlockState())
                    )
            )
            .defaultBiomeSurface(
                    SurfaceRules.ifTrue(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(60), 0),
                            SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), SurfaceRules.sequence(
                                    SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.waterBlockCheck(-1, 0)), SurfaceRules.state(Blocks.DIRT.defaultBlockState())),
                                    SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.state(Blocks.GRASS_BLOCK.defaultBlockState())),
                                    SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.steep()), SurfaceRules.sequence(
                                            SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SurfaceRules.state(Blocks.DIRT.defaultBlockState()))
                                    ))
                            ))
                    ),
                    SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(60), 0)),
                            SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), SurfaceRules.sequence(
                                    SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.waterBlockCheck(-1, 0)), SurfaceRules.state(Blocks.DIRT.defaultBlockState())),
                                    SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.steep()), SurfaceRules.sequence(
                                            SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.state(Blocks.DIRT.defaultBlockState()))
                                    ))
                            ))
                    )
            )
            .build();
    
    public FeySurface(Properties properties) {
        super(properties);
    }
}
