package com.anonym.astran.systems.cybernetics;

import com.anonym.astran.registries.custom.AstranMaterialTypeRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CyberModule {

    public String moduleID;
    public LimbType attachment;
    public Quality quality;
    public int tier;
    public int color1,color2,color3;
    public List<MaterialType> materials;


    public static final Codec<LimbType> LIMB_TYPE_CODEC = Codec.STRING.xmap(LimbType::valueOf, Enum::name);
    public static final Codec<Quality> QUALITY_CODEC = Codec.STRING.xmap(Quality::valueOf, Enum::name);

    public static final Codec<CyberModule> CODEC = RecordCodecBuilder.create(questInstance ->
            questInstance.group(
                    Codec.STRING.fieldOf("moduleID").forGetter(CyberModule::getModuleID),
                    LIMB_TYPE_CODEC.fieldOf("attachment").forGetter(CyberModule::getAttachment),
                    QUALITY_CODEC.fieldOf("quality").forGetter(CyberModule::getQuality),
                    Codec.INT.fieldOf("tier").forGetter(CyberModule::getTier),
                    Codec.list(Codec.INT,3,3).fieldOf("colors").forGetter(cyberModule -> {
                        List<Integer> list = new ArrayList<>();
                        list.add(cyberModule.getColor1());
                        list.add(cyberModule.getColor2());
                        list.add(cyberModule.getColor3());
                        return list;
                    }),
                    Codec.list(MaterialType.CODEC).fieldOf("materials").forGetter(CyberModule::getMaterials)
            ).apply(questInstance, CyberModule::new));
    public static final StreamCodec<ByteBuf, CyberModule> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);


    public CyberModule(String moduleID, LimbType attachment,Quality quality, int tier,
                       int color1, int color2, int color3,
                       List<MaterialType> materials) {
        this.moduleID = moduleID;
        this.attachment = attachment;
        this.quality = quality;
        this.tier = tier;
        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;
        this.materials = materials;
    }
    public CyberModule(String moduleID,LimbType attachment, Quality quality, int tier, List<Integer> colors, List<MaterialType> materials) {
        this(moduleID,attachment,quality,tier,colors.get(0),colors.get(1), colors.get(3), materials);
    }
    public CyberModule(String moduleID, LimbType attachment, Quality quality, int tier , List<MaterialType> materials) {
        this(moduleID,attachment,quality,tier, Color.WHITE.getRGB(),Color.WHITE.getRGB(), Color.WHITE.getRGB(), materials);
    }
    public CyberModule(String moduleID, LimbType attachment, Quality quality, List<MaterialType> materials) {
        this(moduleID,attachment,quality,1, materials);
    }
    public CyberModule(String moduleID, LimbType attachment, List<MaterialType> materials) {
        this(moduleID,attachment,Quality.NORMAL, materials);
    }
    public CyberModule(String moduleID, LimbType attachment) {
        this(moduleID,attachment,Quality.NORMAL, new ArrayList<>());
        List<MaterialType> types = new ArrayList<>();
        types.add(AstranMaterialTypeRegistry.ELECTRUM.get());
    }

    @OnlyIn(Dist.CLIENT)
    public void render(AbstractClientPlayer entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {

    }
    public CyberModule withColor(Color first, Color second, Color third) {
        this.color1 = first.getRGB();
        this.color2 = second.getRGB();
        this.color3 = third.getRGB();
        return this;
    }

    public CyberModule withQuality(Quality quality) {
        this.quality = quality;
        return this;
    }

    public CyberModule withMaterials(List<MaterialType> materials) {
        this.materials = materials;
        return this;
    }

    public int getMaterialNeededCount() {
        return 1;
    }


    public List<MaterialType> getMaterials() {
        return this.materials;
    }

    public String getModuleID() {
        return this.moduleID;
    }

    public LimbType getAttachment() {
        return this.attachment;
    }

    public Quality getQuality() {
        return this.quality;
    }

    public int getColor1() {
        return this.color1;
    }

    public int getColor2() {
        return this.color2;
    }

    public int getColor3() {
        return this.color3;
    }

    public int getTier() {
        return this.tier;
    }

    public enum Quality {
        LESSER,
        NORMAL,
        GOOD,
        WELL_FORGED,
        PERFECT
    }
}
