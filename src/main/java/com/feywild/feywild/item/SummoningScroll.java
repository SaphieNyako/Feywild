package com.feywild.feywild.item;

import com.feywild.feywild.entity.ModEntityTypes;
import com.feywild.feywild.entity.base.FeyEntity;
import com.feywild.feywild.util.TooltipHelper;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class SummoningScroll<T extends LivingEntity> extends TooltipItem {

    protected final EntityType<T> type;

    @Nullable
    private final SoundEvent soundEvent;

    public SummoningScroll(ModX mod, EntityType<T> type, @Nullable SoundEvent soundEvent, Properties properties) {
        super(mod, properties);
        this.type = type;
        this.soundEvent = soundEvent;
    }

    protected boolean canSummon(World world, PlayerEntity player, BlockPos pos) {
        return false;
    }

    protected void prepareEntity(World world, PlayerEntity player, BlockPos pos, T entity) {

    }

    @Nonnull
    @Override
    public ActionResultType useOn(@Nonnull ItemUseContext context) {
        if (context.getPlayer() != null && this.canSummon(context.getLevel(), context.getPlayer(), context.getClickedPos().immutable())) {
            if (!context.getLevel().isClientSide) {
                T entity = this.type.create(context.getLevel());
                if (entity != null) {
                    BlockPos offsetPos = context.getClickedPos().relative(context.getClickedFace());
                    entity.setPos(offsetPos.getX() + 0.5, offsetPos.getY(), offsetPos.getZ() + 0.5);
                    this.prepareEntity(context.getLevel(), context.getPlayer(), context.getClickedPos().immutable(), entity);
                    context.getLevel().addFreshEntity(entity);
                    if (this.soundEvent != null) entity.playSound(this.soundEvent, 1, 1);
                    if (!context.getPlayer().isCreative()) {
                        context.getItemInHand().shrink(1);
                        context.getPlayer().addItem(new ItemStack(ModItems.summoningScroll));
                    }
                }
            }
            return ActionResultType.sidedSuccess(context.getLevel().isClientSide);
        }
        return ActionResultType.PASS;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
        if (!entity.level.isClientSide && entity instanceof FeyEntity && (Objects.equals(((FeyEntity) entity).getOwner(), player) || player.isCreative()) && !canSummon(entity.level, player, entity.blockPosition())) {
            if (entity.getType().equals(ModEntityTypes.springPixie)) {
                player.addItem(new ItemStack(ModItems.summoningScrollSpringPixie));
            } else if (entity.getType().equals(ModEntityTypes.autumnPixie)) {
                player.addItem(new ItemStack(ModItems.summoningScrollAutumnPixie));
            } else if (entity.getType().equals(ModEntityTypes.summerPixie)) {
                player.addItem(new ItemStack(ModItems.summoningScrollSummerPixie));
            } else if (entity.getType().equals(ModEntityTypes.winterPixie)) {
                player.addItem(new ItemStack(ModItems.summoningScrollWinterPixie));
            }
            entity.remove();
            if (!player.isCreative())
                stack.shrink(1);
        }
        return true;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, World world, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flag) {
        TooltipHelper.addTooltip(tooltip, new TranslationTextComponent("message.feywild.summoning_scroll"));
    }
}

