package com.feywild.feywild.data.worldgen.data;

import com.feywild.feywild.world.gen.structure.FeywildStructurePoolElement;
import com.mojang.datafixers.util.Either;
import io.github.noeppi_noeppi.mods.sandbox.datagen.ext.TemplateData;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

public class FeyTemplates extends TemplateData {

    public final Holder<StructureTemplatePool> blacksmith = this.template().element(this.feywild("blacksmith")).build();
    public final Holder<StructureTemplatePool> library = this.template().element(this.feywild("library")).build();
    public final Holder<StructureTemplatePool> springWorldTree = this.template().element(this.feywild("spring_world_tree")).build();
    public final Holder<StructureTemplatePool> summerWorldTree = this.template().element(this.feywild("summer_world_tree")).build();
    public final Holder<StructureTemplatePool> autumnWorldTree = this.template().element(this.feywild("autumn_world_tree")).build();
    public final Holder<StructureTemplatePool> winterWorldTree = this.template().element(this.feywild("winter_world_tree")).build();
    public final Holder<StructureTemplatePool> beekeep = this.template().element(this.feywild("beekeep")).build();
    public final Holder<StructureTemplatePool> feyCircle = this.template().element(this.feywild("fey_circle")).build();
    
    public FeyTemplates(Properties properties) {
        super(properties);
    }

    private StructurePoolElement feywild(String templatePath) {
        return feywild(templatePath, ProcessorLists.EMPTY);
    }

    @SuppressWarnings("SameParameterValue")
    private StructurePoolElement feywild(String templatePath, Holder<StructureProcessorList> processor) {
        return feywild(this.mod.resource(templatePath), processor);
    }
    
    private StructurePoolElement feywild(ResourceLocation id) {
        return feywild(id, ProcessorLists.EMPTY);
    }
    
    private StructurePoolElement feywild(ResourceLocation id, Holder<StructureProcessorList> processor) {
        return new FeywildStructurePoolElement(Either.left(id), this.registries.holder(Registry.PROCESSOR_LIST_REGISTRY, processor), StructureTemplatePool.Projection.RIGID);
    }
}
