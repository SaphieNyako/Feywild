package com.feywild.feywild.entity;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.base.BotaniaPixie;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.moddingx.libx.annotation.registration.RegisterClass;

@RegisterClass(registry = "ENTITY_TYPE_REGISTRY")
public class ModEntities {

    public static final EntityType<BeeKnight> beeKnight = EntityType.Builder.of(BeeKnight::new, MobCategory.CREATURE)
            .sized(0.7f, 1)
            .build(FeywildMod.getInstance().modid + "_bee_knight");

    public static final EntityType<Mandragora> mandragora = EntityType.Builder.of(Mandragora::new, MobCategory.CREATURE)
            .sized(0.7f, 1)
            .build(FeywildMod.getInstance().modid + "_mandragora");

    public static final EntityType<Shroomling> shroomling = EntityType.Builder.of(Shroomling::new, MobCategory.CREATURE)
            .sized(0.7f, 0.8f)
            .build(FeywildMod.getInstance().modid + "_shroomling");

    public static final EntityType<DwarfBlacksmith> dwarfBlacksmith = EntityType.Builder.of(DwarfBlacksmith::new, MobCategory.MONSTER)
            .sized(1, 2)
            .build(FeywildMod.getInstance().modid + "_dwarf_blacksmith");

    public static final EntityType<MarketDwarf> dwarfToolsmith = EntityType.Builder.of(MarketDwarf::new, MobCategory.MONSTER)
            .sized(1, 1)
            .build(FeywildMod.getInstance().modid + "_dwarf_toolsmith");

    public static final EntityType<MarketDwarf> dwarfArtificer = EntityType.Builder.of(MarketDwarf::new, MobCategory.CREATURE)
            .sized(1, 1)
            .build(FeywildMod.getInstance().modid + "_dwarf_artificer");

    public static final EntityType<MarketDwarf> dwarfBaker = EntityType.Builder.of(MarketDwarf::new, MobCategory.CREATURE)
            .sized(1, 1)
            .build(FeywildMod.getInstance().modid + "_dwarf_baker");

    public static final EntityType<MarketDwarf> dwarfShepherd = EntityType.Builder.of(MarketDwarf::new, MobCategory.CREATURE)
            .sized(1, 1)
            .build(FeywildMod.getInstance().modid + "_dwarf_shepherd");

    public static final EntityType<MarketDwarf> dwarfDragonHunter = EntityType.Builder.of(MarketDwarf::new, MobCategory.CREATURE)
            .sized(1, 1)
            .build(FeywildMod.getInstance().modid + "_dwarf_dragon_hunter");

    public static final EntityType<MarketDwarf> dwarfMiner = EntityType.Builder.of(MarketDwarf::new, MobCategory.CREATURE)
            .sized(1, 1)
            .build(FeywildMod.getInstance().modid + "_dwarf_miner");

    public static final EntityType<SpringPixie> springPixie = EntityType.Builder.of(SpringPixie::new, MobCategory.CREATURE)
            .sized(0.7f, 1)
            .build(FeywildMod.getInstance().modid + "_spring_pixie");

    public static final EntityType<SummerPixie> summerPixie = EntityType.Builder.of(SummerPixie::new, MobCategory.CREATURE)
            .sized(0.7f, 1)
            .build(FeywildMod.getInstance().modid + "_summer_pixie");

    public static final EntityType<AutumnPixie> autumnPixie = EntityType.Builder.of(AutumnPixie::new, MobCategory.CREATURE)
            .sized(0.7f, 1)
            .build(FeywildMod.getInstance().modid + "_autumn_pixie");

    public static final EntityType<WinterPixie> winterPixie = EntityType.Builder.of(WinterPixie::new, MobCategory.CREATURE)
            .sized(0.7f, 1)
            .build(FeywildMod.getInstance().modid + "_winter_pixie");

    public static final EntityType<BotaniaPixie> botaniaPixie = EntityType.Builder.of(BotaniaPixie::new, MobCategory.CREATURE)
            .sized(0.25f, 0.25f)
            .build(FeywildMod.getInstance().modid + "_botania_pixie");

    public static final EntityType<SpringTreeEnt> springTreeEnt = EntityType.Builder.of(SpringTreeEnt::new, MobCategory.CREATURE)
            .sized(1.0f, 5.0f)
            .build(FeywildMod.getInstance().modid + "_spring_tree_ent");

    public static final EntityType<SummerTreeEnt> summerTreeEnt = EntityType.Builder.of(SummerTreeEnt::new, MobCategory.CREATURE)
            .sized(1.0f, 5.0f)
            .build(FeywildMod.getInstance().modid + "_summer_tree_ent");

    public static final EntityType<WinterTreeEnt> winterTreeEnt = EntityType.Builder.of(WinterTreeEnt::new, MobCategory.CREATURE)
            .sized(1.0f, 5.0f)
            .build(FeywildMod.getInstance().modid + "_winter_tree_ent");

    public static final EntityType<AutumnTreeEnt> autumnTreeEnt = EntityType.Builder.of(AutumnTreeEnt::new, MobCategory.CREATURE)
            .sized(1.0f, 5.0f)
            .build(FeywildMod.getInstance().modid + "_autumn_tree_ent");

    public static final EntityType<BlossomTreeEnt> blossomTreeEnt = EntityType.Builder.of(BlossomTreeEnt::new, MobCategory.CREATURE)
            .sized(1.0f, 5.0f)
            .build(FeywildMod.getInstance().modid + "_blossom_tree_ent");

    public static final EntityType<HexenTreeEnt> hexenTreeEnt = EntityType.Builder.of(HexenTreeEnt::new, MobCategory.CREATURE)
            .sized(1.0f, 5.0f)
            .build(FeywildMod.getInstance().modid + "_hexen_tree_ent");

    public static final EntityType<Titania> titania = EntityType.Builder.of(Titania::new, MobCategory.MONSTER)
            .sized(1.0f, 4.0f)
            .build(FeywildMod.getInstance().modid + "_titania");

    public static final EntityType<Mab> mab = EntityType.Builder.of(Mab::new, MobCategory.MONSTER)
            .sized(1.0f, 4.0f)
            .build(FeywildMod.getInstance().modid + "_mab");


}
