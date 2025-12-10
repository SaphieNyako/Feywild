package com.saphienyako.feywild.datagen;

import com.saphienyako.feywild.block.MandrakeCropBlock;
import com.saphienyako.feywild.block.ModBlocks;
import com.saphienyako.feywild.item.ModItems;
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
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;

import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
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
    }

    protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(pBlock, this.applyExplosionDecay(pBlock,
                LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));

    }

    private static final Set<Block> EXCLUDED_BLOCKS = Set.of(
            ModBlocks.GIANT_SUN_FLOWER.get(),
            ModBlocks.GIANT_CROCUS_FLOWER.get(),
            ModBlocks.GIANT_DANDELION_FLOWER.get()
    );

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream()
                .map(Holder::value)
                .filter(block -> !EXCLUDED_BLOCKS.contains(block))
                ::iterator;
    }
}