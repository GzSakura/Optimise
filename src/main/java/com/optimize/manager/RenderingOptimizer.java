package com.optimize.manager;

import com.optimize.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import java.util.ArrayList;
import java.util.List;

public class RenderingOptimizer {
    private static final List<Particle> activeParticles = new ArrayList<>();
    private static int currentParticleCount = 0;
    
    public static void initialize() {
        // 初始化渲染优化器
        System.out.println("[性能优化] 渲染优化器已初始化");
    }
    
    public static boolean shouldRenderEntity(Entity entity, Vec3d cameraPos, double maxDistance) {
        if (!ModConfig.get().optimizeRendering || !ModConfig.get().reduceEntityRendering) {
            return true;
        }
        
        double distance = entity.getPos().distanceTo(cameraPos);
        return distance <= maxDistance && distance <= ModConfig.get().entityRenderDistance;
    }
    
    public static boolean shouldRenderParticle(Particle particle) {
        if (!ModConfig.get().optimizeRendering) {
            return true;
        }
        
        if (currentParticleCount >= ModConfig.get().maxParticles) {
            return false;
        }
        
        // 检查粒子是否可见
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) {
            return false;
        }
        
        try {
            // 使用反射获取粒子的x、y、z坐标
            java.lang.reflect.Field xField = Particle.class.getDeclaredField("x");
            java.lang.reflect.Field yField = Particle.class.getDeclaredField("y");
            java.lang.reflect.Field zField = Particle.class.getDeclaredField("z");
            
            xField.setAccessible(true);
            yField.setAccessible(true);
            zField.setAccessible(true);
            
            double particleX = xField.getDouble(particle);
            double particleY = yField.getDouble(particle);
            double particleZ = zField.getDouble(particle);
            
            // 计算与玩家的距离
            double dx = particleX - client.player.getX();
            double dy = particleY - client.player.getY();
            double dz = particleZ - client.player.getZ();
            double distanceSq = dx * dx + dy * dy + dz * dz;
            double distance = Math.sqrt(distanceSq);
            return distance <= 64; // 只渲染64格内的粒子
        } catch (Exception e) {
            // 如果无法获取粒子位置，则默认渲染粒子
            return true;
        }
    }
    
    public static void onParticleAdded(Particle particle) {
        if (shouldRenderParticle(particle)) {
            activeParticles.add(particle);
            currentParticleCount++;
        }
    }
    
    public static void onParticleRemoved(Particle particle) {
        activeParticles.remove(particle);
        currentParticleCount = Math.max(0, currentParticleCount - 1);
    }
    
    public static void cleanupParticles() {
        if (currentParticleCount > ModConfig.get().maxParticles) {
            int toRemove = currentParticleCount - ModConfig.get().maxParticles;
            for (int i = 0; i < toRemove && !activeParticles.isEmpty(); i++) {
                Particle particle = activeParticles.remove(0);
                particle.markDead();
                currentParticleCount--;
            }
        }
    }
    
    public static boolean shouldRenderChunk(BlockPos pos, Vec3d cameraPos) {
        if (!ModConfig.get().optimizeRendering || !ModConfig.get().lazyChunkRendering) {
            return true;
        }
        
        double distance = Math.sqrt(pos.getSquaredDistance(cameraPos));
        return distance <= ModConfig.get().chunkLoadRadius * 16;
    }
    
    public static void optimizeRenderLayer(VertexConsumerProvider vertexConsumers, RenderLayer layer) {
        if (!ModConfig.get().optimizeRendering) {
            return;
        }
        
        // 优化渲染层的使用
        if (layer == RenderLayer.getTranslucent()) {
            // 减少半透明渲染的复杂度
            VertexConsumer consumer = vertexConsumers.getBuffer(layer);
            // 这里可以添加特定的优化逻辑
        }
    }
    
    public static int getCurrentParticleCount() {
        return currentParticleCount;
    }
    
    public static int getMaxParticles() {
        return ModConfig.get().maxParticles;
    }
}