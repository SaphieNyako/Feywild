package com.feywild.feywild.events;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.events.loot_modifiers.MineshaftChestLootModifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = "feywild", bus = Mod.EventBusSubscriber.Bus.MOD)
public class LootModifierEvent {

    @SubscribeEvent
    public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>>
                                                           event) {
        event.getRegistry().registerAll(
                new MineshaftChestLootModifier.Serializer().setRegistryName(
                        new ResourceLocation(FeywildMod.getInstance().modid, "mineshaft_chest_additions")
                )
        );
    }

}
