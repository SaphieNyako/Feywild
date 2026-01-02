package com.saphienyako.feywild.entity.animations;

public class SpringPixieAnimations implements AnimationsInterface {


    // --------------------- POSE ---------------------
    public static final ScaleKeyframe[] BODY_POSE_SCALE = {
            new ScaleKeyframe(0f, 0.85f, 0.85f, 0.85f)
    };

    public static final RotationKeyframe[] HEAD_POSE = {
            new RotationKeyframe(0f, -17.7531f, 9.5327f, -3.0351f)
    };

    public static final RotationKeyframe[] RIGHT_ARM_POSE = {
            new RotationKeyframe(0f, 7.5f, 0f, 10f)
    };

    public static final RotationKeyframe[] LEFT_ARM_POSE = {
            new RotationKeyframe(0f, 1.6535f, -0.5f, 5.461f)
    };

    // --------------------- IDLE ---------------------
    public static final RotationKeyframe[] BODY_IDLE_ROT = {
            new RotationKeyframe(0f, -2.5f, 0f, 0f)
    };
    public static final ScaleKeyframe[] BODY_IDLE_SCALE = {
            new ScaleKeyframe(0f, 0.85f, 0.85f, 0.85f)
    };
    public static final PositionKeyframe[] BODY_IDLE_POS = {
            new PositionKeyframe(0f, 0f, 0f, 0f),
            new PositionKeyframe(2f, 0f, -1f, 0f),
            new PositionKeyframe(4f, 0f, 0f, 0f)
    };
    public static final RotationKeyframe[] HEAD_IDLE = {
            new RotationKeyframe(0f, 0f, 0f, 0f),
            new RotationKeyframe(2f, 5f, 0f, 0f),
            new RotationKeyframe(4f, 0f, 0f, 0f)
    };
    public static final RotationKeyframe[] LEFT_HAIR_IDLE = {
            new RotationKeyframe(0f, 0f, 0f, 0f),
            new RotationKeyframe(2f, 0f, 0f, 5f),
            new RotationKeyframe(4f, 0f, 0f, 0f)
    };
    public static final RotationKeyframe[] RIGHT_HAIR_IDLE = {
            new RotationKeyframe(0f, 0f, 0f, 0f),
            new RotationKeyframe(2f, 0f, 0f, -5f),
            new RotationKeyframe(4f, 0f, 0f, 0f)
    };
    public static final RotationKeyframe[] HAIR_IDLE = {
            new RotationKeyframe(0f, 0f, 0f, 0f),
            new RotationKeyframe(2f, -5f, 0f, 0f),
            new RotationKeyframe(4f, 0f, 0f, 0f)
    };
    public static final RotationKeyframe[] RIGHT_ARM_IDLE = {
            new RotationKeyframe(0f, -33.8477f, -37.0215f, -13.063f)
    };
    public static final RotationKeyframe[] LEFT_ARM_IDLE = {
            new RotationKeyframe(0f, -33.8477f, 37.0215f, 13.063f)
    };
    public static final RotationKeyframe[] LEFT_LEG_IDLE = {
            new RotationKeyframe(0f, -5f, 0f, 2.5f)
    };
    public static final RotationKeyframe[] RIGHT_LEG_IDLE = {
            new RotationKeyframe(0f, -7.5f, 0f, 0f)
    };
    public static final RotationKeyframe[] LEFT_WING_IDLE = {
            new RotationKeyframe(0f, 0f, 0f, 0f),
            new RotationKeyframe(2f, 0f, -20f, 0f),
            new RotationKeyframe(4f, 0f, 0f, 0f)
    };
    public static final RotationKeyframe[] RIGHT_WING_IDLE = {
            new RotationKeyframe(0f, 0f, 0f, 0f),
            new RotationKeyframe(2f, 0f, 20f, 0f),
            new RotationKeyframe(4f, 0f, 0f, 0f)
    };

