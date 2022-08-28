package com.feywild.feywild.data.worldgen.data;

import io.github.noeppi_noeppi.mods.sandbox.datagen.ext.StructureSetData;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

public class FeyStructureSets extends StructureSetData {

    private final FeyStructures structures = this.resolve(FeyStructures.class);
    
    public final Holder<StructureSet> overworldHouses = this.structureSet()
            .entry(this.structures.library)
            .entry(this.structures.blacksmith)
            .placeRandom(10, 5)
            .frequency(0.9f)
            .build();
    
    public final Holder<StructureSet> worldTrees = this.structureSet()
            .entry(this.structures.springWorldTree)
            .entry(this.structures.summerWorldTree)
            .entry(this.structures.autumnWorldTree)
            .entry(this.structures.winterWorldTree)
            .placeRandom(50, 20)
            .spreadType(RandomSpreadType.TRIANGULAR)
            .frequency(0.6f)
            .build();
    
    public final Holder<StructureSet> beekeep = this.simple(this.structures.beekeep, 23, 8, 0.9f).build();
    public final Holder<StructureSet> feyCircle = this.simple(this.structures.feyCircle, 13, 6, 0.8f).build();
    
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
