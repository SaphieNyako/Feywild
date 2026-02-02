package com.saphienyako.feywild.entity.goals;

import com.saphienyako.feywild.entity.MandragoraEntity;
import com.saphienyako.feywild.network.ParticleMessage;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Map;

public class SingGoal extends Goal {


    protected final Level level;
    protected final MandragoraEntity entity;
    private int ticksLeft = 0;

    public static final Map<MandragoraEntity.MandragoraVariant, BlockState> FLOWER_VARIANTS = Map.ofEntries(
            Map.entry(MandragoraEntity.MandragoraVariant.ALLIUM, Blocks.ALLIUM.defaultBlockState()),
            Map.entry(MandragoraEntity.MandragoraVariant.AZURE_BLUET, Blocks.AZURE_BLUET.defaultBlockState()),
            Map.entry(MandragoraEntity.MandragoraVariant.BLUE_ORCHID, Blocks.BLUE_ORCHID.defaultBlockState()),
            Map.entry(MandragoraEntity.MandragoraVariant.CORNFLOWER, Blocks.CORNFLOWER.defaultBlockState()),
            Map.entry(MandragoraEntity.MandragoraVariant.DANDELION, Blocks.DANDELION.defaultBlockState()),
            Map.entry(MandragoraEntity.MandragoraVariant.LILY_OF_THE_VALLEY, Blocks.LILY_OF_THE_VALLEY.defaultBlockState()),
            Map.entry(MandragoraEntity.MandragoraVariant.ORANGE_TULIP, Blocks.ORANGE_TULIP.defaultBlockState()),
            Map.entry(MandragoraEntity.MandragoraVariant.PINK_TULIP, Blocks.PINK_TULIP.defaultBlockState()),
            Map.entry(MandragoraEntity.MandragoraVariant.WHITE_TULIP, Blocks.WHITE_TULIP.defaultBlockState()),
            Map.entry(MandragoraEntity.MandragoraVariant.RED_TULIP, Blocks.RED_TULIP.defaultBlockState()),
            Map.entry(MandragoraEntity.MandragoraVariant.OXEYE_DAISY, Blocks.OXEYE_DAISY.defaultBlockState()),
            Map.entry(MandragoraEntity.MandragoraVariant.POPPY, Blocks.POPPY.defaultBlockState()),
            Map.entry(MandragoraEntity.MandragoraVariant.WITHER_ROSE, Blocks.WITHER_ROSE.defaultBlockState()),
            Map.entry(MandragoraEntity.MandragoraVariant.TORCHFLOWER, Blocks.TORCHFLOWER.defaultBlockState())

    );

    public SingGoal(MandragoraEntity mandragora) {
        this.entity = mandragora;
        this.level = mandragora.level();

    }


    @Override
    public boolean canUse() {
        Player owning = this.entity.getOwningPlayer();
        if (owning instanceof ServerPlayer && this.entity.getAbilityActive()) {
            return this.level.random.nextFloat() < 0.01f;
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
            } else if (this.ticksLeft <= 20) {
                //MANDRAGORA SINGING NOTES
                if(level.random.nextInt(3) < 1) {
                    PacketDistributor.sendToPlayersTrackingEntity(
                            entity,
                            new ParticleMessage(
                                    ParticleMessage.Particles.SINGING,
                                    entity.blockPosition().above()
                            )
                    );
                }
            } else if (this.ticksLeft == 35) {
                this.singing();
                //TODO add Sound
                this.entity.playSound(entity.getSingSound(), 1, 1);
            }
        }
    }

    private void growFlowersAroundMandragora() {
        if (level.isClientSide()) return;

        BlockPos basePos = entity.blockPosition();
        if (!entity.onGround()) return;

        int radius = 3;
        BlockPos.MutableBlockPos mpos = new BlockPos.MutableBlockPos();

        for (BlockPos pos : BlockPos.betweenClosed(
                basePos.offset(-radius, 0, -radius),
                basePos.offset(radius, 0, radius))) {

            if (!pos.closerToCenterThan(entity.position(), radius)) continue;

            mpos.set(pos);

            BlockState blockAtPos = level.getBlockState(mpos);

            if (!blockAtPos.isAir()) continue;

            BlockState blockBelow = level.getBlockState(mpos.below());

            if (!blockBelow.is(BlockTags.DIRT)) continue;

            BlockState flower = getRandomFlower(entity.getRandom(), entity);
            if (flower == null) continue;

            if (!flower.canSurvive(level, mpos)) continue;
            if (!level.isUnobstructed(flower, mpos, CollisionContext.empty())) continue;

            if (level instanceof ServerLevel serverLevel) {
                serverLevel.setBlock(mpos, flower, 3);
                serverLevel.sendBlockUpdated(mpos, Blocks.AIR.defaultBlockState(), flower, 3);

            }

            System.out.println("Placed flower at: " + mpos);
        }
    }

    private static BlockState getRandomFlower(RandomSource random, MandragoraEntity entity ) {
        if(entity.getVariant() == MandragoraEntity.MandragoraVariant.DEFAULT) {
            return switch (random.nextInt(8)) {
                case 0 -> Blocks.RED_TULIP.defaultBlockState();
                case 1 -> Blocks.DANDELION.defaultBlockState();
                case 2 -> Blocks.ORANGE_TULIP.defaultBlockState();
                case 3 -> Blocks.BLUE_ORCHID.defaultBlockState();
                case 4 -> Blocks.ALLIUM.defaultBlockState();
                case 5 -> Blocks.AZURE_BLUET.defaultBlockState();
                case 6 -> Blocks.WHITE_TULIP.defaultBlockState();
                case 7 -> Blocks.OXEYE_DAISY.defaultBlockState();
                default -> Blocks.SHORT_GRASS.defaultBlockState();
            };
        } else {
            return switch (random.nextInt(2)) {
                case 0 -> FLOWER_VARIANTS.get(entity.getVariant());
                default -> Blocks.SHORT_GRASS.defaultBlockState();
            };
        }
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
