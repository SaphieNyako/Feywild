package com.saphienyako.feywild.effect;

import com.saphienyako.feywild.Feywild;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


public class ModEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, Feywild.MOD_ID);


    public static final DeferredHolder<MobEffect, MobEffect> AUTUMN_TREE_ENT_PROTECTION =
            MOB_EFFECTS.register("autumn_tree_ent_protection", AutumnTreeEntProtectionEffect::new);

    public static final DeferredHolder<MobEffect, MobEffect> SPRING_TREE_ENT_PROTECTION =
            MOB_EFFECTS.register("spring_tree_ent_protection", SpringTreeEntProtectionEffect::new);

    public static final DeferredHolder<MobEffect, MobEffect> SUMMER_TREE_ENT_PROTECTION =
            MOB_EFFECTS.register("summer_tree_ent_protection", SummerTreeEntProtectionEffect::new);

    public static final DeferredHolder<MobEffect, MobEffect> WINTER_TREE_ENT_PROTECTION =
            MOB_EFFECTS.register("winter_tree_ent_protection", WinterTreeEntProtectionEffect::new);

    public static final DeferredHolder<MobEffect, MobEffect> FEY_FLYING =
            MOB_EFFECTS.register("fey_flying", FeyFlyingEffect::new);

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
