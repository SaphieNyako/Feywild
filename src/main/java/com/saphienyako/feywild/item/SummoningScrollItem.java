package com.saphienyako.feywild.item;

import com.saphienyako.feywild.config.ModConfig;
import com.saphienyako.feywild.entity.ModEntities;
import com.saphienyako.feywild.entity.base.FeyBase;
import com.saphienyako.feywild.entity.base.PixieBase;
import com.saphienyako.feywild.entity.base.intereface.IOwnable;
import com.saphienyako.feywild.entity.base.intereface.ISummonable;
import com.saphienyako.feywild.network.FeywildNetwork;
import com.saphienyako.feywild.network.ParticleMessage;
import com.saphienyako.feywild.network.PlaySoundMessage;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import java.util.Objects;

import static com.saphienyako.feywild.item.ModItems.*;

public class SummoningScrollItem<T extends LivingEntity> extends Item {


    public SummoningScrollItem(Properties pProperties) {
        super(pProperties);
    }

    protected void prepareEntity(FeyBase entity, @Nonnull UseOnContext context) {
        BlockPos pos = context.getClickedPos();
        entity.setPos(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);

        entity.setSummonPos(pos.immutable());

        Player player = context.getPlayer();
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
    public InteractionResult useOn(@Nonnull UseOnContext context) {
        if (context.getPlayer() == null || context.getLevel().isClientSide) return InteractionResult.PASS;

        EntityType<? extends FeyBase> type = getEntityType();
        if (type == null) return InteractionResult.PASS;

        LivingEntity entityRaw = type.create(context.getLevel());
        if (!(entityRaw instanceof FeyBase entity)) return InteractionResult.PASS;

        prepareEntity(entity, context);

        context.getLevel().addFreshEntity(entity);

        Player player = context.getPlayer();
        player.sendMessage(entity.getFeySummonMessage(), player.getUUID());

        FeywildNetwork.sendParticles(context.getLevel(), ParticleMessage.Type.DANDELION_FLUFF, context.getClickedPos());

        if (ModConfig.CLIENT.voices_active.get() && player instanceof ServerPlayer serverPlayer) {
            FeywildNetwork.sendToPlayer(new PlaySoundMessage(entity.getSummonSound().getLocation(), entity.blockPosition()), serverPlayer);
        }

        if (!player.isCreative()) {
            context.getItemInHand().shrink(1);
        }

        return InteractionResult.sidedSuccess(context.getLevel().isClientSide);
    }

}
