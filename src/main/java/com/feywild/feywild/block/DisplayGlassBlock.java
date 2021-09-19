package com.feywild.feywild.block;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.entity.DisplayGlassEntity;
import com.feywild.feywild.block.render.DisplayGlassRenderer;
import com.feywild.feywild.block.render.FeyAltarRenderer;
import com.feywild.feywild.item.ModItems;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.BlockTE;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.util.function.Consumer;

public class DisplayGlassBlock extends BlockTE<DisplayGlassEntity> {

    public static final BooleanProperty GENERATOR = BooleanProperty.create("generator");
    public static final IntegerProperty STATE = IntegerProperty.create("state", 0,4);
    public DisplayGlassBlock(ModX mod) {
        super(mod, DisplayGlassEntity.class, AbstractBlock.Properties.of(Material.GLASS).strength(9999999f).noOcclusion());
        this.registerDefaultState(this.stateDefinition.any().setValue(GENERATOR, false).setValue(STATE,0));
    }

    @Override
    protected boolean shouldDropInventory(World world, BlockPos pos, BlockState state) {
        return state.getValue(STATE) == 3;
    }

    @Override
    public void attack(BlockState pState, World pLevel, BlockPos pPos, PlayerEntity pPlayer) {
        super.attack(pState, pLevel, pPos, pPlayer);
        if(!pLevel.isClientSide && pLevel.getBlockEntity(pPos) instanceof DisplayGlassEntity){
            DisplayGlassEntity entity = (DisplayGlassEntity)pLevel.getBlockEntity(pPos);
            entity.hitGlass(pLevel);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(GENERATOR).add(STATE);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void registerClient(ResourceLocation id, Consumer<Runnable> defer) {
        ClientRegistry.bindTileEntityRenderer(this.getTileType(), DisplayGlassRenderer::new);
        defer.accept(() -> RenderTypeLookup.setRenderLayer(this, RenderType.translucent()));
    }

}
