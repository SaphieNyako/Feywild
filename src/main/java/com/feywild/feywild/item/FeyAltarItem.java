package com.feywild.feywild.item;

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
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

// UPDATE_TODO animations
public class FeyAltarItem extends BlockItem implements GeoItem {
    
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
    
/*
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller",
                0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
 */

    public Alignment getAlignment() {
        return alignment;
    }
}
