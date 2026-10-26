package com.saphienyako.feywild.effect;

import net.minecraft.client.player.ClientInput;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Input;

public class FeyTrickeryInput extends ClientInput {

    private final ClientInput original;
    private final LocalPlayer player;

    public FeyTrickeryInput(ClientInput original, LocalPlayer player) {
        this.original = original;
        this.player = player;
    }

    @Override
    public void tick() {
        original.tick();

        this.keyPresses = original.keyPresses;
        this.moveVector = original.getMoveVector();

        if (player.hasEffect(ModEffects.FEY_TRICKERY)) {
            Input keys = this.keyPresses;

            this.keyPresses = new Input(
                    keys.backward(),
                    keys.forward(),
                    keys.right(),
                    keys.left(),
                    keys.shift(),
                    keys.jump(),
                    keys.sprint()
            );

            this.moveVector = this.moveVector.scale(-1.0F);
        }
    }
}
