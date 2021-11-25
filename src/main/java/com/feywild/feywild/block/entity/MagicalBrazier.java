package com.feywild.feywild.block.entity;

import io.github.noeppi_noeppi.libx.base.tile.BlockEntityBase;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class MagicalBrazier extends BlockEntityBase implements IAnimatable {

    private final AnimationFactory animationFactory = new AnimationFactory(this);

    private int textureNumber = 1;
    private int animationCount = 0;

    public MagicalBrazier(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state); //1.16 only has type
    }

    public int getTextureNumber() {
        if (!(animationCount == 10)) {
            animationCount++;
        } else {
            animationCount = 0;

            if (!(textureNumber == 8)) {
                textureNumber++;
            } else {
                textureNumber = 1;

            }
        }

        return textureNumber;
    }

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
}
