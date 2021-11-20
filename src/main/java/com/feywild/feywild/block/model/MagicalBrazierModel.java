package com.feywild.feywild.block.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.MagicalBrazierBlock;
import com.feywild.feywild.block.entity.MagicalBrazier;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MagicalBrazierModel extends AnimatedGeoModel<MagicalBrazier> {

    @Override
    public ResourceLocation getModelLocation(MagicalBrazier magicalBrazier) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "geo/magical_brazier.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(MagicalBrazier magicalBrazier) {
        BlockState state = magicalBrazier.getBlockState();
        if (MagicalBrazierBlock.isLit(state)) {
            // if (state.hasProperty(MagicalBrazierBlock.LIT) && state.getValue(MagicalBrazierBlock.LIT)) {
            //if (state.getBlock().getStateDefinition().any().getValue(MagicalBrazierBlock.LIT)) {
            return new ResourceLocation(FeywildMod.getInstance().modid, "textures/block/magical_brazier" + magicalBrazier.getTextureNumber() + ".png");
        } else return new ResourceLocation(FeywildMod.getInstance().modid, "textures/block/magical_brazier.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(MagicalBrazier magicalBrazier) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "animations/magical_brazier.animation.json");
    }
}

