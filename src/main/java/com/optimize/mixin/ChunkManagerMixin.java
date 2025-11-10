package com.optimize.mixin;

import com.optimize.config.ModConfig;
import net.minecraft.server.world.ChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkManager.class)
public class ChunkManagerMixin {
    
    @Shadow
    private ServerWorld world;
    
    @Inject(method = "tick", at = @At("HEAD"))
    private void optimizeChunkLoading(CallbackInfo ci) {
        if (!ModConfig.get().enabled || !ModConfig.get().optimizeChunkLoading) {
            return;
        }
        
        // 限制区块加载半径
        ChunkManager manager = (ChunkManager) (Object) this;
        
        // 这里可以添加更复杂的区块加载优化逻辑
        // 例如：根据玩家距离动态调整加载优先级
        if (world != null) {
            world.getPlayers().forEach(player -> {
                ChunkPos playerPos = player.getChunkPos();
                int radius = ModConfig.get().chunkLoadRadius;
                
                // 只加载指定半径内的区块
                for (int dx = -radius; dx <= radius; dx++) {
                    for (int dz = -radius; dz <= radius; dz++) {
                        ChunkPos pos = new ChunkPos(playerPos.x + dx, playerPos.z + dz);
                        // 这里可以添加额外的加载逻辑
                    }
                }
            });
        }
    }
    
    @Inject(method = "unload", at = @At("HEAD"), cancellable = true)
    private void optimizeChunkUnloading(long pos, CallbackInfo ci) {
        if (!ModConfig.get().enabled || !ModConfig.get().optimizeChunkLoading) {
            return;
        }
        
        // 延迟卸载区块，减少频繁的加载/卸载
        if (Math.random() < 0.1) { // 10%的概率延迟卸载
            ci.cancel();
        }
    }
}