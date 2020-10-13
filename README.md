# DevOps比赛项目说明

[![pipeline status](https://gitlab.com/baixingwang/devops-user-service/badges/master/pipeline.svg)](https://gitlab.com/baixingwang/devops-user-service/-/commits/master) [![coverage report](https://gitlab.com/baixingwang/devops-user-service/badges/master/coverage.svg)](https://gitlab.com/baixingwang/devops-user-service/-/commits/master)

## 1、总体说明

### 1.1、项目说明

### 1.2、团队介绍

### 1.3、流水线说明



## 2、需求管理

### 2.1、需求说明

本次演示项目采用一个简单的微服务作为流水线的演示，需求相对比较简单。需要完成一个【用户管理】的模块，需要实现的功能有：

- 用户信息的管理，包括新增、修改、查询和删除
- 用户信息包括用户姓名和用户年龄

【说明】：本次演示项目技术选型为SpringBoot，数据库采用内存数据库h2

### 2.2、需求管理

需求管理采用工具为TAPD，具体拆分为【需求】-【子需求】-【任务】，具体的任务对应到相关研发人员，并评估工作时间。

![image-20201013095000571](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjnguqjvhsj321m0r4qai.jpg)

![image-20201013095050567](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjngvjpuquj31zy0u0dnx.jpg)

### 2.3、工作量评估

工作量在Scrum计划会议上全员进行参与，在【理解用户故事】和【拆分功能点】后进行，所有工作量必须透明公开，承诺在计划能完成。在TAPD上输入【预计开始】和【预计结束】时间

![image-20201013095716421](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjnh29362sj322a0mggro.jpg)

### 2.4、代码库关联

我们在TAPD上将需求和Gitlab代码库进行关联，在小组成员提交代码时需要明确关联的需求。

![image-20201013101037123](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjnhg56ho4j31v20i6tc4.jpg)


## 3、代码管理

### 3.1、代码管理工具

代码管理库采用Gitlab进行版本管理，项目代码地址为：https://gitlab.com/baixingwang/devops-user-service

### 3.2、分支管理

本次演示项目分支策略比较简单，采用【dev】-【master】两分支策略，简单说明如下：

- dev分支
  - 开发分支
  - 编译、测试、单测覆盖度、代码质量、测试环境部署等均在该分支上进行
- master分支
  - 部署分支
  - 完成测试流程后，合并到该分支进行上线部署

![image-20201013103129578](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjni1uh8j8j31j40mydix.jpg)

### 3.3、代码审查

