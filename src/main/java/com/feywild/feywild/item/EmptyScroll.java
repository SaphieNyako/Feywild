package com.feywild.feywild.item;

import com.feywild.feywild.util.TooltipHelper;
import com.google.common.collect.ImmutableSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.moddingx.libx.base.ItemBase;
import org.moddingx.libx.mod.ModX;

import javax.annotation.Nonnull;
import java.util.*;

public class EmptyScroll extends ItemBase {

    // Custom name is on the list as the custom name is handles by the stacks display name
    private static final Set<String> STORE_TAG_BLACKLIST = ImmutableSet.of(
            "id", "Pos", "Motion", "Rotation", "FallDistance", "Fire", "Air", "OnGround", "UUID", "CustomName",
            "CustomNameVisible", "Passengers", "SummonPos"
    );

    private static final Map<EntityType<?>, SummoningScroll<?>> CAPTURE_MAP = Collections.synchronizedMap(new HashMap<>());

    public static <T extends LivingEntity> void registerCapture(EntityType<? extends T> type, SummoningScroll<T> scroll) {
        CAPTURE_MAP.put(type, scroll);
    }

    public EmptyScroll(ModX mod, Properties properties) {
        super(mod, properties);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack oldStack, Player player, Entity entity) {
        EntityType<?> type = entity.getType();
        if (entity instanceof LivingEntity && CAPTURE_MAP.containsKey(type)) {
            SummoningScroll<?> scroll = CAPTURE_MAP.get(type);
            //noinspection unchecked
            if (!entity.level.isClientSide && ((SummoningScroll<LivingEntity>) scroll).canCapture(entity.level, player, (LivingEntity) entity)) {
                // Saving the dat of riding entities breaks stuff.
                if (entity.isPassenger()) entity.stopRiding();
                entity.getPassengers().forEach(Entity::stopRiding);
                ItemStack stack = new ItemStack(scroll);
                CompoundTag storedData = entity.saveWithoutId(new CompoundTag());
                // Remove some data that should not be kept
                STORE_TAG_BLACKLIST.forEach(storedData::remove);
                stack.getOrCreateTag().put("StoredEntityData", storedData);
                if (entity.hasCustomName()) {
                    stack.setHoverName(entity.getCustomName());
                }
                if (!player.isCreative()) {
                    oldStack.shrink(1);
                }
                if (Inventory.isHotbarSlot(player.getInventory().selected) && player.getInventory().getItem(player.getInventory().selected).isEmpty()) {
                    // First try to place the new stack at the same position in the hotbar where the old one was.
                    player.getInventory().setItem(player.getInventory().selected, stack);
                } else if (!player.getInventory().add(stack)) {
                    // inventory is full. Drop the item
                    ItemEntity ie = new ItemEntity(entity.level, entity.getX(), entity.getY(), entity.getZ(), stack.copy());
                    ie.setOwner(player.getUUID()); // Only the player can pick this up
                    entity.level.addFreshEntity(ie);
                }
                player.swing(InteractionHand.MAIN_HAND);
                entity.remove(Entity.RemovalReason.DISCARDED);
            }
            return true;
        }
        return false;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        TooltipHelper.addTooltip(tooltip, Component.translatable("message.feywild.summoning_scroll"));
        super.appendHoverText(stack, level, tooltip, flag);
    }
}
