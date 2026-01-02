package com.saphienyako.feywild.entity.animations;

public interface AnimationsInterface {

    // --------------------- Keyframe Classes ---------------------
    public static class RotationKeyframe {
        public final float time;
        public final float xRot, yRot, zRot;
        public RotationKeyframe(float time, float xRot, float yRot, float zRot) {
            this.time = time; this.xRot = xRot; this.yRot = yRot; this.zRot = zRot;
        }
    }

    public static class PositionKeyframe {
        public final float time;
        public final float x, y, z;
        public PositionKeyframe(float time, float x, float y, float z) {
            this.time = time; this.x = x; this.y = y; this.z = z;
        }
    }

    public static class ScaleKeyframe {
        public final float time;
        public final float x, y, z;
        public ScaleKeyframe(float time, float x, float y, float z) {
            this.time = time; this.x = x; this.y = y; this.z = z;
        }
    }

    // --------------------- POSE ---------------------
    // --------------------- IDLE ---------------------
    // --------------------- FLY ---------------------
    // --------------------- SPELL CASTING ---------------------
    // --------------------- Interpolation Helpers ---------------------
}
