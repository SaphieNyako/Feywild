package com.feywild.feywild.block;

import com.feywild.feywild.block.entity.FeyAltar;
import com.feywild.feywild.block.render.FeyAltarRenderer;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.BlockTE;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class FeyAltarBlock extends BlockTE<FeyAltar> {

    public FeyAltarBlock(ModX mod) {
        super(mod, FeyAltar.class, AbstractBlock.Properties.of(Material.STONE).strength(0f).noOcclusion());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void registerClient(ResourceLocation id, Consumer<Runnable> defer) {
        ClientRegistry.bindTileEntityRenderer(this.getTileType(), FeyAltarRenderer::new);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public ActionResultType use(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand hand, @Nonnull BlockRayTraceResult hit) {
        FeyAltar tile = this.getTile(world, pos);
        if (!world.isClientSide) {
            if (player.isShiftKeyDown()) {
                for (int slot = tile.getInventory().getSlots() - 1; slot >= 0; slot--) {
                    if (!tile.getInventory().getStackInSlot(slot).isEmpty()) {
                        player.addItem(tile.getInventory().extractItem(slot, 64, false));
                        return ActionResultType.CONSUME;
                    }
                }
                return ActionResultType.FAIL;
            } else if (!player.getItemInHand(hand).isEmpty()) {
                for (int slot = 0; slot < tile.getInventory().getSlots(); slot++) {
                    if (tile.getInventory().getStackInSlot(slot).isEmpty()) {
                        ItemStack insertStack = player.getItemInHand(hand).copy();
                        insertStack.setCount(1);
                        if (tile.getInventory().insertItem(slot, insertStack, true).isEmpty()) {
                            tile.getInventory().insertItem(slot, insertStack, false);
                            player.getItemInHand(hand).shrink(1);
                            return ActionResultType.CONSUME;
                        }
                    }
                }
                return ActionResultType.FAIL;
            } else {
                return ActionResultType.PASS;
            }
        } else {
            if (player.isShiftKeyDown()) {
                // Will succeed on server if inventory is non-empty
                // We need to return SUCCESS in that case to swing the arm
                for (int slots = 0; slots < tile.getInventory().getSlots(); slots++) {
                    if (!tile.getInventory().getStackInSlot(slots).isEmpty()) {
                        return ActionResultType.SUCCESS;
                    }
                }
                return ActionResultType.FAIL;
            } else if (!player.getItemInHand(hand).isEmpty()) {
                // We succeed when there's at least one free slot
                for (int slots = 0; slots < tile.getInventory().getSlots(); slots++) {
                    if (tile.getInventory().getStackInSlot(slots).isEmpty()) {
                        return ActionResultType.SUCCESS;
                    }
                }
                return ActionResultType.FAIL;
            } else {
                return ActionResultType.PASS;
            }
        }
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public BlockRenderType getRenderShape(@Nonnull BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    protected boolean shouldDropInventory(World world, BlockPos pos, BlockState state) {
        return true;
    }
}
