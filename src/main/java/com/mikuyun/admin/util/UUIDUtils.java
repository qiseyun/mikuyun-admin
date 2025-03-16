package com.mikuyun.admin.util;

import cn.hutool.core.util.IdUtil;


/**
 * @author qiseyun
 * @version 1.0
 * @date 2023/3/8 11:47
 */
public class UUIDUtils {

    /**
     * cdk生成规则
     *
     * @return String
     */
    public static String cdkUuid() {
        return "CDK_" + IdUtil.fastUUID();
    }

    /**
     * 简单UUID
     *
     * @return String
     */
    public static String simpleUuid() {
        return IdUtil.simpleUUID();
    }

}
