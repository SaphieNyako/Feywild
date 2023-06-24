package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.BeeKnight;
import net.minecraft.resources.ResourceLocation;

public class BeeKnightModel extends TypedEntityModel<BeeKnight> {

    public BeeKnightModel() {
        super("bee_knight", null);
    }

    @Override
    public ResourceLocation getTextureResource(BeeKnight beeKnight) {
        return beeKnight.getEntityData().get(BeeKnight.AGGRAVATED) ? FeywildMod.getInstance().resource("textures/entity/angry_bee_knight.png") : FeywildMod.getInstance().resource("textures/entity/bee_knight.png");
    }
}
