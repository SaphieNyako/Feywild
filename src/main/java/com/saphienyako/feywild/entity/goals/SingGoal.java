package com.saphienyako.feywild.entity.goals;

import com.saphienyako.feywild.entity.MandragoraEntity;
import com.saphienyako.feywild.network.ParticleMessage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Objects;

public class SingGoal extends Goal {


    protected final Level level;
    protected final MandragoraEntity entity;
    private int ticksLeft = 0;

    public SingGoal(MandragoraEntity mandragora) {
        this.entity = mandragora;
        this.level = mandragora.level();

    }


    @Override
    public boolean canUse() {
        Player owning = this.entity.getOwningPlayer();
        if (owning instanceof ServerPlayer && this.entity.getAbilityActive()) {
            return this.level.random.nextFloat() < 0.02f;
        } else {
            return false;
        }
    }

    private  void reset(){
        this.entity.setState(MandragoraEntity.State.IDLE);
        this.ticksLeft = -1;
    }



    @Override
    public void tick() {
        if (this.ticksLeft > 0) {
            this.ticksLeft--;
            if (this.ticksLeft <= 0) {
                if (!level.isClientSide()) {
                  growFlowersAroundMandragora();
                }
                this.reset();
            } else if (this.ticksLeft == 20) {
                //MANDRAGORA SINGING NOTES
                PacketDistributor.sendToPlayersTrackingEntity(
                        entity,
                        new ParticleMessage(
                                ParticleMessage.Particles.SINGING,
                                entity.blockPosition().above()
                        )
                );
            } else if (this.ticksLeft == 35) {
                this.singing();
                //TODO add Sound
                // this.entity.playSound(ModSoundEvents.shroomlingSneeze.getSoundEvent(), 1, 1);
            }
        }
    }
    /*
    private void growFlowersAroundMandragora() {
        Level level = entity.getEntityLevel();
        BlockPos basePos = entity.blockPosition();
        if (entity.onGround()) {
            int radius = Math.min(2, 2);
            BlockPos.MutableBlockPos mpos = new BlockPos.MutableBlockPos();
            for (BlockPos pos : BlockPos.betweenClosed(basePos.offset(-radius, 0, -radius), basePos.offset(radius, 0, radius))) {
                if (pos.closerToCenterThan(entity.position(), radius)) {
                    mpos.set(pos.getX(), pos.getY(), pos.getZ());
                    BlockState current = level.getBlockState(mpos);
                    if (current.isAir()) {
                        current = level.getBlockState(pos.below());
                        BlockState flower = getRandomFlower(entity.getRandom());
                        if (flower != null && current.is(BlockTags.DIRT) && flower.canSurvive(level, pos) && level.isUnobstructed(flower, pos, CollisionContext.empty())) {

                            level.setBlockAndUpdate(pos.above(), flower);


                            System.out.println("Entity pos: " + entity.blockPosition());
                            System.out.println("Block under entity: " +
                                    level.getBlockState(entity.blockPosition().below()));
                            System.out.println("Block place on;" + pos);

                        }
                    }
                }
            }
        }
    } */

    private void growFlowersAroundMandragora() {
        if (level.isClientSide()) return; // Goals run server-side, but just in case

        BlockPos basePos = entity.blockPosition();
        if (!entity.onGround()) return;

        int radius = 2; // or any value you want
        BlockPos.MutableBlockPos mpos = new BlockPos.MutableBlockPos();

        for (BlockPos pos : BlockPos.betweenClosed(
                basePos.offset(-radius, 0, -radius),
                basePos.offset(radius, 0, radius))) {

            // Check if within radius from entity center
            if (!pos.closerToCenterThan(entity.position(), radius)) continue;

            mpos.set(pos);

            BlockState blockAtPos = level.getBlockState(mpos);

            // Only place flowers in air
            if (!blockAtPos.isAir()) continue;

            BlockState blockBelow = level.getBlockState(mpos.below());

            // Only place on dirt/grass
            if (!blockBelow.is(BlockTags.DIRT)) continue;

            // Get a random flower
            BlockState flower = getRandomFlower(entity.getRandom());
            if (flower == null) continue;

            // Check if flower can survive at this position and space is unobstructed
            if (!flower.canSurvive(level, mpos)) continue;
            if (!level.isUnobstructed(flower, mpos, CollisionContext.empty())) continue;

            // Place flower on the server and notify clients
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.setBlock(mpos, flower, 3); // 3 = UPDATE_CLIENTS | UPDATE_NEIGHBORS
                serverLevel.sendBlockUpdated(mpos, Blocks.AIR.defaultBlockState(), flower, 3); // force client refresh
            }

            // Debug logging
            System.out.println("Placed flower at: " + mpos);
        }
    }

    private static BlockState getRandomFlower(RandomSource random) {
        return switch (random.nextInt(7)) {
            case 0 -> Blocks.RED_TULIP.defaultBlockState();
            case 1 -> Blocks.DANDELION.defaultBlockState();
            case 2 -> Blocks.ORANGE_TULIP.defaultBlockState();
            case 3 -> Blocks.BLUE_ORCHID.defaultBlockState();
            case 4 -> Blocks.ALLIUM.defaultBlockState();
            case 5 -> Blocks.AZURE_BLUET.defaultBlockState();
            case 6 -> Blocks.WHITE_TULIP.defaultBlockState();
            default -> Blocks.OXEYE_DAISY.defaultBlockState();
        };
    }


    private void singing() {
        this.entity.setState(MandragoraEntity.State.SING);
    }

    @Override
    public void start() {
        this.ticksLeft = 45;
    }

    @Override
    public boolean canContinueToUse() {
        return this.ticksLeft > 0;
    }
}
