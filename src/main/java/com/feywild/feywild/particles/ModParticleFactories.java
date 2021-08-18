package com.feywild.feywild.particles;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

// TODO
@Mod.EventBusSubscriber(modid = "feywild", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModParticleFactories {
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void registerParticles(ParticleFactoryRegisterEvent event){
        Minecraft.getInstance().particleEngine.register(ModParticles.leafParticle, LeafParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ModParticles.springLeafParticle, LeafParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ModParticles.summerLeafParticle, LeafParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ModParticles.winterLeafParticle, LeafParticle.Factory::new);
    }
}
