package com.anonym.astran.systems.cybernetics.material;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class MaterialType {
    public static final Codec<MaterialType> CODEC = RecordCodecBuilder.create(questInstance ->
            questInstance.group(
                    Codec.STRING.fieldOf("materialID").forGetter(MaterialType::getMaterialID)
            ).apply(questInstance, MaterialType::new));
    public static final StreamCodec<ByteBuf, MaterialType> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);


    private final String materialID;
    private double protectionModifier = 0.0D;
    private double weightModifier = 1.0D;
    private float heatResistanceModifier = 1050.0F;
    private double corrosionResistanceModifier = 5.0F;
    private MaterialPalette colorPaletteModifier = new MaterialPalette(new Color(255,255,255));

    public MaterialType(String materialID) {
        this.materialID = materialID;
    }

    public String getMaterialID() {
        return this.materialID;
    }

    /**
     * This modifies the protection value of your material
     * @param protection is the Protection Value
     * @return your modified Material Instance
     */
    public MaterialType protection(double protection) {
        this.protectionModifier = protection;
        return this;
    }

    /**
     * This modifies the weight Value of your material
     * @param weight is the Weight Value in KG for 10cm^3 material;
     * @return your modified Material Instance
     */
    public MaterialType weight(double weight) {
        this.weightModifier = weight;
        return this;
    }

    /**
     * This modifies the Heat Resistance Value of your material
     * Affects Degradation of material cause of high temperature
     * @param heatResistance is the treshold at what temperature the material will degrade itself base is 1050.0°C
     * @return your modified Material Instance
     */
    public MaterialType heatResistance(float heatResistance) {
        this.heatResistanceModifier = heatResistance;
        return this;
    }

    /**
     * This modifies the Corrosion Resistance Value of your material
     * Affects the damage that a material will get cause of high acidity
     * @param corrosionResistance is the multiplier for the time of Damage from corrosion
     * @return your modified Material Instance
     */
    public MaterialType corrosionResistance(double corrosionResistance) {
        this.corrosionResistanceModifier = corrosionResistance;
        return this;
    }

    /**
     * This modifies the color palette of your material
     * @param colors Colors from Darker to Lighter
     * @return your modified Material Instance
     */
    public MaterialType colorPalette(Color... colors) {
        this.colorPaletteModifier = new MaterialPalette(colors);
        return this;
    }
    public MaterialType colorPalette(MaterialPalette palette) {
        this.colorPaletteModifier = palette;
        return this;
    }

    public MaterialPalette getColorPaletteModifier() {
        return this.colorPaletteModifier;
    }

    public static class MaterialPalette {

        private List<Color> colorPalette;

        public MaterialPalette(Color... colors) {
            this.colorPalette = Arrays.stream(colors).toList();
        }

        public List<Color> getColorPalette() {
            return this.colorPalette;
        }

        public Color getLighter() {
            return this.colorPalette.getFirst();
        }

        public Color getDarker() {
            return this.colorPalette.getLast();
        }


    }

}
