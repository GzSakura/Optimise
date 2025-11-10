# 性能优化模组 (Performance Optimize)

![Minecraft](https://img.shields.io/badge/Minecraft-1.21.1-blueviolet)
![Environment](https://img.shields.io/badge/Environment-Client%20%26%20Server-blue)
![License](https://img.shields.io/badge/License-MIT-green)

一个为Minecraft 1.21.1设计的全面性能优化模组，旨在提升游戏运行效率并降低系统资源占用。

## 功能特性

### 🚀 渲染优化
- 粒子系统优化：限制最大粒子数量，自动清理多余粒子
- 实体渲染优化：根据距离动态调整实体渲染
- 区块渲染优化：智能加载和渲染可视区域内的区块
- 半透明物体渲染优化：减少不必要的复杂渲染计算

### 🧠 内存优化
- 自动垃圾回收：当内存使用率达到阈值时触发GC
- 对象池技术：重用常用对象以减少内存分配
- 内存泄漏防护：及时释放无用资源

### 🌍 世界与实体优化
- 实体AI优化：减少非必要实体AI计算
- 区块加载优化：按需加载和卸载区块数据
- 实体数量控制：限制单个区块内最大实体数量

### ⚙️ 可配置选项
通过图形界面或配置文件自定义所有优化参数：
- 启用/禁用各项优化功能
- 调整粒子、实体、区块等的最大数量
- 设置渲染距离和优化强度
- 开启调试信息显示

### 🎮 用户界面
- 快捷键（默认O）打开性能设置面板
- 实时显示FPS和内存使用情况
- 支持ModMenu集成

## 安装说明

1. 确保已安装Fabric Loader和Fabric API
2. 下载本模组的最新版本JAR文件
3. 将JAR文件放入Minecraft的`mods`文件夹中
4. 启动游戏即可

## 依赖项

- Minecraft 1.21.1
- Fabric Loader 0.16.5+
- Fabric API
- Cloth Config API (可选，用于更好的配置体验)
- Mod Menu (可选，用于在模组菜单中访问设置)

## 配置说明

模组会在首次运行后生成配置文件`config/performance-optimize.json`，也可通过游戏内界面（默认按键O）进行配置：

```json
{
  "enabled": true,                     // 启用模组
  "optimizeRendering": true,           // 启用渲染优化
  "maxParticles": 4000,                // 最大粒子数量
  "reduceEntityRendering": true,       // 减少实体渲染
  "entityRenderDistance": 64,          // 实体渲染距离
  "optimizeChunkLoading": true,        // 区块加载优化
  "chunkLoadRadius": 8,                // 区块加载半径
  "lazyChunkRendering": true,          // 延迟区块渲染
  "optimizeEntities": true,            // 实体优化
  "maxEntitiesPerChunk": 32,           // 每区块最大实体数
  "optimizeMemory": true,              // 内存优化
  "gcThreshold": 80,                   // 垃圾回收阈值(%)
  "showDebugInfo": false,              // 显示调试信息
  "logPerformance": false,             // 记录性能日志
  "aggressiveOptimizations": false,    // 激进优化模式
  "threadedRendering": false           // 多线程渲染(实验性)
}
```

## 技术实现

本模组采用以下技术实现高性能优化：

### Mixin注入技术
通过Mixin注入修改原版Minecraft代码，在不影响兼容性的前提下实现深度优化：

- `ParticleManagerMixin`: 控制粒子生成和更新
- `GameRendererMixin`: 优化渲染流程
- `WorldRendererMixin`: 优化世界渲染
- `EntityMixin`: 优化实体行为
- `MinecraftClientMixin`: 监控帧率和客户端性能
- `ServerWorldMixin`: 优化服务端世界更新

### 性能监控系统
内置实时性能监控，包括：
- FPS实时监测 
- 内存使用情况跟踪
- 自适应优化策略

## 使用建议

1. **日常游戏**: 推荐使用默认设置，平衡性能和视觉效果
2. **低配置设备**: 启用"激进优化模式"以获得最佳性能
3. **录制视频/直播**: 可适当调高渲染质量参数
4. **服务器环境**: 重点启用实体和世界优化功能

## 已知问题与限制

- 某些优化可能影响视觉效果，可根据个人喜好调整
- 激进优化模式可能导致部分视觉特效丢失
- 多线程渲染功能仍处于实验阶段，请谨慎使用

## 更新日志

### v1.0.0
- 初始版本发布
- 实现基础性能优化功能
- 添加完整的配置系统
- 提供图形化设置界面

## 贡献与反馈

如果您发现任何问题或有改进建议，请通过以下方式联系我们：
- 提交GitHub Issue
- 在相关论坛发帖反馈
- 通过Discord联系开发团队

## 开源许可

本项目基于MIT许可证开源，详情请参见[LICENSE](LICENSE)文件。

---

*注意：此模组为第三方修改内容，不隶属于Mojang Studios或Microsoft。*