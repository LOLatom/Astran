package com.anonym.astran.systems.cybernetics;

import com.anonym.astran.Astran;
import com.anonym.astran.client.models.modules.ModuleModel;
import com.anonym.astran.helpers.UUIDHelper;
import com.anonym.astran.registries.custom.AstranRegistries;
import com.anonym.astran.systems.cybernetics.material.MaterialType;
import com.anonym.astran.systems.gui.theinterface.pages.LimbInterface;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.realmsclient.dto.Ops;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import foundry.veil.api.client.render.rendertype.VeilRenderType;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagNetworkSerialization;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.*;
import java.util.List;

public class CyberModule {

    protected final Color ADJUSTMENT_COLOR = new Color(15,15,15);

    private final UUID instanceId;

    private String moduleID;
    private LimbType attachment;
    private Quality quality;
    private int tier;
    private Integer color1, color2, color3;
    private Map<String, MaterialType> materials = new HashMap<>();

    private ModuleModel model = null;
    private ModuleModel onPlayerModel = null;

    @Nullable
    private CompoundTag additionalData = null;

    public static final Codec<Map<String, MaterialType>> MATERIAL_MAP_CODEC =
            Codec.unboundedMap(Codec.STRING, MaterialType.CODEC);

    public static final Codec<Quality> QUALITY_CODEC = Codec.STRING.xmap(Quality::valueOf, Enum::name);

    public static final Codec<CyberModule> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    UUIDHelper.CODEC.optionalFieldOf("instanceId").forGetter(cm -> Optional.ofNullable(cm.instanceId)),
                    Codec.STRING.fieldOf("moduleID").forGetter(CyberModule::getModuleID),
                    LimbType.CODEC.fieldOf("attachment").forGetter(CyberModule::getAttachment),
                    QUALITY_CODEC.fieldOf("quality").forGetter(CyberModule::getQuality),
                    Codec.INT.fieldOf("tier").forGetter(CyberModule::getTier),

                    Codec.INT.optionalFieldOf("color1").forGetter(cm -> Optional.ofNullable(cm.color1)),
                    Codec.INT.optionalFieldOf("color2").forGetter(cm -> Optional.ofNullable(cm.color2)),
                    Codec.INT.optionalFieldOf("color3").forGetter(cm -> Optional.ofNullable(cm.color3)),

                    Codec.list(Codec.INT).optionalFieldOf("colors").forGetter(cm -> Optional.empty()),

