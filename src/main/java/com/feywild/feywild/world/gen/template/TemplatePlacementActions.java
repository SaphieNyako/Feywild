package com.feywild.feywild.world.gen.template;

import com.feywild.feywild.FeyRegistries;
import com.feywild.feywild.world.gen.tree.MushroomAction;
import org.moddingx.libx.annotation.registration.RegisterClass;

@RegisterClass(registryClass = FeyRegistries.class, registry = "TEMPLATE_ACTIONS")
public class TemplatePlacementActions {
    
    public static final TemplatePlacementAction mushroomAction = new MushroomAction();
}
