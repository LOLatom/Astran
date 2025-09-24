package com.anonym.astran.systems.energy.node;

import com.anonym.astran.systems.energy.CoreNodeItem;
import net.minecraft.world.item.Item;

import java.awt.*;

public class AstraniumNodeItem extends CoreNodeItem {

    private static final Color nodeColor = new Color(240,112,210);

    public AstraniumNodeItem(Properties properties) {
        super(properties);
    }


    @Override
    public Color getNodeColor() {
        return nodeColor;
    }


    @Override
    public String energyType() {
        return "Astra";
    }
}
