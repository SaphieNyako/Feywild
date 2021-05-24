package com.feywild.feywild.block;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.entity.DwarvenAnvilEntity;
import com.feywild.feywild.block.entity.FeyAltarBlockEntity;
import com.feywild.feywild.block.trees.*;
import com.feywild.feywild.util.Registration;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static final RegistryObject<Block> FEY_GEM_BLOCK = register("fey_gem_block",
            () -> new Block(AbstractBlock.Properties.of(Material.STONE)
                    .strength(3f, 10f)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.STONE)), true);


    /* TILE ENTITIES */

    public static final RegistryObject<Block> DWARVEN_ANVIL = register("dwarven_anvil", DwarvenAnvil::new, true);

    public static final RegistryObject<TileEntityType<DwarvenAnvilEntity>> DWARVEN_ANVIL_ENTITY = registerTile("dwarven_anvil_entity",
            () -> TileEntityType.Builder.of(DwarvenAnvilEntity::new, DWARVEN_ANVIL.get()).build(null));

    public static final RegistryObject<Block> FEY_ALTAR = register("fey_altar", FeyAltar::new, true);

    public static final RegistryObject<TileEntityType<FeyAltarBlockEntity>> FEY_ALTAR_ENTITY = registerTile("fey_altar_entity",
            () -> TileEntityType.Builder.of(FeyAltarBlockEntity::new, FEY_ALTAR.get()).build(null));

    /* CROPS */

    public static final RegistryObject<Block> MANDRAKE_CROP =
            Registration.BLOCKS.register("mandrake_crop",
                    () -> new MandrakeCrop(AbstractBlock.Properties.copy(Blocks.WHEAT)));

    /* TREES */

    public static final RegistryObject<Block> SPRING_TREE_LOG = register("spring_tree_log",
            () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.JUNGLE_WOOD)), true);

    public static final RegistryObject<Block> SPRING_TREE_WOOD = register("spring_tree_wood",
            () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.SPRUCE_WOOD)), true);


    public static final RegistryObject<Block> SPRING_TREE_LEAVES = register("spring_tree_leaves",
            FeyLeavesBlock::new, true);


    public static final RegistryObject<Block> SPRING_TREE_SAPLING = register("spring_tree_sapling",
            SpringTreeSapling::new, true);

    public static final RegistryObject<Block> SUMMER_TREE_LOG = register("summer_tree_log",
            SummerTreeLog::new, true);

    public static final RegistryObject<Block> SUMMER_TREE_WOOD = register("summer_tree_wood",
            () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.SPRUCE_WOOD)), true);


    public static final RegistryObject<Block> SUMMER_TREE_LEAVES = register("summer_tree_leaves",
            FeyLeavesBlock::new, true);


    public static final RegistryObject<Block> SUMMER_TREE_SAPLING = register("summer_tree_sapling",
            SummerTreeSapling::new, true);

    public static final RegistryObject<Block> AUTUMN_TREE_LOG = register("autumn_tree_log",
            AutumnTreeLog::new, true);

    public static final RegistryObject<Block> AUTUMN_TREE_WOOD = register("autumn_tree_wood",
            () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.SPRUCE_WOOD)), true);

    public static final RegistryObject<Block> AUTUMN_TREE_LEAVES = register("autumn_tree_leaves",
            AutumnLeavesBlock::new, true);


    public static final RegistryObject<Block> AUTUMN_TREE_SAPLING = register("autumn_tree_sapling",
            AutumnTreeSapling::new, true);

    public static final RegistryObject<Block> WINTER_TREE_LOG = register("winter_tree_log",
            () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.JUNGLE_WOOD)), true);

    public static final RegistryObject<Block> WINTER_TREE_WOOD = register("winter_tree_wood",
            () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.JUNGLE_WOOD)), true);

    public static final RegistryObject<Block> WINTER_TREE_LEAVES = register("winter_tree_leaves",
            WinterLeavesBlock::new, true);


    public static final RegistryObject<Block> WINTER_TREE_SAPLING = register("winter_tree_sapling",
            WinterTreeSapling::new, true);

    public static final RegistryObject<Block> TREE_MUSHROOM_BLOCK = register("tree_mushroom",
            TreeMushroomBlock::new, true);


    public static void register() {}

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block, boolean createItem) {

        RegistryObject<T> toReturn = Registration.BLOCKS.register(name, block);

        if (createItem)
            Registration.ITEMS.register(name, () -> new BlockItem(toReturn.get(),
                    new Item.Properties().tab(FeywildMod.FEYWILD_TAB)));

        return toReturn;
    }

    private static <T extends TileEntityType<?>> RegistryObject<T> registerTile(String name, Supplier<T> type) {
        return Registration.TILE_ENTITY_TYPES.register(name, type);
    }

}
