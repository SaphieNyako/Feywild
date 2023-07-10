package com.feywild.feywild.item;

import com.feywild.feywild.config.MiscConfig;
import com.feywild.feywild.effects.ModEffects;
import com.feywild.feywild.item.render.FeyWingsRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FeyWing extends ArmorItem implements GeoItem {

    private final Variant variant;
    
    public FeyWing(ArmorMaterial materialIn, Properties builder, Variant variant) {
        super(materialIn, Type.CHESTPLATE, builder);
        this.variant = variant;
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (player.getItemBySlot(this.getEquipmentSlot()).getItem() instanceof FeyWing && MiscConfig.players_can_fly) {
            if (!player.hasEffect(ModEffects.feyFlying)) {
                player.addEffect(new MobEffectInstance(ModEffects.feyFlying, 60 * 20, 0));
            }
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        Map<Variant, FeyWingsRenderer> rendererMap = Arrays.stream(Variant.values()).collect(Collectors.toUnmodifiableMap(Function.identity(), FeyWingsRenderer::new));
        consumer.accept(new IClientItemExtensions() {
            
            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> model) {
                return rendererMap.get(getRenderVariant(stack));
            }
        });
    }

    private Variant getRenderVariant(ItemStack stack) {
        return switch (stack.getDisplayName().getString().strip()) {
            case "lesbian_pride_wings" -> Variant.LESBIAN_PRIDE;
            case "trans_pride_wings" -> Variant.TRANS_PRIDE;
            case "gay_pride_wings" -> Variant.GAY_PRIDE;
            case "bi_pride_wings" -> Variant.BI_PRIDE;
            case "pan_pride_wings" -> Variant.PAN_PRIDE;
            case "aroace_pride_wings" -> Variant.AROACE_PRIDE;
            case "asexual_pride_wings" -> Variant.ASEXUAL_PRIDE;
            default -> this.variant;
        };
    }

    private final AnimatableInstanceCache animationCache = GeckoLibUtil.createInstanceCache(this);
    
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, event -> {
            event.getController().setAnimation(RawAnimation.begin().thenLoop("move"));
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animationCache;
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
