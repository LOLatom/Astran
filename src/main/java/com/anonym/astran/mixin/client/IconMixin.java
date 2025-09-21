package com.anonym.astran.mixin.client;

import com.mojang.blaze3d.platform.IconSet;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.resources.IoSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.InputStream;
import java.util.List;

@Mixin(IconSet.class)
public class IconMixin {

    @Inject(method = "getStandardIcons", at = @At("HEAD") , cancellable = true)
    public void getNewStandardsIcons(PackResources resources, CallbackInfoReturnable<List<IoSupplier<InputStream>>> cir) {
        List<IoSupplier<InputStream>> customIcons = List.of(
                createIconSupplier("icon_16x16.png"),
                createIconSupplier("icon_32x32.png"),
                createIconSupplier("icon_48x48.png"),
                createIconSupplier("icon_128x128.png"),
                createIconSupplier("icon_256x256.png")
        );

        customIcons.forEach(icon -> System.out.println("Loaded icon supplier: " + icon));

        cir.setReturnValue(customIcons);
    }

    private IoSupplier<InputStream> createIconSupplier(String fileName) {
        return () -> {
            InputStream stream = IconMixin.class.getClassLoader().getResourceAsStream("assets/astran/icons/" + fileName);
            if (stream == null) {
                System.err.println("Icon not found: " + fileName);
            }
            return stream;
        };    }

}
