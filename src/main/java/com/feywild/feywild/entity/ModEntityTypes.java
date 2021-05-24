package com.feywild.feywild.entity;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.util.Registration;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.fml.RegistryObject;

import java.util.Random;

public class ModEntityTypes {

    public static final RegistryObject<EntityType<SpringPixieEntity>> SPRING_PIXIE = Registration.ENTITIES.register("spring_pixie",
            () -> EntityType.Builder.<SpringPixieEntity>of(SpringPixieEntity::new, EntityClassification.CREATURE)
                    .sized(1, 1)
                    .build(new ResourceLocation(FeywildMod.MOD_ID + "spring_pixie").toString()));

    public static final RegistryObject<EntityType<AutumnPixieEntity>> AUTUMN_PIXIE = Registration.ENTITIES.register("autumn_pixie",
            () -> EntityType.Builder.<AutumnPixieEntity>of(AutumnPixieEntity::new, EntityClassification.CREATURE)
                    .sized(1, 1)
                    .build(new ResourceLocation(FeywildMod.MOD_ID + "autumn_pixie").toString()));

    public static final RegistryObject<EntityType<SummerPixieEntity>> SUMMER_PIXIE = Registration.ENTITIES.register("summer_pixie",
            () -> EntityType.Builder.<SummerPixieEntity>of(SummerPixieEntity::new, EntityClassification.CREATURE)
                    .sized(1, 1)
                    .build(new ResourceLocation(FeywildMod.MOD_ID + "summer_pixie").toString()));

    public static final RegistryObject<EntityType<WinterPixieEntity>> WINTER_PIXIE = Registration.ENTITIES.register("winter_pixie",
            () -> EntityType.Builder.<WinterPixieEntity>of(WinterPixieEntity::new, EntityClassification.CREATURE)
                    .sized(1, 1)
                    .build(new ResourceLocation(FeywildMod.MOD_ID + "winter_pixie").toString()));

    public static final RegistryObject<EntityType<DwarfBlacksmithEntity>> DWARF_BLACKSMITH = Registration.ENTITIES.register("dwarf_blacksmith",
            () -> EntityType.Builder.<DwarfBlacksmithEntity>of(DwarfBlacksmithEntity::new, EntityClassification.CREATURE)
                    .sized(1, 1)
                    .build(new ResourceLocation(FeywildMod.MOD_ID + "dwarf_blacksmith").toString()));

    public static void register() {}

    /* CUSTOM CONDITIONS */
    public static boolean spawnFey(EntityType<? extends FeyEntity> entity, IWorld worldIn, SpawnReason reason, BlockPos pos, Random random) {

        return worldIn.getBlockState(pos.below()).is(Blocks.DIRT) ||
                worldIn.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK) ||
                worldIn.getBlockState(pos.below()).is(Blocks.SAND);
    }

    public static boolean spawnDwarf(EntityType<? extends DwarfBlacksmithEntity> entity, IWorld worldIn, SpawnReason reason, BlockPos pos, Random random) {
        return !worldIn.canSeeSky(pos) && pos.getY() < worldIn.getSeaLevel() - 10 && random.nextDouble() > 0.85;
    }

}
