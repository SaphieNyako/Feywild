package com.feywild.feywild.item;

import com.feywild.feywild.config.MiscConfig;
import com.feywild.feywild.effects.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoItem;

// UPDATE_TODO animations
public class FeyWing extends ArmorItem implements GeoItem {

    private final Variant variant;
    
    public FeyWing(ArmorMaterial materialIn, Properties builder, Variant variant) {
        super(materialIn, Type.CHESTPLATE, builder);
        this.variant = variant;
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        setPrideWings(stack);
        if (player.getItemBySlot(this.getEquipmentSlot()).getItem() instanceof FeyWing && MiscConfig.players_can_fly) {
            if (!player.hasEffect(ModEffects.feyFlying)) {
                player.addEffect(new MobEffectInstance(ModEffects.feyFlying, 60 * 20, 0));
            }
        }
    }

/*
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
*/

    public enum Variant {

        SPRING("spring"), SUMMER("summer"), AUTUMN("autumn"), WINTER("winter"), BLOSSOM("blossom"), LIGHT("light"), SHADOW("shadow"), HEXEN("hexen"),
        LESBIAN_PRIDE("lesbian_pride"), TRANS_PRIDE("trans_pride"), GAY_PRIDE("gay_pride"), BI_PRIDE("bi_pride"), PAN_PRIDE("pan_pride"),
        AROACE_PRIDE("aroace_pride"), ASEXUAL_PRIDE("asexual_pride");

        public final String id;

        Variant(String id) {
            this.id = id;
        }
    }
 */
}
