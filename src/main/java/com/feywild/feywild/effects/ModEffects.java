package com.feywild.feywild.effects;

import org.moddingx.libx.annotation.registration.RegisterClass;

@RegisterClass(registry = "MOB_EFFECT")
public class ModEffects {

    public static final WindWalkEffect windWalk = new WindWalkEffect();
    public static final FrostWalkEffect frostWalk = new FrostWalkEffect();
    public static final FireWalkEffect fireWalk = new FireWalkEffect();
    public static final FlowerWalkEffect flowerWalk = new FlowerWalkEffect();
    public static final FeyFlyingEffect feyFlying = new FeyFlyingEffect();
}
