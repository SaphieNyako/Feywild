package com.feywild.feywild.recipes;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AltarRecipeCategory implements IRecipeCategory<AltarRecipe> {

    public final static ResourceLocation UID = new ResourceLocation(FeywildMod.MOD_ID, "fey_altar");
    public IDrawable background;
    public IDrawable icon;
    IGuiHelper helper;

    public AltarRecipeCategory(IGuiHelper helper) {
        ResourceLocation location = new ResourceLocation(FeywildMod.MOD_ID, "textures/gui/fey_altar_jei.png");
        this.helper = helper;
        //background = helper.createBlankDrawable(85, 85); //Would we be able to get a 85x85 image?
        background = helper.createDrawable(location, 0, 0, 85, 85);

        icon = helper.createDrawableIngredient(new ItemStack(ModBlocks.FEY_ALTAR.get()));

    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends AltarRecipe> getRecipeClass() {
        return AltarRecipe.class;
    }

    @Override
    public String getTitle() {
        return "Fey Altar";
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    /*
    @Override
    public void draw(AltarRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        IDrawableAnimated arrow = this.cachedArrows.getUnchecked(40); // The crafting arrow ???
        arrow.draw(matrixStack, 38, 6); //location on screen
    } */

    @Override
    public void setIngredients(AltarRecipe altarRecipe, IIngredients iIngredients) {
        List<List<ItemStack>> itemStacks = new ArrayList<>();

        altarRecipe.getInputs().forEach(ingredient -> itemStacks.add(Arrays.asList(ingredient.getItems())));
        iIngredients.setInputLists(VanillaTypes.ITEM, itemStacks);
        iIngredients.setOutput(VanillaTypes.ITEM, altarRecipe.getResultItem());
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, AltarRecipe altarRecipe, IIngredients iIngredients) {
        //Where is the location of every item.
       /* int xPos = 0;
        int yPos = 8;

        for (int i = 0; i < iIngredients.getInputs(VanillaTypes.ITEM).size(); i++) {
            iRecipeLayout.getItemStacks().init(i, true, xPos, yPos);
            iRecipeLayout.getItemStacks().set(i, iIngredients.getInputs(VanillaTypes.ITEM).get(i));
            xPos += 16;
        }
        iRecipeLayout.getItemStacks().init(iIngredients.getInputs(VanillaTypes.ITEM).size(), true, 32, 48);
        iRecipeLayout.getItemStacks().set(iIngredients.getInputs(VanillaTypes.ITEM).size(), iIngredients.getOutputs(VanillaTypes.ITEM).get(0));
*/
        iRecipeLayout.getItemStacks().init(0, true, 0, 32);
        iRecipeLayout.getItemStacks().set(0, iIngredients.getInputs(VanillaTypes.ITEM).get(0));

        iRecipeLayout.getItemStacks().init(1, true, 16, 64);
        iRecipeLayout.getItemStacks().set(1, iIngredients.getInputs(VanillaTypes.ITEM).get(1));

        iRecipeLayout.getItemStacks().init(2, true, 32, 0);
        iRecipeLayout.getItemStacks().set(2, iIngredients.getInputs(VanillaTypes.ITEM).get(2));

        iRecipeLayout.getItemStacks().init(3, true, 48, 64);
        iRecipeLayout.getItemStacks().set(3, iIngredients.getInputs(VanillaTypes.ITEM).get(3));

        iRecipeLayout.getItemStacks().init(4, true, 64, 32);
        iRecipeLayout.getItemStacks().set(4, iIngredients.getInputs(VanillaTypes.ITEM).get(4));

        iRecipeLayout.getItemStacks().init(iIngredients.getInputs(VanillaTypes.ITEM).size(), true, 32, -32);
        iRecipeLayout.getItemStacks().set(iIngredients.getInputs(VanillaTypes.ITEM).size(), iIngredients.getOutputs(VanillaTypes.ITEM).get(0));

    }
}
