package com.feywild.feywild.quest.task;

import com.feywild.feywild.FeyRegistries;
import com.feywild.feywild.block.trees.BaseTree;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryManager;

public class GrowTreeTask extends RegistryTaskType<BaseTree, BlockState> {

    public static final GrowTreeTask INSTANCE = new GrowTreeTask();

    private GrowTreeTask() {
        super("tree");
    }

    @Override
    public IForgeRegistry<BaseTree> registry() {
        //noinspection UnstableApiUsage
        return RegistryManager.ACTIVE.getRegistry(FeyRegistries.TREES);
    }

    @Override
    public Class<BlockState> testType() {
        return BlockState.class;
    }

    @Override
    public boolean checkCompleted(ServerPlayer player, ResourceKey<BaseTree> element, BlockState match) {
        //noinspection UnstableApiUsage
        BaseTree tree = RegistryManager.ACTIVE.getRegistry(FeyRegistries.TREES).getValue(element.location());
        return tree != null && tree.getSapling() == match.getBlock();
    }
}