                    MATERIAL_MAP_CODEC.fieldOf("materials").forGetter(CyberModule::getMaterials),
                    CompoundTag.CODEC.optionalFieldOf("additionalData").forGetter(cm -> Optional.ofNullable(cm.additionalData))
            ).apply(instance, (idOpt, moduleID, attachment, quality, tier,
                               color1Opt, color2Opt, color3Opt,
                               legacyColorsOpt,
                               materials,tagOpt) -> {

                Integer finalC1 = color1Opt.orElseGet(() ->
                        legacyColorsOpt.map(l -> l.size() > 0 ? l.get(0) : null).orElse(null));
                Integer finalC2 = color2Opt.orElseGet(() ->
                        legacyColorsOpt.map(l -> l.size() > 1 ? l.get(1) : null).orElse(null));
                Integer finalC3 = color3Opt.orElseGet(() ->
                        legacyColorsOpt.map(l -> l.size() > 2 ? l.get(2) : null).orElse(null));

                return new CyberModule(
                        idOpt.orElse(UUID.randomUUID()),
                        moduleID,
                        attachment,
                        quality,
                        tier,
                        finalC1,
                        finalC2,
                        finalC3,
                        materials,
                        tagOpt.orElse(null)
                );
            })
    );

    public static final StreamCodec<ByteBuf, CyberModule> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);

    public CyberModule(UUID instanceId, String moduleID, LimbType attachment, Quality quality, int tier,
                       Integer color1, Integer color2, Integer color3, Map<String, MaterialType> materials ,
                       @Nullable CompoundTag data) {
        this.instanceId = instanceId;
        this.moduleID = moduleID;
        this.attachment = attachment;
        this.quality = quality;
        this.tier = tier;
        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;
        this.materials = materials;
        this.additionalData = data;
    }

    public CyberModule(String moduleID, LimbType attachment, Quality quality, int tier,
                       int color1, int color2, int color3, Map<String, MaterialType> materials, @Nullable CompoundTag data) {
        this(UUID.randomUUID(), moduleID, attachment, quality, tier, color1, color2, color3, materials, data);
    }

    public CyberModule(String moduleID, LimbType attachment, Quality quality, int tier, Map<String, MaterialType> materials, @Nullable CompoundTag data) {
        this(UUID.randomUUID(), moduleID, attachment, quality, tier, null, null, null, materials, data);
    }

    public CyberModule(String moduleID, LimbType attachment, Quality quality, Map<String, MaterialType> materials) {
        this(moduleID, attachment, quality, 1, materials,null);
    }

    public CyberModule(String moduleID, LimbType attachment, Map<String, MaterialType> materials) {
        this(moduleID, attachment, Quality.NORMAL, materials);
    }

    public CyberModule(String moduleID, LimbType attachment) {
        this(moduleID, attachment, Quality.NORMAL, new HashMap<>());
    }

    @OnlyIn(Dist.CLIENT)
    public void render(CyberModule module, AbstractClientPlayer player, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, boolean inDisplay) {
        this.setModelIfAbsent();
    }

    public void renderWithMaterialLayer(AbstractClientPlayer player, PoseStack poseStack, CyberModule module, MultiBufferSource buffer, int packedLight, String locationStart, int materialAmount, boolean isLastModified
    ) {
        if (materialAmount <= 0) return;

        List<MaterialType> materials = new ArrayList<>(module.getMaterials().values());
        int count = Math.min(materials.size(), materialAmount);

        for (int i = 0; i < count; i++) {
            MaterialType type = materials.get(i);
            boolean isFinal = (i == count - 1);

            poseStack.pushPose();

            ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(
                    Astran.MODID,
                    locationStart + type.getMaterialID() + (i + 1) + ".png"
            );

            VertexConsumer consumer = buffer.getBuffer(VeilRenderType.entityCutoutNoCull(texture));

            int color = (isFinal && !isLastModified) ? Color.WHITE.getRGB() : ADJUSTMENT_COLOR.getRGB();

            this.model().getMainPart().render(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, color);

            poseStack.popPose();
        }

        MaterialType lastType = materials.get(count - 1);

        poseStack.pushPose();
        poseStack.scale(0, 0, 0);
        ResourceLocation fakeTexture = ResourceLocation.fromNamespaceAndPath(
                Astran.MODID,
                locationStart + lastType.getMaterialID() + "1.png"
        );

        VertexConsumer fakeConsumer = buffer.getBuffer(VeilRenderType.entityCutoutNoCull(fakeTexture));
        this.model().getMainPart().render(poseStack, fakeConsumer, packedLight, OverlayTexture.NO_OVERLAY, ADJUSTMENT_COLOR.getRGB());


        poseStack.popPose();
    }
    public void renderOnPlayer(AbstractClientPlayer player, PoseStack poseStack, CyberModule module, MultiBufferSource buffer, int packedLight, String locationStart, int materialAmount, boolean isLastModified
    ) {
        if (materialAmount <= 0) return;

        List<MaterialType> materials = new ArrayList<>(module.getMaterials().values());
        int count = Math.min(materials.size(), materialAmount);

        for (int i = 0; i < count; i++) {
            MaterialType type = materials.get(i);
            boolean isFinal = (i == count - 1);

            poseStack.pushPose();

            ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(
                    Astran.MODID,
                    locationStart + type.getMaterialID() + (i + 1) + ".png"
            );

            VertexConsumer consumer = buffer.getBuffer(VeilRenderType.entityCutoutNoCull(texture));

            int color = Color.WHITE.getRGB();

            this.model().getMainPart().render(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, color);

            poseStack.popPose();
        }

        MaterialType lastType = materials.get(count - 1);

        poseStack.pushPose();
        poseStack.scale(0, 0, 0);
        ResourceLocation fakeTexture = ResourceLocation.fromNamespaceAndPath(
                Astran.MODID,
                locationStart + lastType.getMaterialID() + "1.png"
        );

        VertexConsumer fakeConsumer = buffer.getBuffer(VeilRenderType.entityCutoutNoCull(fakeTexture));
        this.model().getMainPart().render(poseStack, fakeConsumer, packedLight, OverlayTexture.NO_OVERLAY, Color.WHITE.getRGB());


        poseStack.popPose();
    }
    public void renderSingleMaterialLayer(AbstractClientPlayer player, PoseStack poseStack, CyberModule module, MultiBufferSource buffer, int packedLight, String locationStart) {

        List<MaterialType> materials = new ArrayList<>(module.getMaterials().values());

            poseStack.pushPose();

            ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(
                    Astran.MODID,
                    locationStart + materials.get(0).getMaterialID() + "1.png"
            );

            VertexConsumer consumer = buffer.getBuffer(VeilRenderType.entityCutoutNoCull(texture));
        if (Minecraft.getInstance().screen instanceof LimbInterface) {
            this.model().getMainPart().render(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, ADJUSTMENT_COLOR.getRGB());

        } else {
            this.model().getMainPart().render(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, Color.WHITE.getRGB());

        }

            poseStack.popPose();


        poseStack.popPose();
    }

    public void renderMaterialLayer(AbstractClientPlayer player, PoseStack poseStack, CyberModule module, MultiBufferSource buffer, int packedLight,float partialTicks, String locationStart, int materialAmount) {
        VertexConsumer consumer;
        int i = 0;
        for (MaterialType type : module.getMaterials().values()) {

            if (i < materialAmount - 1) {
                    poseStack.pushPose();
                consumer = buffer.getBuffer(VeilRenderType.entityCutoutNoCull(
                            ResourceLocation.fromNamespaceAndPath(Astran.MODID, locationStart + type.getMaterialID() + String.valueOf(i + 1) + ".png")));

                    this.model().getMainPart().render(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, Color.WHITE.getRGB());
                    poseStack.popPose();
                } else if (i < materialAmount) {

                    poseStack.pushPose();
                consumer = buffer.getBuffer(VeilRenderType.entityCutoutNoCull(
                            ResourceLocation.fromNamespaceAndPath(Astran.MODID, locationStart + type.getMaterialID() + String.valueOf(i + 1) + ".png")));

                    this.model().getMainPart().render(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, Color.WHITE.getRGB());
                    poseStack.popPose();
                }
                poseStack.pushPose();
                poseStack.scale(0,0,0);
                consumer = buffer.getBuffer(VeilRenderType.entityCutoutNoCull(
                        ResourceLocation.fromNamespaceAndPath(Astran.MODID, locationStart + type.getMaterialID() + "1" + ".png")));
            this.model().getMainPart().render(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, Color.WHITE.getRGB());

            poseStack.popPose();
            i++;
        }

    }

    protected float onPlayerTakeDamage(CyberModule module, DamageSource source, Player player, float damage) {
        return damage;
    }
    public final float playerTakeDamage(CyberModule module, DamageSource source, Player player, float damage) {
        return this.getPrimitiveClass().onPlayerTakeDamage(module,source,player, damage);
    }


    protected boolean canTick() {
        return false;
    }

    public final boolean isTicking() {
        return this.getPrimitiveClass().canTick();
    }

    protected boolean canBeEquipped(CyberModule module, CyberneticsManager manager, SocketData socket) {
        return true;
    }

    public final boolean isEquippable(CyberModule module, CyberneticsManager manager, SocketData socket) {
        return this.getPrimitiveClass().canBeEquipped(module, manager,socket);
    }

    protected boolean canBeCollected(CyberModule module, CyberneticsManager manager) {
        return true;
    }

    public final boolean isCollectable(CyberModule module, CyberneticsManager manager) {
        return this.getPrimitiveClass().canBeCollected(module, manager);
    }

    protected void tick(CyberModule module, Player player) {

    }

    public final void tickModule(Player player) {
        this.getPrimitiveClass().tick(this,player);
    }

    public CyberModule getPrimitiveClass() {
        return AstranRegistries.CYBER_MODULE_REGISTRY
                .get(ResourceLocation.fromNamespaceAndPath(Astran.MODID,this.getModuleID()));
    }

    public void setAdditionalData(@Nullable CompoundTag additionalData) {
        this.additionalData = additionalData;
    }


    public CyberModule withColor(Integer first, Integer second, Integer third) {
        this.color1 = first;
        this.color2 = second;
        this.color3 = third;
        return this;
    }

    public CyberModule withColor(Color first, Color second, Color third) {
        this.color1 = first != null ? first.getRGB() : null;
        this.color2 = second != null ? second.getRGB() : null;
        this.color3 = third != null ? third.getRGB() : null;
        return this;
    }

    public CyberModule clearColors() {
        this.color1 = null;
        this.color2 = null;
        this.color3 = null;
        return this;
    }

    public CyberModule withQuality(Quality quality) {
        this.quality = quality;
        return this;
    }

    public CyberModule withMaterials(Map<String, MaterialType> materials) {
        this.materials = materials;
        return this;
    }

    public int getMaterialNeededCount() {
        return 1;
    }

    public void setModelIfAbsent() {
        if (this.model == null) {
            this.model = this.getModelLayer();
            this.onPlayerModel = getOnModelLayer();
        }
    }

    public float[] getRotations(CyberModule module, AbstractClientPlayer player, float partialTicks) {
        float[] rot = {0f,0f,0f};
        return rot;
    }
    public float[] getPositionOffset(CyberModule module, AbstractClientPlayer player, float partialTicks) {
        float[] pos = {0f,0f,0f};
        return pos;
    }
    public float[] getScale(CyberModule module, AbstractClientPlayer player, float partialTicks) {
        float[] scale = {1f,1f,1f};
        return scale;
    }

    public ModuleModel model() {
        return this.model;
    }

    public ModuleModel onPlayerModel() {
        return this.onPlayerModel;
    }

    public ModuleModel getModelLayer() {
        return null;
    }

    public ModuleModel getOnModelLayer() {
        return null;
    }

    public Map<String, MaterialType> getMaterials() {
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

    public boolean hasMask() {return false;}

    public boolean firstMaskActive() {return false;}
    public boolean secondMaskActive() {return false;}

    public Optional<Integer> getColor1Optional() { return Optional.ofNullable(this.color1); }
    public Optional<Integer> getColor2Optional() { return Optional.ofNullable(this.color2); }
    public Optional<Integer> getColor3Optional() { return Optional.ofNullable(this.color3); }

    public int getColor1() { return this.color1 != null ? this.color1 : Color.WHITE.getRGB(); }
    public int getColor2() { return this.color2 != null ? this.color2 : Color.WHITE.getRGB(); }
    public int getColor3() { return this.color3 != null ? this.color3 : Color.WHITE.getRGB(); }

    public boolean hasAnyColor() {
        return this.color1 != null || this.color2 != null || this.color3 != null;
    }

    public boolean hasAllColors() {
        return this.color1 != null && this.color2 != null && this.color3 != null;
    }

    public int getTier() {
        return this.tier;
    }

    public String getSubType() {
        return "none";
    }

    public UUID getInstanceId() {
        return this.instanceId;
    }

    @Nullable
    public Optional<CompoundTag> getAdditionalData() {
        return Optional.ofNullable(this.additionalData);
    }

    public CompoundTag getOrCreateTag() {
        if (this.additionalData == null) this.additionalData = new CompoundTag();
        return this.additionalData;
    }


    public CyberModule copy() {
        return new CyberModule(
                UUID.randomUUID(),
                this.moduleID,
                this.attachment,
                this.quality,
                this.tier,
                this.color1,
                this.color2,
                this.color3,
                new HashMap<>(this.materials),
                this.additionalData
        );
    }

    public enum Quality {
        LESSER,
        NORMAL,
        GOOD,
        WELL_FORGED,
        PERFECT
    }
}