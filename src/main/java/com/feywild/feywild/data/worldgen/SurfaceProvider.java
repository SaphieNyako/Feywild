package com.feywild.feywild.data.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import org.moddingx.libx.datagen.DatagenContext;
import org.moddingx.libx.datagen.provider.sandbox.SurfaceProviderBase;
import org.moddingx.libx.sandbox.surface.BiomeSurface;
import org.moddingx.libx.sandbox.surface.SurfaceRuleSet;

public class SurfaceProvider extends SurfaceProviderBase {

    private final BiomeProvider biomes = this.context.findRegistryProvider(BiomeProvider.class);
    
    public final Holder<SurfaceRuleSet> mainSurface = this.ruleSet()
            .beforeBiomes(
                    SurfaceRules.ifTrue(
                            SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), 
                            SurfaceRules.state(Blocks.BEDROCK.defaultBlockState())
                    )
            )
            .afterBiomes(
                    SurfaceRules.ifTrue(
                            SurfaceRules.verticalGradient("deepslate", VerticalAnchor.absolute(0), VerticalAnchor.absolute(8)),
                            SurfaceRules.state(Blocks.DEEPSLATE.defaultBlockState())
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
    
    public final Holder<BiomeSurface> feywildOcean = this.biome(this.biomes.feywildOcean,
            SurfaceRules.ifTrue(
                    SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(64), 0)),
                    SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(),
                            SurfaceRules.sequence(
                                    SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.state(Blocks.GRAVEL.defaultBlockState())),
                                    SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.steep()), SurfaceRules.sequence(
                                            SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SurfaceRules.state(Blocks.GRAVEL.defaultBlockState()))
                                    ))
                            )
                    )
            ),
            SurfaceRules.ifTrue(
                    SurfaceRules.yStartCheck(VerticalAnchor.absolute(64), 0),
                    SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(),
                            SurfaceRules.sequence(
                                    SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.state(Blocks.SAND.defaultBlockState())),
                                    SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.steep()), SurfaceRules.sequence(
                                            SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SurfaceRules.state(Blocks.SANDSTONE.defaultBlockState()))
                                    ))
                            )
                    )
            )
    );
    
    public SurfaceProvider(DatagenContext ctx) {
        super(ctx);
    }
}
