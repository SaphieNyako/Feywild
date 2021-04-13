package com.feywild.feywild.block.entity;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.network.FeywildPacketHandler;
import com.feywild.feywild.network.ParticleMessage;
import com.feywild.feywild.recipes.AltarRecipe;
import com.feywild.feywild.recipes.FeywildRecipes;
import com.feywild.feywild.recipes.IAltarRecipe;
import com.feywild.feywild.recipes.ModRecipeTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JsonAppend;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class FeyAltarBlockEntity extends InventoryTile implements ITickableTileEntity, IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);

    private boolean shouldLoad = true;
    private int count = 0, limit;
    Inventory inv = new Inventory(5);
    Random random = new Random();
    //Items
    NonNullList<ItemStack> stackList = NonNullList.withSize(5, ItemStack.EMPTY);

    public FeyAltarBlockEntity() {
        super(ModBlocks.FEY_ALTAR_ENTITY.get());
    }


    //Read data on world init
    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        for(int i =0; i < getSizeInventory(); i++){
            stackList.set(i,ItemStack.read((CompoundNBT) nbt.get("stacks")));
        }
        super.read(state, nbt);
    }

    //Save data on world close
    @Override
    public CompoundNBT write(CompoundNBT compound) {
        CompoundNBT compoundNBT = new CompoundNBT();
        for (int i = 0; i < getSizeInventory(); i++) {
            stackList.get(i).write(compoundNBT);
        }
        compound.put("stacks",compoundNBT);
        return super.write(compound);
    }

    @Override
    public void updateInventory(int flags, boolean shouldCraft) {
        if(shouldCraft) {
            craft();
        }
        super.updateInventory(flags,false);
    }

    //gets called every tick
    @Override
    public void tick() {
        if(world.isRemote) return;
        count++;
        if(shouldLoad){
            // initialize limit and loop through all items to sync them with the client
            limit = random.nextInt(20*6);
            updateInventory(-1, false);
            shouldLoad = true;
        }
        //summon particles randomly (did this here bc for some reason random ticks are killing me today)
        if(count > limit){
            limit = random.nextInt(20*6);
            if(random.nextDouble() > 0.5) {
                // send packet to player to summon particles
                FeywildPacketHandler.sendToPlayersInRange(world, pos, new ParticleMessage(pos.getX()+ random.nextDouble(),pos.getY()+ random.nextDouble(), pos.getZ()+ random.nextDouble(), 0, 0, 0, 1,2), 32);
            }
            count = 0;
        }
    }

    public void craft(){
       inv.clear();
        for(int i = 0; i < getItems().size(); i++){
            inv.setInventorySlotContents(i, getItems().get(i));
        }

       Optional<AltarRecipe> recipe = world.getRecipeManager().getRecipe(ModRecipeTypes.ALTAR_RECIPE, inv, world);

       recipe.ifPresent(iRecipe -> {
           ItemStack output = iRecipe.getRecipeOutput();
           ItemEntity entity = new ItemEntity(world, pos.getX() + 0.5, pos.getY()+1.1, pos.getZ()+0.5, output);
           world.addEntity(entity);
           this.clear();
           FeywildPacketHandler.sendToPlayersInRange(world, pos, new ParticleMessage(pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5, -4, -2, -4, 10, 0), 32);
       });


    }

    @Override
    public int getSizeInventory() {
        return 5;
    }

    @Override
    public List<ItemStack> getItems() {
        return stackList;
    }


    /* ANIMATION */

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
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