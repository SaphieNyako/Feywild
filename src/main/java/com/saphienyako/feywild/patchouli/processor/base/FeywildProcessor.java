package com.saphienyako.feywild.patchouli.processor.base;

import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public abstract class FeywildProcessor implements IComponentProcessor {

    @Nullable
    private Recipe<?> recipe;

    @Nullable
    private HolderLookup.Provider registries;

    @Override
    public void setup(Level level, IVariableProvider vars) {
        registries = level.registryAccess();
        var manager = level.getRecipeManager();

        recipe = manager.byKey(Objects.requireNonNull(ResourceLocation.tryParse(getRecipeId())))
                .map(RecipeHolder::value)
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found: " + getRecipeId()));
    }

    @Nonnull
    @Override
    public IVariable process(Level level, String key) {
        if (recipe == null || registries == null) return IVariable.empty();

        return switch (key) {
            case "description" -> {
                ItemStack output = recipe.getResultItem(registries);
                yield IVariable.from(Component.translatable(output.getDescriptionId()), registries);
            }
            case "output" -> {
                ItemStack output = recipe.getResultItem(registries);
                yield IVariable.from(output, registries);
            }
            case "inputs" -> {
                List<IVariable> stacks = recipe.getIngredients().stream()
                        .map(ingredient -> IVariable.from(ingredient, registries))
                        .toList();
                yield IVariable.wrapList(stacks, registries);

            }
            default -> IVariable.empty();
        };
    }

    public abstract String getRecipeId();

}
