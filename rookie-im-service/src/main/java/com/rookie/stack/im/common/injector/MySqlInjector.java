package com.rookie.stack.im.common.injector;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.injector.methods.*;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import org.apache.ibatis.session.Configuration;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Name：MySqlInjector
 * Author：eumenides
 * Created on: 2025/1/2
 * Description:
 */
public class MySqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Configuration configuration, Class<?> mapperClass, TableInfo tableInfo) {
        // 从全局配置中获取数据库配置
        GlobalConfig.DbConfig dbConfig = GlobalConfigUtils.getDbConfig(configuration);

        // 构建基础方法列表
        Stream.Builder<AbstractMethod> builder = Stream.<AbstractMethod>builder()
                .add(new Insert(dbConfig.isInsertIgnoreAutoIncrementColumn())) // 插入
                .add(new Delete())                                            // 删除
                .add(new Update())                                            // 更新
                .add(new SelectCount())                                       // 查询总数
                .add(new SelectMaps())                                        // 查询 Map 集合
                .add(new SelectObjs())                                        // 查询单个字段集合
                .add(new SelectList());                                       // 查询列表

        // 如果表有主键，添加 ById 系列方法
        if (tableInfo.havePK()) {
            builder.add(new DeleteById())
                    .add(new DeleteByIds())
                    .add(new UpdateById())
                    .add(new SelectById())
                    .add(new SelectByIds());
        } else {
            logger.warn(String.format("%s ,Not found @TableId annotation, Cannot use Mybatis-Plus 'xxById' Method.",
                    tableInfo.getEntityType()));
        }

        // 添加批量插入方法，自动跳过 `FieldFill.UPDATE` 标记的字段
        builder.add(new InsertBatchSomeColumn(column -> column.getFieldFill() != FieldFill.UPDATE));

        return builder.build().collect(Collectors.toList());
    }
}
