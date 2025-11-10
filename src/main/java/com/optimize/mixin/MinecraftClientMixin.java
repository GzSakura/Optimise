package com.optimize.mixin;

import com.optimize.PerformanceOptimizeMod;
import com.optimize.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    
    @Inject(method = "render", at = @At("HEAD"))
    private void onRenderStart(boolean tick, CallbackInfo ci) {
        if (ModConfig.get().enabled) {
            PerformanceOptimizeMod.getPerformanceManager().onFrame();
        }
    }
    
    @Inject(method = "render", at = @At("RETURN"))
    private void onRenderEnd(boolean tick, CallbackInfo ci) {
        if (ModConfig.get().enabled && ModConfig.get().optimizeMemory) {
            // 渲染结束后可以进行一些清理工作
            if (Math.random() < 0.01) { // 1%的概率进行清理
                System.gc();
            }
        }
    }
    
    @Inject(method = "tick", at = @At("HEAD"))
    private void onTickStart(CallbackInfo ci) {
        if (ModConfig.get().enabled && ModConfig.get().optimizeMemory) {
            Runtime runtime = Runtime.getRuntime();
            long totalMemory = runtime.totalMemory();
            long freeMemory = runtime.freeMemory();
            long usedMemory = totalMemory - freeMemory;
            
            double memoryUsage = (double) usedMemory / totalMemory * 100;
            
            // 内存使用过高时进行清理
            if (memoryUsage > 85) {
                runtime.gc();
            }
        }
    }
}