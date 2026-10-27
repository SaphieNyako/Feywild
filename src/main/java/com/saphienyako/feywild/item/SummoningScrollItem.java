package com.saphienyako.feywild.item;

import com.saphienyako.feywild.compat.ModCompat;
import com.saphienyako.feywild.config.FeywildConfig;
import com.saphienyako.feywild.entity.ModEntities;
import com.saphienyako.feywild.entity.base.FeyBase;
import com.saphienyako.feywild.entity.base.intereface.IOwnable;
import com.saphienyako.feywild.entity.base.intereface.ISummonable;
import com.saphienyako.feywild.item.base.ToolTipBaseItem;
import com.saphienyako.feywild.network.ParticleMessage;
import com.saphienyako.quest_giver.quest.data.QuestData;
import com.saphienyako.quest_giver.quest.task.SpecialTask;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nonnull;
import java.util.Objects;

import static com.saphienyako.feywild.item.ModItems.*;

public class SummoningScrollItem extends ToolTipBaseItem {

    public SummoningScrollItem(Properties pProperties, MutableComponent component) {
        super(pProperties, component);
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

    protected EntityType<? extends FeyBase> returnLivingEntity(Player player){
        if(this.equals(SUMMONING_SCROLL_SPRING_PIXIE.get())){
            return ModEntities.SPRING_PIXIE.get();
        } else if (this.equals(SUMMONING_SCROLL_SUMMER_PIXIE.get())) {
            return ModEntities.SUMMER_PIXIE.get();
        } else if (this.equals(SUMMONING_SCROLL_AUTUMN_PIXIE.get())){
            return ModEntities.AUTUMN_PIXIE.get();
        } else if (this.equals(SUMMONING_SCROLL_WINTER_PIXIE.get())){
            return ModEntities.WINTER_PIXIE.get();
        } else if (this.equals(SUMMONING_SCROLL_SHROOMLING.get())){
            if (ModCompat.QUEST_GIVER_LOADED) {
                QuestData.get((ServerPlayer) player).checkComplete(SpecialTask.INSTANCE, "summon_shroomling");
            }
            return ModEntities.SHROOMLING.get();
        } else if (this.equals(SUMMONING_SCROLL_MANDRAGORA.get())){
            if (ModCompat.QUEST_GIVER_LOADED) {
                QuestData.get((ServerPlayer) player).checkComplete(SpecialTask.INSTANCE, "summon_mandragora");
            }
            return ModEntities.MANDRAGORA.get();

        } else if (this.equals(SUMMONING_SCROLL_BELLSNICKEL.get())){
            if (ModCompat.QUEST_GIVER_LOADED) {
                QuestData.get((ServerPlayer) player).checkComplete(SpecialTask.INSTANCE, "summon_bellsnickel");
            }
            return ModEntities.BELLSNICKEL.get();
        }else if (this.equals(SUMMONING_SCROLL_SPRING_TREE_ENT.get())){
            if (ModCompat.QUEST_GIVER_LOADED) {
                QuestData.get((ServerPlayer) player).checkComplete(SpecialTask.INSTANCE, "summon_spring_tree_ent");
            }
            return ModEntities.SPRING_TREE_ENT.get();
        }else if (this.equals(SUMMONING_SCROLL_SUMMER_TREE_ENT.get())){
            if (ModCompat.QUEST_GIVER_LOADED) {
                QuestData.get((ServerPlayer) player).checkComplete(SpecialTask.INSTANCE, "summon_summer_tree_ent");
            }
            return ModEntities.SUMMER_TREE_ENT.get();
        }else if (this.equals(SUMMONING_SCROLL_AUTUMN_TREE_ENT.get())){
            if (ModCompat.QUEST_GIVER_LOADED) {
                QuestData.get((ServerPlayer) player).checkComplete(SpecialTask.INSTANCE, "summon_autumn_tree_ent");
            }
            return ModEntities.AUTUMN_TREE_ENT.get();
        }else if (this.equals(SUMMONING_SCROLL_WINTER_TREE_ENT.get())){
            if (ModCompat.QUEST_GIVER_LOADED) {
                QuestData.get((ServerPlayer) player).checkComplete(SpecialTask.INSTANCE, "summon_winter_tree_ent");
            }
            return ModEntities.WINTER_TREE_ENT.get();
        } else if (this.equals(SUMMONING_SCROLL_BEE_KNIGHT.get())){
            if (ModCompat.QUEST_GIVER_LOADED) {
                QuestData.get((ServerPlayer) player).checkComplete(SpecialTask.INSTANCE, "summon_bee_knight");
            }
            return ModEntities.BEE_MOUNT.get();
        }

        else return null;
    }

    @Nonnull
    @Override
    public InteractionResult useOn(@Nonnull UseOnContext context) {
        if (context.getPlayer() != null) {
            if (!context.getLevel().isClientSide()) {

                FeyBase entity = returnLivingEntity(context.getPlayer()).create(context.getLevel(), EntitySpawnReason.SPAWN_ITEM_USE);

                if (entity != null) {
                    this.prepareEntity(entity, context);

                    context.getLevel().addFreshEntity(entity);
                    context.getPlayer().sendSystemMessage(entity.getFeySummonMessage());
                    PacketDistributor.sendToPlayersTrackingEntity(entity, new ParticleMessage(ParticleMessage.Particles.DANDELION_FLUFF, context.getClickedPos()));


                    if (FeywildConfig.voicesActive) {
                        context.getLevel().playSound(null, entity.blockPosition(), entity.getSummonSound(), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    }

                    if (!context.getPlayer().isCreative()) {
                        context.getItemInHand().shrink(1);
                    }
                }
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
