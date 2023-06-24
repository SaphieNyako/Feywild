package com.feywild.feywild.data.worldgen;

import com.feywild.feywild.world.gen.structure.FeywildStructurePoolElement;
import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import org.moddingx.libx.datagen.DatagenContext;
import org.moddingx.libx.datagen.provider.sandbox.TemplateProviderBase;

public class TemplateProvider extends TemplateProviderBase {

    private static final ResourceKey<StructureProcessorList> EMPTY = ResourceKey.create(Registries.PROCESSOR_LIST, new ResourceLocation("minecraft", "empty"));
    
    public final Holder<StructureTemplatePool> blacksmith = this.template().element(this.feywild("blacksmith")).build();
    public final Holder<StructureTemplatePool> library = this.template().element(this.feywild("library")).build();
    public final Holder<StructureTemplatePool> springWorldTree = this.template().element(this.feywild("spring_world_tree")).build();
    public final Holder<StructureTemplatePool> summerWorldTree = this.template().element(this.feywild("summer_world_tree")).build();
    public final Holder<StructureTemplatePool> autumnWorldTree = this.template().element(this.feywild("autumn_world_tree")).build();
    public final Holder<StructureTemplatePool> winterWorldTree = this.template().element(this.feywild("winter_world_tree")).build();
    public final Holder<StructureTemplatePool> beekeep = this.template().element(this.feywild("beekeep")).build();
    public final Holder<StructureTemplatePool> feyCircle = this.template().element(this.feywild("fey_circle")).build();
    public final Holder<StructureTemplatePool> feyGeode = this.template().element(this.feywild("fey_geode")).build();

    public TemplateProvider(DatagenContext ctx) {
        super(ctx);
    }

    private StructurePoolElement feywild(String templatePath) {
        return feywild(templatePath, EMPTY);
    }

    @SuppressWarnings("SameParameterValue")
    private StructurePoolElement feywild(String templatePath, ResourceKey<StructureProcessorList> processor) {
        return feywild(this.mod.resource(templatePath), processor);
    }

    private StructurePoolElement feywild(ResourceLocation id) {
        return feywild(id, EMPTY);
    }

    private StructurePoolElement feywild(ResourceLocation id, ResourceKey<StructureProcessorList> processor) {
        return new FeywildStructurePoolElement(Either.left(id), this.holder(processor), StructureTemplatePool.Projection.RIGID);
    }
}
