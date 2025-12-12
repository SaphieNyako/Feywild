package com.saphienyako.feywild.recipe;

import com.saphienyako.feywild.Feywild;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, Feywild.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, Feywild.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<FeyAltarRecipe>> FEY_ALTAR_SERIALIZER =
            SERIALIZERS.register("fey_altar", FeyAltarRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<FeyAltarRecipe>> FEY_ALTAR_TYPE =
            TYPES.register("fey_altar", () -> new RecipeType<FeyAltarRecipe>() {
                @Override
                public String toString() {
                    return "fey_altar";
                }
            });

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }



}
