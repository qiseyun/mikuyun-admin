package com.mikuyun.admin.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.mikuyun.admin.common.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * MybatisPlus 自动填充配置
 *
 * @author qise
 */
@Slf4j
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    /**
     * 新增自动填充
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("mybatis plus start insert fill ....");
        LocalDateTime now = LocalDateTime.now();
        fillStrategy(metaObject, "gmtCreated", now);
        fillStrategy(metaObject, "gmtModified", now);
        // 删除标记自动填充
        fillStrategy(metaObject, "isDelete", Constant.STATUS_NORMAL_INT);
    }

    /**
     * 修改自动填充
     *
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("mybatis plus start update fill ....");
        fillStrategy(metaObject, "gmtModified", LocalDateTime.now());
    }

}
