package com.feywild.feywild.world.gen.feature;

import com.feywild.feywild.world.gen.template.TemplatePlacementAction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TemplateFeature extends Feature<TemplateFeatureConfig> {
    
    public TemplateFeature() {
        super(TemplateFeatureConfig.CODEC);
    }

    @Override
    public boolean place(@Nonnull FeaturePlaceContext<TemplateFeatureConfig> context) {
        TemplateFeatureConfig cfg = context.config();
        Optional<WeightedEntry.Wrapper<ResourceLocation>> templateWrapper = cfg.getTemplates().getRandom(context.random());
        if (templateWrapper.isEmpty()) return false;
        ResourceLocation templateId = templateWrapper.get().getData();

        StructureTemplate template = Objects.requireNonNull(context.level().getServer()).getStructureManager().get(templateId).orElse(null);
        if (template == null) return false;

        StructurePlaceSettings settings = new StructurePlaceSettings();
        settings.setIgnoreEntities(true);
        settings.clearProcessors();
        cfg.getProcessors().get().list().forEach(settings::addProcessor);
        settings.setMirror(switch (context.random().nextInt(3)) {
            default -> Mirror.NONE;
            case 1 -> Mirror.FRONT_BACK;
            case 2 -> Mirror.LEFT_RIGHT;
        });
        settings.setRotation(Rotation.getRandom(context.random()));

        Vec3i size = template.getSize(settings.getRotation());
        Vec3i offset = new Vec3i(-(size.getX() / 2) + cfg.getOffset().getX(), cfg.getOffset().getY(), -(size.getZ() / 2) + cfg.getOffset().getZ());
        
        BlockPos start = context.origin().offset(offset);
        if (!template.placeInWorld(context.level(), start, start, settings, context.random(), 18)) return false;
        
        if (!cfg.getActions().isEmpty()) {
            List<StructureTemplate.StructureBlockInfo> processedBlocks = StructureTemplate.processBlockInfos(context.level(), start, start, settings, settings.getRandomPalette(template.palettes, start).blocks(), template);

            for (TemplatePlacementAction action : cfg.getActions()) {
                action.act(context.level(), template, settings, start, processedBlocks, context.random().fork());
            }
        }
        
        return true;
    }
}
