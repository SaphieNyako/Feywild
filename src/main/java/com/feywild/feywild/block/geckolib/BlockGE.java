package com.feywild.feywild.block.geckolib;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.registries.ForgeRegistries;
import org.moddingx.libx.base.BlockBase;
import org.moddingx.libx.base.tile.GameEventBlock;
import org.moddingx.libx.base.tile.TickingBlock;
import org.moddingx.libx.mod.ModX;
import org.moddingx.libx.registration.RegistrationContext;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.lang.reflect.Constructor;
import java.util.Set;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import org.moddingx.libx.registration.Registerable.EntryCollector;
import org.moddingx.libx.registration.Registerable.TrackingCollector;

public class BlockGE<T extends BlockEntity> extends BlockBase implements EntityBlock {

    //Geckolib Entity Class, use this instead of BlockBE when using geckolib. Copy of LibX' BlockBE

    private final Class<T> geClass;
    private final Constructor<T> geConstructor;
    private final BlockEntityType<T> geType;

    public BlockGE(ModX mod, Class<T> geClass, Properties properties) {
        this(mod, geClass, properties, null);
    }

    public BlockGE(ModX mod, Class<T> geClass, Properties properties, @javax.annotation.Nullable Item.Properties itemProperties) {
        super(mod, properties, itemProperties);
        this.geClass = geClass;

        try {
            this.geConstructor = geClass.getConstructor(BlockEntityType.class, BlockPos.class, BlockState.class);
        } catch (ReflectiveOperationException e) {
            if (e.getCause() != null)
                e.getCause().printStackTrace();
            throw new RuntimeException("Could not get constructor for block entity " + geClass + ".", e);
        }
        //noinspection ConstantConditions
        this.geType = new BlockEntityType<>((pos, state) -> {
            try {
                return this.geConstructor.newInstance(this.getBlockEntityType(), pos, state);
            } catch (ReflectiveOperationException e) {
                if (e.getCause() != null)
                    e.getCause().printStackTrace();
                throw new RuntimeException("Could not create BlockEntity of type " + geClass + ".", e);
            }
        }, Set.of(this), null);
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void registerAdditional(RegistrationContext ctx, EntryCollector builder) {
        super.registerAdditional(ctx, builder);
        builder.register(Registry.BLOCK_ENTITY_TYPE, this.geType);
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void initTracking(RegistrationContext ctx, TrackingCollector builder) throws ReflectiveOperationException {
        super.initTracking(ctx, builder);
        builder.track(ForgeRegistries.BLOCK_ENTITY_TYPES, BlockGE.class.getDeclaredField("geType"));
    }

    @javax.annotation.Nullable
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return this.geType.create(pos, state);
    }

    @javax.annotation.Nullable
    @Override
    public <X extends BlockEntity> BlockEntityTicker<X> getTicker(@Nonnull Level level, @Nonnull BlockState state, @Nonnull BlockEntityType<X> beType) {
        if (this.geType.isValid(state) && TickingBlock.class.isAssignableFrom(this.geClass)) {
            //noinspection Convert2Lambda
            return new BlockEntityTicker<>() {

                @Override
                public void tick(@Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull X blockEntity) {
                    if (blockEntity instanceof TickingBlock ticking) {
                        ticking.tick();
                    }
                }
            };
        }
        return null;
    }

    @javax.annotation.Nullable
    @Override
    public <X extends BlockEntity> GameEventListener getListener(@Nonnull ServerLevel level, @Nonnull X blockEntity) {
        if (blockEntity instanceof GameEventBlock eventBlock) {
            PositionSource source = new BlockPositionSource(blockEntity.getBlockPos());
            return new GameEventListener() {

                @Nonnull
                @Override
                public PositionSource getListenerSource() {
                    return source;
                }

                @Override
                public int getListenerRadius() {
                    return eventBlock.gameEventRange();
                }

                @Override
                public boolean handleGameEvent(@Nonnull ServerLevel level, @Nonnull GameEvent.Message message) {
                    return eventBlock.notifyGameEvent(level, message);
                }
            };
        } else {
            return null;
        }
    }

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (!level.isClientSide && (!state.is(newState.getBlock()) || !newState.hasBlockEntity()) && this.shouldDropInventory(level, pos, state)) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be != null) {
                be.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(handler -> {
                    if (handler instanceof IItemHandlerModifiable modifiable) {
                        for (int i = 0; i < modifiable.getSlots(); i++) {
                            ItemStack stack = modifiable.getStackInSlot(i);
                            ItemEntity entity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5, stack.copy());
                            level.addFreshEntity(entity);
                            modifiable.setStackInSlot(i, ItemStack.EMPTY);
                        }
                    }
                });
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    public T getBlockEntity(Level level, BlockPos pos) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be == null || !this.geClass.isAssignableFrom(be.getClass())) {
            throw new IllegalStateException("Expected a block entity of type " + this.geClass + " at " + level + " " + pos + ", got" + be);
        }
        //noinspection unchecked
        return (T) be;
    }

    public BlockEntityType<T> getBlockEntityType() {
        return this.geType;
    }

    protected boolean shouldDropInventory(Level level, BlockPos pos, BlockState state) {
        return true;
    }
}
