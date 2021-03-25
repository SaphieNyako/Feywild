package com.feywild.feywild.block.entity;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.events.ModRecipes;
import com.feywild.feywild.network.FeywildPacketHandler;
import com.feywild.feywild.network.ItemMessage;
import com.feywild.feywild.network.ParticleMessage;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.network.PacketDistributor;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.*;

public class FeyAltarBlockEntity extends InventoryTile implements ITickableTileEntity, IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);

    private boolean shouldLoad = true;
    private int count = 0, limit;

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
    public void updateInventory(int flags) {
        if(flags != -1) {
            craft();
        }else
        super.updateInventory(flags);
    }

    //gets called every tick
    @Override
    public void tick() {
        if(world.isRemote) return;
        count++;
        if(shouldLoad){
            // initialize limit and loop through all items to sync them with the client
            limit = random.nextInt(20*6);
            updateInventory(-1);
            shouldLoad = true;
        }
        //summon particles randomly (did this here bc for some reason random ticks are killing me today)
        if(count > limit){
            limit = random.nextInt(20*6);
            if(random.nextDouble() > 0.5) {
                // send packet to player to summon particles
                FeywildPacketHandler.sendToPlayersInRange(world, pos, new ParticleMessage(pos.getX()+ random.nextDouble(),pos.getY()+ random.nextDouble(), pos.getZ()+ random.nextDouble(), 0, 0, 0, 1), 32);
            }
            count = 0;
        }
    }

    //frequency sensitive
    public void craft(){
        List<String> recipe = new LinkedList<>(), items = new LinkedList<>();
        ModRecipes.getAltarRecipes().keySet().forEach(itemStacks -> {
            for(ItemStack stack: stackList){
                items.add(stack.copy().toString());
            }
            for(ItemStack stack: itemStacks){
                recipe.add(stack.copy().toString());
            }
            Collections.sort(items);
            Collections.sort(recipe);
            if(items.equals(recipe)) {
                world.addEntity(new ItemEntity(world,pos.getX()+0.5,pos.getY()+1.15,pos.getZ()+0.5,ModRecipes.getAltarRecipes().get(itemStacks).copy()));
                FeywildPacketHandler.sendToPlayersInRange(world,pos,new ParticleMessage(pos.getX()+0.5,pos.getY()+1.2,pos.getZ()+0.5,-4,-2,-4,10),32);
                clear();
            }

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