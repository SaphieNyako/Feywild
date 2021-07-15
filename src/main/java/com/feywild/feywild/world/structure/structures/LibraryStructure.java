package com.feywild.feywild.world.structure.structures;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.util.configs.Config;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;
import org.apache.logging.log4j.Level;

import javax.annotation.Nonnull;
import java.util.List;

public class LibraryStructure extends BaseStructure {

    public final static int AVERAGE_DISTANCE_BETWEEN_CHUNKS = Config.LIBRARY_CONFIG.getCachedDistance();
    public final static int MIN_DISTANCE_BETWEEN_CHUNKS = Config.LIBRARY_CONFIG.getCachedMinDistance();
    public final static int SEED_MODIFIER = 1238904567;
    private static final List<MobSpawnInfo.Spawners> STRUCTURE_CREATURES = ImmutableList.of(
            new MobSpawnInfo.Spawners(EntityType.VILLAGER, 1, 1, 2)
    );
    private static final String MESSAGE_LOCATION = "Library at: ";
    private static final String MESSAGE_POOL = "library/start_pool";

    @Override
    public int getAverageDistanceBetweenChunks() {
        return AVERAGE_DISTANCE_BETWEEN_CHUNKS;
    }

    @Override
    public int getMinDistanceBetweenChunks() {
        return MIN_DISTANCE_BETWEEN_CHUNKS;
    }

    @Override
    public int getSeedModifier() {
        return SEED_MODIFIER;
    }

    @Override
    public IStartFactory<NoFeatureConfig> getStartFactory() {
        return LibraryStructure.Start::new;
    }

    @Override
    public List<MobSpawnInfo.Spawners> getDefaultCreatureSpawnList() {
        return STRUCTURE_CREATURES;
    }

    //START CLASS
    //TODO: make BaseStart class
    public static class Start extends StructureStart<NoFeatureConfig> {

        public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
        }

        @Override  //generatePieces
        public void generatePieces(@Nonnull DynamicRegistries dynamicRegistryManager, @Nonnull ChunkGenerator chunkGenerator, @Nonnull TemplateManager templateManagerIn, int chunkX, int chunkZ, @Nonnull Biome biomeIn, @Nonnull NoFeatureConfig config) {

            // Turns the chunk coordinates into actual coordinates we can use. (Gets center of that chunk)
            int x = (chunkX << 4) + 7;
            int z = (chunkZ << 4) + 7;

            BlockPos blockpos = new BlockPos(x, 0, z);

            //addpieces()
            JigsawManager.addPieces(
                    dynamicRegistryManager,

                    new VillageConfig(() -> dynamicRegistryManager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
                            .get(new ResourceLocation(FeywildMod.MOD_ID, MESSAGE_POOL)),
                            10),

                    AbstractVillagePiece::new,
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

            FeywildMod.LOGGER.log(Level.DEBUG, MESSAGE_LOCATION +
                    this.pieces.get(0).getBoundingBox().x0 + " " +
                    this.pieces.get(0).getBoundingBox().y0 + " " +
                    this.pieces.get(0).getBoundingBox().z0);
        }
    }

}




