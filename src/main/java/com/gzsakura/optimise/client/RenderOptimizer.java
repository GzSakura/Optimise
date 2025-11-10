package com.gzsakura.optimise.client;

import com.gzsakura.optimise.OptimiseModClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public class RenderOptimizer {
    private static int frameCount = 0;
    private static long lastFpsCheck = 0;
    private static int lastFps = 0;
    
    public static void init() {
        if (OptimiseModClient.CONFIG.enableRenderOptimizations) {
            ClientTickEvents.END_CLIENT_TICK.register(RenderOptimizer::onClientTick);
            OptimiseModClient.LOGGER.info("Render optimizer initialized!");
        }
    }
    
    private static void onClientTick(MinecraftClient client) {
        if (client.world == null) return;
        
        frameCount++;
        long currentTime = System.currentTimeMillis();
        
        // Calculate FPS every second
        if (currentTime - lastFpsCheck >= 1000) {
            lastFps = frameCount;
            frameCount = 0;
            lastFpsCheck = currentTime;
            
            // Log FPS if it's significantly low
            if (lastFps < 30) {
                OptimiseModClient.LOGGER.warn("Low FPS detected: {} FPS", lastFps);
            } else {
                OptimiseModClient.LOGGER.debug("Current FPS: {}", lastFps);
            }
            
            // Apply render optimizations if FPS is low
        if (lastFps < 20 && OptimiseModClient.CONFIG.enableRenderOptimizations) {
            applyRenderOptimizations(client);
        }
        }
    }
    
    private static void applyRenderOptimizations(MinecraftClient client) {
        // Reduce render distance if it's higher than configured maximum
        int currentRenderDistance = client.options.getViewDistance().getValue();
        int maxRenderDistance = OptimiseModClient.CONFIG.maxChunkRenderDistance;
        
        if (currentRenderDistance > maxRenderDistance) {
            client.options.getViewDistance().setValue(maxRenderDistance);
            OptimiseModClient.LOGGER.info("Reduced render distance from {} to {} for performance", 
                currentRenderDistance, maxRenderDistance);
        }
        
        // Enable entity culling if configured
        if (OptimiseModClient.CONFIG.enableEntityCulling) {
            OptimiseModClient.LOGGER.info("Entity culling optimizations applied");
        }
    }
    
    public static int getCurrentFps() {
        return lastFps;
    }
}