package com.feywild.feywild.util;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FeywildJigsawHelper {

    private static final ResourceKey<StructureProcessorList> PROCESSOR_LIST_KEY = ResourceKey.create(Registry.PROCESSOR_LIST, new ResourceLocation("minecraft", "empty"));

    public static void registerJigsaw(MinecraftServer server, ResourceLocation poolLocation, ResourceLocation nbtLocation, int weight) {

        RegistryAccess manager = server.registryAccess();
        Holder<StructureProcessorList> processorListHolder = manager.registry(Registry.PROCESSOR_LIST).orElseThrow().getHolderOrThrow(PROCESSOR_LIST_KEY);
        SinglePoolElement element = SinglePoolElement.single(nbtLocation.toString(), processorListHolder).apply(StructureTemplatePool.Projection.RIGID);
        StructureTemplatePool pool = manager.registryOrThrow(Registry.TEMPLATE_POOL).get(poolLocation);

        if (pool == null) return;

        ObjectArrayList<StructurePoolElement> elements = Objects.requireNonNull(pool).templates;

        for (int i = 0; i < weight; i++) {
            elements.add(element);
        }

        List<Pair<StructurePoolElement, Integer>> elementCounts = new ArrayList(pool.rawTemplates);
        elements.addAll(pool.templates);
        elementCounts.addAll(pool.rawTemplates);

        pool.templates = elements;
        pool.rawTemplates = elementCounts;
    }

}
