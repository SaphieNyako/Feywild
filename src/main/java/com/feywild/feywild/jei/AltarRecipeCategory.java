//package com.feywild.feywild.jei;
//
//import com.feywild.feywild.FeywildMod;
//import com.feywild.feywild.block.ModBlocks;
//import com.feywild.feywild.recipes.AltarRecipe;
//import mezz.jei.api.constants.VanillaTypes;
//import mezz.jei.api.gui.IRecipeLayout;
//import mezz.jei.api.gui.drawable.IDrawable;
//import mezz.jei.api.helpers.IGuiHelper;
//import mezz.jei.api.ingredients.IIngredients;
//import mezz.jei.api.recipe.category.IRecipeCategory;
//import net.minecraft.network.chat.Component;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.ItemStack;
//
//import javax.annotation.Nonnull;
//
//public class AltarRecipeCategory implements IRecipeCategory<AltarRecipe> {
//
//    public final static ResourceLocation UID = new ResourceLocation(FeywildMod.getInstance().modid, "fey_altar");
//    public final static ResourceLocation TEXTURE = new ResourceLocation(FeywildMod.getInstance().modid, "textures/gui/fey_altar_jei.png");
//
//    private final IDrawable background;
//    private final IDrawable icon;
//
//    public AltarRecipeCategory(IGuiHelper helper) {
//        this.background = helper.createDrawable(TEXTURE, 0, 0, 85, 85);
//        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(ModBlocks.feyAltar));
//    }
//
//    @Nonnull
//    @Override
//    public ResourceLocation getUid() {
//        return UID;
//    }
//
//    @Nonnull
//    @Override
//    public Class<? extends AltarRecipe> getRecipeClass() {
//        return AltarRecipe.class;
//    }
//
//    @Nonnull
//    @Override
//    public Component getTitle() {
//        return ModBlocks.feyAltar.getName();
//    }
//
//    @Nonnull
//    @Override
//    public IDrawable getBackground() {
//        return this.background;
//    }
//
//    @Nonnull
//    @Override
//    public IDrawable getIcon() {
//        return this.icon;
//    }
//
//    @Override
//    public void setIngredients(AltarRecipe recipe, IIngredients ii) {
//        ii.setInputIngredients(recipe.getIngredients());
//        ii.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
//    }
//
//    @Override
//    public void setRecipe(@Nonnull IRecipeLayout layout, @Nonnull AltarRecipe recipe, IIngredients ii) {
//        double anglePerItem = (2 * Math.PI) / ii.getInputs(VanillaTypes.ITEM).size();
//        for (int i = 0; i < ii.getInputs(VanillaTypes.ITEM).size(); i++) {
//            double xd = Math.round(29 * Math.sin(anglePerItem * i));
//            double yd = -Math.round(29 * Math.cos(anglePerItem * i));
//            int x = (int) Math.round(43 + xd - 8);
//            int z = (int) Math.round(45 + yd - 8);
//            layout.getItemStacks().init(i, true, x, z);
//        }
//        layout.getItemStacks().init(ii.getInputs(VanillaTypes.ITEM).size(), false, 35, 37);
//        layout.getItemStacks().set(ii);
//    }
//}
