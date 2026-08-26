package com.saphienyako.feywild.item;

import com.saphienyako.feywild.entity.ModEntities;
import com.saphienyako.feywild.entity.OberonEntity;
import com.saphienyako.feywild.entity.base.BossBase;
import com.saphienyako.feywild.network.FeywildNetwork;
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
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

import static com.saphienyako.feywild.item.ModItems.*;

public class BossSummoningScrollItem extends Item {

    protected final MutableComponent component;
    public BossSummoningScrollItem(Properties pProperties, MutableComponent component) {
        super(pProperties);
        this.component = component;
    }


    protected void prepareEntity(LivingEntity entity, @Nonnull UseOnContext context) {
        entity.setPos(context.getClickedPos().getX(), context.getClickedPos().getY() + 1, context.getClickedPos().getZ());
    }

    protected EntityType<? extends BossBase> returnLivingEntity(){
        if(this.equals(SUMMONING_SCROLL_QUEEN_TITANIA.get())){
            return ModEntities.TITANIA.get();
        } else if (this.equals(SUMMONING_SCROLL_QUEEN_MAB.get())) {
            return ModEntities.MAB.get();
        } else if (this.equals(SUMMONING_SCROLL_OBERON.get())) {
            return ModEntities.OBERON.get();
        } else if (this.equals(SUMMONING_SCROLL_ASHEN_LORD.get())) {
            return ModEntities.ASHEN_LORD.get();
        }
        else return null;
    }

    @Nonnull
    @Override
    public InteractionResult useOn(@Nonnull UseOnContext context) {
        if (context.getPlayer() != null) {
            if (!context.getLevel().isClientSide) {

                BossBase entity = returnLivingEntity().create(context.getLevel());

                if (entity != null) {
                    this.prepareEntity(entity, context);

                    context.getLevel().addFreshEntity(entity);
                    FeywildNetwork.sendParticles(context.getLevel(), ParticleMessage.Type.DANDELION_FLUFF, context.getClickedPos());

                    if (!context.getPlayer().isCreative()) {
                        context.getItemInHand().shrink(1);
                    }

                    if(entity instanceof OberonEntity) {
                        context.getLevel().playSound(
                                null,
                                entity.blockPosition(),
                                entity.getSummonSound(),
                                SoundSource.NEUTRAL,
                                1.0F,
                                1.0F
                        );
                    }
                }
            }
            return InteractionResult.sidedSuccess(context.getLevel().isClientSide);
        }
        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level pLevel, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        if(Screen.hasShiftDown()){
            tooltip.add(this.component.withStyle(ChatFormatting.BLUE));
        }

        else {
            tooltip.add(Component.translatable("message.feywild.shift_down").withStyle(ChatFormatting.GREEN));
            tooltip.add(Component.translatable("message.feywild.boss_fight").withStyle(ChatFormatting.RED));
        }
        super.appendHoverText(stack, pLevel, tooltip, flag);
    }
}