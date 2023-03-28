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

import javax.annotation.Nullable;
import java.util.Map;

public class FeyWing extends GeoArmorItem implements IAnimatable {

    private static final Map<ArmorMaterial, MobEffectInstance> MATERIAL_TO_EFFECT_MAP =
            (new ImmutableMap.Builder<ArmorMaterial, MobEffectInstance>())
                    .put(ModArmorMaterials.FEY_WINGS, new MobEffectInstance(ModEffects.windWalk)).build();
    public final Variant variant;
    public AnimationFactory factory = new AnimationFactory(this);

    public FeyWing(ArmorMaterial materialIn, EquipmentSlot slot, Properties builder, Variant variant) {
        super(materialIn, slot, builder);
        this.variant = variant;
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

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {
        var abilities = player.getAbilities();
        if (!abilities.mayfly) {
            abilities.mayfly = true;
        }
    }

    public enum Variant {

        SPRING("spring"), SUMMER("summer"), AUTUMN("autumn"), WINTER("winter"), BLOSSOM("blossom"), LIGHT("light"), SHADOW("shadow"), HEXEN("hexen");

        public final String id;

        Variant(String id) {
            this.id = id;
        }
        /*
        public static Variant byId(String id){
            return switch (id.toLowerCase(Locale.ROOT).trim()){
                case "spring" -> SPRING;
                case "summer" -> SUMMER;
                case "autumn" -> AUTUMN;
                case "winter" -> WINTER;
                case "blossom" -> BLOSSOM;
                case "light" -> LIGHT;
                case "shadow" -> SHADOW;
                case "hexen" -> HEXEN;
                default -> throw new IllegalArgumentException("Invalid Fey Wing Variant: " + id);
            };
        } */

        public static String getVariantId(@Nullable Variant variant) {
            return variant == null ? "spring" : variant.id;
        }
    }
}
