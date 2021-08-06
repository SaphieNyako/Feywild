package com.feywild.feywild.jei;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.recipes.DwarvenAnvilRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DwarvenAnvilRecipeCategory implements IRecipeCategory<DwarvenAnvilRecipe> {

    public final static ResourceLocation UID = new ResourceLocation(FeywildMod.getInstance().modid, "dwarven_anvil");
    public IDrawable background;
    public IDrawable icon;
    IGuiHelper helper;

    public DwarvenAnvilRecipeCategory(IGuiHelper helper) {
        ResourceLocation location = new ResourceLocation(FeywildMod.getInstance().modid, "textures/gui/dwarven_anvil_jei.png");
        this.helper = helper;
        //background = helper.createBlankDrawable(85, 85); //Would we be able to get a 85x85 image?
        background = helper.createDrawable(location, 0, 0, 85, 85);

        icon = helper.createDrawableIngredient(new ItemStack(ModBlocks.dwarvenAnvil));

    }

    @Nonnull
    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Nonnull
    @Override
    public Class<? extends DwarvenAnvilRecipe> getRecipeClass() {
        return DwarvenAnvilRecipe.class;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return "Dwarven Anvil";
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Nonnull
    @Override
    public IDrawable getIcon() {
        return icon;
    }

    // TODO needs fixing (new recipe format)
    @Override
    public void setIngredients(DwarvenAnvilRecipe recipe, IIngredients iIngredients) {
        List<List<ItemStack>> itemStacks = new ArrayList<>();

        recipe.getInputs().forEach(ingredient -> itemStacks.add(Arrays.asList(ingredient.getItems())));
        iIngredients.setInputLists(VanillaTypes.ITEM, itemStacks);
        iIngredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());

    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout iRecipeLayout, @Nonnull DwarvenAnvilRecipe recipe, IIngredients iIngredients) {
        for (int i = 0; i < iIngredients.getInputs(VanillaTypes.ITEM).size(); i++) {

            if (i == 0) {
                iRecipeLayout.getItemStacks().init(i, true, 0, 0);
                iRecipeLayout.getItemStacks().set(i, iIngredients.getInputs(VanillaTypes.ITEM).get(i));
            }
            if (i == 1) {
                iRecipeLayout.getItemStacks().init(i, true, 22, 32);  // 0 32
                iRecipeLayout.getItemStacks().set(i, iIngredients.getInputs(VanillaTypes.ITEM).get(i));
            }
            if (i == 2) {
                iRecipeLayout.getItemStacks().init(i, true, 32, 54);  //16 64
                iRecipeLayout.getItemStacks().set(i, iIngredients.getInputs(VanillaTypes.ITEM).get(i));
            }
            if (i == 3) {
                iRecipeLayout.getItemStacks().init(i, true, 42, 12);
                iRecipeLayout.getItemStacks().set(i, iIngredients.getInputs(VanillaTypes.ITEM).get(i));
            }
            if (i == 4) {
                iRecipeLayout.getItemStacks().init(i, true, 54, 54);
                iRecipeLayout.getItemStacks().set(i, iIngredients.getInputs(VanillaTypes.ITEM).get(i));
            }
            if (i == 5) {
                iRecipeLayout.getItemStacks().init(i, true, 66, 32);
                iRecipeLayout.getItemStacks().set(i, iIngredients.getInputs(VanillaTypes.ITEM).get(i));
            }
        }

        iRecipeLayout.getItemStacks().init(iIngredients.getInputs(VanillaTypes.ITEM).size(), true, 32, -32);
        iRecipeLayout.getItemStacks().set(iIngredients.getInputs(VanillaTypes.ITEM).size(), iIngredients.getOutputs(VanillaTypes.ITEM).get(0));

    }
}
