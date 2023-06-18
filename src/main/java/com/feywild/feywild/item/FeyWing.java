package com.feywild.feywild.item;

import com.feywild.feywild.config.MiscConfig;
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

import net.minecraft.world.item.Item.Properties;

public class FeyWing extends GeoArmorItem implements IAnimatable {

    private static final Map<ArmorMaterial, MobEffectInstance> MATERIAL_TO_EFFECT_MAP =
            (new ImmutableMap.Builder<ArmorMaterial, MobEffectInstance>())
                    .put(ModArmorMaterials.FEY_WINGS, new MobEffectInstance(ModEffects.feyFlying, 20 * 60)).build();

    public Variant variant;
    public AnimationFactory factory = new AnimationFactory(this);

    public FeyWing(ArmorMaterial materialIn, EquipmentSlot slot, Properties builder, Variant variant) {
        super(materialIn, slot, builder);
        this.variant = variant;
    }

    // UPDATE_TODO correctly apply effect
    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        setPrideWings(stack);
        if (stack.getDisplayName().getString().contains("lesbian_pride_wings")) {
            setVariant(Variant.LESBIAN_PRIDE);
        }
        for (Map.Entry<ArmorMaterial, MobEffectInstance> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
            ArmorMaterial mapArmorMaterial = entry.getKey();
            MobEffectInstance mapStatusEffect = entry.getValue();
            if (player.getInventory().getArmor(2).getItem() instanceof FeyWing && MiscConfig.players_can_fly) {
                addStatusEffectForMaterial(player, mapArmorMaterial, mapStatusEffect);
            }
        }
    }

    private void setPrideWings(ItemStack stack) {
        if (stack.getDisplayName().getString().contains("lesbian_pride_wings")) {
            setVariant(Variant.LESBIAN_PRIDE);
        } else if (stack.getDisplayName().getString().contains("trans_pride_wings")) {
            setVariant(Variant.TRANS_PRIDE);
        } else if (stack.getDisplayName().getString().contains("gay_pride_wings")) {
            setVariant(Variant.GAY_PRIDE);
        } else if (stack.getDisplayName().getString().contains("bi_pride_wings")) {
            setVariant(Variant.BI_PRIDE);
        } else if (stack.getDisplayName().getString().contains("pan_pride_wings")) {
            setVariant(Variant.PAN_PRIDE);
        } else if (stack.getDisplayName().getString().contains("aroace_pride_wings")) {
            setVariant(Variant.AROACE_PRIDE);
        } else if (stack.getDisplayName().getString().contains("asexual_pride_wings")) {
            setVariant(Variant.ASEXUAL_PRIDE);
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

    public void setVariant(Variant variant) {
        this.variant = variant;
    }

    public enum Variant {

        SPRING("spring"), SUMMER("summer"), AUTUMN("autumn"), WINTER("winter"), BLOSSOM("blossom"), LIGHT("light"), SHADOW("shadow"), HEXEN("hexen"),
        LESBIAN_PRIDE("lesbian_pride"), TRANS_PRIDE("trans_pride"), GAY_PRIDE("gay_pride"), BI_PRIDE("bi_pride"), PAN_PRIDE("pan_pride"),
        AROACE_PRIDE("aroace_pride"), ASEXUAL_PRIDE("asexual_pride");

        public final String id;

        Variant(String id) {
            this.id = id;
        }
    }
}
