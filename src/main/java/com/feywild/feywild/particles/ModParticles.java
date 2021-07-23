package com.feywild.feywild.particles;


import com.feywild.feywild.util.Registration;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;

public class ModParticles {


    public static RegistryObject<BasicParticleType> LEAF_PARTICLE = registrer("leaf_particle" , new BasicParticleType(true));


            public static <T extends ParticleType<?>>  RegistryObject<T> registrer(String name, T particle){
                return Registration.PARTICLES.register(name, () -> particle);
            }

            public static void register(){

            }

}
