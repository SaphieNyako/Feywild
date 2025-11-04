package com.saphienyako.feywild.recipe;

import com.saphienyako.feywild.Feywild;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Feywild.MOD_ID);

    public static final RegistryObject<RecipeSerializer<FeyAltarRecipe>> FEY_ALTAR_SERIALIZER =
            SERIALIZERS.register("fey_altar", () -> FeyAltarRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }

}
