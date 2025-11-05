package com.saphienyako.feywild.datagen.loot;

import com.saphienyako.feywild.block.ModBlocks;
import com.saphienyako.feywild.item.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

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

        this.add(ModBlocks.MANDRAKE_CROP.get(),
                block -> createSingleItemTable(ModItems.MANDRAKE.get()));


    }

    protected LootTable.Builder createOreDrops(Block pBlock, Item item) {
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
