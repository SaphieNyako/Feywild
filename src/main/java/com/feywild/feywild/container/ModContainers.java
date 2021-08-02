package com.feywild.feywild.container;

import com.feywild.feywild.entity.ModEntityTypes;
import com.feywild.feywild.entity.SpringPixieEntity;
import com.feywild.feywild.util.Registration;
import io.github.noeppi_noeppi.libx.annotation.RegisterClass;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;

@RegisterClass
public class ModContainers {

    public static final ContainerType<PixieContainer> pixieContainer = IForgeContainerType.create((((windowId, inv, data) -> {
        SpringPixieEntity entity = new SpringPixieEntity(ModEntityTypes.springPixie, inv.player.getCommandSenderWorld());
        return new PixieContainer(windowId, inv, inv.player, entity);
    })));
}
