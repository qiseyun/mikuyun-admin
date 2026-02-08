package com.mikuyun.admin.evt;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2024年1月14日/0014 10点54分
 */
@Data
public class BasePageEvt {

    /**
     * 当前页
     */
    private long current = 1;

    /**
     * 每页大小
     */
    private long size = 10;

    /**
     * 偏移量
     */
    private long offset;


    /**
     * 获取一个分页对象关闭count
     */
    public Page<T> generatePageCountFalse() {
        return new Page<>(current, size, false);
    }

    /**
     * 获取一个分页对象(打开count)
     */
    public Page<T> generatePageCountTrue() {
        return new Page<>(current, size, true);
    }

    public void initPageParams() {
        this.offset = (this.current - 1) * this.size;
    }

}
