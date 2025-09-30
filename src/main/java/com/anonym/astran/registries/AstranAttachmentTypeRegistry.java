package com.anonym.astran.registries;

import com.anonym.astran.Astran;
import com.anonym.astran.systems.attachments.SteelHeartReservoirData;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Optional;
import java.util.function.Supplier;

public class AstranAttachmentTypeRegistry {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Astran.MODID);


    public static final Supplier<AttachmentType<SteelHeartReservoirData>> STEEL_HEART_RESSERVOIR =
            ATTACHMENT_TYPES.register("reservoirdata",
            () -> AttachmentType.builder(() -> new SteelHeartReservoirData(Optional.empty()))
                    .serialize(SteelHeartReservoirData.CODEC).sync(SteelHeartReservoirData.STREAM_CODEC).build());
}
