package com.feywild.feywild.data.worldgen.data;

import io.github.noeppi_noeppi.mods.sandbox.datagen.ext.FeatureData;
import net.minecraft.core.Holder;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class FeyPlacements extends FeatureData {

    private final FeyFeatures features = this.resolve(FeyFeatures.class);
    /*
    public final Holder<PlacedFeature> springTree = this.placement(this.features.springTree)
            .rarity(2)
            .countExtra(2, 0.1f, 1)
            .add(this.plant())
            .build();

    public final Holder<PlacedFeature> summerTree = this.placement(this.features.summerTree)
            .rarity(2)
            .countExtra(2, 0.1f, 1)
            .add(this.plant())
            .build();

    public final Holder<PlacedFeature> autumnTree = this.placement(this.features.autumnTree)
            .rarity(2)
            .countExtra(2, 0.1f, 1)
            .add(this.plant())
            .build();

    public final Holder<PlacedFeature> winterTree = this.placement(this.features.winterTree)
            .rarity(2)
            .countExtra(2, 0.1f, 1)
            .add(this.plant())
            .build();

    //TODO Change extra

    public final Holder<PlacedFeature> blossomTree = this.placement(this.features.blossomTree)
            .rarity(2)
            .countExtra(2, 0.1f, 1)
            .add(this.plant())
            .build();

    public final Holder<PlacedFeature> hexenTree = this.placement(this.features.hexenTree)
            .rarity(2)
            .countExtra(2, 0.1f, 1)
            .add(this.plant())
            .build();
    */

    public final Holder<PlacedFeature> autumnPumpkins = this.placement(this.features.autumnPumpkins)
            .countExtra(1, 0.5f, 3)
            .add(this.plant())
            .build();

    public final Holder<PlacedFeature> treeMushrooms = this.placement(this.features.treeMushrooms)
            .rarity(32)
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
            .rarity(1)
            .countExtra(2, 0.2f, 1)
            .add(this.patchPlant())
            .build();

    public final Holder<PlacedFeature> summerFlowers = this.placement(this.features.summerFlowers)
            .rarity(16)
            .countExtra(2, 0.2f, 1)
            .add(this.patchPlant())
            .build();

    public final Holder<PlacedFeature> autumnFlowers = this.placement(this.features.autumnFlowers)
            .rarity(16)
            .countExtra(2, 0.2f, 1)
            .add(this.patchPlant())
            .build();

    public final Holder<PlacedFeature> winterFlowers = this.placement(this.features.winterFlowers)
            .rarity(32)
            .countExtra(2, 0.2f, 1)
            .add(this.patchPlant())
            .build();

    public final Holder<PlacedFeature> feyGemOre = this.placement(this.features.feyGemOre)
            .count(16)
            .spread()
            .heightTriangle(VerticalAnchor.aboveBottom(30), VerticalAnchor.absolute(60))
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
