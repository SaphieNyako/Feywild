package com.feywild.feywild.entity;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.util.FeyEntity;
import com.feywild.feywild.util.Registration;
import io.github.noeppi_noeppi.libx.annotation.RegisterClass;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.fml.RegistryObject;

import java.util.Random;

@RegisterClass
public class ModEntityTypes {

    public static final EntityType<DwarfBlacksmithEntity> dwarfBlacksmith = EntityType.Builder.<DwarfBlacksmithEntity>of(DwarfBlacksmithEntity::new, EntityClassification.CREATURE)
            .sized(1, 1)
            .build(FeywildMod.getInstance().modid + "_dwarf_blacksmith");
    
    public static final EntityType<SpringPixieEntity> springPixie = EntityType.Builder.<SpringPixieEntity>of(SpringPixieEntity::new, EntityClassification.CREATURE)
                    .sized(1, 1)
                    .build(FeywildMod.getInstance().modid + "_spring_pixie");

    public static final EntityType<SummerPixieEntity> summerPixie = EntityType.Builder.<SummerPixieEntity>of(SummerPixieEntity::new, EntityClassification.CREATURE)
            .sized(1, 1)
            .build(FeywildMod.getInstance().modid + "_summer_pixie");
    
    public static final EntityType<AutumnPixieEntity> autumnPixie = EntityType.Builder.<AutumnPixieEntity>of(AutumnPixieEntity::new, EntityClassification.CREATURE)
                    .sized(1, 1)
                    .build(FeywildMod.getInstance().modid + "_autumn_pixie");
    
    public static final EntityType<WinterPixieEntity> winterPixie = EntityType.Builder.<WinterPixieEntity>of(WinterPixieEntity::new, EntityClassification.CREATURE)
                    .sized(1, 1)
                    .build(FeywildMod.getInstance().modid + "_winter_pixie");
}
