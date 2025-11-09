package com.example.optimise.mixin;

import com.example.optimise.OptimiseConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.ResourceReload;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = "reloadResources", at = @At("HEAD"))
    private void onReloadResources(CallbackInfoReturnable<ResourceReload> cir) {
        if (!OptimiseConfig.getInstance().isResourceUnloading()) {
            return;
        }
        
        // Resource unloading optimizations could be added here
        // For example, more aggressive resource cleanup
    }
    
    @Inject(method = "getFps", at = @At("RETURN"), cancellable = true)
    private void onGetFps(CallbackInfoReturnable<Integer> cir) {
        if (!OptimiseConfig.getInstance().isShowFps()) {
            return;
        }
        
        // This could be used to display FPS in a custom way
        // The actual FPS value is already calculated and returned
    }
}