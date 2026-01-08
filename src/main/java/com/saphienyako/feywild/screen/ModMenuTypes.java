package com.saphienyako.feywild.screen;

import com.saphienyako.feywild.Feywild;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class ModMenuTypes {

    public static final DeferredRegister<ContainerType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.CONTAINERS, Feywild.MOD_ID);

    public static final RegistryObject<ContainerType<FeyAltarMenu>> FEY_ALTAR_MENU =
            MENUS.register("fey_altar_menu",
                    () -> IForgeContainerType.create(FeyAltarMenu::new));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
