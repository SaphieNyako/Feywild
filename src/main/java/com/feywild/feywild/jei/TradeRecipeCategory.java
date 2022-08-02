//package com.feywild.feywild.jei;
//
//import com.feywild.feywild.FeywildMod;
//import com.feywild.feywild.item.ModItems;
//import com.feywild.feywild.trade.recipe.TradeRecipe;
//import com.google.common.collect.ImmutableList;
//import com.mojang.blaze3d.vertex.PoseStack;
//import mezz.jei.api.constants.VanillaTypes;
//import mezz.jei.api.gui.IRecipeLayout;
//import mezz.jei.api.gui.drawable.IDrawable;
//import mezz.jei.api.helpers.IGuiHelper;
//import mezz.jei.api.ingredients.IIngredients;
//import mezz.jei.api.recipe.category.IRecipeCategory;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.Font;
//import net.minecraft.network.chat.Component;
//import net.minecraft.network.chat.TranslatableComponent;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.ItemStack;
//
//import javax.annotation.Nonnull;
//import java.util.List;
//
//public class TradeRecipeCategory implements IRecipeCategory<TradeRecipe> {
//
//    public final static ResourceLocation UID = new ResourceLocation(FeywildMod.getInstance().modid, "trades");
//
//    private final IDrawable background;
//    private final IDrawable icon;
//    private final IDrawable slot;
//    private final IDrawable rightArrow;
//
//    public TradeRecipeCategory(IGuiHelper helper) {
//        this.background = helper.createBlankDrawable(178, 192);
//        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(ModItems.marketRuneStone));
//        this.slot = helper.getSlotDrawable();
//        this.rightArrow = helper.createDrawable(new ResourceLocation("minecraft", "textures/gui/container/furnace.png"), 176, 14, 24, 16);
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
//    public Class<? extends TradeRecipe> getRecipeClass() {
//        return TradeRecipe.class;
//    }
//
//    @Nonnull
//    @Override
//    public Component getTitle() {
//        return new TranslatableComponent("jei.trades.title");
//    }
//
//    @Nonnull
//    @Override
//    public IDrawable getBackground() {
//        return background;
//    }
//
//    @Nonnull
//    @Override
//    public IDrawable getIcon() {
//        return icon;
//    }
//
//    @Override
//    public void setIngredients(@Nonnull TradeRecipe recipe, @Nonnull IIngredients ii) {
//        ImmutableList.Builder<List<ItemStack>> packedInputs = ImmutableList.builder();
//        ImmutableList.Builder<List<ItemStack>> packedOutputs = ImmutableList.builder();
//        for (TradeRecipe.Entry entry : recipe.trades) {
//            packedInputs.add(entry.input);
//            packedInputs.add(entry.additional);
//            packedOutputs.add(entry.output);
//        }
//        ii.setInputLists(VanillaTypes.ITEM, packedInputs.build());
//        ii.setOutputLists(VanillaTypes.ITEM, packedOutputs.build());
//    }
//
//    @Override
//    public void setRecipe(@Nonnull IRecipeLayout layout, @Nonnull TradeRecipe recipe, @Nonnull IIngredients ii) {
//        for (int i = 0; i < recipe.trades.size(); i++) {
//            int xOff = recipe.trades.size() <= 8 ? 42 : (i / 8) * 93;
//            int yOff = 26 + (20 * (i % 8));
//            layout.getItemStacks().init(3 * i, true, xOff + 1, yOff);
//            layout.getItemStacks().init((3 * i) + 1, true, xOff + 21, yOff);
//            layout.getItemStacks().init((3 * i) + 2, false, xOff + 67, yOff);
//        }
//        layout.getItemStacks().set(ii);
//    }
//
//    @Override
//    public void draw(@Nonnull TradeRecipe recipe, @Nonnull PoseStack poseStack, double mouseX, double mouseY) {
//        Font font = Minecraft.getInstance().font;
//        int textX = 89 - (font.width(recipe.id.getPath()) / 2);
//        font.drawShadow(poseStack, getText(recipe.id.getPath()), textX, 3, 0xFFFFFF);
//        Component levelText = new TranslatableComponent("jei.trades.level", recipe.level);
//        font.drawShadow(poseStack, levelText, textX, 14, 0xFFFFFF);
//        for (int i = 0; i < recipe.trades.size(); i++) {
//            int xOff = recipe.trades.size() <= 8 ? 42 : (i / 8) * 93;
//            int yOff = 26 + (20 * (i % 8));
//            slot.draw(poseStack, xOff + 1, yOff);
//            slot.draw(poseStack, xOff + 21, yOff);
//            slot.draw(poseStack, xOff + 67, yOff);
//            rightArrow.draw(poseStack, xOff + 41, yOff + 1);
//        }
//    }
//
//    public String getText(String text) {
//        return text.replace("_", " ").replace("/trades", " ");
//    }
//}
