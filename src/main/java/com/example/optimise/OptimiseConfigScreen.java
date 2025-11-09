package com.example.optimise;

import com.example.optimise.lang.ChineseLanguagePack;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.gui.widget.TabButtonWidget;
import net.minecraft.client.gui.widget.TabNavigationWidget;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class OptimiseConfigScreen extends Screen {
    private final Screen parent;
    private TabNavigationWidget tabNavigation;
    private int currentTab = 0;
    private List<OptimiseTab> tabs = new ArrayList<>();
    
    // Rendering options
    private CyclingButtonWidget<Boolean> entityOptimizationButton;
    private CyclingButtonWidget<Boolean> particleOptimizationButton;
    private CyclingButtonWidget<Boolean> blockOptimizationButton;
    private CyclingButtonWidget<Boolean> weatherOptimizationButton;
    
    // Advanced rendering options
    private CyclingButtonWidget<Boolean> entityDistanceCullingButton;
    private CyclingButtonWidget<Boolean> entityLODButton;
    private CyclingButtonWidget<Boolean> particleDistanceCullingButton;
    private CyclingButtonWidget<Boolean> particleCountLimitButton;
    private CyclingButtonWidget<Integer> maxParticleCountButton;
    private CyclingButtonWidget<Boolean> fastSkyRenderingButton;
    private CyclingButtonWidget<Boolean> fastCloudRenderingButton;
    private CyclingButtonWidget<Boolean> optimizedBlockTickingButton;
    private CyclingButtonWidget<Boolean> reducedFogRenderingButton;
    
    // Memory options
    private CyclingButtonWidget<Boolean> memoryOptimizationButton;
    private CyclingButtonWidget<Boolean> textureOptimizationButton;
    private CyclingButtonWidget<Boolean> resourceUnloadingButton;
    private CyclingButtonWidget<Integer> resourceUnloadDelayButton;
    
    // GUI options
    private CyclingButtonWidget<Boolean> guiOptimizationButton;
    private CyclingButtonWidget<Boolean> reducedGuiAnimationsButton;
    private CyclingButtonWidget<Boolean> fastInventoryRenderingButton;
    
    public OptimiseConfigScreen(Screen parent) {
        super(ChineseLanguagePack.translate("optimise.config.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();
        
        // Initialize tabs
        tabs.add(new OptimiseTab("optimise.config.tab.rendering", 0xFF5555));
        tabs.add(new OptimiseTab("optimise.config.tab.advanced", 0x55FF55));
        tabs.add(new OptimiseTab("optimise.config.tab.memory", 0x5555FF));
        tabs.add(new OptimiseTab("optimise.config.tab.gui", 0xFFFF55));
        
        // Create tab navigation
        List<TabButtonWidget> tabButtons = new ArrayList<>();
        for (int i = 0; i < tabs.size(); i++) {
            final int tabIndex = i;
            TabButtonWidget tabButton = new TabButtonWidget(
                this.width / 2 - 150 + i * 75, 30, 70, 20,
                tabs.get(i).getName(),
                button -> this.switchTab(tabIndex)
            );
            tabButtons.add(tabButton);
        }
        
        this.tabNavigation = new TabNavigationWidget(tabButtons, currentTab);
        this.addDrawableChild(tabNavigation);
        
        // Initialize tab content
        this.initTabContent(currentTab);
        
        // Add common buttons
        this.addDrawableChild(ButtonWidget.builder(ChineseLanguagePack.translate("optimise.config.apply"), button -> {
            OptimiseConfig.saveConfig();
            assert this.client != null;
            this.client.setScreen(parent);
        }).dimensions(this.width / 2 - 155, this.height - 30, 100, 20).build());
        
        this.addDrawableChild(ButtonWidget.builder(ChineseLanguagePack.translate("optimise.config.reset"), button -> {
            // Reset config to defaults
            OptimiseConfig.INSTANCE = new OptimiseConfig();
            OptimiseConfig.saveConfig();
            this.clearAndInit();
        }).dimensions(this.width / 2 - 50, this.height - 30, 100, 20).build());
        
        this.addDrawableChild(ButtonWidget.builder(ChineseLanguagePack.translate("optimise.config.cancel"), button -> {
            assert this.client != null;
            this.client.setScreen(parent);
        }).dimensions(this.width / 2 + 55, this.height - 30, 100, 20).build());
    }
    
    private void switchTab(int tabIndex) {
        if (tabIndex != currentTab) {
            currentTab = tabIndex;
            this.clearAndInit();
        }
    }
    
    private void initTabContent(int tabIndex) {
        OptimiseConfig config = OptimiseConfig.getInstance();
        
        // Clear all existing widgets except tab navigation
        this.clearChildren();
        this.addDrawableChild(tabNavigation);
        
        switch (tabIndex) {
            case 0: // Rendering tab
                initRenderingTab(config);
                break;
            case 1: // Advanced tab
                initAdvancedTab(config);
                break;
            case 2: // Memory tab
                initMemoryTab(config);
                break;
            case 3: // GUI tab
                initGUITab(config);
                break;
        }
    }
    
    private void initRenderingTab(OptimiseConfig config) {
        int y = 70;
        
        // Entity rendering optimization
        entityOptimizationButton = this.addDrawableChild(CyclingButtonWidget.onOffBuilder(config.isEntityRenderingOptimization())
                .tooltip((state) -> Tooltip.of(ChineseLanguagePack.translate("optimise.tooltip.entity_rendering_optimization")))
                .build(this.width / 2 - 100, y, 200, 20, 
                        ChineseLanguagePack.translate("optimise.config.entity_rendering_optimization"), 
                        (button, value) -> {
                            config.setEntityRenderingOptimization(value);
                        }));
        y += 25;

        // Particle optimization
        particleOptimizationButton = this.addDrawableChild(CyclingButtonWidget.onOffBuilder(config.isParticleOptimization())
                .tooltip((state) -> Tooltip.of(ChineseLanguagePack.translate("optimise.tooltip.particle_optimization")))
                .build(this.width / 2 - 100, y, 200, 20, 
                        ChineseLanguagePack.translate("optimise.config.particle_optimization"), 
                        (button, value) -> {
                            config.setParticleOptimization(value);
                        }));
        y += 25;

        // Block rendering optimization
        blockOptimizationButton = this.addDrawableChild(CyclingButtonWidget.onOffBuilder(config.isBlockRenderingOptimization())
                .tooltip((state) -> Tooltip.of(ChineseLanguagePack.translate("optimise.tooltip.block_rendering_optimization")))
                .build(this.width / 2 - 100, y, 200, 20, 
                        ChineseLanguagePack.translate("optimise.config.block_rendering_optimization"), 
                        (button, value) -> {
                            config.setBlockRenderingOptimization(value);
                        }));
        y += 25;

        // Weather rendering optimization
        weatherOptimizationButton = this.addDrawableChild(CyclingButtonWidget.onOffBuilder(config.isWeatherRenderingOptimization())
                .tooltip((state) -> Tooltip.of(ChineseLanguagePack.translate("optimise.tooltip.weather_rendering_optimization")))
                .build(this.width / 2 - 100, y, 200, 20, 
                        ChineseLanguagePack.translate("optimise.config.weather_rendering_optimization"), 
                        (button, value) -> {
                            config.setWeatherRenderingOptimization(value);
                        }));
    }
    
    private void initAdvancedTab(OptimiseConfig config) {
        int y = 70;
        
        // Entity distance culling
        entityDistanceCullingButton = this.addDrawableChild(CyclingButtonWidget.onOffBuilder(config.isEntityDistanceCulling())
                .tooltip((state) -> Tooltip.of(ChineseLanguagePack.translate("optimise.tooltip.entity_distance_culling")))
                .build(this.width / 2 - 100, y, 200, 20, 
                        ChineseLanguagePack.translate("optimise.config.entity_distance_culling"), 
                        (button, value) -> {
                            config.setEntityDistanceCulling(value);
                        }));
        y += 25;

        // Entity LOD
        entityLODButton = this.addDrawableChild(CyclingButtonWidget.onOffBuilder(config.isEntityLOD())
                .tooltip((state) -> Tooltip.of(ChineseLanguagePack.translate("optimise.tooltip.entity_lod")))
                .build(this.width / 2 - 100, y, 200, 20, 
                        ChineseLanguagePack.translate("optimise.config.entity_lod"), 
                        (button, value) -> {
                            config.setEntityLOD(value);
                        }));
        y += 25;

        // Particle distance culling
        particleDistanceCullingButton = this.addDrawableChild(CyclingButtonWidget.onOffBuilder(config.isParticleDistanceCulling())
                .tooltip((state) -> Tooltip.of(ChineseLanguagePack.translate("optimise.tooltip.particle_distance_culling")))
                .build(this.width / 2 - 100, y, 200, 20, 
                        ChineseLanguagePack.translate("optimise.config.particle_distance_culling"), 
                        (button, value) -> {
                            config.setParticleDistanceCulling(value);
                        }));
        y += 25;

        // Particle count limit
        particleCountLimitButton = this.addDrawableChild(CyclingButtonWidget.onOffBuilder(config.isParticleCountLimit())
                .tooltip((state) -> Tooltip.of(ChineseLanguagePack.translate("optimise.tooltip.particle_count_limit")))
                .build(this.width / 2 - 100, y, 200, 20, 
                        ChineseLanguagePack.translate("optimise.config.particle_count_limit"), 
                        (button, value) -> {
                            config.setParticleCountLimit(value);
                        }));
        y += 25;

        // Max particle count
        maxParticleCountButton = this.addDrawableChild(CyclingButtonWidget.builder(config.getMaxParticleCount(), value -> Text.literal(String.valueOf(value)))
                .values(50, 100, 200, 500, 1000)
                .tooltip((state) -> Tooltip.of(ChineseLanguagePack.translate("optimise.tooltip.max_particle_count")))
                .build(this.width / 2 - 100, y, 200, 20, 
                        ChineseLanguagePack.translate("optimise.config.max_particle_count"), 
                        (button, value) -> {
                            config.setMaxParticleCount(value);
                        }));
        y += 25;

        // Fast sky rendering
        fastSkyRenderingButton = this.addDrawableChild(CyclingButtonWidget.onOffBuilder(config.isFastSkyRendering())
                .tooltip((state) -> Tooltip.of(ChineseLanguagePack.translate("optimise.tooltip.fast_sky_rendering")))
                .build(this.width / 2 - 100, y, 200, 20, 
                        ChineseLanguagePack.translate("optimise.config.fast_sky_rendering"), 
                        (button, value) -> {
                            config.setFastSkyRendering(value);
                        }));
        y += 25;

        // Fast cloud rendering
        fastCloudRenderingButton = this.addDrawableChild(CyclingButtonWidget.onOffBuilder(config.isFastCloudRendering())
                .tooltip((state) -> Tooltip.of(ChineseLanguagePack.translate("optimise.tooltip.fast_cloud_rendering")))
                .build(this.width / 2 - 100, y, 200, 20, 
                        ChineseLanguagePack.translate("optimise.config.fast_cloud_rendering"), 
                        (button, value) -> {
                            config.setFastCloudRendering(value);
                        }));
        y += 25;

        // Optimized block ticking
        optimizedBlockTickingButton = this.addDrawableChild(CyclingButtonWidget.onOffBuilder(config.isOptimizedBlockTicking())
                .tooltip((state) -> Tooltip.of(ChineseLanguagePack.translate("optimise.tooltip.optimized_block_ticking")))
                .build(this.width / 2 - 100, y, 200, 20, 
                        ChineseLanguagePack.translate("optimise.config.optimized_block_ticking"), 
                        (button, value) -> {
                            config.setOptimizedBlockTicking(value);
                        }));
        y += 25;

        // Reduced fog rendering
        reducedFogRenderingButton = this.addDrawableChild(CyclingButtonWidget.onOffBuilder(config.isReducedFogRendering())
                .tooltip((state) -> Tooltip.of(ChineseLanguagePack.translate("optimise.tooltip.reduced_fog_rendering")))
                .build(this.width / 2 - 100, y, 200, 20, 
                        ChineseLanguagePack.translate("optimise.config.reduced_fog_rendering"), 
                        (button, value) -> {
                            config.setReducedFogRendering(value);
                        }));
    }
    
    private void initMemoryTab(OptimiseConfig config) {
        int y = 70;
        
        // Memory optimization
        memoryOptimizationButton = this.addDrawableChild(CyclingButtonWidget.onOffBuilder(config.isMemoryOptimization())
                .tooltip((state) -> Tooltip.of(ChineseLanguagePack.translate("optimise.tooltip.memory_optimization")))
                .build(this.width / 2 - 100, y, 200, 20, 
                        ChineseLanguagePack.translate("optimise.config.memory_optimization"), 
                        (button, value) -> {
                            config.setMemoryOptimization(value);
                        }));
        y += 25;

        // Texture optimization
        textureOptimizationButton = this.addDrawableChild(CyclingButtonWidget.onOffBuilder(config.isTextureOptimization())
                .tooltip((state) -> Tooltip.of(ChineseLanguagePack.translate("optimise.tooltip.texture_optimization")))
                .build(this.width / 2 - 100, y, 200, 20, 
                        ChineseLanguagePack.translate("optimise.config.texture_optimization"), 
                        (button, value) -> {
                            config.setTextureOptimization(value);
                        }));
        y += 25;

        // Resource unloading
        resourceUnloadingButton = this.addDrawableChild(CyclingButtonWidget.onOffBuilder(config.isResourceUnloading())
                .tooltip((state) -> Tooltip.of(ChineseLanguagePack.translate("optimise.tooltip.resource_unloading")))
                .build(this.width / 2 - 100, y, 200, 20, 
                        ChineseLanguagePack.translate("optimise.config.resource_unloading"), 
                        (button, value) -> {
                            config.setResourceUnloading(value);
                        }));
        y += 25;

        // Resource unload delay
        resourceUnloadDelayButton = this.addDrawableChild(CyclingButtonWidget.builder(config.getResourceUnloadDelay(), value -> Text.literal(String.valueOf(value) + "s"))
                .values(60, 120, 300, 600, 900)
                .tooltip((state) -> Tooltip.of(ChineseLanguagePack.translate("optimise.tooltip.resource_unload_delay")))
                .build(this.width / 2 - 100, y, 200, 20, 
                        ChineseLanguagePack.translate("optimise.config.resource_unload_delay"), 
                        (button, value) -> {
                            config.setResourceUnloadDelay(value);
                        }));
    }
    
    private void initGUITab(OptimiseConfig config) {
        int y = 70;
        
        // GUI optimization
        guiOptimizationButton = this.addDrawableChild(CyclingButtonWidget.onOffBuilder(config.isGuiOptimization())
                .tooltip((state) -> Tooltip.of(ChineseLanguagePack.translate("optimise.tooltip.gui_optimization")))
                .build(this.width / 2 - 100, y, 200, 20, 
                        ChineseLanguagePack.translate("optimise.config.gui_optimization"), 
                        (button, value) -> {
                            config.setGuiOptimization(value);
                        }));
        y += 25;

        // Reduced GUI animations
        reducedGuiAnimationsButton = this.addDrawableChild(CyclingButtonWidget.onOffBuilder(config.isReducedGuiAnimations())
                .tooltip((state) -> Tooltip.of(ChineseLanguagePack.translate("optimise.tooltip.reduced_gui_animations")))
                .build(this.width / 2 - 100, y, 200, 20, 
                        ChineseLanguagePack.translate("optimise.config.reduced_gui_animations"), 
                        (button, value) -> {
                            config.setReducedGuiAnimations(value);
                        }));
        y += 25;

        // Fast inventory rendering
        fastInventoryRenderingButton = this.addDrawableChild(CyclingButtonWidget.onOffBuilder(config.isFastInventoryRendering())
                .tooltip((state) -> Tooltip.of(ChineseLanguagePack.translate("optimise.tooltip.fast_inventory_rendering")))
                .build(this.width / 2 - 100, y, 200, 20, 
                        ChineseLanguagePack.translate("optimise.config.fast_inventory_rendering"), 
                        (button, value) -> {
                            config.setFastInventoryRendering(value);
                        }));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, ChineseLanguagePack.translate("optimise.config.title"), this.width / 2, 15, 0xFFFFFF);
        
        // Draw tab content background
        context.fill(this.width / 2 - 160, 60, this.width / 2 + 160, this.height - 40, 0x80000000);
        
        // Draw current tab name
        if (currentTab < tabs.size()) {
            context.drawCenteredTextWithShadow(this.textRenderer, 
                tabs.get(currentTab).getName(), 
                this.width / 2, 65, tabs.get(currentTab).getColor());
        }
        
        super.render(context, mouseX, mouseY, delta);
    }
    
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            this.close();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
    
    @Override
    public void close() {
        OptimiseConfig.saveConfig();
        super.close();
    }
    
    private static class OptimiseTab {
        private final Text name;
        private final int color;
        
        public OptimiseTab(String nameKey, int color) {
            this.name = ChineseLanguagePack.translate(nameKey);
            this.color = color;
        }
        
        public Text getName() {
            return name;
        }
        
        public int getColor() {
            return color;
        }
    }
}