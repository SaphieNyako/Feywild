package com.feywild.feywild.block.trees;

import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.ForgeRegistries;
import org.moddingx.libx.base.BlockBase;
import org.moddingx.libx.mod.ModX;
import org.moddingx.libx.registration.Registerable;
import org.moddingx.libx.registration.RegistrationContext;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.function.Consumer;

public class LeavesBlockBase extends LeavesBlock implements Registerable {

    protected final ModX mod;

    @Nullable
    private final Item item;


    public LeavesBlockBase(ModX mod, Properties properties) {
        this(mod, properties, new Item.Properties());
    }

    public LeavesBlockBase(ModX mod, Properties properties, @Nullable Item.Properties itemProperties) {
        super(properties);
        this.mod = mod;
        if (itemProperties == null) {
            this.item = null;
        } else {
            if (mod.tab != null) {
                itemProperties.tab(mod.tab);
            }

            this.item = new BlockItem(this, itemProperties) {

                @Override
                public void initializeClient(@Nonnull Consumer<IClientItemExtensions> consumer) {
                    LeavesBlockBase.this.initializeItemClient(consumer);
                }
            };
        }
    }

    public void initializeItemClient(@Nonnull Consumer<IClientItemExtensions> consumer) {

    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void registerAdditional(RegistrationContext ctx, EntryCollector builder) {
        if (this.item != null) {
            builder.register(Registry.ITEM_REGISTRY, this.item);
        }
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void initTracking(RegistrationContext ctx, TrackingCollector builder) throws ReflectiveOperationException {
        builder.track(ForgeRegistries.ITEMS, BlockBase.class.getDeclaredField("item"));
    }
}
