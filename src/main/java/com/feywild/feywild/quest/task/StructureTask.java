// UPDATE_TODO
//package com.feywild.feywild.quest.task;
//
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
//import net.minecraft.world.level.levelgen.feature.StructureFeature;
//import net.minecraftforge.registries.ForgeRegistries;
//
//public class StructureTask extends RegistryTaskType<StructureFeature<?>, ConfiguredStructureFeature<?, ?>> {
//
//    public static final StructureTask INSTANCE = new StructureTask();
//
//    protected StructureTask() {
//        super("structure", ForgeRegistries.STRUCTURE_FEATURES);
//    }
//
//    @Override
//    public Class<ConfiguredStructureFeature<?, ?>> testType() {
//        //noinspection unchecked
//        return (Class<ConfiguredStructureFeature<?, ?>>) (Class<?>) ConfiguredStructureFeature.class;
//    }
//
//    @Override
//    public boolean checkCompleted(ServerPlayer player, StructureFeature<?> element, ConfiguredStructureFeature<?, ?> match) {
//        return element == match.feature;
//    }
//
//    @Override
//    public boolean repeatable() {
//        return false;
//    }
//
//}
