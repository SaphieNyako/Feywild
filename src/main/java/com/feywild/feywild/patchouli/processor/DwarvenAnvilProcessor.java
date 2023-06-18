// UPDATE_TODO patchouli
//package com.feywild.feywild.patchouli.processor;
//
//import com.feywild.feywild.patchouli.PatchouliUtils;
//import com.feywild.feywild.recipes.DwarvenAnvilRecipe;
//import com.feywild.feywild.recipes.ModRecipeTypes;
//import net.minecraft.network.chat.Component;
//import net.minecraft.world.item.crafting.Ingredient;
//import vazkii.patchouli.api.IComponentProcessor;
//import vazkii.patchouli.api.IVariable;
//import vazkii.patchouli.api.IVariableProvider;
//
//import javax.annotation.Nonnull;
//import javax.annotation.Nullable;
//import java.util.List;
//
//public class DwarvenAnvilProcessor implements IComponentProcessor {
//
//    @Nullable
//    private DwarvenAnvilRecipe recipe;
//
//    @Override
//    public void setup(IVariableProvider vars) {
//        this.recipe = PatchouliUtils.getRecipe(DwarvenAnvilRecipe.class, ModRecipeTypes.dwarvenAnvil, vars.get("recipe").asString());
//    }
//
//    @Nonnull
//    @Override
//    public IVariable process(@Nonnull String key) {
//        if (recipe == null) return IVariable.empty();
//        return switch (key) {
//            case "description" -> IVariable.from(Component.translatable(this.recipe.getResultItem().getDescriptionId()));
//            case "schematic" -> IVariable.from(this.recipe.getSchematics());
//            case "mana" -> IVariable.wrap(this.recipe.getMana());
//            case "output" -> IVariable.from(this.recipe.getResultItem());
//            case "item0" -> IVariable.from(this.getInput(0).getItems());
//            case "item1" -> IVariable.from(this.getInput(1).getItems());
//            case "item2" -> IVariable.from(this.getInput(2).getItems());
//            case "item3" -> IVariable.from(this.getInput(3).getItems());
//            case "item4" -> IVariable.from(this.getInput(4).getItems());
//            default -> IVariable.empty();
//        };
//    }
//
//    private Ingredient getInput(int idx) {
//        if (this.recipe == null) return Ingredient.EMPTY;
//        List<Ingredient> list = this.recipe.getInputs();
//        if (idx < 0 || idx >= list.size()) return Ingredient.EMPTY;
//        return list.get(idx);
//    }
//}
