package com.optimize.manager;

import com.optimize.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Util;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PerformanceManager {
    private static PerformanceManager instance;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private long lastMemoryCheck = 0;
    private int frameCounter = 0;
    private long lastFrameTime = 0;
    private double currentFPS = 0;
    private long usedMemory = 0;
    private long totalMemory = 0;
    
    public void initialize() {
        instance = this;
        
        // 启动性能监控任务
        scheduler.scheduleAtFixedRate(this::updatePerformanceMetrics, 0, 1, TimeUnit.SECONDS);
        
        if (ModConfig.get().optimizeMemory) {
            scheduleMemoryOptimization();
        }
    }
    
    public void initializeClient() {
        // 客户端特定的初始化
        if (ModConfig.get().optimizeRendering) {
            RenderingOptimizer.initialize();
        }
    }
    
    private void updatePerformanceMetrics() {
        Runtime runtime = Runtime.getRuntime();
        totalMemory = runtime.totalMemory();
        usedMemory = totalMemory - runtime.freeMemory();
        
        // 计算FPS
        long currentTime = Util.getMeasuringTimeMs();
        if (lastFrameTime != 0) {
            double frameTime = (currentTime - lastFrameTime) / 1000.0;
            if (frameTime > 0) {
                currentFPS = frameCounter / frameTime;
            }
        }
        frameCounter = 0;
        lastFrameTime = currentTime;
    }
    
    private void scheduleMemoryOptimization() {
        scheduler.scheduleAtFixedRate(() -> {
            Runtime runtime = Runtime.getRuntime();
            long total = runtime.totalMemory();
            long free = runtime.freeMemory();
            long used = total - free;
            
            double usagePercent = (double) used / total * 100;
            
            if (usagePercent > ModConfig.get().gcThreshold) {
                System.gc();
                if (ModConfig.get().logPerformance) {
                    System.out.println("[性能优化] 执行垃圾回收，内存使用率: " + String.format("%.1f%%", usagePercent));
                }
            }
        }, 30, 30, TimeUnit.SECONDS);
    }
    
    public void onFrame() {
        frameCounter++;
    }
    
    public void cleanup() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
        }
    }
    
    public static PerformanceManager getInstance() {
        return instance;
    }
    
    public double getCurrentFPS() {
        return currentFPS;
    }
    
    public long getUsedMemory() {
        return usedMemory;
    }
    
    public long getTotalMemory() {
        return totalMemory;
    }
    
    public double getMemoryUsagePercent() {
        return totalMemory > 0 ? (double) usedMemory / totalMemory * 100 : 0;
    }
}