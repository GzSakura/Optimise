package com.optimize.mixin;

import com.optimize.config.ModConfig;
import com.optimize.manager.RenderingOptimizer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    
    @Inject(method = "reload", at = @At("HEAD"))
    private void onReload(CallbackInfo ci) {
        if (ModConfig.get().enabled && ModConfig.get().optimizeRendering) {
            // 清理过多的粒子
            RenderingOptimizer.cleanupParticles();
        }
    }
    
    @Inject(method = "render", at = @At("HEAD"))
    private void onRenderStart(CallbackInfo ci) {
        if (ModConfig.get().enabled && ModConfig.get().optimizeRendering) {
            // 清理过多的粒子
            RenderingOptimizer.cleanupParticles();
        }
    }
}