package com.mikuyun.admin.common;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2024/3/13 13:33
 */
public class Constant {

    /**
     * 删除
     */
    public static final Integer STATUS_DEL_INT = 1;

    /**
     * 正常
     */
    public static final Integer STATUS_NORMAL_INT = 0;

    /**
     * 成功标记
     */
    public static final Integer SUCCESS = 0;

    /**
     * 失败标记
     */
    public static final Integer FAIL = 1;

    /**
     * 失败标记
     */
    public static final String FAIL_STR = "fail";

    /**
     * 成功标记
     */
    public static final String SUCCESS_STR = "success";

    /**
     * JSON 资源
     */
    public static final String CONTENT_TYPE = "application/json; charset=utf-8";

    /**
     * MIKUYUN
     */
    public static final String MIKUYUN = "mikuyun";

    /**
     * 系统配置key
     */
    public static class SysConfigKey {


    }

    /**
     * redis缓存key
     */
    public static class CacheConstants {

        /**
         * 字典信息缓存
         */
        public static final String DICT_DETAILS = "dict_info";

        /**
         * 菜单树
         */
        public static final String MENU_TREE = "admin:menu_tree:";

        /**
         * 菜单树
         */
        public static final String NORMAL_EXCEL_TASK = "excel:task:normal";

        /**
         * 菜单树
         */
        public static final String SLOW_EXCEL_TASK = "excel:task:slow";

    }

    /**
     * satoken相关key
     */
    public static class Satoken {

        public static final String ADMIN_SESSION_KEY = "admin_session";

    }

}
