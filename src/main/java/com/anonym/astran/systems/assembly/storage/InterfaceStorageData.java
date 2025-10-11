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

    public static final Codec<Map<String, Integer>> STRING_INT_MAP =
            Codec.unboundedMap(Codec.STRING, Codec.INT);

    public static final Codec<InterfaceStorageData> CODEC = RecordCodecBuilder.create(questInstance ->
            questInstance.group(
                    Codec.list(CyberModule.CODEC).fieldOf("modules").forGetter(InterfaceStorageData::getCyberModules),
                    STRING_INT_MAP.fieldOf("formulas").forGetter(InterfaceStorageData::getFormulas)
            ).apply(questInstance, InterfaceStorageData::new));
    public static final StreamCodec<ByteBuf, InterfaceStorageData> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);

    private List<CyberModule> cyberModules;
    private Map<String, Integer> formulas;


    public InterfaceStorageData(List<CyberModule> modules, Map<String, Integer> formulas) {
        this.cyberModules = modules;
        this.formulas = formulas;
    }

    public List<CyberModule> getCyberModules() {
        return this.cyberModules;
    }

    public void setCyberModules(List<CyberModule> cyberModules) {
        this.cyberModules = cyberModules;
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

    public void addModule(CyberModule module) {
        List<CyberModule> list = new ArrayList<>(this.getCyberModules());
        list.add(module);
        this.cyberModules = list;
    }
}