    // --------------------- FLY ---------------------
    public static final RotationKeyframe[] BODY_FLY = {
            new RotationKeyframe(0f, 10f, 0f, 0f),
            new RotationKeyframe(1f, 5f, 0f, 0f),
            new RotationKeyframe(2f, 10f, 0f, 0f)
    };
    public static final ScaleKeyframe[] BODY_FLY_SCALE = {
            new ScaleKeyframe(0f, 0.85f, 0.85f, 0.85f)
    };
    public static final PositionKeyframe[] BODY_FLY_POS = {
            new PositionKeyframe(0f, 0f, 0f, 0f),
            new PositionKeyframe(1f, 0f, -0.5f, 0f),
            new PositionKeyframe(2f, 0f, 0f, 0f)
    };

    // Wings flap
    public static final RotationKeyframe[] LEFT_WING_FLY = {
            new RotationKeyframe(0f, 0f, -32.5f, 0f),
            new RotationKeyframe(0.25f, 0f, 15f, 0f),
            new RotationKeyframe(0.5f, 0f, -32.5f, 0f),
            new RotationKeyframe(0.75f, 0f, 15f, 0f),
            new RotationKeyframe(1f, 0f, -32.5f, 0f),
            new RotationKeyframe(1.25f, 0f, 15f, 0f),
            new RotationKeyframe(1.5f, 0f, -32.5f, 0f),
            new RotationKeyframe(1.75f, 0f, 15f, 0f),
            new RotationKeyframe(2f, 0f, -32.5f, 0f)
    };
    public static final RotationKeyframe[] RIGHT_WING_FLY = {
            new RotationKeyframe(0f, 0f, 32.5f, 0f),
            new RotationKeyframe(0.25f, 0f, -15f, 0f),
            new RotationKeyframe(0.5f, 0f, 32.5f, 0f),
            new RotationKeyframe(0.75f, 0f, -15f, 0f),
            new RotationKeyframe(1f, 0f, 32.5f, 0f),
            new RotationKeyframe(1.25f, 0f, -15f, 0f),
            new RotationKeyframe(1.5f, 0f, 32.5f, 0f),
            new RotationKeyframe(1.75f, 0f, -15f, 0f),
            new RotationKeyframe(2f, 0f, 32.5f, 0f)
    };

    // --------------------- SPELL CASTING ---------------------

    // BODY
    public static final RotationKeyframe[] BODY_SPELL_CAST = {
            new RotationKeyframe(0f, -2.5f, 0f, 0f),
            new RotationKeyframe(0.5f, 2.5f, 0f, 0f),
            new RotationKeyframe(0.6f, 2.5f, 0f, 0f),
            new RotationKeyframe(4.25f, -2.5f, 0f, 0f),
            new RotationKeyframe(4.75f, -2.5f, 0f, 0f)
    };
    public static final ScaleKeyframe[] BODY_SPELL_CAST_SCALE = {
            new ScaleKeyframe(0f, 0.85f, 0.85f, 0.85f),
            new ScaleKeyframe(4.25f, 0.85f, 0.85f, 0.85f),
            new ScaleKeyframe(4.75f, 0.85f, 0.85f, 0.85f)
    };

    // SPRITE POSITION
    public static final PositionKeyframe[] SPRITE_SPELL_CAST_POS = {
            new PositionKeyframe(0f, 0f, 0f, 0f),
            new PositionKeyframe(0.5f, 0f, -0.25f, 0f),
            new PositionKeyframe(1f, 0f, 0f, 0f),
            new PositionKeyframe(1.5f, 0f, -0.25f, 0f),
            new PositionKeyframe(2f, 0f, 0f, 0f),
            new PositionKeyframe(2.5f, 0f, -0.25f, 0f),
            new PositionKeyframe(3f, 0f, 0f, 0f),
            new PositionKeyframe(3.5f, 0f, -0.25f, 0f),
            new PositionKeyframe(4f, 0f, 0f, 0f),
            new PositionKeyframe(4.25f, 0f, 0f, 0f),
            new PositionKeyframe(4.75f, 0f, 0f, 0f)
    };

