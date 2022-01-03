package com.feywild.feywild.block;

import com.feywild.feywild.block.entity.DisplayGlass;
import com.feywild.feywild.block.render.DisplayGlassRenderer;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.BlockTE;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class DisplayGlassBlock extends BlockTE<DisplayGlass> {

    public static final BooleanProperty CAN_GENERATE = BooleanProperty.create("can_generate");
    public static final IntegerProperty BREAKAGE = IntegerProperty.create("breakage", 0, 4);

    public DisplayGlassBlock(ModX mod) {
        super(mod, DisplayGlass.class, AbstractBlock.Properties.of(Material.GLASS).strength(9999999f).noOcclusion().noDrops());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(CAN_GENERATE, false)
                .setValue(BREAKAGE, 0)
        );
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void registerClient(ResourceLocation id, Consumer<Runnable> defer) {
        defer.accept(() -> {
            ClientRegistry.bindTileEntityRenderer(this.getTileType(), DisplayGlassRenderer::new);
            RenderTypeLookup.setRenderLayer(this, RenderType.translucent());
        });
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(CAN_GENERATE).add(BREAKAGE);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void attack(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull PlayerEntity player) {
        super.attack(state, world, pos, player);
        if (!world.isClientSide) {
            getTile(world, pos).hitGlass();
        }
    }

    @Override
    protected boolean shouldDropInventory(World world, BlockPos pos, BlockState state) {
        return state.getValue(BREAKAGE) == 3;
    }
}
