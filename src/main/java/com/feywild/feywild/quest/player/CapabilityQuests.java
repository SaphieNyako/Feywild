package com.feywild.feywild.quest.player;

import com.feywild.feywild.FeywildMod;
import io.github.noeppi_noeppi.libx.util.LazyValue;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityQuests {

    public static final ResourceLocation KEY = new ResourceLocation(FeywildMod.getInstance().modid, "player_quests");

    public static final Capability<QuestData> QUESTS = CapabilityManager.get(new CapabilityToken<>() {
    });

    public static void register(RegisterCapabilitiesEvent event) {
        event.register(QuestData.class);
    }

    public static void attachPlayerCaps(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof ServerPlayer player) {
            event.addCapability(KEY, new Provider(QUESTS, new LazyValue<>(() -> {
                QuestData data = new QuestData();
                data.attach(player);
                return data;
            })));
        }
    }

    @SuppressWarnings("CodeBlock2Expr")
    public static void playerCopy(PlayerEvent.Clone event) {
        // Keep the data on death
        Player oldData = event.getOriginal();
        Player newData = event.getPlayer();

        if (event.isWasDeath()) {
            oldData.reviveCaps();
            newData.getCapability(CapabilityQuests.QUESTS)
                    .orElseThrow(RuntimeException::new)
                    .setAlignment(oldData.getCapability(CapabilityQuests.QUESTS).orElseThrow(RuntimeException::new).getAlignment());
            newData.getCapability(CapabilityQuests.QUESTS)
                    .orElseThrow(RuntimeException::new)
                    .setReputation(oldData.getCapability(CapabilityQuests.QUESTS).orElseThrow(RuntimeException::new).getReputation());
            newData.getCapability(CapabilityQuests.QUESTS)
                    .orElseThrow(RuntimeException::new)
                    .setPendingCompletion(oldData.getCapability(CapabilityQuests.QUESTS).orElseThrow(RuntimeException::new).getPendingCompletion());
            newData.getCapability(CapabilityQuests.QUESTS)
                    .orElseThrow(RuntimeException::new)
                    .setActiveQuests(oldData.getCapability(CapabilityQuests.QUESTS).orElseThrow(RuntimeException::new).getActiveQuests());
            newData.getCapability(CapabilityQuests.QUESTS)
                    .orElseThrow(RuntimeException::new)
                    .setCompletedQuests(oldData.getCapability(CapabilityQuests.QUESTS).orElseThrow(RuntimeException::new).getCompletedQuests());
            oldData.invalidateCaps();
        }
    }

    private static class Provider implements ICapabilityProvider, INBTSerializable<Tag> {

        public final Capability<QuestData> capability;
        public final LazyValue<QuestData> value;

        public Provider(Capability<QuestData> capability, LazyValue<QuestData> value) {
            this.capability = capability;
            this.value = value;
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            //noinspection NullableProblems
            return cap == this.capability ? LazyOptional.of(this.value::get).cast() : LazyOptional.empty();
        }

        @Override
        public Tag serializeNBT() {
            return this.value.get().write();
        }

        @Override
        public void deserializeNBT(Tag nbt) {
            if (nbt instanceof CompoundTag tag) {
                this.value.get().read(tag);
            }
        }
    }
}
