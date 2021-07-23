package com.feywild.feywild.entity.goals;

import com.feywild.feywild.effects.ModEffects;
import com.feywild.feywild.entity.AutumnPixieEntity;
import com.feywild.feywild.network.FeywildPacketHandler;
import com.feywild.feywild.network.ParticleMessage;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class AddShieldGoal extends Goal {

    protected final World worldLevel;
    protected AutumnPixieEntity entity;
    protected PlayerEntity target;
    protected int count = 0;

    public AddShieldGoal(AutumnPixieEntity entity) {
        this.entity = entity;
        this.worldLevel = entity.level;

    }

    //add to list
    //Loop over list

    @Override
    public void tick() {
        if (target == null) {
            return;
        }
        count--;

        if (count <= 0) {
            addShieldEffect();
            reset();

        } else if (count == 110) {
            spellCasting();
        } else if (count <= 100) {
            entity.lookAt(EntityAnchorArgument.Type.EYES, target.position());
        }

    }

    @Override
    public void start() {

        count = 120;
        entity.setCasting(false);
        target = null;

        World world = entity.getCommandSenderWorld();
        AxisAlignedBB box = new AxisAlignedBB(entity.blockPosition()).inflate(4);
        world.getEntities(null, box).forEach(entity1 -> {
            if(entity1 instanceof PlayerEntity){
                target = (PlayerEntity) entity1;
            }
        });

    }

    private void spellCasting() {
        entity.getNavigation().moveTo(target.getX(), target.getY(), target.getZ(), 0.5);
        entity.setCasting(true);
        entity.playSound(ModSoundEvents.PIXIE_SPELLCASTING.get(), 2, 2);
    }

    private void addShieldEffect() {

        target.addEffect(new EffectInstance(ModEffects.WIND_WALK_EFFECT.get(), 20 * 60,2));
        target.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 20 * 60,2));
        FeywildPacketHandler.sendToPlayersInRange(worldLevel, entity.blockPosition()
                , new ParticleMessage(target.getX(), target.getY(), target.getZ(), 0, 0, 0, 10, 0, 0)
                , 64);

    }

    private void reset() {
        entity.setCasting(false);
        target = null;
    }

    @Override
    public boolean canContinueToUse() {
        return count > 0;
    }

    @Override
    public boolean canUse() {
        return entity.level.random.nextFloat() < 0.005f;
    }
}
