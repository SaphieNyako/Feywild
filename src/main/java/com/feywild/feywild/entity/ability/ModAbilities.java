package com.feywild.feywild.entity.ability;

import com.feywild.feywild.FeyRegistries;
import com.feywild.feywild.effects.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import org.moddingx.libx.annotation.registration.RegisterClass;

@RegisterClass(registryClass = FeyRegistries.class, registry = "ABILITIES")
public class ModAbilities {
    
    public static final Ability<?> flowerWalk = new EffectAbility(() -> new MobEffectInstance(ModEffects.flowerWalk, 400, 2), () -> new MobEffectInstance(MobEffects.REGENERATION, 100, 0));
    public static final Ability<?> frostWalk = new EffectAbility(() -> new MobEffectInstance(ModEffects.frostWalk, 400, 2));
    public static final Ability<?> fireWalk = new EffectAbility(() -> new MobEffectInstance(ModEffects.fireWalk, 400, 2), () -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 400, 0));
    public static final Ability<?> windWalk = new EffectAbility(() -> new MobEffectInstance(ModEffects.windWalk, 400, 2));
    public static final Ability<?> animalBreed = new BreedAbility();
}
