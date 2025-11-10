package com.optimize.mixin;

import com.optimize.config.ModConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.render.RenderSystem.class)
public class RenderSystemMixin {
    
    @Inject(method = "enableBlend", at = @At("HEAD"), cancellable = true)
    private static void optimizeBlend(CallbackInfo ci) {
        if (!ModConfig.get().enabled || !ModConfig.get().optimizeRendering) {
            return;
        }
        
        // 在激进优化模式下，减少混合操作
        if (ModConfig.get().aggressiveOptimizations) {
            if (Math.random() < 0.1) { // 90%的情况下跳过混合
                ci.cancel();
            }
        }
    }
    
    @Inject(method = "disableCull", at = @At("HEAD"), cancellable = true)
    private static void optimizeCull(CallbackInfo ci) {
        if (!ModConfig.get().enabled || !ModConfig.get().optimizeRendering) {
            return;
        }
        
        // 优化剔除操作
        if (ModConfig.get().aggressiveOptimizations) {
            // 在激进模式下保持剔除开启以提高性能
            ci.cancel();
        }
    }
}