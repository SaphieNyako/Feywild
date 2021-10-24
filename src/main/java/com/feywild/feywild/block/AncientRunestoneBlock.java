package com.feywild.feywild.block;

import com.feywild.feywild.block.entity.AncientRunestone;
import com.feywild.feywild.block.render.AncientRunestoneRenderer;
import io.github.noeppi_noeppi.libx.base.tile.BlockBE;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class AncientRunestoneBlock extends BlockBE<AncientRunestone> {

    public static final ResourceLocation NIDAVELLIR_RUNE = new ResourceLocation("mythicbotany", "nidavellir_rune");
    
    public AncientRunestoneBlock(ModX mod) {
        super(mod, AncientRunestone.class, BlockBehaviour.Properties.of(Material.STONE).strength(-1.0F, 3600000).noDrops());
    }

    @Override
    public void registerClient(ResourceLocation id, Consumer<Runnable> defer) {
        defer.accept(() -> BlockEntityRenderers.register(this.getBlockEntityType(), ctx -> new AncientRunestoneRenderer()));
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        if (!stack.isEmpty() && NIDAVELLIR_RUNE.equals(stack.getItem().getRegistryName())) {
            if (getBlockEntity(level, pos).start() && !player.isCreative()) {
                stack.shrink(1);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.use(state, level, pos, player, hand, hit);
    }
}
