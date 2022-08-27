package com.feywild.feywild.compat;

import com.feywild.feywild.FeywildMod;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.util.List;

public class JeiRestarter {

    public static void restart() {
        try {
            Class<?> jeiListenerClass = Class.forName("mezz.jei.forge.startup.StartEventObserver");
            if (Minecraft.getInstance().getResourceManager() instanceof ReloadableResourceManager rm) {
                List<PreparableReloadListener> listeners = ObfuscationReflectionHelper.getPrivateValue(ReloadableResourceManager.class, rm, "f_203816_");
                //noinspection ConstantConditions
                for (PreparableReloadListener listener : listeners) {
                    if (listener instanceof ResourceManagerReloadListener rll && jeiListenerClass.isAssignableFrom(listener.getClass())) {
                        rll.onResourceManagerReload(rm);
                    }
                }
            }
        } catch (Throwable t) {
            FeywildMod.logger.warn("Failed to restart JEI", t);
        }
    }
}