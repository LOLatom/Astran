package com.anonym.astran.registries;

import com.anonym.astran.Astran;
import com.anonym.astran.systems.assembly.storage.InterfaceStorageData;
import com.anonym.astran.systems.attachments.SteelHeartReservoirData;
import com.anonym.astran.systems.cybernetics.BoneData;
import com.anonym.astran.systems.cybernetics.LimbType;
import com.anonym.astran.systems.cybernetics.BoneData;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Supplier;

public class AstranBoneDataRegistry {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Astran.MODID);


    public static final Supplier<AttachmentType<BoneData>> HEAD =
            ATTACHMENT_TYPES.register("head",
                    () -> AttachmentType.builder(() -> new BoneData(LimbType.HEAD))
                            .serialize(BoneData.CODEC).sync(BoneData.STREAM_CODEC).copyOnDeath().build());

    public static final Supplier<AttachmentType<BoneData>> TORSO =
            ATTACHMENT_TYPES.register("torso",
                    () -> AttachmentType.builder(() -> new BoneData(LimbType.TORSO))
                            .serialize(BoneData.CODEC).sync(BoneData.STREAM_CODEC).copyOnDeath().build());

    public static final Supplier<AttachmentType<BoneData>> HIP =
            ATTACHMENT_TYPES.register("hip",
                    () -> AttachmentType.builder(() -> new BoneData(LimbType.HIPS))
                            .serialize(BoneData.CODEC).sync(BoneData.STREAM_CODEC).copyOnDeath().build());

    public static final Supplier<AttachmentType<BoneData>> RIGHT_SHOULDER =
            ATTACHMENT_TYPES.register("right",
                    () -> AttachmentType.builder(() -> new BoneData(LimbType.RIGHT_SHOULDER))
                            .serialize(BoneData.CODEC).sync(BoneData.STREAM_CODEC).copyOnDeath().build());

    public static final Supplier<AttachmentType<BoneData>> LEFT_SHOULDER =
            ATTACHMENT_TYPES.register("left_shoulder",
                    () -> AttachmentType.builder(() -> new BoneData(LimbType.LEFT_SHOULDER))
                            .serialize(BoneData.CODEC).sync(BoneData.STREAM_CODEC).copyOnDeath().build());

    public static final Supplier<AttachmentType<BoneData>> RIGHT_HAND =
            ATTACHMENT_TYPES.register("right_hand",
                    () -> AttachmentType.builder(() -> new BoneData(LimbType.RIGHT_HAND))
                            .serialize(BoneData.CODEC).sync(BoneData.STREAM_CODEC).copyOnDeath().build());

    public static final Supplier<AttachmentType<BoneData>> LEFT_HAND =
            ATTACHMENT_TYPES.register("left_hand",
                    () -> AttachmentType.builder(() -> new BoneData(LimbType.LEFT_HAND))
                            .serialize(BoneData.CODEC).sync(BoneData.STREAM_CODEC).copyOnDeath().build());

    public static final Supplier<AttachmentType<BoneData>> RIGHT_LEG =
            ATTACHMENT_TYPES.register("right_leg",
                    () -> AttachmentType.builder(() -> new BoneData(LimbType.RIGHT_LEG))
                            .serialize(BoneData.CODEC).sync(BoneData.STREAM_CODEC).copyOnDeath().build());

    public static final Supplier<AttachmentType<BoneData>> LEFT_LEG =
            ATTACHMENT_TYPES.register("left_leg",
                    () -> AttachmentType.builder(() -> new BoneData(LimbType.LEFT_LEG))
                            .serialize(BoneData.CODEC).sync(BoneData.STREAM_CODEC).copyOnDeath().build());

}
