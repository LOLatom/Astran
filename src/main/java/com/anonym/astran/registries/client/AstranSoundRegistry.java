package com.anonym.astran.registries.client;

import com.anonym.astran.Astran;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AstranSoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, Astran.MODID);


    public static final DeferredHolder<SoundEvent,SoundEvent> STEEL_HEART_INSERT = createEvent("steel_heart_insert");



    private static DeferredHolder<SoundEvent, SoundEvent> createEvent(String sound) {
        return SOUND_EVENTS.register(sound, () -> SoundEvent.createVariableRangeEvent(Astran.prefix(sound)));
    }
}
