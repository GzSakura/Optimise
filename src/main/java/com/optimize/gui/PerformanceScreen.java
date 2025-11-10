package com.optimize.gui;

import com.optimize.PerformanceOptimizeMod;
import com.optimize.config.ModConfig;
import com.optimize.manager.PerformanceManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import java.text.DecimalFormat;

public class PerformanceScreen extends Screen {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");
    private final Screen parent;
    
    public PerformanceScreen() {
        this(null);
    }
    
    public PerformanceScreen(Screen parent) {
        super(Text.literal("性能优化设置"));
        this.parent = parent;
    }
    
    @Override
    protected void init() {
        int centerX = this.width / 2;
        int startY = 60;
        int buttonHeight = 20;
        int buttonWidth = 200;
        int spacing = 24;
        
        // 标题
        this.addDrawable((matrices, mouseX, mouseY, delta) -> {
            drawCenteredTextWithShadow(matrices, this.textRenderer, "性能优化设置", centerX, 20, 0xFFFFFF);
        });
        
        // 启用/禁用优化
        this.addDrawableChild(CyclingButtonWidget.onOffBuilder(ModConfig.get().enabled)
            .build(centerX - buttonWidth / 2, startY, buttonWidth, buttonHeight, 
                Text.literal("启用性能优化"), (button, value) -> {
                    ModConfig.get().enabled = value;
                    AutoConfig.getConfigHolder(ModConfig.class).save();
                }));
        
        // 渲染优化
        this.addDrawableChild(CyclingButtonWidget.onOffBuilder(ModConfig.get().optimizeRendering)
            .build(centerX - buttonWidth / 2, startY + spacing, buttonWidth, buttonHeight, 
                Text.literal("渲染优化"), (button, value) -> {
                    ModConfig.get().optimizeRendering = value;
                    AutoConfig.getConfigHolder(ModConfig.class).save();
                }));
        
        // 内存优化
        this.addDrawableChild(CyclingButtonWidget.onOffBuilder(ModConfig.get().optimizeMemory)
            .build(centerX - buttonWidth / 2, startY + spacing * 2, buttonWidth, buttonHeight, 
                Text.literal("内存优化"), (button, value) -> {
                    ModConfig.get().optimizeMemory = value;
                    AutoConfig.getConfigHolder(ModConfig.class).save();
                }));
        
        // 区块优化
        this.addDrawableChild(CyclingButtonWidget.onOffBuilder(ModConfig.get().optimizeChunkLoading)
            .build(centerX - buttonWidth / 2, startY + spacing * 3, buttonWidth, buttonHeight, 
                Text.literal("区块加载优化"), (button, value) -> {
                    ModConfig.get().optimizeChunkLoading = value;
                    AutoConfig.getConfigHolder(ModConfig.class).save();
                }));
        
        // 实体优化
        this.addDrawableChild(CyclingButtonWidget.onOffBuilder(ModConfig.get().optimizeEntities)
            .build(centerX - buttonWidth / 2, startY + spacing * 4, buttonWidth, buttonHeight, 
                Text.literal("实体优化"), (button, value) -> {
                    ModConfig.get().optimizeEntities = value;
                    AutoConfig.getConfigHolder(ModConfig.class).save();
                }));
        
        // 调试信息
        this.addDrawableChild(CyclingButtonWidget.onOffBuilder(ModConfig.get().showDebugInfo)
            .build(centerX - buttonWidth / 2, startY + spacing * 5, buttonWidth, buttonHeight, 
                Text.literal("显示调试信息"), (button, value) -> {
                    ModConfig.get().showDebugInfo = value;
                    AutoConfig.getConfigHolder(ModConfig.class).save();
                }));
        
        // 高级优化
        this.addDrawableChild(CyclingButtonWidget.onOffBuilder(ModConfig.get().aggressiveOptimizations)
            .build(centerX - buttonWidth / 2, startY + spacing * 6, buttonWidth, buttonHeight, 
                Text.literal("高级优化"), (button, value) -> {
                    ModConfig.get().aggressiveOptimizations = value;
                    AutoConfig.getConfigHolder(ModConfig.class).save();
                }));
        
        // 关闭按钮
        this.addDrawableChild(ButtonWidget.builder(Text.literal("关闭"), button -> {
            this.close();
        }).dimensions(centerX - buttonWidth / 2, this.height - 30, buttonWidth, buttonHeight).build());
    }
    
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        
        // 显示性能信息
        PerformanceManager manager = PerformanceOptimizeMod.getPerformanceManager();
        if (manager != null && ModConfig.get().showDebugInfo) {
            int left = 10;
            int top = 10;
            int lineHeight = 10;
            
            drawTextWithShadow(matrices, this.textRenderer, "=== 性能监控 ===", left, top, 0x00FF00);
            top += lineHeight * 2;
            
            drawTextWithShadow(matrices, this.textRenderer, "FPS: " + DECIMAL_FORMAT.format(manager.getCurrentFPS()), left, top, 0xFFFFFF);
            top += lineHeight;
            
            double memoryUsage = manager.getMemoryUsagePercent();
            int memoryColor = memoryUsage > 80 ? 0xFF0000 : memoryUsage > 60 ? 0xFFFF00 : 0x00FF00;
            drawTextWithShadow(matrices, this.textRenderer, "内存使用: " + DECIMAL_FORMAT.format(memoryUsage) + "%", left, top, memoryColor);
            top += lineHeight;
            
            drawTextWithShadow(matrices, this.textRenderer, "已用内存: " + formatBytes(manager.getUsedMemory()), left, top, 0xFFFFFF);
            top += lineHeight;
            
            drawTextWithShadow(matrices, this.textRenderer, "总内存: " + formatBytes(manager.getTotalMemory()), left, top, 0xFFFFFF);
        }
    }
    
    private String formatBytes(long bytes) {
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        String pre = "KMGTPE".charAt(exp-1) + "";
        return String.format("%.1f %sB", bytes / Math.pow(1024, exp), pre);
    }
    
    @Override
    public boolean shouldPause() {
        return false;
    }
    
    private void close() {
        if (this.client != null) {
            this.client.setScreen(this.parent);
        }
    }
}