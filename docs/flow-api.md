# 流程管理 API 文档

> 基础路径：`http://{host}:{port}`
> 所有接口返回统一响应格式 `R<T>`，见 [通用说明](#通用说明)

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

### 权限说明

接口通过 Sa-Token 权限校验，需在请求头携带 token。各接口需要的权限码见下方标注。

### 流程状态枚举

| 值 | 说明 |
|------|------|
| toDo | 待办 |
| pass | 已通过 |
| reject | 已驳回 |
| complete | 已完成 |
| termination | 已终止 |

### 流转类型枚举 (skipType)

| 值 | 说明 |
|------|------|
| PASS | 通过 |
| REJECT | 驳回 |
| TRANSFER | 转办 |
| DEPUTE | 委派 |
| ADDSIGNATURE | 加签 |
| REDUCTIONSIGNATURE | 减签 |
| TERMINATION | 终止 |
| REVOKE | 撤销 |

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

**响应 `R<String>`：**

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

**响应 `R<Void>`：** `{"code":0,"msg":"success","data":null}`

### 1.5 取消发布

```
POST /flowDef/unPublish?id={id}
```

**权限：** `system:flow:def:edit`

**响应 `R<Void>`**

### 1.6 激活流程

```
POST /flowDef/active?id={id}
```

**权限：** `system:flow:def:edit`

**响应 `R<Void>`**

### 1.7 挂起流程

```
POST /flowDef/unActive?id={id}
```

**权限：** `system:flow:def:edit`

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

**响应 `R<Void>`**

### 1.9 复制流程定义

```
POST /flowDef/copy?id={id}
```

**权限：** `system:flow:def:add`

**响应 `R<Void>`**

---

## 二、流程实例管理 `/flowIns`

权限前缀：`system:flow:ins`

### 2.1 分页列表

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
| createBy | String | 否 | 发起人ID |

**响应 `R<List<FlowInsVo>>`：**

```json
{
  "code": 0,
  "msg": "success",
  "data": [
    {
      "id": 987654321,
      "definitionId": 123456789,
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

### 2.2 实例详情

```
GET /flowIns/detail/{id}
```

**权限：** `system:flow:ins:list`

**响应 `R<FlowInsVo>`**

### 2.3 启动流程

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
| businessId | String | **是** | 业务数据ID |
| variable | Map | 否 | 流程变量 |
| message | String | 否 | 审批消息 |
| permissionFlag | List\<String\> | 否 | 权限标识，如 `["role:1", "user:2"]` |

**响应 `R<FlowInsVo>`：** 返回新创建的流程实例

### 2.4 激活实例

```
POST /flowIns/active/{id}
```

**权限：** `system:flow:ins:edit`

**响应 `R<Void>`**

### 2.5 挂起实例

```
POST /flowIns/unActive/{id}
```

**权限：** `system:flow:ins:edit`

**响应 `R<Void>`**

### 2.6 删除实例

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

**响应 `R<Void>`**

---

## 三、流程任务管理 `/flowTask`

权限前缀：`system:flow:task`

### 3.1 我的待办

```
GET /flowTask/myTodo
```

**权限：** `system:flow:task:list`

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
      "flowName": "请假申请",
      "nodeCode": "3",
      "nodeName": "组长审批",
      "nodeType": 1,
      "flowStatus": "toDo",
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
      "skipType": "PASS",
      "message": "同意",
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

按时间正序返回该流程实例的完整审批链路。

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
  "skipType": "PASS",
  "message": "同意请假",
  "variable": {},
  "permissionFlag": ["role:2"]
}
```

### 3.6 审批驳回

```
POST /flowTask/reject
Content-Type: application/json
```

**权限：** `system:flow:task:approve`

**请求体：** 格式同审批通过，`skipType` 设为 `REJECT`

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

### 3.8 委派

```
POST /flowTask/depute
Content-Type: application/json
```

**权限：** `system:flow:task:approve`

参数同转办。

### 3.9 加签

```
POST /flowTask/addSignature
Content-Type: application/json
```

**权限：** `system:flow:task:approve`

参数同转办。

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

### 3.11 终止流程

```
POST /flowTask/termination
Content-Type: application/json
```

**权限：** `system:flow:task:approve`

**请求体：**

```json
{
  "taskId": 555666777,
  "message": "业务变更，终止流程"
}
```
> 也可传 `instanceId` 代替 `taskId`

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

---

## 四、通用审批操作参数 FlowActionDto

以下操作共用此参数模型：`pass`/`reject`/`transfer`/`depute`/`addSignature`/`reductionSignature`/`termination`/`revoke`

| 字段 | 类型 | 必填 | 适用操作 | 说明 |
|------|------|------|----------|------|
| taskId | Long | 条件 | 所有 | 任务ID（与instanceId二选一） |
| instanceId | Long | 条件 | 所有 | 实例ID（与taskId二选一） |
| nodeCode | String | 否 | pass/reject | 目标节点编码 |
| skipType | String | **是** | 所有 | 流转类型，见上方枚举 |
| message | String | 否 | 所有 | 审批意见/备注 |
| variable | Map | 否 | 所有 | 流程变量 |
| permissionFlag | List\<String\> | 否 | 所有 | 权限标识 |
| addHandlers | List\<String\> | 否 | transfer/depute/addSignature | 新增办理人 |
| reductionHandlers | List\<String\> | 否 | reductionSignature | 减少办理人 |

---

## 五、流程设计器入口

Warm-Flow 自带流程设计器，无需额外开发：

```
GET /warm-flow-ui/index?id={definitionId}&disabled={disabled}
```

- `id` — 流程定义ID（新建时可省略）
- `disabled` — 是否禁用编辑（查看模式）

---

## 六、权限码清单

| 权限码 | 说明 |
|--------|------|
| `system:flow:def:list` | 流程定义查看 |
| `system:flow:def:add` | 流程定义新增/复制 |
| `system:flow:def:edit` | 流程定义编辑（发布/激活/挂起） |
| `system:flow:def:delete` | 流程定义删除 |
| `system:flow:ins:list` | 流程实例查看 |
| `system:flow:ins:start` | 启动流程 |
| `system:flow:ins:edit` | 激活/挂起实例 |
| `system:flow:ins:delete` | 删除实例 |
| `system:flow:task:list` | 任务查看（待办/已办/历史） |
| `system:flow:task:approve` | 任务审批（通过/驳回/转办/委派/加签/减签/终止/撤销） |

---

## 七、错误码

| code | 说明 |
|------|------|
| 0 | 成功 |
| 1 | 失败 |
| 11011 | 未能读取到有效Token |
| 11012 | Token无效 |
| 11013 | Token已过期 |
| 60000 | 数据不存在 |
| 60100 | 数据已存在 |
| 611400 | 无权限 |
| 611500 | 无角色权限 |
