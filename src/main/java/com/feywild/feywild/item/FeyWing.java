package com.feywild.feywild.item;

import com.feywild.feywild.effects.ModEffects;
import com.google.common.collect.ImmutableMap;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.item.GeoArmorItem;

import java.util.Map;

public class FeyWing extends GeoArmorItem implements IAnimatable {

    private static final Map<ArmorMaterial, MobEffectInstance> MATERIAL_TO_EFFECT_MAP =
            (new ImmutableMap.Builder<ArmorMaterial, MobEffectInstance>())
                    .put(ModArmorMaterials.FEY_WINGS, new MobEffectInstance(ModEffects.feyFlying)).build();

    public final Variant variant;
    public AnimationFactory factory = new AnimationFactory(this);

    public FeyWing(ArmorMaterial materialIn, EquipmentSlot slot, Properties builder, Variant variant) {
        super(materialIn, slot, builder);
        this.variant = variant;
    }

    public static void canPlayerFly(Player player) {
        var abilities = player.getAbilities();
        if (!player.isCreative() && !player.isSpectator()) {
            abilities.mayfly = hasCorrectArmorOn(player);
            abilities.flying = hasCorrectArmorOn(player);
        }
    }

    public static boolean hasCorrectArmorOn(Player player) {
        return player.getInventory().getArmor(2).getItem() instanceof FeyWing;

    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {


        for (Map.Entry<ArmorMaterial, MobEffectInstance> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
            ArmorMaterial mapArmorMaterial = entry.getKey();
            MobEffectInstance mapStatusEffect = entry.getValue();
            if (player.getInventory().getArmor(2).getItem() instanceof FeyWing) {
                addStatusEffectForMaterial(player, mapArmorMaterial, mapStatusEffect);
            }
        }

    }

    private void addStatusEffectForMaterial(Player player, ArmorMaterial mapArmorMaterial, MobEffectInstance mapStatusEffect) {
        boolean hasPlayerEffect = player.hasEffect(mapStatusEffect.getEffect());
        if (!hasPlayerEffect) {
            player.addEffect(new MobEffectInstance(mapStatusEffect.getEffect(),
                    mapStatusEffect.getDuration(), mapStatusEffect.getAmplifier()));
        }
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<FeyWing>(this, "controller", 0, this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> animationEvent) {
        animationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("move", true));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    public enum Variant {

        SPRING("spring"), SUMMER("summer"), AUTUMN("autumn"), WINTER("winter"), BLOSSOM("blossom"), LIGHT("light"), SHADOW("shadow"), HEXEN("hexen");

        public final String id;

        Variant(String id) {
            this.id = id;
        }
    }
}
