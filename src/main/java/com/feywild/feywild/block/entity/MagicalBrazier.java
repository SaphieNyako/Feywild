package com.feywild.feywild.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.moddingx.libx.base.tile.BlockEntityBase;
import org.moddingx.libx.base.tile.TickingBlock;
import software.bernie.geckolib.animatable.GeoBlockEntity;

// UPDATE_TODO animations
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

/*
    private <E extends IAnimatable> PlayState animationPredicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.magical_brazier.hover", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::animationPredicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.animationFactory;
    }
 */
}
