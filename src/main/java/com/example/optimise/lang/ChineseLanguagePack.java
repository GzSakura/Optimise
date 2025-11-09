package com.example.optimise.lang;

import net.minecraft.text.Text;
import net.minecraft.util.Language;

import java.util.HashMap;
import java.util.Map;

public class ChineseLanguagePack {
    private static final Map<String, String> translations = new HashMap<>();
    
    static {
        // UI elements
        translations.put("optimise.config.title", "Optimise 模组配置");
        translations.put("optimise.config.apply", "应用");
        translations.put("optimise.config.reset", "重置为默认值");
        translations.put("optimise.config.cancel", "取消");
        
        // Tabs
        translations.put("optimise.config.tab.rendering", "渲染");
        translations.put("optimise.config.tab.advanced", "高级");
        translations.put("optimise.config.tab.memory", "内存");
        translations.put("optimise.config.tab.gui", "界面");
        
        // Rendering tab
        translations.put("optimise.config.entity_rendering_optimization", "实体渲染优化");
        translations.put("optimise.config.particle_optimization", "粒子效果优化");
        translations.put("optimise.config.block_rendering_optimization", "方块渲染优化");
        translations.put("optimise.config.weather_rendering_optimization", "天气渲染优化");
        
        // Advanced tab
        translations.put("optimise.config.entity_distance_culling", "实体距离剔除");
        translations.put("optimise.config.entity_lod", "实体细节层次");
        translations.put("optimise.config.particle_distance_culling", "粒子距离剔除");
        translations.put("optimise.config.particle_count_limit", "粒子数量限制");
        translations.put("optimise.config.max_particle_count", "最大粒子数量");
        translations.put("optimise.config.fast_sky_rendering", "快速天空渲染");
        translations.put("optimise.config.fast_cloud_rendering", "快速云渲染");
        translations.put("optimise.config.optimized_block_ticking", "优化方块更新");
        translations.put("optimise.config.reduced_fog_rendering", "简化迷雾渲染");
        
        // Memory tab
        translations.put("optimise.config.memory_optimization", "内存优化");
        translations.put("optimise.config.texture_optimization", "纹理优化");
        translations.put("optimise.config.resource_unloading", "资源卸载");
        translations.put("optimise.config.resource_unload_delay", "资源卸载延迟");
        
        // GUI tab
        translations.put("optimise.config.gui_optimization", "界面优化");
        translations.put("optimise.config.reduced_gui_animations", "简化界面动画");
        translations.put("optimise.config.fast_inventory_rendering", "快速物品栏渲染");
        
        // Tooltips
        translations.put("optimise.tooltip.entity_rendering_optimization", "减少实体渲染开销");
        translations.put("optimise.tooltip.particle_optimization", "限制粒子效果数量和渲染距离");
        translations.put("optimise.tooltip.block_rendering_optimization", "优化方块渲染和地形更新频率");
        translations.put("optimise.tooltip.weather_rendering_optimization", "优化天空和天气效果渲染");
        translations.put("optimise.tooltip.entity_distance_culling", "不渲染远距离的实体");
        translations.put("optimise.tooltip.entity_lod", "对远距离实体使用简化渲染");
        translations.put("optimise.tooltip.particle_distance_culling", "不渲染远距离的粒子");
        translations.put("optimise.tooltip.particle_count_limit", "限制同时显示的粒子数量");
        translations.put("optimise.tooltip.max_particle_count", "最大同时显示的粒子数量");
        translations.put("optimise.tooltip.fast_sky_rendering", "使用简化的天空渲染");
        translations.put("optimise.tooltip.fast_cloud_rendering", "使用简化的云渲染");
        translations.put("optimise.tooltip.optimized_block_ticking", "优化方块更新以提高性能");
        translations.put("optimise.tooltip.reduced_fog_rendering", "减少迷雾渲染复杂度");
        translations.put("optimise.tooltip.memory_optimization", "启用通用内存优化");
        translations.put("optimise.tooltip.texture_optimization", "优化纹理加载和使用");
        translations.put("optimise.tooltip.resource_unloading", "启用自动资源卸载");
        translations.put("optimise.tooltip.resource_unload_delay", "卸载未使用资源前的延迟时间（秒）");
        translations.put("optimise.tooltip.gui_optimization", "启用通用界面优化");
        translations.put("optimise.tooltip.reduced_gui_animations", "减少界面动画复杂度");
        translations.put("optimise.tooltip.fast_inventory_rendering", "优化物品栏渲染速度");
    }
    
    public static Text translate(String key) {
        return Text.literal(translations.getOrDefault(key, key));
    }
    
    public static String translateString(String key) {
        return translations.getOrDefault(key, key);
    }
}