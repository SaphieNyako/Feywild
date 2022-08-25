package com.feywild.feywild.data.worldgen.data;

import io.github.noeppi_noeppi.mods.sandbox.datagen.ext.StructureSetData;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;

public class FeyStructureSets extends StructureSetData {

    private final FeyStructures structures = this.resolve(FeyStructures.class);
    
    public final Holder<StructureSet> blacksmith = this.simple(this.structures.blacksmith, 10, 5, 1).build();
    public final Holder<StructureSet> library = this.simple(this.structures.library, 10, 5, 1).build();
    public final Holder<StructureSet> springWorldTree = this.simple(this.structures.springWorldTree, 10, 5, 1).build();
    public final Holder<StructureSet> summerWorldTree = this.simple(this.structures.summerWorldTree, 10, 5, 1).build();
    public final Holder<StructureSet> autumnWorldTree = this.simple(this.structures.autumnWorldTree, 10, 5, 1).build();
    public final Holder<StructureSet> winterWorldTree = this.simple(this.structures.winterWorldTree, 10, 5, 1).build();
    public final Holder<StructureSet> beekeep = this.simple(this.structures.beekeep, 10, 5, 1).build();
    public final Holder<StructureSet> feyCircle = this.simple(this.structures.feyCircle, 10, 5, 1).build();
    
    public FeyStructureSets(Properties properties) {
        super(properties);
    }
    
    @SuppressWarnings("SameParameterValue")
    private RandomPlacementBuilder simple(Holder<Structure> structure, int spacing, int separation, float frequency) {
        return this.structureSet()
                .entry(structure)
                .placeRandom(spacing, separation)
                .frequency(frequency);
    }
}
