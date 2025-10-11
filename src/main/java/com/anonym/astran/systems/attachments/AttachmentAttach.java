package com.anonym.astran.systems.attachments;

import com.anonym.astran.registries.AstranAttachmentTypeRegistry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber
public class AttachmentAttach {

    @SubscribeEvent
    public static void attachToPlayer(PlayerEvent.Clone event) {
        System.out.println("[ASTRAN/DEBUG]: " + " Steel Heart Data Created : \n" + event.getEntity().getData(AstranAttachmentTypeRegistry.STEEL_HEART_RESSERVOIR));
        System.out.println("[ASTRAN/DEBUG]: " + " Interface Storage Created : \n" + event.getEntity().getData(AstranAttachmentTypeRegistry.INTERFACE_STORAGE_DATA));
        System.out.println("[ASTRAN/DEBUG]: " + " HEAD Storage Created : \n" + event.getEntity().getData(AstranAttachmentTypeRegistry.HEAD_STORAGE));
        System.out.println("[ASTRAN/DEBUG]: " + " TORSO Storage Created : \n" + event.getEntity().getData(AstranAttachmentTypeRegistry.TORSO_STORAGE));
        System.out.println("[ASTRAN/DEBUG]: " + " HIP Storage Created : \n" + event.getEntity().getData(AstranAttachmentTypeRegistry.HIP_STORAGE));
        System.out.println("[ASTRAN/DEBUG]: " + " RIGHT LEG Storage Created : \n" + event.getEntity().getData(AstranAttachmentTypeRegistry.RIGHT_LEG_STORAGE));
        System.out.println("[ASTRAN/DEBUG]: " + " LEFT LEG Storage Created : \n" + event.getEntity().getData(AstranAttachmentTypeRegistry.LEFT_LEG_STORAGE));
        System.out.println("[ASTRAN/DEBUG]: " + " RIGHT SHOULDER Storage Created : \n" + event.getEntity().getData(AstranAttachmentTypeRegistry.RIGHT_SHOULDER_STORAGE));
        System.out.println("[ASTRAN/DEBUG]: " + " LEFT SHOULDER Storage Created : \n" + event.getEntity().getData(AstranAttachmentTypeRegistry.LEFT_SHOULDER_STORAGE));
        System.out.println("[ASTRAN/DEBUG]: " + " RIGHT HAND Storage Created : \n" + event.getEntity().getData(AstranAttachmentTypeRegistry.RIGHT_HAND_STORAGE));
        System.out.println("[ASTRAN/DEBUG]: " + " LEFT HAND Storage Created : \n" + event.getEntity().getData(AstranAttachmentTypeRegistry.LEFT_HAND_STORAGE));

    }

}
