package com.spidey.openmusicapi.utils;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.yulichang.extension.mapping.base.MPJDeepService;
import com.github.yulichang.wrapper.JoinAbstractWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.spidey.openmusicapi.common.SFModel;
import com.spidey.openmusicapi.common.SFPage;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.spidey.openmusicapi.utils.IdentifierUtils.camel2snake;

@UtilityClass
public class SFPageUtils {

    /**
     * 可排序过滤搜索的分页查询参数预处理
     *
     * @param wrapper 查询条件
     * @param model   可排序过滤的分页参数模型
     * @param columns 可搜索的字段
     * @param <T>     实体类型
     * @param <W>     查询包装器类型
     * @return 分页参数
     */
    public <T, W extends AbstractWrapper<T, String, ? extends W>>
    W prepareForPaging(
            @NonNull W wrapper,
            @NonNull SFModel model,
            List<String> columns) {
        // 搜索逻辑
        wrapper.and(
                model.isSearching() && !columns.isEmpty(),
                qw -> {
                    for (int i = 0; i < columns.size(); i++) {
                        qw.like(camel2snake(columns.get(i)), model.getKeyword());
                        if (i + 1 < columns.size()) {
                            qw.or();
                        }
                    }
                }
        );
        // 过滤逻辑
        wrapper.and(
                model.isFiltering(),
                qw -> model.getFilters().forEach((s, list) -> {
                    qw.in(camel2snake(s), list);
                }));
        // 排序逻辑
        wrapper.orderBy(model.isSorting(), model.isAscending(), camel2snake(model.getSort()));
        return wrapper;
    }

    /**
     * 可排序过滤搜索的分页查询参数预处理 - MPJ版本的Join版本
     *
     * @param wrapper 查询条件
     * @param model   可排序过滤的分页参数模型
     * @param columns 可搜索的字段
     * @param <T>     实体类型
     * @param <W>     查询包装器类型
     * @return 分页参数
     */
    public <T, W extends JoinAbstractWrapper<T, ? extends W>>
    W prepareForPaging(
            @NonNull W wrapper,
            @NonNull SFModel model,
            List<String> columns) {
        Function<String, String> prepareColumn = column -> wrapper.getAlias() + "." + camel2snake(column);
        // 搜索逻辑
        wrapper.and(
                model.isSearching() && !columns.isEmpty(),
                qw -> {
                    for (int i = 0; i < columns.size(); i++) {
                        qw.like(prepareColumn.apply(columns.get(i)), model.getKeyword());
                        if (i + 1 < columns.size()) {
                            qw.or();
                        }
                    }
                }
        );
        // 过滤逻辑
        wrapper.and(
                model.isFiltering(),
                qw -> model.getFilters().forEach((s, list) -> {
                    qw.in(prepareColumn.apply(s), list);
                }));
        // 排序逻辑
        wrapper.orderBy(model.isSorting(), model.isAscending(), prepareColumn.apply(model.getSort()));
        return wrapper;
    }

    /**
     * 分页查询
     *
     * @param service IService服务
     * @param wrapper 查询条件
     * @param model   可排序过滤的分页参数模型
     * @param columns 可搜索的字段
     * @param <T>     实体类型
     * @return 分页查询结果
     */
    public <T> SFPage<T> doPage(
            @NonNull IService<T> service,
            @NonNull QueryWrapper<T> wrapper,
            @NonNull SFModel model,
            String... columns) {
        return SFPage.of(
                service.page(
                        model.toPage(),
                        prepareForPaging(wrapper, model, List.of(columns)))
        ).extractFrom(model);
    }

    /**
     * 分页查询 - 递归查询
     *
     * @param service           IService服务
     * @param wrapper           查询条件
     * @param model             可排序过滤的分页参数模型
     * @param columns 可搜索的字段
     * @param <T>               实体类型
     * @return 分页查询结果
     */
    public <T> SFPage<T> doPageDeep(
            @NonNull MPJDeepService<T> service,
            @NonNull QueryWrapper<T> wrapper,
            @NonNull SFModel model,
            String... columns) {
        return SFPage.of(
                service.pageDeep(
                        model.toPage(),
                        prepareForPaging(wrapper, model, List.of(columns)))
        ).extractFrom(model);
    }

    public <T> SFPage<T> doPage(
            @NonNull IService<T> service,
            @NonNull SFModel model,
            String... columns) {
        return doPage(service, new QueryWrapper<>(), model, columns);
    }

    public <T> SFPage<T> doPageDeep(
            @NonNull MPJDeepService<T> service,
            @NonNull SFModel model,
            String... columns) {
        return doPageDeep(service, new QueryWrapper<>(), model, columns);
    }

    public <T> MPJLambdaWrapper<T> prepareForJoinListPaging(
            MPJLambdaWrapper<T> wrapper,
            SFModel model,
            String[] columns
    ) {
        Function<String, String> prepareColumn = column -> wrapper.getAlias() + "." + camel2snake(column);
        return SFPageUtils.prepareForPaging(
                wrapper,
                model,
                Stream.of(columns)
                        .map(prepareColumn)
                        .toList());
    }

}
