package com.anonym.astran.systems.cybernetics.core;

import com.anonym.astran.registries.AstranAttachmentTypeRegistry;
import com.anonym.astran.systems.attachments.SteelHeartReservoirData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Optional;

public class SetSteelHeartHandler {
    public class Client {
        public static void handleDataOnNetwork(SetSteelHeartPayload data, final IPayloadContext context) {
            Player player = context.player();
            SteelHeartReservoirData reservoirData = player.getData(AstranAttachmentTypeRegistry.STEEL_HEART_RESSERVOIR);
            if (reservoirData.getSteelHeart().isEmpty()) {
                player.setData(AstranAttachmentTypeRegistry.STEEL_HEART_RESSERVOIR,new SteelHeartReservoirData(Optional.of(data.stack())));
                player.getItemInHand(InteractionHand.MAIN_HAND).shrink(1);
            }

        }


    }
}
