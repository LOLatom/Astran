package com.anonym.astran.networking;

import com.anonym.astran.systems.entity.breakableentity.BreakingStageHandler;
import com.anonym.astran.systems.entity.breakableentity.BreakingStagePayload;
import com.anonym.astran.systems.cybernetics.core.SetSteelHeartHandler;
import com.anonym.astran.systems.cybernetics.core.SetSteelHeartPayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber
public class NetworkRegistry {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.executesOn(HandlerThread.MAIN);

        registrar.playBidirectional(BreakingStagePayload.TYPE,
                BreakingStagePayload.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                    BreakingStageHandler.Client::handleDataOnNetwork,
                    BreakingStageHandler.Server::handleDataOnNetwork
                ));

        registrar.playToServer(
                SetSteelHeartPayload.TYPE,
                SetSteelHeartPayload.STREAM_CODEC,
                SetSteelHeartHandler.Client::handleDataOnNetwork);

    }
}
