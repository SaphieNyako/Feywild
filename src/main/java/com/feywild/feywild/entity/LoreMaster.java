package com.feywild.feywild.entity;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.network.LibraryScreenMessage;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.quest.task.AnimalPetTask;
import com.feywild.feywild.util.LibraryBooks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.Objects;

public class LoreMaster extends Villager {

    public LoreMaster(EntityType<? extends Villager> entityType, Level level) {
        super(entityType, level);
    }

    public static boolean canSpawn(EntityType<? extends LoreMaster> entityType, LevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return Objects.requireNonNull(ForgeRegistries.BLOCKS.tags()).getTag(BlockTags.DIRT).contains(level.getBlockState(pos.below()).getBlock()) || Objects.requireNonNull(ForgeRegistries.BLOCKS.tags()).getTag(BlockTags.SAND).contains(level.getBlockState(pos.below()).getBlock());

    }

    @Nonnull
    @Override
    public InteractionResult interactAt(@Nonnull Player player, @Nonnull Vec3 vec, @Nonnull InteractionHand hand) {
        InteractionResult superResult = super.interactAt(player, vec, hand);
        Level level = player.level();
        if (superResult == InteractionResult.PASS) {
            if (!level.isClientSide && player instanceof ServerPlayer) {
                QuestData.get((ServerPlayer) player).checkComplete(AnimalPetTask.INSTANCE, this);
                player.sendSystemMessage(Component.translatable("librarian.feywild.initial"));
                FeywildMod.getNetwork().channel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new LibraryScreenMessage(this.getDisplayName(), LibraryBooks.getLibraryBooks()));
                player.swing(hand, true);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }
}
