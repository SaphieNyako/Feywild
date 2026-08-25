package com.saphienyako.feywild.compat;

import com.saphienyako.feywild.item.PixieWingTiaraItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;


public class ModCompat {

    public static final boolean CURIOS_LOADED = ModList.get().isLoaded("curios");
    public static final boolean QUEST_GIVER_LOADED = ModList.get().isLoaded("quest_giver");
    public static  final boolean PATCHOULI_LOADED = ModList.get().isLoaded("patchouli");

    private ModCompat() {
    }

    public static ItemStack findWingTiara(LivingEntity entity) {
        if (CURIOS_LOADED) {
            return CuriosCompat.findWingTiara(entity);
        }

        ItemStack offhandStack = entity.getOffhandItem();

        if (offhandStack.getItem() instanceof PixieWingTiaraItem) {
            return offhandStack;
        }

        return ItemStack.EMPTY;
    }
}


