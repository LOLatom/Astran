package com.anonym.astran.systems.assembly.storage;

import com.anonym.astran.systems.cybernetics.CyberModule;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InterfaceStorageData {

    public static final Codec<Map<String, Integer>> FORMULAS =
            Codec.unboundedMap(Codec.STRING, Codec.INT);

    public static final Codec<InterfaceStorageData> CODEC = RecordCodecBuilder.create(questInstance ->
            questInstance.group(
                    FORMULAS.fieldOf("formulas").forGetter(InterfaceStorageData::getFormulas)
            ).apply(questInstance, InterfaceStorageData::new));
    public static final StreamCodec<ByteBuf, InterfaceStorageData> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);

    private Map<String, Integer> formulas;


    public InterfaceStorageData(Map<String, Integer> formulas) {
        this.formulas = formulas;
    }


    public Map<String, Integer> getFormulas() {
        return this.formulas;
    }

    public int getFormulaCount(String string) {
        return this.formulas.get(string);
    }

    public void addFormula(String string, int count) {
        Map<String,Integer> newMap = new HashMap<>(this.formulas);
        newMap.replace(string,newMap.get(string) + count);
        this.formulas = newMap;
    }

}
