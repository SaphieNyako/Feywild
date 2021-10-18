package com.feywild.feywild.entity;

import com.feywild.feywild.FeywildMod;
import io.github.noeppi_noeppi.libx.annotation.RegisterClass;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;

@RegisterClass
public class ModEntityTypes {

    public static final EntityType<BeeKnight> beeKnight = EntityType.Builder.of(BeeKnight::new, EntityClassification.CREATURE)
            .sized(0.7f, 1)
            .build(FeywildMod.getInstance().modid + "_bee_knight");

    public static final EntityType<MelonMandragoraEntity> melonMandragora = EntityType.Builder.of(MelonMandragoraEntity::new, EntityClassification.CREATURE)
            .sized(0.7f, 1)
            .build(FeywildMod.getInstance().modid + "melon_mandragora");

    public static final EntityType<OnionMandragoraEntity> onionMandragora = EntityType.Builder.of(OnionMandragoraEntity::new, EntityClassification.CREATURE)
            .sized(0.7f, 1)
            .build(FeywildMod.getInstance().modid + "onion_mandragora");

    public static final EntityType<PotatoMandragoraEntity> potatoMandragora = EntityType.Builder.of(PotatoMandragoraEntity::new, EntityClassification.CREATURE)
            .sized(0.7f, 1)
            .build(FeywildMod.getInstance().modid + "potato_mandragora");

    public static final EntityType<PumpkinMandragoraEntity> pumpkinMandragora = EntityType.Builder.of(PumpkinMandragoraEntity::new, EntityClassification.CREATURE)
            .sized(0.7f, 1)
            .build(FeywildMod.getInstance().modid + "pumpkin_mandragora");

    public static final EntityType<TomatoMandragoraEntity> tomatoMandragora = EntityType.Builder.of(TomatoMandragoraEntity::new, EntityClassification.CREATURE)
            .sized(0.7f, 1)
            .build(FeywildMod.getInstance().modid + "potato_mandragora");

    public static final EntityType<DwarfBlacksmithEntity> dwarfBlacksmith = EntityType.Builder.of(DwarfBlacksmithEntity::new, EntityClassification.MONSTER)
            .sized(1, 1)
            .build(FeywildMod.getInstance().modid + "_dwarf_blacksmith");

    public static final EntityType<MarketDwarfEntity> dwarfToolsmith = EntityType.Builder.of(MarketDwarfEntity::new, EntityClassification.MONSTER)
            .sized(1, 1)
            .build(FeywildMod.getInstance().modid + "_dwarf_toolsmith");

    public static final EntityType<MarketDwarfEntity> dwarfArtificer = EntityType.Builder.of(MarketDwarfEntity::new, EntityClassification.CREATURE)
            .sized(1, 1)
            .build(FeywildMod.getInstance().modid + "_dwarf_artificer");

    public static final EntityType<MarketDwarfEntity> dwarfBaker = EntityType.Builder.of(MarketDwarfEntity::new, EntityClassification.CREATURE)
            .sized(1, 1)
            .build(FeywildMod.getInstance().modid + "_dwarf_baker");

    public static final EntityType<MarketDwarfEntity> dwarfShepherd = EntityType.Builder.of(MarketDwarfEntity::new, EntityClassification.CREATURE)
            .sized(1, 1)
            .build(FeywildMod.getInstance().modid + "_dwarf_shepherd");

    public static final EntityType<MarketDwarfEntity> dwarfDragonHunter = EntityType.Builder.of(MarketDwarfEntity::new, EntityClassification.CREATURE)
            .sized(1, 1)
            .build(FeywildMod.getInstance().modid + "_dwarf_dragon_hunter");

    public static final EntityType<MarketDwarfEntity> dwarfMiner = EntityType.Builder.of(MarketDwarfEntity::new, EntityClassification.CREATURE)
            .sized(1, 1)
            .build(FeywildMod.getInstance().modid + "_dwarf_miner");

    public static final EntityType<SpringPixieEntity> springPixie = EntityType.Builder.of(SpringPixieEntity::new, EntityClassification.CREATURE)
            .sized(0.7f, 1)
            .build(FeywildMod.getInstance().modid + "_spring_pixie");

    public static final EntityType<SummerPixieEntity> summerPixie = EntityType.Builder.of(SummerPixieEntity::new, EntityClassification.CREATURE)
            .sized(0.7f, 1)
            .build(FeywildMod.getInstance().modid + "_summer_pixie");

    public static final EntityType<AutumnPixieEntity> autumnPixie = EntityType.Builder.of(AutumnPixieEntity::new, EntityClassification.CREATURE)
            .sized(0.7f, 1)
            .build(FeywildMod.getInstance().modid + "_autumn_pixie");

    public static final EntityType<WinterPixieEntity> winterPixie = EntityType.Builder.of(WinterPixieEntity::new, EntityClassification.CREATURE)
            .sized(0.7f, 1)
            .build(FeywildMod.getInstance().modid + "_winter_pixie");
}
