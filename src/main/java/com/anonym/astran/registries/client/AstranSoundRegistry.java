package com.anonym.astran.registries.client;

import com.anonym.astran.Astran;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AstranSoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, Astran.MODID);


    public static final DeferredHolder<SoundEvent,SoundEvent> STEEL_HEART_INSERT = createEvent("steel_heart_insert");

    public static final DeferredHolder<SoundEvent,SoundEvent> INTERFACE_SLOT_SELECT = createEvent("interface_slot_select");

    public static final DeferredHolder<SoundEvent,SoundEvent> INTERFACE_SLOT_OPEN = createEvent("interface_slot_open");

    public static final DeferredHolder<SoundEvent,SoundEvent> INTERFACE_CLOSE = createEvent("interface_close");

    public static final DeferredHolder<SoundEvent,SoundEvent> INTERFACE_ERROR = createEvent("interface_error");

    public static final DeferredHolder<SoundEvent,SoundEvent> INTERFACE_START = createEvent("interface_start");



    private static DeferredHolder<SoundEvent, SoundEvent> createEvent(String sound) {
        return SOUND_EVENTS.register(sound, () -> SoundEvent.createVariableRangeEvent(Astran.prefix(sound)));
    }
}
