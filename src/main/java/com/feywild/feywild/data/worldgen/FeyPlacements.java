package com.feywild.feywild.data.worldgen;

import io.github.noeppi_noeppi.mods.sandbox.datagen.ext.FeatureData;
import net.minecraft.core.Holder;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class FeyPlacements extends FeatureData {

    private final FeyFeatures features = this.resolve(FeyFeatures.class);
    
    public final Holder<PlacedFeature> autumnPumpkins = this.placement(this.features.autumnPumpkins)
            .countExtra(1, 0.5f, 3)
            .add(this.plant())
            .build();
    
    public final Holder<PlacedFeature> sunflowers = this.placement(this.features.sunflowers)
            .rarity(2)
            .countExtra(2, 0.2f, 1)
            .add(this.plant())
            .build();
    
    public final Holder<PlacedFeature> dandelions = this.placement(this.features.dandelions)
            .rarity(2)
            .countExtra(2, 0.2f, 1)
            .add(this.plant())
            .build();
    
    public final Holder<PlacedFeature> crocus = this.placement(this.features.crocus)
            .rarity(2)
            .countExtra(2, 0.2f, 1)
            .add(this.plant())
            .build();
    
    public final Holder<PlacedFeature> springFlowers = this.placement(this.features.springFlowers)
            .rarity(32)
            .add(this.patchPlant())
            .build();

    public final Holder<PlacedFeature> summerFlowers = this.placement(this.features.summerFlowers)
            .rarity(32)
            .add(this.patchPlant())
            .build();

    public final Holder<PlacedFeature> autumnFlowers = this.placement(this.features.autumnFlowers)
            .rarity(32)
            .add(this.patchPlant())
            .build();

    public final Holder<PlacedFeature> winterFlowers = this.placement(this.features.winterFlowers)
            .rarity(32)
            .add(this.patchPlant())
            .build();
    
    public final Holder<PlacedFeature> feyGemOre = this.placement(this.features.feyGemOre)
            .count(20)
            .spread()
            .heightTriangle(VerticalAnchor.aboveBottom(4), VerticalAnchor.absolute(60))
            .biomeFilter()
            .build();
    
    public FeyPlacements(Properties properties) {
        super(properties);
    }

    private PlacementModifiers plant() {
        return this.modifiers()
                .spread()
                .waterDepth(0)
                .heightmap(Heightmap.Types.WORLD_SURFACE_WG)
                .validGround(BlockTags.DIRT)
                .biomeFilter()
                .build();
    }

    private PlacementModifiers patchPlant() {
        return this.modifiers()
                .spread()
                .waterDepth(0)
                .heightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES)
                .biomeFilter()
                .build();
    }
}
