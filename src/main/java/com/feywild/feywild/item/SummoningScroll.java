package com.feywild.feywild.item;

import com.feywild.feywild.util.TooltipHelper;
import com.google.common.collect.ImmutableSet;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class SummoningScroll<T extends LivingEntity> extends TooltipItem {

    // Custom name is on the list as the custom name is handles by the stacks display name
    private static final Set<String> STORE_TAG_BLACKLIST = ImmutableSet.of(
            "id", "Pos", "Motion", "Rotation", "FallDistance", "Fire", "Air", "OnGround", "UUID", "CustomName",
            "CustomNameVisible", "Passengers"
    );

    private static final Map<EntityType<?>, SummoningScroll<?>> CAPTURE_MAP = Collections.synchronizedMap(new HashMap<>());

    protected final EntityType<T> type;

    @Nullable
    private final SoundEvent soundEvent;

    public SummoningScroll(ModX mod, EntityType<T> type, @Nullable SoundEvent soundEvent, Properties properties) {
        super(mod, properties);
        this.type = type;
        this.soundEvent = soundEvent;
    }

    public static <T extends LivingEntity> void registerCapture(EntityType<? extends T> type, SummoningScroll<T> scroll) {
        CAPTURE_MAP.put(type, scroll);
    }

    protected boolean canSummon(Level level, Player player, BlockPos pos, @Nullable CompoundTag storedTag) {
        return true;
    }

    protected boolean canCapture(Level level, Player player, T entity) {
        return true;
    }

    protected void prepareEntity(Level level, Player player, BlockPos pos, T entity) {

    }

    @Nonnull
    @Override
    public InteractionResult useOn(@Nonnull UseOnContext context) {
        CompoundTag storedTag = null;
        if (context.getItemInHand().hasTag() && context.getItemInHand().getOrCreateTag().contains("StoredEntityData", Constants.NBT.TAG_COMPOUND)) {
            storedTag = context.getItemInHand().getOrCreateTag().getCompound("StoredEntityData");
        }
        if (context.getPlayer() != null && this.canSummon(context.getLevel(), context.getPlayer(), context.getClickedPos().immutable(), storedTag)) {
            if (!context.getLevel().isClientSide) {
                T entity = this.type.create(context.getLevel());
                if (entity != null) {
                    if (storedTag != null) entity.load(storedTag);
                    if (context.getItemInHand().hasCustomHoverName()) {
                        entity.setCustomName(context.getItemInHand().getHoverName());
                    }
                    BlockPos offsetPos = context.getClickedPos().relative(context.getClickedFace());
                    entity.setPos(offsetPos.getX() + 0.5, offsetPos.getY(), offsetPos.getZ() + 0.5);
                    this.prepareEntity(context.getLevel(), context.getPlayer(), context.getClickedPos().immutable(), entity);
                    if (this instanceof SummoningScrollFey || this instanceof SummoningScrollDwarfBlacksmith) {
                        context.getLevel().addFreshEntity(entity);
                        if (this.soundEvent != null) entity.playSound(this.soundEvent, 1, 1);
                    }
                    if (!context.getPlayer().isCreative()) {
                        context.getItemInHand().shrink(1);
                        context.getPlayer().addItem(new ItemStack(ModItems.summoningScroll));
                    }
                }
            }
            return InteractionResult.sidedSuccess(context.getLevel().isClientSide);
        }
        return InteractionResult.PASS;
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
        TooltipHelper.addTooltip(tooltip, new TranslatableComponent("message.feywild.summoning_scroll"));
    }
}

