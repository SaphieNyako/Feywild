package com.feywild.feywild.container;

import com.feywild.feywild.entity.ModEntityTypes;
import com.feywild.feywild.entity.SpringPixieEntity;
import com.feywild.feywild.util.Registration;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;

public class ModContainers {

    // TODO I'm not sure about how the defferred register stuff works but I think this could break
    // if classes are loaded in a different order.
    // needs testing tough
    public static final RegistryObject<ContainerType<DwarvenAnvilContainer>> DWARVEN_ANVIL_CONTAINER
            = Registration.CONTAINERS.register("dwarven_anvil_container",
            () -> IForgeContainerType.create((((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                World world = inv.player.getCommandSenderWorld();
                return new DwarvenAnvilContainer(windowId, world, pos, inv, inv.player);
            }))));

    public static final RegistryObject<ContainerType<PixieContainer>> PIXIE_CONTAINER
            = Registration.CONTAINERS.register("pixie_container",
            () -> IForgeContainerType.create((((windowId, inv, data) -> {
                SpringPixieEntity entity = new SpringPixieEntity(ModEntityTypes.SPRING_PIXIE.get(), inv.player.getCommandSenderWorld());
                return new PixieContainer(windowId, inv, inv.player, entity);
            }))));

    public static void register() {
    }
}