    // HEAD
    public static final RotationKeyframe[] HEAD_SPELL_CAST = {
            new RotationKeyframe(0f, 0f, 0f, 0f),
            new RotationKeyframe(0.5f, -17.6417f, -7.151f, 2.2671f),
            new RotationKeyframe(0.6f, -17.6417f, -7.151f, 2.2671f),
            new RotationKeyframe(3.1f, -17.6417f, -7.151f, 2.2671f),
            new RotationKeyframe(3.15f, -17.6417f, -7.151f, 2.2671f),
            new RotationKeyframe(3.3f, 17.5f, 0f, 0f),
            new RotationKeyframe(3.4f, 17.5f, 0f, 0f),
            new RotationKeyframe(3.6f, -15f, 0f, 0f),
            new RotationKeyframe(4f, -15f, 0f, 0f),
            new RotationKeyframe(4.25f, 0f, 0f, 0f),
            new RotationKeyframe(4.75f, 0f, 0f, 0f)
    };

    // LEFT HAIR
    public static final RotationKeyframe[] LEFT_HAIR_SPELL_CAST = {
            new RotationKeyframe(0f, 0f, 0f, 0f),
            new RotationKeyframe(4.25f, 0f, 0f, 0f),
            new RotationKeyframe(4.75f, 0f, 0f, 0f)
    };

    // RIGHT HAIR
    public static final RotationKeyframe[] RIGHT_HAIR_SPELL_CAST = {
            new RotationKeyframe(0f, 0f, 0f, 0f),
            new RotationKeyframe(4.25f, 0f, 0f, 0f),
            new RotationKeyframe(4.75f, 0f, 0f, 0f)
    };

    // HAIR
    public static final RotationKeyframe[] HAIR_SPELL_CAST = {
            new RotationKeyframe(0f, 0f, 0f, 0f),
            new RotationKeyframe(0.5f, 10f, 0f, 0f),
            new RotationKeyframe(0.6f, 10f, 0f, 0f),
            new RotationKeyframe(4.25f, 0f, 0f, 0f),
            new RotationKeyframe(4.75f, 0f, 0f, 0f)
    };

    // RIGHT ARM
    public static final RotationKeyframe[] RIGHT_ARM_SPELL_CAST = {
            new RotationKeyframe(0f, -33.8477f, -37.0215f, -13.063f),
            new RotationKeyframe(0.5f, 6.805f, -27.8225f, 11.383f),
            new RotationKeyframe(0.6f, 6.805f, -27.8225f, 11.383f),
            new RotationKeyframe(3.1f, 6.805f, -27.8225f, 11.383f),
            new RotationKeyframe(3.15f, 6.805f, -27.8225f, 11.383f),
            new RotationKeyframe(3.3f, -70.7954f, 6.5796f, -23.8436f),
            new RotationKeyframe(3.4f, -70.7954f, 6.5796f, -23.8436f),
            new RotationKeyframe(3.6f, -108.3644f, 64.7579f, -23.5308f),
            new RotationKeyframe(4f, -108.3644f, 64.7579f, -23.5308f),
            new RotationKeyframe(4.25f, -33.8477f, -37.0215f, -13.063f),
            new RotationKeyframe(4.75f, -33.8477f, -37.0215f, -13.063f)
    };

    // LEFT ARM
    public static final RotationKeyframe[] LEFT_ARM_SPELL_CAST = {
            new RotationKeyframe(0f, -33.8477f, 37.0215f, 13.063f),
            new RotationKeyframe(0.5f, -98.0277f, -26.4216f, 21.3078f),
            new RotationKeyframe(0.6f, -98.0277f, -26.4216f, 21.3078f),
            new RotationKeyframe(0.8f, -86.4562f, -33.4459f, 24.0972f),
            new RotationKeyframe(1f, -62.7866f, -9.3632f, 30.9869f),
            new RotationKeyframe(1.2f, -90.3543f, 3.1261f, 30.4649f),
            new RotationKeyframe(1.4f, -98.0277f, -26.4216f, 21.3078f),
            new RotationKeyframe(1.65f, -86.4562f, -33.4459f, 24.0972f),
            new RotationKeyframe(1.85f, -62.7866f, -9.3632f, 30.9869f),
            new RotationKeyframe(2.05f, -90.3543f, 3.1261f, 30.4649f),
            new RotationKeyframe(2.25f, -98.0277f, -26.4216f, 21.3078f),
            new RotationKeyframe(2.45f, -86.4562f, -33.4459f, 24.0972f),
            new RotationKeyframe(2.65f, -62.7866f, -9.3632f, 30.9869f),
            new RotationKeyframe(2.9f, -90.3543f, 3.1261f, 30.4649f),
            new RotationKeyframe(3.1f, -98.0277f, -26.4216f, 21.3078f),
            new RotationKeyframe(3.15f, -98.0277f, -26.4216f, 21.3078f),
            new RotationKeyframe(3.3f, -70.7954f, -6.5796f, 23.8436f),
            new RotationKeyframe(3.4f, -70.7954f, -6.5796f, 23.8436f),
            new RotationKeyframe(3.6f, -108.3644f, -64.7579f, 23.5308f),
            new RotationKeyframe(4f, -108.3644f, -64.7579f, 23.5308f),
            new RotationKeyframe(4.25f, -33.8477f, 37.0215f, 13.063f),
            new RotationKeyframe(4.75f, -33.8477f, 37.0215f, 13.063f)
    };

