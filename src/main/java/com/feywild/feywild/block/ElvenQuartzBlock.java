package com.feywild.feywild.block;

import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import org.moddingx.libx.base.decoration.DecoratedBlock;
import org.moddingx.libx.base.decoration.DecorationContext;
import org.moddingx.libx.mod.ModX;
import org.moddingx.libx.registration.RegistrationContext;

import javax.annotation.OverridingMethodsMustInvokeSuper;

public class ElvenQuartzBlock extends DecoratedBlock {

    private final DecoratedBlock brick;
    private final Block mossyBrick;
    private final DecoratedBlock crackedBrick;
    private final ElvenQuartzPillar pillar;
    private final DecoratedBlock polished;
    
    public ElvenQuartzBlock(ModX mod, Properties properties) {
        super(mod, DecorationContext.GENERIC, properties);
        this.brick = new DecoratedBlock(mod, DecorationContext.GENERIC, properties);
        this.mossyBrick = new MossyBlock(mod, this.brick, properties);
        this.crackedBrick = new DecoratedBlock(mod, DecorationContext.GENERIC, properties);
        this.pillar = new ElvenQuartzPillar(mod, properties);
        this.polished = new DecoratedBlock(mod, DecorationContext.GENERIC, properties);
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void registerAdditional(RegistrationContext ctx, EntryCollector builder) {
        super.registerAdditional(ctx, builder);
        builder.registerNamed(Registry.BLOCK_REGISTRY, "brick", this.brick);
        builder.registerNamed(Registry.BLOCK_REGISTRY, "mossy_brick", this.mossyBrick);
        builder.registerNamed(Registry.BLOCK_REGISTRY, "cracked_brick", this.crackedBrick);
        builder.registerNamed(Registry.BLOCK_REGISTRY, "pillar", this.pillar);
        builder.registerNamed(Registry.BLOCK_REGISTRY, "polished", this.polished);
    }

    public DecoratedBlock getBrickBlock() {
        return brick;
    }

    public Block getMossyBrickBlock() {
        return mossyBrick;
    }

    public DecoratedBlock getCrackedBrickBlock() {
        return crackedBrick;
    }

    public ElvenQuartzPillar getPillarBlock() {
        return pillar;
    }

    public DecoratedBlock getPolishedBlock() {
        return polished;
    }
}
