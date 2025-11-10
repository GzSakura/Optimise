package com.gzsakura.optimise.client.gui;

import com.gzsakura.optimise.OptimiseModClient;
import com.gzsakura.optimise.config.OptimiseConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class OptimiseConfigScreen extends Screen {
    private final Screen parent;
    private CyclingButtonWidget<Boolean> memoryOptimizationsButton;
    private CyclingButtonWidget<Boolean> renderOptimizationsButton;
    private CyclingButtonWidget<Boolean> entityCullingButton;
    private CyclingButtonWidget<Boolean> fpsOverlayButton;
    private CyclingButtonWidget<Boolean> smoothLightingButton;
    private TextFieldWidget renderDistanceField;
    private CyclingButtonWidget<Integer> particleQualityButton;
    
    public OptimiseConfigScreen(Screen parent) {
        super(Text.translatable("optimise.config.title"));
        this.parent = parent;
    }
    
    @Override
    protected void init() {
        OptimiseConfig config = OptimiseModClient.CONFIG;
        
        int centerX = this.width / 2;
        int startY = 60;
        int buttonHeight = 20;
        int spacing = 25;
        
        // 内存优化选项
        this.memoryOptimizationsButton = CyclingButtonWidget.builder((Boolean value) -> 
                value ? Text.translatable("options.on") : Text.translatable("options.off"))
            .values(true, false)
            .initially(config.enableMemoryOptimizations)
            .build(centerX - 100, startY, 200, buttonHeight, 
                Text.translatable("optimise.config.memory_optimizations"), 
                (button, value) -> config.enableMemoryOptimizations = value);
        this.addDrawableChild(this.memoryOptimizationsButton);
        
        // 渲染优化选项
        this.renderOptimizationsButton = CyclingButtonWidget.builder((Boolean value) -> 
                value ? Text.translatable("options.on") : Text.translatable("options.off"))
            .values(true, false)
            .initially(config.enableRenderOptimizations)
            .build(centerX - 100, startY + spacing, 200, buttonHeight, 
                Text.translatable("optimise.config.render_optimizations"), 
                (button, value) -> config.enableRenderOptimizations = value);
        this.addDrawableChild(this.renderOptimizationsButton);
        
        // 实体剔除选项
        this.entityCullingButton = CyclingButtonWidget.builder((Boolean value) -> 
                value ? Text.translatable("options.on") : Text.translatable("options.off"))
            .values(true, false)
            .initially(config.enableEntityCulling)
            .build(centerX - 100, startY + spacing * 2, 200, buttonHeight, 
                Text.translatable("optimise.config.entity_culling"), 
                (button, value) -> config.enableEntityCulling = value);
        this.addDrawableChild(this.entityCullingButton);
        
        // FPS显示选项
        this.fpsOverlayButton = CyclingButtonWidget.builder((Boolean value) -> 
                value ? Text.translatable("options.on") : Text.translatable("options.off"))
            .values(true, false)
            .initially(config.showFpsOverlay)
            .build(centerX - 100, startY + spacing * 3, 200, buttonHeight, 
                Text.translatable("optimise.config.fps_overlay"), 
                (button, value) -> config.showFpsOverlay = value);
        this.addDrawableChild(this.fpsOverlayButton);
        
        // 平滑光照选项
        this.smoothLightingButton = CyclingButtonWidget.builder((Boolean value) -> 
                value ? Text.translatable("options.on") : Text.translatable("options.off"))
            .values(true, false)
            .initially(config.enableSmoothLighting)
            .build(centerX - 100, startY + spacing * 4, 200, buttonHeight, 
                Text.translatable("optimise.config.smooth_lighting"), 
                (button, value) -> config.enableSmoothLighting = value);
        this.addDrawableChild(this.smoothLightingButton);
        
        // 渲染距离输入
        this.renderDistanceField = new TextFieldWidget(
            this.textRenderer, centerX - 100, startY + spacing * 5, 200, buttonHeight, 
            Text.literal("渲染距离")
        );
        this.renderDistanceField.setText(String.valueOf(config.maxChunkRenderDistance));
        this.addDrawableChild(this.renderDistanceField);
        
        // 粒子质量选项
        this.particleQualityButton = CyclingButtonWidget.builder((Integer value) -> {
            switch (value) {
                case 0: return Text.translatable("optimise.config.particle_quality.minimal");
                case 1: return Text.translatable("optimise.config.particle_quality.decreased");
                case 2: return Text.translatable("optimise.config.particle_quality.all");
                default: return Text.literal("Unknown");
            }
        })
            .values(0, 1, 2)
            .initially(config.particleQuality)
            .build(centerX - 100, startY + spacing * 6, 200, buttonHeight, 
                Text.translatable("optimise.config.particle_quality"), 
                (button, value) -> config.particleQuality = value);
        this.addDrawableChild(this.particleQualityButton);
        
        // 保存按钮
        this.addDrawableChild(ButtonWidget.builder(Text.translatable("optimise.config.save"), button -> {
            saveConfig();
            OptimiseModClient.LOGGER.info("Optimise mod配置已保存");
            this.close();
        }).dimensions(centerX - 100, startY + spacing * 8, 200, buttonHeight).build());
        
        // 返回按钮
        this.addDrawableChild(ButtonWidget.builder(Text.translatable("optimise.config.back"), button -> {
            this.close();
        }).dimensions(centerX - 100, startY + spacing * 9, 200, buttonHeight).build());
    }
    
    private void saveConfig() {
        OptimiseConfig config = OptimiseModClient.CONFIG;
        
        // 从界面获取值
        config.enableMemoryOptimizations = this.memoryOptimizationsButton.getValue();
        config.enableRenderOptimizations = this.renderOptimizationsButton.getValue();
        config.enableEntityCulling = this.entityCullingButton.getValue();
        config.showFpsOverlay = this.fpsOverlayButton.getValue();
        config.enableSmoothLighting = this.smoothLightingButton.getValue();
        config.particleQuality = this.particleQualityButton.getValue();
        
        // 解析渲染距离
        try {
            int renderDistance = Integer.parseInt(this.renderDistanceField.getText());
            if (renderDistance >= 2 && renderDistance <= 32) {
                config.maxChunkRenderDistance = renderDistance;
            }
        } catch (NumberFormatException e) {
            OptimiseModClient.LOGGER.warn("{}: {}", 
                Text.translatable("optimise.messages.invalid_render_distance"), 
                this.renderDistanceField.getText());
        }
        
        // 保存到文件
        config.save();
    }
    
    @Override
    public void render(net.minecraft.client.gui.DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        
        // 标题
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        
        // 标签
        int centerX = this.width / 2;
        int startY = 60;
        int spacing = 25;
        
        context.drawTextWithShadow(this.textRenderer, Text.translatable("optimise.config.render_distance"), centerX - 100, startY + spacing * 5 - 10, 0xFFFFFF);
        
        super.render(context, mouseX, mouseY, delta);
    }
    
    @Override
    public void close() {
        this.client.setScreen(this.parent);
    }
}