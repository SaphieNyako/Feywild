package com.feywild.feywild.world.structure.structures;

import com.feywild.feywild.FeywildMod;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;
import org.apache.logging.log4j.Level;

import java.util.List;

public class SpringWorldTreeStructure extends Structure<NoFeatureConfig> {

    public final static int AVERAGE_DISTANCE_BETWEEN_CHUNKS = 10;
    public final static int MIN_DISTANCE_BETWEEN_CHUNKS = 10;
    public final static int SEED_MODIFIER =  1234567890;
    /* this modifies the seed of the structure so no two structures always spawn over each-other. Make this large and unique. */

    public SpringWorldTreeStructure() {
        super(NoFeatureConfig.field_236558_a_);
    }

    @Override
    public IStartFactory<NoFeatureConfig> getStartFactory() {

        return SpringWorldTreeStructure.Start::new;
    }

    @Override
    public GenerationStage.Decoration getDecorationStage() {

        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }




    //Mob Spawn in Structure
    private static final List<MobSpawnInfo.Spawners> STRUCTURE_MONSTERS = ImmutableList.of(
            new MobSpawnInfo.Spawners(EntityType.ILLUSIONER, 100, 4, 9),
            new MobSpawnInfo.Spawners(EntityType.VINDICATOR, 100, 4, 9)
    );

    @Override
    public List<MobSpawnInfo.Spawners> getDefaultSpawnList() {

        return STRUCTURE_MONSTERS;
    }

    private static final List<MobSpawnInfo.Spawners> STRUCTURE_CREATURES = ImmutableList.of(
            new MobSpawnInfo.Spawners(EntityType.SHEEP, 30, 10, 15),
            new MobSpawnInfo.Spawners(EntityType.RABBIT, 100, 1, 2)
    );

    @Override
    public List<MobSpawnInfo.Spawners> getDefaultCreatureSpawnList() {

        return STRUCTURE_CREATURES;
    }


    //OPTIONAL
    @Override
    protected boolean func_230363_a_(ChunkGenerator chunkGenerator, BiomeProvider biomeSource, long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, NoFeatureConfig featureConfig) {
        BlockPos centerOfChunk = new BlockPos((chunkX << 4) + 7, 0, (chunkZ << 4) + 7);

        // getFirstOccupiedHeight();
        int landHeight = chunkGenerator.getNoiseHeight(centerOfChunk.getX(), centerOfChunk.getZ(), Heightmap.Type.WORLD_SURFACE_WG);

        //getBaseColumn();
        IBlockReader columnOfBlocks = chunkGenerator.func_230348_a_(centerOfChunk.getX(), centerOfChunk.getZ());

        BlockState topBlock = columnOfBlocks.getBlockState(centerOfChunk.up(landHeight));

        return topBlock.getFluidState().isEmpty(); //landHeight > 100;
    }




    //START CLASS
    public static class Start extends StructureStart<NoFeatureConfig> {
        public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
        }


        @Override  //generatePieces
        public void func_230364_a_(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn, NoFeatureConfig config) {

            // Turns the chunk coordinates into actual coordinates we can use. (Gets center of that chunk)
            int x = (chunkX << 4) + 7;
            int z = (chunkZ << 4) + 7;

            BlockPos blockpos = new BlockPos(x, 0, z);

            //addpieces()
            JigsawManager.func_242837_a(
                    dynamicRegistryManager,

                    new VillageConfig(() -> (JigsawPattern) dynamicRegistryManager.getRegistry(Registry.STRUCTURE_POOL_ELEMENT_KEY)
                            .getOrDefault(new ResourceLocation(FeywildMod.MOD_ID, "spring_world_tree/start_pool")),
                            10),

                    AbstractVillagePiece::new,
                    chunkGenerator,
                    templateManagerIn,
                    blockpos,
                    this.components,
                    this.rand,
                    false,
                    true);
            // Keep this false when placing structures in the nether as otherwise, heightmap placing will put the structure on the Bedrock roof.

            //OPTIONAL
            this.components.forEach(piece -> piece.offset(0,1,0));
            this.components.forEach(piece -> piece.getBoundingBox().maxY -= 1);

            // Sets the bounds of the structure once you are finished. // calculateBoundingBox();
            this.recalculateStructureSize();

           FeywildMod.LOGGER.log(Level.DEBUG, "Spring World Tree at: " +
                    this.components.get(0).getBoundingBox().maxX + " " +
                    this.components.get(0).getBoundingBox().maxY + " " +
                    this.components.get(0).getBoundingBox().maxZ);
        }
    }
}