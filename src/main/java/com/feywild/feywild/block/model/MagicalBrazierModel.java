package com.feywild.feywild.block.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.MagicalBrazierBlock;
import com.feywild.feywild.block.entity.MagicalBrazier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import java.util.List;
import java.util.stream.IntStream;

public class MagicalBrazierModel extends AnimatedGeoModel<MagicalBrazier> {

    public static final List<ResourceLocation> TEXTURE_IDS = IntStream.range(1, 9).mapToObj(i -> FeywildMod.getInstance().resource("textures/block/magical_brazier" + i + ".png")).toList();

    @Override
    public ResourceLocation getModelLocation(MagicalBrazier magicalBrazier) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "geo/magical_brazier.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(MagicalBrazier magicalBrazier) {
        BlockState state = magicalBrazier.getBlockState();
        if (MagicalBrazierBlock.isLit(state)) {
            return TEXTURE_IDS.get(magicalBrazier.getTextureNumber() - 1);
        } else {
            return new ResourceLocation(FeywildMod.getInstance().modid, "textures/block/magical_brazier.png");
        }
    }

    @Override
    public ResourceLocation getAnimationFileLocation(MagicalBrazier animatable) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "animations/magical_brazier.animation.json");
    }
}
