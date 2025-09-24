package com.anonym.astran.systems.attachments;

import com.anonym.astran.registries.AstranAttachmentTypeRegistry;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber
public class AttachmentAttach {

    @SubscribeEvent
    public static void attachToPlayer(PlayerEvent.Clone event) {
        System.out.println("[ASTRAN/DEBUG]: " + " Steel Heart Data Created : \n" + event.getEntity().getData(AstranAttachmentTypeRegistry.PLAYER_ADDED_DATA.get()));
    }

}
