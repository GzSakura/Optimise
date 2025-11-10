package com.optimize.mixin;

import com.optimize.config.ModConfig;
import com.optimize.manager.RenderingOptimizer;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkBuilder.class)
public class ChunkBuilderMixin {
    
    @Inject(method = "rebuild", at = @At("HEAD"), cancellable = true)
    private void optimizeChunkBuilding(CallbackInfo ci) {
        if (!ModConfig.get().enabled || !ModConfig.get().optimizeRendering) {
            return;
        }
        
        // 在激进优化模式下，减少区块重建频率
        if (ModConfig.get().aggressiveOptimizations) {
            if (Math.random() < 0.3) { // 30%的概率跳过重建
                ci.cancel();
            }
        }
    }
}