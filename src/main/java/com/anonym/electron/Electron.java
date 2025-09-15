package com.anonym.electron;

import com.anonym.electron.registries.*;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(Electron.MODID)
public class Electron {

    //First

    public static final String MODID = "electron";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Electron(IEventBus modEventBus, ModContainer modContainer) {


        NeoForge.EVENT_BUS.register(this);

        ElectronEntityRegistry.ENTITIES.register(modEventBus);
        ElectronBlockRegistry.BLOCKS.register(modEventBus);
        ElectronBlockRegistry.ORE_BLOCKS.register(modEventBus);
        ElectronItemRegistry.ITEMS.register(modEventBus);
        ElectronAttachmentTypeRegistry.ATTACHMENT_TYPES.register(modEventBus);
        ElectronItemBlockRegistry.BLOCK_ITEMS.register(modEventBus);
        ElectronItemBlockRegistry.registerItemsForBlocks();

        modEventBus.addListener(this::addCreative);


        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }


}
