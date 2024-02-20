package com.feywild.feywild.item;

import com.feywild.feywild.block.FeyAltarBlock;
import com.feywild.feywild.item.render.FeyAltarItemRenderer;
import com.feywild.feywild.quest.Alignment;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class FeyAltarItem extends BlockItem implements GeoItem, FeyAltarBlock.FeyAltarModelProperties {
    
    private final Alignment alignment;

    public FeyAltarItem(Block block, Alignment alignment) {
        super(block, new Item.Properties());
        this.alignment = alignment;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(@Nonnull Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {
            private final BlockEntityWithoutLevelRenderer renderer = new FeyAltarItemRenderer();

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer;
            }
        });
    }

    @Override
    public Alignment getAlignment() {
        return alignment;
    }

    private final AnimatableInstanceCache animationCache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, event -> PlayState.CONTINUE));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animationCache;
    }
}
