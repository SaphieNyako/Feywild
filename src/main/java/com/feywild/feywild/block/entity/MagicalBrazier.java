package com.feywild.feywild.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.moddingx.libx.base.tile.BlockEntityBase;
import org.moddingx.libx.base.tile.TickingBlock;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MagicalBrazier extends BlockEntityBase implements TickingBlock, GeoBlockEntity {


    private int textureNumber = 1;
    private int animationCount = 0;

    public MagicalBrazier(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public int getTextureNumber() {
        return this.textureNumber;
    }

    @Override
    public void tick() {
        if (level != null && level.isClientSide) {
            if (!(this.animationCount == 2)) {
                this.animationCount++;
            } else {
                this.animationCount = 0;

                if (!(this.textureNumber == 8)) {
                    this.textureNumber++;
                } else {
                    this.textureNumber = 1;

                }
            }
        }
    }

    private final AnimatableInstanceCache animationCache = GeckoLibUtil.createInstanceCache(this);
    
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, event -> {
            event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.magical_brazier.hover"));
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animationCache;
    }
}