    // LEFT LEG
    public static final RotationKeyframe[] LEFT_LEG_SPELL_CAST = {
            new RotationKeyframe(0f, -5f, 0f, 2.5f),
            new RotationKeyframe(0.5f, 5f, 0f, 2.5f),
            new RotationKeyframe(0.6f, 5f, 0f, 2.5f),
            new RotationKeyframe(3.9f, 5f, 0f, 2.5f),
            new RotationKeyframe(4f, 5f, 0f, 2.5f),
            new RotationKeyframe(4.25f, -5f, 0f, 2.5f),
            new RotationKeyframe(4.75f, -5f, 0f, 2.5f)
    };

    // RIGHT LEG
    public static final RotationKeyframe[] RIGHT_LEG_SPELL_CAST = {
            new RotationKeyframe(0f, -7.5f, 0f, 0f),
            new RotationKeyframe(0.5f, 12.5f, 0f, 0f),
            new RotationKeyframe(0.6f, 12.5f, 0f, 0f),
            new RotationKeyframe(3.9f, 12.5f, 0f, 0f),
            new RotationKeyframe(4f, 12.5f, 0f, 0f),
            new RotationKeyframe(4.25f, -7.5f, 0f, 0f),
            new RotationKeyframe(4.75f, -7.5f, 0f, 0f)
    };

    // LEFT WING ROTATION
    public static final RotationKeyframe[] LEFT_WING_ROT_SPELL_CAST = {
            new RotationKeyframe(0f, 0f, 0f, 0f),
            new RotationKeyframe(0.5f, -20.0735f, -22.477f, -0.6631f),
            new RotationKeyframe(0.6f, -20.0735f, -22.477f, -0.6631f),
            new RotationKeyframe(1f, -22.234f, -13.399f, -5.3845f),
            new RotationKeyframe(1.5f, -20.0735f, -22.477f, -0.6631f),
            new RotationKeyframe(2f, -22.234f, -13.399f, -5.3845f),
            new RotationKeyframe(2.5f, -20.0735f, -22.477f, -0.6631f),
            new RotationKeyframe(3f, -22.234f, -13.399f, -5.3845f),
            new RotationKeyframe(3.5f, -20.0735f, -22.477f, -0.6631f),
            new RotationKeyframe(4f, -22.234f, -13.399f, -5.3845f),
            new RotationKeyframe(4.25f, 0f, 0f, 0f),
            new RotationKeyframe(4.75f, 0f, 0f, 0f)
    };

    // LEFT WING POSITION
    public static final PositionKeyframe[] LEFT_WING_POS_SPELL_CAST = {
            new PositionKeyframe(0f, 0f, 0f, 0f),
            new PositionKeyframe(0.5f, 0f, -3f, 3f),
            new PositionKeyframe(0.65f, 0f, -3f, 3f),
            new PositionKeyframe(3.9f, 0f, -3f, 3f),
            new PositionKeyframe(4f, 0f, -3f, 3f),
            new PositionKeyframe(4.25f, 0f, 0f, 0f),
            new PositionKeyframe(4.75f, 0f, 0f, 0f)
    };

