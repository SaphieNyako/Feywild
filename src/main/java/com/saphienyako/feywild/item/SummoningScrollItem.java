package com.saphienyako.feywild.item;

import com.saphienyako.feywild.config.ModConfig;
import com.saphienyako.feywild.entity.ModEntities;
import com.saphienyako.feywild.entity.base.FeyBase;
import com.saphienyako.feywild.entity.base.PixieBase;
import com.saphienyako.feywild.entity.base.intereface.IOwnable;
import com.saphienyako.feywild.entity.base.intereface.ISummonable;
import com.saphienyako.feywild.network.FeywildNetwork;
import com.saphienyako.feywild.network.ParticleMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

import javax.annotation.Nonnull;
import java.util.Objects;

import static com.saphienyako.feywild.item.ModItems.*;

public class SummoningScrollItem<T extends LivingEntity> extends Item {


    public SummoningScrollItem(Properties pProperties) {
        super(pProperties);
    }

    protected void prepareEntity(LivingEntity entity, @Nonnull UseOnContext context) {
        entity.setPos(context.getClickedPos().getX(), context.getClickedPos().getY() + 1, context.getClickedPos().getZ());
        if (entity instanceof ISummonable summoned) {
            summoned.setSummonPos(context.getClickedPos().immutable());
        }
        if (entity instanceof IOwnable owned) {
            owned.setOwner(Objects.requireNonNull(context.getPlayer()));
        }
    }

    protected EntityType<? extends FeyBase> returnLivingEntity(){
        if(this.equals(SUMMONING_SCROLL_SPRING_PIXIE.get())){
            return ModEntities.SPRING_PIXIE.get();
        } else if (this.equals(SUMMONING_SCROLL_SUMMER_PIXIE.get())) {
            return ModEntities.SUMMER_PIXIE.get();
        } else if (this.equals(SUMMONING_SCROLL_AUTUMN_PIXIE.get())){
            return ModEntities.AUTUMN_PIXIE.get();
        } else if (this.equals(SUMMONING_SCROLL_WINTER_PIXIE.get())){
            return ModEntities.WINTER_PIXIE.get();
        } else if (this.equals(SUMMONING_SCROLL_SHROOMLING.get())){
            return ModEntities.SHROOMLING.get();
        } else if (this.equals(SUMMONING_SCROLL_MANDRAGORA.get())){
            return ModEntities.MANDRAGORA.get();
        } else return null;

    }

    @Nonnull
    @Override
    public InteractionResult useOn(@Nonnull UseOnContext context) {
        if (context.getPlayer() != null) {
            if (!context.getLevel().isClientSide) {

                FeyBase entity = returnLivingEntity().create(context.getLevel());

                if (entity != null) {
                    this.prepareEntity(entity, context);

                    context.getLevel().addFreshEntity(entity);
                    context.getPlayer().sendSystemMessage(entity.getFeySummonMessage());
                    FeywildNetwork.sendParticles(context.getLevel(), ParticleMessage.Type.DANDELION_FLUFF, context.getClickedPos());

                    if (ModConfig.COMMON.voice_active.get()) {
                        context.getLevel().playSound(
                                null,
                                entity.blockPosition(),
                                entity.getSummonSound(),
                                SoundSource.NEUTRAL,
                                1.0F,
                                1.0F
                        );
                    }

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
