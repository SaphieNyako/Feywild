package com.feywild.feywild.particles;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;

public class ModParticleFactories {
    
    public static void registerParticles(ParticleFactoryRegisterEvent event){
        Minecraft.getInstance().particleEngine.register(ModParticles.leafParticle, LeafParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ModParticles.springLeafParticle, LeafParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ModParticles.summerLeafParticle, LeafParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ModParticles.winterLeafParticle, LeafParticle.Factory::new);
    }
}
