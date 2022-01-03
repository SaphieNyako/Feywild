package com.feywild.feywild.block.entity;

import com.feywild.feywild.recipes.IAltarRecipe;
import com.feywild.feywild.recipes.ModRecipeTypes;
import com.feywild.feywild.util.StreamUtil;
import io.github.noeppi_noeppi.libx.base.tile.BlockEntityBase;
import io.github.noeppi_noeppi.libx.base.tile.TickableBlock;
import io.github.noeppi_noeppi.libx.capability.ItemCapabilities;
import io.github.noeppi_noeppi.libx.inventory.BaseItemStackHandler;
import io.github.noeppi_noeppi.libx.inventory.IAdvancedItemHandlerModifiable;
import io.github.noeppi_noeppi.libx.util.LazyValue;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import org.apache.commons.lang3.tuple.Pair;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FeyAltar extends BlockEntityBase implements TickableBlock, IAnimatable {

    public static final int MAX_PROGRESS = 40;

    private final BaseItemStackHandler inventory;
    private final LazyOptional<IAdvancedItemHandlerModifiable> itemHandler;
    private final AnimationFactory animationFactory = new AnimationFactory(this);
    private int progress = 0;
    private int particleTimer = 0;
    private boolean needsUpdate = false;
    private LazyValue<Optional<Pair<ItemStack, IAltarRecipe>>> recipe = new LazyValue<>(Optional::empty);

    public FeyAltar(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.inventory = BaseItemStackHandler.builder(5)
                .contentsChanged(() -> {
                    this.setChanged();
                    this.updabeRecipe();
                    this.setDispatchable();
                })
                .defaultSlotLimit(1)
                .build();
        this.itemHandler = ItemCapabilities.create(() -> this.inventory);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return this.itemHandler.cast();
        } else {
            return super.getCapability(capability, side);
        }
    }

    @Override
    public void tick() {
        if (this.level == null) return;
        if (!this.level.isClientSide) {
            if (this.needsUpdate) {
                this.updabeRecipe();
                this.needsUpdate = false;
            }
            if (this.recipe.get().isPresent()) {
                // We need to save the result before changing the inventory
                // as changing the inventory will invalidate the result.
                Pair<ItemStack, IAltarRecipe> currentRecipe = this.recipe.get().get();
                this.progress++;
                if (this.progress >= MAX_PROGRESS) {
                    // Clear the inventory
                    for (int slot = 0; slot < this.inventory.getSlots(); slot++) {
                        this.inventory.setStackInSlot(slot, ItemStack.EMPTY);
                    }
                    ItemEntity entity = new ItemEntity(this.level, this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 2, this.worldPosition.getZ() + 0.5, currentRecipe.getLeft().copy());
                    this.level.addFreshEntity(entity);
                    this.progress = 0;
                }
                this.setChanged();
                this.setDispatchable();
            } else {
                this.progress = 0;
            }
        } else {
            if (this.progress > 0) {
                if (this.progress >= (MAX_PROGRESS - 1)) {
                    for (int i = 0; i < 20; i++) {
                        this.level.addParticle(ParticleTypes.END_ROD, true, this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 1.2, this.worldPosition.getZ() + 0.5, 0.5 - this.level.random.nextDouble(), 0.7 - this.level.random.nextDouble(), 0.5 - this.level.random.nextDouble());
                    }
                    this.progress = 0;
                } else {
                    List<ItemStack> stacks = new ArrayList<>();
                    for (int slot = 0; slot < this.getInventory().getSlots(); slot++) {
                        ItemStack stack = this.getInventory().getStackInSlot(slot);
                        if (!stack.isEmpty()) stacks.add(stack);
                    }
                    if (!stacks.isEmpty()) {
                        double progressScaled = this.progress / (double) FeyAltar.MAX_PROGRESS;
                        double anglePerStack = (2 * Math.PI) / stacks.size();
                        for (int idx = 0; idx < stacks.size(); idx++) {
                            double shiftX = Math.cos((this.level.getGameTime() / (double) 8) + (idx * anglePerStack)) * (1 - progressScaled);
                            double shiftZ = Math.sin((this.level.getGameTime() / (double) 8) + (idx * anglePerStack)) * (1 - progressScaled);
                            this.level.addParticle(ParticleTypes.END_ROD, true, this.worldPosition.getX() + 0.5 + shiftX, this.worldPosition.getY() + 1 + progressScaled, this.worldPosition.getZ() + 0.5 + shiftZ, 0, 0, 0);
                        }
                    }
                }
            } else {
                if (this.particleTimer <= 0) {
                    this.particleTimer = this.level.random.nextInt(120);
                    if (this.level.random.nextFloat() < 0.5) {
                        this.level.addParticle(ParticleTypes.END_ROD, true, this.worldPosition.getX() + this.level.random.nextDouble(), this.worldPosition.getY() + this.level.random.nextDouble(), this.worldPosition.getZ() + this.level.random.nextDouble(), 0, 0, 0);
                    }
                }
            }
        }
    }

    private void updabeRecipe() {
        if (this.level != null && !this.level.isClientSide) {
            this.recipe = new LazyValue<>(() -> {
                List<ItemStack> inputs = IntStream.range(0, this.inventory.getSlots()).mapToObj(this.inventory::getStackInSlot).filter(stack -> !stack.isEmpty()).collect(Collectors.toList());
                return this.level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.ALTAR).stream()
                        .flatMap(r -> StreamUtil.zipOption(r.getResult(inputs), r))
                        .findFirst();
            });
        } else {
            this.recipe = new LazyValue<>(Optional::empty);
        }
    }

    public BaseItemStackHandler getInventory() {
        return this.inventory;
    }

    public int getProgress() {
        return this.progress;
    }

    @Override
    public void saveAdditional(@Nonnull CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.put("inventory", this.inventory.serializeNBT());
        nbt.putInt("progress", this.progress);
    }

    @Override
    public void load(@Nonnull CompoundTag nbt) {
        super.load(nbt);
        this.inventory.deserializeNBT(nbt.getCompound("inventory"));
        this.progress = nbt.getInt("progress");
        this.needsUpdate = true;
    }

    @Nonnull
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = super.getUpdateTag();
        if (this.level != null && !this.level.isClientSide) {
            nbt.put("inventory", this.inventory.serializeNBT());
            nbt.putInt("progress", this.progress);
        }
        return nbt;
    }

    @Override
    public void handleUpdateTag(CompoundTag nbt) {
        super.handleUpdateTag(nbt);
        if (this.level != null && this.level.isClientSide) {
            this.inventory.deserializeNBT(nbt.getCompound("inventory"));
            this.progress = nbt.getInt("progress");
        }
    }

    private <E extends IAnimatable> PlayState animationPredicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.altar.motion", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::animationPredicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.animationFactory;
    }
}
