package com.optimize.mixin;

import com.optimize.config.ModConfig;
import com.optimize.manager.RenderingOptimizer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    
    @Inject(method = "method_15554", at = @At("HEAD"), cancellable = true)
    private void optimizeChunkRendering(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (!ModConfig.get().enabled || !ModConfig.get().optimizeRendering) {
            return;
        }
        
        // 获取相机位置
        WorldRenderer renderer = (WorldRenderer) (Object) this;
        Vec3d cameraPos = renderer.getCamera().getPos();
        
        if (!RenderingOptimizer.shouldRenderChunk(pos, cameraPos)) {
            cir.setReturnValue(false);
        }
    }
    
    @Inject(method = "render", at = @At("HEAD"))
    private void onRenderStart(CallbackInfoReturnable<Void> cir) {
        if (ModConfig.get().enabled && ModConfig.get().optimizeRendering) {
            // 清理过多的粒子
            RenderingOptimizer.cleanupParticles();
        }
    }
}