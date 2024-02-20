package com.feywild.feywild.data.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.moddingx.libx.datagen.DatagenContext;
import org.moddingx.libx.datagen.provider.sandbox.FeatureProviderBase;

public class TreeProvider extends FeatureProviderBase {

    private final FeatureProvider features = this.context.findRegistryProvider(FeatureProvider.class);
    
    public final Holder<PlacedFeature> springTrees = denseTrees(this.features.springTree);
    public final Holder<PlacedFeature> summerTrees = denseTrees(this.features.summerTree);
    public final Holder<PlacedFeature> autumnTrees = denseTrees(this.features.autumnTree);
    public final Holder<PlacedFeature> winterTrees = denseTrees(this.features.winterTree);
    public final Holder<PlacedFeature> blossomTrees = denseTrees(this.features.blossomTree);
    public final Holder<PlacedFeature> hexenTrees = denseTrees(this.features.hexenTree);
    
    public final Holder<PlacedFeature> springTreePatches = treePatches(this.features.springTree);
    public final Holder<PlacedFeature> summerTreePatches = treePatches(this.features.summerTree);
    public final Holder<PlacedFeature> autumnTreePatches = treePatches(this.features.autumnTree);
    public final Holder<PlacedFeature> winterTreePatches = treePatches(this.features.winterTree);
    public final Holder<PlacedFeature> blossomTreePatches = treePatches(this.features.blossomTree);
    public final Holder<PlacedFeature> hexenTreePatches = treePatches(this.features.hexenTree);

    public TreeProvider(DatagenContext ctx) {
        super(ctx);
    }

    private Holder<PlacedFeature> denseTrees(Holder<ConfiguredFeature<?, ?>> tree) {
        return this.placement(tree)
                .countExtra(1, 0.2f, 2)
                .add(this.trees())
                .build();
    }

    private Holder<PlacedFeature> treePatches(Holder<ConfiguredFeature<?, ?>> tree) {
        return this.placement(tree)
                .rarity(80)
                .add(this.trees())
                .build();
    }

    private PlacementModifiers trees() {
        return this.modifiers()
                .spread()
                .waterDepth(0)
                .heightmap(Heightmap.Types.WORLD_SURFACE_WG)
                .validGround(BlockTags.DIRT)
                .biomeFilter()
                .build();
    }
}
