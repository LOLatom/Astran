package com.anonym.astran.networking;

import com.anonym.astran.systems.cybernetics.network.*;
import com.anonym.astran.systems.entity.breakableentity.BreakingStageHandler;
import com.anonym.astran.systems.entity.breakableentity.BreakingStagePayload;
import com.anonym.astran.systems.cybernetics.core.SetSteelHeartHandler;
import com.anonym.astran.systems.cybernetics.core.SetSteelHeartPayload;
import com.anonym.astran.systems.assembly.network.AssembleModuleHandler;
import com.anonym.astran.systems.assembly.network.AssembleModulePayload;
import com.anonym.astran.systems.world.structure.RequestStructureHandler;
import com.anonym.astran.systems.world.structure.RequestStructurePayload;
import com.anonym.astran.systems.world.structure.SendStructureDataHandler;
import com.anonym.astran.systems.world.structure.SendStructureDataPayload;
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

        registrar.playToServer(
                AssembleModulePayload.TYPE,
                AssembleModulePayload.STREAM_CODEC,
                AssembleModuleHandler.Server::handleDataOnNetwork);





        //Structure Requests
        registrar.playToServer(RequestStructurePayload.TYPE,
                RequestStructurePayload.STREAM_CODEC,
                RequestStructureHandler.Server::handleDataOnNetwork);

        registrar.playToClient(SendStructureDataPayload.TYPE,
                SendStructureDataPayload.STREAM_CODEC,
                SendStructureDataHandler.Client::handleDataOnNetwork);




        //CYBERNETICS-MANAGER
        registrar.playBidirectional(AddModulePayload.TYPE,
                AddModulePayload.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        AddModuleHandler.Client::handleDataOnNetwork,
                        AddModuleHandler.Server::handleDataOnNetwork
                ));

        registrar.playBidirectional(RemoveModulePayload.TYPE,
                RemoveModulePayload.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        RemoveModuleHandler.Client::handleDataOnNetwork,
                        RemoveModuleHandler.Server::handleDataOnNetwork
                ));

        registrar.playBidirectional(EquipModulePayload.TYPE,
                EquipModulePayload.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        EquipModuleHandler.Client::handleDataOnNetwork,
                        EquipModuleHandler.Server::handleDataOnNetwork
                ));

        registrar.playBidirectional(UnEquipModulePayload.TYPE,
                UnEquipModulePayload.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        UnEquipModuleHandler.Client::handleDataOnNetwork,
                        UnEquipModuleHandler.Server::handleDataOnNetwork
                ));

    }
}
