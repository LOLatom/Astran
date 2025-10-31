package com.anonym.astran.api.swiff.scene;

import com.anonym.astran.helpers.StructureHelper;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.Optional;

public class StructureObject extends SceneObject {

    private final ResourceLocation structureLocation;

    private StructureTemplate template = null;

    public StructureObject(float x, float y, float z, float width, float height, float depth, ResourceLocation structureLocation) {
        super(x, y, z, width, height, depth);
        this.structureLocation = structureLocation;
        if (StructureHelper.structureCache.containsKey(structureLocation)) {
            Optional<StructureTemplate> temp = StructureHelper.getAsTemplate(structureLocation);
            temp.ifPresent(structureTemplate -> this.template = structureTemplate);
        }

    }

    @Override
    public void onRender(GuiGraphics graphics, float partialTicks) {
        if (this.template != null) {
            StructureHelper.renderStructure(graphics,this.template, true);
        } else {
            System.out.println(StructureHelper.structureCache);

            if (StructureHelper.structureCache.containsKey(this.structureLocation)) {
                System.out.println("NOT PRESENT 2");

                Optional<StructureTemplate> temp = StructureHelper.getAsTemplate(this.structureLocation);
                temp.ifPresent(structureTemplate -> this.template = structureTemplate);
            }
        }


    }
}
