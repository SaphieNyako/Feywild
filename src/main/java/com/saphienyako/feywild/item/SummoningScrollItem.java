package com.saphienyako.feywild.item;

import com.saphienyako.feywild.config.ModConfig;
import com.saphienyako.feywild.entity.ModEntities;
import com.saphienyako.feywild.entity.base.FeyBase;
import com.saphienyako.feywild.entity.base.intereface.ISummonable;
import com.saphienyako.feywild.network.FeywildNetwork;
import com.saphienyako.feywild.network.ParticleMessage;
import com.saphienyako.feywild.network.PlaySoundMessage;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.RegistryObject;


import javax.annotation.Nonnull;


public class SummoningScrollItem<T extends LivingEntity> extends Item {


    public SummoningScrollItem(Properties pProperties) {
        super(pProperties);
    }

    protected void prepareEntity(FeyBase entity, ItemUseContext context) {
        BlockPos pos = context.getClickedPos();
        entity.setPos(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);

        entity.setSummonPos(pos.immutable());

        PlayerEntity player = context.getPlayer();
        if (player != null) {
                entity.setOwner(player.getUUID());
        }

    }

    protected EntityType<? extends FeyBase> getEntityType() {
        if (this.equals(ModItems.SUMMONING_SCROLL_SPRING_PIXIE.get())) {
            return ModEntities.SPRING_PIXIE.get();
        } else if (this.equals(ModItems.SUMMONING_SCROLL_SUMMER_PIXIE.get())) {
            return ModEntities.SUMMER_PIXIE.get();
        } else if (this.equals(ModItems.SUMMONING_SCROLL_AUTUMN_PIXIE.get())) {
            return ModEntities.AUTUMN_PIXIE.get();
        } else if (this.equals(ModItems.SUMMONING_SCROLL_WINTER_PIXIE.get())) {
            return ModEntities.WINTER_PIXIE.get();
        } else {
            return null;
        }
    }

    @Nonnull
    @Override
    public ActionResultType useOn(@Nonnull ItemUseContext context) {
        if (context.getPlayer() != null) {
            if (!context.getLevel().isClientSide) {

                FeyBase entity = (FeyBase) getEntityType().create(context.getLevel());

                if (entity != null) {
                    this.prepareEntity(entity, context);

                    context.getLevel().addFreshEntity(entity);
                    context.getPlayer().sendMessage(entity.getFeySummonMessage(), context.getPlayer().getUUID());
                    FeywildNetwork.sendParticles(context.getLevel(), ParticleMessage.Type.DANDELION_FLUFF, context.getClickedPos());

                    if (ModConfig.CLIENT.voices_active.get()) {
                        FeywildNetwork.sendToPlayer(new PlaySoundMessage(entity.getSummonSound().getLocation(), entity.blockPosition()), (ServerPlayerEntity) context.getPlayer());
                    }
                    if (!context.getPlayer().isCreative()) {
                        context.getItemInHand().shrink(1);
                    }
                }
            }
            return ActionResultType.sidedSuccess(context.getLevel().isClientSide);
        }
        return ActionResultType.PASS;
    }
}
