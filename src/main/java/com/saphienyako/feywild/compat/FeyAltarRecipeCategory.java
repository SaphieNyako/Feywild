package com.saphienyako.feywild.compat;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.block.ModBlocks;
import com.saphienyako.feywild.recipe.FeyAltarRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class FeyAltarRecipeCategory implements IRecipeCategory<FeyAltarRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(Feywild.MOD_ID, "fey_altar");
    public static final ResourceLocation TEXTURE = new ResourceLocation(Feywild.MOD_ID,
            "textures/gui/fey_altar_gui.png");

    public static final RecipeType<FeyAltarRecipe> FEY_ALTAR_TYPE =
            new RecipeType<>(UID, FeyAltarRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public FeyAltarRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 5, 3,155 , 80);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.FEY_ALTAR.get()));
    }

    @Override
    public @NotNull RecipeType<FeyAltarRecipe> getRecipeType() {
        return FEY_ALTAR_TYPE;
    }


    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("block.feywild.fey_altar");
    }


    @Override
    public @NotNull IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, FeyAltarRecipe recipe, @NotNull IFocusGroup group) {
        builder.addSlot(RecipeIngredientRole.INPUT, 75, 2).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 44, 23).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 105, 23).addIngredients(recipe.getIngredients().get(2));
        builder.addSlot(RecipeIngredientRole.INPUT, 56, 58).addIngredients(recipe.getIngredients().get(3));
        builder.addSlot(RecipeIngredientRole.INPUT, 94, 58).addIngredients(recipe.getIngredients().get(4));


        builder.addSlot(RecipeIngredientRole.OUTPUT, 138, 51).addItemStack(recipe.getResultItem());
    }
}
