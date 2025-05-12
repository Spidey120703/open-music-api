package com.spidey.openmusicapi.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.yulichang.extension.mapping.base.MPJDeepService;
import com.github.yulichang.query.MPJQueryWrapper;
import com.spidey.openmusicapi.common.SFModel;
import com.spidey.openmusicapi.common.SFPage;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.function.BiFunction;

import static com.spidey.openmusicapi.utils.IdentifierUtils.camel2snake;

@UtilityClass
public class SFPageUtils {

    /**
     * 可排序过滤搜索的分页查询参数预处理
     *
     * @param model   可排序过滤的分页参数模型
     * @param columns 可搜索的字段
     * @return 分页参数
     */
    private <T> IPage<T> prepareForPaging(
            @NonNull BiFunction<IPage<T>, QueryWrapper<T>, IPage<T>> pageExecutor,
            @NonNull SFModel model,
            List<String> columns) {
        Page<T> page = new Page<>(model.getCurrent(), model.getSize());
        QueryWrapper<T> wrapper = new QueryWrapper<>();
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
        return pageExecutor.apply(page, wrapper);
    }

    /**
     * 分页查询
     *
     * @param service IService服务
     * @param model 可排序过滤的分页参数模型
     * @param columns 可搜索的字段
     * @return 分页查询结果
     * @param <T> 实体类型
     */
    public <T> SFPage<T> paging(
            @NonNull IService<T> service,
            @NonNull SFModel model,
            String... columns) {
        IPage<T> iPage = prepareForPaging(service::page, model, List.of(columns));
        return SFPage.extend(iPage, model);
    }

    /**
     * 分页查询 - 递归查询
     *
     * @param service IService服务
     * @param model 可排序过滤的分页参数模型
     * @param searchableColumns 可搜索的字段
     * @return 分页查询结果
     * @param <T> 实体类型
     */
    public <T> SFPage<T> pagingDeep(
            @NonNull MPJDeepService<T> service,
            @NonNull SFModel model,
            String... searchableColumns) {
        IPage<T> iPage = prepareForPaging(service::pageDeep, model, List.of(searchableColumns));
        return SFPage.extend(iPage, model);
    }

}
