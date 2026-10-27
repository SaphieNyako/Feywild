package com.saphienyako.feywild.item;

import com.saphienyako.feywild.entity.ModEntities;
import com.saphienyako.feywild.entity.OberonEntity;
import com.saphienyako.feywild.entity.base.BossBase;
import com.saphienyako.feywild.item.base.ToolTipBaseItem;
import com.saphienyako.feywild.network.ParticleMessage;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.UseOnContext;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nonnull;

import static com.saphienyako.feywild.item.ModItems.*;

public class BossSummoningScrollItem extends ToolTipBaseItem {

    public BossSummoningScrollItem(Properties pProperties, MutableComponent component) {
        super(pProperties, component);
    }


    protected void prepareEntity(LivingEntity entity, @Nonnull UseOnContext context) {
        entity.setPos(context.getClickedPos().getX(), context.getClickedPos().getY() + 1, context.getClickedPos().getZ());
    }

    protected EntityType<? extends BossBase> returnLivingEntity(){
        if(this.equals(SUMMONING_SCROLL_QUEEN_TITANIA.get())){
            return ModEntities.TITANIA.get();
        } else if (this.equals(SUMMONING_SCROLL_QUEEN_MAB.get())) {
            return ModEntities.MAB.get();
        }else if (this.equals(SUMMONING_SCROLL_OBERON.get())) {
            return ModEntities.OBERON.get();
        }else if (this.equals(SUMMONING_SCROLL_ASHEN_LORD.get())) {
            return ModEntities.ASHEN_LORD.get();
        }
        else return null;
    }

    @Nonnull
    @Override
    public InteractionResult useOn(@Nonnull UseOnContext context) {
        if (context.getPlayer() != null) {
            if (!context.getLevel().isClientSide()) {

                BossBase entity = returnLivingEntity().create(context.getLevel(), EntitySpawnReason.SPAWN_ITEM_USE);

                if (entity != null) {
                    this.prepareEntity(entity, context);

                    context.getLevel().addFreshEntity(entity);
                    PacketDistributor.sendToPlayersTrackingEntity(entity, new ParticleMessage(ParticleMessage.Particles.DANDELION_FLUFF, context.getClickedPos()));

                    if (!context.getPlayer().isCreative()) {
                        context.getItemInHand().shrink(1);
                    }

                    if(entity instanceof OberonEntity) {
                        context.getLevel().playSound(null, entity.blockPosition(), entity.getSummonSound(), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    }
                }
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}