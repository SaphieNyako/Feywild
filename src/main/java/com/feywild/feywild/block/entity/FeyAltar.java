package com.feywild.feywild.block.entity;

import com.feywild.feywild.recipes.IAltarRecipe;
import com.feywild.feywild.recipes.ModRecipeTypes;
import com.feywild.feywild.util.StreamUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import org.apache.commons.lang3.tuple.Pair;
import org.moddingx.libx.base.tile.BlockEntityBase;
import org.moddingx.libx.base.tile.TickingBlock;
import org.moddingx.libx.capability.ItemCapabilities;
import org.moddingx.libx.inventory.BaseItemStackHandler;
import org.moddingx.libx.inventory.IAdvancedItemHandlerModifiable;
import org.moddingx.libx.util.lazy.LazyValue;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FeyAltar extends BlockEntityBase implements TickingBlock, GeoBlockEntity {

    public static final int MAX_PROGRESS = 40;

    private final BaseItemStackHandler inventory;
    private final LazyOptional<IAdvancedItemHandlerModifiable> itemHandler;
    private int progress = 0;
    private boolean needsUpdate = false;
    private LazyValue<Optional<Pair<ItemStack, IAltarRecipe>>> recipe = new LazyValue<>(Optional::empty);

    public FeyAltar(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.inventory = BaseItemStackHandler.builder(5)
                .contentsChanged(() -> {
                    this.setChanged();
                    this.updateRecipe();
                    this.setDispatchable();
                })
                .defaultSlotLimit(1)
                .build();
        this.itemHandler = ItemCapabilities.create(() -> this.inventory);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
        if (capability == ForgeCapabilities.ITEM_HANDLER) {
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
                this.updateRecipe();
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
                if (this.level.random.nextFloat() < 0.02) {
                    this.level.addParticle(ParticleTypes.END_ROD, true,
                            this.worldPosition.getX() + (Math.random()),
                            this.worldPosition.getY() + 1 + (Math.random()),
                            this.worldPosition.getZ() + (Math.random()),
                            0, 0, 0);
                }
            }
        }
    }


    private void updateRecipe() {
        if (this.level != null && !this.level.isClientSide) {
            this.recipe = new LazyValue<>(() -> {
                List<ItemStack> inputs = IntStream.range(0, this.inventory.getSlots()).mapToObj(this.inventory::getStackInSlot).filter(stack -> !stack.isEmpty()).collect(Collectors.toList());
                return this.level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.altar).stream()
                        .flatMap(r -> StreamUtil.zipOption(r.getResult(level.registryAccess(), inputs), r))
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

    private final AnimatableInstanceCache animationCache = GeckoLibUtil.createInstanceCache(this);
    
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, event -> {
            event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.fey_altar.standard"));
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animationCache;
    }
}
