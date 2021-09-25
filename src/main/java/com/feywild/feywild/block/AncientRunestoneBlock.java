package com.feywild.feywild.block;

import com.feywild.feywild.block.entity.AncientRunestone;
import com.feywild.feywild.block.render.AncientRunestoneRenderer;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.BlockTE;
import net.minecraft.block.AbstractBlock;
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
import net.minecraftforge.fml.client.registry.ClientRegistry;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class AncientRunestoneBlock extends BlockTE<AncientRunestone> {

    public static final ResourceLocation NIDAVELLIR_RUNE = new ResourceLocation("mythicbotany", "nidavellir_rune");
    
    public AncientRunestoneBlock(ModX mod) {
        super(mod, AncientRunestone.class, AbstractBlock.Properties.of(Material.STONE).strength(-1.0F, 3600000).noDrops());
    }

    @Override
    public void registerClient(ResourceLocation id, Consumer<Runnable> defer) {
        defer.accept(() -> ClientRegistry.bindTileEntityRenderer(this.getTileType(), AncientRunestoneRenderer::new));
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public ActionResultType use(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand hand, @Nonnull BlockRayTraceResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        if (!stack.isEmpty() && NIDAVELLIR_RUNE.equals(stack.getItem().getRegistryName())) {
            if (getTile(world, pos).start() && !player.isCreative()) {
                stack.shrink(1);
            }
            return ActionResultType.sidedSuccess(world.isClientSide);
        }
        return super.use(state, world, pos, player, hand, hit);
    }
}
