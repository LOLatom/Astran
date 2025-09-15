package com.anonym.electron.registries;

import com.anonym.electron.Electron;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ElectronItemBlockRegistry {
    public static final DeferredRegister.Items BLOCK_ITEMS = DeferredRegister.createItems(Electron.MODID);


    public static void registerItemsForBlocks() {
        ElectronBlockRegistry.ORE_BLOCKS.getEntries().stream().forEach((deferredHolder)-> {
            BLOCK_ITEMS.registerSimpleBlockItem(deferredHolder.getId().getPath(),deferredHolder);
        });
        ElectronBlockRegistry.BLOCKS.getEntries().stream().forEach((deferredHolder)-> {
            BLOCK_ITEMS.registerSimpleBlockItem(deferredHolder.getId().getPath(),deferredHolder);
        });
    }

}
