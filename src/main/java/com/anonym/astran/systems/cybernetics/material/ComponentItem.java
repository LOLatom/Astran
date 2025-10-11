package com.anonym.astran.systems.cybernetics.material;

import com.anonym.astran.systems.assembly.IAssemblyComponent;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ComponentItem extends Item implements IAssemblyComponent {

    private final DeferredHolder<MaterialType, MaterialType> materialType;

    public ComponentItem(DeferredHolder<MaterialType, MaterialType> materialType, Properties properties) {
        super(properties);
        this.materialType = materialType;
    }

    @Override
    public MaterialType getMaterial() {
        return this.materialType.get();
    }
}
