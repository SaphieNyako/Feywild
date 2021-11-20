package com.feywild.feywild.item;

import com.feywild.feywild.entity.DwarfBlacksmithEntity;
import com.feywild.feywild.util.TooltipHelper;
import com.google.common.collect.ImmutableSet;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class SummoningScroll<T extends LivingEntity> extends TooltipItem {

    // Custom name is on the list as the custom name is handles by the stacks display name
    private static final Set<String> STORE_TAG_BLACKLIST = ImmutableSet.of(
            "id", "Pos", "Motion", "Rotation", "FallDistance", "Fire", "Air", "OnGround", "UUID", "CustomName",
            "CustomNameVisible", "Passengers", "SummonPos"
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

    protected boolean canSummon(World world, PlayerEntity player, BlockPos pos, @Nullable CompoundNBT storedTag, T entity) {
        return true;
    }

    protected boolean canCapture(World world, PlayerEntity player, T entity) {
        return true;
    }

    protected void prepareEntity(World world, PlayerEntity player, BlockPos pos, T entity) {

    }

    @Nonnull
    @Override
    public ActionResultType useOn(@Nonnull ItemUseContext context) {
        if (this.type != null) {
            CompoundNBT storedTag = null;
            if (context.getItemInHand().hasTag() && context.getItemInHand().getOrCreateTag().contains("StoredEntityData", Constants.NBT.TAG_COMPOUND)) {
                storedTag = context.getItemInHand().getOrCreateTag().getCompound("StoredEntityData");
            }
            if (context.getPlayer() != null) {
                if (!context.getLevel().isClientSide) {
                    T entity = this.type.create(context.getLevel());
                    if (entity != null && this.canSummon(context.getLevel(), context.getPlayer(), context.getClickedPos().immutable(), storedTag, entity)) {
                        if (storedTag != null) entity.load(storedTag);
                        if (context.getItemInHand().hasCustomHoverName()) {
                            entity.setCustomName(context.getItemInHand().getHoverName());
                        }
                        BlockPos offsetPos = context.getClickedPos().relative(context.getClickedFace());
                        entity.setPos(offsetPos.getX() + 0.5, offsetPos.getY(), offsetPos.getZ() + 0.5);
                        this.prepareEntity(context.getLevel(), context.getPlayer(), context.getClickedPos().immutable(), entity);
                        context.getLevel().addFreshEntity(entity);
                        if (this.soundEvent != null) entity.playSound(this.soundEvent, 1, 1);
                        if (!context.getPlayer().isCreative()) {
                            context.getItemInHand().shrink(1);
                            if (!(entity instanceof DwarfBlacksmithEntity)) {
                                context.getPlayer().addItem(new ItemStack(ModItems.summoningScroll));
                            }
                        }
                    } else
                        context.getPlayer().sendMessage(new TranslationTextComponent("message.feywild.summon_fail"), context.getPlayer().getUUID());
                }
                return ActionResultType.sidedSuccess(context.getLevel().isClientSide);
            }
        }
        return ActionResultType.PASS;

    }

    @Override
    public boolean onLeftClickEntity(ItemStack oldStack, PlayerEntity player, Entity entity) {
        if (this.type == null) {
            EntityType<?> type = entity.getType();
            if (entity instanceof LivingEntity && CAPTURE_MAP.containsKey(type)) {
                SummoningScroll<?> scroll = CAPTURE_MAP.get(type);
                //noinspection unchecked
                if (!entity.level.isClientSide && ((SummoningScroll<LivingEntity>) scroll).canCapture(entity.level, player, (LivingEntity) entity)) {
                    // Saving the dat of riding entities breaks stuff.
                    if (entity.isPassenger()) entity.stopRiding();
                    entity.getPassengers().forEach(Entity::stopRiding);

                    ItemStack stack = new ItemStack(scroll);
                    CompoundNBT storedData = entity.saveWithoutId(new CompoundNBT());
                    // Remove some data that should not be kept
                    STORE_TAG_BLACKLIST.forEach(storedData::remove);

                    stack.getOrCreateTag().put("StoredEntityData", storedData);
                    if (entity.hasCustomName()) {
                        stack.setHoverName(entity.getCustomName());
                    }

                    if (!player.isCreative()) {
                        oldStack.shrink(1);
                    }

                    if (PlayerInventory.isHotbarSlot(player.inventory.selected) && player.inventory.getItem(player.inventory.selected).isEmpty()) {
                        // First try to place the new stack at the same position in the hotbar where the old one was.
                        player.inventory.setItem(player.inventory.selected, stack);
                    } else if (!player.inventory.add(stack)) {
                        // inventory is full. Drop the item
                        ItemEntity ie = new ItemEntity(entity.level, entity.getX(), entity.getY(), entity.getZ(), stack.copy());
                        ie.setOwner(player.getUUID()); // Only the player can pick this up
                        entity.level.addFreshEntity(ie);
                    }

                    player.swing(Hand.MAIN_HAND);

                    entity.remove();
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, World world, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flag) {
        TooltipHelper.addTooltip(tooltip, new TranslationTextComponent("message.feywild.summoning_scroll"));
    }
}

