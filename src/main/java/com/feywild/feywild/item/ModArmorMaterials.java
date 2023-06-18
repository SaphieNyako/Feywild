package com.feywild.feywild.item;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import org.moddingx.libx.util.lazy.LazyValue;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {

    FEY_WINGS("fey_wings", 20, new int[]{1, 3, 2, 1}, 25, ModSoundEvents.feyWings::getSoundEvent, 0.0F, 4.0F, () -> Ingredient.of(ModItems.feyDust));

    private static final int[] DURABILITY_PER_SLOT = new int[]{11, 16, 15, 13};
    
    private final String name;
    private final int durabilityMultiplier;
    private final int[] slotProtections;
    private final int enchantmentValue;
    private final LazyValue<SoundEvent> sound;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyValue<Ingredient> repairIngredient;

    ModArmorMaterials(String name, int durability, int[] slotProtections, int enchantments, Supplier<SoundEvent> sound, float toughness, float knockback, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durability;
        this.slotProtections = slotProtections;
        this.enchantmentValue = enchantments;
        this.sound = new LazyValue<>(sound);
        this.toughness = toughness;
        this.knockbackResistance = knockback;
        this.repairIngredient = new LazyValue<>(repairIngredient);
    }
    
    @Override
    public int getDurabilityForType(@Nonnull ArmorItem.Type type) {
        return DURABILITY_PER_SLOT[type.ordinal()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(@Nonnull ArmorItem.Type type) {
        return this.slotProtections[type.ordinal()];
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    @Nonnull
    public SoundEvent getEquipSound() {
        return this.sound.get();
    }

    @Override
    @Nonnull
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    @Nonnull
    public String getName() {
        return FeywildMod.getInstance() + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
