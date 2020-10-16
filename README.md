# DevOps流水线

[![pipeline status](https://gitlab.com/baixingwang/devops-user-service/badges/master/pipeline.svg)](https://gitlab.com/baixingwang/devops-user-service/-/commits/master) [![coverage report](https://gitlab.com/baixingwang/devops-user-service/badges/master/coverage.svg)](https://gitlab.com/baixingwang/devops-user-service/-/commits/master)

[TOC]

## 1、总体说明

### 1.1、项目说明

本项目是百姓网DevOps团队基于现有工作提炼出来的相对表完整的流水线，项目功能是一个简单的用户信息的增删改查，采用SpringBoot框架开发，数据库采用H2。

对应的API为：

| API列表  |           路径           | 请求方式 |              参数说明              |
| :------: | :----------------------: | :------: | :--------------------------------: |
| 项目首页 |            /             |   GET    |                 无                 |
| 查询用户 |      api/user/list       |   GET    |                 无                 |
| 新增用户 |     api/user/create      |   POST   | {   "name": "messi",   "age": 30 } |
| 更新用户 |     api/user/update      |   POST   |     {   "id": 1,   "age": 40 }     |
| 删除用户 | api/user/remove?id=${id} |   POST   |          ${id}代表用户id           |

产线环境为：http://prod-devops.baixing.cn:8088/

![image-20201015100933256](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjpsnnz50fj30w507j0t8.jpg)

### 1.2、团队介绍

百姓网DevOps团队成员介绍：

- 王翱
- 李如磊
- 高榕
- 王东兴
- 虞伟

### 1.3、流水线说明

- 分支策略

  - 采用小步快跑的模式
  - 在流程设计上，master 作为发布分支，release-* 为提测分支，提测分支是短期的
  - 在 release 测试过程中，发现某个 feature 的 bug， 直接从 release 分支 checkout 出来进行修复，并再次合入 release

  ![img](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjpz1vja66j30ky0kbmyz.jpg)

- 流水线说明

  流水线包含以下几个步骤：

  - 编译
  - 代码检查 && 单元测试
  - 生成可执行文件并提交
  - 生成镜像文件并提交
  - 部署对应环境
  - 自动化测试
  - 发布生产（该步骤只在上线时启用）
  - 清理 && TAG

### 1.4、工具链

介绍下本次演示所使用到的工具链和对应的访问地址

- 基础环境：
  - EKS
  - ElasticStack：https://8a639b1c32ea43df8f6d9157eb6e2ef8.ap-southeast-1.aws.found.io:9243/app/apm#/services?rangeFrom=now-15m&rangeTo=now

- 代码
  - 技术框架：SpringBoot
  - 数据库：H2
- 持续集成
  - 代码版本库：Gitlab：https://gitlab.com/baixingwang/devops-user-service
  - 编译工具：Maven
  - 代码质控：Sonar
  - 单侧覆盖度：Jacoco
  - 制品管理：nexus
  - 项目管理：TAPD
  - 接口测试：Yapi
  - 性能测试：Jmeter
- 持续部署
  - 容器技术：docker
  - 容器声明管理：Kustomize
  - 部署工具：ArgoCD
  - 灰度发布：Flagger
  - 可视化度量：Prometheus+Grafana
  - 全链路：Elastic APM

### 1.5、演示说明

以下会从**十个维度**对整个流水线运行过程进行详细说明并附上关键截图，供各位参考

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

同时可以看到该项目中代码提交的统计数据

![image-20201013170014708](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjntadoh42j31280f00ti.jpg)

## 3、代码管理

### 3.1、代码管理工具

代码管理库采用Gitlab进行版本管理，项目代码地址为：https://gitlab.com/baixingwang/devops-user-service

### 3.2、分支管理

本次演示项目分支策略比较简单，采用【master】作为主干分支策略，简单说明如下：

- feature分支
  - 功能分支
- release分支
  - 提测分支
  - 编译、测试、单测覆盖度、代码质量、测试环境部署等均在该分支上进行
- master分支
  - 部署分支
  - 完成测试流程后，合并到该分支进行上线部署

![image-20201016103056983](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjqyw8332qj30t60ha40i.jpg)
### 3.3、代码审查

上线发版时需要将代码合并入master分支，此时需要人工进行代码审核，确认无误后进行发布线上流程，此步骤也是本次演示中唯一的人工介入的部分

![image-20201014172731484](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjozp2qhb6j30rw0fjq4s.jpg)

## 4、制品管理

### 4.1、统一制品库

本次项目采用的制品库有两个：

- 项目依赖制品库：nexus

  ![image-20201013113037369](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjnjrdst8rj32ky0qcwmv.jpg)

- Docker镜像制品库：Gitlab Registry

### 4.2、版本号管理

**软件版本号由四部分组成：**

- **第一个1为主版本号**
- **第二个1为子版本号**
- **第三个1为阶段版本号**
- **第四部分为发布版本号，主要有2个，分别是`SNAPSHOT`和`RELEASE`**

例如：1.1.1.RELEASE

主版本号(1)：当功能模块有较大的变动，比如增加多个模块或者整体架构发生变化

子版本号(1)：当功能有一定的增加或变化，比如增加了对权限控制、增加自定义视图等功能

阶段版本号(1)：一般是 Bug 修复或是一些小的变动，要经常发布修订版，时间间隔不限，修复一个严重的bug即可发布一个修订版

发布版本号(RELEASE):此版本号用于标注当前版本的软件处于哪个发布阶段，当软件进入到发布阶段时需要修改此版本号

![image-20201013105412847](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjnipi5eagj31910u0tf7.jpg)

### 4.3、依赖组件管理

 代码中配置仓库地址，确保对应的依赖从制品库中进行下载，参考【pom.xml】

![image-20201013104735168](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjniilliu3j31kr0u0tdq.jpg)



## 5、构建方式

### 5.1、自动化构建脚本

本次自动化构建以来【Gitlab Runner】进行，相关脚本参考项目路径下的【.gitlab-ci.yml】:

https://gitlab.com/baixingwang/devops-user-service/-/blob/master/.gitlab-ci.yml

![image-20201016110755961](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjqzyordb9j30rc0dbgmt.jpg)
### 5.2、模块级别复用

在整个CI流程中，存在两种级别的复用：

- 依赖JAR包的服务，采用CI缓存进行实现，避免每次都下载依赖，下图说明缓存生效

  ![image-20201013114659560](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjnk8ehl4qj30t20c8dhg.jpg)

  ![image-20201013114803908](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjnk9ire2uj31cm05i3zs.jpg)

- 项目打包完成后，需要传到【app.jar】供生成docker镜像，以下为两个阶段的依赖传递

  ![image-20201013115128606](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjnkd4cgq4j30oq07emxj.jpg)

  【package】阶段最后上传app.jar

  ![image-20201013115257567](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjnkemalrtj31dg06kjso.jpg)

  【docker-build】阶段下载app.jar用来构建镜像

  ![image-20201013115357126](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjnkfn99fvj31a602c0tf.jpg)

### 5.3、自动构建

设置每天自动构建的任务，目标为dev分支，构建完毕后自动部署至测试环境

![image-20201013121131531](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjnkxxq1p7j31yk0jq0wd.jpg)

### 5.4、构建资源弹性

采用Gitlab Runner进行构建，并行构建的时候自动化选择不同的Runner进行，以下两个构建阶段采用了不同的Runner进行

![image-20201013120348690](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjnkpwpu96j328m0e0aeo.jpg)

![image-20201013120503644](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjnkr7lxzcj327o0fawjx.jpg)



## 6、持续集成

### 6.1、按需集成

项目团队成员提交代码变更后自动触发CI流程，以下是两位不同的项目成员触发的CI流程

![image-20201013134852305](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjnnr7t6ulj323e07stb2.jpg)

### 6.2、触发机制

提交代码变更后后自动触发，目前只允许研发人员提交dev分支，持续集成只适配dev分支

![image-20201013135654526](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjnnzkyvorj31b20l441d.jpg)

### 6.3、集成结果推送

流水线执行结果通过邮件和企业微信到达项目成员，如果构建失败，通过详情信息直接关联到对应的构建任务，查看失败原因。以下是企业微信的相关截图

- 构建成功

  ![image-20201013140419174](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjno7akl4rj30us0aw40c.jpg)

  ![image-20201013140533768](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjno8lhx21j312d0u0dyg.jpg)

- 构建失败

  ![image-20201013140604154](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjno94dnryj30us0jen2v.jpg)

  ![image-20201013140657049](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjnoa1d5zfj31u70u0hdt.jpg)

### 6.4、自动化测试

每次代码变更触发持续集成时，都会进行自动化单元测试，在流水线配置时单独配置一个单测阶段

![image-20201013141148656](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjnof3i9t4j31bs0kewhi.jpg)

以下是执行日志输出和测试结果

![image-20201013141241485](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjnog0emwrj31os0u0n85.jpg)

![image-20201013141301548](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjnogcqnsqj31ei0bognl.jpg)

## 7、自动化测试
### 7.1、测试计划
测试计划使用TAPD平台进行管理，关联需求、用例及缺陷。
![image-testplan](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjp0s70dzlj31a10es41u.jpg)

### 7.2、单元测试和覆盖度

针对service层做单元测试，并通过Jacoco生成单元测试覆盖度报告

![image-20201016150925063](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjr6y0zlthj310b059aai.jpg)

同时在【ReadME】文件中生成覆盖度标签

![image-20201016150947186](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjr6yc9vvbj30c002rdfx.jpg)

### 7.3、接口测试
![image-20201015112907155](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjpuyg89hej30zi0b6gqf.jpg)
使用YPAI平台进行接口协议管理及自动化用例执行，其他依赖前置后置操作能力，由自研服务支持。
![image-interface](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjp0yvicmqj31870liq5t.jpg)

### 7.4、性能测试
基于jmeter自研性能测试平台，进行性能测试。
![image-stress](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjp10x7y54j30t708gjsl.jpg)
![image-20201015113017795](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjpv07tgg1j31000ejwi1.jpg)

### 7.5、测试报告
使用TAPD做测试报告数据收集及发送。
![image-report](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjp123hqzkj31c80f7jul.jpg)

## 8、代码质量管控

### 8.1、质控工具

本次项目代码质量管控采用sonar，访问地址为：http://39.100.144.36

![image-20201014102547753](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjonibbvhuj311e0eqq4i.jpg)

同时引入阿里代码规范作为代码检测规则：

![image-20201014102659354](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjonjjuj41j314t0jvte8.jpg)

### 8.2、自动化检测

引入sonar自动化检测插件，在流水线中单独创建一个任务进行自动化代码质量管控，为了加快速度，和单元测试并行处理

![image-20201014102948937](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjonmhxnqzj30j1060q38.jpg)

![image-20201014103023212](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjonn32e2dj30os0ccjtn.jpg)

### 8.3、可视化

登录sonar即可查看代码质量报告

![image-20201014103236638](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjonpe935qj310s0lzac5.jpg)

## 9、部署流水线

### 9.1、自动化

代码提交变更后自动触发流水线，流水线的整个生命周期中只有上线合并代码的确认环节需要人为干预，其他均为自动化处理

![image-20201014135600476](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjotl1dxadj30cz06b3z0.jpg)

### 9.2、多环境

本次演示包括四个环境：

- 开发环境
- 测试环境
- 预发布环境
- 产线环境

具体说明见【第10部分】

### 9.3、应用和配置分离

项目部署在k8s中，配置可以通过ConfigMap来进行读取，做到应用和配置分离的效果

### 9.4、可视化

在Gitlab中可以展示流水线先的DAG图

![image-20201015120152131](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjpvwixtpxj31270hxjt3.jpg)

### 9.5、灰度发布

利用flagger组件进行灰度发布，灰度发布示意图和执行日志如下

![img](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjq7yvytbnj31q40t6q3n.jpg)

![企业微信截图_16027489266827](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjq7xgtdtlj319406jq3p.jpg)


## 10、环境管理

### 10.1、环境确定

本次演示项目准备了四个环境，以下是环境说明和访问地址

|       环境        |                说明                |          访问地址           |
| :---------------: | :--------------------------------: | :-------------------------: |
|  开发环境（dev）  |             功能自测试             | dev-devops.baixing.cn:8088  |
| 测试环境（test）  | 包括功能测试、集成测试和压力测试等 | test-devops.baixing.cn:8088 |
| 预发布环境（stg） |  上线前的回归，和真实环境基本一致  | stg-devops.baixing.cn:8088  |
| 产线环境（prod）  |            最终产线环境            | prod-devops.baixing.cn:8088 |

### 10.2、环境交付

由于机器数量的限制，环境交付采用k8s的namespace做逻辑隔离

![企业微信截图_63aac2d6-c729-4df3-bf44-1806b776264d](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjorjygr1aj30k803yq3b.jpg)

## 

## 11、度量可视化

本次项目最终线上监控采用【Metrics】+ 【Log】+ 【Tracing】

### 11.1、日志

通过Fluntd采集日志文件，并输出到ES集群中，研发人员通过kibana可以查询对应服务的日志

![image-20201015145540865](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjq0xf5kbsj31gm0muai7.jpg)

### 11.2、Metrics

埋点数据指标通过Prometheus+Grafana进行展示，可以进行自定义

![image-20201015095040818](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjps413kvej31em0gttbr.jpg)

同时数据会进入ElasticStack中，通过Kibana进行展示

![image-20201015095209548](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjps5k2nm0j31ge0okq77.jpg)

![image-20201015095227567](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjps5vaqumj31gy0rf77q.jpg)

### 11.3、Tracing

链路追踪依赖APM组件，在Kibana中进行展示

![image-20201015095340138](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjps74s019j318v09kabb.jpg)

![image-20201015095304146](https://tva1.sinaimg.cn/large/007S8ZIlgy1gjps6ic17tj313d0lpjtd.jpg)
