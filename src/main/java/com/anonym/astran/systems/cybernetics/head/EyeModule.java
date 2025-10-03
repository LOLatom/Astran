package com.anonym.astran.systems.cybernetics.head;

import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.LimbType;
import com.anonym.astran.systems.cybernetics.MaterialType;

import java.util.List;

public class EyeModule extends CyberModule {
    public EyeModule(Quality quality, int tier, int color1, int color2, int color3, List<MaterialType> materials) {
        super("eye_module", LimbType.HEAD, quality, tier, color1, color2, color3, materials);
    }
}
