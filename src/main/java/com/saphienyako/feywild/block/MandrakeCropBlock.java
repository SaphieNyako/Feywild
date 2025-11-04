package com.saphienyako.feywild.block;

import com.saphienyako.feywild.item.ModItems;
import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;

public class MandrakeCropBlock extends CropBlock {

    private static final VoxelShape[] SHAPES = new VoxelShape[]{
            Block.box(0, 0, 0, 16, 2, 16),
            Block.box(0, 0, 0, 16, 4, 16),
            Block.box(0, 0, 0, 16, 6, 16),
            Block.box(0, 0, 0, 16, 8, 16),
            Block.box(0, 0, 0, 16, 10, 16),
            Block.box(0, 0, 0, 16, 12, 16),
            Block.box(0, 0, 0, 16, 14, 16),
            Block.box(0, 0, 0, 16, 16, 16)
    };

    public MandrakeCropBlock(Properties pProperties) {
        super(pProperties);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return SHAPES[state.getValue(this.getAgeProperty())];
    }

    @Nonnull
    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.MANDRAKE_CROP_SEED.get();
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hit) {
            level.playSound(player, pos, ModSounds.MANDRAKE_SCREAM.get(), SoundSource.BLOCKS, 0.6f, 0.8f);
            return super.use(state, level, pos, player, hand, hit);
        }
    }
