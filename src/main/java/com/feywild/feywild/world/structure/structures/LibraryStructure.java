package com.feywild.feywild.world.structure.structures;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.config.WorldGenConfig;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.Registry;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.structures.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

import javax.annotation.Nonnull;
import java.util.List;

import net.minecraft.world.level.levelgen.feature.StructureFeature.StructureStartFactory;

public class LibraryStructure extends BaseStructure {
    
    public final static int SEED_MODIFIER = 1238904567;
    private static final List<MobSpawnSettings.SpawnerData> STRUCTURE_CREATURES = ImmutableList.of(
            new MobSpawnSettings.SpawnerData(EntityType.VILLAGER, 1, 1, 2)
    );
    
    private static final String POOL = "library/start_pool";

    @Override
    public int getAverageDistanceBetweenChunks() {
        return WorldGenConfig.structures.library.average_distance;
    }

    @Override
    public int getMinDistanceBetweenChunks() {
        return WorldGenConfig.structures.library.minimum_distance;
    }

    @Override
    public int getSeedModifier() {
        return SEED_MODIFIER;
    }

    @Nonnull
    @Override
    public StructureStartFactory<NoneFeatureConfiguration> getStartFactory() {
        return LibraryStructure.Start::new;
    }

    @Override
    public List<MobSpawnSettings.SpawnerData> getDefaultCreatureSpawnList() {
        return STRUCTURE_CREATURES;
    }

    //START CLASS
    //TODO: make BaseStart class
    public static class Start extends StructureStart<NoneFeatureConfiguration> {

        public Start(StructureFeature<NoneFeatureConfiguration> structureIn, int chunkX, int chunkZ, BoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
        }

        @Override  //generatePieces
        public void generatePieces(@Nonnull RegistryAccess dynamicRegistryManager, @Nonnull ChunkGenerator chunkGenerator, @Nonnull StructureManager templateManagerIn, int chunkX, int chunkZ, @Nonnull Biome biomeIn, @Nonnull NoneFeatureConfiguration config) {

            // Turns the chunk coordinates into actual coordinates we can use. (Gets center of that chunk)
            int x = (chunkX << 4) + 7;
            int z = (chunkZ << 4) + 7;

            BlockPos blockpos = new BlockPos(x, 0, z);

            ResourceLocation res;

            if(getFeature() instanceof LibraryStructure){
                res = new ResourceLocation(FeywildMod.getInstance().modid, POOL);
            }
            else
                res = new ResourceLocation(FeywildMod.getInstance().modid, POOL);

            //addpieces()
            JigsawPlacement.addPieces(
                    dynamicRegistryManager,

                    new JigsawConfiguration(() -> dynamicRegistryManager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
                            .get(res),
                            10),

                    PoolElementStructurePiece::new,
                    chunkGenerator,
                    templateManagerIn,
                    blockpos,
                    this.pieces,
                    this.random,
                    false,
                    true);
            // Keep this false when placing structures in the nether as otherwise, heightmap placing will put the structure on the Bedrock roof.

            //OPTIONAL
            this.pieces.forEach(piece -> piece.move(0, 1, 0));
            this.pieces.forEach(piece -> piece.getBoundingBox().y1 -= 1);

            // Sets the bounds of the structure once you are finished. // calculateBoundingBox();
            this.calculateBoundingBox();

           /* FeywildMod.getInstance().logger.log(Level.DEBUG, MESSAGE_LOCATION +
                    this.pieces.get(0).getBoundingBox().x0 + " " +
                    this.pieces.get(0).getBoundingBox().y0 + " " +
                    this.pieces.get(0).getBoundingBox().z0);
            */
        }
    }

}




