package com.saphienyako.feywild.screen;

import com.saphienyako.feywild.Feywild;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

public class ModMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, Feywild.MOD_ID);

    public static final DeferredHolder<MenuType<?>, @NotNull MenuType<@NotNull FeyAltarMenu>> FEY_ALTAR_MENU =
            registerMenuType("fey_altar_menu", FeyAltarMenu::create);

    public static final DeferredHolder<MenuType<?>, @NotNull MenuType<@NotNull BellsnickelMenu>> BELLSNICKEL_MENU =
            registerMenuType("bellsnickel_menu", BellsnickelMenu::create);

    public static final DeferredHolder<MenuType<?>, @NotNull MenuType<@NotNull BeeKnightMenu>> BEE_KNIGHT_MENU =
            registerMenuType("bee_knight_menu", BeeKnightMenu::create);

    private static <T extends AbstractContainerMenu>DeferredHolder<MenuType<?>, @NotNull MenuType<@NotNull T>> registerMenuType(String name, IContainerFactory<@NotNull T> factory) {
        return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
