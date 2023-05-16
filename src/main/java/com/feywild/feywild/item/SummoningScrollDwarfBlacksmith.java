package com.feywild.feywild.item;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.entity.DwarfBlacksmith;
import com.feywild.feywild.network.ParticleMessage;
import com.feywild.feywild.util.TooltipHelper;
import com.feywild.feywild.world.FeywildDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.moddingx.libx.mod.ModX;
import org.moddingx.libx.registration.Registerable;
import org.moddingx.libx.registration.SetupContext;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class SummoningScrollDwarfBlacksmith extends SummoningScroll<DwarfBlacksmith> implements Registerable {

    public SummoningScrollDwarfBlacksmith(ModX mod, EntityType<DwarfBlacksmith> type, Properties properties) {
        super(mod, type, null, properties);
    }

    @Override
    public void registerCommon(SetupContext ctx) {
        EmptyScroll.registerCapture(type, this);
    }

    @Override
    protected boolean canSummon(Level level, Player player, BlockPos pos, @Nullable CompoundTag storedTag, DwarfBlacksmith entity) {
        if (player.getLevel().dimension() == FeywildDimensions.MARKETPLACE) {
            player.sendSystemMessage(Component.translatable("message.feywild.summon_market"));
            return false;
        } else if (level.getBlockState(pos).getBlock() == ModBlocks.dwarvenAnvil) {
            return level.getBlockState(pos).getBlock() == ModBlocks.dwarvenAnvil;
        } else {
            player.sendSystemMessage(Component.translatable("message.feywild.summon_fail_dwarf"));
            return false;
        }
    }

    @Override
    protected boolean canCapture(Level level, Player player, DwarfBlacksmith entity) {
        return player.getUUID().equals(entity.getOwner());
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        if (level != null) {
            TooltipHelper.addTooltip(tooltip, level, Component.translatable("message.feywild.dwarf_blacksmith"));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }

    @Nonnull
    @Override
    public InteractionResult useOn(@Nonnull UseOnContext context) {
        CompoundTag storedTag = null;
        if (context.getItemInHand().hasTag() && context.getItemInHand().getOrCreateTag().contains("StoredEntityData", Tag.TAG_COMPOUND)) {
            storedTag = context.getItemInHand().getOrCreateTag().getCompound("StoredEntityData");
        }
        if (context.getPlayer() != null) {
            if (!context.getLevel().isClientSide) {
                DwarfBlacksmith entity = this.type.create(context.getLevel());
                if (entity != null && this.canSummon(context.getLevel(), context.getPlayer(), context.getClickedPos().immutable(), storedTag, entity)) {
                    if (storedTag != null) entity.load(storedTag);
                    if (context.getItemInHand().hasCustomHoverName()) {
                        entity.setCustomName(context.getItemInHand().getHoverName());
                    }
                    BlockPos offsetPos = context.getClickedPos().relative(context.getClickedFace());
                    entity.setPos(offsetPos.getX() + 0.5, offsetPos.getY(), offsetPos.getZ() + 0.5);
                    this.prepareEntity(context.getLevel(), context.getPlayer(), context.getClickedPos().immutable(), entity);

                    context.getLevel().addFreshEntity(entity);
                    FeywildMod.getNetwork().sendParticles(context.getLevel(), ParticleMessage.Type.DANDELION_FLUFF, context.getClickedPos().getX(), context.getClickedPos().getY() + 1, context.getClickedPos().getZ());

                    if (this.soundEvent != null) entity.playSound(this.soundEvent, 1, 1);

                    if (!context.getPlayer().isCreative()) {
                        context.getItemInHand().shrink(1);
                    }
                }
            }
            return InteractionResult.sidedSuccess(context.getLevel().isClientSide);
        }
        return InteractionResult.PASS;
    }

}
