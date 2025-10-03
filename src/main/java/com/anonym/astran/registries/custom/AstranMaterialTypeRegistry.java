package com.anonym.astran.registries.custom;

import com.anonym.astran.Astran;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.MaterialType;
import foundry.veil.platform.registry.RegistryObject;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.awt.*;

public class AstranMaterialTypeRegistry {

    public static final DeferredRegister<MaterialType> MATERIAL_TYPE = DeferredRegister.create(AstranRegistries.MATERIAL_TYPE_REGISTRY, Astran.MODID);

    public static final DeferredHolder<MaterialType, MaterialType> ELECTRUM = MATERIAL_TYPE.register("electrum",
            () -> new MaterialType("electrum").heatResistance(1500F).corrosionResistance(4.0D).protection(4.0D).weight(3.5D)
                    .colorPalette(
                            new Color(190,185,198),
                            new Color(122,113,135),
                            new Color(94,91,101),
                            new Color(74,74,85),
                            new Color(50,58,74),
                            new Color(30,46,47),
                            new Color(24,33,29)));

    public static final DeferredHolder<MaterialType, MaterialType> BRONZINE = MATERIAL_TYPE.register("bronzine",
            () -> new MaterialType("bronzine").heatResistance(1250F).corrosionResistance(2.0D).protection(6.5D).weight(4.2D)
                    .colorPalette(
                            new Color(250,181,137),
                            new Color(201,152,93),
                            new Color(171,133,87),
                            new Color(140,109,72),
                            new Color(87,60,44),
                            new Color(56,38,33),
                            new Color(33,25,24)));

    public static final DeferredHolder<MaterialType, MaterialType> INFERNUM = MATERIAL_TYPE.register("infernum",
            () -> new MaterialType("infernum").heatResistance(3500F).corrosionResistance(3.8D).protection(6.0D).weight(2.8D)
                    .colorPalette(
                            new Color(255,171,115),
                            new Color(189,100,55),
                            new Color(156,37,28),
                            new Color(120,31,25),
                            new Color(89,33,29),
                            new Color(61,24,30),
                            new Color(33,18,21)));

}
