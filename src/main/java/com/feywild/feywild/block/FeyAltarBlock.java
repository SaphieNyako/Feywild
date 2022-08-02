package com.feywild.feywild.block;

import com.feywild.feywild.block.entity.FeyAltar;
import com.feywild.feywild.block.render.FeyAltarRenderer;
import org.moddingx.libx.base.tile.BlockBE;
import org.moddingx.libx.mod.ModX;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.moddingx.libx.registration.SetupContext;

import javax.annotation.Nonnull;

public class FeyAltarBlock extends BlockBE<FeyAltar> {

    public FeyAltarBlock(ModX mod) {
        super(mod, FeyAltar.class, BlockBehaviour.Properties.of(Material.STONE).strength(0f).noOcclusion());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void registerClient(SetupContext ctx) {
        ctx.enqueue(() -> BlockEntityRenderers.register(this.getBlockEntityType(), FeyAltarRenderer::new));
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hit) {
        FeyAltar tile = this.getBlockEntity(level, pos);
        if (!level.isClientSide) {
            if (player.isShiftKeyDown()) {
                for (int slot = tile.getInventory().getSlots() - 1; slot >= 0; slot--) {
                    if (!tile.getInventory().getStackInSlot(slot).isEmpty()) {
                        player.addItem(tile.getInventory().extractItem(slot, 64, false));
                        return InteractionResult.CONSUME;
                    }
                }
                return InteractionResult.FAIL;
            } else if (!player.getItemInHand(hand).isEmpty()) {
                for (int slot = 0; slot < tile.getInventory().getSlots(); slot++) {
                    if (tile.getInventory().getStackInSlot(slot).isEmpty()) {
                        ItemStack insertStack = player.getItemInHand(hand).copy();
                        insertStack.setCount(1);
                        if (tile.getInventory().insertItem(slot, insertStack, true).isEmpty()) {
                            tile.getInventory().insertItem(slot, insertStack, false);
                            player.getItemInHand(hand).shrink(1);
                            return InteractionResult.CONSUME;
                        }
                    }
                }
                return InteractionResult.FAIL;
            } else {
                return InteractionResult.PASS;
            }
        } else {
            if (player.isShiftKeyDown()) {
                // Will succeed on server if inventory is non-empty
                // We need to return SUCCESS in that case to swing the arm
                for (int slots = 0; slots < tile.getInventory().getSlots(); slots++) {
                    if (!tile.getInventory().getStackInSlot(slots).isEmpty()) {
                        return InteractionResult.SUCCESS;
                    }
                }
                return InteractionResult.FAIL;
            } else if (!player.getItemInHand(hand).isEmpty()) {
                // We succeed when there's at least one free slot
                for (int slots = 0; slots < tile.getInventory().getSlots(); slots++) {
                    if (tile.getInventory().getStackInSlot(slots).isEmpty()) {
                        return InteractionResult.SUCCESS;
                    }
                }
                return InteractionResult.FAIL;
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public RenderShape getRenderShape(@Nonnull BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    protected boolean shouldDropInventory(Level level, BlockPos pos, BlockState state) {
        return true;
    }
}
