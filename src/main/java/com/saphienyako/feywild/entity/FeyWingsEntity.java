package com.saphienyako.feywild.entity;

import com.saphienyako.feywild.Feywild;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FeyWingsEntity extends LivingEntity {

    private ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/fey_wings/spring.png");


    protected FeyWingsEntity(EntityType<? extends LivingEntity> entity, Level level) {
        super(entity, level);
    }

    public static AttributeSupplier.Builder getDefaultAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1)
                .add(Attributes.MOVEMENT_SPEED, 0);
    }

    @Override
    public @NotNull Iterable<ItemStack> getArmorSlots() { return List.of(); }

    @Override
    public @NotNull ItemStack getItemBySlot(EquipmentSlot slot) { return ItemStack.EMPTY; }

    @Override
    public void setItemSlot(@NotNull EquipmentSlot slot, @NotNull ItemStack stack) {}

    @Override
    public @NotNull HumanoidArm getMainArm() { return HumanoidArm.RIGHT; }

    public void setTexture(ResourceLocation texture) {
        this.texture = texture;
    }

    public String getTexture() {
        return texture.getPath();
    }
}
