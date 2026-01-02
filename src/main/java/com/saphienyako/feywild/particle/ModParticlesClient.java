package com.saphienyako.feywild.particle;

import com.saphienyako.feywild.Feywild;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = Feywild.MOD_ID,
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT)
public class ModParticlesClient {

    @SubscribeEvent
    public static void registerParticles(ParticleFactoryRegisterEvent event) {

        Minecraft minecraft = Minecraft.getInstance();

        minecraft.particleEngine.register(
                ModParticles.SPRING_SPARKLE_PARTICLE.get(),
                SparkleParticle.provider(0, 1, 0)
        );

        minecraft.particleEngine.register(
                ModParticles.SUMMER_SPARKLE_PARTICLE.get(),
                SparkleParticle.provider(1, 0.8f, 0)
        );

        minecraft.particleEngine.register(
                ModParticles.AUTUMN_SPARKLE_PARTICLE.get(),
                SparkleParticle.provider(1, 0.4f, 0)
        );

        minecraft.particleEngine.register(
                ModParticles.WINTER_SPARKLE_PARTICLE.get(),
                SparkleParticle.provider(0.2f, 0.8f, 0.9f)
        );

        minecraft.particleEngine.register(
                ModParticles.FEY_SPARKLE_PARTICLE.get(),
                SparkleParticle.provider(0.3f, 0.9f, 0.9f)
        );

        minecraft.particleEngine.register(
                ModParticles.AUTUMN_LEAF_PARTICLE.get(),
                LeafParticle.Factory::new
        );
    }

}
