# Optimise Mod for Minecraft 1.21.1

作者: GZ_Sakura | 版本: 0.1

这是一个**纯客户端**的Minecraft Fabric mod，专注于客户端优化和性能改进。

## 特性

- 
- ✅ **纯客户端mod** - 无需在服务器端安装
- ✅ 支持Minecraft 1.21.1
- ✅ 使用Fabric Loader 0.16.2
- ✅ 使用Fabric API
- ✅ Java 21支持
- ✅ 客户端内存优化功能
- ✅ 客户端渲染优化功能
- ✅ FPS监控和优化
- ✅ 可配置选项
- ✅ Mixin支持

## 项目结构

```
src/
├── main/
│   ├── java/
│   │   └── com/gzsakura/optimise/
│   │       ├── OptimiseMod.java          # 主mod类
│   │       ├── OptimiseModClient.java    # 客户端mod类
│   │       ├── config/
│   │       │   └── OptimiseConfig.java   # 配置管理
│   │       └── mixin/
│   │           └── OptimiseMixin.java    # 优化mixin
│   └── resources/
│       ├── fabric.mod.json               # mod配置
│       └── optimise.mixins.json          # mixin配置
```

## 功能特性

### 客户端内存优化
- **智能内存管理**: 自动检测高内存使用并触发垃圾回收
- **内存使用监控**: 实时监控客户端内存使用情况

### 客户端渲染优化
- **FPS监控**: 实时客户端FPS监控和显示
- **动态渲染距离**: 根据性能自动调整渲染距离
- **实体剔除**: 优化实体渲染性能
- **粒子效果优化**: 可配置粒子效果质量

### 配置系统
- **JSON配置**: 易于编辑的配置文件
- **热重载**: 支持配置热重载
- **模块化**: 可单独启用/禁用各项优化

## 配置选项

配置文件位于 `config/optimise.json`，包含以下选项：

```json
{
  "enableMemoryOptimizations": true,
  "enableRenderOptimizations": true,
  "maxChunkRenderDistance": 12,
  "enableEntityCulling": true,
  "showFpsOverlay": true,
  "enableSmoothLighting": true,
  "particleQuality": "high"
}
```

## 开始使用

1. **构建mod**:
   ```bash
   ./gradlew build
   ```

2. **运行Minecraft**:
   ```bash
   ./gradlew runClient   # 运行客户端
   ./gradlew runServer   # 运行服务器
   ```

3. **生成开发环境**:
   ```bash
   ./gradlew genSources
   ./gradlew genEclipseRuns  # 如果使用Eclipse
   ./gradlew genIntellijRuns # 如果使用IntelliJ IDEA
   ```

## 开发建议

- 使用IDE（IntelliJ IDEA或Eclipse）进行开发
- 熟悉Fabric API文档：https://fabricmc.net/wiki/
- 查看示例mod了解最佳实践
- 使用版本控制（Git）管理代码

## 许可证

本项目使用CC0 1.0 Universal许可证，你可以自由地使用、修改和分发这个模板。

## 支持

如果你遇到问题，可以：
- 查看Fabric官方文档
- 在Fabric Discord社区寻求帮助
- 参考其他开源Fabric mod的代码

祝你mod开发愉快！