package com.feywild.feywild.world.gen.feature;

import com.feywild.feywild.FeyRegistries;
import com.feywild.feywild.world.gen.template.TemplatePlacementAction;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraftforge.registries.RegistryManager;
import org.moddingx.libx.codec.MoreCodecs;

import java.util.ArrayList;
import java.util.List;

public class TemplateFeatureConfig implements FeatureConfiguration {
    
    @SuppressWarnings("UnstableApiUsage")
    public static final Codec<TemplateFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            WeightedRandomList.codec(WeightedEntry.Wrapper.codec(ResourceLocation.CODEC)).fieldOf("templates").forGetter(TemplateFeatureConfig::getTemplates),
            StructureProcessorType.LIST_CODEC.fieldOf("processors").forGetter(TemplateFeatureConfig::getProcessors),
            MoreCodecs.lazy(() -> RegistryManager.ACTIVE.getRegistry(FeyRegistries.TEMPLATE_ACTIONS).getCodec()).listOf().fieldOf("actions").forGetter(TemplateFeatureConfig::getActions),
            BlockPos.CODEC.optionalFieldOf("offset", BlockPos.ZERO).forGetter(TemplateFeatureConfig::getOffset)
    ).apply(instance, TemplateFeatureConfig::new));
    
    private final WeightedRandomList<WeightedEntry.Wrapper<ResourceLocation>> templates;
    private final Holder<StructureProcessorList> processors;
    private final List<TemplatePlacementAction> actions;
    private final BlockPos offset;
    
    private TemplateFeatureConfig(WeightedRandomList<WeightedEntry.Wrapper<ResourceLocation>> templates, Holder<StructureProcessorList> processors, List<TemplatePlacementAction> actions, BlockPos offset) {
        this.templates = templates;
        this.processors = processors;
        this.actions = List.copyOf(actions);
        this.offset = offset.immutable();
    }

    public WeightedRandomList<WeightedEntry.Wrapper<ResourceLocation>> getTemplates() {
        return templates;
    }

    public Holder<StructureProcessorList> getProcessors() {
        return processors;
    }

    public List<TemplatePlacementAction> getActions() {
        return actions;
    }

    public BlockPos getOffset() {
        return offset;
    }

    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
    
        private final List<WeightedEntry.Wrapper<ResourceLocation>> templates;
        private Holder<StructureProcessorList> processors;
        private final List<TemplatePlacementAction> actions;
        private BlockPos offset;

        private Builder() {
            this.templates = new ArrayList<>();
            this.processors = Holder.direct(new StructureProcessorList(List.of()));
            this.actions = new ArrayList<>();
            this.offset = BlockPos.ZERO;
        }

        public Builder template(ResourceLocation template) {
            return this.template(template, 1);
        }
        
        public Builder template(ResourceLocation template, int weight) {
            this.templates.add(WeightedEntry.wrap(template, weight));
            return this;
        }

        public Builder processors(StructureProcessorList processors) {
            return this.processors(Holder.direct(processors));
        }
        
        public Builder processors(Holder<StructureProcessorList> processors) {
            this.processors = processors;
            return this;
        }

        public Builder action(TemplatePlacementAction action) {
            this.actions.add(action);
            return this;
        }

        public Builder offset(BlockPos offset) {
            this.offset = offset;
            return this;
        }

        public TemplateFeatureConfig build() {
            return new TemplateFeatureConfig(WeightedRandomList.create(this.templates), this.processors, List.copyOf(this.actions), this.offset);
        }
    }
}
