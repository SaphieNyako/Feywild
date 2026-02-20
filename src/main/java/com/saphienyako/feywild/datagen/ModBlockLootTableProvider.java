package com.saphienyako.feywild.datagen;

import com.saphienyako.feywild.block.MandrakeCropBlock;
import com.saphienyako.feywild.block.ModBlocks;
import com.saphienyako.feywild.item.ModItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.predicates.*;

import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider provider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
    }

    @Override
    protected void generate() {
        this.add(ModBlocks.FEY_GEM_ORE.get(),
                block -> createMultipleOreDrops(ModBlocks.FEY_GEM_ORE.get(), ModItems.FEY_GEM.get(), 1, 3));

        this.add(ModBlocks.FEY_GEM_ORE_DEEP_SLATE.get(),
                block -> createMultipleOreDrops(ModBlocks.FEY_GEM_ORE_DEEP_SLATE.get(), ModItems.FEY_GEM.get(), 1, 3));

        LootItemCondition.Builder lootItemConditionBuilder = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.MANDRAKE_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(MandrakeCropBlock.AGE, 7));
        this.add(ModBlocks.MANDRAKE_CROP.get(), this.createCropDrops(ModBlocks.MANDRAKE_CROP.get(),
                ModItems.MANDRAKE.get(), ModItems.MANDRAKE_ROOT.asItem(), lootItemConditionBuilder));

        dropSelf(ModBlocks.ORANGE_MUSHROOM.get());
        dropSelf(ModBlocks.YELLOW_MUSHROOM.get());
        dropSelf(ModBlocks.GREEN_MUSHROOM.get());
        dropSelf(ModBlocks.LIGHT_BLUE_MUSHROOM.get());
        dropSelf(ModBlocks.BLUE_MUSHROOM.get());
        dropSelf(ModBlocks.PURPLE_MUSHROOM.get());
        dropSelf(ModBlocks.PINK_MUSHROOM.get());

        this.add(ModBlocks.ORANGE_MUSHROOM_BLOCK.get(),
                block -> createMushroomDrops(block, ModBlocks.ORANGE_MUSHROOM.asItem(), -6, 2));
        this.add(ModBlocks.YELLOW_MUSHROOM_BLOCK.get(),
                block -> createMushroomDrops(block, ModBlocks.YELLOW_MUSHROOM.asItem(), -6, 2));
        this.add(ModBlocks.GREEN_MUSHROOM_BLOCK.get(),
                block -> createMushroomDrops(block, ModBlocks.GREEN_MUSHROOM.asItem(), -6, 2));
        this.add(ModBlocks.LIGHT_BLUE_MUSHROOM_BLOCK.get(),
                block -> createMushroomDrops(block, ModBlocks.LIGHT_BLUE_MUSHROOM.asItem(), -6, 2));
        this.add(ModBlocks.BLUE_MUSHROOM_BLOCK.get(),
                block -> createMushroomDrops(block, ModBlocks.BLUE_MUSHROOM.asItem(), -6, 2));
        this.add(ModBlocks.PURPLE_MUSHROOM_BLOCK.get(),
                block -> createMushroomDrops(block, ModBlocks.PURPLE_MUSHROOM.asItem(), -6, 2));
        this.add(ModBlocks.PINK_MUSHROOM_BLOCK.get(),
                block -> createMushroomDrops(block, ModBlocks.PINK_MUSHROOM.asItem(), -6, 2));

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

        this.dropSelf(ModBlocks.AUTUMN_TREE_LOG.get());
        this.dropSelf(ModBlocks.AUTUMN_TREE_WOOD.get());
        this.dropSelf(ModBlocks.AUTUMN_TREE_STRIPPED_LOG.get());
        this.dropSelf(ModBlocks.AUTUMN_TREE_STRIPPED_WOOD.get());

        this.dropSelf(ModBlocks.AUTUMN_TREE_PLANKS.get());
        this.dropSelf(ModBlocks.AUTUMN_TREE_SAPLING.get());

        this.add(ModBlocks.AUTUMN_TREE_LEAVES.get(), block ->
                createLeavesDrops(block, ModBlocks.AUTUMN_TREE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        //TODO custumn drops

    }

    protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(pBlock, this.applyExplosionDecay(pBlock,
                LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
    }

    protected LootTable.Builder createMushroomDrops(Block mushroomBlock, Item mushroomItem, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registryLookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(mushroomBlock, this.applyExplosionDecay(mushroomBlock,
                LootItem.lootTableItem(mushroomItem)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))));
    }

    private static final Set<Block> EXCLUDED_BLOCKS = Set.of(
            ModBlocks.GIANT_SUN_FLOWER.get(),
            ModBlocks.GIANT_CROCUS_FLOWER.get(),
            ModBlocks.GIANT_DANDELION_FLOWER.get(),
            ModBlocks.FEY_ALTAR.get()
    );

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream()
                .map(Holder::value)
                .filter(block -> !EXCLUDED_BLOCKS.contains(block))
                ::iterator;
    }
}