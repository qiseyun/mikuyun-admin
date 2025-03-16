package com.mikuyun.admin.util;

import java.time.format.DateTimeFormatter;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2023年4月30日/0030 0点52分
 */
public class DateTimeInitializeUtils {

    /**
     * yyyy-MM-dd
     */
    public static DateTimeFormatter commonDate() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    /**
     * yyyy/MM/dd
     */
    public static DateTimeFormatter slashDate() {
        return DateTimeFormatter.ofPattern("yyyy/MM/dd");
    }

    /**
     * yyyy年MM月dd日
     */
    public static DateTimeFormatter chineseDate() {
        return DateTimeFormatter.ofPattern("yyyy年MM月dd日");
    }

    /**
     * yyyy.MM.dd
     */
    public static DateTimeFormatter dotDate() {
        return DateTimeFormatter.ofPattern("yyyy.MM.dd");
    }

    /**
     * yyyyMMdd
     */
    public static DateTimeFormatter coiledYMD() {
        return DateTimeFormatter.ofPattern("yyyyMMdd");
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static DateTimeFormatter commonDateTime() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * yyyyMMddHHmmssSSS
     */
    public static DateTimeFormatter coiledYMDHMSS() {
        return DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    }

    /**
     * yyyyMMddHHmmss
     */
    public static DateTimeFormatter coiledYMDHMS() {
        return DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    }

    /**
     * yyyy年MM月dd日HH时mm分ss秒
     */
    public static DateTimeFormatter chineseDateTime() {
        return DateTimeFormatter.ofPattern("yyyy年MM月dd日HH时mm分ss秒");
    }

    /**
     * yyyy.MM.dd HH.mm.ss
     */
    public static DateTimeFormatter dotDateTime() {
        return DateTimeFormatter.ofPattern("yyyy.MM.dd HH.mm.ss");
    }

}
