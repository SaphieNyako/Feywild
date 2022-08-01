package com.feywild.feywild.recipes;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.ForgeRegistries;
import org.moddingx.libx.registration.Registerable;
import org.moddingx.libx.registration.RegistrationContext;
import org.moddingx.libx.util.Misc;

import java.util.Objects;

public record FeywildRecipeType<T extends Recipe<?>, I extends T>(RecipeSerializer<I> serializer) implements RecipeType<T>, Registerable {
    
    @Override
    public void registerAdditional(RegistrationContext ctx, EntryCollector builder) {
        builder.register(Registry.RECIPE_SERIALIZER_REGISTRY, this.serializer);
    }

    public ResourceLocation id() {
        return Objects.requireNonNull(ForgeRegistries.RECIPE_TYPES.getKey(this), "Recipe type not registered");
    }
    
    @Override
    public String toString() {
        ResourceLocation id = ForgeRegistries.RECIPE_TYPES.getKey(this);
        return Objects.requireNonNullElse(id, Misc.MISSIGNO).toString();
    }
}
