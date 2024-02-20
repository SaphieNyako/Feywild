package com.feywild.feywild.block.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.entity.MagicalBrazier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import software.bernie.geckolib.model.GeoModel;

import java.util.List;
import java.util.stream.IntStream;

public class MagicalBrazierModel extends GeoModel<MagicalBrazier> {

    public static final List<ResourceLocation> TEXTURE_IDS = IntStream.range(1, 9).mapToObj(i -> FeywildMod.getInstance().resource("textures/block/magical_brazier" + i + ".png")).toList();

    @Override
    public ResourceLocation getModelResource(MagicalBrazier brazier) {
        return FeywildMod.getInstance().resource("geo/magical_brazier.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MagicalBrazier brazier) {
        BlockState state = brazier.getBlockState();
        if (state.getValue(BlockStateProperties.LIT)) {
            return TEXTURE_IDS.get(brazier.getTextureNumber() - 1);
        } else {
            return FeywildMod.getInstance().resource("textures/block/magical_brazier.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(MagicalBrazier brazier) {
        return FeywildMod.getInstance().resource("animations/magical_brazier.animation.json");
    }
}
