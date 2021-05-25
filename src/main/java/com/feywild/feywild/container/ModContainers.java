package com.feywild.feywild.container;

import com.feywild.feywild.util.Registration;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;

public class ModContainers {

    public static final RegistryObject<ContainerType<DwarvenAnvilContainer>> DWARVEN_ANVIL_CONTAINER
            = Registration.CONTAINERS.register("dwarven_anvil_container",
            () -> IForgeContainerType.create((((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                World world = inv.player.getCommandSenderWorld();
                return new DwarvenAnvilContainer(windowId, world, pos, inv, inv.player);
            }))));

    public static void register() {}

}
