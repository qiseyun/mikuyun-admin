package com.mikuyun.admin.util;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;
import java.util.regex.Pattern;

/**
 * 代码自动生成,因为是连的开发数据库生成的实体类,可能存在线上数据库表没有对应字段,一定要慎用。
 *
 * @author: jiangQL
 * @date: 4/12/24 8:06
 */
public class CodeGenerator {

    public static void main(String[] args) {
        codeAutoGenerator("jiangQL", "mk_dict", "mk_dict_type");
    }

    /**
     * @param auth       作者
     * @param tableNames 数据库表名数组
     */
    private static void codeAutoGenerator(String auth, String... tableNames) {

        String projectPath = StrUtil.join("", System.getProperty("user.dir"), "/src/main/java");

        String mapperPath = StrUtil.join("", System.getProperty("user.dir"), "/src/main/resources/mapper");

        //数据库配置
        String dbUrl = "jdbc:mysql://127.0.0.1:3306/mikuyun?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&allowMultiQueries=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai";
        String userName = "root";
        String password = "mikuyun";

        //tinyint转Integer
        DataSourceConfig.Builder sourceConfig = new DataSourceConfig.Builder(dbUrl, userName, password)
                .typeConvert(new CustomMySqlTypeConvert());

        FastAutoGenerator.create(sourceConfig)
                //全局配置
                .globalConfig(builder ->
                        builder.author(auth)
                                .enableSpringdoc()
                                .disableOpenDir()
                                .outputDir(projectPath).commentDate("yyyy-MM-dd HH:mm")
                )
                //包配置
                .packageConfig(builder ->
                        builder.parent("com.mikuyun.admin")
                                .entity("entity")
                                .moduleName("")
                                .pathInfo(Collections.singletonMap(OutputFile.xml, mapperPath))
                )
                //策略配置
                .strategyConfig(builder -> {
                    //需要生成代码的数据库表
                    builder.addInclude(tableNames)
                            .controllerBuilder()
                            .enableRestStyle()
                            .enableHyphenStyle().build();
                    //生成的类去除前缀
                    builder.addTablePrefix("mk");
                    //实体类配置
                    builder.entityBuilder()
                            .enableLombok()
                            .disableSerialVersionUID()
                            .enableChainModel();
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();

    }


    static class CustomMySqlTypeConvert extends MySqlTypeConvert {

        @Override
        public IColumnType processTypeConvert(GlobalConfig config, String fieldType) {
            IColumnType columnType = super.processTypeConvert(config, fieldType);
            if (Pattern.matches("^(tinyint|bit).*", fieldType) && DbColumnType.BOOLEAN.getType().equals(columnType.getType())) {
                return DbColumnType.INTEGER;
            }
            return columnType;
        }
    }

}
