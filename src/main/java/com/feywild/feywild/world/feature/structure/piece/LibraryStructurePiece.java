package com.feywild.feywild.world.feature.structure.piece;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.NotNull;

public class LibraryStructurePiece extends FeywildStructurePiece {

    public static final StructurePoolElementType<LibraryStructurePiece> TYPE = type(LibraryStructurePiece::new);

    protected LibraryStructurePiece(Either<ResourceLocation, StructureTemplate> template, Holder<StructureProcessorList> processors, StructureTemplatePool.Projection projection) {
        super(template, processors, projection);
    }

    @NotNull
    @Override
    public StructurePoolElementType<?> getType() {
        return TYPE;
    }

    // placementOffset
}
