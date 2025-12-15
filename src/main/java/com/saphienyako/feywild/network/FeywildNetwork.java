package com.saphienyako.feywild.network;

import com.saphienyako.feywild.Feywild;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class FeywildNetwork {
    public static final String PROTOCOL_VERSION = "1";

    public static PayloadRegistrar REGISTRAR;

    public static void register(RegisterPayloadHandlersEvent event) {
        REGISTRAR = event.registrar(Feywild.MOD_ID)
                .versioned(PROTOCOL_VERSION);

        // Client
        REGISTRAR.playToClient(
                ParticleMessage.TYPE,
                ParticleMessage.STREAM_CODEC,
                ParticleMessage::handle
        );

        REGISTRAR.playToClient(
                AltarParticleMessage.TYPE,
                AltarParticleMessage.STREAM_CODEC,
                AltarParticleMessage::handle
        );

        REGISTRAR.playToClient(
                OpenMenuMessage.TYPE,
                OpenMenuMessage.STREAM_CODEC,
                OpenMenuMessage::handle
        );


        // Server

        REGISTRAR.playToServer(
                ToggleFollowPlayerMessage.TYPE,
                ToggleFollowPlayerMessage.STREAM_CODEC,
                ToggleFollowPlayerMessage::handle
        );

        REGISTRAR.playToServer(
                ToggleAbilityMessage.TYPE,
                ToggleAbilityMessage.STREAM_CODEC,
                ToggleAbilityMessage::handle
        );

        REGISTRAR.playToServer(
                DismissEntityMessage.TYPE,
                DismissEntityMessage.STREAM_CODEC,
                DismissEntityMessage::handle
        );
    }
}
