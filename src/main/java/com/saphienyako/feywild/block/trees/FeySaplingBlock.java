package com.saphienyako.feywild.block.trees;

import com.saphienyako.feywild.Feywild;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.ArrayList;
import java.util.List;

public abstract class FeySaplingBlock extends BushBlock implements BonemealableBlock {

    public FeySaplingBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return random.nextFloat() < 0.45f;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        growStructure(level, pos, random);
    }

    private void growStructure(ServerLevel level, BlockPos pos, RandomSource random) {
        if (!canGrow(level, pos, 2, 6)) {
            sendPixieMessage(level, pos);
            return;
        }
        StructureTemplateManager manager = level.getStructureManager();

        ResourceLocation structureLocation = getTrees().get(random.nextInt(getTrees().size()));
        StructureTemplate template = manager.get(structureLocation).orElse(null);

        if (template == null) {
            Feywild.LOGGER.error("Structure not found: {}", structureLocation);
            return;
        }

        StructurePlaceSettings settings = new StructurePlaceSettings()
                .setIgnoreEntities(true)
                .addProcessor(getProcessor());

        Vec3i size = template.getSize();

        int halfX = size.getX() / 2;
        int halfZ = size.getZ() / 2;

        BlockPos placementPos = pos.offset(-halfX + 1, 0, -halfZ + 1);

        template.placeInWorld(level, placementPos, placementPos, settings, random, Block.UPDATE_ALL);
    }

    private boolean canGrow(ServerLevel level, BlockPos origin, int radius, int height) {
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                for (int dy = 0; dy <= height; dy++) {
                    cursor.set(origin.getX() + dx, origin.getY() + dy, origin.getZ() + dz);

                    BlockState state = level.getBlockState(cursor);

                    // Skip the sapling itself
                    if (cursor.equals(origin)) {
                        continue;
                    }
                    if (!state.isAir() && !state.canBeReplaced()) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private void sendPixieMessage(ServerLevel level, BlockPos pos) {
        if (!(level.getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), 5.0, false) instanceof ServerPlayer player)) {
            return;
        }

        player.displayClientMessage(
                Component.literal("[A Pixie whispers] ")
                        .withStyle(ChatFormatting.LIGHT_PURPLE)
                        .append(Component.literal("This little Sapling needs more room to grow!").withStyle(ChatFormatting.ITALIC)),
                true // actionbar; set false for chat
        );
    }

    abstract protected StructureProcessor getProcessor();

    abstract protected List<ResourceLocation> getTrees();

}