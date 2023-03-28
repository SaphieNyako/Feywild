package com.feywild.feywild.item.render;

import com.feywild.feywild.item.FeyWing;
import com.feywild.feywild.item.model.FeyWingsModel;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class FeyWingsRenderer extends GeoArmorRenderer<FeyWing> {


    public FeyWingsRenderer() {
        super(new FeyWingsModel());

        this.headBone = "armorHead";
        this.bodyBone = "armorBody";
        this.rightArmBone = "armorRightArm";
        this.leftArmBone = "armorLeftArm";
        this.rightLegBone = "armorLeftLeg";
        this.leftLegBone = "armorRightLeg";
        this.rightBootBone = "armorLeftBoot";
        this.leftBootBone = "armorRightBoot";
    }
}
