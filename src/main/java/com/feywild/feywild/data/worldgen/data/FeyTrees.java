package com.feywild.feywild.data.worldgen.data;

import com.feywild.feywild.block.ModTrees;
import com.feywild.feywild.block.trees.BaseTree;
import io.github.noeppi_noeppi.mods.sandbox.datagen.ext.FeatureData;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class FeyTrees extends FeatureData {

    public final Holder<PlacedFeature> springTrees = denseTrees(ModTrees.springTree);
    public final Holder<PlacedFeature> summerTrees = denseTrees(ModTrees.summerTree);
    public final Holder<PlacedFeature> autumnTrees = denseTrees(ModTrees.autumnTree);
    public final Holder<PlacedFeature> winterTrees = denseTrees(ModTrees.winterTree);

    public final Holder<PlacedFeature> springTreePatches = treePatches(ModTrees.springTree);
    public final Holder<PlacedFeature> summerTreePatches = treePatches(ModTrees.summerTree);
    public final Holder<PlacedFeature> autumnTreePatches = treePatches(ModTrees.autumnTree);
    public final Holder<PlacedFeature> winterTreePatches = treePatches(ModTrees.winterTree);

    public FeyTrees(Properties properties) {
        super(properties);
    }

    private Holder<PlacedFeature> denseTrees(BaseTree tree) {
        return this.placement(tree.getConfiguredFeature())
                .countExtra(1, 0.2f, 2)
                .add(trees(tree))
                .build();
    }

    private Holder<PlacedFeature> treePatches(BaseTree tree) {
        return this.placement(tree.getConfiguredFeature())
                .rarity(80)
                .add(trees(tree))
                .build();
    }

    private PlacementModifiers trees(BaseTree tree) {
        return this.modifiers()
                .spread()
                .waterDepth(0)
                .heightmap(Heightmap.Types.OCEAN_FLOOR_WG)
                .validGround(tree.getSapling())
                .biomeFilter()
                .build();
    }
}
