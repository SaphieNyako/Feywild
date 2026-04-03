package com.saphienyako.feywild.item;

import com.saphienyako.feywild.config.FeywildConfig;
import com.saphienyako.feywild.entity.ModEntities;
import com.saphienyako.feywild.entity.base.FeyBase;
import com.saphienyako.feywild.entity.base.intereface.IOwnable;
import com.saphienyako.feywild.entity.base.intereface.ISummonable;
import com.saphienyako.feywild.network.ParticleMessage;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

import static com.saphienyako.feywild.item.ModItems.*;

public class SummoningScrollItem extends Item {

    protected final MutableComponent component;
    public SummoningScrollItem(Properties pProperties, MutableComponent component) {
        super(pProperties);
        this.component = component;
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
        } else if (this.equals(SUMMONING_SCROLL_BELLSNICKEL.get())){
            return ModEntities.BELLSNICKEL.get();
        }else if (this.equals(SUMMONING_SCROLL_SPRING_TREE_ENT.get())){
            return ModEntities.SPRING_TREE_ENT.get();
        }else if (this.equals(SUMMONING_SCROLL_SUMMER_TREE_ENT.get())){
            return ModEntities.SUMMER_TREE_ENT.get();
        }else if (this.equals(SUMMONING_SCROLL_AUTUMN_TREE_ENT.get())){
            return ModEntities.AUTUMN_TREE_ENT.get();
        }else if (this.equals(SUMMONING_SCROLL_WINTER_TREE_ENT.get())){
            return ModEntities.WINTER_TREE_ENT.get();
        } else if (this.equals(SUMMONING_SCROLL_BEE_KNIGHT.get())){
            return ModEntities.BEE_MOUNT.get();
        }  else return null;

        //TODO on summon set Variant for TreeEnts
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
                    PacketDistributor.sendToPlayersTrackingEntity(
                            entity,
                            new ParticleMessage(
                                    ParticleMessage.Particles.DANDELION_FLUFF,
                                    context.getClickedPos()
                            )
                    );


                    if (FeywildConfig.voicesActive) {
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

    @Override
    public void appendHoverText(@Nonnull ItemStack stack,@Nonnull TooltipContext context,@Nonnull List<Component> tooltip,@Nonnull TooltipFlag flag) {
        if(Screen.hasShiftDown()){
            tooltip.add(this.component.withStyle(ChatFormatting.BLUE));
        }

        else {
            tooltip.add(Component.translatable("message.feywild.shift_down").withStyle(ChatFormatting.GREEN));
        }
        super.appendHoverText(stack, context, tooltip, flag);
    }
}
