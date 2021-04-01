package com.feywild.feywild.item;

import com.feywild.feywild.FeywildMod;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.feywild.feywild.entity.ModEntityTypes.*;
import static com.feywild.feywild.entity.ModEntityTypes.WINTER_PIXIE;

public class ModSpawnEggItem extends SpawnEggItem {

    protected static final List<ModSpawnEggItem> MOD_EGGS = new ArrayList<>();

    private final Lazy<? extends EntityType<?>> entityTypeSupplier;


    /* CONSTRUCTOR */
    public ModSpawnEggItem(final RegistryObject<? extends EntityType<?>> entitySupplier, int primaryColorIn, int secondaryColorIn, Properties builder)
    {
        super(null, primaryColorIn, secondaryColorIn, builder);
        this.entityTypeSupplier = Lazy.of(entitySupplier::get);
        MOD_EGGS.add(this); }


    public static void initSpawnEggs(){

        final Map<EntityType<?>, SpawnEggItem> EGGS =
                ObfuscationReflectionHelper.getPrivateValue(SpawnEggItem.class, null, "EGGS");

        DefaultDispenseItemBehavior dispenseItemBehavior = new DefaultDispenseItemBehavior() {

            @Override
            protected ItemStack dispenseStack(IBlockSource source, ItemStack stack)
            {
                Direction direction = source.getBlockState().get(DispenserBlock.FACING);
                EntityType type = ((SpawnEggItem) stack.getItem()).getType(stack.getTag());
                type.spawn(source.getWorld(), stack, null, source.getBlockPos(),
                        SpawnReason.DISPENSER, direction !=Direction.UP, false );

                stack.shrink(1);

                return stack;
            }
        };


        for (final SpawnEggItem spawnEgg : ModSpawnEggItem.MOD_EGGS) {

            EGGS.put(spawnEgg.getType(null), spawnEgg);
            DispenserBlock.registerDispenseBehavior(spawnEgg, dispenseItemBehavior);
        }

        ModSpawnEggItem.MOD_EGGS.clear();
    }


    @Override
    public EntityType<?> getType(CompoundNBT nbt) {

        return this.entityTypeSupplier.get();
    }


}
