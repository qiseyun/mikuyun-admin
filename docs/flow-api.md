# 流程管理 API 文档

> 基础路径：`http://{host}:{port}`
> 所有接口返回统一响应格式 `R<T>`，见 [通用说明](#通用说明)
> 
> 📖 **使用指南请参阅：** [flow-guide.md](flow-guide.md)

---

## 通用说明

### 响应格式

所有接口统一返回：

```json
{
  "code": 0,       // 0=成功, 1=失败, 其他见错误码表
  "msg": "success",
  "data": <T>      // 具体数据，类型见各接口
}
```

### 分页参数

分页查询接口继承 `BasePageDto`，通用参数：

| 参数 | 类型 | 默认值 | 说明 |
|------|------|--------|------|
| current | long | 1 | 当前页码 |
| size | long | 10 | 每页条数（最大100） |

> ⚠️ **注意**：分页接口返回的是 `R<List<T>>`（仅数据列表），不包含分页元信息（总数、总页数等）。如需要分页元信息，需扩展接口返回 `Page<T>`。

### 权限说明

接口通过 Sa-Token 权限校验，需在请求头携带 token。各接口需要的权限码见下方标注。

### 权限标识（permissionFlag）格式

`permissionFlag` 是流程中用于**指派审核人**的核心字段，格式为 `<类型>:<标识>`：

| 格式 | 示例 | 说明 |
|------|------|------|
| `role:N` | `role:1` | 角色ID为1的所有用户 |
| `user:N` | `user:zhangsan` | 指定用户 |
| `warmFlowInitiator` | `warmFlowInitiator` | 流程发起人（无需前缀） |

**多个值分隔规则**：
- **API 请求中**（JSON 数组）：`["role:1", "role:2", "user:boss"]`
- **流程设计 JSON 中**（字符串）：`"role:1@@role:2@@user:boss"`（用 `@@` 分隔）

`permissionFlag` 在**流程设计节点**中配置决定"谁能处理该节点"，也可在**启动流程**或**审批通过**时动态传入覆盖设计时配置。

### 办理人标识（handler）格式

`addHandlers` 和 `reductionHandlers` 使用与 `permissionFlag` 相同的格式：

```
["user:zhangsan", "user:lisi", "role:5"]
```

- `user:xxx` — 指定用户
- `role:N` — 角色下的所有用户

### 流程状态枚举

| 值 | 说明 |
|------|------|
| toDo | 待办 |
| pass | 已通过 |
| reject | 已驳回 |
| complete | 已完成 |
| termination | 已终止 |

### 流转类型枚举 (skipType)

| 值 | 说明 | 对应操作 |
|------|------|---------|
| PASS | 通过 | `/flowTask/pass` |
| REJECT | 驳回 | `/flowTask/reject` |
| TRANSFER | 转办 | `/flowTask/transfer` |
| DEPUTE | 委派 | `/flowTask/depute` |
| ADDSIGNATURE | 加签 | `/flowTask/addSignature` |
| REDUCTIONSIGNATURE | 减签 | `/flowTask/reductionSignature` |
| TERMINATION | 终止 | `/flowTask/termination` |
| REVOKE | 撤销 | `/flowTask/revoke` |

> ⚠️ **重要**：`skipType` 由各操作接口**自动确定**，调用方无需传入。即使传入也会被忽略。

### 节点类型

| nodeType | 名称 | 说明 |
|:--------:|------|------|
| 0 | 开始节点 | 流程起点，自动流转 |
| 1 | 中间节点 | 审批节点，需要用户操作 |
| 2 | 结束节点 | 流程终点，到达后状态变为 `complete` |

---

## 一、流程定义管理 `/flowDef`

权限前缀：`system:flow:def`

### 1.1 分页列表

```
GET /flowDef/list
```

**权限：** `system:flow:def:list`

**请求参数（Query String）：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| current | long | 否 | 当前页，默认1 |
| size | long | 否 | 每页条数，默认10 |
| flowCode | String | 否 | 流程编码（模糊匹配） |
| flowName | String | 否 | 流程名称（模糊匹配） |
| isPublish | Integer | 否 | 发布状态：0-未发布 1-已发布 |

**响应 `R<List<FlowDefVo>>`：**

```json
{
  "code": 0,
  "msg": "success",
  "data": [
    {
      "id": 123456789,
      "flowCode": "leave_apply",
      "flowName": "请假申请",
      "version": "1",
      "isPublish": 1,
      "isActive": 1,
      "fromPath": null,
      "listenerType": null,
      "listenerPath": null,
      "description": null,
      "createTime": "2026-06-18 10:00:00",
      "updateTime": "2026-06-18 12:00:00"
    }
  ]
}
```

### 1.2 流程详情（含节点）

```
GET /flowDef/detail/{id}
```

**权限：** `system:flow:def:list`

**响应 `R<FlowDefVo>`：** 同列表，额外包含 `nodeList` 字段：

```json
{
  "code": 0,
  "msg": "success",
  "data": {
    "id": 123456789,
    "flowCode": "leave_apply",
    "flowName": "请假申请",
    "version": "1",
    "isPublish": 1,
    "isActive": 1,
    "nodeList": [
      {
        "id": 1,
        "definitionId": 123456789,
        "nodeCode": "1",
        "nodeName": "开始",
        "nodeType": 0,
        "permissionFlag": null,
        "coordinate": "200,100"
      }
    ],
    "createTime": "2026-06-18 10:00:00",
    "updateTime": "2026-06-18 12:00:00"
  }
}
```

### 1.3 获取流程设计 JSON

```
GET /flowDef/design/{id}
```

**权限：** `system:flow:def:list`

**响应 `R<String>`：** `data` 为流程定义的完整 JSON 字符串，可用于导出或导入。

```json
{
  "code": 0,
  "msg": "success",
  "data": "{\"flowCode\":\"leave_apply\",\"flowName\":\"请假申请\",\"nodeList\":[...]}"
}
```

### 1.4 发布流程

```
POST /flowDef/publish?id={id}
```

**权限：** `system:flow:def:edit`

发布后流程定义变为"已发布"状态，可以启动流程实例。已发布的流程定义**不能编辑**，需要先取消发布。

**响应 `R<Void>`：** `{"code":0,"msg":"success","data":null}`

### 1.5 取消发布

```
POST /flowDef/unPublish?id={id}
```

**权限：** `system:flow:def:edit`

取消发布后可重新编辑流程定义。已有运行中的实例不受影响。

**响应 `R<Void>`**

### 1.6 激活流程

```
POST /flowDef/active?id={id}
```

**权限：** `system:flow:def:edit`

激活后该流程可正常使用。与挂起对应。

**响应 `R<Void>`**

### 1.7 挂起流程

```
POST /flowDef/unActive?id={id}
```

**权限：** `system:flow:def:edit`

挂起后该流程定义的已有实例无法继续操作。新流程也无法发起。

**响应 `R<Void>`**

### 1.8 删除流程定义

```
POST /flowDef/del
Content-Type: application/json
```

**权限：** `system:flow:def:delete`

**请求体：**

```json
{
  "idList": [123456789, 123456790]
}
```

> ⚠️ 删除流程定义前确保没有运行中的实例，否则可能导致数据不完整。

**响应 `R<Void>`**

### 1.9 复制流程定义

```
POST /flowDef/copy?id={id}
```

**权限：** `system:flow:def:add`

复制后的流程定义为"未发布"状态，版本号自动递增，可独立编辑。

**响应 `R<Void>`**

---

## 二、流程实例管理 `/flowIns`

权限前缀：`system:flow:ins`

### 2.1 分页列表（所有实例）

```
GET /flowIns/list
```

**权限：** `system:flow:ins:list`

**请求参数（Query String）：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| current | long | 否 | 当前页，默认1 |
| size | long | 否 | 每页条数，默认10 |
| definitionId | Long | 否 | 流程定义ID |
| flowCode | String | 否 | 流程编码 |
| businessId | String | 否 | 业务ID |
| flowStatus | String | 否 | 流程状态：toDo/pass/reject/complete/termination |
| createBy | String | 否 | 发起人ID（传入当前用户ID可查询"我的申请"） |

**响应 `R<List<FlowInsVo>>`：**

```json
{
  "code": 0,
  "msg": "success",
  "data": [
    {
      "id": 987654321,
      "definitionId": 123456789,
      "flowCode": "leave_apply",
      "flowName": "请假申请",
      "businessId": "BIZ-2026001",
      "nodeType": 1,
      "nodeCode": "3",
      "nodeName": "组长审批",
      "flowStatus": "toDo",
      "createBy": "admin",
      "ext": null,
      "createTime": "2026-06-18 10:30:00",
      "updateTime": "2026-06-18 10:30:00"
    }
  ]
}
```

### 2.2 我的申请

```
GET /flowIns/list?createBy={当前用户ID}
```

**权限：** `system:flow:ins:list`

查询当前用户发起的所有流程实例。传入 `createBy` 为当前登录用户即可。

> 💡 **提示**：此查询复用"分页列表"接口，通过 `createBy` 参数筛选。前端可直接从当前登录态获取用户ID作为 `createBy` 值。

### 2.3 实例详情

```
GET /flowIns/detail/{id}
```

**权限：** `system:flow:ins:list`

**响应 `R<FlowInsVo>`**

### 2.4 启动流程

```
POST /flowIns/start
Content-Type: application/json
```

**权限：** `system:flow:ins:start`

**请求体：**

```json
{
  "flowCode": "leave_apply",
  "businessId": "BIZ-2026001",
  "variable": {
    "reason": "家中有事"
  },
  "message": "提交请假申请",
  "permissionFlag": ["role:2", "role:3"]
}
```

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| flowCode | String | **是** | 流程编码 |
| businessId | String | **是** | 业务数据ID（关联业务表主键） |
| variable | Map | 否 | 流程变量，用于条件分支判断 |
| message | String | 否 | 审批消息/备注 |
| permissionFlag | List\<String\> | 否 | 权限标识，如 `["role:1", "user:2"]`。传入后会**覆盖**设计时配置的首节点审核人 |

**响应 `R<FlowInsVo>`：** 返回新创建的流程实例

### 2.5 激活实例

```
POST /flowIns/active/{id}
```

**权限：** `system:flow:ins:edit`

**响应 `R<Void>`**

### 2.6 挂起实例

```
POST /flowIns/unActive/{id}
```

**权限：** `system:flow:ins:edit`

挂起后该实例暂时无法进行任何审批操作。

**响应 `R<Void>`**

### 2.7 删除实例

```
POST /flowIns/del
Content-Type: application/json
```

**权限：** `system:flow:ins:delete`

**请求体：**

```json
{
  "idList": [987654321]
}
```

> ⚠️ 删除实例会同时清理关联的任务和历史记录。

**响应 `R<Void>`**

---

## 三、流程任务管理 `/flowTask`

权限前缀：`system:flow:task`

### 3.1 我的待办

```
GET /flowTask/myTodo
```

**权限：** `system:flow:task:list`

返回当前登录用户的待办任务列表，按创建时间倒序。

**请求参数（Query String）：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| current | long | 否 | 当前页，默认1 |
| size | long | 否 | 每页条数，默认10 |
| definitionId | Long | 否 | 流程定义ID |
| instanceId | Long | 否 | 流程实例ID |
| flowCode | String | 否 | 流程编码 |
| flowStatus | String | 否 | 任务状态 |

**响应 `R<List<FlowTaskVo>>`：**

```json
{
  "code": 0,
  "msg": "success",
  "data": [
    {
      "id": 555666777,
      "definitionId": 123456789,
      "instanceId": 987654321,
      "flowCode": "leave_apply",
      "flowName": "请假申请",
      "nodeCode": "3",
      "nodeName": "组长审批",
      "nodeType": 1,
      "permissionFlag": "role:1@@role:2",
      "handler": null,
      "flowStatus": "toDo",
      "createBy": "admin",
      "createTime": "2026-06-18 10:30:00",
      "updateTime": "2026-06-18 10:30:00"
    }
  ]
}
```

### 3.2 我的已办

```
GET /flowTask/myDone
```

**权限：** `system:flow:task:list`

参数同"我的待办"。

**响应 `R<List<FlowHisTaskVo>>`：**

```json
{
  "code": 0,
  "msg": "success",
  "data": [
    {
      "id": 111222333,
      "definitionId": 123456789,
      "instanceId": 987654321,
      "taskId": 555666777,
      "nodeCode": "3",
      "nodeName": "组长审批",
      "nodeType": 1,
      "flowStatus": "pass",
      "handler": "zhangsan",
      "skipType": "PASS",
      "message": "同意",
      "createBy": "admin",
      "createTime": "2026-06-18 11:00:00"
    }
  ]
}
```

### 3.3 任务详情

```
GET /flowTask/detail/{id}
```

**权限：** `system:flow:task:list`

**响应 `R<FlowTaskVo>`**

### 3.4 审批历史

```
GET /flowTask/hisList/{instanceId}
```

**权限：** `system:flow:task:list`

按时间正序返回该流程实例的完整审批链路，包括所有通过/驳回/转办/委派/加签/减签/终止/撤销记录。

**响应 `R<List<FlowHisTaskVo>>`**

---

### 3.5 审批通过

```
POST /flowTask/pass
Content-Type: application/json
```

**权限：** `system:flow:task:approve`

**请求体：**

```json
{
  "taskId": 555666777,
  "message": "同意请假",
  "variable": {},
  "permissionFlag": ["role:2"]
}
```

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| taskId | Long | 条件 | 任务ID（与instanceId二选一，优先taskId） |
| instanceId | Long | 条件 | 实例ID（会签场景下使用） |
| message | String | 否 | 审批意见 |
| variable | Map | 否 | 流程变量 |
| permissionFlag | List\<String\> | 否 | 下一节点的权限标识，传入后覆盖设计时配置 |
| nodeCode | String | 否 | 跳转到指定节点编码 |

**操作效果**：将当前任务标记为通过，流程沿 `PASS` 连线流转到下一个节点。下一节点的审核人根据其 `permissionFlag` 配置自动接收待办任务。

### 3.6 审批驳回

```
POST /flowTask/reject
Content-Type: application/json
```

**权限：** `system:flow:task:approve`

**请求体：** 格式同审批通过。

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| taskId | Long | 条件 | 任务ID（与instanceId二选一） |
| instanceId | Long | 条件 | 实例ID |
| message | String | 否 | 驳回理由 |
| variable | Map | 否 | 流程变量 |
| nodeCode | String | 否 | 指定驳回到的目标节点编码。不传则按流程设计中的 `REJECT` 连线自动跳转 |

**操作效果**：将当前任务标记为驳回，流程沿 `REJECT` 连线回到之前的节点（或通过 `nodeCode` 指定的节点）。

> ⚠️ **注意**：如果流程设计中当前节点没有配置 `REJECT` 连线，且未传 `nodeCode`，驳回操作会失败。建议在设计时为每个中间节点都配置驳回路径。

### 3.7 转办

```
POST /flowTask/transfer
Content-Type: application/json
```

**权限：** `system:flow:task:approve`

**请求体：**

```json
{
  "taskId": 555666777,
  "message": "转给张三处理",
  "addHandlers": ["user:zhangsan"]
}
```

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| taskId | Long | **是** | 任务ID |
| message | String | 否 | 转办说明 |
| addHandlers | List\<String\> | **是** | 目标办理人列表 |

**操作效果**：将任务彻底移交给目标办理人。原办理人失去处理权，任务从其待办中消失。目标办理人收到新任务。

### 3.8 委派

```
POST /flowTask/depute
Content-Type: application/json
```

**权限：** `system:flow:task:approve`

参数同转办。

**操作效果**：将任务临时委托给他人审批。被委派人审批完成后，任务回到原办理人。原办理人的待办任务保留。

### 3.9 加签

```
POST /flowTask/addSignature
Content-Type: application/json
```

**权限：** `system:flow:task:approve`

参数同转办。

**操作效果**：在当前节点新增办理人与原办理人并行审批（形成会签）。所有办理人都通过后，流程才会流转到下一节点。

### 3.10 减签

```
POST /flowTask/reductionSignature
Content-Type: application/json
```

**权限：** `system:flow:task:approve`

**请求体：**

```json
{
  "taskId": 555666777,
  "message": "移除李四审批",
  "reductionHandlers": ["user:lisi"]
}
```

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| taskId | Long | **是** | 任务ID |
| message | String | 否 | 减签说明 |
| reductionHandlers | List\<String\> | **是** | 要移除的办理人列表 |

> ⚠️ 减签只能移除之前通过加签增加的办理人，不能移除流程设计时配置的固定办理人。

### 3.11 终止流程

```
POST /flowTask/termination
Content-Type: application/json
```

**权限：** `system:flow:task:approve`

**按任务终止：**
```json
{
  "taskId": 555666777,
  "message": "业务变更，终止流程"
}
```

**按实例终止：**
```json
{
  "instanceId": 987654321,
  "message": "业务变更，终止流程"
}
```

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| taskId | Long | 条件 | 任务ID（与instanceId二选一） |
| instanceId | Long | 条件 | 实例ID（与taskId二选一） |
| message | String | 否 | 终止原因 |

**操作效果**：关闭该实例的所有待办任务，实例状态变为 `termination`。**不可恢复**，需要重新发起流程。

### 3.12 撤销流程

```
POST /flowTask/revoke
Content-Type: application/json
```

**权限：** `system:flow:task:approve`

**请求体：**

```json
{
  "instanceId": 987654321,
  "message": "撤回重新提交"
}
```

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| instanceId | Long | **是** | 实例ID（撤销只能按实例操作） |
| message | String | 否 | 撤销原因 |

**操作效果**：流程回到起始节点（发起人待办），发起人可修改后重新提交。与终止不同，撤销后可恢复。

---

## 四、通用审批操作参数速查

以下为各操作接口**实际需要的参数**速查表（`skipType` 无需传入，由接口自动确定）：

| 操作 | taskId | instanceId | message | permissionFlag | nodeCode | addHandlers | reductionHandlers |
|------|:------:|:----------:|:-------:|:--------------:|:--------:|:-----------:|:-----------------:|
| pass 通过 | 二选一 | 二选一 | 可选 | 可选 | 可选 | — | — |
| reject 驳回 | 二选一 | 二选一 | 可选 | 可选 | 可选 | — | — |
| transfer 转办 | **必填** | — | 可选 | — | — | **必填** | — |
| depute 委派 | **必填** | — | 可选 | — | — | **必填** | — |
| addSignature 加签 | **必填** | — | 可选 | — | — | **必填** | — |
| reductionSignature 减签 | **必填** | — | 可选 | — | — | — | **必填** |
| termination 终止 | 二选一 | 二选一 | 可选 | — | — | — | — |
| revoke 撤销 | — | **必填** | 可选 | — | — | — | — |

> "二选一"表示 `taskId` 和 `instanceId` 二者必传其一

---

## 五、流程设计器入口

Warm-Flow 自带可视化流程设计器，无需额外开发：

```
GET /warm-flow-ui/index?id={definitionId}&disabled={disabled}
```

| 参数 | 说明 |
|------|------|
| `id` | 流程定义ID。新建流程时留空，编辑已有流程时传入 |
| `disabled` | `true`=查看模式（不可编辑），`false`或留空=编辑模式 |

---

## 六、权限码清单

| 权限码 | 说明 | 对应操作 |
|--------|------|---------|
| `system:flow:def:list` | 流程定义查看 | 列表、详情、设计JSON |
| `system:flow:def:add` | 流程定义新增/复制 | 复制流程 |
| `system:flow:def:edit` | 流程定义编辑 | 发布/取消发布、激活/挂起 |
| `system:flow:def:delete` | 流程定义删除 | 删除流程定义 |
| `system:flow:ins:list` | 流程实例查看 | 实例列表、详情、我的申请 |
| `system:flow:ins:start` | 启动流程 | 发起新流程 |
| `system:flow:ins:edit` | 激活/挂起实例 | 激活、挂起 |
| `system:flow:ins:delete` | 删除实例 | 删除流程实例 |
| `system:flow:task:list` | 任务查看 | 我的待办、我的已办、任务详情、审批历史 |
| `system:flow:task:approve` | 任务审批 | 通过/驳回/转办/委派/加签/减签/终止/撤销 |

---

## 七、错误码

| code | 说明 |
|------|------|
| 0 | 成功 |
| 1 | 失败（通用错误） |
| 11011 | 未能读取到有效Token |
| 11012 | Token无效 |
| 11013 | Token已过期 |
| 60000 | 数据不存在 |
| 60100 | 数据已存在 |
| 611400 | 无权限（操作） |
| 611500 | 无角色权限 |

**Warm-Flow 引擎常见错误**：

| 场景 | 可能原因 | 解决方式 |
|------|---------|---------|
| 驳回操作失败 | 当前节点未配置 REJECT 路径 | 在设计器中为节点添加驳回连线 |
| 启动流程失败 | 流程未发布或未激活 | 先发布再激活流程定义 |
| 找不到下一节点 | PASS 连线指向的节点不存在 | 检查流程设计的连线配置 |
| 任务不存在 | taskId 错误或任务已被处理 | 刷新待办列表获取最新任务 |
