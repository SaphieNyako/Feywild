package com.feywild.feywild.data;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.ModTrees;
import com.feywild.feywild.entity.ModEntityTypes;
import com.feywild.feywild.item.ModItems;
import io.github.noeppi_noeppi.libx.data.provider.AdvancementProviderBase;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.VillagerTradeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.Supplier;

public class Advancements extends AdvancementProviderBase {

    public Advancements(ModX mod, DataGenerator generator) {
        super(mod, generator);
    }

    @Override
    public void setup() {
        this.root()
                .display(ModItems.feywildLexicon)
                .task(items(ModItems.feywildLexicon));
        
        // Don't ask about this
        try {
            ResourceLocation rl = new ResourceLocation(mod.modid, mod.modid + "/root");
            Field mapField = AdvancementProviderBase.class.getDeclaredField("advancements");
            mapField.setAccessible(true);
            //noinspection unchecked
            Map<ResourceLocation, Supplier<Advancement>> map = (Map<ResourceLocation, Supplier<Advancement>>) mapField.get(this);
            Supplier<Advancement> oldRoot = map.get(rl);
            Supplier<Advancement> newRoot = () -> {
                Advancement adv = oldRoot.get();
                if (adv.getDisplay() == null) throw new RuntimeException("Nul display on root");
                DisplayInfo display = new DisplayInfo(
                        adv.getDisplay().getIcon(), adv.getDisplay().getTitle(), adv.getDisplay().getDescription(),
                        new ResourceLocation("minecraft", "textures/gui/advancements/backgrounds/adventure.png"),
                        adv.getDisplay().getFrame(), adv.getDisplay().shouldShowToast(),
                        adv.getDisplay().shouldAnnounceChat(), adv.getDisplay().isHidden()
                );
                return new Advancement(adv.getId(), adv.getParent(), display, adv.getRewards(), adv.getCriteria(), adv.getRequirements());
            };
            map.put(rl, newRoot);
            Field rootSupplierField = AdvancementProviderBase.class.getDeclaredField("rootSupplier");
            rootSupplierField.setAccessible(true);
            rootSupplierField.set(this, newRoot);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
        
        this.advancement("fey_dust")
                .display(ModItems.feyDust)
                .task(items(ModItems.feyDust))
                .task(items(ModItems.lesserFeyGem));
        
        this.advancement("dwarf_trade").parent("fey_dust")
                .display(ModItems.lesserFeyGem)
                .task(new VillagerTradeTrigger.Instance(EntityPredicate.AndPredicate.ANY, entity(ModEntityTypes.dwarfBlacksmith), stack(ModItems.lesserFeyGem)));
        
        this.advancement("dwarf_contract").parent("dwarf_trade")
                .display(ModItems.summoningScrollDwarfBlacksmith)
                .task(items(ModItems.summoningScrollDwarfBlacksmith));
        
        this.advancement("schematics_fey_altar").parent("dwarf_contract")
                .display(ModItems.schematicsFeyAltar)
                .task(items(ModItems.schematicsFeyAltar));
        
        this.advancement("schematics_gem_transmutation").parent("dwarf_contract")
                .display(ModItems.schematicsGemTransmutation)
                .task(items(ModItems.schematicsGemTransmutation));
        
        this.advancement("fey_altar").parent("schematics_fey_altar")
                .display(ModBlocks.feyAltar)
                .task(items(ModBlocks.feyAltar));
        
        this.advancement("fey_sapling")
                .display(ModTrees.springTree.getSapling())
                .task(
                        items(ModTrees.springTree.getSapling()),
                        items(ModTrees.summerTree.getSapling()),
                        items(ModTrees.autumnTree.getSapling()),
                        items(ModTrees.winterTree.getSapling())
                );
        
        this.advancement("summon_spring").parent("fey_altar")
                .display(ModItems.summoningScrollSpringPixie)
                .task(items(ModItems.summoningScrollSpringPixie));

        this.advancement("summon_summer").parent("fey_altar")
                .display(ModItems.summoningScrollSummerPixie)
                .task(items(ModItems.summoningScrollSummerPixie));

        this.advancement("summon_autumn").parent("fey_altar")
                .display(ModItems.summoningScrollAutumnPixie)
                .task(items(ModItems.summoningScrollAutumnPixie));

        this.advancement("summon_winter").parent("fey_altar")
                .display(ModItems.summoningScrollWinterPixie)
                .task(items(ModItems.summoningScrollWinterPixie));
    }
}
