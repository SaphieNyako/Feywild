package com.feywild.feywild.block;

import com.feywild.feywild.block.entity.FeyAltarBlockEntity;
import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.network.FeywildPacketHandler;
import com.feywild.feywild.network.ItemMessage;
import com.feywild.feywild.network.ParticleMessage;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.*;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.lwjgl.system.CallbackI;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class FeyAltar extends Block {
Random random = new Random();

     //Constructor
    public FeyAltar() {

        super(AbstractBlock.Properties.of(Material.STONE).strength(0f).noOcclusion().requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE));
    }

    //Activate on player r click
    @SuppressWarnings("all")
    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        //Server check
        if (!worldIn.isClientSide && worldIn.getBlockEntity(pos) instanceof FeyAltarBlockEntity) {
            //Store data that might get reused
            ItemStack stack = player.getItemInHand(handIn);
            FeyAltarBlockEntity entity = (FeyAltarBlockEntity) worldIn.getBlockEntity(pos);
            int flagStack = -1;
            //Remove item from inventory
            if (player.isShiftKeyDown()) {
                for (int i = entity.getContainerSize()-1; i > -1; i--) {
                    if (!entity.getItem(i).isEmpty()) {
                        player.addItem(entity.getItem(i));
                        entity.setItem(i, ItemStack.EMPTY);
                        flagStack = i;
                        break;
                    }
                }
            } else
                //Add item to inventory if player is NOT sneaking and is holding an item
                if (!stack.isEmpty()) {
                    for (int i = 0; i < entity.getContainerSize(); i++) {
                        if (entity.getItem(i).isEmpty()) {
                            entity.setItem(i, new ItemStack(stack.getItem(), 1));
                            player.getItemInHand(handIn).shrink(1);
                            flagStack = i;
                            break;
                        }
                    }
                }
                //Format and send item data to client
               entity.updateInventory(flagStack,true);
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void playerWillDestroy(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if(worldIn.isClientSide){
            worldIn.playSound(player,pos, SoundEvents.STONE_BREAK,SoundCategory.BLOCKS,1,1);
            for (int i = 0; i < 20; i++) {
                worldIn.addParticle(ParticleTypes.POOF, pos.getX()+0.5,pos.getY()+0.5,pos.getZ()+ 0.5, (random.nextDouble() - 0.5 )/10,(random.nextDouble() - 0.5 )/10,(random.nextDouble() - 0.5 )/10);
            }
        }else
        if(worldIn.getBlockEntity(pos) instanceof FeyAltarBlockEntity) {
            ItemEntity entity;
            for (ItemStack stack:((FeyAltarBlockEntity) Objects.requireNonNull(worldIn.getBlockEntity(pos))).getItems()) {
                entity = new ItemEntity( worldIn,pos.getX(),pos.getY(),pos.getZ(),stack);
                worldIn.addFreshEntity(entity);
            }
        }
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new FeyAltarBlockEntity();
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state)
    {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

}
