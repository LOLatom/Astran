package com.anonym.astran;

import com.anonym.astran.registries.*;
import com.anonym.astran.registries.client.AstranSoundRegistry;
import com.anonym.astran.registries.custom.AstranAssemblyRecipesRegistry;
import com.anonym.astran.registries.custom.AstranCyberModuleRegistry;
import com.anonym.astran.registries.custom.AstranMaterialTypeRegistry;
import net.minecraft.resources.ResourceLocation;
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

import java.util.Locale;

@Mod(Astran.MODID)
public class Astran {

    //First

    public static final String MODID = "astran";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Astran(IEventBus modEventBus, ModContainer modContainer) {


        NeoForge.EVENT_BUS.register(this);


        AstranMaterialTypeRegistry.MATERIAL_TYPE.register(modEventBus);
        AstranSoundRegistry.SOUND_EVENTS.register(modEventBus);
        AstranDataComponentRegistry.DATA_COMPONENTS.register(modEventBus);
        AstranEntityRegistry.ENTITIES.register(modEventBus);
        AstranBlockRegistry.BLOCKS.register(modEventBus);
        AstranBlockRegistry.ORE_BLOCKS.register(modEventBus);
        AstranItemRegistry.ITEMS.register(modEventBus);
        AstranAttachmentTypeRegistry.ATTACHMENT_TYPES.register(modEventBus);
        AstranItemBlockRegistry.BLOCK_ITEMS.register(modEventBus);
        AstranCyberModuleRegistry.CYBER_MODULE_TYPE.register(modEventBus);
        AstranAssemblyRecipesRegistry.ASSEMBLY_RECIPES.register(modEventBus);
        AstranItemTabs.CREATIVE_MODE_TABS.register(modEventBus);
        AstranItemBlockRegistry.registerItemsForBlocks();

        modEventBus.addListener(this::addCreative);


        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    public static ResourceLocation prefix(String name) {
        return ResourceLocation.fromNamespaceAndPath(MODID, name.toLowerCase(Locale.ROOT));
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }


}
