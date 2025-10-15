package com.anonym.astran.registries;

import com.anonym.astran.Astran;
import com.anonym.astran.systems.assembly.storage.InterfaceStorageData;
import com.anonym.astran.systems.attachments.SteelHeartReservoirData;
import com.anonym.astran.systems.cybernetics.CachedModuleData;
import com.anonym.astran.systems.cybernetics.StorageForLimbData;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Supplier;

public class AstranAttachmentTypeRegistry {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Astran.MODID);


    public static final Supplier<AttachmentType<SteelHeartReservoirData>> STEEL_HEART_RESSERVOIR =
            ATTACHMENT_TYPES.register("reservoirdata",
            () -> AttachmentType.builder(() -> new SteelHeartReservoirData(Optional.empty()))
                    .serialize(SteelHeartReservoirData.CODEC).sync(SteelHeartReservoirData.STREAM_CODEC).copyOnDeath().build());

    public static final Supplier<AttachmentType<InterfaceStorageData>> INTERFACE_STORAGE_DATA =
            ATTACHMENT_TYPES.register("interface_storage_data",
                    () -> AttachmentType.builder(() -> new InterfaceStorageData(new ArrayList<>(),new HashMap<>()))
                            .serialize(InterfaceStorageData.CODEC).sync(InterfaceStorageData.STREAM_CODEC).copyOnDeath().build());

    public static final Supplier<AttachmentType<StorageForLimbData>> HEAD_STORAGE =
            ATTACHMENT_TYPES.register("head_storage",
                    () -> AttachmentType.builder(() -> new StorageForLimbData())
                            .serialize(StorageForLimbData.CODEC).sync(StorageForLimbData.STREAM_CODEC).copyOnDeath().build());

    public static final Supplier<AttachmentType<StorageForLimbData>> TORSO_STORAGE =
            ATTACHMENT_TYPES.register("torso_storage",
                    () -> AttachmentType.builder(() -> new StorageForLimbData())
                            .serialize(StorageForLimbData.CODEC).sync(StorageForLimbData.STREAM_CODEC).copyOnDeath().build());

    public static final Supplier<AttachmentType<StorageForLimbData>> HIP_STORAGE =
            ATTACHMENT_TYPES.register("hip_storage",
                    () -> AttachmentType.builder(() -> new StorageForLimbData())
                            .serialize(StorageForLimbData.CODEC).sync(StorageForLimbData.STREAM_CODEC).copyOnDeath().build());

    public static final Supplier<AttachmentType<StorageForLimbData>> RIGHT_SHOULDER_STORAGE =
            ATTACHMENT_TYPES.register("right_shoulder_storage",
                    () -> AttachmentType.builder(() -> new StorageForLimbData())
                            .serialize(StorageForLimbData.CODEC).sync(StorageForLimbData.STREAM_CODEC).copyOnDeath().build());

    public static final Supplier<AttachmentType<StorageForLimbData>> LEFT_SHOULDER_STORAGE =
            ATTACHMENT_TYPES.register("left_shoulder_storage",
                    () -> AttachmentType.builder(() -> new StorageForLimbData())
                            .serialize(StorageForLimbData.CODEC).sync(StorageForLimbData.STREAM_CODEC).copyOnDeath().build());

    public static final Supplier<AttachmentType<StorageForLimbData>> RIGHT_HAND_STORAGE =
            ATTACHMENT_TYPES.register("right_hand_storage",
                    () -> AttachmentType.builder(() -> new StorageForLimbData())
                            .serialize(StorageForLimbData.CODEC).sync(StorageForLimbData.STREAM_CODEC).copyOnDeath().build());

    public static final Supplier<AttachmentType<StorageForLimbData>> LEFT_HAND_STORAGE =
            ATTACHMENT_TYPES.register("left_hand_storage",
                    () -> AttachmentType.builder(() -> new StorageForLimbData())
                            .serialize(StorageForLimbData.CODEC).sync(StorageForLimbData.STREAM_CODEC).copyOnDeath().build());

    public static final Supplier<AttachmentType<StorageForLimbData>> RIGHT_LEG_STORAGE =
            ATTACHMENT_TYPES.register("right_leg_storage",
                    () -> AttachmentType.builder(() -> new StorageForLimbData())
                            .serialize(StorageForLimbData.CODEC).sync(StorageForLimbData.STREAM_CODEC).copyOnDeath().build());

    public static final Supplier<AttachmentType<StorageForLimbData>> LEFT_LEG_STORAGE =
            ATTACHMENT_TYPES.register("left_leg_storage",
                    () -> AttachmentType.builder(() -> new StorageForLimbData())
                            .serialize(StorageForLimbData.CODEC).sync(StorageForLimbData.STREAM_CODEC).copyOnDeath().build());

    public static final Supplier<AttachmentType<CachedModuleData>> MODULE_CACHE_DATA =
            ATTACHMENT_TYPES.register("cached_module_data",
                    () -> AttachmentType.builder(() -> new CachedModuleData(new HashMap<>(), new HashMap<>(), new HashMap<>(),0.0f))
                            .serialize(CachedModuleData.CODEC).sync(CachedModuleData.STREAM_CODEC).copyOnDeath().build());

}
