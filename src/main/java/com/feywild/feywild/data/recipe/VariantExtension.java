package com.feywild.feywild.data.recipe;

import com.feywild.feywild.block.decoration.ElvenQuartzBlock;
import net.minecraft.world.item.Items;
import org.moddingx.libx.base.decoration.DecoratedBlock;
import org.moddingx.libx.base.decoration.DecorationType;
import org.moddingx.libx.datagen.provider.recipe.RecipeExtension;
import org.moddingx.libx.datagen.provider.recipe.StoneCuttingExtension;
import org.moddingx.libx.datagen.provider.recipe.crafting.CraftingExtension;

public interface VariantExtension extends RecipeExtension, CraftingExtension, StoneCuttingExtension {
    
    default void quartzRecipes(ElvenQuartzBlock block) {
        this.decoRecipes(block, block.getBrickBlock(), block.getCrackedBrickBlock(), block.getPolishedBlock());
        this.decoRecipes(block.getBrickBlock(), block.getCrackedBrickBlock());
        
        this.shaped(block.getPolishedBlock(), 4, "aa", "aa", 'a', block);
        this.shaped(block.getBrickBlock(), 4, "aa", "aa", 'a', block.getPolishedBlock());
        this.shapeless(block.getMossyBrickBlock(), block.getBrickBlock(), Items.VINE);
        this.shaped(block.getPillarBlock(), 2, "a", "a", 'a', block);
        this.stoneCutting(block, block.getPillarBlock());
    }

    private void decoRecipes(DecoratedBlock block, DecoratedBlock... otherBlocks) {
        this.decoRecipes(block, block);
        for (DecoratedBlock other : otherBlocks) {
            this.stoneCutting(block, other);
            this.decoRecipes(block, other);
        }
    }
    
    private void decoRecipes(DecoratedBlock from, DecoratedBlock block) {
        if (block.has(DecorationType.SLAB)) {
            this.stoneCutting(from, block.get(DecorationType.SLAB), 2);
            if (from.has(DecorationType.SLAB)) {
                this.stoneCutting(from.get(DecorationType.SLAB), block.get(DecorationType.SLAB));
            }
        }
        if (block.has(DecorationType.STAIRS)) {
            this.stoneCutting(from, block.get(DecorationType.STAIRS));
            if (from.has(DecorationType.STAIRS)) {
                this.stoneCutting(from.get(DecorationType.STAIRS), block.get(DecorationType.STAIRS));
            }
        }
        if (block.has(DecorationType.WALL)) {
            this.stoneCutting(from, block.get(DecorationType.WALL));
            if (from.has(DecorationType.WALL)) {
                this.stoneCutting(from.get(DecorationType.WALL), block.get(DecorationType.WALL));
            }
        }
    }
}
