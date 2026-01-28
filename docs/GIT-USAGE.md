# Git 使用指南

> yudao-boot-mini 前后端一体化项目的 Git 操作文档

## 项目结构

```
yudao-boot-mini/              ← 后端主仓库
├── yudao-framework/          ← 技术框架层
├── yudao-module-system/      ← 系统模块
├── yudao-module-infra/       ← 基础设施模块
├── yudao-server/             ← 启动模块
└── yudao-ui/                 ← 前端 Submodule (Vue3)
```

## 仓库信息

| 仓库 | 目录 | GitHub 地址 |
|-----|------|------------|
| 后端 | `/` | https://github.com/1571312541/yudao-boot-mini |
| 前端 | `/yudao-ui` | https://github.com/1571312541/yudao-ui-admin-vue3 |

## 分支说明

| 分支名 | 用途 |
|-------|------|
| `master` | 主分支，同步上游 |
| `zhuozhou/main` | 涿州项目定制分支 |
| `qixiang/main` | 气象项目定制分支 |

---

## 一、首次克隆

### 克隆完整项目（推荐）

```bash
# 克隆主仓库并自动拉取前端 submodule
git clone --recurse-submodules https://github.com/1571312541/yudao-boot-mini.git

# 进入项目
cd yudao-boot-mini
```

### 如果忘记 --recurse-submodules

```bash
# 已克隆后，初始化并更新 submodule
git submodule init
git submodule update
```

### 或者一条命令

```bash
git submodule update --init --recursive
```

---

## 二、分支操作

### 查看分支

```bash
# 查看后端分支
git branch -a

# 查看前端分支
cd yudao-ui && git branch -a && cd ..
```

### 切换分支（前后端同步）

```bash
# 切换后端分支
git checkout zhuozhou/main

# 切换前端分支（保持一致）
cd yudao-ui
git checkout zhuozhou/main
cd ..
```

### 快捷脚本：同步切换分支

**Windows (PowerShell)**
```powershell
# 保存为 switch-branch.ps1
param([string]$branch)
git checkout $branch
Set-Location yudao-ui
git checkout $branch
Set-Location ..
Write-Host "已切换到分支: $branch"
```

使用方式：
```powershell
.\switch-branch.ps1 zhuozhou/main
```

**Linux/macOS (Bash)**
```bash
# 保存为 switch-branch.sh
#!/bin/bash
BRANCH=$1
git checkout $BRANCH
cd yudao-ui && git checkout $BRANCH && cd ..
echo "已切换到分支: $BRANCH"
```

使用方式：
```bash
chmod +x switch-branch.sh
./switch-branch.sh zhuozhou/main
```

---

## 三、日常开发

### 拉取最新代码

```bash
# 拉取后端最新代码
git pull origin zhuozhou/main

# 更新前端 submodule
git submodule update --remote

# 或者进入前端目录手动拉取
cd yudao-ui
git pull origin zhuozhou/main
cd ..
```

### 提交代码

#### 后端提交
```bash
# 添加更改
git add .

# 提交
git commit -m "feat: 添加新功能"

# 推送
git push origin zhuozhou/main
```

#### 前端提交
```bash
cd yudao-ui

# 添加更改
git add .

# 提交
git commit -m "feat: 添加新页面"

# 推送
git push origin zhuozhou/main

cd ..
```

#### 更新后端的 submodule 引用
```bash
# 前端提交后，需要更新后端对前端的引用
git add yudao-ui
git commit -m "chore: 更新前端 submodule 引用"
git push origin zhuozhou/main
```

---

## 四、同步上游仓库

### 添加上游远程仓库

```bash
# 后端添加上游
git remote add upstream https://github.com/yudaocode/yudao-boot-mini.git

# 前端添加上游
cd yudao-ui
git remote add upstream https://github.com/yudaocode/yudao-ui-admin-vue3.git
cd ..
```

### 同步上游更新

```bash
# 获取上游更新
git fetch upstream

# 合并到当前分支
git merge upstream/master

# 解决冲突后推送
git push origin zhuozhou/main
```

### 前端同步上游

```bash
cd yudao-ui
git fetch upstream
git merge upstream/master
git push origin zhuozhou/main
cd ..

# 更新后端的 submodule 引用
git add yudao-ui
git commit -m "chore: 同步前端上游更新"
git push origin zhuozhou/main
```

---

## 五、创建新分支

### 从当前分支创建

```bash
# 后端创建新分支
git checkout -b feature/new-feature

# 前端创建对应分支
cd yudao-ui
git checkout -b feature/new-feature
cd ..
```

### 推送新分支到远程

```bash
# 后端推送
git push -u origin feature/new-feature

# 前端推送
cd yudao-ui
git push -u origin feature/new-feature
cd ..
```

---

## 六、常见问题

### Q1: submodule 目录为空

```bash
git submodule update --init --recursive
```

### Q2: 前端 submodule 显示 dirty

```bash
# 查看前端有什么更改
cd yudao-ui
git status

# 如果是要提交的更改
git add . && git commit -m "message"

# 如果是要丢弃的更改
git checkout -- .
cd ..
```

### Q3: 切换分支后前端版本不对

```bash
# 更新 submodule 到正确版本
git submodule update

# 或者手动切换
cd yudao-ui
git checkout zhuozhou/main
cd ..
```

### Q4: 克隆时 submodule 失败

```bash
# 单独克隆前端
cd yudao-ui
git clone https://github.com/1571312541/yudao-ui-admin-vue3.git .
git checkout zhuozhou/main
cd ..
```

### Q5: 查看 submodule 状态

```bash
git submodule status
```

输出说明：
- ` ` (空格): submodule 已同步
- `+`: submodule 有新提交
- `-`: submodule 未初始化
- `U`: submodule 有冲突

---

## 七、Git 配置建议

### 设置用户信息

```bash
git config --global user.name "Your Name"
git config --global user.email "your.email@example.com"
```

### 设置默认分支名

```bash
git config --global init.defaultBranch master
```

### 设置 submodule 递归更新

```bash
git config --global submodule.recurse true
```

### 设置中文文件名显示

```bash
git config --global core.quotepath false
```

---

## 八、快速参考

| 操作 | 命令 |
|-----|------|
| 克隆项目 | `git clone --recurse-submodules <url>` |
| 初始化 submodule | `git submodule update --init` |
| 切换后端分支 | `git checkout <branch>` |
| 切换前端分支 | `cd yudao-ui && git checkout <branch>` |
| 拉取后端 | `git pull` |
| 拉取前端 | `cd yudao-ui && git pull` |
| 更新 submodule | `git submodule update --remote` |
| 查看 submodule 状态 | `git submodule status` |

---

*文档更新时间: 2026-01-28*
