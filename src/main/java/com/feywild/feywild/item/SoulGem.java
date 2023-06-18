package com.feywild.feywild.item;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.network.ParticleMessage;
import com.feywild.feywild.tag.ModEntityTags;
import com.feywild.feywild.util.TooltipHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.moddingx.libx.base.ItemBase;
import org.moddingx.libx.mod.ModX;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class SoulGem extends ItemBase {

    public SoulGem(ModX mod, Properties properties) {
        super(mod, properties);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        if (level != null) TooltipHelper.addTooltip(tooltip, level, Component.translatable("message.feywild.soul_gem"));
        super.appendHoverText(stack, level, tooltip, flag);
    }

    @Nonnull
    @Override
    public InteractionResult useOn(@Nonnull UseOnContext context) {
        if (context.getPlayer() != null) {
            if (!context.getLevel().isClientSide) {
                if (!context.getPlayer().isCreative()) context.getPlayer().getItemInHand(context.getHand()).shrink(1);
                context.getPlayer().swing(context.getHand(), true);
                EntityType<?> type = Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.tags()).getTag(ModEntityTags.SOUL_GEM).getRandomElement(context.getPlayer().getRandom()).orElse(null);
                Entity entity = type == null ? null : type.create(context.getLevel());
                if (entity != null) {
                    entity.setPos(context.getClickedPos().getX() + 0.5, context.getClickedPos().getY() + 1, context.getClickedPos().getZ() + 0.5);
                    context.getLevel().addFreshEntity(entity);
                    FeywildMod.getNetwork().sendParticles(context.getLevel(), ParticleMessage.Type.MAGIC_EFFECT, context.getPlayer().getX(), context.getPlayer().getY(), context.getPlayer().getZ());
                }
            }
            return InteractionResult.sidedSuccess(context.getLevel().isClientSide);
        }
        return super.useOn(context);
    }
}
