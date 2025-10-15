package com.anonym.astran.helpers;

import com.anonym.astran.mixin.common.StructureTemplateAccessor;
import com.anonym.astran.systems.world.structure.RequestStructurePayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.neoforged.neoforge.client.model.data.ModelData;

import java.util.*;

public class StructureHelper {

    public static Map<ResourceLocation, CompoundTag> structureCache = new HashMap<>();
    public static Map<String,StructureTemplate> templateCache = new HashMap<>();

    public static Optional<StructureTemplate> getAsTemplate(ResourceLocation location) {
        if (structureCache.containsKey(location)) {
            StructureTemplate template = new StructureTemplate();
            template.load(BuiltInRegistries.BLOCK.asLookup(),structureCache.get(location));
            return Optional.of(template);
        } else {
            return Optional.empty();
        }
    }

    public static void storeInTemplateCache(String name, StructureTemplate template) {
        templateCache.put(name,template);
    }

    public static void clientRequestStructures(ResourceLocation... locations) {
        List<ResourceLocation> locationList = new ArrayList<>();
        for (ResourceLocation location : locations) {
            if (!structureCache.containsKey(location)) {
                locationList.add(location);
            }
        }
        System.out.println("SENDING : " + locationList);
        Minecraft.getInstance().getConnection().send(new RequestStructurePayload(locationList));
    }

    public static List<Block> getStructureBlockList(StructureTemplate template) {
        StructureTemplateAccessor accessor = (StructureTemplateAccessor) template;
        List<Block> blocks = new ArrayList<>();
        for (StructureTemplate.Palette palette : accessor.getPalettes()) {
            for (StructureTemplate.StructureBlockInfo info : palette.blocks()) {
                if (!blocks.contains(info.state().getBlock())) {
                    blocks.add(info.state().getBlock());
                }
            }
        }
        return blocks;
    }


    public static void renderStructure(GuiGraphics guiGraphics, StructureTemplate structure, boolean center) {

        for (StructureTemplate.Palette palette : ((StructureTemplateAccessor) structure).getPalettes()) {
            for (StructureTemplate.StructureBlockInfo info : palette.blocks()) {
                guiGraphics.pose().pushPose();
                if (center) {
                    guiGraphics.pose().translate(
                            -((float) structure.getSize(Rotation.NONE).getX() / 2),
                            -((float) structure.getSize(Rotation.NONE).getY() / 2),
                            -((float) structure.getSize(Rotation.NONE).getZ() / 2));
                }
                guiGraphics.pose().translate(info.pos().getX(), info.pos().getY(),info.pos().getZ());

                Minecraft.getInstance().getBlockRenderer().getModelRenderer()
                        .renderModel(guiGraphics.pose().last(),
                                guiGraphics.bufferSource().getBuffer(RenderType.cutoutMipped()),info.state(),
                                Minecraft.getInstance().getBlockRenderer().getBlockModel(info.state())
                                ,1f,1f,1f,15728880, OverlayTexture.NO_OVERLAY
                                , ModelData.EMPTY,(RenderType)null);
                guiGraphics.pose().popPose();
            }
        }

    }





}
