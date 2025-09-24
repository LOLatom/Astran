package com.anonym.astran.systems.energy;

import net.minecraft.world.item.Item;

public class CoreNodeItem extends Item implements INodeItem {
    public CoreNodeItem(Properties properties) {
        super(properties);
    }

    public String getEnergyID() {
        return this.energyType().toLowerCase();
    }

}
