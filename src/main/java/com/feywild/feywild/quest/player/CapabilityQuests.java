package com.feywild.feywild.quest.player;

import com.feywild.feywild.FeywildMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.LazyValue;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityQuests {
    
    public static final ResourceLocation KEY = new ResourceLocation(FeywildMod.getInstance().modid, "player_quests");
    
    @CapabilityInject(QuestData.class)
    public static Capability<QuestData> QUESTS = null;

    public static void register() {

        CapabilityManager.INSTANCE.register(QuestData.class, new Capability.IStorage<QuestData>() {
            
            @Nullable
            @Override
            public INBT writeNBT(Capability<QuestData> capability, QuestData instance, Direction side) {
                return instance.write();
            }

            @Override
            public void readNBT(Capability<QuestData> capability, QuestData instance, Direction side, INBT nbt) {
                if (nbt instanceof CompoundNBT) {
                    instance.read((CompoundNBT) nbt);
                }
            }
        }, QuestData::new);
    }

    public static void attachPlayerCaps(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.getObject();
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
        if (event.isWasDeath()) {
            event.getOriginal().getCapability(QUESTS).ifPresent(oldData -> {
                event.getPlayer().getCapability(QUESTS).ifPresent(newData -> {
                    newData.read(oldData.write());
                });
            });
        }
    }
    
    private static class Provider implements ICapabilityProvider, INBTSerializable<INBT> {
        
        public final Capability<?> capability;
        public final LazyValue<?> value;

        public <T> Provider(Capability<T> capability, LazyValue<? extends T> value) {
            this.capability = capability;
            this.value = value;
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return cap == this.capability ? LazyOptional.of(this.value::get).cast() : LazyOptional.empty();
        }

        @Override
        public INBT serializeNBT() {
            //noinspection unchecked
            return ((Capability<Object>) this.capability).writeNBT(this.value.get(), null);
        }

        @Override
        public void deserializeNBT(INBT nbt) {
            //noinspection unchecked
            ((Capability<Object>) this.capability).readNBT(this.value.get(), null, nbt);
        }
    }
}
