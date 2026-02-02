package com.saphienyako.feywild.datagen.loot;

import com.saphienyako.feywild.block.ModBlocks;
import com.saphienyako.feywild.item.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.FEY_GEM_ORE.get());
        this.dropSelf(ModBlocks.FEY_GEM_ORE_DEEP_SLATE.get());
        this.dropSelf(ModBlocks.FEY_ALTAR.get());

        this.add(ModBlocks.FEY_GEM_ORE.get(),
                block -> createOreDrops(ModBlocks.FEY_GEM_ORE.get(), ModItems.FEY_GEM.get()));
        this.add(ModBlocks.FEY_GEM_ORE_DEEP_SLATE.get(),
                block -> createOreDrops(ModBlocks.FEY_GEM_ORE_DEEP_SLATE.get(), ModItems.FEY_GEM.get()));

        this.add(ModBlocks.GIANT_CROCUS_FLOWER.get(),
                block -> createSingleItemTable(ModItems.GIANT_CROCUS_FLOWER_SEED.get()));
        this.add(ModBlocks.GIANT_DANDELION_FLOWER.get(),
                block -> createSingleItemTable(ModItems.GIANT_DANDELION_FLOWER_SEED.get()));
        this.add(ModBlocks.GIANT_SUN_FLOWER.get(),
                block -> createSingleItemTable(ModItems.GIANT_SUN_FLOWER_SEED.get()));

       // this.add(ModBlocks.MANDRAKE_CROP.get(), block -> createSingleItemTable(ModItems.MANDRAKE.get()));

        this.add(ModBlocks.MANDRAKE_CROP.get(), block -> createCropDrops(block, ModItems.MANDRAKE.get(), ModItems.MANDRAKE_ROOT.get(),
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                .hasProperty(CropBlock.AGE, CropBlock.MAX_AGE))));

        dropSelf(ModBlocks.ORANGE_MUSHROOM.get());
        dropSelf(ModBlocks.YELLOW_MUSHROOM.get());
        dropSelf(ModBlocks.GREEN_MUSHROOM.get());
        dropSelf(ModBlocks.LIGHT_BLUE_MUSHROOM.get());
        dropSelf(ModBlocks.BLUE_MUSHROOM.get());
        dropSelf(ModBlocks.PURPLE_MUSHROOM.get());
        dropSelf(ModBlocks.PINK_MUSHROOM.get());

        this.add(ModBlocks.ORANGE_MUSHROOM_BLOCK.get(),
                block -> createMushroomDrops(block, ModBlocks.ORANGE_MUSHROOM.get().asItem(), -6, 2));
        this.add(ModBlocks.YELLOW_MUSHROOM_BLOCK.get(),
                block -> createMushroomDrops(block, ModBlocks.YELLOW_MUSHROOM.get().asItem(), -6, 2));
        this.add(ModBlocks.GREEN_MUSHROOM_BLOCK.get(),
                block -> createMushroomDrops(block, ModBlocks.GREEN_MUSHROOM.get().asItem(), -6, 2));
        this.add(ModBlocks.LIGHT_BLUE_MUSHROOM_BLOCK.get(),
                block -> createMushroomDrops(block, ModBlocks.LIGHT_BLUE_MUSHROOM.get().asItem(), -6, 2));
        this.add(ModBlocks.BLUE_MUSHROOM_BLOCK.get(),
                block -> createMushroomDrops(block, ModBlocks.BLUE_MUSHROOM.get().asItem(), -6, 2));
        this.add(ModBlocks.PURPLE_MUSHROOM_BLOCK.get(),
                block -> createMushroomDrops(block, ModBlocks.PURPLE_MUSHROOM.get().asItem(), -6, 2));
        this.add(ModBlocks.PINK_MUSHROOM_BLOCK.get(),
                block -> createMushroomDrops(block, ModBlocks.PINK_MUSHROOM.get().asItem(), -6, 2));

        dropSelf(ModBlocks.ELVEN_QUARTZ_BLOCK.get());
        dropSelf(ModBlocks.ELVEN_QUARTZ_STAIRS.get());
        this.add(ModBlocks.ELVEN_QUARTZ_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.ELVEN_QUARTZ_SLAB.get()));
        dropSelf(ModBlocks.ELVEN_QUARTZ_BRICK.get());
        dropSelf(ModBlocks.ELVEN_QUARTZ_BRICK_STAIRS.get());
        this.add(ModBlocks.ELVEN_QUARTZ_BRICK_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.ELVEN_QUARTZ_BRICK_SLAB.get()));
        dropSelf(ModBlocks.ELVEN_QUARTZ_MOSSY_BRICK.get());
        dropSelf(ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK.get());
        dropSelf(ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get());
        this.add(ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get()));
        dropSelf(ModBlocks.ELVEN_QUARTZ_PILLAR.get());
        dropSelf(ModBlocks.ELVEN_QUARTZ_POLISHED.get());
        dropSelf(ModBlocks.ELVEN_QUARTZ_POLISHED_STAIRS.get());
        this.add(ModBlocks.ELVEN_QUARTZ_POLISHED_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.ELVEN_QUARTZ_POLISHED_SLAB.get()));

        dropSelf(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get());
        dropSelf(ModBlocks.SPRING_ELVEN_QUARTZ_STAIRS.get());
        this.add(ModBlocks.SPRING_ELVEN_QUARTZ_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.SPRING_ELVEN_QUARTZ_SLAB.get()));
        dropSelf(ModBlocks.SPRING_ELVEN_QUARTZ_BRICK.get());
        dropSelf(ModBlocks.SPRING_ELVEN_QUARTZ_BRICK_STAIRS.get());
        this.add(ModBlocks.SPRING_ELVEN_QUARTZ_BRICK_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.SPRING_ELVEN_QUARTZ_BRICK_SLAB.get()));
        dropSelf(ModBlocks.SPRING_ELVEN_QUARTZ_MOSSY_BRICK.get());
        dropSelf(ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK.get());
        dropSelf(ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get());
        this.add(ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get()));
        dropSelf(ModBlocks.SPRING_ELVEN_QUARTZ_PILLAR.get());
        dropSelf(ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED.get());
        dropSelf(ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED_STAIRS.get());
        this.add(ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED_SLAB.get()));

        dropSelf(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get());
        dropSelf(ModBlocks.SUMMER_ELVEN_QUARTZ_STAIRS.get());
        this.add(ModBlocks.SUMMER_ELVEN_QUARTZ_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.SUMMER_ELVEN_QUARTZ_SLAB.get()));
        dropSelf(ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK.get());
        dropSelf(ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK_STAIRS.get());
        this.add(ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK_SLAB.get()));
        dropSelf(ModBlocks.SUMMER_ELVEN_QUARTZ_MOSSY_BRICK.get());
        dropSelf(ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK.get());
        dropSelf(ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get());
        this.add(ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get()));
        dropSelf(ModBlocks.SUMMER_ELVEN_QUARTZ_PILLAR.get());
        dropSelf(ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED.get());
        dropSelf(ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED_STAIRS.get());
        this.add(ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED_SLAB.get()));

        dropSelf(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get());
        dropSelf(ModBlocks.WINTER_ELVEN_QUARTZ_STAIRS.get());
        this.add(ModBlocks.WINTER_ELVEN_QUARTZ_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.WINTER_ELVEN_QUARTZ_SLAB.get()));
        dropSelf(ModBlocks.WINTER_ELVEN_QUARTZ_BRICK.get());
        dropSelf(ModBlocks.WINTER_ELVEN_QUARTZ_BRICK_STAIRS.get());
        this.add(ModBlocks.WINTER_ELVEN_QUARTZ_BRICK_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.WINTER_ELVEN_QUARTZ_BRICK_SLAB.get()));
        dropSelf(ModBlocks.WINTER_ELVEN_QUARTZ_MOSSY_BRICK.get());
        dropSelf(ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK.get());
        dropSelf(ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get());
        this.add(ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get()));
        dropSelf(ModBlocks.WINTER_ELVEN_QUARTZ_PILLAR.get());
        dropSelf(ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED.get());
        dropSelf(ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED_STAIRS.get());
        this.add(ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED_SLAB.get()));

        dropSelf(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get());
        dropSelf(ModBlocks.AUTUMN_ELVEN_QUARTZ_STAIRS.get());
        this.add(ModBlocks.AUTUMN_ELVEN_QUARTZ_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.AUTUMN_ELVEN_QUARTZ_SLAB.get()));
        dropSelf(ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK.get());
        dropSelf(ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK_STAIRS.get());
        this.add(ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK_SLAB.get()));
        dropSelf(ModBlocks.AUTUMN_ELVEN_QUARTZ_MOSSY_BRICK.get());
        dropSelf(ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK.get());
        dropSelf(ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get());
        this.add(ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get()));
        dropSelf(ModBlocks.AUTUMN_ELVEN_QUARTZ_PILLAR.get());
        dropSelf(ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED.get());
        dropSelf(ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED_STAIRS.get());
        this.add(ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED_SLAB.get()));


    }

    protected LootTable.Builder createOreDrops(Block pBlock, Item item) {
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    protected LootTable.Builder createMushroomDrops(Block mushroomBlock, Item mushroomItem, float minDrops, float maxDrops) {
        return createSilkTouchDispatchTable(
                mushroomBlock,
                this.applyExplosionDecay(
                        mushroomBlock,
                        LootItem.lootTableItem(mushroomItem)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                )
        );
    }
    @Nonnull
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
