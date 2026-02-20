package com.saphienyako.feywild.block.trees;

import com.mojang.serialization.MapCodec;
import com.saphienyako.feywild.Feywild;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.phys.Vec3;

public class FeySaplingBlock extends BushBlock implements BonemealableBlock {

    public static final ResourceLocation STRUCTURE =
            ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "autumn_tree");

    public FeySaplingBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return simpleCodec(FeySaplingBlock::new);
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
        StructureTemplateManager manager = level.getStructureManager();
        StructureTemplate template = manager.get(STRUCTURE).orElse(null);

        Feywild.LOGGER.info("Template loaded: {}", template != null);

        if (template == null) {
            Feywild.LOGGER.error("Structure not found: {}", STRUCTURE);
            return;
        }

        BlockPos newPos = new BlockPos(pos.getX()-1, pos.getY(), pos.getZ()-1);

        StructurePlaceSettings settings = new StructurePlaceSettings()
                .setIgnoreEntities(true)
                .setRotation(Rotation.NONE)
                .setMirror(Mirror.NONE)
                .setRotationPivot(pos);

        template.placeInWorld(level, newPos, newPos, settings, random, Block.UPDATE_ALL);
    }
}