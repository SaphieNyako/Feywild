package com.feywild.feywild.block.entity;

import com.feywild.feywild.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nonnull;

public class ElectrifiedGroundTileEntity extends TileEntity implements IAnimatable, ITickableTileEntity {

    private final AnimationFactory factory = new AnimationFactory(this);
    int life = 30;
    AxisAlignedBB box;
    boolean init = true;

    public ElectrifiedGroundTileEntity() {
        super(ModBlocks.ELECTRIFIED_GROUND_ENTITY.get());
    }

    // ANIMATION
    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.circle.appear", false));
        return PlayState.CONTINUE;
    }

    // LIFE
    @Override
    public void tick() {
        if (level != null && !level.isClientSide) {
            if (life % 2 == 0) {
                if (init) {
                    box = new AxisAlignedBB(getBlockPos());
                    init = false;
                }
                box = box.inflate(0.2, 0.1, 0.2);
                level.getEntities(null, box).forEach(entity -> {
                    if (entity.isAlive() && entity instanceof MonsterEntity)
                        entity.hurt(DamageSource.LIGHTNING_BOLT, 1);
                });
            }

            life--;
            if (life <= 0) {
                level.setBlock(getBlockPos(), Blocks.AIR.defaultBlockState(), 2);
            }
        }
    }

    @Override
    public void load(@Nonnull BlockState state, @Nonnull CompoundNBT nbt) {
        super.load(state, nbt);
        life = nbt.getInt("life");
    }

    @Nonnull
    @Override
    public CompoundNBT save(CompoundNBT compound) {
        compound.putInt("life", life);
        return super.save(compound);
    }
}
