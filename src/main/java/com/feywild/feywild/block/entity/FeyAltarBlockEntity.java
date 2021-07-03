package com.feywild.feywild.block.entity;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.network.DataMessage;
import com.feywild.feywild.network.FeywildPacketHandler;
import com.feywild.feywild.network.ParticleMessage;
import com.feywild.feywild.recipes.AltarRecipe;
import com.feywild.feywild.recipes.ModRecipeTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.NonNullList;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class FeyAltarBlockEntity extends InventoryTile implements ITickableTileEntity, IAnimatable {

    Random random = new Random();
    NonNullList<ItemStack> stackList = NonNullList.withSize(5, ItemStack.EMPTY);
    private AnimationFactory factory = new AnimationFactory(this);
    private boolean shouldLoad = true, shouldCraft = false;
    private int count = 0, limit, craftCount = 0;

    public FeyAltarBlockEntity() {
        super(ModBlocks.FEY_ALTAR_ENTITY.get());
    }

    /* DATA */
    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        for (int i = 0; i < getContainerSize(); i++) {
            stackList.set(i, ItemStack.of((CompoundNBT) nbt.get("stack" + i)));
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        for (int i = 0; i < getItems().size(); i++) {
            CompoundNBT compoundNBT = new CompoundNBT();
            stackList.get(i).copy().save(compoundNBT);
            compound.put("stack" + i, compoundNBT);
        }
        return super.save(compound);
    }

    @Override
    public void updateInventory(int flags, boolean shouldCraft) {
        if (shouldCraft) {
            Inventory inv = new Inventory(5);
            for (int i = 0; i < getItems().size(); i++) {
                inv.setItem(i, getItems().get(i));
            }

            Optional<AltarRecipe> recipe = level.getRecipeManager().getRecipeFor(ModRecipeTypes.ALTAR_RECIPE, inv, level);

            recipe.ifPresent(altarRecipe -> {
                        this.shouldCraft = true;
                    }
            );
        } else {
            super.updateInventory(flags, false);
        }
    }

    @Override
    public void tick() {
        if (level.isClientSide) return;
        count++;
        if (shouldLoad) {
            // initialize limit and loop through all items to sync them with the client
            limit = random.nextInt(20 * 6);
            updateInventory(-1, false);
            shouldLoad = true;
        }
        if (shouldCraft) {
            craftCount++;
            if (craftCount == 10) {
                FeywildPacketHandler.sendToPlayersInRange(level, worldPosition, new DataMessage(1, worldPosition), 100);
            }
            if (craftCount > 40) {
                craft();
                craftCount = 0;
                shouldCraft = false;
            }
        }
        //summon particles randomly (did this here bc for some reason random ticks are killing me today)
        if (count > limit) {
            limit = random.nextInt(20 * 6);
            if (random.nextDouble() > 0.5) {
                // send packet to player to summon particles
                FeywildPacketHandler.sendToPlayersInRange(level, worldPosition, new ParticleMessage(worldPosition.getX() + random.nextDouble(), worldPosition.getY() + random.nextDouble(), worldPosition.getZ() + random.nextDouble(), 0, 0, 0, 1, 2, 0), 32);
            }
            count = 0;
        }
    }

    public void craft() {
        Inventory inv = new Inventory(5);
        for (int i = 0; i < getItems().size(); i++) {
            inv.setItem(i, getItems().get(i));
        }

        Optional<AltarRecipe> recipe = level.getRecipeManager().getRecipeFor(ModRecipeTypes.ALTAR_RECIPE, inv, level);

        recipe.ifPresent(iRecipe -> {
            ItemStack output = iRecipe.getResultItem();
            ItemEntity entity = new ItemEntity(level, worldPosition.getX() + 0.5, worldPosition.getY() + 2, worldPosition.getZ() + 0.5, output);
            level.addFreshEntity(entity);
            clearContent();
            FeywildPacketHandler.sendToPlayersInRange(level, worldPosition, new DataMessage(0, worldPosition), 100);
            //  FeywildPacketHandler.sendToPlayersInRange(level, worldPosition, new ParticleMessage(worldPosition.getX() + 0.5, worldPosition.getY() + 1.2, worldPosition.getZ() + 0.5, -4, -2, -4, 10, 0,0), 32);
            FeywildPacketHandler.sendToPlayersInRange(level, worldPosition, new ParticleMessage(worldPosition.getX() + 0.5, worldPosition.getY() + 1.2, worldPosition.getZ() + 0.5, 0.5, 0.7, 0.5, 20, -2, 0), 64);

        });

    }

    @Override
    public int getContainerSize() {
        return 5;
    }

    @Override
    public List<ItemStack> getItems() {
        return stackList;
    }


    /* ANIMATION */

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.altar.motion", true));

        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {

        animationData.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));

    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}