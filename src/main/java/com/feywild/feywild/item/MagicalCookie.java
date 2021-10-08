package com.feywild.feywild.item;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.MandrakeCrop;
import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.entity.MandragoraEntity;
import com.feywild.feywild.entity.ModEntityTypes;
import com.feywild.feywild.network.ParticleSerializer;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.ItemBase;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import java.util.Objects;

public class MagicalCookie extends TooltipItem {
    public MagicalCookie(ModX mod) {
        super(mod, new Item.Properties().food(
                new Food.Builder()
                        .nutrition(5)
                        .saturationMod(2.0f)
                        .effect(() -> new EffectInstance(Effects.DAMAGE_RESISTANCE, 900, 0), 1)
                        .build()), new TranslationTextComponent("message.feywild.magical_honey_cookie"));
    }

    @Nonnull
    @Override
    public ActionResultType useOn(ItemUseContext pContext) {
        if(!pContext.getLevel().isClientSide){
            if(pContext.getLevel().getBlockState(pContext.getClickedPos()).getValue(MandrakeCrop.AGE) == ModBlocks.mandrakeCrop.getMaxAge()){
                MandragoraEntity entity = ModEntityTypes.mandragora.create(pContext.getLevel());
                entity.setPos(pContext.getClickedPos().getX() + 0.5, pContext.getClickedPos().getY(), pContext.getClickedPos().getZ() + 0.5);
                pContext.getLevel().addFreshEntity(entity);
                pContext.getLevel().removeBlock(pContext.getClickedPos(), false);
                Objects.requireNonNull(pContext.getPlayer()).getItemInHand(pContext.getHand()).shrink(1);
                FeywildMod.getNetwork().sendParticles(pContext.getLevel(), ParticleSerializer.Type.DANDELION_FLUFF, pContext.getClickedPos().offset(0.5,0.5,0.5));
            }
        }
        return ActionResultType.SUCCESS;
    }
}
