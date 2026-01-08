package com.saphienyako.feywild.recipe;

import com.saphienyako.feywild.Feywild;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Feywild.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModRecipes {

    public static final DeferredRegister<IRecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Feywild.MOD_ID);

    public static final RegistryObject<IRecipeSerializer<FeyAltarRecipe>> FEY_ALTAR_SERIALIZER =
            SERIALIZERS.register("fey_altar", FeyAltarRecipe.Serializer::new);

    public static final IRecipeType<FeyAltarRecipe> FEY_ALTAR_TYPE = FeyAltarRecipe.Type.INSTANCE;


    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
