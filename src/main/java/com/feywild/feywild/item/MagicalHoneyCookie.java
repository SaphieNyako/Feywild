package com.feywild.feywild.item;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.entity.MandragoraEntity;
import com.feywild.feywild.util.TooltipHelper;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class MagicalHoneyCookie extends SummoningScroll<MandragoraEntity> {

    public MagicalHoneyCookie(ModX mod, EntityType<MandragoraEntity> type, @Nullable SoundEvent soundEvent, Properties properties) {
        super(mod, type, soundEvent, properties);
    }

    @Override
    protected boolean canSummon(World world, PlayerEntity player, BlockPos pos) {
        return world.getBlockState(pos).getBlock() == ModBlocks.mandrakeCrop && world.getBlockState(pos).getValue(BlockStateProperties.AGE_7) == 7;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable World world, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flag) {
        TooltipHelper.addTooltip(tooltip, new TranslationTextComponent("message.feywild.magical_honey_cookie"));
        super.appendHoverText(stack, world, tooltip, flag);
    }

    @Nonnull
    @Override
    public ActionResultType useOn(@Nonnull ItemUseContext context) {
        super.useOn(context);
        World world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if (!world.isClientSide) {
            if (world.getBlockState(pos).getBlock() == ModBlocks.mandrakeCrop && world.getBlockState(pos).getValue(BlockStateProperties.AGE_7) == 7) {
                // world.destroyBlock(context.getClickedPos(), true);
                world.removeBlock(context.getClickedPos(), true);
            }
        }
        return super.useOn(context);
    }
}
