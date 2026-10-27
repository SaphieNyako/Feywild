package com.saphienyako.feywild.item;

import com.saphienyako.feywild.entity.ModEntities;
import com.saphienyako.feywild.entity.SpriteEntity;
import com.saphienyako.feywild.item.base.ToolTipBaseItem;
import com.saphienyako.feywild.network.OpenLexiconMenuMessage;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;


public class FeywildLexicon extends ToolTipBaseItem {
    public FeywildLexicon(Properties pProperties) {
        super(pProperties, Component.translatable("message.feywild.feywild_lexicon"));
    }

    @Override
    public @NotNull InteractionResult use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {

        if (player instanceof ServerPlayer serverPlayer) {

            SpriteEntity entity = ModEntities.SPRITE.get().create(level, EntitySpawnReason.SPAWN_ITEM_USE);

            if (entity != null) {
                entity.setPos(player.getEyePosition());
                level.addFreshEntity(entity);
                PacketDistributor.sendToPlayer(serverPlayer, new OpenLexiconMenuMessage(entity.getId()));
            }
        }
        return InteractionResult.SUCCESS;
    }
}
