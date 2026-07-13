package com.saphienyako.feywild.effect;

import com.saphienyako.feywild.Feywild;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Feywild.MOD_ID);

    public static final RegistryObject<MobEffect> WINTER_TREE_ENT_PROTECTION = MOB_EFFECTS.register("winter_tree_ent_protection", WinterTreeEntProtectionEffect::new);
    public static final RegistryObject<MobEffect> AUTUMN_TREE_ENT_PROTECTION = MOB_EFFECTS.register("autumn_tree_ent_protection", AutumnTreeEntProtectionEffect::new);

    public static final RegistryObject<MobEffect> SUMMER_TREE_ENT_PROTECTION = MOB_EFFECTS.register("summer_tree_ent_protection", SummerTreeEntProtectionEffect::new);

    public static final RegistryObject<MobEffect> SPRING_TREE_ENT_PROTECTION = MOB_EFFECTS.register("spring_tree_ent_protection", SpringTreeEntProtectionEffect::new);

    public static final RegistryObject<MobEffect> FEY_FLYING = MOB_EFFECTS.register("fey_flying", FeyFlyingEffect::new);

    public static final RegistryObject<MobEffect> FEY_TRICKERY =
            MOB_EFFECTS.register("fey_trickery", FeyTrickeryEffect::new);


    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