    // RIGHT WING ROTATION
    public static final RotationKeyframe[] RIGHT_WING_ROT_SPELL_CAST = {
            new RotationKeyframe(0f, 0f, 0f, 0f),
            new RotationKeyframe(0.5f, -20.0735f, 22.477f, 0.6631f),
            new RotationKeyframe(0.6f, -20.0735f, 22.477f, 0.6631f),
            new RotationKeyframe(1f, -22.234f, 13.399f, 5.3845f),
            new RotationKeyframe(1.5f, -20.0735f, 22.477f, 0.6631f),
            new RotationKeyframe(2f, -22.234f, 13.399f, 5.3845f),
            new RotationKeyframe(2.5f, -20.0735f, 22.477f, 0.6631f),
            new RotationKeyframe(3f, -22.234f, 13.399f, 5.3845f),
            new RotationKeyframe(3.5f, -20.0735f, 22.477f, 0.6631f),
            new RotationKeyframe(4f, -22.234f, 13.399f, 5.3845f),
            new RotationKeyframe(4.25f, 0f, 0f, 0f),
            new RotationKeyframe(4.75f, 0f, 0f, 0f)
    };

    // RIGHT WING POSITION
    public static final PositionKeyframe[] RIGHT_WING_POS_SPELL_CAST = {
            new PositionKeyframe(0f, 0f, 0f, 0f),
            new PositionKeyframe(0.5f, 0f, -3f, 3f),
            new PositionKeyframe(0.65f, 0f, -3f, 3f),
            new PositionKeyframe(3.9f, 0f, -3f, 3f),
            new PositionKeyframe(4f, 0f, -3f, 3f),
            new PositionKeyframe(4.25f, 0f, 0f, 0f),
            new PositionKeyframe(4.75f, 0f, 0f, 0f)
    };

    // --------------------- Interpolation Helpers ---------------------
    public static float[] interpolateRotation(SpringPixieAnimations.RotationKeyframe[] frames, float time) {
        if (frames.length == 0) return new float[]{0f,0f,0f};
        for (int i = 0; i < frames.length - 1; i++) {
            SpringPixieAnimations.RotationKeyframe a = frames[i], b = frames[i+1];
            if (time >= a.time && time <= b.time) {
                float t = (time - a.time) / (b.time - a.time);
                return new float[]{
                        a.xRot + (b.xRot - a.xRot) * t,
                        a.yRot + (b.yRot - a.yRot) * t,
                        a.zRot + (b.zRot - a.zRot) * t
                };
            }
        }
        SpringPixieAnimations.RotationKeyframe last = frames[frames.length - 1];
        return new float[]{last.xRot,last.yRot,last.zRot};
    }

    public static float[] interpolatePosition(SpringPixieAnimations.PositionKeyframe[] frames, float time) {
        if (frames.length == 0) return new float[]{0f,0f,0f};
        for (int i = 0; i < frames.length - 1; i++) {
            SpringPixieAnimations.PositionKeyframe a = frames[i], b = frames[i+1];
            if (time >= a.time && time <= b.time) {
                float t = (time - a.time) / (b.time - a.time);
                return new float[]{
                        a.x + (b.x - a.x) * t,
                        a.y + (b.y - a.y) * t,
                        a.z + (b.z - a.z) * t
                };
            }
        }
        SpringPixieAnimations.PositionKeyframe last = frames[frames.length - 1];
        return new float[]{last.x,last.y,last.z};
    }

    public static float[] interpolateScale(SpringPixieAnimations.ScaleKeyframe[] frames, float time) {
        if (frames.length == 0) return new float[]{1f,1f,1f};
        for (int i = 0; i < frames.length - 1; i++) {
            SpringPixieAnimations.ScaleKeyframe a = frames[i], b = frames[i+1];
            if (time >= a.time && time <= b.time) {
                float t = (time - a.time) / (b.time - a.time);
                return new float[]{
                        a.x + (b.x - a.x) * t,
                        a.y + (b.y - a.y) * t,
                        a.z + (b.z - a.z) * t
                };
            }
        }
        SpringPixieAnimations.ScaleKeyframe last = frames[frames.length - 1];
        return new float[]{last.x,last.y,last.z};
    }
}
