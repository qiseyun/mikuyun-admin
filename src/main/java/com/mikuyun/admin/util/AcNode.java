package com.mikuyun.admin.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/12/17 22:20
 * AC 自动机节点
 */
public class AcNode {

    /**
     * 当前字符
     */
    char ch;

    /**
     * 是否为关键词结尾
     */
    boolean isEnd = false;

    /**
     * 如果是结尾，记录完整关键词（用于返回）
     */
    String word = null;

    /**
     *
     */
    Map<Character, AcNode> children = new HashMap<>();

    /**
     * 失败指针
     */
    AcNode fail = null;

    /**
     * 父节点（可选，用于调试）
     */
    AcNode parent = null;

    public AcNode(char ch) {
        this.ch = ch;
    }

}
