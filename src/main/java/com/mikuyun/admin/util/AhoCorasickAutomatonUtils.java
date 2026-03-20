package com.mikuyun.admin.util;

import com.mikuyun.admin.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/12/17 22:21
 */
@Slf4j
@Component
public class AhoCorasickAutomatonUtils {

    /**
     * 根节点（虚拟字符）
     */
    private final AcNode root = new AcNode('^');

    private final ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

    /**
     * 初始化：从文件加载敏感词库
     * 在 Bean 初始化后自动加载 vocabulary/ 目录下的所有文件
     *
     * @throws IOException IOException
     */
    @PostConstruct
    public void init() throws IOException {
        // 加载 vocabulary 目录下所有 .txt 文件（可按需调整通配符）
        Resource[] resources = resourceResolver.getResources("classpath:vocabulary/*.txt");

        if (resources.length == 0) {
            throw new BizException("敏感词库 vocabulary/*.txt 未找到");
        }

        for (Resource resource : resources) {
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
                String word;
                while ((word = reader.readLine()) != null) {
                    word = word.trim();
                    if (!word.isEmpty()) {
                        addWord(word);
                    }
                }
            }
        }

        buildFailureLinks();
        log.info("敏感词库挂载成功，共加载 {} 个文件", resources.length);
    }

    /**
     * 添加关键词到 Trie
     *
     * @param word 关键词
     */
    private void addWord(String word) {
        AcNode cur = root;
        for (char c : word.toCharArray()) {
            cur.children.putIfAbsent(c, new AcNode(c));
            AcNode next = cur.children.get(c);
            next.parent = cur;
            cur = next;
        }
        cur.isEnd = true;
        cur.word = word;
    }

    /**
     * 构建失败指针（核心！使用 BFS）
     */
    private void buildFailureLinks() {
        Queue<AcNode> queue = new LinkedList<>();
        // 第一层子节点的 fail 指向 root
        for (AcNode child : root.children.values()) {
            child.fail = root;
            queue.offer(child);
        }
        while (!queue.isEmpty()) {
            AcNode current = queue.poll();
            for (Map.Entry<Character, AcNode> entry : current.children.entrySet()) {
                char ch = entry.getKey();
                AcNode child = entry.getValue();
                // 找 current.fail 的子节点中是否有 ch
                AcNode f = current.fail;
                while (f != root && !f.children.containsKey(ch)) {
                    f = f.fail;
                }
                child.fail = f.children.getOrDefault(ch, root);
                // 优化：如果 fail 节点是关键词结尾，继承其敏感性（可选）
                // 这样可以匹配 "abc" 和 "bc" 同时存在的情况
                if (child.fail.isEnd) {
                    child.isEnd = true;
                }
                queue.offer(child);
            }
        }
    }

    /**
     * 检测文本是否包含敏感词
     *
     * @param text 需要检测的文本
     * @return true:包含 false:不包含
     */
    public Boolean containsSensitiveWord(String text) {
        return !getSensitiveWords(text).isEmpty();
    }

    /**
     * 获取所有匹配到的敏感词（去重）
     *
     * @param text 需要检测的文本
     * @return Set<String>
     */
    public Set<String> getSensitiveWords(String text) {
        Set<String> matches = new HashSet<>();
        AcNode cur = root;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            // 沿着 fail 链回退，直到找到匹配或回到 root
            while (cur != root && !cur.children.containsKey(c)) {
                cur = cur.fail;
            }
            // 安全兜底
            cur = cur.children.getOrDefault(c, root);
            // 检查当前节点及其 fail 链上是否有关键词
            AcNode temp = cur;
            while (temp != root) {
                if (temp.isEnd && temp.word != null) {
                    matches.add(temp.word);
                }
                temp = temp.fail;
            }
        }
        return matches;
    }

    /**
     * 替换敏感词为指定字符（如 *）
     *
     * @param text        需要检测的文本
     * @param replacement 替换字符
     * @return 已和谐的文本
     */
    public String replaceSensitiveWords(String text, char replacement) {
        // 标记哪些位置要替换
        boolean[] mask = new boolean[text.length()];
        AcNode cur = root;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            while (cur != root && !cur.children.containsKey(c)) {
                cur = cur.fail;
            }
            cur = cur.children.getOrDefault(c, root);
            // 回溯 fail 链，标记所有匹配的词
            AcNode temp = cur;
            while (temp != root) {
                if (temp.isEnd && temp.word != null) {
                    int len = temp.word.length();
                    // 从当前位置向前标记 len 个字符
                    for (int j = Math.max(0, i - len + 1); j <= i; j++) {
                        mask[j] = true;
                    }
                }
                temp = temp.fail;
            }
        }
        // 构建结果
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            sb.append(mask[i] ? replacement : text.charAt(i));
        }
        return sb.toString();
    }

}
